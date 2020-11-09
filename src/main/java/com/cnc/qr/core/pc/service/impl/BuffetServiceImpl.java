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
import com.cnc.qr.common.entity.RItem;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCategoryRepository;
import com.cnc.qr.common.repository.MItemImageRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.RBuffetItemRepository;
import com.cnc.qr.common.repository.RCategoryItemRepository;
import com.cnc.qr.common.repository.RItemRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.BuffetDelInputDto;
import com.cnc.qr.core.pc.model.BuffetDto;
import com.cnc.qr.core.pc.model.BuffetIdDto;
import com.cnc.qr.core.pc.model.BuffetInputDto;
import com.cnc.qr.core.pc.model.BuffetListInputDto;
import com.cnc.qr.core.pc.model.BuffetListOutputDto;
import com.cnc.qr.core.pc.model.ChangeBuffetItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeBuffetItemStatusInputDto;
import com.cnc.qr.core.pc.model.GetBuffetInputDto;
import com.cnc.qr.core.pc.model.GetBuffetOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.ItemListDto;
import com.cnc.qr.core.pc.model.ItemSortOrderDto;
import com.cnc.qr.core.pc.model.RegistBuffetInputDto;
import com.cnc.qr.core.pc.service.BuffetService;
import com.cnc.qr.security.until.SecurityUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * のみ放題サービス実装クラス.
 */
@Service
@Transactional
public class BuffetServiceImpl implements BuffetService {

    /**
     * 共通部品サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * 商品画像情報共有サービス.
     */
    @Autowired
    private MItemImageRepository itemImageRepository;

    /**
     * 放題商品選択テーブルリポジトリ.
     */
    @Autowired
    private RBuffetItemRepository buffetItemRepository;

    /**
     * カテゴリ商品関連リポジトリ.
     */
    @Autowired
    private RCategoryItemRepository categoryItemRepository;

    /**
     * カテゴリーマスタリポジトリ.
     */
    @Autowired
    private MCategoryRepository categoryRepository;

    /**
     * 関連商品テーブルリポジトリ.
     */
    @Autowired
    private RItemRepository relationItemRepository;

