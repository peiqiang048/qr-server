package com.cnc.qr.api.stpdorder.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.api.stpdorder.resource.AmountSoldInputResource;
import com.cnc.qr.api.stpdorder.resource.AmountSoldOutputResource;
import com.cnc.qr.api.stpdorder.resource.AssignationTableInputResource;
import com.cnc.qr.api.stpdorder.resource.AssignationTableOutputResource;
import com.cnc.qr.api.stpdorder.resource.ChangeCustomerCountInputResource;
import com.cnc.qr.api.stpdorder.resource.ChangeCustomerCountOutputResource;
import com.cnc.qr.api.stpdorder.resource.ChangePrintStatusInputResource;
import com.cnc.qr.api.stpdorder.resource.ChangePrintStatusOutputResource;
import com.cnc.qr.api.stpdorder.resource.DiscardOrderInputResource;
import com.cnc.qr.api.stpdorder.resource.DiscardOrderOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetDeliveryOrderDetailInfoInputResource;
import com.cnc.qr.api.stpdorder.resource.GetDeliveryOrderDetailInfoOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetDeliveryOrderEditInfoInputResource;
import com.cnc.qr.api.stpdorder.resource.GetDeliveryOrderEditInfoOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetDeliveryOrderListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetDeliveryOrderListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetItemDetailInputResource;
import com.cnc.qr.api.stpdorder.resource.GetItemDetailOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderDetailInputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderDetailOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderFlagInputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderFlagOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderItemListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderItemListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetOrderListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetPrintOrderListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetReturnReasonInputResource;
import com.cnc.qr.api.stpdorder.resource.GetReturnReasonOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetSeatReleaseListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetSeatReleaseListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetSelectedAreaListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetSelectedAreaListOutputResource;
import com.cnc.qr.api.stpdorder.resource.QrCodeIssueDeliveryInputResource;
import com.cnc.qr.api.stpdorder.resource.QrCodeIssueDeliveryOutputResource;
import com.cnc.qr.api.stpdorder.resource.QrCodeIssueInputResource;
import com.cnc.qr.api.stpdorder.resource.QrCodeIssueOutputResource;
import com.cnc.qr.api.stpdorder.resource.ReceptionDisposalInputResource;
import com.cnc.qr.api.stpdorder.resource.ReceptionDisposalOutputResource;
import com.cnc.qr.api.stpdorder.resource.ReceptionInitInputResource;
import com.cnc.qr.api.stpdorder.resource.ReceptionInitOutputResource;
import com.cnc.qr.api.stpdorder.resource.RegistOrderInputResource;
import com.cnc.qr.api.stpdorder.resource.RegistOrderOutputResource;
import com.cnc.qr.api.stpdorder.resource.RegistReturnInputResource;
import com.cnc.qr.api.stpdorder.resource.RegistReturnOutputResource;
import com.cnc.qr.api.stpdorder.resource.ResendMailInputResource;
import com.cnc.qr.api.stpdorder.resource.ResendMailOutputResource;
import com.cnc.qr.api.stpdorder.resource.SeatReleaseInputResource;
import com.cnc.qr.api.stpdorder.resource.SeatReleaseOutputResource;
import com.cnc.qr.api.stpdorder.resource.SetTableInputResource;
import com.cnc.qr.api.stpdorder.resource.SetTableOutputResource;
import com.cnc.qr.api.stpdorder.resource.SureOrderItemInputResource;
import com.cnc.qr.api.stpdorder.resource.SureOrderItemOutputResource;
import com.cnc.qr.api.stpdorder.resource.UpdateDeliveryOrderInputResource;
import com.cnc.qr.api.stpdorder.resource.UpdateDeliveryOrderOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CodeConstants.TakeoutFlag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.shared.model.CustomerOrderInfoDto;
import com.cnc.qr.common.shared.model.KitchenOutputDto;
import com.cnc.qr.common.shared.model.QrCodeInputDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.model.SlipPrintDto;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.core.order.model.AmountSoldInputDto;
import com.cnc.qr.core.order.model.AmountSoldOutputDto;
import com.cnc.qr.core.order.model.AssignationTableInputDto;
import com.cnc.qr.core.order.model.AssignationTableOutputDto;
import com.cnc.qr.core.order.model.ChangeCustomerCountInputDto;
import com.cnc.qr.core.order.model.ChangePrintStatusInputDto;
import com.cnc.qr.core.order.model.DiscardOrderInputDto;
import com.cnc.qr.core.order.model.GetAreaDevInfoDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderDetailInfoInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderDetailInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderEditInfoInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderEditInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderListInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderListOutputDto;
import com.cnc.qr.core.order.model.GetItemDetailInputDto;
import com.cnc.qr.core.order.model.GetItemDetailOutputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailOutputDto;
import com.cnc.qr.core.order.model.GetOrderFlagInputDto;
import com.cnc.qr.core.order.model.GetOrderFlagOutputDto;
import com.cnc.qr.core.order.model.GetOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetOrderListInputDto;
import com.cnc.qr.core.order.model.GetOrderListOutputDto;
import com.cnc.qr.core.order.model.GetPrintOrderListInputDto;
import com.cnc.qr.core.order.model.GetReceivablesInfoInputDto;
import com.cnc.qr.core.order.model.GetReceivablesInfoOutputDto;
import com.cnc.qr.core.order.model.GetReturnReasonInputDto;
import com.cnc.qr.core.order.model.GetReturnReasonOutputDto;
import com.cnc.qr.core.order.model.GetSeatReleaseListInputDto;
import com.cnc.qr.core.order.model.GetSeatReleaseListOutputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListOutputDto;
import com.cnc.qr.core.order.model.OrderInputDto;
import com.cnc.qr.core.order.model.OrderOutputDto;
import com.cnc.qr.core.order.model.OrderSummaryAndOrderIdDto;
import com.cnc.qr.core.order.model.QrCodeDeliveryInputDto;
import com.cnc.qr.core.order.model.QrCodeDeliveryOutputDto;
import com.cnc.qr.core.order.model.ReceptionDisposalInputDto;
import com.cnc.qr.core.order.model.ReceptionInitInputDto;
import com.cnc.qr.core.order.model.ReceptionInitOutputDto;
import com.cnc.qr.core.order.model.RegistReturnInputDto;
import com.cnc.qr.core.order.model.RegistReturnOutputDto;
import com.cnc.qr.core.order.model.SeatReleaseInputDto;
import com.cnc.qr.core.order.model.SetTableInputDto;
import com.cnc.qr.core.order.model.SureOrderItemInputDto;
import com.cnc.qr.core.order.model.UpdateDeliveryOrderInputDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.security.jwt.TokenProvider;
import com.cnc.qr.security.service.MailService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 注文処理コントローラ.
 */
