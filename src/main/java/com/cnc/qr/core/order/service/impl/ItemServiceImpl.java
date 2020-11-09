package com.cnc.qr.core.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.BuffetType;
import com.cnc.qr.common.constants.CodeConstants.DeliveryTypeFlag;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemBuffetType;
import com.cnc.qr.common.constants.CodeConstants.ItemShowStatus;
import com.cnc.qr.common.constants.CodeConstants.ItemStatus;
import com.cnc.qr.common.constants.CodeConstants.MenuShowType;
import com.cnc.qr.common.constants.CodeConstants.MstItemType;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.CodeConstants.UrlSource;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.common.entity.MItem;
import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.common.repository.MCategoryRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MSpeechPhrasesRepository;
import com.cnc.qr.common.repository.OOrderDetailsRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OReceivablesRepository;
import com.cnc.qr.common.shared.model.GetItemOptionInfoInputDto;
import com.cnc.qr.common.shared.model.GetItemOptionInfoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.BuffetListDto;
import com.cnc.qr.core.order.model.CheckOrderItemInputDto;
import com.cnc.qr.core.order.model.CheckOrderItemOutputDto;
import com.cnc.qr.core.order.model.DeliveryCategoryListDto;
import com.cnc.qr.core.order.model.GetBuffetInputDto;
import com.cnc.qr.core.order.model.GetBuffetListInputDto;
import com.cnc.qr.core.order.model.GetBuffetListOutputDto;
import com.cnc.qr.core.order.model.GetBuffetOutputDto;
import com.cnc.qr.core.order.model.GetCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryCategoryInputDto;
import com.cnc.qr.core.order.model.GetDeliveryCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryItemDto;
import com.cnc.qr.core.order.model.GetDeliveryItemInputDto;
import com.cnc.qr.core.order.model.GetItemBySpeechDto;
import com.cnc.qr.core.order.model.GetItemBySpeechInputDto;
import com.cnc.qr.core.order.model.GetItemBySpeechOutputDto;
import com.cnc.qr.core.order.model.GetItemCategoryInputDto;
import com.cnc.qr.core.order.model.GetItemDto;
import com.cnc.qr.core.order.model.GetItemInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeOutputDto;
import com.cnc.qr.core.order.model.GetItemOutputDto;
import com.cnc.qr.core.order.model.GetTextBySpeechInputDto;
import com.cnc.qr.core.order.model.GetTextBySpeechOutputDto;
import com.cnc.qr.core.order.model.ItemCategoryListDto;
import com.cnc.qr.core.order.service.ItemService;
import com.github.dozermapper.core.Mapper;
import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognizeRequest;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;
import com.worksap.nlp.sudachi.Dictionary;
import com.worksap.nlp.sudachi.DictionaryFactory;
import com.worksap.nlp.sudachi.Morpheme;
import com.worksap.nlp.sudachi.Tokenizer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品情報取得サービス実装クラス.
 */
@Service
public class ItemServiceImpl implements ItemService {

    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * カテゴリマスタリポジトリ.
     */
    @Autowired
    private MCategoryRepository categoryRepository;

    /**
     * 注文サマリリポジトリ.
     */
    @Autowired
    private OOrderSummaryRepository orderSummaryRepository;

    /**
     * 受付リポジトリ.
     */
    @Autowired
    private OReceivablesRepository receivablesRepository;

    /**
     * 注文詳細リポジトリ.
     */
    @Autowired
    private OOrderDetailsRepository orderDetailsRepository;

