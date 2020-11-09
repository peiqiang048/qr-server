package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.api.pc.constants.PCConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemCategory;
import com.cnc.qr.common.constants.CodeConstants.ItemShowStatus;
import com.cnc.qr.common.constants.CodeConstants.MstItemType;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MItemImage;
import com.cnc.qr.common.entity.RBuffetItem;
import com.cnc.qr.common.entity.RCategoryItem;
import com.cnc.qr.common.entity.RItemOption;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCategoryRepository;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MItemImageRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MKitchenRepository;
import com.cnc.qr.common.repository.MOptionTypeRepository;
import com.cnc.qr.common.repository.MTaxRepository;
import com.cnc.qr.common.repository.MUnitRepository;
import com.cnc.qr.common.repository.RBuffetItemRepository;
import com.cnc.qr.common.repository.RCategoryItemRepository;
import com.cnc.qr.common.repository.RItemOptionRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.ItemOptionDataDto;
import com.cnc.qr.core.pc.model.BuffetDto;
import com.cnc.qr.core.pc.model.ChangeCourseItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeCourseItemStatusInputDto;
import com.cnc.qr.core.pc.model.ChangeItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeItemStatusInputDto;
import com.cnc.qr.core.pc.model.CourseDelInputDto;
import com.cnc.qr.core.pc.model.CourseIdDto;
import com.cnc.qr.core.pc.model.CourseInputDto;
import com.cnc.qr.core.pc.model.DeleteItemInputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemInputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCateringTypeFlagListOutputDto;
import com.cnc.qr.core.pc.model.GetCourseInputDto;
import com.cnc.qr.core.pc.model.GetCourseList;
import com.cnc.qr.core.pc.model.GetCourseListInputDto;
import com.cnc.qr.core.pc.model.GetCourseListOutputDto;
import com.cnc.qr.core.pc.model.GetCourseOutputDto;
import com.cnc.qr.core.pc.model.GetDeliveryFlagListOutputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryInputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryOutputDto;
import com.cnc.qr.core.pc.model.GetItemInputDto;
import com.cnc.qr.core.pc.model.GetItemList;
import com.cnc.qr.core.pc.model.GetItemListInputDto;
import com.cnc.qr.core.pc.model.GetItemListOutputDto;
import com.cnc.qr.core.pc.model.GetItemOptionDto;
import com.cnc.qr.core.pc.model.GetItemOutputDto;
import com.cnc.qr.core.pc.model.GetKitchenListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionDto;
import com.cnc.qr.core.pc.model.GetOptionTypeDto;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
import com.cnc.qr.core.pc.model.ItemIdDto;
import com.cnc.qr.core.pc.model.ItemListDto;
import com.cnc.qr.core.pc.model.ItemSortOrderDto;
import com.cnc.qr.core.pc.model.RegistCourseInputDto;
import com.cnc.qr.core.pc.model.RegistItemInputDto;
import com.cnc.qr.core.pc.model.RegistItemOptionDto;
import com.cnc.qr.core.pc.service.PcItemService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品情報取得サービス実装クラス.
 */
@Service
public class PcItemServiceImpl implements PcItemService {

    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private RBuffetItemRepository rbuffetItemRepository;

    /**
     * カテゴリ商品関連リポジトリ.
     */
    @Autowired
    private RCategoryItemRepository categoryItemRepository;

    /**
     * 単位マスタリポジトリ.
     */
    @Autowired
    private MUnitRepository unitRepository;

    /**
     * カテゴリーマスタリポジトリ.
     */
    @Autowired
    private MCategoryRepository categoryRepository;

    /**
     * 税マスタリポジトリ.
     */
    @Autowired
    private MTaxRepository taxRepository;

    /**
     * キチンマスタリポジトリ.
     */
    @Autowired
    private MKitchenRepository kitchenRepository;

    /**
     * オプション種類リポジトリ.
     */
    @Autowired
    private MOptionTypeRepository optionTypeRepository;

    /**
     * 商品オプション関連リポジトリ.
     */
    @Autowired
    private RItemOptionRepository itemOptionRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * 商品画像情報共有サービス.
     */
    @Autowired
    private MItemImageRepository itemImageRepository;