@RestController
public class StpdOrderInfoController {

    /**
     * 注文確認サービス.
     */
    @Autowired
    private OrderService orderService;

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
     * 環境変数.
     */
    @Autowired
    private Environment env;

    /**
     * ユーザ認証情報取得.
     */
    @Autowired
    @Qualifier("AuthenticationManagerImpl")
    AuthenticationManager authenticationManager;

    /**
     * トークン処理クラス.
     */
    @Autowired
    private TokenProvider tokenProvider;

    /**
     * 店舗受付報取得.
     */
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * 伝票印刷.
     */
    @Autowired
    PrintDataSharedService printDataSharedService;

    /**
     * メールサービス.
     */
    @Autowired
    private MailService mailService;

    /**
     * 注文商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文商品情報
     */
    @PostMapping(UrlConstants.STPD_GET_ITEM_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetItemDetailOutputResource getItem(
        @Validated @RequestBody GetItemDetailInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetItemDetailInputDto inputDto = beanMapper.map(inputResource, GetItemDetailInputDto.class);

        // 注文商品情報取得サービス処理を実行する
        GetItemDetailOutputDto outputDto = orderService.getOrderItemDetail(inputDto);

        // 注文商品情報を設定する
        return beanMapper.map(outputDto, GetItemDetailOutputResource.class);
    }

    /**
     * 注文状態確認.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_SURE_ORDER_ITEM_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public SureOrderItemOutputResource sureOrderItem(
        @Validated @RequestBody SureOrderItemInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        SureOrderItemInputDto inputDto = beanMapper.map(inputResource, SureOrderItemInputDto.class);

        // 注文状態確認サービス処理を実行する
        OOrderSummary orderSummaryDto = orderService.sureOrderItem(inputDto);
        // 処理結果を設定する
        SureOrderItemOutputResource outputResource = new SureOrderItemOutputResource();
        //テイクアウト　｜｜　
        if (Objects.equals(TakeoutFlag.TAKE_OUT.getCode(), orderSummaryDto.getTakeoutFlag())
            || (!Objects.isNull(orderSummaryDto.getTableId()) && Objects
            .equals(TakeoutFlag.EAT_IN.getCode(), orderSummaryDto.getTakeoutFlag()))) {
            // 確認印刷
            SlipInputDto slipInputDto = new SlipInputDto();
            slipInputDto.setStoreId(inputDto.getStoreId());
            slipInputDto.setOrderSummaryId(orderSummaryDto.getOrderSummaryId());
            slipInputDto.setOrderIdList(inputDto.getOrderIdList());
            // ユーザID
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }
            slipInputDto.setUserName(userOperCd);
            SlipPrintDto slipPrintDto = new SlipPrintDto();
            slipPrintDto.setKitchenDto(printDataSharedService.getKitchenPrintData(slipInputDto));
            slipPrintDto.setCustomerOrderInfoDto(
                printDataSharedService.getCustomerOrderPrintData(slipInputDto));

            outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        }

        return outputResource;
    }

    /**
     * 注文一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文一覧情報
     */
    @PostMapping(UrlConstants.STPD_GET_ORDER_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ResponseEntity<GetOrderListOutputResource> getOrderList(
        @Validated @RequestBody GetOrderListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderListInputDto inputDto = beanMapper.map(inputResource, GetOrderListInputDto.class);

        // 注文一覧情報取得サービス処理を実行する
        GetOrderListOutputDto outputDto = orderService.getOrderList(inputDto,
            PageRequest.of(0 < inputResource.getPage() ? inputResource.getPage() - 1 : 0,
                inputResource.getPageCount()));

        // インプット情報をDTOにセットする
        GetOrderListOutputResource outputResource = new GetOrderListOutputResource();

        // 注文一覧情報を設定する
        outputResource.setOrderList(outputDto.getOrderList().getContent());

        // 注文総件数
        outputResource.setOrderCount(outputDto.getOrderCount());

        // ページ情報を設定する
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                outputDto.getOrderList());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 人数変更.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 変更結果
     */
    @PostMapping(UrlConstants.STPD_CHANGE_CUSTOMER_COUNT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ChangeCustomerCountOutputResource changeCustomerCount(
        @Validated @RequestBody ChangeCustomerCountInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ChangeCustomerCountInputDto inputDto = beanMapper
            .map(inputResource, ChangeCustomerCountInputDto.class);

        // 人数変更処理を実行する
        orderService.changeCustomerCount(inputDto);

        return new ChangeCustomerCountOutputResource();
    }

    /**
     * 返品原因情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 返品原因情報
     */
    @PostMapping(UrlConstants.STPD_GET_RETURN_REASON_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetReturnReasonOutputResource getReturnReasonList(
        @Validated @RequestBody GetReturnReasonInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetReturnReasonInputDto inputDto = beanMapper
            .map(inputResource, GetReturnReasonInputDto.class);

        // サービス処理を実行する
        GetReturnReasonOutputDto outputDto = orderService.getReturnReasonList(inputDto);

        // インプット情報をDTOにセットする
        GetReturnReasonOutputResource outputResource = new GetReturnReasonOutputResource();

        // 返品原因情報を設定する
        outputResource.setReturnReasonList(outputDto.getReturnReason());

        return outputResource;
    }

    /**
     * 返品登録.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 変更結果
     */
    @PostMapping(UrlConstants.STPD_REGIST_RETURN_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER_CANCEL + "\")")
    public RegistReturnOutputResource registReturn(
        @Validated @RequestBody RegistReturnInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistReturnInputDto inputDto = beanMapper.map(inputResource, RegistReturnInputDto.class);

        // 人数変更処理を実行する
        RegistReturnOutputDto outputDto = orderService.registReturn(inputDto);
        // 返品商品印刷
        SlipInputDto slipInputDto = new SlipInputDto();
        //店舗ID
        slipInputDto.setStoreId(inputDto.getStoreId());
        //注文サマリID
        slipInputDto.setOrderSummaryId(outputDto.getOrderSummaryId());
        // 注文ID
        List<Integer> orderIdList = new ArrayList<>();
        orderIdList.add(inputDto.getOrderId());
        slipInputDto.setOrderIdList(orderIdList);
        //商品詳細
        slipInputDto.setOrderDetailId(outputDto.getOrderDetailId());
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        slipInputDto.setUserName(userOperCd);
        // 商品
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        // 客用印刷
        slipPrintDto.setCustomerOrderInfoDto(
            printDataSharedService.getCustomerOrderPrintData(slipInputDto));
        //キッチン印刷
        slipPrintDto.setKitchenDto(printDataSharedService.getKitchenPrintData(slipInputDto));

        RegistReturnOutputResource outputResource = new RegistReturnOutputResource();
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        return outputResource;
    }

    /**
     * 注文商品情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文商品情報
     */
    @PostMapping(UrlConstants.STPD_GET_ORDER_ITEM_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetOrderItemListOutputResource getOrderItemList(
        @Validated @RequestBody GetOrderItemListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderItemListInputDto inputDto = beanMapper
            .map(inputResource, GetOrderItemListInputDto.class);

        // 注文商品リスト取得サービス処理を実行する
        GetOrderItemListOutputDto outputDto = orderService.getOrderItemList(inputDto);

        // インプット情報をDTOにセットする
        GetOrderItemListOutputResource outputResource = new GetOrderItemListOutputResource();

        // 注文商品リストを設定する
        outputResource.setOrderStatus(outputDto.getOrderStatus());
        outputResource.setCustomerCount(outputDto.getCustomerCount());
        outputResource.setOrderAmount(outputDto.getOrderAmount());
        outputResource.setItemList(outputDto.getItemList());
        outputResource.setAccountsType(outputDto.getAccountsType());

        return outputResource;
    }

    /**
     * 注文.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_REGIST_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public RegistOrderOutputResource registOrder(
        @Validated @RequestBody RegistOrderInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        OrderInputDto inputDto = beanMapper.map(inputResource, OrderInputDto.class);
        inputDto.setItems(inputResource.getItems());

        // 注文サービス処理を実行する
        OrderOutputDto outputDto = orderService.registOrder(inputDto);
        // 伝票印刷データ取得
        SlipInputDto slipInputDto = new SlipInputDto();
        slipInputDto.setOrderSummaryId(outputDto.getOrderSummaryId());
        slipInputDto.setStoreId(inputResource.getStoreId());
        List<Integer> orderList = new ArrayList<>();
        orderList.add(outputDto.getOrderId());
        slipInputDto.setOrderIdList(orderList);
        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        slipInputDto.setUserName(userOperCd);
        KitchenOutputDto kitchenOutputDto = printDataSharedService
            .getKitchenPrintData(slipInputDto);
        CustomerOrderInfoDto customerOrderInfoDto = printDataSharedService
            .getCustomerOrderPrintData(slipInputDto);
        SlipPrintDto slipPrintDto = new SlipPrintDto();

        slipPrintDto.setCustomerOrderInfoDto(customerOrderInfoDto);
        slipPrintDto.setKitchenDto(kitchenOutputDto);
        RegistOrderOutputResource outputResource = new RegistOrderOutputResource();
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        // 処理結果を設定する
        return outputResource;
    }

    /**
     * 配席.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_SET_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public SetTableOutputResource setTable(
        @Validated @RequestBody SetTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        SetTableInputDto inputDto = beanMapper.map(inputResource, SetTableInputDto.class);
        inputDto.setTableId(inputResource.getTableId());
        // 配席サービス処理を実行する
        orderService.setTable(inputDto);
        SetTableOutputResource outputResource = new SetTableOutputResource();
        OrderSummaryAndOrderIdDto orderSummaryAndOrderIdDto = orderService
            .getOrderSummaryAndOrderId(inputDto);
        if (!Objects.isNull(orderSummaryAndOrderIdDto)) {
            // 伝票印刷データ取得
            SlipInputDto slipInputDto = new SlipInputDto();
            slipInputDto.setOrderSummaryId(orderSummaryAndOrderIdDto.getOrderSummaryId());
            slipInputDto.setStoreId(inputResource.getStoreId());
            List<Integer> orderList = new ArrayList<>();
            orderList.addAll(orderSummaryAndOrderIdDto.getOrderIdList());
            slipInputDto.setOrderIdList(orderList);
            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }
            slipInputDto.setUserName(userOperCd);
            KitchenOutputDto kitchenOutputDto = printDataSharedService
                .getKitchenPrintData(slipInputDto);
            CustomerOrderInfoDto customerOrderInfoDto = printDataSharedService
                .getCustomerOrderPrintData(slipInputDto);
            SlipPrintDto slipPrintDto = new SlipPrintDto();

            slipPrintDto.setCustomerOrderInfoDto(customerOrderInfoDto);
            slipPrintDto.setKitchenDto(kitchenOutputDto);
            outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        }

        // 処理結果を設定する
        return outputResource;
    }

    /**
     * QRコード発行.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return QRコード発行情報
     */
    @PostMapping(UrlConstants.STPD_QR_CODE_ISSUE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public QrCodeIssueOutputResource qrCodeIssue(
        @Validated @RequestBody QrCodeIssueInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // ユーザ認証情報設定
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(env.getProperty("qr.env.mobile.login.user"),
                env.getProperty("qr.env.mobile.login.password"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("storeId", inputResource.getStoreId()); // 店舗ID
        jsonObject.put("userCd", CommonConstants.OPER_CD_MOBILE); // 登録者スマホ
        authenticationToken.setDetails(jsonObject.toString());

        // トークン生成
        Authentication authentication = this.authenticationManager
            .authenticate(authenticationToken);
        String jwt = "";
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        // ユーザ権限がある場合
        if (StringUtils.isNotEmpty(authorities)) {
            jwt = tokenProvider.createToken(authentication, false,
                storeInfoService.getTokenValidityInMilliseconds(inputResource.getStoreId()));
        }

        // 結果設定
        QrCodeIssueOutputResource outputResource = new QrCodeIssueOutputResource();
        outputResource.setAccessToken(jwt);

        // 店舗受付情報取得
        GetReceivablesInfoInputDto inputDto = new GetReceivablesInfoInputDto();
        inputDto.setStoreId(inputResource.getStoreId());
        inputDto.setCustomerCount(Integer.parseInt(inputResource.getCustomerCount()));
        inputDto.setTableId(inputResource.getTableId());
        inputDto.setBuffetId(inputResource.getBuffetId());
        inputDto.setCourseId(inputResource.getCourseId());
        GetReceivablesInfoOutputDto outputDto = storeInfoService.getReceivablesInfo(inputDto);
        outputResource.setReceivablesId(outputDto.getReceivablesId());
        // トークン情報登録
        if (StringUtils.isNotEmpty(jwt)) {
            storeInfoService
                .storeTokenRegister(inputResource.getStoreId(), outputDto.getReceivablesId(), jwt);
        }
        QrCodeInputDto qrCodeInputDto = new QrCodeInputDto();
        qrCodeInputDto.setReceivablesId(outputDto.getReceivablesId());
        qrCodeInputDto.setStoreId(inputResource.getStoreId());
        if (inputResource.getTableId().size() == 0) {
            qrCodeInputDto.setTableId(null);
        } else {
            qrCodeInputDto.setTableId(inputResource.getTableId().get(0).getTableId());
        }
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        qrCodeInputDto.setUserName(userOperCd);
        qrCodeInputDto.setToken(jwt);
        outputDto.setPrintInfo(printDataSharedService.getQrCodePrintData(qrCodeInputDto));

        return beanMapper.map(outputDto, QrCodeIssueOutputResource.class);
    }


    /**
     * 受付番号廃棄.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 受付番号廃棄
     */
    @PostMapping(UrlConstants.STPD_RECEPTION_DISPOSAL_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ReceptionDisposalOutputResource receptionDisposal(
        @Validated @RequestBody ReceptionDisposalInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ReceptionDisposalInputDto inputDto = beanMapper
            .map(inputResource, ReceptionDisposalInputDto.class);

        // 受付番号廃棄サービス処理を実行する
        orderService.receptionDisposal(inputDto);

        return new ReceptionDisposalOutputResource();
    }


    /**
     * 受付画面_（初期化&検索）&配席一覧画面_配席.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 受付画面_（初期化&検索）&配席一覧画面_配席
     */
    @PostMapping(UrlConstants.STPD_RECEPTION_INIT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ReceptionInitOutputResource getReceptionInit(
        @Validated @RequestBody ReceptionInitInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        ReceptionInitInputDto inputDto = beanMapper.map(inputResource, ReceptionInitInputDto.class);

        // 受付番号選択可能サービス処理を実行する
        ReceptionInitOutputDto outputDto = orderService.getReceptionInit(inputDto);
        // インプット情報をDTOにセットする
        ReceptionInitOutputResource outputResource = beanMapper
            .map(outputDto, ReceptionInitOutputResource.class);

        return outputResource;
    }


    /**
     * 配せき情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 配せき情報取得
     */
    @PostMapping(UrlConstants.STPD_ASSIGNATION_TABLE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public AssignationTableOutputResource assignationTable(
        @Validated @RequestBody AssignationTableInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        AssignationTableInputDto inputDto = beanMapper
            .map(inputResource, AssignationTableInputDto.class);

        // 受付番号選択可能サービス処理を実行する
        AssignationTableOutputDto outputDto = orderService.assignationTable(inputDto);
        // インプット情報をDTOにセットする
        AssignationTableOutputResource outputResource = beanMapper
            .map(outputDto, AssignationTableOutputResource.class);

        return outputResource;
    }

    /**
     * 注文詳細情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文詳細情報取得
     */
    @PostMapping(UrlConstants.STPD_ORDER_DETAIL_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetOrderDetailOutputResource getOrderDetail(
        @Validated @RequestBody GetOrderDetailInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderDetailInputDto inputDto = beanMapper
            .map(inputResource, GetOrderDetailInputDto.class);

        // 注文詳細サービス処理を実行する
        GetOrderDetailOutputDto outputDto = orderService.getOrderDetail(inputDto);
        // インプット情報をDTOにセットする
        GetOrderDetailOutputResource outputResource = beanMapper
            .map(outputDto, GetOrderDetailOutputResource.class);
        outputResource.setItemList(outputDto.getItemList());

        return outputResource;
    }

    /**
     * 売上一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文一覧情報
     */
    @PostMapping(UrlConstants.STPD_AMOUNT_SOLD_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ResponseEntity<AmountSoldOutputResource> amountSold(
        @Validated @RequestBody AmountSoldInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        AmountSoldInputDto inputDto = beanMapper.map(inputResource, AmountSoldInputDto.class);

        // 注文一覧情報取得サービス処理を実行する
        AmountSoldOutputDto outputDto = orderService.amountSold(inputDto,
            PageRequest.of(inputResource.getPage(), inputResource.getPageCount()));

        // インプット情報をDTOにセットする
        AmountSoldOutputResource outputResource = new AmountSoldOutputResource();
        // 支払金額
        outputResource.setTotalPay(outputDto.getTotalPay());
        // 注文一覧情報を設定する
        outputResource.setAmountSold(outputDto.getAmountSold().getContent());

        // 注文総件数
        outputResource.setSoldOrderCount(outputDto.getSoldOrderCount());

        // ページ情報を設定する
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                outputDto.getAmountSold());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 席解除一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 席解除一覧情報
     */
    @PostMapping(UrlConstants.STPD_SEAT_RELEASE_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetSeatReleaseListOutputResource getSeatReleaseList(
        @Validated @RequestBody GetSeatReleaseListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetSeatReleaseListInputDto inputDto = beanMapper
            .map(inputResource, GetSeatReleaseListInputDto.class);

        // 席解除リスト取得サービス処理を実行する
        GetSeatReleaseListOutputDto outputDto = orderService.getSeatReleaseList(inputDto);

        // インプット情報をDTOにセットする
        GetSeatReleaseListOutputResource outputResource = new GetSeatReleaseListOutputResource();

        // 席解除リストを設定する
        outputResource.setSeatReleaseList(outputDto.getSeatReleaseList());

        return outputResource;
    }

    /**
     * 席解除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 解除結果
     */
    @PostMapping(UrlConstants.STPD_SEAT_RELEASE_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public SeatReleaseOutputResource seatRelease(
        @Validated @RequestBody SeatReleaseInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        SeatReleaseInputDto inputDto = beanMapper.map(inputResource, SeatReleaseInputDto.class);

        // 座席変更サービス処理を実行する
        orderService.seatRelease(inputDto);

        return new SeatReleaseOutputResource();
    }

    /**
     * 注文フラグ取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 解除結果
     */
    @PostMapping(UrlConstants.STPD_GET_ORDER_FLAG)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetOrderFlagOutputResource getOrderFlag(
        @Validated @RequestBody GetOrderFlagInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderFlagInputDto inputDto = beanMapper.map(inputResource, GetOrderFlagInputDto.class);

        // 座席変更サービス処理を実行する
        GetOrderFlagOutputDto outputDto = orderService.getOrderFlag(inputDto);

        GetOrderFlagOutputResource outputResource = new GetOrderFlagOutputResource();
        beanMapper.map(outputDto, outputResource);

        return outputResource;
    }

    /**
     * 注文廃棄.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 解除結果
     */
    @PostMapping(UrlConstants.STPD_DISCARD_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public DiscardOrderOutputResource discardOrder(
        @Validated @RequestBody DiscardOrderInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DiscardOrderInputDto inputDto = beanMapper.map(inputResource, DiscardOrderInputDto.class);

        // 座席変更サービス処理を実行する
        orderService.discardOrder(inputDto);

        return new DiscardOrderOutputResource();
    }

    /**
     * 注文一覧取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文一覧情報
     */
    @PostMapping(UrlConstants.STPD_GET_DELIVERY_ORDER_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ResponseEntity<GetDeliveryOrderListOutputResource> getDeliveryOrderList(
        @Validated @RequestBody GetDeliveryOrderListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliveryOrderListInputDto inputDto = beanMapper
            .map(inputResource, GetDeliveryOrderListInputDto.class);

        if (null == inputDto.getDeliveryTypeFlag()) {
            inputDto.setDeliveryTypeFlag("");
        }

        if (null == inputDto.getStatus()) {
            inputDto.setStatus("");
        }

        // 出前注文一覧情報取得サービス処理を実行する
        GetDeliveryOrderListOutputDto outputDto = orderService.getDeliveryOrderList(inputDto,
            PageRequest.of(0 < inputResource.getPage() ? inputResource.getPage() - 1 : 0,
                inputResource.getPageCount()));

        // インプット情報をDTOにセットする
        GetDeliveryOrderListOutputResource outputResource = new GetDeliveryOrderListOutputResource();

        // 出前仕方リスト情報を設定する
        outputResource.setDeliveryTypeFlagList(outputDto.getDeliveryTypeFlagList());

        // 状態リスト情報を設定する
        outputResource.setStatusList(outputDto.getStatusList());

        // 出前注文一覧情報を設定する
        outputResource.setDeliveryOrderList(outputDto.getDeliveryOrderList().getContent());

        // 出前注文総件数
        outputResource.setDeliveryOrderCount(outputDto.getDeliveryOrderCount());

        // ページ情報を設定する
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
                outputDto.getDeliveryOrderList());

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 注文明細取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文明細情報
     */
    @PostMapping(UrlConstants.STPD_GET_DELIVERY_ORDER_DETAIL_INFO_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetDeliveryOrderDetailInfoOutputResource getDeliveryOrderDetailInfo(
        @Validated @RequestBody GetDeliveryOrderDetailInfoInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliveryOrderDetailInfoInputDto inputDto = beanMapper
            .map(inputResource, GetDeliveryOrderDetailInfoInputDto.class);

        // 出前注文詳細サービス処理を実行する
        GetDeliveryOrderDetailInfoOutputDto outputDto = orderService
            .getDeliveryOrderDetailInfo(inputDto);
        // インプット情報をDTOにセットする
        GetDeliveryOrderDetailInfoOutputResource outputResource = beanMapper
            .map(outputDto, GetDeliveryOrderDetailInfoOutputResource.class);
        outputResource.setItemList(outputDto.getItemList());

        return outputResource;
    }

    /**
     * 注文編集取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文編集情報
     */
    @PostMapping(UrlConstants.STPD_GET_DELIVERY_ORDER_EDIT_INFO_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetDeliveryOrderEditInfoOutputResource getDeliveryOrderEditInfo(
        @Validated @RequestBody GetDeliveryOrderEditInfoInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetDeliveryOrderEditInfoInputDto inputDto = beanMapper
            .map(inputResource, GetDeliveryOrderEditInfoInputDto.class);

        // 出前注文編集サービス処理を実行する
        GetDeliveryOrderEditInfoOutputDto outputDto = orderService
            .getDeliveryOrderEditInfo(inputDto);
        // インプット情報をDTOにセットする
        GetDeliveryOrderEditInfoOutputResource outputResource = beanMapper
            .map(outputDto, GetDeliveryOrderEditInfoOutputResource.class);
        outputResource.setCateringTimeList(outputDto.getCateringTimeList());
        outputResource.setTakeoutTimeList(outputDto.getTakeoutTimeList());
        outputResource.setDeliveryTypeFlagList(outputDto.getDeliveryTypeFlagList());
        outputResource.setStatusList(outputDto.getStatusList());
        outputResource.setPrefectureList(outputDto.getPrefectureList());
        outputResource.setCityList(outputDto.getCityList());
        outputResource.setBlockList(outputDto.getBlockList());

        return outputResource;
    }

    /**
     * 店舗指定区域取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 店舗指定区域情報
     */
    @PostMapping(UrlConstants.STPD_SELECTED_AREA_LIST_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public GetSelectedAreaListOutputResource getSelectedAreaList(
        @Validated @RequestBody GetSelectedAreaListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetSelectedAreaListInputDto inputDto = beanMapper
            .map(inputResource, GetSelectedAreaListInputDto.class);

        // 店舗指定区域情報取得サービス処理を実行する
        GetSelectedAreaListOutputDto outputDto = orderService.getSelectedAreaList(inputDto);

        // インプット情報をDTOにセットする
        GetSelectedAreaListOutputResource outputResource = new GetSelectedAreaListOutputResource();

        // 店舗指定区域情報を設定する
        List<GetAreaDevInfoDto> areaList = new ArrayList<>();
        outputDto.getAreaList().forEach(areaInfoDto -> {
            GetAreaDevInfoDto areaInfo = new GetAreaDevInfoDto();
            areaInfo.setAreaId(areaInfoDto.getAreaId());
            areaInfo.setAreaName(areaInfoDto.getAreaName());
            areaList.add(areaInfo);
        });

        outputResource.setAreaList(areaList);

        return outputResource;
    }

    /**
     * 注文編集.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_UPDATE_DELIVERY_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public UpdateDeliveryOrderOutputResource updateDeliveryOrder(
        @Validated @RequestBody UpdateDeliveryOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        UpdateDeliveryOrderInputDto inputDto = beanMapper
            .map(inputResource, UpdateDeliveryOrderInputDto.class);

        // 出前注文編集サービス処理を実行する
        orderService.updateDeliveryOrder(inputDto);

        return new UpdateDeliveryOrderOutputResource();
    }

    /**
     * メール再送信.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STPD_RESEND_MAIL_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public ResendMailOutputResource resendMail(
        @Validated @RequestBody ResendMailInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // メールタイトル
        String subject = "受付ID再送信";
        // メール内容
        String content = inputResource.getCustomerName() + "様<BR><BR>ご注文した受付IDは" + inputResource
            .getReceivablesId() + "です。<BR>ご利用ください。";

        // メール再送信サービス処理を実行する
        mailService.sendEmail(inputResource.getMailAddress(), subject, content, false, true);

        return new ResendMailOutputResource();
    }

    /**
     * 出前QRコード発行.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return QRコード発行情報
     */
    @PostMapping(UrlConstants.STPD_QR_CODE_ISSUE_DELIVERY_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ORDER + "\")")
    public QrCodeIssueDeliveryOutputResource qrCodeIssueDelivery(
        @Validated @RequestBody QrCodeIssueDeliveryInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        QrCodeDeliveryInputDto inputDto = new QrCodeDeliveryInputDto();
        inputDto.setStoreId(inputResource.getStoreId());
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        inputDto.setUserName(userOperCd);
        QrCodeDeliveryOutputDto outputDto = new QrCodeDeliveryOutputDto();
        outputDto.setPrintInfo(printDataSharedService.getQrCodeDeliveryPrintData(inputDto));

        return beanMapper.map(outputDto, QrCodeIssueDeliveryOutputResource.class);
    }

    /**
     * 印刷注文検索.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 印刷注文検索
     */
    @PostMapping(UrlConstants.STPD_GET_PRINT_ORDER_LIST)
    @ResponseStatus(HttpStatus.OK)

    public List<String> getPrintOrderList(
        @Validated @RequestBody GetPrintOrderListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        GetPrintOrderListInputDto inputDto = beanMapper
            .map(inputResource, GetPrintOrderListInputDto.class);
        List<String> output = printDataSharedService.getPrintOrderList(inputDto);
        return output;
    }

    /**
     * 印刷状態変更.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 印刷状態変更
     */
    @PostMapping(UrlConstants.STPD_CHANGE_PRINT_STATUS)
    @ResponseStatus(HttpStatus.OK)
    public ChangePrintStatusOutputResource changePrintStatus(
        @Validated @RequestBody ChangePrintStatusInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        ChangePrintStatusInputDto inputDto = new ChangePrintStatusInputDto();
        inputDto.setOrderList(inputResource.getOrderList());

        printDataSharedService.changePrintStatus(inputDto);

        return new ChangePrintStatusOutputResource();
    }
}
