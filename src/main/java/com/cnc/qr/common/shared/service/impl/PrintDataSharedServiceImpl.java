package com.cnc.qr.common.shared.service.impl;

import static com.cnc.qr.common.constants.CommonConstants.CODE_GROUP_PAYMENT;
import static com.cnc.qr.common.constants.CommonConstants.CODE_GROUP_PRINT_ACCOUNT;
import static com.cnc.qr.common.constants.CommonConstants.CODE_GROUP_PRINT_CUSTOMER;
import static com.cnc.qr.common.constants.CommonConstants.CODE_GROUP_PRINT_KITCHEN;
import static com.cnc.qr.common.constants.CommonConstants.CODE_GROUP_PRINT_QRCODE;
import static com.cnc.qr.common.constants.CommonConstants.CUSTOMER_PRINT;
import static com.cnc.qr.common.constants.CommonConstants.KITCHENLIST_PRINT;
import static com.cnc.qr.common.constants.CommonConstants.MONEY_CHARACTER;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.AccountsType;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemClassification;
import com.cnc.qr.common.constants.CodeConstants.MstItemType;
import com.cnc.qr.common.constants.CodeConstants.OrderType;
import com.cnc.qr.common.constants.CodeConstants.PayStatus;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.PrintStatus;
import com.cnc.qr.common.constants.CodeConstants.SettleType;
import com.cnc.qr.common.constants.CodeConstants.TakeoutFlag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MCode;
import com.cnc.qr.common.entity.MControl;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.MTable;
import com.cnc.qr.common.entity.OOrderDetails;
import com.cnc.qr.common.entity.OPrintQueue;
import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.common.entity.OTaxAmount;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MControlRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MReceiptRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.MTableRepository;
import com.cnc.qr.common.repository.ODeliveryOrderSummaryRepository;
import com.cnc.qr.common.repository.OInspectionSettleRepository;
import com.cnc.qr.common.repository.OOrderDetailsRepository;
import com.cnc.qr.common.repository.OOrderRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OPrintQueueRepository;
import com.cnc.qr.common.repository.OReceivablesRepository;
import com.cnc.qr.common.repository.OTaxAmountRepository;
import com.cnc.qr.common.repository.PPaymentDetailRepository;
import com.cnc.qr.common.repository.PPaymentRepository;
import com.cnc.qr.common.repository.PSbResultDetailRepository;
import com.cnc.qr.common.repository.RBuffetItemRepository;
import com.cnc.qr.common.shared.model.CustomerOrderDto;
import com.cnc.qr.common.shared.model.CustomerOrderInfoDto;
import com.cnc.qr.common.shared.model.CustomerOrderLabelDto;
import com.cnc.qr.common.shared.model.DeliveryPersonalDto;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.InspectionSettleDto;
import com.cnc.qr.common.shared.model.InspectionSettleInfoDto;
import com.cnc.qr.common.shared.model.InspectionSettleLabelDto;
import com.cnc.qr.common.shared.model.ItemOptionDto;
import com.cnc.qr.common.shared.model.KitchenDetailsDto;
import com.cnc.qr.common.shared.model.KitchenItemDto;
import com.cnc.qr.common.shared.model.KitchenListDto;
import com.cnc.qr.common.shared.model.KitchenOutputDto;
import com.cnc.qr.common.shared.model.KitchenSlipDto;
import com.cnc.qr.common.shared.model.KitchenSlipLabelDto;
import com.cnc.qr.common.shared.model.NormallyItemDto;
import com.cnc.qr.common.shared.model.OptionDetailDto;
import com.cnc.qr.common.shared.model.OrderAccountDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.OrderAccountLabelDto;
import com.cnc.qr.common.shared.model.OrderTaxPaymentDto;
import com.cnc.qr.common.shared.model.PrintDto;
import com.cnc.qr.common.shared.model.QrCodeInputDto;
import com.cnc.qr.common.shared.model.QrCodeOutputDto;
import com.cnc.qr.common.shared.model.ReceiptInfoDto;
import com.cnc.qr.common.shared.model.ReturnItemDto;
import com.cnc.qr.common.shared.model.RgDailyReportDto;
import com.cnc.qr.common.shared.model.RgDailyReportInputDto;
import com.cnc.qr.common.shared.model.RgOrderSummaryDto;
import com.cnc.qr.common.shared.model.SlipDeliveryDto;
import com.cnc.qr.common.shared.model.SlipDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.model.SlipPrintDto;
import com.cnc.qr.common.shared.model.StoreOpenColseTimeDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.order.model.ChangePrintStatusDto;
import com.cnc.qr.core.order.model.ChangePrintStatusInputDto;
import com.cnc.qr.core.order.model.GetPrintOrderListInputDto;
import com.cnc.qr.core.order.model.ItemDto;
import com.cnc.qr.core.order.model.ItemsDto;
import com.cnc.qr.core.order.model.QrCodeDeliveryInputDto;
import com.github.dozermapper.core.Mapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * プリンター伝票サービス実装クラス.
 */
@Service
public class PrintDataSharedServiceImpl implements PrintDataSharedService {

    Logger log = LoggerFactory.getLogger(PrintDataSharedServiceImpl.class);
    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * コントロールマスタリポジトリ.
     */
    @Autowired
    private MControlRepository controlRepository;


    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * コードマスタタリポジトリ.
     */
    @Autowired
    private MCodeRepository mcodeRepository;

    /**
     * 注文明細テーブルリポジトリ.
     */
    @Autowired
    private OOrderDetailsRepository orderDetailsRepository;

    /**
     * 共通部品.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * 共通部品（印刷）.
     */
    @Autowired
    private PrintDataSharedService printDataSharedService;

    /**
     * 注文サマリテーブルリポジトリ.
     */
    @Autowired
    private OOrderSummaryRepository orderSummaryRepository;

    /**
     * 税額テーブルリポジトリ.
     */
    @Autowired
    private OTaxAmountRepository taxAmountRepository;


    /**
     * 点検精算テーブルリポジトリ.
     */
    @Autowired
    private OInspectionSettleRepository inspectionSettleRepository;


    /**
     * 出前注文サマリテーブルリポジトリ.
     */
    @Autowired
    private ODeliveryOrderSummaryRepository deliveryOrderSummaryRepository;

    /**
     * 注文テーブルリポジトリ.
     */
    @Autowired
    private OOrderRepository orderRepository;
    /**
     * 伝票マスタリポジトリ.
     */
    @Autowired
    private MReceiptRepository receiptRepository;

    /**
     * 支払リポジトリ.
     */
    @Autowired
    private PPaymentDetailRepository ppaymentDetailRepository;

    /**
     * 受付テーブルリポジトリ.
     */
    @Autowired
    private OReceivablesRepository receivablesRepository;

    /**
     * テーブルリポジトリ.
     */
    @Autowired
    private MTableRepository tableRepository;


    /**
     * 支払明細テーブルリポジトリ.
     */
    @Autowired
    private PPaymentDetailRepository paymentDetailRepository;

    /**
     * 支払明細テーブルリポジトリ.
     */
    @Autowired
    private PSbResultDetailRepository sbResultDetailRepository;

    /**
     * 放題商品選択テーブルリポジトリ.
     */
    @Autowired
    RBuffetItemRepository buffetItemRepository;

    /**
     * 共通部品プリンター.
     */
    @Autowired
    OPrintQueueRepository printQueueRepository;
    /**
     * 支払テーブルリポジトリ.
     */
    @Autowired
    PPaymentRepository paymentRepository;

    /**
     * 環境変数.
     */
    @Autowired
    private Environment env;


    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * QRコード印刷情報取得.
     *
     * @param inputDto 検索条件
     * @return QRコード印刷情報
     */
    @Override
    public String getQrCodePrintData(QrCodeInputDto inputDto) {

        Properties properties = languageProperties(inputDto.getStoreId(),
            CommonConstants.QR_PRINT);

        //urn QRコード印刷
        QrCodeOutputDto outputDto = new QrCodeOutputDto();
        //QRコードURL
        String url = env.getProperty("qr.env.qrCodeUrl") + "receivablesId=" + Md5Util.aesEncrypt(
            inputDto.getReceivablesId(), env.getProperty("qr.env.secretKey")) + "&storeId="
            + Md5Util.aesEncrypt(inputDto.getStoreId(), env.getProperty("qr.env.secretKey"));

        outputDto.setQrCodeUrl(url);

        // テーブル名
        if (!Objects.isNull(inputDto.getTableId())) {
            outputDto.setTableNameLabel(properties.getProperty("tableNameLabel"));
            MTable table = tableRepository
                .findByStoreIdAndTableIdAndDelFlag(inputDto.getStoreId(), inputDto.getTableId(),
                    Flag.OFF.getCode());
            outputDto.setTableName(table.getTableName());
        }

        // プリンター情報を取得する
        PrintDto printDto = getPrintInfo(inputDto.getStoreId(), CommonConstants.QR_PRINT);
        //ブルートゥース名称
        outputDto.setBluetoothName(printDto.getBluetoothName());
        //プリンターIP
        outputDto.setPrintIp(printDto.getPrintIp());
        // 接続名称
        outputDto.setConnectionMethod(printDto.getConnectionMethodCode());
        //プリンタブランドCD
        outputDto.setBrandCode(printDto.getBrandCode());
        //伝票幅CD
        outputDto.setPrintSize(printDto.getPrintSize());
        //受付番号ラベル
        outputDto.setReceptionNoLabel(properties.getProperty("receptionNoLabel"));
        //受付番号
        outputDto
            .setReceptionNo(
                getReceivablesNo(inputDto.getStoreId(), inputDto.getReceivablesId()));
        //受付時間ラベル
        outputDto.setReceptionDateTimeLabel(properties.getProperty("receptionDateTimeLabel"));
        //受付時間
        outputDto.setReceptionDateTime(DateUtil
            .getZonedDateString(ZonedDateTime.now(), CommonConstants.DATE_FORMAT_DATETIME));
        //担当ラベル
        outputDto.setStaffNameLabel(properties.getProperty("staffNameLabel"));
        //担当
        outputDto.setStaffName(inputDto.getUserName());
        //メッセージ
        outputDto.setMessage(properties.getProperty("messageLabel"));
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        slipPrintDto.setQrCodeDto(outputDto);
        return JSON.toJSONString(slipPrintDto);

    }

    /**
     * 出前QRコード印刷情報取得.
     *
     * @param inputDto 検索条件
     * @return 出前QRコード印刷情報
     */
    @Override
    public String getQrCodeDeliveryPrintData(QrCodeDeliveryInputDto inputDto) {

        Properties properties = languageProperties(inputDto.getStoreId(),
            CommonConstants.QR_PRINT);

        //urn QRコード印刷
        QrCodeOutputDto outputDto = new QrCodeOutputDto();
        //QRコードURL
        String url =
            env.getProperty("qr.env.qrCodeDeliveryUrl") + "&storeId=" + inputDto.getStoreId();

        outputDto.setQrCodeUrl(url);

        // プリンター情報を取得する
        PrintDto printDto = getPrintInfo(inputDto.getStoreId(), CommonConstants.QR_PRINT);
        //ブルートゥース名称
        outputDto.setBluetoothName(printDto.getBluetoothName());
        //プリンターIP
        outputDto.setPrintIp(printDto.getPrintIp());
        // 接続名称
        outputDto.setConnectionMethod(printDto.getConnectionMethodCode());
        //プリンタブランドCD
        outputDto.setBrandCode(printDto.getBrandCode());
        //伝票幅CD
        outputDto.setPrintSize(printDto.getPrintSize());
        //担当ラベル
        outputDto.setStaffNameLabel(properties.getProperty("staffNameLabel"));
        //担当
        outputDto.setStaffName(inputDto.getUserName());
        //メッセージ
        outputDto.setMessage(properties.getProperty("messageLabel"));
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        slipPrintDto.setQrCodeDto(outputDto);
        return JSON.toJSONString(slipPrintDto);

    }

