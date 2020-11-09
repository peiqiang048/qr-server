package com.cnc.qr.api.stmborder.controller;

import com.cnc.qr.api.stmborder.constants.StmbConstants;
import com.cnc.qr.api.stmborder.resource.GetBuffetInputResource;
import com.cnc.qr.api.stmborder.resource.GetBuffetListInputResource;
import com.cnc.qr.api.stmborder.resource.GetBuffetListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetBuffetOutputResource;
import com.cnc.qr.api.stmborder.resource.GetCategoryListInputResource;
import com.cnc.qr.api.stmborder.resource.GetCategoryListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetItemListInputResource;
import com.cnc.qr.api.stmborder.resource.GetItemListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetItemOptionListInputResource;
import com.cnc.qr.api.stmborder.resource.GetItemOptionListOutputResource;
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
import com.cnc.qr.core.order.model.GetItemOptionTypeInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeOutputDto;
import com.cnc.qr.core.order.model.ItemCategoryListDto;
import com.cnc.qr.core.order.service.ItemService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
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
public class StmbItemInfoController {

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
    @PostMapping(UrlConstants.STMB_GET_CATEGORY_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public GetCategoryListOutputResource getCategoryList(
        @Validated @RequestBody GetCategoryListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemCategoryInputDto inputDto = beanMapper
            .map(inputResource, GetItemCategoryInputDto.class);
        inputDto.setGradation(StmbConstants.GRADATION);
        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.STMB.getCode());
        inputDto.setItemGetType(UrlSource.TOP.getCode());

        // サービス処理を実行する
        GetCategoryListOutputDto outputDto = itemService.getItemCategoryList(inputDto);

        // アウトプット情報を作成する
        GetCategoryListOutputResource outputResource = new GetCategoryListOutputResource();

        // 店商品カテゴリー情報を設定する
        List<ItemCategoryListDto> categoryList = new ArrayList<>();
        for (ItemCategoryListDto info : outputDto.getItemCategoryList()) {
            if (Objects.equals(Flag.ON.getCode().toString(), info.getCourseFlag())) {
                continue;
            }
            ItemCategoryListDto categoryData = new ItemCategoryListDto();
            categoryData.setItemCategoryId(info.getItemCategoryId());
            categoryData.setItemCategoryName(info.getItemCategoryName());
            categoryData.setBuffetFlag(info.getBuffetFlag());
            categoryList.add(categoryData);
        }

        // 店商品カテゴリー情報を設定する
        outputResource.setItemCategoryList(categoryList);

        return outputResource;
    }

    /**
     * 商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品情報
     */
    @PostMapping(UrlConstants.STMB_GET_ITEM_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public ResponseEntity<GetItemListOutputResource> getItemList(
        @Validated @RequestBody GetItemListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemInputDto inputDto = beanMapper.map(inputResource, GetItemInputDto.class);
        inputDto.setBuffetIdList(inputResource.getBuffetIdList());

        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.STMB.getCode());

        // サービス処理を実行する
        Page<GetItemDto> page = itemService.getItem(inputDto,
            PageRequest.of(inputResource.getPage(), inputResource.getPageCount()));

        // インプット情報をDTOにセットする
        GetItemListOutputResource outputResource = new GetItemListOutputResource();

        // 商品情報を設定する
        outputResource.setItemList(page.getContent());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 商品オプション情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品オプション情報
     */
    @PostMapping(UrlConstants.STMB_GET_ITEM_OPTION_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public GetItemOptionListOutputResource getItemOptionList(
        @Validated @RequestBody GetItemOptionListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemOptionTypeInputDto inputDto =
            beanMapper.map(inputResource, GetItemOptionTypeInputDto.class);

        // サービス処理を実行する
        GetItemOptionTypeOutputDto outputDto = itemService.getItemOptionType(inputDto);

        // アウトプット情報を作成する
        GetItemOptionListOutputResource outputResource = new GetItemOptionListOutputResource();

        // 商品オプション類型情報を設定する
        outputResource.setOptionTypeList(outputDto.getOptionTypeList());

        return outputResource;
    }

    /**
     * 放題一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 放題情報
     */
    @PostMapping(UrlConstants.STMB_GET_BUFFET_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
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
    @PostMapping(UrlConstants.STMB_GET_BUFFET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
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
        GetBuffetOutputDto outputDto = itemService.getBuffetInfo(inputDto);

        beanMapper.map(outputDto, outputResource);

        return outputResource;
    }
}
