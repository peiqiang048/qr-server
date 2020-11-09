package com.cnc.qr.api.stmborder.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.api.stmborder.resource.DeleteItemInputResource;
import com.cnc.qr.api.stmborder.resource.DeleteItemOutputResource;
import com.cnc.qr.api.stmborder.resource.EmptyItemInputResource;
import com.cnc.qr.api.stmborder.resource.EmptyItemOutputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderHistoryListInputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderHistoryListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderInputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderItemListInputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderItemListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderListInputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderListOutputResource;
import com.cnc.qr.api.stmborder.resource.GetOrderOutputResource;
import com.cnc.qr.api.stmborder.resource.InitOrderInputResource;
import com.cnc.qr.api.stmborder.resource.InitOrderOutputResource;
import com.cnc.qr.api.stmborder.resource.RegisterOrderInputResource;
import com.cnc.qr.api.stmborder.resource.RegisterOrderOutputResource;
import com.cnc.qr.api.stmborder.resource.SureOrderInputResource;
import com.cnc.qr.api.stmborder.resource.SureOrderOutputResource;
import com.cnc.qr.api.stpdorder.resource.QrCodeIssueOutputResource;
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
import com.cnc.qr.core.order.model.DeleteItemInputDto;
import com.cnc.qr.core.order.model.EmptyItemInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInfoInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInfoOutputDto;
import com.cnc.qr.core.order.model.GetOrderHistoryListInputDto;
import com.cnc.qr.core.order.model.GetOrderHistoryListOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListOutputDto;
import com.cnc.qr.core.order.model.InitOrderInputDto;
import com.cnc.qr.core.order.model.InitOrderOutputDto;
import com.cnc.qr.core.order.model.OrderInputDto;
import com.cnc.qr.core.order.model.OrderOutputDto;
import com.cnc.qr.core.order.model.SureOrderItemInputDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.security.jwt.TokenProvider;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注文処理コントローラ.
 */
@RestController
public class StmbOrderInfoController {

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
     * プリンターデータ取得.
     */
    @Autowired
    PrintDataSharedService printDataSharedService;

