package com.cnc.qr.api.csmborder.controller;

import com.cnc.qr.api.csmborder.constants.CsmbConstants;
import com.cnc.qr.api.csmborder.resource.CheckOrderItemInputResource;
import com.cnc.qr.api.csmborder.resource.CheckOrderItemOutputResource;
import com.cnc.qr.api.csmborder.resource.GetBuffetInputResource;
import com.cnc.qr.api.csmborder.resource.GetBuffetListInputResource;
import com.cnc.qr.api.csmborder.resource.GetBuffetListOutputResource;
import com.cnc.qr.api.csmborder.resource.GetBuffetOutputResource;
import com.cnc.qr.api.csmborder.resource.GetItemBySpeechInputResource;
import com.cnc.qr.api.csmborder.resource.GetItemBySpeechOutputResource;
import com.cnc.qr.api.csmborder.resource.GetItemCategoryInputResource;
import com.cnc.qr.api.csmborder.resource.GetItemCategoryOutputResource;
import com.cnc.qr.api.csmborder.resource.GetItemInputResource;
import com.cnc.qr.api.csmborder.resource.GetItemOptionTypeInputResource;
import com.cnc.qr.api.csmborder.resource.GetItemOptionTypeOutputResource;
import com.cnc.qr.api.csmborder.resource.GetItemOutputResource;
import com.cnc.qr.api.csmborder.resource.GetTextBySpeechOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.order.model.CheckOrderItemInputDto;
import com.cnc.qr.core.order.model.CheckOrderItemOutputDto;
import com.cnc.qr.core.order.model.GetBuffetInputDto;
import com.cnc.qr.core.order.model.GetBuffetListInputDto;
import com.cnc.qr.core.order.model.GetBuffetListOutputDto;
import com.cnc.qr.core.order.model.GetBuffetOutputDto;
import com.cnc.qr.core.order.model.GetCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetItemBySpeechInputDto;
import com.cnc.qr.core.order.model.GetItemBySpeechOutputDto;
import com.cnc.qr.core.order.model.GetItemCategoryInputDto;
import com.cnc.qr.core.order.model.GetItemDto;
import com.cnc.qr.core.order.model.GetItemInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeOutputDto;
import com.cnc.qr.core.order.model.GetTextBySpeechInputDto;
import com.cnc.qr.core.order.model.GetTextBySpeechOutputDto;
import com.cnc.qr.core.order.service.ItemService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 商品情報コントローラ.
 */
@RestController
public class CsmbItemInfoController {

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
    @PostMapping(UrlConstants.CSMB_GET_ITEM_CATEGORY)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
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
        inputDto.setGradation(CsmbConstants.GRADATION);
        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.CSMB.getCode());

        // サービス処理を実行する
        GetCategoryListOutputDto outputDto = itemService.getItemCategoryList(inputDto);

        // アウトプット情報を作成する
        GetItemCategoryOutputResource outputResource = new GetItemCategoryOutputResource();

        // 店商品カテゴリー情報を設定する
        outputResource.setItemCategoryList(outputDto.getItemCategoryList());
        outputResource.setItemListType(outputDto.getItemListType());

        return outputResource;
    }

    /**
     * 商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param pageable      ページ情報
     * @param result        バインド結果
     * @return 商品情報
     */
    @GetMapping(UrlConstants.CSMB_GET_ITEM)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public ResponseEntity<GetItemOutputResource> getItem(
        @Validated GetItemInputResource inputResource,
        @PageableDefault(size = 8) Pageable pageable, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemInputDto inputDto = beanMapper.map(inputResource, GetItemInputDto.class);
        inputDto.setBuffetIdList(inputResource.getBuffetIdList());

        // 店舗媒体用途区分を設定する
        inputDto.setTerminalDistinction(TerminalDistinction.CSMB.getCode());

        // サービス処理を実行する
        Page<GetItemDto> page = itemService.getItem(inputDto, pageable);

        // インプット情報をDTOにセットする
        GetItemOutputResource outputResource = new GetItemOutputResource();

        // 商品情報を設定する
        outputResource.setItems(page.getContent());

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
    @PostMapping(UrlConstants.CSMB_GET_ITEM_OPTION_TYPE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetItemOptionTypeOutputResource getItemOption(
        @Validated @RequestBody GetItemOptionTypeInputResource inputResource,
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
        GetItemOptionTypeOutputResource outputResource = new GetItemOptionTypeOutputResource();

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
    @PostMapping(UrlConstants.CSMB_GET_BUFFET_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
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
    @PostMapping(UrlConstants.CSMB_GET_BUFFET)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
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

    /**
     * 商品情報取得.
     *
     * @param storeId    店舗ID
     * @param languages  语言
     * @param speechFile 音ファイル
     * @return 商品情報
     */
    @PostMapping(UrlConstants.CSMB_GET_TEXT_BY_SPEECH)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetTextBySpeechOutputResource getTextBySpeech(
        @RequestParam("storeId") String storeId, @RequestParam("languages") String languages,
        @RequestParam("speechFile") MultipartFile speechFile) {

        // 単項目チェック
        if (StringUtils.isEmpty(storeId) || StringUtils.isEmpty(languages)) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetTextBySpeechInputDto inputDto = new GetTextBySpeechInputDto();
        inputDto.setStoreId(storeId);
        inputDto.setLanguages(languages);
        inputDto.setFile(speechFile);

        // サービス処理を実行する
        GetTextBySpeechOutputDto outputDto = itemService.getTextBySpeech(inputDto);

        // インプット情報をDTOにセットする
        GetTextBySpeechOutputResource outputResource = new GetTextBySpeechOutputResource();

        // 認識結果を設定する
        outputResource.setTranscript(outputDto.getTranscript());

        return outputResource;
    }

    /**
     * 商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 商品情報
     */
    @PostMapping(UrlConstants.CSMB_GET_ITEM_BY_SPEECH)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetItemBySpeechOutputResource getItemsBySpeech(
        @Validated @RequestBody GetItemBySpeechInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemBySpeechInputDto inputDto = new GetItemBySpeechInputDto();
        inputDto.setStoreId(inputResource.getStoreId());
        inputDto.setLanguages(inputResource.getLanguages());
        inputDto.setSearchInfo(inputResource.getSearchInfo());
        inputDto.setReceivablesId(inputResource.getReceivablesId());

        // サービス処理を実行する
        GetItemBySpeechOutputDto outputDto = itemService.getItemBySpeech(inputDto);

        // インプット情報をDTOにセットする
        GetItemBySpeechOutputResource outputResource = new GetItemBySpeechOutputResource();

        // 商品情報を設定する
        outputResource.setItems(outputDto.getItems());
        outputResource.setTranscript(outputDto.getTranscript());

        return outputResource;
    }

    /**
     * チェック注文商品.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return チェック結果
     */
    @PostMapping(UrlConstants.CSMB_CHECK_ORDER_ITEM)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CheckOrderItemOutputResource checkOrderItem(
        @Validated @RequestBody CheckOrderItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        CheckOrderItemInputDto inputDto = beanMapper.map(inputResource, CheckOrderItemInputDto.class);

        // サービス処理を実行する
        CheckOrderItemOutputDto outputDto = itemService.checkOrderItem(inputDto);

        // インプット情報をDTOにセットする
        CheckOrderItemOutputResource outputResource = new CheckOrderItemOutputResource();

        // チェック結果を設定する
        beanMapper.map(outputDto, outputResource);

        return outputResource;
    }
}