    /**
     * 音声認識マスタリポジトリ.
     */
    @Autowired
    private MSpeechPhrasesRepository speechPhrasesRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

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
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public Page<GetItemDto> getItem(GetItemInputDto inputDto, Pageable pageable) {

        Page<Map<String, Object>> itemMap;

        // 放題表示フラグを初期化する
        Integer buffetDisplayFlag = Flag.OFF.getCode();

        // 店員スマホの場合
        if (TerminalDistinction.STMB.getCode()
            .equals(inputDto.getTerminalDistinction())) {
            buffetDisplayFlag = Flag.ON.getCode();
            // 客先スマホの場合
        } else if (TerminalDistinction.CSMB.getCode()
            .equals(inputDto.getTerminalDistinction())) {
            buffetDisplayFlag = Flag.ON.getCode();
        }

        // 放題商品が各カテゴリに表示する場合
        if (buffetDisplayFlag == Flag.OFF.getCode()) {
            // 放題IDがNULLである場合
            if (Objects.isNull(inputDto.getBuffetId())) {
                // 受付IDが空白であり、或いはNULLである場合
                if ("".equals(inputDto.getReceivablesId()) || Objects
                    .isNull(inputDto.getReceivablesId())) {
                    List<String> idList = new ArrayList<>();

                    // 客用スマホである場合
                    if (TerminalDistinction.CSMB.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        // 店員スマホである場合
                    } else if (TerminalDistinction.STMB.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        idList.add(MstItemType.COURSE.getCode());
                        idList.add(MstItemType.BUFFET.getCode());
                    }

                    // 商品情報取得
                    itemMap = itemRepository
                        .getItem(inputDto.getStoreId(), inputDto.getCategoryId(),
                            inputDto.getLanguages(),
                            StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                : inputDto.getSearchInfo(), ZonedDateTime.now(), pageable, idList,
                            ItemShowStatus.OVERHEAD.getCode());
                    // 上記以外の場合
                } else {
                    // 放題商品情報件数を取得する
                    Integer buffetCount = itemRepository
                        .getItemByReceivableBuffetCount(inputDto.getStoreId(),
                            inputDto.getLanguages(),
                            StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                : inputDto.getSearchInfo(), inputDto.getReceivablesId());

                    // 放題商品が存在する場合
                    if (buffetCount > 0) {
                        List<String> idList = new ArrayList<>();

                        // 店員スマホである場合
                        if (TerminalDistinction.STMB.getCode()
                            .equals(inputDto.getTerminalDistinction())) {
                            idList.add(MstItemType.USUALLY.getCode());
                            idList.add(MstItemType.SETMEAL.getCode());
                            idList.add(MstItemType.COURSE.getCode());
                            idList.add(MstItemType.BUFFET.getCode());
                        }

                        // 商品情報取得
                        itemMap = itemRepository
                            .getItemByReceivableBuffetAndNormal(inputDto.getStoreId(),
                                inputDto.getCategoryId(),
                                inputDto.getLanguages(),
                                StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                    : inputDto.getSearchInfo(), ZonedDateTime.now(),
                                inputDto.getReceivablesId(),
                                idList, pageable,
                                ItemShowStatus.OVERHEAD.getCode());
                        // 上記以外の場合
                    } else {
                        List<String> idList = new ArrayList<>();

                        // 店員スマホである場合
                        if (TerminalDistinction.STMB.getCode()
                            .equals(inputDto.getTerminalDistinction())) {
                            idList.add(MstItemType.USUALLY.getCode());
                            idList.add(MstItemType.SETMEAL.getCode());
                            idList.add(MstItemType.COURSE.getCode());
                            idList.add(MstItemType.BUFFET.getCode());
                        }

                        // 商品情報取得
                        itemMap = itemRepository
                            .getItem(inputDto.getStoreId(), inputDto.getCategoryId(),
                                inputDto.getLanguages(),
                                StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                    : inputDto.getSearchInfo(), ZonedDateTime.now(), pageable,
                                idList, ItemShowStatus.OVERHEAD.getCode());
                    }
                }
                // 上記以外の場合
            } else {
                List<String> idList = new ArrayList<>();

                // 客用スマホである場合
                if (TerminalDistinction.CSMB.getCode().equals(inputDto.getTerminalDistinction())) {
                    idList.add(MstItemType.USUALLY.getCode());
                    idList.add(MstItemType.SETMEAL.getCode());
                    // 店員スマホである場合
                } else if (TerminalDistinction.STMB.getCode()
                    .equals(inputDto.getTerminalDistinction())) {
                    idList.add(MstItemType.USUALLY.getCode());
                    idList.add(MstItemType.SETMEAL.getCode());
                    idList.add(MstItemType.COURSE.getCode());
                    idList.add(MstItemType.BUFFET.getCode());
                }

                // 商品情報取得
                itemMap = itemRepository
                    .getItemBuffetAndNormal(inputDto.getStoreId(), inputDto.getCategoryId(),
                        inputDto.getLanguages(),
                        StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                            : inputDto.getSearchInfo(), ZonedDateTime.now(), inputDto.getBuffetId(),
                        idList, pageable, ItemShowStatus.OVERHEAD.getCode());
            }
            // 放題商品が一番上のカテゴリに表示する場合
        } else {
            // 放題IDがNULLである場合
            if (CollectionUtils.isEmpty(inputDto.getBuffetIdList()) && !Objects
                .equals(Flag.ON.getCode().toString(), inputDto.getCourseFlag())) {
                //　受付IDが空白であり、或いはNULLである場合
                if ("".equals(inputDto.getReceivablesId()) || Objects
                    .isNull(inputDto.getReceivablesId())) {
                    List<String> idList = new ArrayList<>();

                    // 客用スマホである場合
                    if (TerminalDistinction.CSMB.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        // 店員スマホである場合
                    } else if (TerminalDistinction.STMB.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        idList.add(MstItemType.COURSE.getCode());
                        idList.add(MstItemType.BUFFET.getCode());
                    }

                    // 商品情報取得
                    itemMap = itemRepository
                        .getItem(inputDto.getStoreId(), inputDto.getCategoryId(),
                            inputDto.getLanguages(),
                            StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                : inputDto.getSearchInfo(), ZonedDateTime.now(), pageable, idList,
                            ItemShowStatus.OVERHEAD.getCode());
                    // 上記以外の場合
                } else {
                    // 放題商品情報件数を取得する
                    Integer buffetCount = itemRepository
                        .getItemByReceivableBuffetCount(inputDto.getStoreId(),
                            inputDto.getLanguages(),
                            StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                : inputDto.getSearchInfo(), inputDto.getReceivablesId());

                    // 放題商品が存在する場合
                    if (buffetCount > 0) {
                        // 放題商品を取得する場合
                        if (inputDto.getCategoryId() == 0) {
                            // 商品情報取得
                            itemMap = itemRepository
                                .getItemByReceivableBuffet(inputDto.getStoreId(),
                                    inputDto.getLanguages(),
                                    StringUtils.isEmpty(inputDto.getSearchInfo())
                                        ? StringUtils.EMPTY
                                        : inputDto.getSearchInfo(), ZonedDateTime.now(),
                                    inputDto.getReceivablesId(),
                                    pageable);
                            // 上記以外の場合
                        } else {
                            List<String> idList = new ArrayList<>();

                            // 店員スマホである場合
                            if (TerminalDistinction.STMB.getCode()
                                .equals(inputDto.getTerminalDistinction())) {
                                idList.add(MstItemType.USUALLY.getCode());
                                idList.add(MstItemType.SETMEAL.getCode());
                                idList.add(MstItemType.COURSE.getCode());
                                idList.add(MstItemType.BUFFET.getCode());
                            }

                            // 商品情報取得
                            itemMap = itemRepository
                                .getItemByReceivableNormal(inputDto.getStoreId(),
                                    inputDto.getCategoryId(),
                                    inputDto.getLanguages(),
                                    StringUtils.isEmpty(inputDto.getSearchInfo())
                                        ? StringUtils.EMPTY
                                        : inputDto.getSearchInfo(), ZonedDateTime.now(),
                                    inputDto.getReceivablesId(), idList, pageable,
                                    ItemShowStatus.OVERHEAD.getCode());
                        }
                        // 上記以外の場合
                    } else {
                        List<String> idList = new ArrayList<>();

                        // 店員スマホである場合
                        if (TerminalDistinction.STMB.getCode()
                            .equals(inputDto.getTerminalDistinction())) {
                            idList.add(MstItemType.USUALLY.getCode());
                            idList.add(MstItemType.SETMEAL.getCode());
                            idList.add(MstItemType.COURSE.getCode());
                            idList.add(MstItemType.BUFFET.getCode());
                        }

                        // 商品情報取得
                        itemMap = itemRepository
                            .getItem(inputDto.getStoreId(), inputDto.getCategoryId(),
                                inputDto.getLanguages(),
                                StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                    : inputDto.getSearchInfo(), ZonedDateTime.now(), pageable,
                                idList, ItemShowStatus.OVERHEAD.getCode());
                    }
                }
                // コースの場合
            } else if (Objects.equals(Flag.ON.getCode().toString(), inputDto.getCourseFlag())) {

                List<String> idList = new ArrayList<>();
                idList.add(MstItemType.USUALLY.getCode());
                idList.add(MstItemType.SETMEAL.getCode());

                // 商品情報取得
                itemMap = itemRepository
                    .getCourseItem(inputDto.getStoreId(), inputDto.getCategoryId(),
                        inputDto.getLanguages(),
                        StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                            : inputDto.getSearchInfo(), pageable, idList,
                        ItemShowStatus.OVERHEAD.getCode());

                // 上記以外の場合
            } else {
                // 放題商品を取得する場合
                if (inputDto.getBuffetId() != null) {
                    // 商品情報取得
                    itemMap = itemRepository
                        .getItemBuffet(inputDto.getStoreId(), inputDto.getLanguages(),
                            StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                : inputDto.getSearchInfo(), ZonedDateTime.now(),
                            inputDto.getBuffetId(), pageable,
                            ItemShowStatus.OVERHEAD.getCode());
                    // 上記以外の場合
                } else {
                    List<String> idList = new ArrayList<>();

                    // 客用スマホである場合
                    if (TerminalDistinction.CSMB.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        // 店員スマホである場合
                    } else if (TerminalDistinction.STMB.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        idList.add(MstItemType.COURSE.getCode());
                        idList.add(MstItemType.BUFFET.getCode());
                    }

                    // 商品情報取得
                    itemMap = itemRepository
                        .getItemNormal(inputDto.getStoreId(), inputDto.getCategoryId(),
                            inputDto.getLanguages(),
                            StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                                : inputDto.getSearchInfo(), ZonedDateTime.now(),
                            inputDto.getBuffetIdList(), idList, pageable,
                            ItemShowStatus.OVERHEAD.getCode());
                }
            }
        }

        // 商品名称を設定する
        List<GetItemDto> itemList = new ArrayList<>();
        itemMap.forEach(stringObjectMap -> itemList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetItemDto.class)));
        return new PageImpl<>(itemList, itemMap.getPageable(), itemMap.getTotalPages());
    }

    /**
     * 商品カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public GetCategoryListOutputDto getItemCategoryList(GetItemCategoryInputDto inputDto) {

        List<String> idList = new ArrayList<>();

        // 会計タブレットである場合
        if (TerminalDistinction.CSMB.getCode().equals(inputDto.getTerminalDistinction())) {
            idList.add(MstItemType.USUALLY.getCode());
            idList.add(MstItemType.SETMEAL.getCode());
        } else {
            idList.add(MstItemType.USUALLY.getCode());
            idList.add(MstItemType.SETMEAL.getCode());
            idList.add(MstItemType.COURSE.getCode());
            idList.add(MstItemType.BUFFET.getCode());
        }

        // 商品カテゴリー情報取得
        GetCategoryListOutputDto outDto = new GetCategoryListOutputDto();

        List<Map<String, Object>> itemCategoryMap = categoryRepository.getHasItemCategoryList(
            inputDto.getStoreId(), inputDto.getGradation(), inputDto.getLanguages(), idList,
            ItemShowStatus.OVERHEAD.getCode());

        // アウトプットDTO初期化
        List<ItemCategoryListDto> itemCategoryList = new ArrayList<>();

        itemCategoryMap.forEach(stringObjectMap -> itemCategoryList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                ItemCategoryListDto.class)));

        if (UrlSource.TOP.getCode().equals(inputDto.getItemGetType())) {
            // アウトプットDTO初期化
            List<ItemCategoryListDto> categoryList = new ArrayList<>();

            // 受付IDある場合
            if (StringUtils.isNotEmpty(inputDto.getReceivablesId())) {
                // コース商品取得
                List<Map<String, Object>> courseInfo = itemRepository
                    .getCourseInfoByReceivablesId(inputDto.getStoreId(),
                        inputDto.getReceivablesId(),
                        inputDto.getLanguages(), MstItemType.COURSE.getCode());

                List<Integer> courseIdList = new ArrayList<>();

                // データが存在の場合
                if (CollectionUtils.isNotEmpty(courseInfo)) {
                    List<GetItemDto> courseItemInfo = new ArrayList<>();
                    courseInfo.forEach(stringObjectMap -> courseItemInfo.add(
                        JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                            GetItemDto.class)));
                    courseItemInfo.forEach(getItemDto -> {
                        if (StringUtils.isNotEmpty(getItemDto.getItemId())) {
                            ItemCategoryListDto itemCategoryListDto = new ItemCategoryListDto();
                            itemCategoryListDto.setItemCategoryId(getItemDto.getItemId());
                            itemCategoryListDto.setItemCategoryName(getItemDto.getItemInfo());
                            itemCategoryListDto.setCourseFlag(Flag.ON.getCode().toString());
                            categoryList.add(itemCategoryListDto);
                            courseIdList.add(Integer.valueOf(getItemDto.getItemId()));
                        }
                    });
                }

                // 放題商品取得
                List<Map<String, Object>> buffetInfo = itemRepository
                    .getCourseInfoByReceivablesId(inputDto.getStoreId(),
                        inputDto.getReceivablesId(),
                        inputDto.getLanguages(), MstItemType.BUFFET.getCode());

                List<Integer> buffetIdList = new ArrayList<>();

                // データが存在の場合
                if (CollectionUtils.isNotEmpty(buffetInfo)) {

                    List<GetItemDto> buffetItemInfo = new ArrayList<>();
                    buffetInfo.forEach(stringObjectMap -> buffetItemInfo.add(
                        JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                            GetItemDto.class)));

                    buffetItemInfo.forEach(getItemDto -> {
                        if (StringUtils.isNotEmpty(getItemDto.getItemId())) {
                            buffetIdList.add(Integer.valueOf(getItemDto.getItemId()));
                        }
                    });

                    List<Map<String, Object>> buffetItemCategoryMap = categoryRepository
                        .getBuffetItemCategoryList(inputDto.getStoreId(), inputDto.getGradation(),
                            buffetIdList, idList);

                    if (CollectionUtils.isNotEmpty(buffetItemCategoryMap)) {
                        List<ItemCategoryListDto> buffetItemCategoryList = new ArrayList<>();
                        buffetItemCategoryMap.forEach(stringObjectMap -> buffetItemCategoryList.add(
                            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                                ItemCategoryListDto.class)));
                        itemCategoryList.forEach(itemCategoryListDto -> {
                            Optional<ItemCategoryListDto> filterBuffet = buffetItemCategoryList
                                .stream()
                                .filter(
                                    itemCategoryListDto1 -> itemCategoryListDto1.getItemCategoryId()
                                        .equals(itemCategoryListDto.getItemCategoryId()))
                                .findFirst();
                            filterBuffet.ifPresent(categoryListDto -> itemCategoryListDto
                                .setBuffetFlag(categoryListDto.getBuffetFlag()));
                        });
                    }

                    // 放题コース商品取得
                    List<Map<String, Object>> buffetCourseInfo = itemRepository
                        .getBuffetInfoByStoreId(inputDto.getStoreId(), buffetIdList, courseIdList,
                            inputDto.getLanguages());

                    // 店商品カテゴリー情報
                    if (CollectionUtils.isNotEmpty(buffetCourseInfo)) {
                        List<GetItemDto> itemInfo = new ArrayList<>();
                        buffetCourseInfo.forEach(stringObjectMap -> itemInfo.add(
                            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                                GetItemDto.class)));
                        itemInfo.forEach(getItemDto -> {
                            if (StringUtils.isNotEmpty(getItemDto.getItemId())) {
                                ItemCategoryListDto itemCategoryListDto = new ItemCategoryListDto();
                                itemCategoryListDto.setItemCategoryId(getItemDto.getItemId());
                                itemCategoryListDto.setItemCategoryName(getItemDto.getItemName());
                                itemCategoryListDto.setCourseFlag(Flag.ON.getCode().toString());
                                categoryList.add(itemCategoryListDto);
                            }
                        });
                    }

                    // 放题一件以上の場合
                    List<Integer> itemIdList = buffetIdList;
                    if (CollectionUtils.isNotEmpty(buffetIdList) && 1 < buffetIdList.size()) {
                        itemIdList = itemRepository
                            .getBuffetItemByStoreId(inputDto.getStoreId(), buffetIdList);
                    }
                    for (GetItemDto itemDto : buffetItemInfo) {
                        if (StringUtils.isNotEmpty(itemDto.getItemId())) {
                            if (itemIdList.contains(Integer.valueOf(itemDto.getItemId()))) {
                                ItemCategoryListDto itemCategoryListDto = new ItemCategoryListDto();
                                itemCategoryListDto.setItemCategoryId(itemDto.getItemId());
                                itemCategoryListDto.setItemCategoryName(itemDto.getItemInfo());
                                itemCategoryListDto
                                    .setBuffetFlag(BuffetType.CATEGORY_SELECTION.getCode());
                                categoryList.add(itemCategoryListDto);
                            }
                        }
                    }
                }

                if (buffetIdList.size() == 0 && courseIdList.size() == 0) {
                    outDto.setItemListType(MenuShowType.SingleITEM.getCode());
                } else {
                    outDto.setItemListType(MenuShowType.BUFFETCOURSE.getCode());
                }
            }

            for (ItemCategoryListDto info : itemCategoryList) {
                if (Objects.equals(BuffetType.MULTIPLE_SELECTION.getCode(), info.getBuffetFlag())) {
                    continue;
                }
                ItemCategoryListDto categoryData = new ItemCategoryListDto();
                categoryData.setItemCategoryId(info.getItemCategoryId());
                categoryData.setItemCategoryName(info.getItemCategoryName());
                categoryList.add(categoryData);
            }

            outDto.setItemCategoryList(categoryList);
        } else {

            outDto.setItemCategoryList(itemCategoryList);
            outDto.setItemListType(MenuShowType.SingleITEM.getCode());
        }

        return outDto;
    }

    /**
     * 商品オプション種類情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public GetItemOptionTypeOutputDto getItemOptionType(GetItemOptionTypeInputDto inputDto) {

        // 検索条件を設定する
        GetItemOptionInfoInputDto itemOptionInfoInputDto = beanMapper
            .map(inputDto, GetItemOptionInfoInputDto.class);

        // 商品オプション類型情報取得
        GetItemOptionInfoOutputDto outputDto = itemInfoSharedService
            .getItemOptionInfo(itemOptionInfoInputDto);

        // インプット情報をDTOにセットする
        return beanMapper.map(outputDto, GetItemOptionTypeOutputDto.class);
    }

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public GetItemOutputDto getItemList(GetItemInputDto inputDto) {

        List<Map<String, Object>> itemList;

        // 放題表示フラグを初期化する
        Integer buffetDisplayFlag = Flag.OFF.getCode();

        // 会計タブレットの場合
        if (TerminalDistinction.STPD.getCode()
            .equals(inputDto.getTerminalDistinction())) {
            buffetDisplayFlag = Flag.ON.getCode();
        }

        // 放題商品が各カテゴリに表示する場合
        if (buffetDisplayFlag == Flag.OFF.getCode()) {
            // 放題IDがNULLである場合
            if (Objects.isNull(inputDto.getBuffetId())) {
                // 受付IDが空白である場合
                if ("".equals(inputDto.getReceivablesId())) {
                    List<String> idList = new ArrayList<>();

                    // 会計タブレットである場合
                    if (TerminalDistinction.STPD.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        idList.add(MstItemType.COURSE.getCode());
                        idList.add(MstItemType.BUFFET.getCode());
                    }

                    // 商品情報取得
                    itemList = itemRepository
                        .getItemList(inputDto.getStoreId(), inputDto.getCategoryId(),
                            inputDto.getLanguages(), idList, ItemShowStatus.OVERHEAD.getCode());
                    // 上記以外の場合
                } else {
                    // 放題商品情報件数を取得する
                    Integer buffetCount = itemRepository
                        .getItemByReceivableBuffetListCount(inputDto.getStoreId(),
                            inputDto.getReceivablesId());

                    // 放題商品が存在する場合
                    if (buffetCount > 0) {
                        List<String> idList = new ArrayList<>();

                        // 会計タブレットである場合
                        if (TerminalDistinction.STPD.getCode()
                            .equals(inputDto.getTerminalDistinction())) {
                            idList.add(MstItemType.USUALLY.getCode());
                            idList.add(MstItemType.SETMEAL.getCode());
                            idList.add(MstItemType.COURSE.getCode());
                            idList.add(MstItemType.BUFFET.getCode());
                        }

                        // 商品情報取得
                        itemList = itemRepository
                            .getItemByReceivableBuffetUnionNormalList(inputDto.getStoreId(),
                                inputDto.getCategoryId(),
                                inputDto.getLanguages(), inputDto.getReceivablesId(), idList,
                                ItemShowStatus.OVERHEAD.getCode());
                        //　上記以外の場合
                    } else {
                        List<String> idList = new ArrayList<>();

                        // 会計タブレットである場合
                        if (TerminalDistinction.STPD.getCode()
                            .equals(inputDto.getTerminalDistinction())) {
                            idList.add(MstItemType.USUALLY.getCode());
                            idList.add(MstItemType.SETMEAL.getCode());
                            idList.add(MstItemType.COURSE.getCode());
                            idList.add(MstItemType.BUFFET.getCode());
                        }

                        // 商品情報取得
                        itemList = itemRepository
                            .getItemList(inputDto.getStoreId(), inputDto.getCategoryId(),
                                inputDto.getLanguages(), idList, ItemShowStatus.OVERHEAD.getCode());
                    }
                }
                // 上記以外の場合
            } else {
                List<String> idList = new ArrayList<>();

                // 会計タブレットである場合
                if (TerminalDistinction.STPD.getCode().equals(inputDto.getTerminalDistinction())) {
                    idList.add(MstItemType.USUALLY.getCode());
                    idList.add(MstItemType.SETMEAL.getCode());
                    idList.add(MstItemType.COURSE.getCode());
                    idList.add(MstItemType.BUFFET.getCode());
                }

                // 商品情報取得
                itemList = itemRepository
                    .getItemBuffetAndNormalList(inputDto.getStoreId(), inputDto.getCategoryId(),
                        inputDto.getLanguages(), inputDto.getBuffetId(), idList,
                        ItemShowStatus.OVERHEAD.getCode());
            }
            // 放題商品が一番上のカテゴリに表示する場合
        } else {
            // 放題IDがNULLである場合
            if (CollectionUtils.isEmpty(inputDto.getBuffetIdList())) {
                // 受付IDが空白である場合
                if ("".equals(inputDto.getReceivablesId())) {
                    List<String> idList = new ArrayList<>();

                    // 会計タブレットである場合
                    if (TerminalDistinction.STPD.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        idList.add(MstItemType.COURSE.getCode());
                        idList.add(MstItemType.BUFFET.getCode());
                    }

                    // 商品情報取得
                    itemList = itemRepository
                        .getItemList(inputDto.getStoreId(), inputDto.getCategoryId(),
                            inputDto.getLanguages(), idList, ItemShowStatus.OVERHEAD.getCode());
                    // 上記以外の場合
                } else {
                    // 放題商品情報件数を取得する
                    Integer buffetCount = itemRepository
                        .getItemByReceivableBuffetListCount(inputDto.getStoreId(),
                            inputDto.getReceivablesId());

                    // 放題商品が存在する場合
                    if (buffetCount > 0) {
                        // 放題商品を取得する場合
                        if (inputDto.getCategoryId() == 0) {
                            // 商品情報取得
                            itemList = itemRepository
                                .getItemByReceivableBuffetList(inputDto.getStoreId(),
                                    inputDto.getLanguages(),
                                    inputDto.getReceivablesId(), ItemShowStatus.OVERHEAD.getCode());
                            // 上記以外の場合
                        } else {
                            List<String> idList = new ArrayList<>();

                            // 会計タブレットである場合
                            if (TerminalDistinction.STPD.getCode()
                                .equals(inputDto.getTerminalDistinction())) {
                                idList.add(MstItemType.USUALLY.getCode());
                                idList.add(MstItemType.SETMEAL.getCode());
                                idList.add(MstItemType.COURSE.getCode());
                                idList.add(MstItemType.BUFFET.getCode());
                            }

                            // 商品情報取得
                            itemList = itemRepository
                                .getItemByReceivableNormalList(inputDto.getStoreId(),
                                    inputDto.getCategoryId(),
                                    inputDto.getLanguages(), inputDto.getReceivablesId(), idList,
                                    ItemShowStatus.OVERHEAD.getCode());
                        }
                        // 上記以外の場合
                    } else {
                        List<String> idList = new ArrayList<>();

                        // 会計タブレットである場合
                        if (TerminalDistinction.STPD.getCode()
                            .equals(inputDto.getTerminalDistinction())) {
                            idList.add(MstItemType.USUALLY.getCode());
                            idList.add(MstItemType.SETMEAL.getCode());
                            idList.add(MstItemType.COURSE.getCode());
                            idList.add(MstItemType.BUFFET.getCode());
                        }

                        // 商品情報取得
                        itemList = itemRepository
                            .getItemList(inputDto.getStoreId(), inputDto.getCategoryId(),
                                inputDto.getLanguages(), idList, ItemShowStatus.OVERHEAD.getCode());
                    }
                }
                // 上記以外の場合
            } else {
                // 放題商品を取得する場合
                if (inputDto.getBuffetId() != null) {
                    // 商品情報取得
                    itemList = itemRepository
                        .getItemBuffetList(inputDto.getStoreId(), inputDto.getLanguages(),
                            inputDto.getBuffetId(), ItemShowStatus.OVERHEAD.getCode());
                    // 上記以外の場合
                } else {
                    List<String> idList = new ArrayList<>();

                    // 会計タブレットである場合
                    if (TerminalDistinction.STPD.getCode()
                        .equals(inputDto.getTerminalDistinction())) {
                        idList.add(MstItemType.USUALLY.getCode());
                        idList.add(MstItemType.SETMEAL.getCode());
                        idList.add(MstItemType.COURSE.getCode());
                        idList.add(MstItemType.BUFFET.getCode());
                    }

                    // 商品情報取得
                    itemList = itemRepository
                        .getItemNormalList(inputDto.getStoreId(), inputDto.getCategoryId(),
                            inputDto.getLanguages(), inputDto.getBuffetIdList(), idList,
                            ItemShowStatus.OVERHEAD.getCode());
                }
            }
        }

        // 結果DTO初期化
        GetItemOutputDto outDto = new GetItemOutputDto();

        List<GetItemDto> items = new ArrayList<>();
        itemList.forEach(stringObjectMap -> items.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetItemDto.class)));

        outDto.setItems(items);

        return outDto;
    }

    /**
     * 放題一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 放題一覧情報
     */
    @Override
    public GetBuffetListOutputDto getBuffetList(GetBuffetListInputDto inputDto) {

        GetBuffetListOutputDto outputDto = new GetBuffetListOutputDto();

        // 放題リスト取得
        List<BuffetListDto> buffetList = itemRepository.getBuffetList(inputDto.getStoreId(),
            MstItemType.BUFFET.getCode(), ItemShowStatus.OVERHEAD.getCode());

        buffetList.forEach(buffetData -> {
            buffetData.setBuffetName(JSONObject.parseObject(
                buffetData.getBuffetName()).getString(inputDto.getLanguages()));
        });

        outputDto.setBuffetList(buffetList);

        return outputDto;
    }

    /**
     * 放題明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 放題明細情報
     */
    @Override
    public GetBuffetOutputDto getBuffetInfo(GetBuffetInputDto inputDto) {

        GetBuffetOutputDto outputDto = new GetBuffetOutputDto();

        // 受付ID
        String receivablesId = inputDto.getReceivablesId();

        // 放题ID
        Integer buffetId = inputDto.getBuffetId();

        if (null == buffetId) {
            return getStorePadBuffetInfo(inputDto);
        } else {

            Integer count = orderSummaryRepository.getOrderItemCount(inputDto.getStoreId(),
                receivablesId);

            // 放题明细
            MItem buffetInfo = itemRepository
                .findByStoreIdAndItemIdAndDelFlag(inputDto.getStoreId(),
                    buffetId, Flag.OFF.getCode());

            if (count == 1) {

                // 放题份数
                Integer buffetCount = receivablesRepository.findByStoreIdAndDelFlagAndReceivablesId(
                    inputDto.getStoreId(), Flag.OFF.getCode(), receivablesId).getCustomerCount();

                outputDto.setBuffetId(buffetInfo.getItemId());
                outputDto.setBuffetName(JSONObject.parseObject(
                    buffetInfo.getItemName()).getString(inputDto.getLanguages()));
                outputDto.setBuffetAmount(buffetInfo.getItemPrice());
                outputDto.setBuffetCount(buffetCount);

                // 剩余时间
                outputDto.setBuffetRemainTime(buffetInfo.getBuffetTime());
            } else {

                // 注文时间
                Timestamp orderTime = orderDetailsRepository.getOrderTime(inputDto.getStoreId(),
                    receivablesId, buffetId);

                LocalDateTime localDateTimeNoTimeZone = orderTime.toLocalDateTime();

                ZonedDateTime zonedOrderTime = localDateTimeNoTimeZone.atZone(
                    ZoneId.of(CommonConstants.TIMEZONE_TOKYO));

                // 剩余时间
                long timeDiff = Duration.between(DateUtil.getNowDateTime(),
                    zonedOrderTime.plusMinutes(buffetInfo.getBuffetTime())).getSeconds();

                outputDto.setBuffetRemainTime(Math.max((int) timeDiff / 60, 0));
            }
        }

        return outputDto;
    }

    /**
     * 放題明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 放題明細情報
     */
    @Override
    public GetBuffetOutputDto getStorePadBuffetInfo(GetBuffetInputDto inputDto) {

        GetBuffetOutputDto outputDto = new GetBuffetOutputDto();

        // 受付ID
        String receivablesId = inputDto.getReceivablesId();

        // サービス処理を実行する
        List<Integer> orderBuffetId = orderSummaryRepository.getOrderBuffetId(inputDto.getStoreId(),
            receivablesId, MstItemType.BUFFET.getCode());

        if (CollectionUtils.isNotEmpty(orderBuffetId)) {

            // 放题ID
            Integer buffetId = orderBuffetId.get(0);

            // 放题明细
            MItem buffetInfo = itemRepository
                .findByStoreIdAndItemIdAndDelFlag(inputDto.getStoreId(),
                    buffetId, Flag.OFF.getCode());

            if (StringUtils.isEmpty(receivablesId) || Objects.isNull(buffetId)) {

                outputDto.setBuffetId(buffetInfo.getItemId());
                outputDto.setBuffetName(JSONObject.parseObject(
                    buffetInfo.getItemName()).getString(inputDto.getLanguages()));
                outputDto.setBuffetAmount(buffetInfo.getItemPrice());

                // 剩余时间
                outputDto.setBuffetRemainTime(buffetInfo.getBuffetTime());
            } else {

                // 注文时间
                Timestamp orderTime = orderDetailsRepository.getOrderTime(inputDto.getStoreId(),
                    receivablesId, buffetId);

                long timeDiff = buffetInfo.getBuffetTime();

                if (orderTime != null) {
                    LocalDateTime localDateTimeNoTimeZone = orderTime.toLocalDateTime();

                    ZonedDateTime zonedOrderTime = localDateTimeNoTimeZone.atZone(
                        ZoneId.of(CommonConstants.TIMEZONE_TOKYO));

                    // 剩余时间
                    timeDiff = Duration.between(DateUtil.getNowDateTime(),
                        zonedOrderTime.plusMinutes(buffetInfo.getBuffetTime())).getSeconds();

                    outputDto.setBuffetRemainTime(Math.max((int) timeDiff / 60, 0));
                } else {
                    outputDto.setBuffetRemainTime(Math.max((int) timeDiff, 0));
                }
            }
        }

        // 受付テーブル情報取得
        OReceivables receivables = receivablesRepository
            .findByStoreIdAndDelFlagAndReceivablesId(inputDto.getStoreId(), Flag.OFF.getCode(),
                receivablesId);
        if (receivables != null) {
            outputDto.setCustomerCount(receivables.getCustomerCount());
        }

        return outputDto;
    }

    /**
     * 出前商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public Page<GetDeliveryItemDto> getDeliveryItemList(GetDeliveryItemInputDto inputDto,
        Pageable pageable) {

        List<String> idList = new ArrayList<>();
        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        List<String> deliveryTypeFlagList = new ArrayList<>();
        deliveryTypeFlagList.add(DeliveryTypeFlag.TAKE_OUT.getCode());
        if (DeliveryTypeFlag.TAKE_OUT.getCode().equals(inputDto.getDeliveryTypeFlag())) {
            deliveryTypeFlagList.add(DeliveryTypeFlag.CATERING.getCode());
        }

        Page<Map<String, Object>> itemMap = itemRepository
            .getDeliveryItem(inputDto.getStoreId(), inputDto.getCategoryId(),
                inputDto.getLanguages(),
                StringUtils.isEmpty(inputDto.getSearchInfo()) ? StringUtils.EMPTY
                    : inputDto.getSearchInfo(), deliveryTypeFlagList,
                Flag.ON.getCode().toString(),
                ZonedDateTime.now(), pageable, idList);

        // 商品名称を設定する
        List<GetDeliveryItemDto> itemList = new ArrayList<>();
        itemMap.forEach(stringObjectMap -> itemList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetDeliveryItemDto.class)));
        return new PageImpl<>(itemList, itemMap.getPageable(), itemMap.getTotalPages());
    }

    /**
     * 出前商品カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    @Override
    public GetDeliveryCategoryListOutputDto getDeliveryCategoryList(
        GetDeliveryCategoryInputDto inputDto) {

        List<String> deliveryTypeFlagList = new ArrayList<>();
        deliveryTypeFlagList.add(DeliveryTypeFlag.TAKE_OUT.getCode());
        if (DeliveryTypeFlag.CATERING.getCode().equals(inputDto.getDeliveryTypeFlag())) {
            deliveryTypeFlagList.add(DeliveryTypeFlag.CATERING.getCode());
        }

        List<String> idList = new ArrayList<>();
        // 会計タブレットである場合
        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        List<Map<String, Object>> itemCategoryMap = categoryRepository
            .getHasDeliveryItemCategoryList(
                inputDto.getStoreId(), inputDto.getGradation(), inputDto.getLanguages(),
                deliveryTypeFlagList, Flag.ON.getCode().toString(), idList);

        // アウトプットDTO初期化
        List<DeliveryCategoryListDto> itemCategoryList = new ArrayList<>();

        itemCategoryMap.forEach(stringObjectMap -> itemCategoryList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                DeliveryCategoryListDto.class)));

        // 商品カテゴリー情報取得
        GetDeliveryCategoryListOutputDto outDto = new GetDeliveryCategoryListOutputDto();
        outDto.setItemCategoryList(itemCategoryList);

        return outDto;
    }

    /**
     * 音声認識文字列取得.
     *
     * @param inputDto 取得条件
     * @return 文字列情報
     */
    @Override
    public GetTextBySpeechOutputDto getTextBySpeech(GetTextBySpeechInputDto inputDto) {

        // 音声認識文字列取得
        GetTextBySpeechOutputDto outDto = new GetTextBySpeechOutputDto();

        // 検索内容がnullの場合
        if (inputDto.getFile() == null || inputDto.getFile().isEmpty()) {
            return outDto;
        }

        // 音声認識の実行
        String transcript = streamingRecognizeFile(inputDto.getFile(), inputDto.getStoreId(),
            inputDto.getLanguages());
        if (StringUtils.isEmpty(transcript)) {
            return outDto;
        }
        // 音声認識結果設定
        outDto.setTranscript(transcript);

        return outDto;
    }

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    @Override
    public GetItemBySpeechOutputDto getItemBySpeech(GetItemBySpeechInputDto inputDto) {

        // 商品カテゴリー情報取得
        GetItemBySpeechOutputDto outDto = new GetItemBySpeechOutputDto();
        List<GetItemBySpeechDto> itemList = new ArrayList<>();
        outDto.setTranscript(inputDto.getSearchInfo());
        outDto.setItems(itemList);

        // 検索内容がnullの場合
        if (StringUtils.isEmpty(inputDto.getSearchInfo())) {
            return outDto;
        }

        // 0～9の数字のマップ
        Map<String, String> numMap = new HashMap<>();
        numMap.put("一", "1");
        numMap.put("二", "2");
        numMap.put("三", "3");
        numMap.put("四", "4");
        numMap.put("五", "5");
        numMap.put("六", "6");
        numMap.put("七", "7");
        numMap.put("八", "8");
        numMap.put("九", "9");
        numMap.put("十", "10");

        // 検索内容編集
        List<String> searchKeyList = new ArrayList<>();
        Map<String, String> itemCountMap = new HashMap<>();

        // 正規表現のパターンを作成
        Pattern p = Pattern.compile(RegexConstants.NUMERIC_VALIDATION);

        // 日本語
        if (Objects.equals("ja_JP", inputDto.getLanguages())) {
            try (Dictionary dict = new DictionaryFactory()
                .create(env.getProperty("qr.env.sudachi.resourcesPath"),
                    readAll(env.getProperty("qr.env.sudachi.settingsPath")), true)) {
                Tokenizer tokenizer = dict.create();
                List<Morpheme> tokens = tokenizer
                    .tokenize(Tokenizer.SplitMode.C, inputDto.getSearchInfo());
                List<Morpheme> nounTokens = new ArrayList<>();
                for (Morpheme token : tokens) {
                    if (CommonConstants.SPEECH_NOUN.equals(token.partOfSpeech().get(0))) {
                        nounTokens.add(token);
                    }
                }
                if (CollectionUtils.isNotEmpty(nounTokens)) {
                    for (int i = 0; i < nounTokens.size(); i++) {
                        Morpheme morpheme = nounTokens.get(i);
                        if (CommonConstants.SPEECH_NUMERAL.equals(morpheme.partOfSpeech().get(1))) {
                            if (0 < i) {
                                Morpheme checkMorpheme = nounTokens.get(i - 1);
                                if (CommonConstants.SPEECH_PROPER_NOUN
                                    .equals(checkMorpheme.partOfSpeech().get(1))
                                    || CommonConstants.SPEECH_COMMON_NOUN
                                    .equals(checkMorpheme.partOfSpeech().get(1))) {
                                    if (p.matcher(morpheme.normalizedForm()).find()) {
                                        itemCountMap
                                            .put(checkMorpheme.surface(),
                                                morpheme.normalizedForm());
                                    } else if (numMap.containsKey(morpheme.normalizedForm())) {
                                        itemCountMap
                                            .put(checkMorpheme.surface(),
                                                numMap.get(morpheme.normalizedForm()));
                                    }
                                }
                            }
                        } else if (
                            CommonConstants.SPEECH_PROPER_NOUN
                                .equals(morpheme.partOfSpeech().get(1))
                                || CommonConstants.SPEECH_COMMON_NOUN
                                .equals(morpheme.partOfSpeech().get(1))) {
                            searchKeyList.add(morpheme.surface());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 中国語
        } else if (Objects.equals("zh_CN", inputDto.getLanguages())) {
            Result parse = ToAnalysis.parse(inputDto.getSearchInfo());
            if (CollectionUtils.isNotEmpty(parse.getTerms())) {
                for (int i = 0; i < parse.getTerms().size(); i++) {
                    Term term = parse.getTerms().get(i);
                    if (Objects
                        .equals(CommonConstants.SPEECH_CHINESE_COMMON_NOUN, term.getNatureStr())) {
                        searchKeyList.add(term.getName());
                    } else if (term.getNatureStr()
                        .startsWith(CommonConstants.SPEECH_CHINESE_NUMERAL)) {
                        if (0 < i) {
                            Term checkTerm = parse.getTerms().get(i - 1);
                            if (Objects.equals(CommonConstants.SPEECH_CHINESE_COMMON_NOUN,
                                checkTerm.getNatureStr())) {
                                String numberStr = term.getName();
                                if (1 < numberStr.length()) {
                                    String lastNumberStr = numberStr
                                        .substring(numberStr.length() - 1);
                                    if (!p.matcher(lastNumberStr).find() && !numMap
                                        .containsKey(lastNumberStr)) {
                                        numberStr = numberStr.substring(0, numberStr.length() - 1);
                                    }
                                }
                                if (p.matcher(numberStr).find()) {
                                    itemCountMap.put(checkTerm.getName(), numberStr);
                                } else if (numMap.containsKey(numberStr)) {
                                    itemCountMap.put(checkTerm.getName(), numMap.get(numberStr));
                                }
                            }
                        }
                    }
                }
            }
        }

        // 検索条件
        String searchInfo;
        if (CollectionUtils.isNotEmpty(searchKeyList)) {
            searchInfo =
                "%(" + StringUtils.join(searchKeyList, CommonConstants.SPEECH_OR_WHERE) + ")%";
            // 同義語情報取得
            List<String> synonyms = itemRepository.getSynonyms(inputDto.getStoreId(), searchInfo);
            if (CollectionUtils.isNotEmpty(synonyms)) {
                searchInfo = "%(" + StringUtils.join(searchKeyList, CommonConstants.SPEECH_OR_WHERE)
                    + CommonConstants.SPEECH_OR_WHERE + StringUtils
                    .join(synonyms, CommonConstants.SPEECH_OR_WHERE) + ")%";
            }
        } else {
            return outDto;
        }

        // 客用スマホである場合
        List<String> idList = new ArrayList<>();
        idList.add(MstItemType.USUALLY.getCode());
        idList.add(MstItemType.SETMEAL.getCode());

        // 商品情報取得
        List<Map<String, Object>> itemMap = itemRepository
            .getItemBySpeechWithSql(inputDto.getStoreId(), inputDto.getLanguages(),
                searchInfo, ZonedDateTime.now(), idList, inputDto.getReceivablesId(),
                MstItemType.BUFFET.getCode());

        // 商品名称を設定する
        itemMap.forEach(stringObjectMap -> itemList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetItemBySpeechDto.class)));

        // キーワードラベル
        if (CollectionUtils.isNotEmpty(itemList) && 1 < itemList.size()) {

            // 第一キーワード対応
            Optional<GetItemBySpeechDto> firstKeyItem = itemList.stream().filter(
                getItemBySpeechDto -> getItemBySpeechDto.getItemName()
                    .equals(searchKeyList.get(0))).findFirst();
            firstKeyItem.ifPresent(getItemBySpeechDto -> getItemBySpeechDto.setSortOrder("9"));

            // 第二キーワード対応
            if (1 < searchKeyList.size()) {
                Optional<GetItemBySpeechDto> sendKeyItem = itemList.stream().filter(
                    getItemBySpeechDto -> getItemBySpeechDto.getItemName()
                        .contains(searchKeyList.get(0) + searchKeyList.get(1))).findFirst();
                sendKeyItem.ifPresent(getItemBySpeechDto -> getItemBySpeechDto.setSortOrder("8"));
            }

            // 第三キーワード対応
            if (2 < searchKeyList.size()) {
                Optional<GetItemBySpeechDto> threeKeyItem = itemList.stream().filter(
                    getItemBySpeechDto -> getItemBySpeechDto.getItemName()
                        .contains(
                            searchKeyList.get(0) + searchKeyList.get(1) + searchKeyList.get(2)))
                    .findFirst();
                threeKeyItem.ifPresent(getItemBySpeechDto -> getItemBySpeechDto.setSortOrder("7"));
            }

            // 再ソート
            List<GetItemBySpeechDto> sortedItemList = itemList.stream()
                .sorted(Comparator.comparing(GetItemBySpeechDto::getSortOrder,
                    Comparator.nullsLast(String::compareTo)).reversed()
                    .thenComparing(GetItemBySpeechDto::getItemCategoryId,
                        Comparator.nullsLast(String::compareTo))
                    .thenComparing(GetItemBySpeechDto::getItemId,
                        Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
            if (0 < itemCountMap.size()) {
                itemCountMap.forEach((key, val) -> {
                    Optional<GetItemBySpeechDto> item = sortedItemList.stream()
                        .filter(
                            getItemBySpeechDto -> getItemBySpeechDto.getItemName().contains(key))
                        .findFirst();
                    item.ifPresent(getItemBySpeechDto -> getItemBySpeechDto.setItemCount(val));
                });
            }
            outDto.setItems(sortedItemList);
        } else {
            // 検索結果1件以上
            if (CollectionUtils.isNotEmpty(itemList) && 0 < itemCountMap.size()) {
                itemCountMap.forEach((key, val) -> {
                    Optional<GetItemBySpeechDto> item = itemList.stream()
                        .filter(
                            getItemBySpeechDto -> getItemBySpeechDto.getItemName().contains(key))
                        .findFirst();
                    item.ifPresent(getItemBySpeechDto -> getItemBySpeechDto.setItemCount(val));
                });
            }
        }

        return outDto;
    }

    /**
     * 辞書情報取得.
     *
     * @param input 辞書ファイル
     * @return 辞書情報
     */
    private String readAll(String input) {
        try (InputStreamReader isReader = new InputStreamReader(new FileInputStream(input),
            StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isReader)) {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 音声認識の実行.
     *
     * @param file 音声ファイル
     * @return 認識結果
     */
    private String streamingRecognizeFile(MultipartFile file, String storeId, String languageCode) {

        // Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
        try (SpeechClient speechClient = SpeechClient.create()) {
            byte[] data = file.getBytes();
            // SpeechContext: to configure your speech_context see:
            // https://cloud.google.com/speech-to-text/docs/reference/rpc/google.cloud.speech.v1#speechcontext
            // Full list of supported phrases (class tokens) here:
            // https://cloud.google.com/speech-to-text/docs/class-tokens
            // List<String> phrases = speechPhrasesRepository.findSpeechPhrases(storeId);
            // SpeechContext speechContext = SpeechContext.newBuilder().addAllPhrases(phrases).build();
            // Encoding of audio data sent. This sample sets this explicitly.
            // This field is optional for FLAC and WAV audio formats.
            RecognitionConfig.AudioEncoding encoding = RecognitionConfig.AudioEncoding.LINEAR16;
            RecognitionConfig config =
                RecognitionConfig.newBuilder()
                    .setLanguageCode(languageCode)
                    .setSampleRateHertz(16000)
                    .setEncoding(encoding)
                    .setModel("command_and_search")
                    .build();
            ByteString content = ByteString.copyFrom(data);
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(content).build();
            RecognizeRequest request =
                RecognizeRequest.newBuilder().setConfig(config).setAudio(audio).build();
            RecognizeResponse response = speechClient.recognize(request);
            for (SpeechRecognitionResult result : response.getResultsList()) {
                // First alternative is the most probable result
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                return StringUtils.trimToEmpty(alternative.getTranscript());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * チェック注文商品.
     *
     * @param inputDto 取得条件
     * @return チェック結果
     */
    @Override
    public CheckOrderItemOutputDto checkOrderItem(CheckOrderItemInputDto inputDto) {

        List<String> idList = new ArrayList<String>();
        idList.add(MstItemType.COURSE.getCode());
        idList.add(MstItemType.BUFFET.getCode());

        // 商品の場合
        if (ItemBuffetType.ITEM.getCode()
            .equals(inputDto.getItemBuffetType())) {
            idList.add(MstItemType.USUALLY.getCode());
            idList.add(MstItemType.SETMEAL.getCode());
        }

        Integer number = itemRepository.getOrderItemNumber(inputDto.getStoreId(),
            inputDto.getReceivablesId(), idList);

        // 商品カテゴリー情報取得
        CheckOrderItemOutputDto outDto = new CheckOrderItemOutputDto();

        // ないの場合
        if (number == 0) {
            outDto.setExistFlag(Flag.OFF.getCode());
            // あるの場合
        } else {
            outDto.setExistFlag(Flag.ON.getCode());
        }
        
        // 获取未确认的放题コース数
        Integer unconfirmedCount = receivablesRepository
            .getOrderBuffetCourseCount(inputDto.getStoreId(),
                inputDto.getReceivablesId(), ItemStatus.UNCONFIRMED.getCode(),
                MstItemType.COURSE.getCode(), MstItemType.BUFFET.getCode());
        
        outDto.setUnconfirmedCount(unconfirmedCount);

        return outDto;
    }
}
