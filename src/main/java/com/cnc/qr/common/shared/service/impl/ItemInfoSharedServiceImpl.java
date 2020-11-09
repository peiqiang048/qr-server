package com.cnc.qr.common.shared.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.component.ImageUtilJnb;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MLicense;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.OTaxAmount;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MOptionTypeRepository;
import com.cnc.qr.common.repository.MSequenceRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.ODeliveryOrderSummaryRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OTaxAmountRepository;
import com.cnc.qr.common.shared.model.GetItemOptionInfoInputDto;
import com.cnc.qr.common.shared.model.GetItemOptionInfoOutputDto;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.StoreOpenColseTimeDto;
import com.cnc.qr.common.shared.model.TaxAmountInputDto;
import com.cnc.qr.common.shared.model.TaxInfoDto;
import com.cnc.qr.common.shared.model.UploadFileDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.ItemOptionDataDto;
import com.cnc.qr.core.order.model.ItemOptionDto;
import com.cnc.qr.core.order.model.ItemOptionTypeDto;
import com.cnc.qr.core.order.model.ItemsDto;
import com.cnc.qr.core.order.model.ItemsTempDto;
import com.github.dozermapper.core.Mapper;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品情報共有サービス実装クラス.
 */
@Service
public class ItemInfoSharedServiceImpl implements ItemInfoSharedService {

    /**
     * 税情報マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * 税額テーブルリポジトリ..
     */
    @Autowired
    private OTaxAmountRepository taxAmountRepository;

    /**
     * オプション種類マスタリポジトリ.
     */
    @Autowired
    private MOptionTypeRepository optionTypeRepository;

    /**
     * シーケンスマスタリポジトリ.
     */
    @Autowired
    private MSequenceRepository sequenceRepository;

    /**
     * 画像圧縮クラス.
     */
    @Autowired
    private ImageUtilJnb imageUtilJnb;

    /**
     * 注文サマリテーブルリポジトリ.
     */
    @Autowired
    private OOrderSummaryRepository orderSummaryRepository;

    /**
     * 注文サマリテーブルリポジトリ.
     */
    @Autowired
    private ODeliveryOrderSummaryRepository deliveryOrderSummaryRepository;

    /**
     * 環境変数.
     */
    @Autowired
    private Environment env;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 税情報取得.
     *
     * @param inputDto 検索条件
     * @return 税情報
     */
    @Override
    public GetTaxValueOutputDto getTaxValue(GetTaxValueInputDto inputDto) {

        // 商品情報リスト
        List<ItemsDto> itemList = new ArrayList<>();

        // 外税金額合計
        BigDecimal[] sotoTaxPriceSum = {BigDecimal.ZERO};
        // 外税10%值(税値)
        BigDecimal[] sotoTaxTenPriceSum = {BigDecimal.ZERO};
        // 外税8%值(税値)
        BigDecimal[] sotoTaxEightPriceSum = {BigDecimal.ZERO};
        // 外税8%对象值（商品総価）
        BigDecimal[] itemSotoTaxEightSumPrice = {BigDecimal.ZERO};
        // 外税10%对象值（商品総価）
        BigDecimal[] itemSotoTaxTenSumPrice = {BigDecimal.ZERO};
        // 内税金額合計
        BigDecimal[] utiTaxPriceSum = {BigDecimal.ZERO};
        // 内税10%值(税値)
        BigDecimal[] utiTaxTenPriceSum = {BigDecimal.ZERO};
        // 内税8%值(税値)
        BigDecimal[] utiTaxEightPriceSum = {BigDecimal.ZERO};
        // 内税8%对象值（商品総価）
        BigDecimal[] itemUtiTaxEightSumPrice = {BigDecimal.ZERO};
        // 内税10%对象值（商品総価）
        BigDecimal[] itemUtiTaxTenSumPrice = {BigDecimal.ZERO};

        // グループ化
        Map<String, List<ItemsDto>> itemInfoGroupMap = inputDto.getItemList().stream()
            .collect(Collectors.groupingBy(ItemsDto::getItemId));

        // 商品IDリスト
        List<Integer> itemsId = new ArrayList<>();

        // 商品コードを単位に合計価格
        itemInfoGroupMap.forEach((key, itemInfoList) -> {

            // 商品コードを単位に合計金額取得
            List<ItemsTempDto> itemInfoTempList = new ArrayList<>();

            // 合計価格
            for (ItemsDto item : itemInfoList) {
                BigDecimal subSum = BigDecimal.ZERO;
                ItemsTempDto itemsTempDto = new ItemsTempDto();
                itemsTempDto.setItemId(item.getItemId());
                subSum = subSum.add(item.getItemPrice());
                itemsTempDto.setItemIdPriceSum(subSum.longValue());
                itemInfoTempList.add(itemsTempDto);
            }

            // 商品コードにより、商品の合計金額
            BigDecimal priceSum = new BigDecimal(
                itemInfoTempList.stream().mapToLong(ItemsTempDto::getItemIdPriceSum).sum());

            // 整形後のデータ
            ItemsDto itemInfo = new ItemsDto();
            itemInfo.setItemId(key);
            itemInfo.setItemPrice(priceSum);
            itemList.add(itemInfo);
            itemsId.add(Integer.valueOf(key));

        });

        // ビジネスIDを取得する
        MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 税情報取得
        List<TaxInfoDto> taxInfoList = itemRepository.getTaxInfo(store.getBusinessId(),
            inputDto.getStoreId(), ZonedDateTime.now(), itemsId);
        if (taxInfoList.size() == 0) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.052", (Object) null));
        }
        // グループ化
        Map<Integer, List<TaxInfoDto>> itemTaxInfoGroupMap = taxInfoList.stream()
            .collect(Collectors.groupingBy(TaxInfoDto::getTaxId));

