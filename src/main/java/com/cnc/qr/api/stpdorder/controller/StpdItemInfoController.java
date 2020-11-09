package com.cnc.qr.api.stpdorder.controller;

import com.cnc.qr.api.stpdorder.constants.StpdConstants;
import com.cnc.qr.api.stpdorder.resource.GetBuffetInputResource;
import com.cnc.qr.api.stpdorder.resource.GetBuffetListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetBuffetListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetBuffetOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetItemCategoryInputResource;
import com.cnc.qr.api.stpdorder.resource.GetItemCategoryOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetItemInputResource;
import com.cnc.qr.api.stpdorder.resource.GetItemOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.CodeConstants.UrlSource;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.CategoryInfoDto;
import com.cnc.qr.core.order.model.GetBuffetInputDto;
import com.cnc.qr.core.order.model.GetBuffetListInputDto;
import com.cnc.qr.core.order.model.GetBuffetListOutputDto;
import com.cnc.qr.core.order.model.GetBuffetOutputDto;
import com.cnc.qr.core.order.model.GetCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetItemCategoryInputDto;
import com.cnc.qr.core.order.model.GetItemDto;
import com.cnc.qr.core.order.model.GetItemInputDto;
import com.cnc.qr.core.order.model.GetItemOutputDto;
import com.cnc.qr.core.order.model.ItemCategoryListDto;
import com.cnc.qr.core.order.model.ItemDetailInfoDto;
import com.cnc.qr.core.order.service.ItemService;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品情報コントローラ.
 */
@RestController
public class StpdItemInfoController {

    /**
     * 商品情報取得サービス.
     */
    @Autowired
    private ItemService itemService;

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
     * 商品カテゴリー情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品カテゴリー情報
     */
    @PostMapping(UrlConstants.STPD_GET_CATEGORY_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetItemCategoryOutputResource getCategoryList(
        @Validated @RequestBody GetItemCategoryInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemCategoryInputDto inputDto = new GetItemCategoryInputDto();
        inputDto.setStoreId(inputResource.getStoreId());
        inputDto.setLanguages(inputResource.getLanguages());
        inputDto.setGradation(StpdConstants.GRADATION);
        inputDto.setReceivablesId(inputResource.getReceivablesId());
        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.STPD.getCode());
        inputDto.setItemGetType(UrlSource.TOP.getCode());
        // サービス処理を実行する
        GetCategoryListOutputDto outputDto = itemService.getItemCategoryList(inputDto);

        // アウトプット情報を作成する
        GetItemCategoryOutputResource outputResource = new GetItemCategoryOutputResource();

        // 店商品カテゴリー情報を設定する
        List<CategoryInfoDto> categoryList = new ArrayList<>();
        for (ItemCategoryListDto info : outputDto.getItemCategoryList()) {
            if (Objects.equals(Flag.ON.getCode().toString(), info.getCourseFlag())) {
                continue;
            }
            CategoryInfoDto categoryData = new CategoryInfoDto();
            categoryData.setCategoryId(info.getItemCategoryId());
            categoryData.setCategoryName(info.getItemCategoryName());
            categoryData.setBuffetFlag(info.getBuffetFlag());
            categoryList.add(categoryData);
        }
        outputResource.setCategoryList(categoryList);

        return outputResource;
    }

    /**
     * 商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品情報
     */
    @PostMapping(UrlConstants.STPD_GET_ITEM_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetItemOutputResource getItemList(
        @Validated @RequestBody GetItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemInputDto inputDto = beanMapper.map(inputResource, GetItemInputDto.class);
        inputDto.setBuffetIdList(inputResource.getBuffetIdList());

        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.STPD.getCode());

        // サービス処理を実行する
        GetItemOutputDto outputDto = itemService.getItemList(inputDto);

        // インプット情報をDTOにセットする
        GetItemOutputResource outputResource = new GetItemOutputResource();

        // 商品情報を設定する
        List<ItemDetailInfoDto> itemList = new ArrayList<>();
        for (GetItemDto item : outputDto.getItems()) {
            ItemDetailInfoDto itemInfo = beanMapper.map(item, ItemDetailInfoDto.class);
            itemInfo.setItemSortNo(item.getSortOrder());
            itemInfo.setItemDescription(item.getItemInfo());
            itemInfo.setItemSoldOutFlag(item.getItemSelloffFlag());
            itemList.add(itemInfo);
        }
        outputResource.setItemList(itemList);

        return outputResource;
    }

    /**
     * 放題一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 放題情報
     */
    @PostMapping(UrlConstants.STPD_GET_BUFFET_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetBuffetListOutputResource getBuffetList(
        @Validated @RequestBody GetBuffetListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetBuffetListInputDto inputDto = beanMapper
            .map(inputResource, GetBuffetListInputDto.class);

        // サービス処理を実行する
        GetBuffetListOutputDto outputDto = itemService.getBuffetList(inputDto);

        // アウトプット情報を作成する
        GetBuffetListOutputResource outputResource = new GetBuffetListOutputResource();

        // 店商品カテゴリー情報を設定する
        outputResource.setBuffetList(outputDto.getBuffetList());

        return outputResource;
    }

    /**
     * 放題明細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 放題情報
     */
    @PostMapping(UrlConstants.STPD_GET_BUFFET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetBuffetOutputResource getBuffet(
        @Validated @RequestBody GetBuffetInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetBuffetInputDto inputDto = beanMapper.map(inputResource, GetBuffetInputDto.class);

        // アウトプット情報を作成する
        GetBuffetOutputResource outputResource = new GetBuffetOutputResource();

        // サービス処理を実行する
        GetBuffetOutputDto outputDto = itemService.getStorePadBuffetInfo(inputDto);

        beanMapper.map(outputDto, outputResource);

        return outputResource;
    }
}