    /**
     * コードマスタリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public GetItemListOutputDto getItem(GetItemListInputDto inputDto, Pageable pageable) {

        // 商品情報取得
        Page<Map<String, Object>> itemMap = itemRepository
            .getItemData(inputDto.getStoreId(), inputDto.getCategoryId(), inputDto.getLanguages(),
                StringUtils.isEmpty(inputDto.getItemName()) ? StringUtils.EMPTY
                    : inputDto.getItemName(), CommonConstants.CODE_GROUP_ITEM_STATUS,
                inputDto.getItemStatus(), inputDto.getKitchenId(), pageable);

        // 商品情報を設定する
        List<GetItemList> itemList = new ArrayList<>();
        itemMap.forEach(stringObjectMap -> itemList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetItemList.class)));

        GetItemListOutputDto output = new GetItemListOutputDto();

        output.setItems(new PageImpl<>(itemList, itemMap.getPageable(), itemMap.getTotalPages()));

        output.setTotalCount(itemMap.getTotalElements());

        return output;
    }

    /**
     * 商品明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public GetItemOutputDto getItemInfo(GetItemInputDto inputDto) {

        // 商品カテゴリー情報取得
        GetItemOutputDto itemInfo =
            itemRepository.getItemInfo(inputDto.getStoreId(), inputDto.getItemId());

        // 検索結果0件の場合
        if (null == itemInfo) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.053", (Object) null));
        }

        return itemInfo;
    }

    /**
     * 商品カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetCategoryIdListOutputDto> getItemCategory(GetItemInputDto inputDto) {

        // 商品カテゴリー取得
        List<GetCategoryIdListOutputDto> categoryIdList = categoryItemRepository.getCategoryIdList(
            inputDto.getStoreId(), inputDto.getItemId());

        categoryIdList.forEach(categoryIdData -> {
            categoryIdData.setCategoryName(JSONObject.parseObject(
                categoryIdData.getCategoryName()).getString(inputDto.getLanguages()));
        });

        return categoryIdList;
    }

    /**
     * 単位リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetUnitListOutputDto> getUnitList(GetItemInputDto inputDto) {

        // 単位リスト取得
        List<GetUnitListOutputDto> unitList = unitRepository.getUnitList(
            inputDto.getStoreId());
        unitList.forEach(unitData -> {
            unitData.setUnitName(JSONObject.parseObject(
                unitData.getUnitName()).getString(inputDto.getLanguages()));
        });

        return unitList;
    }

    /**
     * カテゴリーリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetCategoryList> getCategoryList(GetItemInputDto inputDto) {

        // カテゴリーリスト取得
        List<GetCategoryList> categoryList = categoryRepository.getCategoryList(
            inputDto.getStoreId(), PCConstants.GRADATION);
        categoryList.forEach(categoryData -> {
            categoryData.setCategoryName(JSONObject.parseObject(
                categoryData.getCategoryName()).getString(inputDto.getLanguages()));
        });

        return categoryList;
    }

    /**
     * 税リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetTaxListOutputDto> getTaxList(GetItemInputDto inputDto) {

        // 登録日時
        ZonedDateTime dateTime = DateUtil.getNowDateTime();

        // 税リスト取得
        List<GetTaxListOutputDto> taxList = taxRepository.getTaxList(
            inputDto.getStoreId(), dateTime);

        return taxList;
    }

    /**
     * キチンリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetKitchenListOutputDto> getKitchenList(GetItemInputDto inputDto) {

        // キチンリスト取得
        List<GetKitchenListOutputDto> kitchenList = kitchenRepository.getKitchenList(
            inputDto.getStoreId());

        return kitchenList;
    }

    /**
     * オプション情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetOptionTypeDto> getOptionTypeList(GetItemInputDto inputDto) {

        // 商品オプション類型情報取得
        List<Map<String, Object>> optionLists = optionTypeRepository.getOptionType(
            inputDto.getStoreId(), inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(optionLists)) {

            return new ArrayList<>();

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

        List<GetItemOptionDto> checkOptionList = itemOptionRepository
            .getCheckOption(inputDto.getStoreId(),
                inputDto.getItemId());

        // 商品オプションリストを編集する
        List<GetOptionTypeDto> optionTypeList = new ArrayList<GetOptionTypeDto>();
        grpByOptionTypeCd.forEach((s, itemOptionDataDtos) -> {
            GetOptionTypeDto itemOptionTypeDto = new GetOptionTypeDto();
            itemOptionTypeDto.setOptionTypeCd(itemOptionDataDtos.get(0).getOptionTypeCd());
            itemOptionTypeDto.setOptionTypeName(itemOptionDataDtos.get(0).getOptionTypeName());
            List<GetOptionDto> optionList = new ArrayList<>();
            itemOptionDataDtos.forEach(itemOptionDataDto -> {
                GetOptionDto option = new GetOptionDto();
                option.setOptionCode(itemOptionDataDto.getOptionCode());
                option.setOptionName(itemOptionDataDto.getOptionName());
                Integer checkFlag = 0;
                BigDecimal diffPrice = null;
                for (int i = 0; i < checkOptionList.size(); i++) {

                    if (checkOptionList.get(i).getOptionCode()
                        .equals(itemOptionDataDto.getOptionCode()) && checkOptionList.get(i)
                        .getOptionTypeCode()
                        .equals(itemOptionDataDtos.get(0).getOptionTypeCd())) {
                        checkFlag = 1;
                        diffPrice = checkOptionList.get(i).getItemOptionDiffPrice();
                    }
                }
                option.setCheckedFlag(checkFlag);
                option.setItemOptionDiffPrice(diffPrice);
                optionList.add(option);
            });
            itemOptionTypeDto.setOptionList(optionList);
            optionTypeList.add(itemOptionTypeDto);
        });

        optionTypeList.stream().sorted(Comparator.comparing(GetOptionTypeDto::getOptionTypeCd))
            .collect(Collectors.toList());

        return optionTypeList;
    }

    /**
     * 商品編集.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void registItem(RegistItemInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // 商品IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("m_item"); // テーブル名
            getSeqNoInputDto.setItem("item_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 商品ID
            Integer itemId = inputDto.getItemId();

            if (itemId == null) {

                // 商品情報作成
                // 商品画像IDのシーケンスNo取得
                GetSeqNoInputDto getSeqImage = new GetSeqNoInputDto();
                getSeqImage.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqImage.setTableName("m_item_image"); // テーブル名
                getSeqImage.setItem("item_image_id"); // 項目
                getSeqImage.setOperCd(userOperCd); // 登録更新者

                // 商品情報作成
                String optionFlag = "";

                if (CollectionUtils.isEmpty(inputDto.getItemOptionList())) {
                    optionFlag = Flag.OFF.getCode().toString();
                } else {
                    optionFlag = Flag.ON.getCode().toString();
                }

                itemRepository.insertItem(inputDto.getStoreId(), getSeqNo.getSeqNo(),
                    inputDto.getItemUnitId(),
                    inputDto.getItemKitchenId(), optionFlag, inputDto.getItemName(),
                    inputDto.getItemPrice(),
                    inputDto.getItemInfo(), ItemShowStatus.OVERHEAD.getCode(),
                    MstItemType.USUALLY.getCode(),
                    inputDto.getItemTaxId(), inputDto.getDeliveryFlag(),
                    inputDto.getDeliveryTypeFlag(),
                    userOperCd, dateTime);

                // 商品画像情報作成
                MItemImage itemImage = new MItemImage();
                itemImage.setStoreId(inputDto.getStoreId());                     //　店舗ID
                itemImage.setItemId(getSeqNo.getSeqNo());                        // 商品ID
                GetSeqNoOutputDto getSeqImageNo = itemInfoSharedService.getSeqNo(getSeqImage);
                itemImage.setItemImageId(getSeqImageNo.getSeqNo());              // 商品画像ID
                itemImage.setImagePath(inputDto.getItemLargePicUrl());           // 画像パス
                itemImage.setShortImagePath(inputDto.getItemSmallPicUrl());      // ショート画像パス
                itemImage.setVideoPath(inputDto.getItemVideo());                 // ビデオパス
                itemImage.setDelFlag(Flag.OFF.getCode());                        // 削除フラグ
                itemImage.setInsOperCd(userOperCd);                              // 登録者
                itemImage.setInsDateTime(dateTime);                              // 登録日時
                itemImage.setUpdOperCd(userOperCd);                              // 更新者
                itemImage.setUpdDateTime(dateTime);                              // 更新日時
                itemImage.setVersion(0);                                         // バージョン
                itemImageRepository.save(itemImage);

                // 商品カテゴリー関連情報作成
                List<RCategoryItem> itemCategoryList = new ArrayList<>();
                List<GetCategoryIdListOutputDto> itemCategoryIdList = inputDto.getCategoryIdList();
                for (GetCategoryIdListOutputDto getCategoryIdListOutputDto : itemCategoryIdList) {

                    RCategoryItem itemCategory = new RCategoryItem();
                    itemCategory
                        .setStoreId(inputDto.getStoreId());                              // 店舗ID
                    itemCategory.setCategoryId(
                        getCategoryIdListOutputDto.getCategoryId());       // カテゴリーID
                    itemCategory
                        .setItemId(getSeqNo.getSeqNo());                                 // 商品ID
                    Integer sortOrder = categoryItemRepository
                        .getMaxSortOrder(inputDto.getStoreId(),
                            getCategoryIdListOutputDto.getCategoryId());
                    if (null == sortOrder) {

                        itemCategory
                            .setSortOrder(1);                                            // 順番
                    } else {

                        itemCategory
                            .setSortOrder(sortOrder + 1);                                // 順番
                    }
                    itemCategory
                        .setDelFlag(Flag.OFF.getCode());                                 // 削除フラグ
                    itemCategory
                        .setInsOperCd(userOperCd);                                       // 登録者
                    itemCategory
                        .setInsDateTime(dateTime);                                       // 登録日時
                    itemCategory
                        .setUpdOperCd(userOperCd);                                       // 更新者
                    itemCategory
                        .setUpdDateTime(dateTime);                                       // 更新日時
                    itemCategory
                        .setVersion(0);                                                  // バージョン
                    itemCategoryList.add(itemCategory);
                }
                categoryItemRepository.saveAll(itemCategoryList);
            } else {

                String optionFlag = "";

                if (CollectionUtils.isEmpty(inputDto.getItemOptionList())) {
                    optionFlag = Flag.OFF.getCode().toString();
                } else {
                    optionFlag = Flag.ON.getCode().toString();
                }

                // 商品情報更新
                itemRepository.updateItem(inputDto.getStoreId(), itemId, inputDto.getItemUnitId(),
                    inputDto.getItemKitchenId(), optionFlag, inputDto.getItemName(),
                    inputDto.getItemPrice(), inputDto.getItemInfo(), inputDto.getItemTaxId(),
                    inputDto.getDeliveryFlag(), inputDto.getDeliveryTypeFlag(), userOperCd,
                    dateTime);

                if (StringUtils.isNotEmpty(inputDto.getItemLargePicUrl()) && StringUtils
                    .isEmpty(inputDto.getItemSmallPicUrl())) {
                    inputDto.setItemSmallPicUrl(inputDto.getItemLargePicUrl());
                }

                // 商品画像情報更新
                Integer upCount = itemImageRepository
                    .updateItemImage(inputDto.getStoreId(), itemId, inputDto.getItemLargePicUrl(),
                        inputDto.getItemSmallPicUrl(), inputDto.getItemVideo(), userOperCd,
                        dateTime);
                if (upCount == 0) {
                    // 商品画像IDのシーケンスNo取得
                    GetSeqNoInputDto getSeqImage = new GetSeqNoInputDto();
                    getSeqImage.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqImage.setTableName("m_item_image"); // テーブル名
                    getSeqImage.setItem("item_image_id"); // 項目
                    getSeqImage.setOperCd(userOperCd); // 登録更新者
                    GetSeqNoOutputDto getSeqImageNo = itemInfoSharedService.getSeqNo(getSeqImage);
                    // 商品画像情報作成
                    MItemImage itemImage = new MItemImage();
                    itemImage.setStoreId(inputDto.getStoreId());                     //　店舗ID
                    itemImage.setItemId(itemId);                                     // 商品ID
                    itemImage.setItemImageId(getSeqImageNo.getSeqNo());              // 商品画像ID
                    itemImage.setImagePath(inputDto.getItemLargePicUrl());           // 画像パス
                    itemImage.setShortImagePath(inputDto.getItemSmallPicUrl());      // ショート画像パス
                    itemImage.setVideoPath(inputDto.getItemVideo());                 // ビデオパス
                    itemImage.setDelFlag(Flag.OFF.getCode());                        // 削除フラグ
                    itemImage.setInsOperCd(userOperCd);                              // 登録者
                    itemImage.setInsDateTime(dateTime);                              // 登録日時
                    itemImage.setUpdOperCd(userOperCd);                              // 更新者
                    itemImage.setUpdDateTime(dateTime);                              // 更新日時
                    itemImage.setVersion(0);                                         // バージョン
                    itemImageRepository.save(itemImage);
                }

                // 商品順番情報取得
                List<RCategoryItem> itemSortOrder = categoryItemRepository
                    .findByStoreIdAndItemIdAndDelFlag(
                        inputDto.getStoreId(), itemId, Flag.OFF.getCode());

                List<GetCategoryIdListOutputDto> itemCategoryIdList = inputDto.getCategoryIdList();

                boolean addBool;

                List<Integer> addCategoryIdList = new ArrayList<>();

                for (GetCategoryIdListOutputDto categoryIdListOutputDto : itemCategoryIdList) {
                    addBool = true;
                    for (RCategoryItem categoryItem : itemSortOrder) {
                        if (categoryIdListOutputDto.getCategoryId()
                            .equals(categoryItem.getCategoryId())) {
                            addBool = false;
                            break;
                        }
                    }
                    if (addBool) {
                        addCategoryIdList.add(categoryIdListOutputDto.getCategoryId());
                    }
                }

                boolean delBool;

                List<Integer> delCategoryIdList = new ArrayList<>();

                for (RCategoryItem categoryItem : itemSortOrder) {

                    delBool = true;

                    for (GetCategoryIdListOutputDto getCategoryIdListOutputDto : itemCategoryIdList) {
                        if (categoryItem.getCategoryId().equals(getCategoryIdListOutputDto
                            .getCategoryId())) {
                            delBool = false;
                        }
                    }

                    if (delBool) {
                        delCategoryIdList.add(categoryItem.getCategoryId());
                    }
                }

                // 商品カテゴリー関連情報削除
                if (delCategoryIdList.size() != 0) {
                    categoryItemRepository
                        .deleteCategoryItem(inputDto.getStoreId(), itemId, delCategoryIdList);
                }

                // 商品カテゴリー関連情報作成
                List<RCategoryItem> itemCategoryList = new ArrayList<>();

                for (Integer integer : addCategoryIdList) {

                    RCategoryItem itemCategory = new RCategoryItem();
                    itemCategory
                        .setStoreId(inputDto.getStoreId());                              // 店舗ID
                    itemCategory.setCategoryId(integer);                        // カテゴリーID
                    itemCategory
                        .setItemId(itemId);                                              // 商品ID
                    Integer sortOrder = categoryItemRepository
                        .getMaxSortOrder(inputDto.getStoreId(), integer);
                    if (sortOrder == null) {
                        itemCategory
                            .setSortOrder(1);                                            // 順番
                    } else {
                        itemCategory
                            .setSortOrder(sortOrder + 1);                                // 順番
                    }
                    itemCategory
                        .setDelFlag(Flag.OFF.getCode());                                 // 削除フラグ
                    itemCategory
                        .setInsOperCd(userOperCd);                                       // 登録者
                    itemCategory
                        .setInsDateTime(dateTime);                                       // 登録日時
                    itemCategory
                        .setUpdOperCd(userOperCd);                                       // 更新者
                    itemCategory
                        .setUpdDateTime(dateTime);                                       // 更新日時
                    itemCategory
                        .setVersion(0);                                                  // バージョン
                    itemCategoryList.add(itemCategory);
                }
                categoryItemRepository.saveAll(itemCategoryList);
            }

            if (null != itemId) {

                // 商品オプション関連情報削除
                itemOptionRepository.deleteItemOption(inputDto.getStoreId(), itemId);
            }

            // 商品オプション関連情報作成
            List<RItemOption> itemOptionList = new ArrayList<RItemOption>();
            List<RegistItemOptionDto> itemOptionData = inputDto.getItemOptionList();
            for (RegistItemOptionDto itemOptionDatum : itemOptionData) {

                RItemOption itemOption = new RItemOption();
                itemOption.setStoreId(inputDto.getStoreId());                            // 店舗ID
                if (null == itemId) {
                    itemOption.setItemId(getSeqNo.getSeqNo());                           // 商品ID
                } else {
                    itemOption.setItemId(itemId);                                        // 商品ID
                }
                itemOption
                    .setOptionTypeCode(itemOptionDatum.getOptionTypeCd());   // オプション種類コード
                itemOption.setOptionCode(itemOptionDatum.getOptionCode());         // オプションコード
                itemOption
                    .setDiffPrice(itemOptionDatum.getItemOptionDiffPrice()); // 商品オプション価格ギャップ
                itemOption.setDefaultFlags(Flag.OFF.getCode());                          // デフォルトフラグ
                itemOption.setDelFlag(Flag.OFF.getCode());                               // 削除フラグ
                itemOption.setInsOperCd(userOperCd);                                     // 登録者
                itemOption.setInsDateTime(dateTime);                                     // 登録日時
                itemOption.setUpdOperCd(userOperCd);                                     // 更新者
                itemOption.setUpdDateTime(dateTime);                                     // 更新日時
                itemOption.setVersion(0);                                                // バージョン
                itemOptionList.add(itemOption);
            }
            itemOptionRepository.saveAll(itemOptionList);
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.055", (Object) null), ex);
        }
    }

    /**
     * 商品情報削除.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void deleteItem(DeleteItemInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 商品ID取得
        List<Integer> itemIdList = new ArrayList<>();
        for (ItemIdDto itemId : inputDto.getItemList()) {
            itemIdList.add(itemId.getItemId());
        }

        // 指定商品ID削除
        itemRepository.updateDelFlagByItemId(inputDto.getStoreId(), itemIdList, userOperCd,
            DateUtil.getNowDateTime());

        // 指定商品ID削除
        itemImageRepository.updateDelFlagByItemId(inputDto.getStoreId(), itemIdList, userOperCd,
            DateUtil.getNowDateTime());

        // 指定商品ID削除
        itemOptionRepository.deleteByItemIdList(inputDto.getStoreId(), itemIdList);

        // 指定商品ID削除
        categoryItemRepository.deleteByItemIdList(inputDto.getStoreId(), itemIdList);
    }

    /**
     * 登録更新者ID取得.
     */
    private String getUserOperCd() {
        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        return userOperCd;
    }

