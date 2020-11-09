package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.ChangeCourseItemSortOrderInputResource;
import com.cnc.qr.api.pc.resource.ChangeCourseItemSortOrderOutputResource;
import com.cnc.qr.api.pc.resource.ChangeCourseItemStatusInputResource;
import com.cnc.qr.api.pc.resource.ChangeCourseItemStatusOutputResource;
import com.cnc.qr.api.pc.resource.ChangeItemSortOrderInputResource;
import com.cnc.qr.api.pc.resource.ChangeItemSortOrderOutputResource;
import com.cnc.qr.api.pc.resource.ChangeItemStatusInputResource;
import com.cnc.qr.api.pc.resource.ChangeItemStatusOutputResource;
import com.cnc.qr.api.pc.resource.CourseDelInputResource;
import com.cnc.qr.api.pc.resource.CourseDelOutputResource;
import com.cnc.qr.api.pc.resource.DeleteItemInputResource;
import com.cnc.qr.api.pc.resource.DeleteItemOutputResource;
import com.cnc.qr.api.pc.resource.GetCategoryItemInputResource;
import com.cnc.qr.api.pc.resource.GetCategoryItemOutputResource;
import com.cnc.qr.api.pc.resource.GetCourseInputResource;
import com.cnc.qr.api.pc.resource.GetCourseListInputResource;
import com.cnc.qr.api.pc.resource.GetCourseListOutputResource;
import com.cnc.qr.api.pc.resource.GetCourseOutputResource;
import com.cnc.qr.api.pc.resource.GetItemCategoryInputResource;
import com.cnc.qr.api.pc.resource.GetItemCategoryOutputResource;
import com.cnc.qr.api.pc.resource.GetItemInputResource;
import com.cnc.qr.api.pc.resource.GetItemListInputResource;
import com.cnc.qr.api.pc.resource.GetItemListOutputResource;
import com.cnc.qr.api.pc.resource.GetItemOutputResource;
import com.cnc.qr.api.pc.resource.RegistCourseInputResource;
import com.cnc.qr.api.pc.resource.RegistCourseOutputResource;
import com.cnc.qr.api.pc.resource.RegistItemInputResource;
import com.cnc.qr.api.pc.resource.RegistItemOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.DeliveryTypeWay;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemCategory;
import com.cnc.qr.common.constants.CodeConstants.MstItemType;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.GetDeliveryTypeFlagInputDto;
import com.cnc.qr.core.order.model.GetDeliveryTypeFlagOutputDto;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.core.pc.model.ChangeCourseItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeCourseItemStatusInputDto;
import com.cnc.qr.core.pc.model.ChangeItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeItemStatusInputDto;
import com.cnc.qr.core.pc.model.CourseDelInputDto;
import com.cnc.qr.core.pc.model.DeleteItemInputDto;
import com.cnc.qr.core.pc.model.DeliveryFlagListDto;
import com.cnc.qr.core.pc.model.DeliveryTypeFlagListDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemInputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCourseInputDto;
import com.cnc.qr.core.pc.model.GetCourseListInputDto;
import com.cnc.qr.core.pc.model.GetCourseListOutputDto;
import com.cnc.qr.core.pc.model.GetCourseOutputDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingInputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryInputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryOutputDto;
import com.cnc.qr.core.pc.model.GetItemInputDto;
import com.cnc.qr.core.pc.model.GetItemListInputDto;
import com.cnc.qr.core.pc.model.GetItemListOutputDto;
import com.cnc.qr.core.pc.model.GetItemOutputDto;
import com.cnc.qr.core.pc.model.GetKitchenListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeDto;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
import com.cnc.qr.core.pc.model.ItemListDto;
import com.cnc.qr.core.pc.model.ItemTypeDto;
import com.cnc.qr.core.pc.model.RegistCourseInputDto;
import com.cnc.qr.core.pc.model.RegistItemInputDto;
import com.cnc.qr.core.pc.service.DeliveryService;
import com.cnc.qr.core.pc.service.PcItemService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 商品情報コントローラ.
 */