    /**
     * のみ放題情報取得.
     *
     * @param inputDto 取得条件
     * @return のみ放題情報
     */
    @Override
    public BuffetListOutputDto getBuffetList(BuffetListInputDto inputDto, Pageable pageable) {

        // のみ放題情報を設定する
        List<BuffetDto> buffetList = new ArrayList<>();

        BuffetListOutputDto output = new BuffetListOutputDto();

        if (inputDto.getCategoryId() != null) {
            List<Map<String, Object>> categoryItems = categoryItemRepository
                .getItemListByItemType(inputDto.getStoreId(), inputDto.getCategoryId(),
                    inputDto.getLanguages(), MstItemType.BUFFET.getCode());
            categoryItems.forEach(stringObjectMap -> buffetList.add(
                JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), BuffetDto.class)));

        } else {

            // のみ放題情報取得
            Page<Map<String, Object>> buffetMap = itemRepository
                .getBuffetData(inputDto.getStoreId(),
                    StringUtils.trimToEmpty(inputDto.getBuffetStatus()), inputDto.getLanguages(),
                    StringUtils.isEmpty(inputDto.getBuffetName()) ? StringUtils.EMPTY
                        : inputDto.getBuffetName(), CommonConstants.CODE_GROUP_ITEM_STATUS,
                    ItemCategory.BUFFET.getCode(), MstItemType.BUFFET.getCode(), pageable);

            buffetMap.getContent().forEach(stringObjectMap -> buffetList.add(
                JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), BuffetDto.class)));

            output.setTotalCount(buffetMap.getTotalElements());
        }

        output.setBuffetList(buffetList);

        return output;
    }

    /**
     * 飲み放題明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 飲み放題明細情報
     */
    @Override
    public GetBuffetOutputDto getBuffet(GetBuffetInputDto inputDto) {

        // 飲み放題明細情報取得
        GetBuffetOutputDto buffetInfo =
            itemRepository.getBuffetInfo(inputDto.getStoreId(), inputDto.getBuffetId());

        // 検索結果0件の場合
        if (null == buffetInfo) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.053", (Object) null));
        }

        return buffetInfo;
    }

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public List<ItemListDto> getSelectedItemList(GetBuffetInputDto inputDto) {

        // コース商品取得
        List<ItemListDto> courseList = buffetItemRepository
            .getBuffetItemList(inputDto.getStoreId(), inputDto.getBuffetId());

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
    public List<ItemListDto> getItemList(GetBuffetInputDto inputDto) {

        List<String> idList = new ArrayList<>();

        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        // 飲み放題商品取得
        List<ItemListDto> buffetList = categoryItemRepository
            .getItemList(inputDto.getStoreId(), idList);

        buffetList.forEach(courseData -> {
            courseData.setItemName(JSONObject.parseObject(
                courseData.getItemName()).getString(inputDto.getLanguages()));
        });

        return buffetList;
    }

    /**
     * カテゴリーリスト取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリーリスト情報
     */
    @Override
    public List<GetCategoryList> getHasItemCategoryList(GetBuffetInputDto inputDto) {

        List<String> idList = new ArrayList<>();

        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        List<Map<String, Object>> itemCategoryMap = categoryRepository.getItemCategoryList(
            inputDto.getStoreId(), PCConstants.GRADATION, inputDto.getLanguages(), idList);

        // アウトプットDTO初期化
        List<GetCategoryList> categoryList = new ArrayList<GetCategoryList>();

        itemCategoryMap.forEach(stringObjectMap -> categoryList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                GetCategoryList.class)));

        return categoryList;
    }


    /**
     * 飲み放題保存.
     *
     * @param inputDto 取得条件
     */
    @Override
    @Transactional
    public void saveBuffet(RegistBuffetInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            // 放題ID
            Integer buffetId = inputDto.getBuffetId();

            if (buffetId == null) {
                // 放題IDのシーケンスNo取得
                GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("m_item"); // テーブル名
                getSeqNoInputDto.setItem("item_id"); // 項目
                getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
                GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                // 商品情報作成
                itemRepository.insertBuffet(inputDto.getStoreId(), getSeqNo.getSeqNo(),
                    Flag.OFF.getCode().toString(), inputDto.getBuffetName(),
                    inputDto.getBuffetPrice(), 1008, 1,
                    inputDto.getBuffetInfo(), ItemShowStatus.OVERHEAD.getCode(),
                    MstItemType.BUFFET.getCode(),
                    inputDto.getBuffetTaxId(),
                    inputDto.getBuffetTime(),
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
                List<ItemListDto> selectedItemList = inputDto.getSelectedItemList();
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
                buffetItemRepository.saveAll(buffetItemList);

                if (null != inputDto.getAttachItemId()) {

                    // 商品関連テーブル情報作成
                    RItem item = new RItem();
                    item.setStoreId(inputDto.getStoreId());                     // 店舗ID
                    item.setBuffetId(getSeqNo.getSeqNo());                      // 放題ID
                    item.setItemId(inputDto.getAttachItemId());                 // 商品ID
                    item.setDelFlag(Flag.OFF.getCode());                        // 削除フラグ
                    item.setInsOperCd(userOperCd);                              // 登録者
                    item.setInsDateTime(dateTime);                              // 登録日時
                    item.setUpdOperCd(userOperCd);                              // 更新者
                    item.setUpdDateTime(dateTime);                              // 更新日時
                    item.setVersion(0);                                         // バージョン
                    relationItemRepository.save(item);
                }

            } else {

                // 商品情報更新(飲み放題用)
                itemRepository
                    .updateBuffet(inputDto.getStoreId(), buffetId, inputDto.getBuffetName(),
                        inputDto.getBuffetPrice(), inputDto.getBuffetInfo(),
                        inputDto.getBuffetTaxId(), inputDto.getBuffetTime(),
                        userOperCd, dateTime);

                // 商品画像情報更新
                itemImageRepository.deleteItemImage(inputDto.getStoreId(), buffetId);
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
                    itemImage.setItemId(buffetId);                        // 商品ID
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
                        inputDto.getStoreId(), buffetId, Flag.OFF.getCode());

                List<GetCategoryIdListOutputDto> itemCategoryIdList = inputDto.getCategoryIdList();

                boolean addBool;

                List<Integer> addCategoryIdList = new ArrayList<>();

                for (GetCategoryIdListOutputDto categoryIdListOutputDto : itemCategoryIdList) {

                    addBool = true;

                    for (RCategoryItem categoryItem : itemSortOrder) {

                        if (categoryIdListOutputDto.getCategoryId()
                            .equals(categoryItem.getCategoryId())) {
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
                        .deleteCategoryItem(inputDto.getStoreId(), buffetId, delCategoryIdList);
                }

                // 商品カテゴリー関連情報作成
                List<RCategoryItem> itemCategoryList = new ArrayList<>();

                for (Integer integer : addCategoryIdList) {

                    RCategoryItem itemCategory = new RCategoryItem();
                    itemCategory
                        .setStoreId(inputDto.getStoreId());                              // 店舗ID
                    itemCategory.setCategoryId(integer);                        // カテゴリーID
                    itemCategory
                        .setItemId(buffetId);                                              // 商品ID
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
                List<Integer> buffetItemIdList = buffetItemRepository
                    .getItemIdList(
                        inputDto.getStoreId(), buffetId);

                List<ItemListDto> selectedItemList = inputDto.getSelectedItemList();

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
                    buffetItemRepository
                        .deleteItem(inputDto.getStoreId(), buffetId, delItemList);
                }

                // 放題商品選択テーブル登録
                for (Integer insertItemId : selectedItemIdList) {

                    if (!buffetItemIdList.contains(insertItemId)) {
                        RBuffetItem itemBuffet = new RBuffetItem();
                        itemBuffet.setStoreId(inputDto.getStoreId());            // 店舗ID
                        itemBuffet.setBuffetId(buffetId);            // 放題ID
                        itemBuffet.setItemId(insertItemId);                        // 商品ID
                        itemBuffet.setDelFlag(Flag.OFF.getCode());              // 削除フラグ
                        itemBuffet.setInsOperCd(userOperCd);                    // 登録者
                        itemBuffet.setInsDateTime(dateTime);                    // 登録日時
                        itemBuffet.setUpdOperCd(userOperCd);                    // 更新者
                        itemBuffet.setUpdDateTime(dateTime);                    // 更新日時
                        itemBuffet.setVersion(0);                               // バージョン
                        buffetItemRepository.save(itemBuffet);
                    }
                }

                if (null != inputDto.getAttachItemId()) {

                    // 商品関連テーブル情報更新
                    relationItemRepository.updateBuffetItem(inputDto.getStoreId(),
                        buffetId, inputDto.getAttachItemId(),
                        userOperCd, dateTime);
                } else {

                    // 商品関連テーブル情報削除
                    relationItemRepository
                        .deleteByStoreIdAndBuffetId(inputDto.getStoreId(), buffetId);
                }
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.055", (Object) null), ex);
        }

    }

    /**
     * 飲み放題情報削除.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void delBuffet(BuffetDelInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 放題ID取得
        List<Integer> buffetIdList = new ArrayList<>();
        for (BuffetInputDto buffetId : inputDto.getBuffetList()) {
            buffetIdList.add(buffetId.getBuffetId());
        }

        // 指定商品ID削除(商品マスタ)
        itemRepository.updateDelFlagByItemId(inputDto.getStoreId(), buffetIdList, userOperCd,
            DateUtil.getNowDateTime());

        // 指定商品ID削除(商品画像マスタ)
        itemImageRepository.updateDelFlagByItemId(inputDto.getStoreId(), buffetIdList, userOperCd,
            DateUtil.getNowDateTime());

        // 指定商品ID削除(カテゴリ商品関連テーブル)
        categoryItemRepository.deleteByItemIdList(inputDto.getStoreId(), buffetIdList);

        // 指定商品ID削除(放題商品選択テーブル)
        buffetItemRepository
            .updateDelFlagByBuffetId(inputDto.getStoreId(), buffetIdList, userOperCd,
                DateUtil.getNowDateTime());

        // 指定商品ID削除(商品関連テーブル)
        relationItemRepository
            .updateDelFlagByBuffetId(inputDto.getStoreId(), buffetIdList, userOperCd,
                DateUtil.getNowDateTime());
    }

    /**
     * 飲み放題商品ステータス更新.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeBuffetItemStatus(ChangeBuffetItemStatusInputDto inputDto) {

        // 登録更新者
        String userOperCd = getUserOperCd();

        // 商品ID取得
        List<Integer> buffetItemIdList = new ArrayList<>();
        for (BuffetIdDto buffetItemId : inputDto.getBuffetList()) {
            buffetItemIdList.add(buffetItemId.getBuffetId());
        }

        // 指定商品ステータス更新
        itemRepository.updateStatusByItemId(inputDto.getStoreId(), buffetItemIdList,
            inputDto.getSalesClassification(), userOperCd, DateUtil.getNowDateTime());
    }

    /**
     * 飲み放題商品順番編集.
     *
     * @param inputDto 商品情報
     */
    @Override
    @Transactional
    public void changeBuffetItemSortOrder(ChangeBuffetItemSortOrderInputDto inputDto) {

        try {

            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // ユーザID取得
            String userOperCd = getUserOperCd();

            List<Integer> delCategoryIdList = new ArrayList<>();

            for (ItemSortOrderDto buffetDto : inputDto.getBuffetItemSortOrderList()) {

                if (!delCategoryIdList.contains(buffetDto.getItemId())) {
                    delCategoryIdList.add(buffetDto.getItemId());
                }
            }

            // 飲み放題商品順番関連情報削除
            categoryItemRepository
                .deleteBuffetItemSortOrder(inputDto.getStoreId(), inputDto.getCategoryId(),
                    delCategoryIdList);

            // 商品カテゴリー関連情報作成
            List<RCategoryItem> itemCategoryList = new ArrayList<RCategoryItem>();
            List<ItemSortOrderDto> buffetItemSortOrderList = inputDto.getBuffetItemSortOrderList();
            for (int i = 0; i < buffetItemSortOrderList.size(); i++) {

                RCategoryItem itemCategory = new RCategoryItem();
                itemCategory.setStoreId(inputDto.getStoreId());                              // 店舗ID
                itemCategory.setCategoryId(
                    inputDto
                        .getCategoryId());                                                 // カテゴリーID
                itemCategory.setItemId(buffetItemSortOrderList.get(i).getItemId());          // 商品ID
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
}