        // グループを単位に合計取得
        itemTaxInfoGroupMap.forEach((key, taxInfoCalculationList) -> {
            if (CollectionUtils.isNotEmpty(taxInfoCalculationList)) {

                // 税率
                BigDecimal tax = BigDecimal.ZERO;
                // 税値
                BigDecimal taxValue = BigDecimal.ZERO;
                // 外税商品価格
                BigDecimal itemSumTempPrice = BigDecimal.ZERO;

                // 税区分判定 外税の場合
                if (Objects.equals(CodeConstants.TaxType.SOTO_RATE.getCode(),
                    taxInfoCalculationList.get(0).getTaxCode())) {

                    // 外税(税値)処理

                    // 適用税率決定
                    tax = this.taxReliefApplyTyp(taxInfoCalculationList, inputDto);

                    // 外税計算
                    taxValue = applicableTaxItemPrice(taxInfoCalculationList, itemList)
                        .multiply(tax);

                    // 端数処理
                    taxValue = taxRoundType(taxValue,
                        taxInfoCalculationList.get(0).getTaxRoundType());

                    // 商品外税金額合計取得
                    sotoTaxPriceSum[0] = sotoTaxPriceSum[0].add(taxValue);

                    // 外税10%对象值（商品総価）
                    tax = BigDecimal.ZERO;
                    taxValue = BigDecimal.ZERO;
                    tax = this.taxReliefApplyTypTaxTen(taxInfoCalculationList, inputDto);
                    if (tax.compareTo(BigDecimal.ZERO) > 0) {

                        // 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得する
                        BigDecimal itemSumPrice = applicableTaxItemPrice(taxInfoCalculationList,
                            itemList);

                        // 外税10%对象值
                        itemSotoTaxTenSumPrice[0] = itemSotoTaxTenSumPrice[0].add(itemSumPrice);
                        // 外税計算
                        taxValue = itemSumPrice.multiply(tax);

                        // 端数処理
                        taxValue = taxRoundType(taxValue,
                            taxInfoCalculationList.get(0).getTaxRoundType());

                        // 外税10%值(税値)
                        sotoTaxTenPriceSum[0] = sotoTaxTenPriceSum[0].add(taxValue);
                    }

                    // 外税8%对象值（商品総価）
                    tax = BigDecimal.ZERO;
                    taxValue = BigDecimal.ZERO;
                    tax = this.taxReliefApplyTypTaxEight(taxInfoCalculationList, inputDto);
                    if (tax.compareTo(BigDecimal.ZERO) > 0) {

                        // 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得する
                        BigDecimal itemSumPrice = applicableTaxItemPrice(taxInfoCalculationList,
                            itemList);

                        // 外税8%对象值
                        itemSotoTaxEightSumPrice[0] = itemSotoTaxEightSumPrice[0].add(itemSumPrice);
                        // 外税計算
                        taxValue = itemSumPrice.multiply(tax);

                        // 端数処理
                        taxValue = taxRoundType(taxValue,
                            taxInfoCalculationList.get(0).getTaxRoundType());

                        // 外税8%值(税値)
                        sotoTaxEightPriceSum[0] = sotoTaxEightPriceSum[0].add(taxValue);
                    }
                } else if (Objects.equals(CodeConstants.TaxType.UCHI_RATE.getCode(),
                    taxInfoCalculationList.get(0).getTaxCode())) {
                    // 内税(税値)処理

                    // 適用税率決定
                    tax = this.taxReliefApplyTyp(taxInfoCalculationList, inputDto);

                    // 内税計算
                    taxValue = applicableTaxItemPrice(taxInfoCalculationList, itemList);

                    // 端数処理
                    taxValue = taxRoundType(taxValue,
                        taxInfoCalculationList.get(0).getTaxRoundType());

                    // 商品外税金額合計取得
                    taxValue = taxValue
                        .subtract(taxValue
                            .divide(tax.add(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP));

                    utiTaxPriceSum[0] = utiTaxPriceSum[0].add(taxValue);
                    // 内税10%对象值（商品総価）
                    tax = BigDecimal.ZERO;
                    taxValue = BigDecimal.ZERO;
                    tax = this.taxReliefApplyTypTaxTen(taxInfoCalculationList, inputDto);
                    if (tax.compareTo(BigDecimal.ZERO) > 0) {

                        // 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得する
                        BigDecimal itemSumPrice = applicableTaxItemPrice(taxInfoCalculationList,
                            itemList);

                        // 内税10%对象值
                        itemUtiTaxTenSumPrice[0] = itemUtiTaxTenSumPrice[0].add(itemSumPrice);
                        // 内税計算
                        taxValue = itemSumPrice
                            .subtract(itemSumPrice
                                .divide(tax.add(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP));

                        // 端数処理
                        taxValue = taxRoundType(taxValue,
                            taxInfoCalculationList.get(0).getTaxRoundType());

                        // 内税10%值(税値)
                        utiTaxTenPriceSum[0] = utiTaxTenPriceSum[0].add(taxValue);
                    }

                    // 内税8%对象值（商品総価）
                    tax = BigDecimal.ZERO;
                    taxValue = BigDecimal.ZERO;
                    tax = this.taxReliefApplyTypTaxEight(taxInfoCalculationList, inputDto);
                    if (tax.compareTo(BigDecimal.ZERO) > 0) {

                        // 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得する
                        BigDecimal itemSumPrice = applicableTaxItemPrice(taxInfoCalculationList,
                            itemList);

                        // 内税8%对象值
                        itemUtiTaxEightSumPrice[0] = itemUtiTaxEightSumPrice[0].add(itemSumPrice);
                        // 外税計算
                        taxValue = itemSumPrice
                            .subtract(itemSumPrice
                                .divide(tax.add(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP));

                        // 端数処理
                        taxValue = taxRoundType(taxValue,
                            taxInfoCalculationList.get(0).getTaxRoundType());

                        // 外税8%值(税値)
                        utiTaxEightPriceSum[0] = utiTaxEightPriceSum[0].add(taxValue);
                    }
                }
            }
        });

        // インプット情報をDTOにセットする
        GetTaxValueOutputDto taxValueOutputDto = new GetTaxValueOutputDto();

        // 外税8%值
        taxValueOutputDto
            .setSotoTaxEight(sotoTaxEightPriceSum[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 外税8%值
        taxValueOutputDto.setSotoTaxTen(sotoTaxTenPriceSum[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 内税8%值
        taxValueOutputDto.setUtiTaxEight(utiTaxEightPriceSum[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 内税8%值
        taxValueOutputDto.setUtiTaxTen(utiTaxTenPriceSum[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 消费税
        taxValueOutputDto
            .setTax(sotoTaxPriceSum[0].add(utiTaxPriceSum[0]).setScale(0, BigDecimal.ROUND_DOWN));
        // 外税8%对象值
        taxValueOutputDto
            .setSotoTaxEightSum(itemSotoTaxEightSumPrice[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 外税10%对象值
        taxValueOutputDto
            .setSotoTaxTenSum(itemSotoTaxTenSumPrice[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 内税8%对象值
        taxValueOutputDto
            .setUtiTaxEightSum(itemUtiTaxEightSumPrice[0].setScale(0, BigDecimal.ROUND_DOWN));
        // 内税10%对象值
        taxValueOutputDto
            .setUtiTaxTensum(itemUtiTaxTenSumPrice[0].setScale(0, BigDecimal.ROUND_DOWN));
        return taxValueOutputDto;
    }

    /**
     * 商品オプション類型情報取得.
     *
     * @param inputDto 検索条件
     * @return 商品オプション類型情報
     */
    @Override
    public GetItemOptionInfoOutputDto getItemOptionInfo(GetItemOptionInfoInputDto inputDto) {

        // 商品オプション類型情報取得
        List<Map<String, Object>> optionLists = optionTypeRepository.findOptionTypeByItemId(
            inputDto.getStoreId(), inputDto.getItemId(), inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(optionLists)) {
            GetItemOptionInfoOutputDto outDto = new GetItemOptionInfoOutputDto();
            outDto.setOptionTypeList(new ArrayList<>());
            return outDto;
        }

        // 商品オプション類型情報を編集する
        List<ItemOptionDataDto> itemOptionDataList = new ArrayList<>();
        optionLists.forEach(stringObjectMap -> itemOptionDataList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), ItemOptionDataDto.class)));

        // 商品オプション類型Codeをグルーピングする
        Map<String, List<ItemOptionDataDto>> grpByOptionTypeCd = itemOptionDataList.stream()
            .collect(
                Collectors.groupingBy(ItemOptionDataDto::getOptionTypeCd));

        // 商品オプションリストを編集する
        List<ItemOptionTypeDto> optionTypeList = new ArrayList<>();
        grpByOptionTypeCd.forEach((s, itemOptionDataDtos) -> {
            ItemOptionTypeDto itemOptionTypeDto = beanMapper
                .map(itemOptionDataDtos.get(0), ItemOptionTypeDto.class);
            itemOptionTypeDto.setSortOrder(itemOptionDataDtos.get(0).getOptionTypeSortOrder());
            List<ItemOptionDto> optionList = new ArrayList<>();
            itemOptionDataDtos.forEach(itemOptionDataDto -> {
                ItemOptionDto option = beanMapper.map(itemOptionDataDto, ItemOptionDto.class);
                option.setSortOrder(itemOptionDataDto.getOptionSortOrder());
                optionList.add(option);
            });
            itemOptionTypeDto.setOptionList(optionList);
            optionTypeList.add(itemOptionTypeDto);
        });

        // 結果DTO初期化
        GetItemOptionInfoOutputDto outDto = new GetItemOptionInfoOutputDto();

        // 商品オプション類型を設定する
        outDto.setOptionTypeList(
            optionTypeList.stream().sorted(Comparator.comparing(ItemOptionTypeDto::getOptionTypeCd))
                .collect(Collectors.toList()));

        return outDto;
    }

    /**
     * シーケンスNo取得.
     *
     * @param inputDto 検索条件
     * @return シーケンスNo
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GetSeqNoOutputDto getSeqNo(GetSeqNoInputDto inputDto) {

        // シーケンスNo取得
        Integer seqNo = sequenceRepository
            .getSeqNo(inputDto.getStoreId(), inputDto.getTableName(), inputDto.getItem(), 1,
                Flag.OFF.getCode(), inputDto.getOperCd(), ZonedDateTime.now(),
                inputDto.getOperCd(), ZonedDateTime.now(), 0);

        // 結果DTO初期化
        GetSeqNoOutputDto outDto = new GetSeqNoOutputDto();
        outDto.setSeqNo(seqNo);

        return outDto;
    }

    /**
     * 受付ID取得.
     *
     * @param storeId     店舗ID
     * @param nowDateTime 受付時間
     * @return 受付ID
     */
    @Override
    public String getReceivablesId(String storeId, ZonedDateTime nowDateTime) {
        // 受付ID ⇒ ビジネスID4桁 + 店舗ID4桁 + 時間10桁 + Random2桁　固定長20桁
        Random ran2 = new Random();
        return StringUtils.rightPad(String.format("%s%s%02d",
            StringUtils.leftPad(StringUtils.substring(storeId, 0, 6), 6, "0"),
            nowDateTime.toEpochSecond(), ran2.nextInt(10000)), 20, "0");
    }

    /**
     * ファイルアップロード.
     *
     * @param storeId 店舗ID
     * @param useType 利用区分
     * @param file    ファイル
     * @return ファイルパス
     */
    @Override
    public UploadFileDto uploadFiles(String storeId, String useType, MultipartFile file) {

        // ファイルが空の場合は異常終了
        if (file.isEmpty()) {
            // 異常終了時の処理
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.057", (Object) null));
        }

        // ファイルパス
        String rootPath = env.getProperty("qr.env.nginx.root.path");
        // アップロードパス
        String uploadPath =
            env.getProperty("qr.env.nginx.images.path") + storeId + File.separator + useType
                + File.separator + DateUtil
                .getNowDateTimeString(CommonConstants.DATE_FORMAT_DATE_SHORT);
        createDirectory(rootPath + uploadPath);

        // ファイル種類
        String fileType = "jpg";
        if (Objects.equals("video", useType)) {
            fileType = "mp4";
        }
        if (StringUtils.isNotEmpty(file.getOriginalFilename())) {
            int dot = file.getOriginalFilename().lastIndexOf(".");
            if (0 < dot) {
                fileType = file.getOriginalFilename().substring(dot).toLowerCase();
            }
        }

        // ファイル名
        String fileName = DateUtil.getNowDateTimeString(CommonConstants.DATE_FORMAT_DATE_TIMESTAMP);

        // アップロードファイル
        Path uploadFile = Paths.get(rootPath + uploadPath + File.separator + fileName + fileType);

        // ファイルをアップロードする
        try (OutputStream os = Files.newOutputStream(uploadFile, StandardOpenOption.CREATE)) {
            byte[] bytes = file.getBytes();
            os.write(bytes);
        } catch (IOException ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.057", (Object) null), ex);
        }

        String filePath = env.getProperty("qr.env.nginx.service.url") + "/" + uploadPath
            .replace(File.separator, "/") + "/" + fileName + fileType;

        String fileSmallPath = filePath;

        if (!Objects.equals("video", useType)) {
            if (imageUtilJnb.zoomFile(rootPath + uploadPath + File.separator + fileName + fileType,
                rootPath + uploadPath + File.separator + fileName + "Small" + fileType, 0.2)) {
                // 圧縮ファイルパス
                fileSmallPath = env.getProperty("qr.env.nginx.service.url") + "/" + uploadPath
                    .replace(File.separator, "/") + "/" + fileName + "Small" + fileType;
            }
        }

        return new UploadFileDto(filePath, fileSmallPath);
    }

    /**
     * フォルダ作成.
     *
     * @param directoryPath パス
     */
    private void createDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                // 異常終了時の処理
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.057", (Object) null), e);
            }
        }
    }

    /**
     * 適用税率決定.
     *
     * @param taxInfoCalculationList 税情報
     * @param inputDto               検索条件
     * @return 適用税率
     */
    private BigDecimal taxReliefApplyTyp(List<TaxInfoDto> taxInfoCalculationList,
        GetTaxValueInputDto inputDto) {

        // 税率
        BigDecimal tax = new BigDecimal(0);

        // 商品外税情報取得処理取得した軽減税率適用区分が「0：常時非適用」の場合
        if (Objects.equals(CodeConstants.TaxReliefApplyType.NOT_ALWAYS_APPLIED.getCode(),
            taxInfoCalculationList.get(0).getTaxReliefApplyType())) {

            // 標準税率/10000
            tax =
                Objects.isNull(taxInfoCalculationList.get(0).getTaxRateNormal()) ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateNormal()
                        .divide(new BigDecimal(10000));

            // 商品外税情報取得処理取得した軽減税率適用区分が「1：常時適用」の場合
        } else if (Objects.equals(CodeConstants.TaxReliefApplyType.ALWAYS_APPLIED.getCode(),
            taxInfoCalculationList.get(0).getTaxReliefApplyType())) {

            // 軽減税率/10000
            tax =
                Objects.isNull(taxInfoCalculationList.get(0).getTaxRateRelief()) ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateRelief()
                        .divide(new BigDecimal(10000));

            // 商品外税情報取得処理取得した軽減税率適用区分が「2：切替適用」の場合
        } else {
            // 入力項目．テイクアウトフラグ＝「1:テイクアウト」の場合、
            if (Objects
                .equals(inputDto.getTakeoutFlag(), CodeConstants.TakeoutFlag.TAKE_OUT.getCode())) {
                // 軽減税率/10000
                tax = Objects.isNull(taxInfoCalculationList.get(0).getTaxRateRelief())
                    ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateRelief()
                        .divide(new BigDecimal(10000));
            } else {

                // 標準税率/10000
                tax = Objects.isNull(taxInfoCalculationList.get(0).getTaxRateNormal())
                    ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateNormal()
                        .divide(new BigDecimal(10000));
            }
        }

        return tax;
    }

    /**
     * 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得.
     *
     * @param taxInfoCalculationList 税情報
     * @param itemList               商品リスト
     * @return 金額
     */
    private BigDecimal applicableTaxItemPrice(List<TaxInfoDto> taxInfoCalculationList,
        List<ItemsDto> itemList) {

        // 税率
        BigDecimal itemSumPrice = BigDecimal.ZERO;

        // 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得する
        for (TaxInfoDto it : taxInfoCalculationList) {
            List<ItemsDto> collect = itemList.stream()
                .filter(s -> s.getItemId().compareTo(it.getItemId().toString()) == 0)
                .collect(Collectors.toList());
            // ロジック上に、必ず一つレコードがある。
            itemSumPrice = itemSumPrice.add(collect.get(0).getItemPrice());
        }

        return itemSumPrice;
    }

    /**
     * 該当税コードのグループで、商品コードは入力項目整形後に存在するレコードの金額取得.
     *
     * @param tempSotoTax  税金額
     * @param taxRoundType 税端数処理区分
     * @return 金額
     */
    private BigDecimal taxRoundType(BigDecimal tempSotoTax, String taxRoundType) {

        // 税率
        BigDecimal itemSumPrice = BigDecimal.ZERO;

        // 商品外税情報取得処理取得した軽減税率適用区分が「1：四捨五入」の場合
        if (Objects.equals(CodeConstants.TaxRoundType.ROUNDING.getCode(), taxRoundType)) {

            tempSotoTax = new BigDecimal(Math.round(tempSotoTax.floatValue()));

            // 商品外税情報取得処理取得した軽減税率適用区分が「2：切り上げ 」の場合
        } else if (Objects.equals(CodeConstants.TaxRoundType.ROUND_UP.getCode(), taxRoundType)) {

            tempSotoTax = new BigDecimal(Math.ceil(tempSotoTax.floatValue()));

            // 商品外税情報取得処理取得した軽減税率適用区分が「 3：切り捨て」の場合
        } else {

            tempSotoTax = new BigDecimal(Math.floor(tempSotoTax.floatValue()));
        }
        return tempSotoTax;
    }

    /**
     * 適用税率決定(10%).
     *
     * @param taxInfoCalculationList 税情報
     * @param inputDto               検索条件
     * @return 金額
     */
    private BigDecimal taxReliefApplyTypTaxTen(List<TaxInfoDto> taxInfoCalculationList,
        GetTaxValueInputDto inputDto) {

        // 税率
        BigDecimal tax = BigDecimal.ZERO;
        // 商品外税情報取得処理取得した軽減税率適用区分が「0：常時非適用」の場合
        if (Objects.equals(CodeConstants.TaxReliefApplyType.NOT_ALWAYS_APPLIED.getCode(),
            taxInfoCalculationList.get(0).getTaxReliefApplyType())) {

            // 標準税率/10000
            tax =
                Objects.isNull(taxInfoCalculationList.get(0).getTaxRateNormal()) ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateNormal()
                        .divide(new BigDecimal(10000));
            // 商品外税情報取得処理取得した軽減税率適用区分が「1：常時適用」の場合
        } else if (Objects.equals(CodeConstants.TaxReliefApplyType.ALWAYS_APPLIED.getCode(),
            taxInfoCalculationList.get(0).getTaxReliefApplyType())) {

            // 商品外税情報取得処理取得した軽減税率適用区分が「2：切替適用」の場合
        } else {
            // 入力項目．テイクアウトフラグ＝「1:テイクアウト」の場合、
            if (!Objects
                .equals(inputDto.getTakeoutFlag(), CodeConstants.TakeoutFlag.TAKE_OUT.getCode())) {
                // 標準税率/10000
                tax = Objects.isNull(taxInfoCalculationList.get(0).getTaxRateNormal())
                    ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateNormal()
                        .divide(new BigDecimal(10000));
            }
        }

        return tax;
    }

    /**
     * 適用税率決定(8%).
     *
     * @param taxInfoCalculationList 税情報
     * @param inputDto               検索条件
     * @return 金額
     */
    private BigDecimal taxReliefApplyTypTaxEight(List<TaxInfoDto> taxInfoCalculationList,
        GetTaxValueInputDto inputDto) {

        // 税率
        BigDecimal tax = BigDecimal.ZERO;
        // 商品外税情報取得処理取得した軽減税率適用区分が「0：常時非適用」の場合
        if (Objects.equals(CodeConstants.TaxReliefApplyType.NOT_ALWAYS_APPLIED.getCode(),
            taxInfoCalculationList.get(0).getTaxReliefApplyType())) {

            // 商品外税情報取得処理取得した軽減税率適用区分が「1：常時適用」の場合
        } else if (Objects.equals(CodeConstants.TaxReliefApplyType.ALWAYS_APPLIED.getCode(),
            taxInfoCalculationList.get(0).getTaxReliefApplyType())) {

            // 軽減税率/10000
            tax =
                Objects.isNull(taxInfoCalculationList.get(0).getTaxRateRelief()) ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateRelief()
                        .divide(new BigDecimal(10000));

            // 商品外税情報取得処理取得した軽減税率適用区分が「2：切替適用」の場合
        } else {
            // 入力項目．テイクアウトフラグ＝「1:テイクアウト」の場合、
            if (Objects
                .equals(inputDto.getTakeoutFlag(), CodeConstants.TakeoutFlag.TAKE_OUT.getCode())) {
                // 軽減税率/10000
                tax = Objects.isNull(taxInfoCalculationList.get(0).getTaxRateRelief())
                    ? BigDecimal.ZERO
                    : taxInfoCalculationList.get(0).getTaxRateRelief()
                        .divide(new BigDecimal(10000));
            }
        }
        return tax;
    }


    /**
     * 税額登録.
     *
     * @param inputDto 検索条件
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registTaxAmount(TaxAmountInputDto inputDto) {

        // オーダー情報取得
        List<Map<String, Object>> orderItemList = new ArrayList<>();

        // 出前の場合
        if (inputDto.getDeliveryFlag().equals(1)) {
            orderItemList = deliveryOrderSummaryRepository
                .getDeliveryOrderItem(inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                    inputDto.getOrderId());
        } else {
            //出前以外の場合
            orderItemList = orderSummaryRepository
                .getOrderItem(inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                    Objects.isNull(inputDto.getOrderId()) ? -1 : inputDto.getOrderId());
        }

        //現金と電子支払の場合
        if ((inputDto.getTaxAmountType().equals(0)
            || inputDto.getTaxAmountType().equals(1))) {
            if (orderItemList.size() == 0 && Objects.isNull(inputDto.getItemList())) {
                return;
            }
            List<ItemsDto> itemTaxAmountDtoList = new ArrayList<>();
            orderItemList.forEach(stringObjectMap -> itemTaxAmountDtoList.add(
                JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                    ItemsDto.class)));
            GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
            taxValueInputDto.setTakeoutFlag(orderItemList.size() == 0 ? inputDto.getTakeoutFlag()
                : orderItemList.get(0).get("takeoutFlag").toString());
            taxValueInputDto.setStoreId(inputDto.getStoreId());
            taxValueInputDto.setItemList(
                orderItemList.size() == 0 ? inputDto.getItemList() : itemTaxAmountDtoList);
            // 税計算
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_tax_amount"); // テーブル名
            getSeqNoInputDto.setItem("tax_amount_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_STORE_PC); // 登録更新者
            GetSeqNoOutputDto seqNoOutputDto = getSeqNo(getSeqNoInputDto);

            GetTaxValueOutputDto taxValueOutputDto = this.getTaxValue(taxValueInputDto);
            OTaxAmount taxAmount = new OTaxAmount();
            taxAmount.setTaxAmountId(seqNoOutputDto.getSeqNo());
            taxAmount.setStoreId(inputDto.getStoreId());
            taxAmount.setOrderSummaryId(inputDto.getOrderSummaryId());
            taxAmount.setOrderId(inputDto.getOrderId());
            taxAmount.setConsumptionAmount(taxValueOutputDto.getTax());
            taxAmount.setForeignTax(
                taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));
            taxAmount.setForeignNormalAmount(taxValueOutputDto.getSotoTaxTen());
            taxAmount.setForeignNormalObjectAmount(taxValueOutputDto.getSotoTaxTenSum());
            taxAmount.setForeignReliefAmount(taxValueOutputDto.getSotoTaxEight());
            taxAmount.setForeignReliefObjectAmount(taxValueOutputDto.getSotoTaxEightSum());
            taxAmount.setIncludedTax(
                taxValueOutputDto.getUtiTaxTen().add(taxValueOutputDto.getUtiTaxTen()));
            taxAmount.setIncludedNormalAmount(taxValueOutputDto.getUtiTaxTen());
            taxAmount.setIncludedNormalObjectAmount(taxValueOutputDto.getUtiTaxTensum());
            taxAmount.setIncludedReliefAmount(taxValueOutputDto.getUtiTaxEight());
            taxAmount.setIncludedReliefObjectAmount(taxValueOutputDto.getUtiTaxEightSum());
            // 現金の場合、削除フラグが０に設定
            if (inputDto.getTaxAmountType().equals(0)) {
                taxAmount.setDelFlag(Flag.OFF.getCode());
            } else {
                //電子で支払の場合、削除フラグが１に設定
                taxAmount.setDelFlag(Flag.ON.getCode());
            }
            taxAmount.setInsOperCd(inputDto.getInsUpdOperCd());
            ZonedDateTime nowDate = ZonedDateTime.now();
            taxAmount.setInsDateTime(nowDate);
            taxAmount.setUpdOperCd(inputDto.getInsUpdOperCd());
            taxAmount.setUpdDateTime(nowDate);
            taxAmount.setVersion(0);
            taxAmountRepository.save(taxAmount);
        }

        //電子支払完了の場合
        if (inputDto.getTaxAmountType().equals(2)) {
            taxAmountRepository
                .updateDelFlagByOrderId(inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                    Objects.isNull(inputDto.getOrderId()) ? -1 : inputDto.getOrderId(),
                    inputDto.getInsUpdOperCd(), ZonedDateTime.now());
        }

        //退款・再会計の場合
        if (inputDto.getTaxAmountType().equals(3)) {
            taxAmountRepository
                .deleteDelFlagByOrderId(inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                    Objects.isNull(inputDto.getOrderId()) ? -1 : inputDto.getOrderId(),
                    inputDto.getInsUpdOperCd(), ZonedDateTime.now());
        }

    }

    /**
     * 税額存在チェック.
     *
     * @param inputDto 検索条件
     */
    @Override
    public Integer getTaxAmountCount(TaxAmountInputDto inputDto) {
        List<OTaxAmount> taxAmountList = taxAmountRepository
            .findByStoreIdAndDelFlagAndOrderSummaryId(inputDto.getStoreId(), Flag.OFF.getCode(),
                inputDto.getOrderSummaryId());
        return taxAmountList.size();
    }

    /**
     * 店舗営業開始終了時間取得
     */
    public StoreOpenColseTimeDto getStoreOpenColseTime(String storeId) {
        // 店舗情報取得
        MStore storeDto = storeRepository
            .findByStoreIdAndDelFlag(storeId, Flag.OFF.getCode());
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // 現在日付
        String nowDate = DateUtil
            .getZonedDateString(nowDateTime, CommonConstants.DATE_FORMAT_DATE);
        ZonedDateTime startTime = DateUtil.getNowDateTime();
        ZonedDateTime endTime = DateUtil.getNowDateTime();
        Comparator<ZonedDateTime> comparator = Comparator.comparing(
            zdt -> zdt.truncatedTo(ChronoUnit.MINUTES));
        //一日以内の場合
        if ((Integer.valueOf(storeDto.getStartTime().split(":")[0]) <= Integer
            .valueOf(storeDto.getEndTime().split(":")[0]) && Integer
            .valueOf(storeDto.getEndTime().split(":")[0]) <= 24) || Integer
            .valueOf(storeDto.getEndTime().split(":")[0]).equals(0)) {
            // 営業開始時間
            startTime = DateUtil
                .getZonedDateByString(nowDate + " " + storeDto.getStartTime() + ":00",
                    CommonConstants.DATE_FORMAT_DATETIME);
            // 0時判断
            String endTimeTemp = storeDto.getEndTime();
            if (Integer
                .valueOf(storeDto.getEndTime().split(":")[0]).equals(0)) {
                endTimeTemp = "23:59";
            }
            // 営業終了時間
            endTime = DateUtil
                .getZonedDateByString(nowDate + " " + endTimeTemp + ":00",
                    CommonConstants.DATE_FORMAT_DATETIME);


        } else {
            //一日以外の場合
            startTime = DateUtil
                .getZonedDateByString(nowDate + " " + storeDto.getStartTime() + ":00",
                    CommonConstants.DATE_FORMAT_DATETIME);
            ZonedDateTime zeroTime = DateUtil
                .getZonedDateByString(nowDate + " " + "23:59:00",
                    CommonConstants.DATE_FORMAT_DATETIME);

            // ０点過ぎの場合
            if (comparator.compare(zeroTime, nowDateTime) == -1) {
                endTime = DateUtil
                    .getZonedDateByString(nowDate + " " + storeDto.getEndTime() + ":00",
                        CommonConstants.DATE_FORMAT_DATETIME);
                startTime = startTime.plusDays(-1);
            } else {
                // ０点前の場合
                endTime = DateUtil
                    .getZonedDateByString(nowDate + " " + storeDto.getEndTime() + ":00",
                        CommonConstants.DATE_FORMAT_DATETIME).plusDays(1);
            }
        }
        // 営業開始終了時間設定する
        StoreOpenColseTimeDto storeOpenColseTimeDto = new StoreOpenColseTimeDto();
        storeOpenColseTimeDto.setStartTime(startTime);
        storeOpenColseTimeDto.setEndTime(endTime);
        return storeOpenColseTimeDto;
    }

    /**
     * ライセンス許可取得.
     *
     * @param licenseList 全部のライセンスリスト
     */
    @Override
    public String getLicenseCode(List<MLicense> licenseList, String licenseType) {
        // フラグ取得
        List<MLicense> licenseTypeList = licenseList.stream().filter(mLicense -> Objects
            .equals(mLicense.getLicenseType(), licenseType)).collect(
            Collectors.toList());
        if (licenseTypeList.size() > 0) {
            return licenseTypeList.get(0).getLicenseCode();
        }
        return StringUtils.EMPTY;
    }
}