@RestController
public class PcItemController {

    /**
     * 商品情報取得サービス.
     */
    @Autowired
    private PcItemService itemService;

    /**
     * 出前設定取得サービス.
     */
    @Autowired
    private DeliveryService deliveryService;

    /**
     * 店舗情報取得サービス.
     */
    @Autowired
    private StoreInfoService storeInfoService;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品情報
     */
    @PostMapping(UrlConstants.PC_GET_ITEM_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ResponseEntity<GetItemListOutputResource> getItemList(
        @Validated @RequestBody GetItemListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemListInputDto inputDto = beanMapper.map(inputResource, GetItemListInputDto.class);

        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.ADMIN.getCode());
        if (null == inputDto.getKitchenId()) {
            inputDto.setKitchenId(-1);
        }

        // サービス処理を実行する
        GetItemListOutputDto data = itemService.getItem(inputDto, PageRequest
            .of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        GetItemListOutputResource outputResource = new GetItemListOutputResource();
        // 商品情報を設定する
        outputResource.setItems(data.getItems().getContent());

        outputResource.setTotalCount(data.getTotalCount());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                data.getItems());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 商品明細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品情報
     */
    @PostMapping(UrlConstants.PC_GET_ITEM)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetItemOutputResource getItem(
        @Validated @RequestBody GetItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemInputDto inputDto = beanMapper.map(inputResource, GetItemInputDto.class);

        // アウトプット情報を作成する
        GetItemOutputResource outputResource = new GetItemOutputResource();

        GetDeliveryTypeFlagInputDto getStoreDeliveryTypeInputDto = new GetDeliveryTypeFlagInputDto();
        getStoreDeliveryTypeInputDto.setStoreId(inputDto.getStoreId());

        // テーブルサービス処理を実行する
        GetDeliveryTypeFlagOutputDto getStoreDeliveryTypeOutputDto = storeInfoService
            .getDeliveryTypeFlag(getStoreDeliveryTypeInputDto);

        GetItemOutputDto outputDto = new GetItemOutputDto();

        if (null != inputDto.getItemId()) {

            // サービス処理を実行する
            outputDto = itemService.getItemInfo(inputDto);

            beanMapper.map(outputDto, outputResource);

            //  カテゴリーID情報取得
            List<GetCategoryIdListOutputDto> categoryIdList = itemService.getItemCategory(inputDto);
            outputResource.setCategoryIdList(categoryIdList);
            if (Flag.OFF.getCode().toString()
                .equals(getStoreDeliveryTypeOutputDto.getDeliveryTypeFlag())
                && outputResource.getCateringTypeFlag().equals(Flag.OFF.getCode().toString())) {
                outputResource.setDeliveryChangeFlag(Flag.OFF.getCode().toString());
            } else {
                outputResource.setDeliveryChangeFlag(Flag.ON.getCode().toString());
            }
        } else {

            if (null == getStoreDeliveryTypeOutputDto.getDeliveryTypeFlag()
                || Flag.OFF.getCode().toString()
                .equals(getStoreDeliveryTypeOutputDto.getDeliveryTypeFlag())) {
                outputResource.setDeliveryFlag(Flag.OFF.getCode().toString());
                outputResource.setCateringTypeFlag(DeliveryTypeWay.NOWAY.getCode());
                outputResource.setDeliveryChangeFlag(Flag.OFF.getCode().toString());
            } else {
                outputResource.setDeliveryFlag(Flag.ON.getCode().toString());
                outputResource
                    .setCateringTypeFlag(getStoreDeliveryTypeOutputDto.getDeliveryTypeFlag());
                outputResource.setDeliveryChangeFlag(Flag.ON.getCode().toString());
            }
        }

        // 単位リスト取得
        List<GetUnitListOutputDto> unitList = itemService.getUnitList(inputDto);
        outputResource.setUnitList(unitList);

        // カテゴリーリスト取得
        List<GetCategoryList> categoryList = itemService.getCategoryList(inputDto);
        List<GetCategoryList> categoryItemList = new ArrayList<>();
        categoryList.forEach(getCategoryList -> {
            if (!ItemCategory.COURSE.getCode().equals(getCategoryList.getCategoryId())
                && !ItemCategory.BUFFET.getCode().equals(getCategoryList.getCategoryId())) {
                categoryItemList.add(getCategoryList);
            }
        });
        outputResource.setCategoryList(categoryItemList);

        // 税リスト取得
        List<GetTaxListOutputDto> taxList = itemService.getTaxList(inputDto);
        outputResource.setTaxList(taxList);

        // キチンリスト取得
        List<GetKitchenListOutputDto> kitchenList = itemService.getKitchenList(inputDto);
        outputResource.setKitchenList(kitchenList);

        // オプション取得
        List<GetOptionTypeDto> optionTypeList = itemService.getOptionTypeList(inputDto);
        outputResource.setOptionTypeList(optionTypeList);

        // 商品区分リスト
        List<ItemTypeDto> itemTypeList = new ArrayList<>();
        for (MstItemType type : MstItemType.values()) {
            if (!type.getCode().equals(MstItemType.BUFFET.getCode())
                && !type.getCode().equals(MstItemType.COURSE.getCode())) {
                ItemTypeDto itemType = new ItemTypeDto();
                itemType.setItemType(type.getCode());
                itemType.setItemTypeName(type.getValue());
                itemTypeList.add(itemType);
            }
        }
        outputResource.setItemTypeList(itemTypeList);

        GetDeliverySettingInputDto deliveryInputDto = new GetDeliverySettingInputDto();
        deliveryInputDto.setStoreId(inputDto.getStoreId());

        // 出前区分リスト取得
        List<DeliveryFlagListDto> deliveryFlagList = deliveryService
            .getDeliveryFlagList(deliveryInputDto);
        outputResource.setDeliveryFlagList(deliveryFlagList);

        // 出前仕方リスト取得
        List<DeliveryTypeFlagListDto> deliveryTypeFlagList = deliveryService
            .getDeliveryTypeFlagList(deliveryInputDto);
        outputResource.setDeliveryTypeFlagList(deliveryTypeFlagList);

        return outputResource;
    }

    /**
     * 商品編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_REGIST_ITEM)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public RegistItemOutputResource registItem(
        @Validated @RequestBody RegistItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistItemInputDto inputDto = beanMapper.map(inputResource, RegistItemInputDto.class);
        inputDto.setCategoryIdList(inputResource.getCategoryIdList());
        inputDto.setItemOptionList(inputResource.getItemOptionList());

        // 商品編集サービス処理を実行する
        itemService.registItem(inputDto);

        // 処理結果を設定する
        return new RegistItemOutputResource();
    }

    /**
     * 商品情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_ITEN)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public DeleteItemOutputResource deleteItem(
        @Validated @RequestBody DeleteItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteItemInputDto inputDto = beanMapper.map(inputResource, DeleteItemInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        // アカウント管理サービスを実行する
        itemService.deleteItem(inputDto);

        return new DeleteItemOutputResource();
    }

    /**
     * 商品ステータス更新.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_ITEM_STATUS)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeItemStatusOutputResource changeUserStatus(
        @Validated @RequestBody ChangeItemStatusInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeItemStatusInputDto inputDto = beanMapper
            .map(inputResource, ChangeItemStatusInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        // アカウント管理サービスを実行する
        itemService.changeItemStatus(inputDto);

        return new ChangeItemStatusOutputResource();
    }

    /**
     * 商品カテゴリーリスト情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return カテゴリー情報
     */
    @PostMapping(UrlConstants.PC_GET_ITEM_CATEGORY)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetItemCategoryOutputResource getItemCategory(
        @Validated @RequestBody GetItemCategoryInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemCategoryInputDto inputDto = beanMapper
            .map(inputResource, GetItemCategoryInputDto.class);

        // アウトプット情報を作成する
        GetItemCategoryOutputResource outputResource = new GetItemCategoryOutputResource();

        // サービス処理を実行する
        List<GetItemCategoryOutputDto> outputDto = itemService.getItemCategoryList(inputDto);
        outputResource.setItemCategoryList(outputDto);

        return outputResource;
    }

    /**
     * カテゴリーの下に商品リスト情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品情報
     */
    @PostMapping(UrlConstants.PC_GET_CATEGORY_ITEM)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetCategoryItemOutputResource getCategoryItem(
        @Validated @RequestBody GetCategoryItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetCategoryItemInputDto inputDto = beanMapper
            .map(inputResource, GetCategoryItemInputDto.class);

        // アウトプット情報を作成する
        GetCategoryItemOutputResource outputResource = new GetCategoryItemOutputResource();

        // サービス処理を実行する
        List<GetCategoryItemOutputDto> outputDto = itemService.getCategoryItemList(inputDto);
        outputResource.setItemList(outputDto);

        return outputResource;
    }

    /**
     * 商品編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_ITEM_SORT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeItemSortOrderOutputResource changeItemSortOrder(
        @Validated @RequestBody ChangeItemSortOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeItemSortOrderInputDto inputDto = beanMapper
            .map(inputResource, ChangeItemSortOrderInputDto.class);
        inputDto.setItemSortOrderList(inputResource.getItemSortOrderList());

        // 商品順番編集サービス処理を実行する
        itemService.changeItemSortOrder(inputDto);

        // 処理結果を設定する
        return new ChangeItemSortOrderOutputResource();
    }

    /**
     * コース情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース情報
     */
    @PostMapping(UrlConstants.PC_GET_COURSE_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetCourseListOutputResource getCourseList(
        @Validated @RequestBody GetCourseListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetCourseListInputDto inputDto = beanMapper.map(inputResource, GetCourseListInputDto.class);

        // サービス処理を実行する
        GetCourseListOutputDto data = itemService.getCourseList(inputDto, PageRequest
            .of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        GetCourseListOutputResource outputResource = new GetCourseListOutputResource();
        // 商品情報を設定する
        outputResource.setCourseList(data.getCourseList());

        outputResource.setTotalCount(data.getTotalCount());

        return outputResource;
    }

    /**
     * コース明細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース情報
     */
    @PostMapping(UrlConstants.PC_GET_COURSE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetCourseOutputResource getCourse(
        @Validated @RequestBody GetCourseInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetCourseInputDto courseInputDto = beanMapper.map(inputResource, GetCourseInputDto.class);

        // インプット情報をDTOにセットする
        GetItemInputDto itemInputDto = beanMapper.map(courseInputDto, GetItemInputDto.class);
        itemInputDto.setItemId(courseInputDto.getCourseId());

        // アウトプット情報を作成する
        GetCourseOutputResource outputResource = new GetCourseOutputResource();
        outputResource.setSelectedItemList(new ArrayList<>());

        if (null != courseInputDto.getCourseId()) {

            // サービス処理を実行する
            GetCourseOutputDto outputDto = itemService.getCourseInfo(courseInputDto);

            beanMapper.map(outputDto, outputResource);

            // 商品リスト取得
            List<ItemListDto> selectedItemList = itemService.getSelectedItemList(courseInputDto);
            outputResource.setSelectedItemList(selectedItemList);
        }

        //  カテゴリーID情報取得
        List<GetCategoryIdListOutputDto> categoryIdList = new ArrayList<>();
        GetCategoryIdListOutputDto categoryIdDto = new GetCategoryIdListOutputDto();
        categoryIdDto.setCategoryId(ItemCategory.COURSE.getCode());
        categoryIdDto.setCategoryName(ItemCategory.COURSE.getValue());
        categoryIdList.add(categoryIdDto);
        outputResource.setCategoryIdList(categoryIdList);

        // 単位リスト取得
        List<GetUnitListOutputDto> unitList = itemService.getUnitList(itemInputDto);
        outputResource.setUnitList(unitList);

        // カテゴリーリスト取得
        List<GetCategoryList> categoryList = itemService.getCategoryList(itemInputDto);
        outputResource.setCategoryList(categoryList);

        // 税リスト取得
        List<GetTaxListOutputDto> taxList = itemService.getTaxList(itemInputDto);
        outputResource.setTaxList(taxList);

        // 商品リスト取得
        List<ItemListDto> itemList = itemService.getItemList(courseInputDto);
        outputResource.setItemList(itemList);

        // カテゴリーリスト取得
        List<GetCategoryList> hasItemCategory = itemService.getHasItemCategoryList(courseInputDto);
        List<GetCategoryList> hasItemCategoryList = new ArrayList<>();
        hasItemCategory.forEach(getCategoryList -> {
            if (!ItemCategory.COURSE.getCode().equals(getCategoryList.getCategoryId())
                && !ItemCategory.BUFFET.getCode().equals(getCategoryList.getCategoryId())) {
                hasItemCategoryList.add(getCategoryList);
            }
        });
        outputResource.setHasItemCategoryList(hasItemCategoryList);

        return outputResource;
    }

    /**
     * コース編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース保存情報
     */
    @PostMapping(UrlConstants.PC_REGIST_COURSE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistCourseOutputResource registCourse(
        @Validated @RequestBody RegistCourseInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        RegistCourseInputDto inputDto = beanMapper.map(inputResource, RegistCourseInputDto.class);
        inputDto.setCategoryIdList(inputResource.getCategoryIdList());
        inputDto.setItemList(inputResource.getItemList());

        // 商品情報取得サービス処理を実行する
        itemService.saveCourse(inputDto);

        return new RegistCourseOutputResource();
    }

    /**
     * コース削除情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース保存情報
     */
    @PostMapping(UrlConstants.PC_DELETE_COURSE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public CourseDelOutputResource delCourse(
        @Validated @RequestBody CourseDelInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        CourseDelInputDto inputDto = beanMapper.map(inputResource, CourseDelInputDto.class);
        inputDto.setCourseList(inputResource.getCourseList());

        //   商品情報取得サービス処理を実行する
        itemService.delCourse(inputDto);

        return new CourseDelOutputResource();
    }

    /**
     * コース商品ステータス更新.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_COURSE_STATUS)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeCourseItemStatusOutputResource changeCourseStatus(
        @Validated @RequestBody ChangeCourseItemStatusInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeCourseItemStatusInputDto inputDto = beanMapper
            .map(inputResource, ChangeCourseItemStatusInputDto.class);
        inputDto.setCourseList(inputResource.getCourseList());

        // 商品情報取得サービス処理を実行する
        itemService.changeCourseItemStatus(inputDto);

        return new ChangeCourseItemStatusOutputResource();
    }

    /**
     * コース順番編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_COURSE_SORT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeCourseItemSortOrderOutputResource changeBuffetItemSortOrder(
        @Validated @RequestBody ChangeCourseItemSortOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeCourseItemSortOrderInputDto inputDto = beanMapper
            .map(inputResource, ChangeCourseItemSortOrderInputDto.class);
        inputDto.setCourseItemSortOrderList(inputResource.getCourseSortOrderList());

        // 商品情報取得サービス処理を実行する
        itemService.changeCourseItemSortOrder(inputDto);

        // 処理結果を設定する
        return new ChangeCourseItemSortOrderOutputResource();
    }
}
