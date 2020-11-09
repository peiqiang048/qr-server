package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.ChangeOptionSortOrderInputResource;
import com.cnc.qr.api.pc.resource.ChangeOptionSortOrderOutputResource;
import com.cnc.qr.api.pc.resource.DeleteOptionInputResource;
import com.cnc.qr.api.pc.resource.DeleteOptionOutputResource;
import com.cnc.qr.api.pc.resource.GetOptionInputResource;
import com.cnc.qr.api.pc.resource.GetOptionListInputResource;
import com.cnc.qr.api.pc.resource.GetOptionListOutputResource;
import com.cnc.qr.api.pc.resource.GetOptionOutputResource;
import com.cnc.qr.api.pc.resource.GetOptionSortOrderListInputResource;
import com.cnc.qr.api.pc.resource.GetOptionSortOrderListOutputResource;
import com.cnc.qr.api.pc.resource.RegistOptionInputResource;
import com.cnc.qr.api.pc.resource.RegistOptionOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.ChangeOptionSortOrderInputDto;
import com.cnc.qr.core.pc.model.DeleteOptionInputDto;
import com.cnc.qr.core.pc.model.GetOptionInputDto;
import com.cnc.qr.core.pc.model.GetOptionListInputDto;
import com.cnc.qr.core.pc.model.GetOptionListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionOutputDto;
import com.cnc.qr.core.pc.model.GetOptionSortOrderListInputDto;
import com.cnc.qr.core.pc.model.GetOptionSortOrderListOutputDto;
import com.cnc.qr.core.pc.model.RegistOptionInputDto;
import com.cnc.qr.core.pc.service.OptionService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
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
 * カテゴリー情報コントローラ.
 */
@RestController
public class OptionController {

    /**
     * オプション情報サービス.
     */
    @Autowired
    private OptionService optionService;

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
     * オプション一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return オプション一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_OPTION_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ResponseEntity<GetOptionListOutputResource> getOptionList(
        @Validated @RequestBody GetOptionListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOptionListInputDto inputDto = beanMapper.map(inputResource, GetOptionListInputDto.class);

        // オプション一覧情報取得サービス処理を実行する
        GetOptionListOutputDto data = optionService.getOptionList(inputDto,
            PageRequest.of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        GetOptionListOutputResource outputResource = new GetOptionListOutputResource();

        // オプション一覧情報を設定する
        outputResource.setOptionList(data.getOptionList().getContent());

        outputResource.setTotalCount(data.getTotalCount());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                data.getOptionList());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * オプション情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return オプション情報
     */
    @PostMapping(UrlConstants.PC_GET_OPTION_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetOptionOutputResource getOption(
        @Validated @RequestBody GetOptionInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOptionInputDto inputDto = beanMapper.map(inputResource, GetOptionInputDto.class);

        // アウトプット情報を作成する
        GetOptionOutputResource outputResource = new GetOptionOutputResource();

        // オプション情報取得サービス処理を実行する
        GetOptionOutputDto outputDto = optionService.getOption(inputDto);

        beanMapper.map(outputDto, outputResource);

        return outputResource;
    }

    /**
     * オプション情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_REGIST_OPTION_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public RegistOptionOutputResource registOption(
        @Validated @RequestBody RegistOptionInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistOptionInputDto inputDto = beanMapper.map(inputResource, RegistOptionInputDto.class);

        // オプション情報編集サービス処理を実行する
        optionService.registOption(inputDto);

        // 処理結果を設定する
        return new RegistOptionOutputResource();
    }

    /**
     * オプション情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_OPTION_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public DeleteOptionOutputResource deleteOption(
        @Validated @RequestBody DeleteOptionInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteOptionInputDto inputDto = beanMapper.map(inputResource, DeleteOptionInputDto.class);
        inputDto.setOptionList(inputResource.getOptionList());

        // オプション情報削除サービスを実行する
        optionService.deleteOption(inputDto);

        return new DeleteOptionOutputResource();
    }

    /**
     * オプション順番情報編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_OPTION_SORT_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeOptionSortOrderOutputResource changeOptionSortOrder(
        @Validated @RequestBody ChangeOptionSortOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeOptionSortOrderInputDto inputDto = beanMapper
            .map(inputResource, ChangeOptionSortOrderInputDto.class);
        inputDto.setOptionSortOrderList(inputResource.getOptionSortOrderList());

        // オプション順番情報編集サービス処理を実行する
        optionService.changeOptionSortOrder(inputDto);

        // 処理結果を設定する
        return new ChangeOptionSortOrderOutputResource();
    }

    /**
     * オプション順番一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return オプション順番情報
     */
    @PostMapping(UrlConstants.PC_GET_SAME_TYPE_OPTION_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetOptionSortOrderListOutputResource getSameTypeOptionList(
        @Validated @RequestBody GetOptionSortOrderListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOptionSortOrderListInputDto inputDto = beanMapper
            .map(inputResource, GetOptionSortOrderListInputDto.class);

        // オプション順番一覧情報取得サービス処理を実行する
        GetOptionSortOrderListOutputDto outputDto = optionService.getOptionSortOrderList(inputDto);

        // オプション順番情報を作成する
        GetOptionSortOrderListOutputResource outputResource = new GetOptionSortOrderListOutputResource();

        // オプション順番情報を設定する
        outputResource.setOptionSortOrderList(outputDto.getOptionSortOrderList());

        return outputResource;
    }
}