    /**
     * 注文確認未済商品クリア.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STMB_EMPTY_ITEM_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public EmptyItemOutputResource emptyItem(
        @Validated @RequestBody EmptyItemInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        EmptyItemInputDto inputDto = beanMapper.map(inputResource, EmptyItemInputDto.class);
        inputDto.setOrderIdList(inputResource.getOrderIdList());

        // 注文確認未済商品クリアサービス処理を実行する
        orderService.emptyItem(inputDto);

        return new EmptyItemOutputResource();
    }

    /**
     * 注文確認未済商品削除.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STMB_DELETE_ITEM_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public DeleteItemOutputResource deleteItem(
        @Validated @RequestBody DeleteItemInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        DeleteItemInputDto inputDto = beanMapper.map(inputResource, DeleteItemInputDto.class);

        // 注文確認未済商品削除サービス処理を実行する
        orderService.deleteItem(inputDto);

        return new DeleteItemOutputResource();
    }

    /**
     * 受付情報設定.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STMB_INIT_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public InitOrderOutputResource initOrder(
        @Validated @RequestBody InitOrderInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        InitOrderInputDto inputDto = beanMapper.map(inputResource, InitOrderInputDto.class);
        inputDto.setBuffetId(inputResource.getBuffetId());
        inputDto.setCourseId(inputResource.getCourseId());

        // 受付情報設定サービス処理を実行する
        InitOrderOutputDto outputDto = orderService.initOrder(inputDto);

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
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = "";
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        // ユーザ権限がある場合
        if (StringUtils.isNotEmpty(authorities)) {
            jwt = tokenProvider.createToken(authentication, false,
                storeInfoService.getTokenValidityInMilliseconds(inputResource.getStoreId()));
        }
        // トークン情報登録
        if (StringUtils.isNotEmpty(jwt)) {
            storeInfoService
                .storeTokenRegister(inputResource.getStoreId(), outputDto.getReceivablesId(), jwt);
        }
        // 結果設定
        QrCodeIssueOutputResource outputResource = new QrCodeIssueOutputResource();
        outputResource.setAccessToken(jwt);

        QrCodeInputDto qrCodeInputDto = new QrCodeInputDto();
        qrCodeInputDto.setReceivablesId(outputDto.getReceivablesId());
        qrCodeInputDto.setStoreId(inputResource.getStoreId());
        qrCodeInputDto.setTableId(inputResource.getTableId());
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        qrCodeInputDto.setUserName(userOperCd);
        qrCodeInputDto.setToken(jwt);
        SlipPrintDto slipPrintDto = new SlipPrintDto();

        outputDto.setPrintInfo(printDataSharedService.getQrCodePrintData(qrCodeInputDto));

        return beanMapper.map(outputDto, InitOrderOutputResource.class);
    }

    /**
     * 注文一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文一覧情報
     */
    @PostMapping(UrlConstants.STMB_GET_ORDER_LIST_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    @ResponseStatus(HttpStatus.OK)
    public GetOrderListOutputResource getOrderList(
        @Validated @RequestBody GetOrderListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetUnCfmOrderListInputDto inputDto = beanMapper
            .map(inputResource, GetUnCfmOrderListInputDto.class);

        // メニューリスト取得
        GetUnCfmOrderListOutputDto outputDto = orderService.getUnCfmOrderList(inputDto);

        // インプット情報をDTOにセットする
        GetOrderListOutputResource outputResource = new GetOrderListOutputResource();
        outputResource.setOrderList(outputDto.getOrderList());

        return outputResource;
    }

    /**
     * 注文履歴取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文履歴情報
     */
    @PostMapping(UrlConstants.STMB_GET_ORDER_HISTORY_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    @ResponseStatus(HttpStatus.OK)
    public GetOrderHistoryListOutputResource getOrderHistoryList(
        @Validated @RequestBody GetOrderHistoryListInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderHistoryListInputDto inputDto = beanMapper
            .map(inputResource, GetOrderHistoryListInputDto.class);

        // 注文履歴リスト取得
        GetOrderHistoryListOutputDto outputDto = orderService.getOrderHistoryList(inputDto);

        // インプット情報をDTOにセットする
        GetOrderHistoryListOutputResource outputResource = new GetOrderHistoryListOutputResource();
        outputResource.setOrderAmount(outputDto.getOrderAmount());
        outputResource.setOrderHistoryList(outputDto.getOrderHistoryList());
        outputResource.setStaffAccountAbleFlag(outputDto.getStaffAccountAbleFlag());
        outputResource.setReceptionNo(outputDto.getReceptionNo());
        outputResource.setTableName(outputDto.getTableName());
        return outputResource;
    }

    /**
     * 注文未確認商品一覧情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文一覧情報
     */
    @PostMapping(UrlConstants.STMB_GET_ORDER_ITEM_LIST_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    @ResponseStatus(HttpStatus.OK)
    public GetOrderItemListOutputResource getOrderItemList(
        @Validated @RequestBody GetOrderItemListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetUnCfmOrderItemListInputDto inputDto = beanMapper
            .map(inputResource, GetUnCfmOrderItemListInputDto.class);

        // メニューリスト取得
        GetUnCfmOrderItemListOutputDto outputDto = orderService.getUnCfmOrderItemList(inputDto);

        // インプット情報をDTOにセットする
        GetOrderItemListOutputResource outputResource = new GetOrderItemListOutputResource();
        outputResource.setUnconfirmedAmount(outputDto.getUnconfirmedAmount());
        outputResource.setItemList(outputDto.getItemList());

        return outputResource;
    }

    /**
     * 注文状態確認.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STMB_SURE_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public SureOrderOutputResource sureOrder(
        @Validated @RequestBody SureOrderInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        SureOrderItemInputDto inputDto = beanMapper.map(inputResource, SureOrderItemInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        // 処理結果を設定する
        SureOrderOutputResource outputResource = new SureOrderOutputResource();
        // 注文状態確認サービス処理を実行する
        OOrderSummary orderSummaryDto = orderService.sureOrderItem(inputDto);
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
        // 処理結果を設定する
        return outputResource;

    }

    /**
     * 注文.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 処理結果
     */
    @PostMapping(UrlConstants.STMB_REGIST_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public RegisterOrderOutputResource registerOrder(
        @Validated @RequestBody RegisterOrderInputResource inputResource, BindingResult result) {

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
        RegisterOrderOutputResource outputResource = new RegisterOrderOutputResource();
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        outputResource.setStaffAccountAbleFlag(outputDto.getStaffAccountAbleFlag());
        // 処理結果を設定する
        return outputResource;
    }

    /**
     * 注文情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文情報
     */
    @PostMapping(UrlConstants.STMB_GET_ORDER_URL)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    @ResponseStatus(HttpStatus.OK)
    public GetOrderOutputResource getOrder(
        @Validated @RequestBody GetOrderInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderDetailInfoInputDto inputDto = beanMapper
            .map(inputResource, GetOrderDetailInfoInputDto.class);

        // 注文サービス処理を実行する
        GetOrderDetailInfoOutputDto outputDto = orderService.getOrderDetailInfo(inputDto);

        return beanMapper.map(outputDto, GetOrderOutputResource.class);
    }


}
