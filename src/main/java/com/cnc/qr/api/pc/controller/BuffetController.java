package com.cnc.qr.api.pc.controller;

import com.cnc.qr.api.pc.resource.BuffetDelInputResource;
import com.cnc.qr.api.pc.resource.BuffetDelOutputResource;
import com.cnc.qr.api.pc.resource.BuffetInputResource;
import com.cnc.qr.api.pc.resource.BuffetOutputResource;
import com.cnc.qr.api.pc.resource.ChangeBuffetItemSortOrderInputResource;
import com.cnc.qr.api.pc.resource.ChangeBuffetItemSortOrderOutputResource;
import com.cnc.qr.api.pc.resource.ChangeBuffetItemStatusInputResource;
import com.cnc.qr.api.pc.resource.ChangeBuffetItemStatusOutputResource;
import com.cnc.qr.api.pc.resource.GetBuffetInputResource;
import com.cnc.qr.api.pc.resource.GetBuffetOutputResource;
import com.cnc.qr.api.pc.resource.RegistBuffetInputResource;
import com.cnc.qr.api.pc.resource.RegistBuffetOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ItemCategory;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.core.pc.model.BuffetDelInputDto;
import com.cnc.qr.core.pc.model.BuffetListInputDto;
import com.cnc.qr.core.pc.model.BuffetListOutputDto;
import com.cnc.qr.core.pc.model.ChangeBuffetItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeBuffetItemStatusInputDto;
import com.cnc.qr.core.pc.model.GetBuffetInputDto;
import com.cnc.qr.core.pc.model.GetBuffetOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetItemInputDto;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.ItemListDto;
import com.cnc.qr.core.pc.model.RegistBuffetInputDto;
import com.cnc.qr.core.pc.service.BuffetService;
import com.cnc.qr.core.pc.service.PcItemService;
import com.github.dozermapper.core.Mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * のみ放題コントローラ.
 */
@RestController
public class BuffetController {

    /**
     * 商品情報取得サービス.
     */
    @Autowired
    private PcItemService itemService;