    /**
     * キッチン印刷情報取得.
     *
     * @param inputDto 検索条件
     * @return キッチン印刷情報
     */
    @Override
    public KitchenOutputDto getKitchenPrintData(SlipInputDto inputDto) {
        KitchenOutputDto kitchenOutputDto = new KitchenOutputDto();
        // 注文厨房伝票ラベル
        kitchenOutputDto
            .setKitchenSlipLabelDto(setKitchenSlipTitle(inputDto.getStoreId(), KITCHENLIST_PRINT));
        // 厨房明細伝票
        KitchenDetailsDto kitchenDetailsDto = new KitchenDetailsDto();
        List<SlipDto> slipList = new ArrayList<>();
        // 出前の場合
        if (Objects.equals(inputDto.getDeliveryFlag(), OrderType.DELIVERY.getCode())) {
            // 注文厨房伝票データ
            List<SlipDeliveryDto> slipDeliveryList = deliveryOrderSummaryRepository
                .findByDeliveryOrderSummaryIdAndByStoreId(
                    inputDto.getStoreId(), inputDto.getOrderSummaryId(), inputDto.getOrderIdList(),
                    inputDto.getOrderDetailId());
            for (SlipDeliveryDto slipDeliveryDto : slipDeliveryList) {
                slipList.add(beanMapper.map(slipDeliveryDto, SlipDto.class));
            }
        } else {
            //店内の場合
            // 注文厨房伝票データ
            slipList = orderSummaryRepository.findByOrderSummaryIdAndByStoreId(
                inputDto.getStoreId(), inputDto.getOrderSummaryId(), inputDto.getOrderIdList(),
                inputDto.getOrderDetailId());
        }

        // 放題商品ID取得
        List<Integer> buffetItemIdList = itemRepository.findBuffetItemByStoreId(
            inputDto.getStoreId(), MstItemType.BUFFET.getCode());

        // 注文厨房伝票印刷
        KitchenSlipDto kitchenSlipDto = new KitchenSlipDto();
        if (slipList.size() > 0) {

            // 食卓名称
            if (StringUtils.isNotEmpty(slipList.get(0).getTableName())) {
                kitchenSlipDto.setTableName(slipList.get(0).getTableName());
            } else {
                kitchenSlipDto.setTableName("take out");
            }
            //受付番号
            kitchenSlipDto.setReceptionNo(String.format("%04d", slipList.get(0).getReceptionNo()));
            // 注文時間
            kitchenSlipDto.setOrderTime(DateUtil
                .getZonedDateString(ZonedDateTime.now(), CommonConstants.DATE_FORMAT_DATETIME));
            //担当者
            kitchenSlipDto.setResponsibleParty(inputDto.getUserName());
            MControl controlDto = controlRepository
                .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                    CommonConstants.CODE_GROUP_PRINT_KITCHEN_DETAIL_WITH_OR_OUT,
                    Flag.OFF.getCode());
            if (Objects.equals(Flag.ON.getCode().toString(), controlDto.getControlCode())) {
                // 厨房明細伝票
                kitchenDetailsDto
                    .setItemList(getKitchenDetails(slipList, inputDto, buffetItemIdList));
            }

            // 厨房明細伝票(堂口单)
            MControl controlDtailDto = controlRepository
                .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                    CommonConstants.CODE_GROUP_PRINT_KITCHEN_LIST_WITH_OR_OUT,
                    Flag.OFF.getCode());
            kitchenSlipDto.setKitchenDetailsDto(kitchenDetailsDto);
            if (Objects.equals(Flag.ON.getCode().toString(), controlDtailDto.getControlCode())) {
                // 厨房リスト伝票(厨房总单)
                kitchenSlipDto
                    .setKitchenListDto(getKitchenList(slipList, inputDto, buffetItemIdList));
            }


        }

        // 厨房用印刷出力情報
        kitchenOutputDto.setKitchenSlipDto(kitchenSlipDto);

        return kitchenOutputDto;
    }


    /**
     * 厨房明細伝票処理.
     *
     * @param slipList プリンターデータ
     * @param inputDto 入力DTO
     * @return 厨房明細伝票印刷
     */
    private List<KitchenItemDto> getKitchenDetails(List<SlipDto> slipList, SlipInputDto inputDto,
        List<Integer> buffetItemIdList) {

        // グルーブ化
        Map<String, List<SlipDto>> itemInfoGroupMap = slipList.stream()
            .collect(Collectors.groupingBy(this::fetchItemGroupKey));
        // 商品リスト
        List<KitchenItemDto> itemList = new ArrayList<KitchenItemDto>();
        itemInfoGroupMap.forEach((key, itemAndOptionList) -> {
            KitchenItemDto kitchenItemDto = new KitchenItemDto();
            // ブルートゥース名称
            kitchenItemDto.setBluetoothName(itemAndOptionList.get(0).getBluetoothName());
            // プリンターIP
            kitchenItemDto.setPrintIp(itemAndOptionList.get(0).getPrintIp());
            // 接続方式区分
            kitchenItemDto.setConnectionMethod(itemAndOptionList.get(0).getConnectionMethodCode());
            // プリンタブランドCD
            kitchenItemDto.setBrandCode(itemAndOptionList.get(0).getBrandCode());
            // プリンタブランドCD
            kitchenItemDto.setBrandCode(itemAndOptionList.get(0).getBrandCode());
            //伝票幅CD
            kitchenItemDto.setPrintSize(itemAndOptionList.get(0).getPrintSize());
            // キチン名
            kitchenItemDto.setKitchenName(itemAndOptionList.get(0).getKitchenName());

            // 普通商品
            List<SlipDto> normallyItemList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.NORMAL.getCode())).collect(Collectors.toList());
            if (normallyItemList.size() > 0) {
                kitchenItemDto
                    .setNormallyItemDto(
                        getNormallyItemList(itemAndOptionList, buffetItemIdList, false,
                            inputDto.getStoreId(), 3));
                itemList.add(kitchenItemDto);
                return;
            }
            // 返品商品
            List<SlipDto> returnItemList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.RETURN.getCode())).collect(Collectors.toList());
            if (returnItemList.size() > 0) {
                kitchenItemDto
                    .setReturnItemDto(getReturnItemList(returnItemList, inputDto.getStoreId(), 3));
                itemList.add(kitchenItemDto);
                return;
            }


        });

        return itemList;
    }

    /**
     * 厨房リスト伝票処理.
     *
     * @param slipList プリンターデータ
     * @param inputDto 入力DTO
     * @return 厨房リスト伝票印刷
     */
    private KitchenListDto getKitchenList(List<SlipDto> slipList, SlipInputDto inputDto,
        List<Integer> buffetItemIdList) {

        KitchenListDto kitchenListDto = new KitchenListDto();

        // プリンター情報取得
        PrintDto printDto = getPrintInfo(inputDto.getStoreId(), KITCHENLIST_PRINT);
        // ブルートゥース名称
        kitchenListDto.setBluetoothName(printDto.getBluetoothName());
        // プリンターIP
        kitchenListDto.setPrintIp(printDto.getPrintIp());
        // 接続方式区分
        kitchenListDto.setConnectionMethod(printDto.getConnectionMethodCode());
        // プリンタブランドCD
        kitchenListDto.setBrandCode(printDto.getBrandCode());
        //伝票幅CD
        kitchenListDto.setPrintSize(printDto.getPrintSize());
        List<NormallyItemDto> normallyItemList = new ArrayList<>();
        List<ReturnItemDto> returnItemList = new ArrayList<>();
        // グルーブ化
        Map<String, List<SlipDto>> itemInfoGroupMap = slipList.stream()
            .collect(Collectors.groupingBy(this::fetchItemGroupKey));
        itemInfoGroupMap.forEach((key, itemAndOptionList) -> {
            // 普通商品
            List<SlipDto> normallyItemTempList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.NORMAL.getCode())).collect(Collectors.toList());
            if (normallyItemTempList.size() > 0) {
                normallyItemList.add(
                    getNormallyItemList(normallyItemTempList, buffetItemIdList, true,
                        inputDto.getStoreId(), KITCHENLIST_PRINT));
            }
            // 返品商品
            List<SlipDto> returnItemTempList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.RETURN.getCode())).collect(Collectors.toList());
            if (returnItemTempList.size() > 0) {
                returnItemList.add(getReturnItemList(returnItemTempList, inputDto.getStoreId(),
                    KITCHENLIST_PRINT));
            }

        });
        kitchenListDto.setNormallyItemList(normallyItemList);
        kitchenListDto.setReturnItemList(returnItemList);

        // 備考取得
        // グルーブ化
        Map<String, List<SlipDto>> itemOrderInfoGroupMap = slipList.stream()
            .collect(Collectors.groupingBy(this::fetchItemOrderGroupKey));
        List<String> commentList = new ArrayList<>();
        itemOrderInfoGroupMap.forEach((key, itemAndOptionList) -> {
            if (StringUtils.isNotEmpty(itemAndOptionList.get(0).getComment())) {
                commentList.add(itemAndOptionList.get(0).getComment());
            }
        });
        kitchenListDto.setCommentList(commentList);
        return kitchenListDto;
    }

    /**
     * 普通商品取得.
     *
     * @param itemAndOptionList 商品リスト
     * @param buffetItemIdList  放題リスト
     * @param buffetItemIdList  放題リスト
     * @return 普通商品
     */
    private NormallyItemDto getNormallyItemList(List<SlipDto> itemAndOptionList,
        List<Integer> buffetItemIdList, Boolean coursePrintFlag, String storeId,
        Integer receiptId) {
        // 普通商品
        NormallyItemDto normallyItemDto = new NormallyItemDto();
        String itemName = jsonToString(storeId, receiptId, itemAndOptionList.get(0).getItemName());
        if (!Objects.isNull(buffetItemIdList)) {
            List<Integer> buffetItemList = buffetItemIdList.stream()
                .filter(s -> s == itemAndOptionList.get(0).getItemId())
                .collect(Collectors.toList());
            if (buffetItemList.size() > 0) {
                itemName = itemName + "(放)";
            }
        }
        // 商品名称
        normallyItemDto.setItemName(itemName);
        // 商品個数
        normallyItemDto.setItemCount(itemAndOptionList.get(0).getItemCount().toString());

        DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
        String itemPrice = df.format(itemAndOptionList.get(0).getItemPrice());
        if (Objects.equals(CodeConstants.TaxType.SOTO_RATE.getCode(),
            itemAndOptionList.get(0).getTaxCode())) {
            // 商品価格
            normallyItemDto.setItemPrice(MONEY_CHARACTER + itemPrice + "(外)");
        } else {
            // 商品価格
            normallyItemDto
                .setItemPrice(
                    MONEY_CHARACTER + itemPrice + "    ");
        }

        //軽減税
        if (Objects.equals(CodeConstants.TaxReliefApplyType.ALWAYS_APPLIED.getCode(),
            itemAndOptionList.get(0).getTaxReliefApplyType())) {
            normallyItemDto.setTaxReliefApplyType("*");
        } else {
            normallyItemDto.setTaxReliefApplyType(" ");
        }

        // コース明細
        if (coursePrintFlag && itemAndOptionList.get(0).getItemType()
            .equals(MstItemType.COURSE.getCode())) {
            List<ItemDto> courseItemList = buffetItemRepository.getItemList(storeId,
                itemAndOptionList.get(0).getItemId());

            List<String> courseList = new ArrayList<>();
            courseItemList.forEach(courseDto -> {
                courseList.add(jsonToString(storeId, receiptId, courseDto.getItemName()));
            });
            normallyItemDto.setItemCourseList(courseList);
        }

        // 商品オプションリスト
        List<ItemOptionDto> itemOptionList = new ArrayList<ItemOptionDto>();
        if (Objects.equals(itemAndOptionList.get(0).getOptionFlag(), "1")) {
            // グループ化
            Map<String, List<SlipDto>> itemOptionGroupMap = itemAndOptionList.stream()
                .collect(Collectors.groupingBy(this::fetchOptionGroupKey));
            itemOptionGroupMap.forEach((code, optionList) -> {
                if (!Objects.isNull(optionList.get(0)) && StringUtils
                    .isNotEmpty(optionList.get(0).getOptionTypeName())) {
                    // 商品オプション
                    ItemOptionDto itemOptionDto = new ItemOptionDto();
                    // オプション見出し
                    itemOptionDto
                        .setOptionTitle(jsonToString(storeId, receiptId,
                            optionList.get(0).getOptionTypeName()));
                    // オプション区分
                    itemOptionDto.setClassification(optionList.get(0).getClassification());
                    List<OptionDetailDto> optionDetailList = new ArrayList<OptionDetailDto>();
                    for (SlipDto slipDto : optionList) {
                        OptionDetailDto optionDetailDto = new OptionDetailDto();
                        // 商品オプション数量
                        optionDetailDto.setOptionCount(slipDto.getItemOptionCount().toString());
                        // オプション名
                        optionDetailDto.setOptionName(
                            jsonToString(storeId, receiptId, slipDto.getOptionName()));
                        optionDetailList.add(optionDetailDto);
                    }
                    itemOptionDto.setOptionDetailList(optionDetailList);
                    itemOptionList.add(itemOptionDto);
                }
            });
            normallyItemDto.setItemOptionList(itemOptionList);

        }
        return normallyItemDto;
    }

    /**
     * 返品商品取得.
     *
     * @param itemAndOptionList 商品グループ化後でデータ
     * @return 返品商品
     */
    private ReturnItemDto getReturnItemList(List<SlipDto> itemAndOptionList, String storeId,
        Integer receiptId) {
        // 普通商品
        ReturnItemDto returnItemDto = new ReturnItemDto();
        // 商品名称
        returnItemDto
            .setItemName(jsonToString(storeId, receiptId, itemAndOptionList.get(0).getItemName()));
        // 商品個数
        returnItemDto.setItemCount(itemAndOptionList.get(0).getItemCount().toString());

        DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
        String itemPrice = df.format(itemAndOptionList.get(0).getItemPrice());
        if (Objects.equals(CodeConstants.TaxType.SOTO_RATE.getCode(),
            itemAndOptionList.get(0).getTaxCode())) {
            // 商品価格
            returnItemDto.setItemPrice("-￥" + itemPrice + "(外)");
        } else {
            // 商品価格
            returnItemDto
                .setItemPrice("-￥" + itemPrice + "    ");
        }

        //軽減税
        if (Objects.equals(CodeConstants.TaxReliefApplyType.ALWAYS_APPLIED.getCode(),
            itemAndOptionList.get(0).getTaxReliefApplyType())) {
            returnItemDto.setTaxReliefApplyType("*");
        } else {
            returnItemDto.setTaxReliefApplyType(" ");
        }

        // 商品オプションリスト
        List<ItemOptionDto> itemOptionList = new ArrayList<ItemOptionDto>();
        if (Objects.equals(itemAndOptionList.get(0).getOptionFlag(), "1")) {
            // グループ化
            Map<String, List<SlipDto>> itemOptionGroupMap = itemAndOptionList.stream()
                .collect(Collectors.groupingBy(this::fetchOptionGroupKey));
            itemOptionGroupMap.forEach((code, optionList) -> {
                if (!Objects.isNull(optionList.get(0)) && StringUtils
                    .isNotEmpty(optionList.get(0).getOptionTypeName())) {

                    // 商品オプション
                    ItemOptionDto itemOptionDto = new ItemOptionDto();
                    // オプション見出し
                    itemOptionDto
                        .setOptionTitle(jsonToString(storeId, receiptId,
                            optionList.get(0).getOptionTypeName()));
                    // オプション区分
                    itemOptionDto.setClassification(optionList.get(0).getClassification());
                    List<OptionDetailDto> optionDetailList = new ArrayList<OptionDetailDto>();
                    for (SlipDto slipDto : optionList) {
                        OptionDetailDto optionDetailDto = new OptionDetailDto();
                        // 商品オプション数量
                        optionDetailDto.setOptionCount(slipDto.getItemOptionCount().toString());
                        // オプション名
                        optionDetailDto.setOptionName(
                            jsonToString(storeId, receiptId, slipDto.getOptionName()));
                        optionDetailList.add(optionDetailDto);
                    }
                    itemOptionDto.setOptionDetailList(optionDetailList);
                    itemOptionList.add(itemOptionDto);
                }
            });
            returnItemDto.setItemOptionList(itemOptionList);

        }
        return returnItemDto;
    }

    /**
     * 客用伝票処理.
     *
     * @param inputDto 入力DTO
     * @return 客用伝票印刷
     */
    @Override
    public CustomerOrderInfoDto getCustomerOrderPrintData(SlipInputDto inputDto) {

        // 客用伝票
        CustomerOrderInfoDto customerOrderInfoDto = new CustomerOrderInfoDto();
        MControl controlDto = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PRINT_CUSTOMER_WITH_OR_OUT,
                Flag.OFF.getCode());
        if (Objects.equals(Flag.OFF.getCode().toString(), controlDto.getControlCode())) {
            return null;
        }
        // 客用伝票ラベル
        customerOrderInfoDto
            .setCustomerOrderLabelDto(getCustomerOrderLabel(inputDto.getStoreId(), 2));

        // 客用伝票データ
        List<SlipDto> slipList = orderSummaryRepository.findByOrderSummaryIdAndByStoreId(
            inputDto.getStoreId(), inputDto.getOrderSummaryId(), inputDto.getOrderIdList(),
            inputDto.getOrderDetailId());

        // 客用伝票
        customerOrderInfoDto.setCustomerOrderDto(getCustomerOrder(slipList, inputDto));

        return customerOrderInfoDto;
    }

    /**
     * 客用伝票処理.
     *
     * @param slipListTemp プリンターデータ
     * @param inputDto     入力DTO
     * @return 客用伝票印刷
     */
    private CustomerOrderDto getCustomerOrder(List<SlipDto> slipListTemp, SlipInputDto inputDto) {

        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        List<SlipDto> slipList = slipListTemp.stream()
            .filter(slipDto -> slipDto.getItemPrice().compareTo(BigDecimal.ZERO) == 1)
            .collect(Collectors.toList());
        if (slipList.size() == 0) {
            return customerOrderDto;
        }
        // 食卓名称
        customerOrderDto.setTableName(slipList.get(0).getTableName());
        // 人数
        if (!Objects.isNull(slipList.get(0).getCustomerCount())) {
            customerOrderDto.setPeopleNumber(slipList.get(0).getCustomerCount().toString());
        }
        // 注文時間
        customerOrderDto.setOrderTime(DateUtil
            .getZonedDateString(ZonedDateTime.now(), CommonConstants.DATE_FORMAT_DATETIME));
        // 担当者
        customerOrderDto.setResponsibleParty(inputDto.getUserName());
        // 受付番号
        customerOrderDto.setReceptionNo(String.format("%04d", slipList.get(0).getReceptionNo()));
        // プリンター情報取得
        PrintDto printDto = getPrintInfo(inputDto.getStoreId(), CUSTOMER_PRINT);

        // ブルートゥース名称
        customerOrderDto.setBluetoothName(printDto.getBluetoothName());
        // プリンターIP
        customerOrderDto.setPrintIp(printDto.getPrintIp());
        // 接続方式区分
        customerOrderDto.setConnectionMethod(printDto.getConnectionMethodCode());
        // プリンタブランドCD
        customerOrderDto.setBrandCode(printDto.getBrandCode());
        //伝票幅CD
        customerOrderDto.setPrintSize(printDto.getPrintSize());
        // グルーブ化
        Map<String, List<SlipDto>> itemInfoGroupMap = slipList.stream()
            .collect(Collectors.groupingBy(this::fetchItemGroupKey));

        // 外税金額
        GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
        List<ItemsDto> itemList = new ArrayList<>();

        List<NormallyItemDto> normallyItemList = new ArrayList<>();
        List<ReturnItemDto> returnItemList = new ArrayList<>();
        itemInfoGroupMap.forEach((key, itemAndOptionList) -> {
            // 税計算用
            ItemsDto itemsDto = new ItemsDto();
            itemsDto.setItemId(itemAndOptionList.get(0).getItemId().toString());
            itemsDto.setItemPrice(itemAndOptionList.get(0).getItemPrice()
                .multiply(new BigDecimal(itemAndOptionList.get(0).getItemCount())));
            itemList.add(itemsDto);

            // 普通商品
            List<SlipDto> normallyItemTempList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.NORMAL.getCode())).collect(Collectors.toList());
            if (normallyItemTempList.size() > 0) {
                normallyItemList.add(
                    getNormallyItemList(normallyItemTempList, null, true, inputDto.getStoreId(),
                        2));
            }
            // 返品商品
            List<SlipDto> returnItemTempList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.RETURN.getCode())).collect(Collectors.toList());
            if (returnItemTempList.size() > 0) {
                returnItemList.add(getReturnItemList(returnItemTempList, inputDto.getStoreId(), 2));
            }
        });
        customerOrderDto.setNormallyItemList(normallyItemList);
        customerOrderDto.setReturnItemDtoList(returnItemList);
        if (Objects.equals(inputDto.getDeliveryFlag(), OrderType.DELIVERY.getCode())) {
            taxValueInputDto.setTakeoutFlag(TakeoutFlag.TAKE_OUT.getCode());
        } else {
            taxValueInputDto.setTakeoutFlag(slipList.get(0).getTakeoutFlag());
        }

        taxValueInputDto.setItemList(itemList);
        taxValueInputDto.setStoreId(inputDto.getStoreId());
        DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
        // 小計金額
        customerOrderDto.setSubtotal(
            MONEY_CHARACTER + df.format(slipList.get(0).getOrderAmount()));
        return customerOrderDto;
    }

    /**
     * 会計伝票処理.
     *
     * @param inputDto 入力DTO
     * @return 会計伝票印刷
     */
    @Override
    public OrderAccountInfoDto getOrderAccountPrintData(SlipInputDto inputDto) {
        {

            // 会計用情報
            OrderAccountInfoDto orderAccountInfoDto = new OrderAccountInfoDto();
            // 後会計レシート印刷フラグ
            if (!Flag.ON.getCode().equals(inputDto.getAccountingPrintFlag())) {
                MControl controlDto = controlRepository
                    .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                        CommonConstants.CODE_GROUP_PRINT_ACCOUNT_WITH_OR_OUT,
                        Flag.OFF.getCode());
                if (Objects.equals(Flag.OFF.getCode().toString(), controlDto.getControlCode())) {
                    return null;
                }
            }

            // 注文会計伝票ラベル
            orderAccountInfoDto
                .setOrderAccountLabelDto(getOrderAccountLabel(inputDto.getStoreId(),
                    CommonConstants.ORDER_ACCOUNT_PRINT));
            // 支払区分情報取得
            MControl control = controlRepository
                .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                    CODE_GROUP_PAYMENT, Flag.OFF.getCode());
            List<Integer> orderIdList = null;
            if (Objects.equals(PaymentType.ADVANCE_PAYMENT.getCode(), control.getControlCode())
                && !Objects.isNull(inputDto.getOrderIdList())) {
                orderIdList = new ArrayList<>();
                orderIdList.addAll(inputDto.getOrderIdList());
            }
            // 会計伝票データ
            List<SlipDto> slipList = new ArrayList<>();
            // 出前の場合
            if (Objects.equals(inputDto.getDeliveryFlag(), OrderType.DELIVERY.getCode())) {
                // 注文厨房伝票データ
                List<SlipDeliveryDto> slipDeliveryList = deliveryOrderSummaryRepository
                    .findByDeliveryOrderSummaryIdAndByStoreId(
                        inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                        orderIdList, null);
                for (SlipDeliveryDto slipDeliveryDto : slipDeliveryList) {
                    slipList.add(beanMapper.map(slipDeliveryDto, SlipDto.class));
                }
            } else {
                slipList = orderSummaryRepository.findByOrderSummaryIdAndByStoreId(
                    inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                    orderIdList, null);
            }

            if (slipList.size() == 0) {
                return orderAccountInfoDto;
            }
            // 注文会計伝票
            OrderAccountDto orderAccountDto = getOrderAccount(slipList, inputDto, 4);

            // 店舗情報取得
            MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());
            // 店舗名称
            orderAccountDto.setStoreName(store.getStoreName());
            // 店舗電話
            orderAccountDto.setStoreTel(store.getStorePhone());
            //店舗住所
            orderAccountDto.setStoreAddress(store.getStoreAddress());
            orderAccountInfoDto.setOrderAccountDto(orderAccountDto);

            // 個人情報
            if (Objects.equals(inputDto.getDeliveryFlag(), OrderType.DELIVERY.getCode())) {
                DeliveryPersonalDto deliveryPersonalDto = new DeliveryPersonalDto();
                deliveryPersonalDto.setPersonalInfoLabel("お客様情報：");
                deliveryPersonalDto.setCustomerNameLabel("氏名：");
                deliveryPersonalDto.setPersonalAddressLabel("住所：");
                deliveryPersonalDto.setPersonalTelNumberLabel("電話番号：");
                deliveryPersonalDto.setCateringChargeAmountLabel("配送料：");

                Map<String, Object> cunstomInfoMap = deliveryOrderSummaryRepository
                    .findCustomInfo(inputDto.getStoreId(), inputDto.getOrderSummaryId(), "ja_JP");
                DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
                deliveryPersonalDto.setCateringChargeAmount(MONEY_CHARACTER + df
                    .format(new BigDecimal(cunstomInfoMap.get("cateringChargeAmount").toString())));
                deliveryPersonalDto.setCustomerName(
                    Objects.isNull(cunstomInfoMap.get("customerName")) ? ""
                        : cunstomInfoMap.get("customerName").toString());
                String address =
                    (Objects.isNull(cunstomInfoMap.get("prefectureName")) ? ""
                        : cunstomInfoMap.get("prefectureName").toString()) + (
                        Objects.isNull(cunstomInfoMap.get("cityName")) ? ""
                            : cunstomInfoMap.get("cityName"))
                        .toString() + (Objects.isNull(cunstomInfoMap.get("blockName")) ? ""
                        : cunstomInfoMap.get("blockName").toString());
                if (!Objects.isNull(cunstomInfoMap.get("deliveryOther"))) {
                    address = address + cunstomInfoMap.get("deliveryOther").toString();
                }
                deliveryPersonalDto.setAddress(address);
                deliveryPersonalDto
                    .setTelNumber(Objects.isNull(cunstomInfoMap.get("telNumber")) ? ""
                        : cunstomInfoMap.get("telNumber").toString());
                deliveryPersonalDto.setComment(Objects.isNull(cunstomInfoMap.get("comment")) ? ""
                    : cunstomInfoMap.get("comment").toString());
                orderAccountDto.setDeliveryPersonalDto(deliveryPersonalDto);
                orderAccountDto.setDeliveryPersonalDto(deliveryPersonalDto);
            }

            return orderAccountInfoDto;
        }
    }

    /**
     * 領収書伝票処理.
     *
     * @param inputDto 入力DTO
     * @return 領収書伝票印刷
     */
    @Override
    public ReceiptInfoDto getReceiptPrintData(SlipInputDto inputDto) {
        {

            ReceiptInfoDto receiptInfoDto = new ReceiptInfoDto();

            // 店舗情報取得
            MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());
            // 店舗名称
            receiptInfoDto.setStoreName(store.getStoreName());
            // 店舗名称
            receiptInfoDto.setStoreAddress(store.getStoreAddress());
            receiptInfoDto.setPostNumber(store.getPostNumber());
            receiptInfoDto.setStorePhone(store.getStorePhone());
            DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
            // 金額
            receiptInfoDto.setPaymentAmount(
                MONEY_CHARACTER + df.format(inputDto.getPaymentAmount()));
            receiptInfoDto.setReceiptLabel("領収書");
            receiptInfoDto.setYouLabel("様");
            receiptInfoDto.setMoneyLabel("金");
            receiptInfoDto.setYenLabel("ー");
            // 消費税取得
            List<OTaxAmount> taxAmountList = taxAmountRepository
                .findByStoreIdAndDelFlagAndOrderSummaryId(inputDto.getStoreId(), Flag.OFF.getCode(),
                    inputDto.getOrderSummaryId());
            String consumptionAmount =
                MONEY_CHARACTER + df.format(taxAmountList.stream()
                    .mapToLong(value -> value.getConsumptionAmount().longValue()).sum());
            receiptInfoDto
                .setConsumptionAmount(String.format("＜消費税等%sを含みます＞", consumptionAmount));
            receiptInfoDto.setBookFee("但し　御飲食代として");
            receiptInfoDto.setWordingLabel("上記正に領収いたしました。");
            // 注文時間
            receiptInfoDto.setIssueDate(DateUtil
                .getZonedDateString(ZonedDateTime.now(), CommonConstants.DATE_FORMAT));
            receiptInfoDto.setMarkLabel("印");

            PrintDto printDto = getPrintInfo(inputDto.getStoreId(), CommonConstants.RECEIPT_PRINT);
            // ブルートゥース名称
            receiptInfoDto.setBluetoothName(printDto.getBluetoothName());
            // プリンターIP
            receiptInfoDto.setPrintIp(printDto.getPrintIp());
            // 接続方式区分
            receiptInfoDto.setConnectionMethod(printDto.getConnectionMethodCode());
            // プリンタブランドCD
            receiptInfoDto.setBrandCode(printDto.getBrandCode());
            //伝票幅CD
            receiptInfoDto.setPrintSize(printDto.getPrintSize());
            return receiptInfoDto;
        }
    }

    /**
     * 龍高飯店日報.
     *
     * @param inputDto 検索条件
     * @return 龍高飯店日報
     */
    @Override
    public RgDailyReportDto getDailyReportPrintData(RgDailyReportInputDto inputDto) {

        RgDailyReportDto rgDailyReportDto = new RgDailyReportDto();
        // プリンター情報取得
        PrintDto printDto = getPrintInfo(inputDto.getStoreId(),
            CommonConstants.ORDER_ACCOUNT_PRINT);
        rgDailyReportDto.setBluetoothName(printDto.getBluetoothName());
        rgDailyReportDto.setBrandCode(printDto.getBrandCode());
        rgDailyReportDto.setPrintIp(printDto.getPrintIp());
        rgDailyReportDto.setPrintSize(printDto.getPrintSize());
        rgDailyReportDto.setConnectionMethod(printDto.getConnectionMethodCode());
        // 店舗情報
        MStore storeDto = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());
        rgDailyReportDto.setStoreName(storeDto.getStoreName());
        rgDailyReportDto.setStoreTel(storeDto.getStorePhone());
        rgDailyReportDto.setStoreAdress(storeDto.getStoreAddress());
        // 受付時間を取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // 現在日付
        String nowDate = DateUtil
            .getZonedDateString(nowDateTime, CommonConstants.DATE_FORMAT_DATE);

        // 営業開始時間
        ZonedDateTime startTime = DateUtil
            .getZonedDateByString(nowDate + " " + storeDto.getStartTime() + ":00",
                CommonConstants.DATE_FORMAT_DATETIME);
        startTime = startTime.plusDays(-90);

        // 営業終了時間
        ZonedDateTime endTime = DateUtil
            .getZonedDateByString(nowDate + " " + storeDto.getEndTime() + ":00",
                CommonConstants.DATE_FORMAT_DATETIME);

        // 当日注文リスト取得
        List<RgOrderSummaryDto> rgOrderSummaryList = orderSummaryRepository
            .getSameDayOrder(inputDto.getStoreId(), startTime, endTime,
                PayStatus.PAY_ALREADY.getCode());
        // 商品数
        rgDailyReportDto.setItemCount(rgOrderSummaryList.size());

        // distinct
        List<RgOrderSummaryDto> rgOrderSummaryDistinctList = rgOrderSummaryList.stream()
            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                Comparator.comparing(RgOrderSummaryDto::getOrderSummaryId))), ArrayList::new));

        BigDecimal itemPriceSum = new BigDecimal(rgOrderSummaryDistinctList.stream()
            .mapToDouble(rgOrderSummaryDto -> rgOrderSummaryDto.getPaymentAmount().doubleValue())
            .reduce(0, Double::sum));

        DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
        // 商品総金額
        rgDailyReportDto.setItemPriceSum(MONEY_CHARACTER + df.format(itemPriceSum));
        // オーダー数
        rgDailyReportDto.setOrderCount(String.valueOf(rgOrderSummaryDistinctList.size()) + "組");

        String customerCount = String.valueOf((int) rgOrderSummaryDistinctList.stream()
            .mapToDouble(rgOrderSummaryDto -> rgOrderSummaryDto.getCustomerCount().doubleValue())
            .reduce(0, Double::sum));
        // 人数
        rgDailyReportDto.setCustomerCount(customerCount + "名");

        // 外税金額
        List<RgOrderSummaryDto> takeOutOrderList = rgOrderSummaryDistinctList.stream()
            .filter(rgOrderSummaryDto -> Objects.equals(rgOrderSummaryDto.getTakeoutFlag(),
                TakeoutFlag.TAKE_OUT.getCode()))
            .collect(Collectors.toList());
        List<RgOrderSummaryDto> eatInOrderList = rgOrderSummaryDistinctList.stream()
            .filter(rgOrderSummaryDto -> Objects.equals(rgOrderSummaryDto.getTakeoutFlag(),
                TakeoutFlag.EAT_IN.getCode()))
            .collect(Collectors.toList());
        BigDecimal takeOutTax = new BigDecimal(takeOutOrderList.stream()
            .mapToDouble(rgOrderSummaryDto -> rgOrderSummaryDto.getPaymentAmount().doubleValue())
            .reduce(0, Double::sum));
        BigDecimal eatInTax = new BigDecimal(eatInOrderList.stream()
            .mapToDouble(rgOrderSummaryDto -> rgOrderSummaryDto.getPaymentAmount().doubleValue())
            .reduce(0, Double::sum));
        String sotoTax = MONEY_CHARACTER + df.format(
            takeOutTax.multiply(BigDecimal.valueOf(0.08))
                .add(eatInTax.multiply(BigDecimal.valueOf(0.1))));
        rgDailyReportDto.setSotoTaxAmount(sotoTax);
        // 支払済み
        List<Map<String, Object>> orderSummaryList = orderSummaryRepository
            .findSaleOrderSumByStoreId(inputDto.getStoreId(), startTime, endTime,
                CODE_GROUP_PAYMENT, AccountsType.CASH.getCode());
        // 現金回数
        rgDailyReportDto.setCashAmount(orderSummaryList.size() + "回");
        // 現金金額
        BigDecimal cashVal = new BigDecimal(orderSummaryList.stream()
            .mapToDouble(map -> Double.valueOf(map.get("paymentAmount").toString()))
            .reduce(0, Double::sum));
        rgDailyReportDto.setCashAmount(MONEY_CHARACTER + df.format(cashVal));
        // クレジットカート計
        rgDailyReportDto.setCashCount("0回");
        rgDailyReportDto.setCashAmount(MONEY_CHARACTER + "0");

        // 掛計
        rgDailyReportDto.setContributionCount("0回");
        rgDailyReportDto.setContributionAmount(MONEY_CHARACTER + "0");

        // 釣銭準備金
        rgDailyReportDto.setChangeReserveAmount(
            MONEY_CHARACTER + df.format(new BigDecimal("1000000")));
        // 現金在高
        rgDailyReportDto.setCashCurrentAmount(
            MONEY_CHARACTER + df.format(new BigDecimal("9000000")));
        // 店内飲食
        List<RgOrderSummaryDto> eatInItemList = rgOrderSummaryList.stream()
            .filter(rgOrderSummaryDto -> Objects.equals(rgOrderSummaryDto.getTakeoutFlag(),
                TakeoutFlag.EAT_IN.getCode()))
            .collect(Collectors.toList());
        rgDailyReportDto.setEatInItemCount(eatInItemList.size() + "点");
        List<RgOrderSummaryDto> eatInItemDistinList = eatInItemList.stream()
            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                Comparator.comparing(RgOrderSummaryDto::getOrderSummaryId))), ArrayList::new));
        BigDecimal eatInVal = new BigDecimal(eatInItemDistinList.stream()
            .mapToDouble(rgOrderSummaryDto -> rgOrderSummaryDto.getPaymentAmount().doubleValue())
            .reduce(0, Double::sum));
        rgDailyReportDto.setEatInItemAmount(MONEY_CHARACTER + df.format(eatInVal));

        // 持ち帰り飲食
        List<RgOrderSummaryDto> takeOutItemList = rgOrderSummaryList.stream()
            .filter(rgOrderSummaryDto -> Objects.equals(rgOrderSummaryDto.getTakeoutFlag(),
                TakeoutFlag.TAKE_OUT.getCode()))
            .collect(Collectors.toList());
        rgDailyReportDto.setTakeOutItemCount(takeOutItemList.size() + "点");
        List<RgOrderSummaryDto> takeOutItemDistinList = takeOutItemList.stream()
            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
                Comparator.comparing(RgOrderSummaryDto::getOrderSummaryId))), ArrayList::new));
        BigDecimal takeOutVal = new BigDecimal(takeOutItemDistinList.stream()
            .mapToDouble(rgOrderSummaryDto -> rgOrderSummaryDto.getPaymentAmount().doubleValue())
            .reduce(0, Double::sum));
        rgDailyReportDto
            .setTakeOutItemAmount(MONEY_CHARACTER + df.format(takeOutVal));
        // 外税対象額
        rgDailyReportDto.setForeignTaxAmount(rgDailyReportDto.getItemPriceSum());
        // 外税対象額
        rgDailyReportDto
            .setTaxableAmount(MONEY_CHARACTER + df.format(new BigDecimal("0")));
        // 消費税
        rgDailyReportDto.setConsumptionTax(MONEY_CHARACTER + df.format(
            takeOutVal.multiply(new BigDecimal("0.08"))
                .add(eatInVal.multiply(new BigDecimal("0.1")))));
        // 客単価
        rgDailyReportDto
            .setAveragePrice(MONEY_CHARACTER + df.format(itemPriceSum.add(
                takeOutVal.multiply(new BigDecimal("0.08"))
                    .add(eatInVal.multiply(new BigDecimal("0.1"))))
                .divide(new BigDecimal(customerCount), BigDecimal.ROUND_HALF_UP)));

        List<OOrderDetails> orderDetailsList = orderDetailsRepository
            .findByStoreIdAndDelFlagAndReturnOrderDetailIdIsNotNull(inputDto.getStoreId(),
                Flag.OFF.getCode());
        Map<Integer, List<OOrderDetails>> itemInfoGroupMap = orderDetailsList.stream()
            .collect(Collectors.groupingBy(OOrderDetails::getOrderId));
        // 返品
        rgDailyReportDto.setReturnItemCount(String.valueOf(itemInfoGroupMap.size()));
        BigDecimal returnItemVal = new BigDecimal(orderDetailsList.stream()
            .mapToDouble(orderDetails -> orderDetails.getItemPrice().doubleValue())
            .reduce(0, Double::sum));
        // 返品金額
        rgDailyReportDto
            .setReturnItemAmount(MONEY_CHARACTER + df.format(returnItemVal));
        // 精算回数
        rgDailyReportDto.setExactCalculation(df.format(new BigDecimal("100")) + "回");

        return rgDailyReportDto;
    }

    /**
     * 客用会計伝票処理.
     *
     * @param slipListTemp プリンターデータ
     * @param inputDto     入力DTO
     * @return 注文会計印刷
     */
    private OrderAccountDto getOrderAccount(List<SlipDto> slipListTemp, SlipInputDto inputDto,
        Integer receiptId) {

        OrderAccountDto orderAccountDto = new OrderAccountDto();

        // 商品価格＞０
        List<SlipDto> slipList = slipListTemp.stream()
            .filter(slipDto -> slipDto.getItemPrice().compareTo(BigDecimal.ZERO) == 1)
            .collect(Collectors.toList());

        if (slipList.size() == 0) {
            return orderAccountDto;
        }
        // 食卓名称
        if (!Objects.isNull(slipList.get(0).getTableName())) {
            orderAccountDto.setTableName(slipList.get(0).getTableName());
        }

        // 人数
        if (!Objects.isNull(slipList.get(0).getCustomerCount())) {
            orderAccountDto.setPeopleNumber(slipList.get(0).getCustomerCount().toString());
        }

        // 注文時間
        orderAccountDto.setOrderTime(DateUtil
            .getZonedDateString(ZonedDateTime.now(), CommonConstants.DATE_FORMAT_DATETIME));
        // 担当者
        orderAccountDto.setResponsibleParty(inputDto.getUserName());
        // 受付番号
        orderAccountDto.setReceptionNo(String.format("%04d", slipList.get(0).getReceptionNo()));

        // プリンター情報取得
        PrintDto printDto = getPrintInfo(inputDto.getStoreId(),
            CommonConstants.ORDER_ACCOUNT_PRINT);
        // ブルートゥース名称
        orderAccountDto.setBluetoothName(printDto.getBluetoothName());
        // プリンターIP
        orderAccountDto.setPrintIp(printDto.getPrintIp());
        // 接続方式区分
        orderAccountDto.setConnectionMethod(printDto.getConnectionMethodCode());
        // プリンタブランドCD
        orderAccountDto.setBrandCode(printDto.getBrandCode());

        //伝票幅CD
        orderAccountDto.setPrintSize(printDto.getPrintSize());
        // グルーブ化
        Map<String, List<SlipDto>> itemInfoGroupMap = slipList.stream()
            .collect(Collectors.groupingBy(this::fetchItemGroupKey));
        List<ReturnItemDto> returnItemList = new ArrayList<>();
        List<NormallyItemDto> normallyItemList = new ArrayList<>();
        itemInfoGroupMap.forEach((key, itemAndOptionList) -> {

            // 普通商品
            List<SlipDto> normallyItemTempList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.NORMAL.getCode())).collect(Collectors.toList());
            if (normallyItemTempList.size() > 0) {
                normallyItemList.add(
                    getNormallyItemList(normallyItemTempList, null, false, inputDto.getStoreId(),
                        4));
            }
            // 返品商品
            List<SlipDto> returnItemTempList = itemAndOptionList.stream()
                .filter(itemDto -> Objects.equals(itemDto.getItemClassification(),
                    ItemClassification.RETURN.getCode())).collect(Collectors.toList());

            if (returnItemTempList.size() > 0) {
                returnItemList.add(getReturnItemList(returnItemTempList, inputDto.getStoreId(), 4));
            }
        });
        orderAccountDto.setNormallyItemList(normallyItemList);
        orderAccountDto.setReturnItemList(returnItemList);

        // 返品商品
        List<SlipDto> returnSlipList = slipList.stream()
            .filter(slipDto -> !Objects.isNull(slipDto.getReturnOrderDetailId()))
            .collect(Collectors.toList());
        // 正常注文商品
        List<SlipDto> normallySlipList = slipList.stream()
            .filter(slipDto -> Objects.isNull(slipDto.getReturnOrderDetailId()))
            .collect(Collectors.toList());
        for (int i = 0; i < returnSlipList.size(); i++) {
            for (int j = 0; j < normallySlipList.size(); j++) {
                if (normallySlipList.get(j).getOrderDetailId().equals(returnSlipList.get(i)
                    .getReturnOrderDetailId())) {
                    if (normallySlipList.get(j).getItemCount().equals(returnSlipList.get(i)
                        .getItemCount())) {
                        normallySlipList.get(j).setItemCount(0);
                    } else {
                        normallySlipList.get(j).setItemCount(
                            normallySlipList.get(j).getItemCount() - returnSlipList.get(i)
                                .getItemCount());
                    }
                }
            }
        }
        normallySlipList = normallySlipList.stream()
            .filter(slipDto -> slipDto.getItemCount() != 0)
            .collect(Collectors.toList());
        List<ItemsDto> itemList = new ArrayList<>();
        // 税計算用
        normallySlipList.forEach(normallyItemDto -> {
            ItemsDto itemsDto = new ItemsDto();
            itemsDto.setItemId(normallyItemDto.getItemId().toString());
            itemsDto.setItemPrice(normallyItemDto.getItemPrice());
            itemList.add(itemsDto);
        });
        // 外税金額
        GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
        //テイクアウト
        taxValueInputDto.setTakeoutFlag(slipList.get(0).getTakeoutFlag());
        // 店舗ＩＤ
        taxValueInputDto.setStoreId(inputDto.getStoreId());
        // 商品リスト
        taxValueInputDto.setItemList(itemList);
        GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
            .getTaxValue(taxValueInputDto);
        DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
        orderAccountDto.setReducedTaxRate(
            MONEY_CHARACTER + df.format(taxValueOutputDto.getSotoTaxEightSum()));
        orderAccountDto.setStandardTaxRate(
            MONEY_CHARACTER + df.format(taxValueOutputDto.getSotoTaxTenSum()));

        if (Objects.equals(inputDto.getDeliveryFlag(), OrderType.DELIVERY.getCode())) {
            //外税
            orderAccountDto
                .setSotoTax(
                    MONEY_CHARACTER + df.format(slipList.get(0).getForeignTax()));
            // 小計
            orderAccountDto.setSubtotal(
                MONEY_CHARACTER + df.format(slipList.get(0).getOrderAmount()));

            // 合計金額
            orderAccountDto
                .setTotalAmount(MONEY_CHARACTER + df
                    .format(slipList.get(0).getOrderAmount().add(slipList.get(0).getForeignTax())));
        } else {
            orderAccountDto
                .setSotoTax(MONEY_CHARACTER + df.format(inputDto.getSotoTax()));

            // 小計
            orderAccountDto.setSubtotal(
                MONEY_CHARACTER + df.format(inputDto.getSubtotal()));

            // 合計金額
            orderAccountDto
                .setTotalAmount(MONEY_CHARACTER + df
                    .format(inputDto.getSubtotal().add(inputDto.getSotoTax())));
            // 値引き
            if (StringUtils.isNotEmpty(inputDto.getPriceDiscountAmount())) {
                orderAccountDto.setDiscountAmount(
                    MONEY_CHARACTER + df
                        .format((new BigDecimal(inputDto.getPriceDiscountAmount()))));
            }
            //割引
            if (StringUtils.isNotEmpty(inputDto.getPriceDiscountRate())) {
                orderAccountDto.setDiscount(
                    (new BigDecimal(inputDto.getPriceDiscountRate()).multiply(new BigDecimal((10))))
                        .toString() + "%");
            }
        }

        // 現金金額
        if (Objects.equals(inputDto.getCashType(), AccountsType.CASH.getCode())) {

            // 現金金額
            orderAccountDto.setCash(MONEY_CHARACTER + df.format(inputDto.getPaymentAmount()));
            // お預かり金額
            orderAccountDto
                .setCustody(MONEY_CHARACTER + df.format(inputDto.getCustody()));
            // お釣り金額
            orderAccountDto.setChange(MONEY_CHARACTER + df
                .format(inputDto.getCustody().subtract(inputDto.getPaymentAmount())));

        }

        List<Map<String, Object>> paymentDetailList = ppaymentDetailRepository
            .findByPayment(inputDto.getStoreId(),
                inputDto.getOrderSummaryId());
        // 後会計レシート印刷フラグ
        if (Flag.ON.getCode().equals(inputDto.getAccountingPrintFlag())
            && paymentDetailList.size() > 0
            && Objects.equals(paymentDetailList.get(0).get("paymentMethodCode").toString(), "00")) {
            orderAccountDto
                .setCash(MONEY_CHARACTER + df.format(
                    new BigDecimal(paymentDetailList.get(0).get("paymentAmount").toString())));
            // お預かり金額
            orderAccountDto
                .setCustody(MONEY_CHARACTER + df.format(
                    new BigDecimal(paymentDetailList.get(0).get("paymentAmount").toString())));
            // お釣り金額
            orderAccountDto.setChange(MONEY_CHARACTER + df
                .format(new BigDecimal("0")));

        }
        if (paymentDetailList.size() > 0) {
            if (!Objects
                .equals(paymentDetailList.get(0).get("paymentMethodCode").toString(), "00")) {
                MCode code = mcodeRepository
                    .findByStoreIdAndDelFlagAndCodeGroupAndCode(inputDto.getStoreId(),
                        Flag.OFF.getCode(), CODE_GROUP_PAYMENT,
                        paymentDetailList.get(0).get("paymentMethodCode").toString());
                // 支払方式１
                orderAccountDto.setPaymentMethod1Label(
                    code.getCodeName());
                orderAccountDto
                    .setPaymentMethod1(MONEY_CHARACTER + df.format(
                        new BigDecimal(paymentDetailList.get(0).get("paymentAmount").toString())));
            }

        }

        if (paymentDetailList.size() > 1) {
            if (!Objects
                .equals(paymentDetailList.get(1).get("paymentMethodCode").toString(), "00")) {
                MCode code = mcodeRepository
                    .findByStoreIdAndDelFlagAndCodeGroupAndCode(inputDto.getStoreId(),
                        Flag.OFF.getCode(), CODE_GROUP_PAYMENT,
                        paymentDetailList.get(1).get("paymentMethodCode").toString());
                // 支払方式２
                orderAccountDto.setPaymentMethod2Label(
                    code.getCodeName());
                orderAccountDto
                    .setPaymentMethod2(
                        MONEY_CHARACTER + df.format(new BigDecimal(
                            paymentDetailList.get(1).get("paymentAmount").toString())));
            }

        }
        if (paymentDetailList.size() > 2) {
            if (!Objects
                .equals(paymentDetailList.get(2).get("paymentMethodCode").toString(), "00")) {
                MCode code = mcodeRepository
                    .findByStoreIdAndDelFlagAndCodeGroupAndCode(inputDto.getStoreId(),
                        Flag.OFF.getCode(), CODE_GROUP_PAYMENT,
                        paymentDetailList.get(2).get("paymentMethodCode").toString());
                // 支払方式３
                orderAccountDto.setPaymentMethod3Label(
                    code.getCodeName());
                orderAccountDto
                    .setPaymentMethod3(
                        MONEY_CHARACTER + df.format(new BigDecimal(
                            paymentDetailList.get(2).get("paymentAmount").toString())));
            }

        }
        if (paymentDetailList.size() > 3) {
            if (!Objects
                .equals(paymentDetailList.get(3).get("paymentMethodCode").toString(), "00")) {
                MCode code = mcodeRepository
                    .findByStoreIdAndDelFlagAndCodeGroupAndCode(inputDto.getStoreId(),
                        Flag.OFF.getCode(), CODE_GROUP_PAYMENT,
                        paymentDetailList.get(3).get("paymentMethodCode").toString());
                // 支払方式４
                orderAccountDto.setPaymentMethod4Label(
                    code.getCodeName());
                orderAccountDto
                    .setPaymentMethod4(
                        MONEY_CHARACTER + df.format(new BigDecimal(
                            paymentDetailList.get(3).get("paymentAmount").toString())));
            }

        }
        return orderAccountDto;
    }

    /**
     * プリンター情報取得.
     *
     * @param storeId   店舗ID
     * @param receiptId 伝票ID  1:QR 2:客用伝票　3:キッチンリスト伝票 4:会計用伝票
     * @return 受付番号
     */
    PrintDto getPrintInfo(String storeId, Integer receiptId) {
        // プリンター情報を取得する
        PrintDto printDto = receiptRepository
            .getPrintInfo(storeId, receiptId);
        if (Objects.equals(printDto, null)) {
            // 異常　TODO
        }
        return printDto;
    }

    /**
     * プリンター情報取得.
     *
     * @param storeId   店舗ID
     * @param receiptId 伝票ID  1:QR 2:客用伝票　3:キッチンリスト伝票 4:会計用伝票
     * @return 受付番号
     */
    String getReceiptLanguage(String storeId, Integer receiptId) {
        String controlType = "";
        if (receiptId == 1) {
            controlType = CODE_GROUP_PRINT_QRCODE;
        } else if (receiptId == 2) {
            controlType = CODE_GROUP_PRINT_CUSTOMER;
        } else if (receiptId == 3) {
            controlType = CODE_GROUP_PRINT_KITCHEN;
        } else if (receiptId == 4) {
            controlType = CODE_GROUP_PRINT_ACCOUNT;
        }
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(storeId, controlType, Flag.OFF.getCode());
        return control.getControlCode();
    }


    Properties languageProperties(String storeId, Integer receiptId) {
        try {
            String languageCode = getReceiptLanguage(storeId, receiptId);
            String printMessages = "";
            // 日本語
            if (Objects.equals(languageCode, "ja_JP")) {
                printMessages = "print_messages_ja_JP.properties";
            } else if (Objects.equals(languageCode, "zh_CN")) {
                // 中国語
                printMessages = "print_messages_zh_CN.properties";
            } else if (Objects.equals(languageCode, "en_US")) {
                // 英語
                printMessages = "print_messages_en_US.properties";
            } else if (Objects.equals(languageCode, "ko_KN")) {
                // 韓国語
                printMessages = "print_messages_ko_KN.properties";
            }
            Properties properties = new Properties();
            InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream("i18n/" + printMessages);
            BufferedReader bf = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            properties.load(bf);
            return properties;
        } catch (IOException e) {
            throw new BusinessException("9001",
                ResultMessages.error().add("e.qr.ph.091", (Object) null));
        }
    }

    /**
     * 受付番号取得.
     *
     * @param storedId      店舗ID
     * @param receivablesId 受付ID
     * @return 受付番号
     */
    String getReceivablesNo(String storedId, String receivablesId) {

        // 受付番号取得
        OReceivables receivables = receivablesRepository
            .findByStoreIdAndDelFlagAndReceivablesId(storedId,
                Flag.OFF.getCode(), receivablesId);
        return String.format("%04d", receivables.getReceptionNo());
    }

    /**
     * キッチン伝票のタイトル設定.
     *
     * @return 注文厨房伝票ラベル
     */
    KitchenSlipLabelDto setKitchenSlipTitle(String storeId, Integer receiptId) {

        Properties properties = languageProperties(storeId,
            receiptId);
        // 注文厨房伝票ラベル
        KitchenSlipLabelDto kitchenSlipLabelDto = new KitchenSlipLabelDto();
        // タイトル(注文リスト)
        kitchenSlipLabelDto
            .setTitleOrderListLabel(properties.getProperty("titleOrderListLabel"));
        // タイトル(注文明細)
        kitchenSlipLabelDto
            .setTitleOrderDetailsLabel(properties.getProperty("titleOrderDetailsLabel"));
        // 食卓名称
        kitchenSlipLabelDto.setTableNameLabel(properties.getProperty("tableNameLabel"));
        // 注文時間
        kitchenSlipLabelDto.setOrderTimeLabel(properties.getProperty("orderTimeLabel"));
        // 受付番号
        kitchenSlipLabelDto.setReceptionNoLabel(properties.getProperty("receptionNoLabel"));
        // キチン
        kitchenSlipLabelDto.setKitchenLabel(properties.getProperty("kitchenLabel"));
        // 担当者
        kitchenSlipLabelDto
            .setResponsiblePartyLabel(properties.getProperty("staffNameLabel"));
        // 備考
        kitchenSlipLabelDto
            .setCommentLabel(properties.getProperty("commentLabel"));

        return kitchenSlipLabelDto;


    }

    /**
     * 客用伝票ラベル設定.
     *
     * @return 客用伝票ラベル
     */
    CustomerOrderLabelDto getCustomerOrderLabel(String storeId, Integer receiptId) {

        Properties properties = languageProperties(storeId,
            receiptId);
        // 客用伝票ラベル
        CustomerOrderLabelDto customerOrderLabelDto = new CustomerOrderLabelDto();
        // タイトル(客用)
        customerOrderLabelDto
            .setTitleOrderLabel(properties.getProperty("titleOrderDetailsLabel"));
        // 食卓名称
        customerOrderLabelDto.setTableNameLabel(properties.getProperty("tableNameLabel"));
        // 人数
        customerOrderLabelDto
            .setPeopleNumberLabel(properties.getProperty("peopleNumberLabel"));
        // 注文時間
        customerOrderLabelDto.setOrderTimeLabel(properties.getProperty("orderTimeLabel"));
        // 受付番号
        customerOrderLabelDto
            .setReceptionNoLabel(properties.getProperty("receptionNoLabel"));
        // 商品名
        customerOrderLabelDto.setItemNameLabel(properties.getProperty("itemNameLabel"));
        // 数量
        customerOrderLabelDto.setQuantityLabel(properties.getProperty("quantityLabel"));
        // 合計
        customerOrderLabelDto.setTotalLabel(properties.getProperty("totalLabel"));
        // 小計
        customerOrderLabelDto.setSubtotalLabel(properties.getProperty("subtotalLabel"));
        // 外税
        customerOrderLabelDto.setSotoTaxLabel(properties.getProperty("sotoTaxLabel"));
        // 合計
        customerOrderLabelDto
            .setTotalAmountLabel(properties.getProperty("totalAmountLabel"));

        return customerOrderLabelDto;

    }

    /**
     * 注文会計伝票ラベル設定.
     *
     * @return 注文会計伝票ラベル
     */
    OrderAccountLabelDto getOrderAccountLabel(String storeId, Integer receiptId) {

        Properties properties = languageProperties(storeId,
            receiptId);
        // 客用伝票ラベル
        OrderAccountLabelDto orderAccountLabelDto = new OrderAccountLabelDto();

        // 店舗住所：
        orderAccountLabelDto
            .setStoreAddressLabel(properties.getProperty("storeAddressLabel"));
        // 店舗電話
        orderAccountLabelDto.setSotreTelLabel(properties.getProperty("sotreTelLabel"));
        // 領収書
        orderAccountLabelDto.setReceiptLabel(properties.getProperty("receiptLabel"));
        // 食卓名称
        orderAccountLabelDto.setTableNameLabel(properties.getProperty("tableNameLabel"));
        // 人数
        orderAccountLabelDto
            .setPeopleNumberLabel(properties.getProperty("peopleNumberLabel"));
        // 受付番号
        orderAccountLabelDto
            .setReceptionNoLabel(properties.getProperty("receptionNoLabel"));
        // 担当者
        orderAccountLabelDto
            .setResponsiblePartyLabel(properties.getProperty("staffNameLabel"));
        // 商品名
        orderAccountLabelDto.setItemNameLabel(properties.getProperty("itemNameLabel"));
        // 数量
        orderAccountLabelDto.setQuantityLabel(properties.getProperty("quantityLabel"));
        // 合計
        orderAccountLabelDto.setTotalLabel(properties.getProperty("totalLabel"));
        // 割引
        orderAccountLabelDto.setDiscountLabel(properties.getProperty("discountLabel"));
        // 値引き
        orderAccountLabelDto
            .setDiscountValLabel(properties.getProperty("discountValLabel"));
        // 小計
        orderAccountLabelDto.setSubtotalLabel(properties.getProperty("subtotalLabel"));
        // 外税
        orderAccountLabelDto.setSotoTaxLabel(properties.getProperty("sotoTaxLabel"));
        // 合計
        orderAccountLabelDto
            .setTotalAmountLabel(properties.getProperty("totalAmountLabel"));
        // 税率
        orderAccountLabelDto.setTaxRateLabel(properties.getProperty("taxRateLabel"));
        // 対象
        orderAccountLabelDto.setTargetLabel(properties.getProperty("targetLabel"));
        // 現金
        orderAccountLabelDto.setCashLabel(properties.getProperty("cashLabel"));
        // お預かり
        orderAccountLabelDto.setCustodyLabel(properties.getProperty("custodyLabel"));
        // お釣り
        orderAccountLabelDto.setChangeLabel(properties.getProperty("changeLabel"));
        // 提示メッセージ
        orderAccountLabelDto
            .setPresentMessageLabel(properties.getProperty("presentMessageLabel"));
        return orderAccountLabelDto;


    }

    /**
     * 商品オーダーグループ化.
     *
     * @param slipDto 商品伝票印刷
     * @return グループ化条件
     */
    private String fetchItemOrderGroupKey(SlipDto slipDto) {
        return slipDto.getOrderSummaryId()
            + slipDto.getOrderId().toString();
    }

    /**
     * 商品グループ化.
     *
     * @param slipDto 商品伝票印刷
     * @return グループ化条件
     */
    private String fetchItemGroupKey(SlipDto slipDto) {
        return slipDto.getOrderSummaryId()
            + slipDto.getOrderId().toString()
            + slipDto.getOrderDetailId().toString();
    }

    /**
     * オプショングループ化.
     *
     * @param slipDto 商品伝票印刷
     * @return グループ化条件
     */
    private String fetchOptionGroupKey(SlipDto slipDto) {
        return slipDto.getOrderSummaryId()
            + slipDto.getOrderId().toString()
            + slipDto.getOrderDetailId().toString()
            + slipDto.getItemOptionTypeCode();
    }

    /**
     * 日本語取得.
     *
     * @return 言語
     */
    private String jsonToString(String storeId, Integer receiptId, String val) {

        return JSONObject.parseObject(val).getString(getReceiptLanguage(storeId, receiptId));
    }

    /**
     * 印刷注文取得.
     *
     * @param inputDto 入力DTO
     * @return 印刷注文取得
     */
    @Override
    @Transactional
    public List<String> getPrintOrderList(GetPrintOrderListInputDto inputDto) {

        List<String> printInfoList = new ArrayList<String>();

        // 印刷注文情報取得
        List<OPrintQueue> printOrderData = printQueueRepository
            .findByStoreIdAndPrintStatusAndDelFlag(
                inputDto.getStoreId(), PrintStatus.UNPRINT.getCode(), Flag.OFF.getCode());
        log.info(DateUtil
            .getZonedDateString(ZonedDateTime.now(), "yyyy-MM-dd HH:mm:ss") + printOrderData
            .size());

        if (printOrderData.size() == 0) {
            return null;
        }
        // 印刷状態変更
        printQueueRepository.updatePrintStatus(inputDto.getStoreId(),
            PrintStatus.UNPRINT.getCode(), PrintStatus.PRINTING.getCode(),
            CommonConstants.OPER_CD_STORE_PAD, DateUtil.getNowDateTime());

        for (int i = 0; i < printOrderData.size(); i++) {

            List<String> orderIdList = Arrays
                .asList(printOrderData.get(i).getOrderId().split(","));
            SlipInputDto inputParam = new SlipInputDto();
            inputParam.setOrderSummaryId(printOrderData.get(i).getOrderSummaryId());
            inputParam.setOrderIdList(
                orderIdList.stream().map(Integer::parseInt).collect(Collectors.toList()));
            inputParam.setPaymentAmount(printOrderData.get(i).getPaymentAmount());
            inputParam.setStoreId(inputDto.getStoreId());
            inputParam.setUserName(CommonConstants.OPER_CD_MOBILE);
            String printInfo = "";
            SlipPrintDto slipPrintDto = new SlipPrintDto();
            slipPrintDto
                .setKitchenDto(printDataSharedService.getKitchenPrintData(inputParam));
            // 厨房印刷
            // 前払いの場合
            if (Objects.equals(printOrderData.get(i).getStaffCheck().trim(), "0")) {
                //外税
                List<Map<String, Object>> sotoTaxMap = orderRepository
                    .findByStoreIdSotoTax(inputDto.getStoreId(), inputParam.getOrderIdList());
                inputParam
                    .setSotoTax(new BigDecimal(sotoTaxMap.get(0).get("foreignTax").toString()));
                // 小計
                inputParam
                    .setSubtotal(
                        inputParam.getPaymentAmount().subtract(inputParam.getSotoTax()));
                //会計印刷
                slipPrintDto.setOrderAccountInfoDto(printDataSharedService
                    .getOrderAccountPrintData(inputParam));
                slipPrintDto.setOrderId(printOrderData.get(i).getOrderId());
                slipPrintDto.setOrderSummaryId(inputParam.getOrderSummaryId());
                printInfo = JSON.toJSONString(slipPrintDto);
            } else {
                //後払い店員確認不要場合
                // 小計
                inputParam
                    .setSubtotal(printOrderData.get(i).getPaymentAmount());
                //会計印刷
                slipPrintDto.setCustomerOrderInfoDto(printDataSharedService
                    .getCustomerOrderPrintData(inputParam));
                slipPrintDto.setOrderId(printOrderData.get(i).getOrderId());
                slipPrintDto.setOrderSummaryId(inputParam.getOrderSummaryId());
                printInfo = JSON.toJSONString(slipPrintDto);

            }
            log.info("printInfo:" + printInfo);

            printInfoList.add(printInfo);

        }
        return printInfoList;
    }

    /**
     * 印刷状態変更.
     *
     * @param inputDto 入力DTO
     */
    @Override
    @Transactional
    public void changePrintStatus(ChangePrintStatusInputDto inputDto) {

        List<ChangePrintStatusDto> dataList = inputDto.getOrderList();

        for (int i = 0; i < dataList.size(); i++) {

            // 印刷状態変更
            printQueueRepository.updateOrderPrintStatus(dataList.get(i).getStoreId(),
                dataList.get(i).getOrderSummaryId(), dataList.get(i).getOrderId(),
                dataList.get(i).getPrintStatus(), CommonConstants.OPER_CD_STORE_PAD,
                DateUtil.getNowDateTime());
        }
    }


    /**
     * 点検精算印刷.
     *
     * @param storeId 店舗ID
     * @return 点検精算印刷情報
     */
    @Override
    public String inspectionSettlePrint(String storeId, String settleType) {

        // 店舗情報取得
        MStore store = storeRepository.findByStoreIdAndDelFlag(storeId,
            Flag.OFF.getCode());

        InspectionSettleInfoDto inspectionSettleInfoDto = new InspectionSettleInfoDto();
        // 点検精算ラベル設定
        inspectionSettleInfoDto.setInspectionSettleLabelDto(
            getInspectionSettleLabel(storeId, CommonConstants.ORDER_ACCOUNT_PRINT, settleType));

        //点検精算の項目設定
        InspectionSettleDto inspectionSettleDto = new InspectionSettleDto();
        // 店舗名称
        inspectionSettleDto.setStoreName(store.getStoreName());
        // 店舗電話
        inspectionSettleDto.setPhoneNumber(store.getStorePhone());
        // No （導入開始から今まで精算の回数取得）
        String noStr = inspectionSettleRepository
            .getInspectionSettlePrintNo(storeId, SettleType.SETTLE.getCode());
        inspectionSettleDto.setNo(noStr);

        // 精算期間
        // 現在日付
        ZonedDateTime nowTime = DateUtil.getNowDateTime();
        StoreOpenColseTimeDto storeOpenColseTimeDto = itemInfoSharedService
            .getStoreOpenColseTime(storeId);
        // 精算開始時間
        inspectionSettleDto.setInspectionSettleStart(DateUtil
            .getZonedDateString(storeOpenColseTimeDto.getStartTime(),
                CommonConstants.DATE_FORMAT_DATETIME));
        // 精算終了時間
        inspectionSettleDto.setInspectionSettleEnd(DateUtil
            .getZonedDateString(nowTime,
                CommonConstants.DATE_FORMAT_DATETIME));

        // オーダー情報取得
        List<Map<String, Object>> orderTaxPaymentMapList = orderSummaryRepository
            .findSaleSummaryOrderAndTaxAmountAndPaymentByStoreId(storeId,
                storeOpenColseTimeDto.getStartTime(), nowTime);
        List<OrderTaxPaymentDto> orderTaxPaymentList = new ArrayList<>();
        orderTaxPaymentMapList.forEach(stringObjectMap -> orderTaxPaymentList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    OrderTaxPaymentDto.class)));
        // 重複データ削除する
        List<OrderTaxPaymentDto> orderTaxPaymentDistcList = orderTaxPaymentList.stream().collect(
            Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                    Comparator.comparing(OrderTaxPaymentDto::getOrderSummaryId))), ArrayList::new)
        );
        //総売上
        BigDecimal paymentAmount = new BigDecimal(orderTaxPaymentDistcList.stream()
            .mapToLong(value -> value.getOrderPaymentAmount().longValue()).sum());
        DecimalFormat df = new DecimalFormat(CommonConstants.DEFAULT_FORMAT);
        inspectionSettleDto.setTotalSales(MONEY_CHARACTER + df.format(paymentAmount));

        // オーダー個数
        inspectionSettleDto.setOrderCount(String.valueOf(orderTaxPaymentDistcList.size()));

        // 消費税総額
        BigDecimal totalConsumptionTax = new BigDecimal(orderTaxPaymentList.stream()
            .mapToLong(value -> value.getConsumptionAmount().longValue()).sum());
        inspectionSettleDto
            .setTotalConsumptionTax(MONEY_CHARACTER + df.format(totalConsumptionTax));

        // 純売上
        inspectionSettleDto
            .setNetSales(MONEY_CHARACTER + df.format(paymentAmount.subtract(totalConsumptionTax)));

        // 人数
        Integer customerCount = orderTaxPaymentList.stream()
            .mapToInt(value -> value.getCustomerCount()).sum();
        inspectionSettleDto.setOrderCustomerCount(customerCount.toString());

        // 売上内訳　10％標準
        BigDecimal salesDetailNormalAmount = new BigDecimal(orderTaxPaymentList.stream()
            .mapToLong(value -> value.getForeignNormalObjectAmount().longValue()).sum())
            .add(new BigDecimal(orderTaxPaymentList.stream()
                .mapToLong(value -> value.getIncludedNormalObjectAmount().longValue()).sum()));
        inspectionSettleDto
            .setSalesDetailNormalAmount(MONEY_CHARACTER + df.format(salesDetailNormalAmount));
        // 売上内訳　8％軽減
        BigDecimal salesDetailReliefAmount = new BigDecimal(orderTaxPaymentList.stream()
            .mapToLong(value -> value.getForeignReliefObjectAmount().longValue()).sum())
            .add(new BigDecimal(orderTaxPaymentList.stream()
                .mapToLong(value -> value.getIncludedReliefObjectAmount().longValue()).sum()));
        inspectionSettleDto
            .setSalesDetailReliefAmount(MONEY_CHARACTER + df.format(salesDetailReliefAmount));

        // 消費税　10％標準
        BigDecimal consumptionTaxDetailNormalAmount = new BigDecimal(orderTaxPaymentList.stream()
            .mapToLong(value -> value.getForeignNormalAmount().longValue()).sum())
            .add(new BigDecimal(orderTaxPaymentList.stream()
                .mapToLong(value -> value.getIncludedNormalAmount().longValue()).sum()));
        inspectionSettleDto.setConsumptionTaxDetailNormalAmount(
            MONEY_CHARACTER + df.format(consumptionTaxDetailNormalAmount));
        // 消費税　8％軽減
        BigDecimal consumptionTaxDetailReliefAmount = new BigDecimal(orderTaxPaymentList.stream()
            .mapToLong(value -> value.getForeignReliefAmount().longValue()).sum())
            .add(new BigDecimal(orderTaxPaymentList.stream()
                .mapToLong(value -> value.getIncludedReliefAmount().longValue()).sum()));
        inspectionSettleDto.setConsumptionTaxDetailReliefAmount(
            MONEY_CHARACTER + df.format(consumptionTaxDetailReliefAmount));
        // 現金支払の件数
        List<OrderTaxPaymentDto> cashList = orderTaxPaymentDistcList.stream().filter(
            orderTaxPaymentDto -> Objects
                .equals(orderTaxPaymentDto.getPaymentMethodCode(), AccountsType.CASH.getCode()))
            .collect(
                Collectors.toList());
        inspectionSettleDto
            .setCashCount(Objects.isNull(cashList) ? "0" : String.valueOf(cashList.size()));
        // 現金支払金額
        BigDecimal cashAmount = BigDecimal.ZERO;
        if (!Objects.isNull(cashList) && cashList.size() > 0) {
            cashAmount = new BigDecimal(cashList.stream()
                .mapToLong(value -> value.getPaymentAmount().longValue()).sum());
        }
        inspectionSettleDto.setCashAmount(MONEY_CHARACTER + df.format(cashAmount));

        // 現金支払の件数
        List<OrderTaxPaymentDto> creditCardList = orderTaxPaymentDistcList.stream().filter(
            orderTaxPaymentDto -> Objects
                .equals(orderTaxPaymentDto.getPaymentMethodCode(), AccountsType.CREDIT.getCode()))
            .collect(
                Collectors.toList());
        inspectionSettleDto
            .setCreditCardCount(
                Objects.isNull(creditCardList) ? "0" : String.valueOf(creditCardList.size()));
        // 現金支払金額
        BigDecimal creditCardAmount = BigDecimal.ZERO;
        if (!Objects.isNull(creditCardList) && creditCardList.size() > 0) {
            creditCardAmount = new BigDecimal(creditCardList.stream()
                .mapToLong(value -> value.getPaymentAmount().longValue()).sum());
        }
        inspectionSettleDto.setCreditCardAmount(MONEY_CHARACTER + df.format(creditCardAmount));

        // 割引回数
        List<OrderTaxPaymentDto> discountList = orderTaxPaymentDistcList.stream()
            .filter(orderTaxPaymentDto ->
                BigDecimal.ZERO.compareTo(orderTaxPaymentDto.getPriceDiscountAmount()) == -1
                    || BigDecimal.ZERO.compareTo(orderTaxPaymentDto.getPriceDiscountRate()) == -1)
            .collect(
                Collectors.toList());
        inspectionSettleDto.setDiscountCount(
            Objects.isNull(discountList) ? "0" : String.valueOf(discountList.size()));
        // 割引金額
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (!Objects.isNull(creditCardList) && discountList.size() > 0) {
            discountAmount = new BigDecimal(discountList.stream()
                .mapToLong(value -> value.getPriceDiscountAmount().longValue()).sum())
                .add(new BigDecimal(discountList.stream()
                    .mapToLong(value -> value.getPriceDiscountRate().longValue()).sum()));
        }
        inspectionSettleDto.setDiscountAmount(MONEY_CHARACTER + df.format(discountAmount));

        // 会計修正回数
        List<OrderTaxPaymentDto> accountingRevisionList = orderTaxPaymentList.stream().filter(
            orderTaxPaymentDto -> BigDecimal.ZERO.compareTo(orderTaxPaymentDto.getPaymentAmount())
                == 1).collect(
            Collectors.toList());
        inspectionSettleDto.setAccountingRevisionCount(Objects.isNull(accountingRevisionList) ? "0"
            : String.valueOf(accountingRevisionList.size()));

        // 会計回数
        List<OrderTaxPaymentDto> numberAmountCountList = orderTaxPaymentList.stream().filter(
            orderTaxPaymentDto -> BigDecimal.ZERO.compareTo(orderTaxPaymentDto.getPaymentAmount())
                == -1).collect(
            Collectors.toList());
        inspectionSettleDto.setNumberAmountCount(Objects.isNull(numberAmountCountList) ? "0"
            : String.valueOf(numberAmountCountList.size()));

        Map<String, Object> inspectionMap = inspectionSettleRepository
            .getNextDayTransferredAmountAndInspectionSettleDate(storeId, settleType);
        inspectionSettleDto.setDepositAmount(
            Objects.isNull(inspectionMap) ? MONEY_CHARACTER + df.format(BigDecimal.ZERO)
                : MONEY_CHARACTER + df
                    .format(
                        new BigDecimal(inspectionMap.get("nextDayTransferredAmount").toString())));
        inspectionSettleDto.setDepositAmountCount("1");
        //点検の場合
        if (Objects.equals(settleType, SettleType.INSPECTION.getCode())) {

            inspectionSettleDto.setWithdrawalAmount(MONEY_CHARACTER + df.format(BigDecimal.ZERO));
            inspectionSettleDto.setWithdrawalAmountCount("0");
            inspectionSettleDto.setTotalDepositWithdrawalAmount(MONEY_CHARACTER + df.format(
                BigDecimal.ZERO.subtract(Objects.isNull(inspectionMap) ? BigDecimal.ZERO
                    : new BigDecimal(inspectionMap.get("nextDayTransferredAmount").toString()))));
        } else {
            List<BigDecimal> outAmountList = inspectionSettleRepository
                .getWithdrawalAmount(storeId, settleType, storeOpenColseTimeDto.getStartTime(),
                    nowTime);
            BigDecimal withdrawalAmount = new BigDecimal(outAmountList.stream()
                .mapToLong(BigDecimal::longValue).sum());
            inspectionSettleDto.setWithdrawalAmount(MONEY_CHARACTER + df.format(withdrawalAmount));
            inspectionSettleDto.setWithdrawalAmountCount(String.valueOf(outAmountList.size()));

            inspectionSettleDto.setTotalDepositWithdrawalAmount(MONEY_CHARACTER + df.format(
                withdrawalAmount.subtract(Objects.isNull(inspectionMap) ? BigDecimal.ZERO
                    : new BigDecimal(inspectionMap.get("nextDayTransferredAmount").toString()))));
        }

        // クーポン利用
        inspectionSettleDto.setCouponCount("0");
        inspectionSettleDto.setCouponAmount(MONEY_CHARACTER + df.format(BigDecimal.ZERO));
        // プリンター取得
        PrintDto printDto = getPrintInfo(storeId, CommonConstants.ORDER_ACCOUNT_PRINT);
        inspectionSettleDto.setPrintIp(printDto.getPrintIp());
        inspectionSettleDto.setPrintSize(printDto.getPrintSize());
        inspectionSettleDto.setBluetoothName(printDto.getBluetoothName());
        inspectionSettleDto.setBrandCode(printDto.getBrandCode());
        inspectionSettleDto.setConnectionMethod(printDto.getConnectionMethodCode());
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        slipPrintDto.setInspectionSettleInfoDto(inspectionSettleInfoDto);
        inspectionSettleInfoDto.setInspectionSettleDto(inspectionSettleDto);
        return JSON.toJSONString(slipPrintDto);

    }


    /**
     * 点検精算伝票ラベル設定.
     *
     * @return 点検精算伝票ラベル
     */
    private InspectionSettleLabelDto getInspectionSettleLabel(String storeId, Integer receiptId,
        String settleType) {

        Properties properties = languageProperties(storeId,
            receiptId);
        // 点検精算伝票ラベル
        InspectionSettleLabelDto inspectionSettleLabelDto = new InspectionSettleLabelDto();
        if (Objects.equals(settleType, SettleType.INSPECTION.getCode())) {
            // タイトル（点検）
            inspectionSettleLabelDto
                .setInspectionLabel(properties.getProperty("inspectionLabel"));
        } else {
            // タイトル（精算）
            inspectionSettleLabelDto
                .setSettleLabel(properties.getProperty("settleLabel"));
        }

        // no
        inspectionSettleLabelDto
            .setNoLabel(properties.getProperty("noLabel"));

        // 期間
        inspectionSettleLabelDto
            .setPeriodLabel(properties.getProperty("periodLabel"));

        // 電話番号
        inspectionSettleLabelDto
            .setPhoneNumberLabel(properties.getProperty("phoneNumberLabel"));

        // 総売上
        inspectionSettleLabelDto
            .setTotalSalesLabel(properties.getProperty("totalSalesLabel"));

        // オーダー個数
        inspectionSettleLabelDto
            .setOrderCountLabel(properties.getProperty("orderCountLabel"));

        // 純売上
        inspectionSettleLabelDto
            .setNetSalesLabel(properties.getProperty("netSalesLabel"));

        // 人数
        inspectionSettleLabelDto
            .setCustomerCountLabel(properties.getProperty("customerCountLabel"));

        // 人数 単位
        inspectionSettleLabelDto
            .setCustomerUnitLabel(properties.getProperty("customerUnitLabel"));

        // オーダー個数単位
        inspectionSettleLabelDto
            .setOrderUnitLabel(properties.getProperty("orderUnitLabel"));

        // 消費税総額
        inspectionSettleLabelDto
            .setTotalConsumptionTaxLabel(properties.getProperty("totalConsumptionTaxLabel"));

        // 売上内訳
        inspectionSettleLabelDto
            .setSalesDetailLabel(properties.getProperty("salesDetailLabel"));

        // 売上内訳（10 % 標準）
        inspectionSettleLabelDto
            .setSalesDetailNormalLabel(properties.getProperty("salesDetailNormalLabel"));

        // 売上内訳（8 % 軽減）
        inspectionSettleLabelDto
            .setSalesDetailReliefLabel(properties.getProperty("salesDetailReliefLabel"));

        // 消費税内訳
        inspectionSettleLabelDto
            .setConsumptionTaxDetailLabel(properties.getProperty("consumptionTaxDetailLabel"));
        // 消費税内訳（10 % 標準）
        inspectionSettleLabelDto
            .setConsumptionTaxDetailNormalLabel(
                properties.getProperty("consumptionTaxDetailNormalLabel"));

        //  消費税内訳（8 % 軽減）
        inspectionSettleLabelDto
            .setConsumptionTaxDetailReliefLabel(
                properties.getProperty("consumptionTaxDetailReliefLabel"));
        //  支払方法
        inspectionSettleLabelDto
            .setPayMethodLabel(properties.getProperty("payMethodLabel"));
        //  現金
        inspectionSettleLabelDto
            .setCashLabel(properties.getProperty("cashLabel"));
        //  商品券
        inspectionSettleLabelDto
            .setGiftCertificatesLabel(properties.getProperty("giftCertificatesLabel"));

        //  クレジットカード
        inspectionSettleLabelDto
            .setCreditCardLabel(properties.getProperty("creditCardLabel"));

        //  割引・割増
        inspectionSettleLabelDto
            .setDiscountSurchargeLabel(properties.getProperty("discountSurchargeLabel"));

        //  割引
        inspectionSettleLabelDto
            .setDiscountLabel(properties.getProperty("discountLabel"));

        //  クーポン利用
        inspectionSettleLabelDto
            .setCouponLabel(properties.getProperty("couponLabel"));

        //  会計修正
        inspectionSettleLabelDto
            .setAccountingRevisionLabel(properties.getProperty("accountingRevisionLabel"));

        //  差異金額
        inspectionSettleLabelDto
            .setDifferenceAmountLabel(properties.getProperty("differenceAmountLabel"));

        //  会計回数
        inspectionSettleLabelDto
            .setNumberAmountLabel(properties.getProperty("numberAmountLabel"));

        //  入出金合計金額
        inspectionSettleLabelDto
            .setTotalDepositWithdrawalAmountLabel(
                properties.getProperty("totalDepositWithdrawalAmountLabel"));

        //  入金金額
        inspectionSettleLabelDto
            .setDepositAmountLabel(properties.getProperty("depositAmountLabel"));

        //  出金金額
        inspectionSettleLabelDto
            .setWithdrawalAmountLabel(properties.getProperty("withdrawalAmountLabel"));

        //  回
        inspectionSettleLabelDto.setTimesLabel(properties.getProperty("times"));

        return inspectionSettleLabelDto;


    }

}