    /**
     * 商品ステータス更新.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeItemStatus(ChangeItemStatusInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 商品ID取得
        List<Integer> itemIdList = new ArrayList<>();
        for (ItemIdDto itemId : inputDto.getItemList()) {
            itemIdList.add(itemId.getItemId());
        }

        // 指定商品ステータス更新
        itemRepository.updateStatusByItemId(inputDto.getStoreId(), itemIdList,
            inputDto.getSalesClassification(), userOperCd, DateUtil.getNowDateTime());
    }

    /**
     * 商品カテゴリーリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public List<GetItemCategoryOutputDto> getItemCategoryList(GetItemCategoryInputDto inputDto) {

        List<GetItemCategoryOutputDto> itemCategoryList = new ArrayList<>();

        if (null == inputDto.getGradation() && null == inputDto.getParentCategoryId()) {

            // 商品カテゴリーリスト取得
            itemCategoryList = categoryRepository.getItemCategoryList(inputDto.getStoreId(),
                PCConstants.GRADATION);
        } else if (null != inputDto.getGradation() && null == inputDto.getParentCategoryId()) {
            // 商品カテゴリーリスト取得
            itemCategoryList = categoryRepository.getItemCategoryList(inputDto.getStoreId(),
                inputDto.getGradation());
        } else {

            // 商品カテゴリーリスト取得
            itemCategoryList = categoryRepository.getItemCategorySortList(inputDto.getStoreId(),
                inputDto.getGradation(), inputDto.getParentCategoryId());
        }

        itemCategoryList.forEach(categoryData -> {
            categoryData.setItemCategoryName(JSONObject.parseObject(
                categoryData.getItemCategoryName()).getString(inputDto.getLanguages()));
        });

        // コースと放題除外
        List<GetItemCategoryOutputDto> returnCategoryList = new ArrayList<>();
        for (GetItemCategoryOutputDto itemCategory : itemCategoryList) {
            if (ItemCategory.COURSE.getCode().intValue() != itemCategory.getItemCategoryId()
                && ItemCategory.BUFFET.getCode().intValue() != itemCategory.getItemCategoryId()) {
                returnCategoryList.add(itemCategory);
            }
        }

        return returnCategoryList;
    }

    /**
     * カテゴリーの下に商品リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public List<GetCategoryItemOutputDto> getCategoryItemList(GetCategoryItemInputDto inputDto) {

        // カテゴリーの下に商品リスト情報取得
        List<GetCategoryItemOutputDto> itemList = categoryItemRepository
            .getItemList(inputDto.getStoreId(),
                inputDto.getCategoryId());

        itemList.forEach(itemData -> {
            itemData.setItemName(JSONObject.parseObject(
                itemData.getItemName()).getString(inputDto.getLanguages()));
        });
        return itemList;
    }

    /**
     * 商品順番編集.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeItemSortOrder(ChangeItemSortOrderInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // 商品順番関連情報削除
            categoryItemRepository
                .deleteItemSortOrder(inputDto.getStoreId(), inputDto.getCategoryId());

            // 商品カテゴリー関連情報作成
            List<RCategoryItem> itemCategoryList = new ArrayList<RCategoryItem>();
            List<ItemSortOrderDto> itemSortOrderList = inputDto.getItemSortOrderList();
            for (int i = 0; i < itemSortOrderList.size(); i++) {

                RCategoryItem itemCategory = new RCategoryItem();
                itemCategory.setStoreId(inputDto.getStoreId());                              // 店舗ID
                itemCategory.setCategoryId(
                    inputDto.getCategoryId());                        // カテゴリーID
                itemCategory.setItemId(itemSortOrderList.get(i).getItemId());                // 商品ID
                itemCategory.setSortOrder(i + 1);                                            // 順番
                itemCategory
                    .setDelFlag(Flag.OFF.getCode());                                 // 削除フラグ
                itemCategory.setInsOperCd(userOperCd);                                       // 登録者
                itemCategory.setInsDateTime(dateTime);                                       // 登録日時
                itemCategory.setUpdOperCd(userOperCd);                                       // 更新者
                itemCategory.setUpdDateTime(dateTime);                                       // 更新日時
                itemCategory
                    .setVersion(0);                                                  // バージョン
                itemCategoryList.add(itemCategory);
            }
            categoryItemRepository.saveAll(itemCategoryList);
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.063", (Object) null), ex);
        }
    }

    /**
     * コース情報取得.
     *
     * @param inputDto 取得条件
     * @return コース情報
     */
    @Override
    public GetCourseListOutputDto getCourseList(GetCourseListInputDto inputDto, Pageable pageable) {

        GetCourseListOutputDto output = new GetCourseListOutputDto();

        List<GetCourseList> courseList = new ArrayList<>();

        if (inputDto.getCategoryId() != null) {
            List<Map<String, Object>> categoryItems = categoryItemRepository
                .getItemListByItemType(inputDto.getStoreId(), inputDto.getCategoryId(),
                    inputDto.getLanguages(), MstItemType.COURSE.getCode());
            categoryItems.forEach(stringObjectMap -> {
                BuffetDto buffetDto = JSONObject
                    .parseObject(JSONObject.toJSONString(stringObjectMap), BuffetDto.class);
                GetCourseList courseData = new GetCourseList();
                courseData.setCourseId(buffetDto.getBuffetId());
                courseData.setCourseName(buffetDto.getBuffetName());
                courseList.add(courseData);
            });

        } else {

            // 商品情報取得
            Page<Map<String, Object>> courseMap = itemRepository
                .getCourseData(inputDto.getStoreId(),
                    StringUtils.trimToEmpty(inputDto.getCourseStatus()), inputDto.getLanguages(),
                    StringUtils.isEmpty(inputDto.getCourseName()) ? StringUtils.EMPTY
                        : inputDto.getCourseName(), CommonConstants.CODE_GROUP_ITEM_STATUS,
                    ItemCategory.COURSE.getCode(), MstItemType.COURSE.getCode(), pageable);

            // コース情報を設定する
            courseMap.forEach(stringObjectMap -> courseList.add(
                JSONObject
                    .parseObject(JSONObject.toJSONString(stringObjectMap), GetCourseList.class)));
            output.setTotalCount(courseMap.getTotalElements());
        }

        output.setCourseList(courseList);

        return output;
    }