    /**
     * のみ放題情報取得サービ.
     */
    @Autowired
    private BuffetService buffetService;

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
     * のみ放題一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return のみ放題一覧情報
     */
    @PostMapping(UrlConstants.PC_GET_BUFFET_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public BuffetOutputResource getBuffetList(
        @Validated @RequestBody BuffetInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        BuffetListInputDto inputDto = beanMapper.map(inputResource, BuffetListInputDto.class);

        // のみ放題一覧情報取得サービス処理を実行する
        BuffetListOutputDto outDto = buffetService.getBuffetList(inputDto, PageRequest
            .of(inputResource.getPage(), inputResource.getSize()));

        // インプット情報をDTOにセットする
        BuffetOutputResource outputDto = beanMapper.map(outDto, BuffetOutputResource.class);

        return outputDto;
    }

    /**
     * のみ放題明細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return のみ放題情報
     */
    @PostMapping(UrlConstants.PC_GET_BUFFET_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public GetBuffetOutputResource getBuffet(
        @Validated @RequestBody GetBuffetInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetBuffetInputDto buffetInputDto = beanMapper.map(inputResource, GetBuffetInputDto.class);

        // インプット情報をDTOにセットする
        GetItemInputDto itemInputDto = beanMapper.map(buffetInputDto, GetItemInputDto.class);
        itemInputDto.setItemId(buffetInputDto.getBuffetId());

        // アウトプット情報を作成する
        GetBuffetOutputResource outputResource = new GetBuffetOutputResource();
        outputResource.setSelectedItemList(new ArrayList<>());

        if (null != buffetInputDto.getBuffetId()) {

            // サービス処理を実行する
            GetBuffetOutputDto outputDto = buffetService.getBuffet(buffetInputDto);

            beanMapper.map(outputDto, outputResource);

            // 商品リスト取得
            List<ItemListDto> selectedItemList = buffetService.getSelectedItemList(buffetInputDto);
            outputResource.setSelectedItemList(selectedItemList);
        }

        //  カテゴリーID情報取得
        List<GetCategoryIdListOutputDto> categoryIdList = new ArrayList<>();
        GetCategoryIdListOutputDto categoryIdDto = new GetCategoryIdListOutputDto();
        categoryIdDto.setCategoryId(ItemCategory.BUFFET.getCode());
        categoryIdDto.setCategoryName(ItemCategory.BUFFET.getValue());
        categoryIdList.add(categoryIdDto);
        outputResource.setCategoryIdList(categoryIdList);

        // カテゴリーリスト取得
        List<GetCategoryList> categoryList = itemService.getCategoryList(itemInputDto);
        outputResource.setCategoryList(categoryList);

        // 税リスト取得
        List<GetTaxListOutputDto> taxList = itemService.getTaxList(itemInputDto);
        outputResource.setTaxList(taxList);

        // 商品リスト取得
        List<ItemListDto> itemList = buffetService.getItemList(buffetInputDto);
        outputResource.setItemList(itemList);

        // カテゴリーリスト取得
        List<GetCategoryList> hasItemCategory = buffetService
            .getHasItemCategoryList(buffetInputDto);
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
     * 飲み放題編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 飲み放題保存情報
     */
    @PostMapping(UrlConstants.PC_REGIST_BUFFET_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public RegistBuffetOutputResource registBuffet(
        @Validated @RequestBody RegistBuffetInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        RegistBuffetInputDto inputDto = beanMapper.map(inputResource, RegistBuffetInputDto.class);
        inputDto.setCategoryIdList(inputResource.getCategoryIdList());
        inputDto.setSelectedItemList(inputResource.getSelectedItemList());

        //  飲み放題サービス処理を実行する
        buffetService.saveBuffet(inputDto);

        return new RegistBuffetOutputResource();
    }

    /**
     * 飲み放題削除情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 飲み放題保存情報
     */
    @PostMapping(UrlConstants.PC_DELETE_BUFFET_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STORE_MAINTENANCE + "\")")
    public BuffetDelOutputResource delBuffet(
        @Validated @RequestBody BuffetDelInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        //  インプット情報をDTOにセットする
        BuffetDelInputDto inputDto = beanMapper.map(inputResource, BuffetDelInputDto.class);
        inputDto.setBuffetList(inputResource.getBuffetList());

        // 飲み放題サービス処理を実行する
        buffetService.delBuffet(inputDto);

        return new BuffetDelOutputResource();
    }

    /**
     * 飲み放題商品ステータス更新.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_BUFFET_STATUS)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeBuffetItemStatusOutputResource changeBuffetStatus(
        @Validated @RequestBody ChangeBuffetItemStatusInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeBuffetItemStatusInputDto inputDto = beanMapper
            .map(inputResource, ChangeBuffetItemStatusInputDto.class);
        inputDto.setBuffetList(inputResource.getBuffetList());

        //   飲み放題サービスを実行する
        buffetService.changeBuffetItemStatus(inputDto);

        return new ChangeBuffetItemStatusOutputResource();
    }

    /**
     * 飲み放題順番編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.PC_CHANGE_BUFFET_SORT_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ITEM_MAINTENANCE + "\")")
    public ChangeBuffetItemSortOrderOutputResource changeBuffetItemSortOrder(
        @Validated @RequestBody ChangeBuffetItemSortOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeBuffetItemSortOrderInputDto inputDto = beanMapper
            .map(inputResource, ChangeBuffetItemSortOrderInputDto.class);
        inputDto.setBuffetItemSortOrderList(inputResource.getBuffetItemSortOrderList());

        // 飲み放題商品順番編集サービス処理を実行する
        buffetService.changeBuffetItemSortOrder(inputDto);

        // 処理結果を設定する
        return new ChangeBuffetItemSortOrderOutputResource();
    }
}
