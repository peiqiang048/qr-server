package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.constants.PCConstants;
import com.cnc.qr.api.pc.resource.ChangeCategorySortOrderInputResource;
import com.cnc.qr.api.pc.resource.ChangeCategorySortOrderOutputResource;
import com.cnc.qr.api.pc.resource.DeleteCategoryInputResource;
import com.cnc.qr.api.pc.resource.DeleteCategoryOutputResource;
import com.cnc.qr.api.pc.resource.GetCategoryInputResource;
import com.cnc.qr.api.pc.resource.GetCategoryListInputResource;
import com.cnc.qr.api.pc.resource.GetCategoryListOutputResource;
import com.cnc.qr.api.pc.resource.GetCategoryOutputResource;
import com.cnc.qr.api.pc.resource.RegistCategoryInputResource;
import com.cnc.qr.api.pc.resource.RegistCategoryOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.ChangeCategorySortOrderInputDto;
import com.cnc.qr.core.pc.model.DeleteCategoryInputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryInputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCategoryListInputDto;
import com.cnc.qr.core.pc.model.GetCategoryListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryOutputDto;
import com.cnc.qr.core.pc.model.RegistCategoryInputDto;
import com.cnc.qr.core.pc.service.CategoryService;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
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
 * カテゴリー情報コントローラ.
 */
@RestController
public class CategoryController {

    /**
     * カテゴリー情報取得サービス.
     */
    @Autowired
    private CategoryService categoryService;

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
     * カテゴリー情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return カテゴリー情報
     */
    @PostMapping(UrlConstants.PC_GET_CATEGORY_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ResponseEntity<GetCategoryListOutputResource> getCategoryList(
        @Validated @RequestBody GetCategoryListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetCategoryListInputDto inputDto = beanMapper
            .map(inputResource, GetCategoryListInputDto.class);

        // サービス処理を実行する
        GetCategoryListOutputDto data = categoryService.getCategory(inputDto,
            PageRequest.of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        GetCategoryListOutputResource outputResource = new GetCategoryListOutputResource();

        // 商品情報を設定する
        outputResource.setCategoryList(data.getCategoryList().getContent());

        outputResource.setTotalCount(data.getTotalCount());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                data.getCategoryList());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * カテゴリー明細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return カテゴリー情報
     */
    @PostMapping(UrlConstants.PC_GET_CATEGORY)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetCategoryOutputResource getCategory(
        @Validated @RequestBody GetCategoryInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetCategoryInputDto inputDto = beanMapper.map(inputResource, GetCategoryInputDto.class);

        // アウトプット情報を作成する
        GetCategoryOutputResource outputResource = new GetCategoryOutputResource();

        if (null != inputDto.getCategoryId()) {

            // サービス処理を実行する
            GetCategoryOutputDto outputDto = categoryService.getCategoryInfo(inputDto);

            beanMapper.map(outputDto, outputResource);

            if (outputDto.getGradation() == 2) {

                // カテゴリーID情報取得
                List<GetCategoryIdListOutputDto> categoryIdList = categoryService
                    .getParentCategoryIdList(inputDto);
                outputResource.setParentCategoryIdList(categoryIdList);
            }

            if (PCConstants.GRADATION == 2) {

                // カテゴリーリスト取得
                List<GetCategoryList> categoryList = categoryService
                    .getParentCategoryList(inputDto);
                outputResource.setParentCategoryList(categoryList);
            }
        } else {

            if (PCConstants.GRADATION == 2) {

                // カテゴリーリスト取得
                List<GetCategoryList> categoryList = categoryService
                    .getParentCategoryList(inputDto);
                outputResource.setParentCategoryList(categoryList);
            }
        }

        outputResource.setMaxGradation(PCConstants.GRADATION);

        return outputResource;
    }

    /**
     * カテゴリー編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_REGIST_CATEGORY)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public RegistCategoryOutputResource registCategory(
        @Validated @RequestBody RegistCategoryInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistCategoryInputDto inputDto = beanMapper
            .map(inputResource, RegistCategoryInputDto.class);

        // カテゴリー編集サービス処理を実行する
        categoryService.registCategory(inputDto);

        // 処理結果を設定する
        return new RegistCategoryOutputResource();
    }

    /**
     * カテゴリー情報削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_DELETE_CATEGORY)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public DeleteCategoryOutputResource deleteCategory(
        @Validated @RequestBody DeleteCategoryInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteCategoryInputDto inputDto = beanMapper
            .map(inputResource, DeleteCategoryInputDto.class);
        inputDto.setCategoryList(inputResource.getCategoryList());

        // アカウント管理サービスを実行する
        categoryService.deleteCategory(inputDto);

        return new DeleteCategoryOutputResource();
    }

    /**
     * カテゴリー順番編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_CATEGORY_SORT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeCategorySortOrderOutputResource changeCategorySortOrder(
        @Validated @RequestBody ChangeCategorySortOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeCategorySortOrderInputDto inputDto = beanMapper
            .map(inputResource, ChangeCategorySortOrderInputDto.class);
        inputDto.setCategorySortOrderList(inputResource.getCategorySortOrderList());

        // 商品順番編集サービス処理を実行する
        categoryService.changeCategorySortOrder(inputDto);

        // 処理結果を設定する
        return new ChangeCategorySortOrderOutputResource();
    }
}