    /**
     * コース明細情報取得.
     *
     * @param inputDto 取得条件
     * @return コースカテゴリー情報
     */
    @Override
    public GetCourseOutputDto getCourseInfo(GetCourseInputDto inputDto) {

        // コースカテゴリー情報取得
        GetCourseOutputDto courseInfo =
            itemRepository.getCourseInfo(inputDto.getStoreId(), inputDto.getCourseId());

        // 検索結果0件の場合
        if (null == courseInfo) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.053", (Object) null));
        }

        return courseInfo;
    }

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public List<ItemListDto> getSelectedItemList(GetCourseInputDto inputDto) {

        // コース商品取得
        List<ItemListDto> courseList = rbuffetItemRepository
            .getCourseItemList(inputDto.getStoreId(), inputDto.getCourseId());

        courseList.forEach(courseData -> {
            courseData.setItemName(JSONObject.parseObject(
                courseData.getItemName()).getString(inputDto.getLanguages()));
            courseData.setCategoryId(null);
        });

        return courseList;
    }

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public List<ItemListDto> getItemList(GetCourseInputDto inputDto) {

        List<String> idList = new ArrayList<>();

        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        // コース商品取得
        List<ItemListDto> courseList = categoryItemRepository
            .getItemList(inputDto.getStoreId(), idList);

        courseList.forEach(courseData -> {
            courseData.setItemName(JSONObject.parseObject(
                courseData.getItemName()).getString(inputDto.getLanguages()));
        });

        return courseList;
    }

    /**
     * カテゴリーリスト取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリーリスト情報
     */
    @Override
    public List<GetCategoryList> getHasItemCategoryList(GetCourseInputDto inputDto) {

        List<String> idList = new ArrayList<>();

        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        List<Map<String, Object>> itemCategoryMap = categoryRepository.getItemCategoryList(
            inputDto.getStoreId(), PCConstants.GRADATION, inputDto.getLanguages(), idList);

        // アウトプットDTO初期化
        List<GetCategoryList> categoryList = new ArrayList<>();

        itemCategoryMap.forEach(stringObjectMap -> categoryList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                GetCategoryList.class)));

        return categoryList;
    }

    /**
     * コース保存.
     *
     * @param inputDto 取得条件
     */
    @Override
    @Transactional
    public void saveCourse(RegistCourseInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // コースID
            Integer courseId = inputDto.getCourseId();

            if (courseId == null) {
                // コースIDのシーケンスNo取得
                GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("m_item"); // テーブル名
                getSeqNoInputDto.setItem("item_id"); // 項目
                getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
                GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);
                // 商品情報作成
                itemRepository.insertCourse(inputDto.getStoreId(), getSeqNo.getSeqNo(),
                    inputDto.getCourseUnitId(), 1,
                    Flag.OFF.getCode().toString(), inputDto.getCourseName(),
                    inputDto.getCoursePrice(),
                    inputDto.getCourseInfo(), ItemShowStatus.OVERHEAD.getCode(),
                    MstItemType.COURSE.getCode(),
                    inputDto.getCourseTaxId(),
                    userOperCd, dateTime);

                if (StringUtils.isNotEmpty(inputDto.getItemLargePicUrl())) {
                    // 商品画像IDのシーケンスNo取得
                    GetSeqNoInputDto getSeqImage = new GetSeqNoInputDto();
                    getSeqImage.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqImage.setTableName("m_item_image"); // テーブル名
                    getSeqImage.setItem("item_image_id"); // 項目
                    getSeqImage.setOperCd(userOperCd); // 登録更新者
                    GetSeqNoOutputDto getSeqImageNo = itemInfoSharedService.getSeqNo(getSeqImage);
                    // 商品画像情報作成
                    MItemImage itemImage = new MItemImage();
                    itemImage.setStoreId(inputDto.getStoreId());                     //　店舗ID
                    itemImage.setItemId(getSeqNo.getSeqNo());                        // 商品ID
                    itemImage.setItemImageId(getSeqImageNo.getSeqNo());              // 商品画像ID
                    itemImage.setImagePath(inputDto.getItemLargePicUrl());           // 画像パス
                    itemImage.setShortImagePath(inputDto.getItemSmallPicUrl());      // ショート画像パス
                    itemImage.setDelFlag(Flag.OFF.getCode());                        // 削除フラグ
                    itemImage.setInsOperCd(userOperCd);                              // 登録者
                    itemImage.setInsDateTime(dateTime);                              // 登録日時
                    itemImage.setUpdOperCd(userOperCd);                              // 更新者
                    itemImage.setUpdDateTime(dateTime);                              // 更新日時
                    itemImage.setVersion(0);                                         // バージョン
                    itemImageRepository.save(itemImage);
                }

                // 商品カテゴリー関連情報作成
                List<RCategoryItem> itemCategoryList = new ArrayList<>();
                List<GetCategoryIdListOutputDto> itemCategoryIdList = inputDto.getCategoryIdList();
                for (GetCategoryIdListOutputDto getCategoryIdListOutputDto : itemCategoryIdList) {

                    RCategoryItem itemCategory = new RCategoryItem();
                    itemCategory
                        .setStoreId(inputDto.getStoreId());                              // 店舗ID
                    itemCategory.setCategoryId(
                        getCategoryIdListOutputDto.getCategoryId());       // カテゴリーID
                    itemCategory
                        .setItemId(getSeqNo.getSeqNo());                                 // 商品ID
                    Integer sortOrder = categoryItemRepository
                        .getMaxSortOrder(inputDto.getStoreId(),
                            getCategoryIdListOutputDto.getCategoryId());
                    if (null == sortOrder) {

                        itemCategory
                            .setSortOrder(1);                                            // 順番
                    } else {

                        itemCategory
                            .setSortOrder(sortOrder + 1);                                // 順番
                    }
                    itemCategory
                        .setDelFlag(Flag.OFF.getCode());                                 // 削除フラグ
                    itemCategory
                        .setInsOperCd(userOperCd);                                       // 登録者
                    itemCategory
                        .setInsDateTime(dateTime);                                       // 登録日時
                    itemCategory
                        .setUpdOperCd(userOperCd);                                       // 更新者
                    itemCategory
                        .setUpdDateTime(dateTime);                                       // 更新日時
                    itemCategory
                        .setVersion(0);                                                  // バージョン
                    itemCategoryList.add(itemCategory);
                }
                categoryItemRepository.saveAll(itemCategoryList);

                // 放題商品選択テーブル情報作成
                List<RBuffetItem> buffetItemList = new ArrayList<>();
                List<ItemListDto> selectedItemList = inputDto.getItemList();
                for (ItemListDto getItemListDto : selectedItemList) {

                    RBuffetItem itemBuffet = new RBuffetItem();
                    itemBuffet.setStoreId(inputDto.getStoreId());            // 店舗ID
                    itemBuffet.setBuffetId(getSeqNo.getSeqNo());            // 放題ID
                    itemBuffet.setItemId(getItemListDto.getItemId());        // 商品ID
                    itemBuffet.setDelFlag(Flag.OFF.getCode());              // 削除フラグ
                    itemBuffet.setInsOperCd(userOperCd);                    // 登録者
                    itemBuffet.setInsDateTime(dateTime);                    // 登録日時
                    itemBuffet.setUpdOperCd(userOperCd);                    // 更新者
                    itemBuffet.setUpdDateTime(dateTime);                    // 更新日時
                    itemBuffet.setVersion(0);                               // バージョン
                    buffetItemList.add(itemBuffet);
                }
                rbuffetItemRepository.saveAll(buffetItemList);

            } else {

                // 商品情報更新(コース用)
                itemRepository
                    .updateCourse(inputDto.getStoreId(), courseId, inputDto.getCourseName(),
                        inputDto.getCoursePrice(), inputDto.getCourseInfo(),
                        inputDto.getCourseTaxId(), inputDto.getCourseUnitId(),
                        userOperCd, dateTime);

                // 商品画像情報更新
                itemImageRepository.deleteItemImage(inputDto.getStoreId(), courseId);
                if (StringUtils.isNotEmpty(inputDto.getItemLargePicUrl())) {
                    // 商品画像IDのシーケンスNo取得
                    GetSeqNoInputDto getSeqImage = new GetSeqNoInputDto();
                    getSeqImage.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqImage.setTableName("m_item_image"); // テーブル名
                    getSeqImage.setItem("item_image_id"); // 項目
                    getSeqImage.setOperCd(userOperCd); // 登録更新者
                    GetSeqNoOutputDto getSeqImageNo = itemInfoSharedService.getSeqNo(getSeqImage);
                    // 商品画像情報作成
                    MItemImage itemImage = new MItemImage();
                    itemImage.setStoreId(inputDto.getStoreId());                     //　店舗ID
                    itemImage.setItemId(courseId);                                   // 商品ID
                    itemImage.setItemImageId(getSeqImageNo.getSeqNo());              // 商品画像ID
                    itemImage.setImagePath(inputDto.getItemLargePicUrl());           // 画像パス
                    itemImage.setShortImagePath(inputDto.getItemSmallPicUrl());      // ショート画像パス
                    itemImage.setDelFlag(Flag.OFF.getCode());                        // 削除フラグ
                    itemImage.setInsOperCd(userOperCd);                              // 登録者
                    itemImage.setInsDateTime(dateTime);                              // 登録日時
                    itemImage.setUpdOperCd(userOperCd);                              // 更新者
                    itemImage.setUpdDateTime(dateTime);                              // 更新日時
                    itemImage.setVersion(0);                                         // バージョン
                    itemImageRepository.save(itemImage);
                }

                // 商品順番情報取得
                List<RCategoryItem> itemSortOrder = categoryItemRepository
                    .findByStoreIdAndItemIdAndDelFlag(
                        inputDto.getStoreId(), courseId, Flag.OFF.getCode());

                List<GetCategoryIdListOutputDto> itemCategoryIdList = inputDto.getCategoryIdList();

                boolean addBool;

                List<Integer> addCategoryIdList = new ArrayList<>();

                for (GetCategoryIdListOutputDto categoryIdListOutputDto : itemCategoryIdList) {

                    addBool = true;

                    for (RCategoryItem categoryItem : itemSortOrder) {

                        if (categoryIdListOutputDto.getCategoryId().equals(categoryItem
                            .getCategoryId())) {
                            addBool = false;
                        }
                    }

                    if (addBool) {

                        addCategoryIdList.add(categoryIdListOutputDto.getCategoryId());
                    }
                }

                boolean delBool;

                List<Integer> delCategoryIdList = new ArrayList<>();

                for (RCategoryItem categoryItem : itemSortOrder) {

                    delBool = true;

                    for (GetCategoryIdListOutputDto getCategoryIdListOutputDto : itemCategoryIdList) {
                        if (categoryItem.getCategoryId() == getCategoryIdListOutputDto
                            .getCategoryId()) {
                            delBool = false;
                        }
                    }

                    if (delBool) {
                        delCategoryIdList.add(categoryItem.getCategoryId());
                    }
                }

                // 商品カテゴリー関連情報削除
                if (delCategoryIdList.size() != 0) {
                    categoryItemRepository
                        .deleteCategoryItem(inputDto.getStoreId(), courseId, delCategoryIdList);
                }

                // 商品カテゴリー関連情報作成
                List<RCategoryItem> itemCategoryList = new ArrayList<>();

                for (Integer integer : addCategoryIdList) {

                    RCategoryItem itemCategory = new RCategoryItem();
                    itemCategory
                        .setStoreId(inputDto.getStoreId());                              // 店舗ID
                    itemCategory.setCategoryId(integer);                        // カテゴリーID
                    itemCategory
                        .setItemId(courseId);                                              // 商品ID
                    Integer sortOrder = categoryItemRepository
                        .getMaxSortOrder(inputDto.getStoreId(), integer);
                    if (sortOrder == null) {
                        itemCategory
                            .setSortOrder(1);                                            // 順番
                    } else {
                        itemCategory
                            .setSortOrder(sortOrder + 1);                                // 順番
                    }
                    itemCategory
                        .setDelFlag(Flag.OFF.getCode());                                 // 削除フラグ
                    itemCategory
                        .setInsOperCd(userOperCd);                                       // 登録者
                    itemCategory
                        .setInsDateTime(dateTime);                                       // 登録日時
                    itemCategory
                        .setUpdOperCd(userOperCd);                                       // 更新者
                    itemCategory
                        .setUpdDateTime(dateTime);                                       // 更新日時
                    itemCategory
                        .setVersion(0);                                                  // バージョン
                    itemCategoryList.add(itemCategory);
                }
                categoryItemRepository.saveAll(itemCategoryList);

                // 放題商品選択テーブル更新開始
                List<Integer> buffetItemIdList = rbuffetItemRepository
                    .getItemIdList(
                        inputDto.getStoreId(), courseId);

                List<ItemListDto> selectedItemList = inputDto.getItemList();

                List<Integer> selectedItemIdList = new ArrayList<>();

                for (ItemListDto itemListDto : selectedItemList) {
                    if (!selectedItemIdList.contains(itemListDto.getItemId())) {
                        selectedItemIdList.add(itemListDto.getItemId());
                    }
                }

                // 放題商品選択テーブル削除
                List<Integer> delItemList = new ArrayList<>();

                for (Integer delItemId : buffetItemIdList) {

                    if (!selectedItemIdList.contains(delItemId)) {
                        delItemList.add(delItemId);
                    }
                }

                if (delItemList.size() != 0) {
                    rbuffetItemRepository
                        .deleteItem(inputDto.getStoreId(), courseId, delItemList);
                }

                // 放題商品選択テーブル登録
                for (Integer insertItemId : selectedItemIdList) {

                    if (!buffetItemIdList.contains(insertItemId)) {
                        RBuffetItem itemBuffet = new RBuffetItem();
                        itemBuffet.setStoreId(inputDto.getStoreId());            // 店舗ID
                        itemBuffet.setBuffetId(courseId);            // 放題ID
                        itemBuffet.setItemId(insertItemId);                        // 商品ID
                        itemBuffet.setDelFlag(Flag.OFF.getCode());              // 削除フラグ
                        itemBuffet.setInsOperCd(userOperCd);                    // 登録者
                        itemBuffet.setInsDateTime(dateTime);                    // 登録日時
                        itemBuffet.setUpdOperCd(userOperCd);                    // 更新者
                        itemBuffet.setUpdDateTime(dateTime);                    // 更新日時
                        itemBuffet.setVersion(0);                               // バージョン
                        rbuffetItemRepository.save(itemBuffet);
                    }
                }
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.055", (Object) null), ex);
        }

    }

    /**
     * コース情報削除.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void delCourse(CourseDelInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // コースID取得
        List<Integer> courseIdList = new ArrayList<>();
        for (CourseInputDto courseId : inputDto.getCourseList()) {
            courseIdList.add(courseId.getCourseId());
        }

        // コースID削除(商品マスタ)
        itemRepository.updateDelFlagByItemId(inputDto.getStoreId(), courseIdList, userOperCd,
            DateUtil.getNowDateTime());

        // コースID削除(商品画像マスタ)
        itemImageRepository.updateDelFlagByItemId(inputDto.getStoreId(), courseIdList, userOperCd,
            DateUtil.getNowDateTime());

        // コースID削除(カテゴリ商品関連テーブル)
        categoryItemRepository.deleteByItemIdList(inputDto.getStoreId(), courseIdList);

        // コースID削除(放題商品選択テーブル)
        rbuffetItemRepository
            .updateDelFlagByBuffetId(inputDto.getStoreId(), courseIdList, userOperCd,
                DateUtil.getNowDateTime());

    }

    /**
     * コース商品ステータス更新.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeCourseItemStatus(ChangeCourseItemStatusInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 商品ID取得
        List<Integer> courseItemIdList = new ArrayList<>();
        for (CourseIdDto courseItemId : inputDto.getCourseList()) {
            courseItemIdList.add(courseItemId.getCourseId());
        }

        // 指定商品ステータス更新
        itemRepository.updateStatusByItemId(inputDto.getStoreId(), courseItemIdList,
            inputDto.getSalesClassification(), userOperCd, DateUtil.getNowDateTime());
    }

    /**
     * コース商品順番編集.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeCourseItemSortOrder(ChangeCourseItemSortOrderInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            List<Integer> delCategoryIdList = new ArrayList<>();

            for (ItemSortOrderDto buffetDto : inputDto.getCourseItemSortOrderList()) {

                if (!delCategoryIdList.contains(buffetDto.getItemId())) {
                    delCategoryIdList.add(buffetDto.getItemId());
                }
            }

            // コース商品順番関連情報削除
            categoryItemRepository
                .deleteBuffetItemSortOrder(inputDto.getStoreId(), inputDto.getCategoryId(),
                    delCategoryIdList);

            // 商品カテゴリー関連情報作成
            List<RCategoryItem> itemCategoryList = new ArrayList<RCategoryItem>();
            List<ItemSortOrderDto> courseItemSortOrderList = inputDto.getCourseItemSortOrderList();
            for (int i = 0; i < courseItemSortOrderList.size(); i++) {

                RCategoryItem itemCategory = new RCategoryItem();
                itemCategory.setStoreId(inputDto.getStoreId());                              // 店舗ID
                itemCategory.setCategoryId(
                    inputDto
                        .getCategoryId());                                                 // カテゴリーID
                itemCategory.setItemId(courseItemSortOrderList.get(i).getItemId());          // 商品ID
                itemCategory.setSortOrder(i + 1);                                            // 順番
                itemCategory
                    .setDelFlag(
                        Flag.OFF.getCode());                                         // 削除フラグ
                itemCategory.setInsOperCd(userOperCd);                                       // 登録者
                itemCategory.setInsDateTime(dateTime);                                       // 登録日時
                itemCategory.setUpdOperCd(userOperCd);                                       // 更新者
                itemCategory.setUpdDateTime(dateTime);                                       // 更新日時
                itemCategory
                    .setVersion(
                        0);                                                          // バージョン
                itemCategoryList.add(itemCategory);
            }
            categoryItemRepository.saveAll(itemCategoryList);
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.063", (Object) null), ex);
        }
    }

    /**
     * 出前区分リスト取得.
     *
     * @param inputDto 取得条件
     * @return 出前区分リスト情報
     */
    @Override
    public List<GetDeliveryFlagListOutputDto> getDeliveryFlagList(GetItemInputDto inputDto) {

        // 出前区分リストを取得する
        List<Map<String, Object>> deliveryTypeFlagList = codeRepository
            .findDeliveryFlagInfo(inputDto.getStoreId(), CommonConstants.CODE_GROUP_DELIVERY_FLAG);

        // 出前区分リストを変換する
        List<GetDeliveryFlagListOutputDto> deliveryTypeFlagDtoList = new ArrayList<>();
        deliveryTypeFlagList.forEach(stringObjectMap -> deliveryTypeFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    GetDeliveryFlagListOutputDto.class)));

        return deliveryTypeFlagDtoList;

    }

    /**
     * 出前仕方リスト取得.
     *
     * @param inputDto 取得条件
     * @return 出前仕方リスト
     */
    @Override
    public List<GetCateringTypeFlagListOutputDto> getCateringTypeFlagList(
        GetItemInputDto inputDto) {

        // 出前仕方フラグ情報を取得する
        List<Map<String, Object>> deliveryTypeFlagList = codeRepository
            .findCateringTypeInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_TYPE_FLAG);

        // 出前仕方フラグ情報を変換する
        List<GetCateringTypeFlagListOutputDto> deliveryTypeFlagDtoList = new ArrayList<>();
        deliveryTypeFlagList.forEach(stringObjectMap -> deliveryTypeFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    GetCateringTypeFlagListOutputDto.class)));

        return deliveryTypeFlagDtoList;
    }


}
