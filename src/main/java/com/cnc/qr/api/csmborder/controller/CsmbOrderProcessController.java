package com.cnc.qr.api.csmborder.controller;

import com.cnc.qr.api.csmborder.constants.CsmbConstants;
import com.cnc.qr.api.csmborder.resource.CourseBuffetConfirmInputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetConfirmOutputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetDetailInputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetDetailOutputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetListInputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetListOutputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetRegistInputResource;
import com.cnc.qr.api.csmborder.resource.CourseBuffetRegistOutputResource;
import com.cnc.qr.api.csmborder.resource.GetOrderInfoInputResource;
import com.cnc.qr.api.csmborder.resource.GetOrderInfoOutputResource;
import com.cnc.qr.api.csmborder.resource.GetShareOrderInfoInputResource;
import com.cnc.qr.api.csmborder.resource.GetShareOrderInfoOutputResource;
import com.cnc.qr.api.csmborder.resource.OrderInputResource;
import com.cnc.qr.api.csmborder.resource.OrderOutputResource;
import com.cnc.qr.api.csmborder.resource.OrderTransferInputResource;
import com.cnc.qr.api.csmborder.resource.OrderTransferOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.push.FcmPush;
import com.cnc.qr.core.order.model.CourseBuffetConfirmInputDto;
import com.cnc.qr.core.order.model.CourseBuffetConfirmOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetDetailInputDto;
import com.cnc.qr.core.order.model.CourseBuffetDetailOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetListInputDto;
import com.cnc.qr.core.order.model.CourseBuffetListOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetOrderOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetRegistInputDto;
import com.cnc.qr.core.order.model.GetOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetShareOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetShareOrderInfoOutputDto;
import com.cnc.qr.core.order.model.OrderInputDto;
import com.cnc.qr.core.order.model.OrderOutputDto;
import com.cnc.qr.core.order.model.OrderTransferInputDto;
import com.cnc.qr.core.order.model.OrderTransferOutputDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.github.dozermapper.core.Mapper;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
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
 * 注文処理コントローラ.
 */
@RestController
public class CsmbOrderProcessController {

    /**
     * 注文済情報取得サービス.
     */
    @Autowired
    private OrderService orderService;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 店舗サービス.
     */
    @Autowired
    StoreInfoService storeInfoService;

    /**
     * 共通部品プリンター.
     */
    @Autowired
    FcmPush fcmPush;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 共有注文情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 共有注文情報
     */
    @PostMapping(UrlConstants.CSMB_GET_SHARE_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetShareOrderInfoOutputResource getShareOrderInfo(
        @Validated @RequestBody GetShareOrderInfoInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetShareOrderInfoInputDto inputDto = beanMapper
            .map(inputResource, GetShareOrderInfoInputDto.class);

        // 共有注文情報取得サービス処理を実行する
        GetShareOrderInfoOutputDto outputDto = orderService.getShareOrderInfo(inputDto);

        // 客の商品情報リストを設定する
        return beanMapper.map(outputDto, GetShareOrderInfoOutputResource.class);
    }

    /**
     * 注文情報取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文情報
     */
    @PostMapping(UrlConstants.CSMB_GET_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetOrderInfoOutputResource getOrderInfo(
        @Validated @RequestBody GetOrderInfoInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderInfoInputDto inputDto = beanMapper.map(inputResource, GetOrderInfoInputDto.class);

        // 注文情報取得サービス処理を実行する
        GetOrderInfoOutputDto outputDto = orderService.getOrderInfo(inputDto);

        // 注文商品情報リストを設定する
        return beanMapper.map(outputDto, GetOrderInfoOutputResource.class);
    }

    /**
     * 注文確定.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 注文確定の結果
     */
    @PostMapping(UrlConstants.CSMB_ORDER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public OrderOutputResource getOrder(@Validated @RequestBody OrderInputResource inputResource,
        BindingResult result, HttpServletRequest request) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        OrderInputDto inputDto = beanMapper.map(inputResource, OrderInputDto.class);
        inputDto.setItems(inputResource.getItems());
        inputDto.setAuthorization(request.getHeader(CsmbConstants.AUTHORIZATION_HEADER));
        inputDto.setItems(inputResource.getItems());
        inputDto.setPaymentType(
            StringUtils.isEmpty(inputResource.getPaymentType()) ? PaymentType.DEFERRED_PAYMENT
                .getCode() : inputResource.getPaymentType());

        // サービス処理を実行するDto
        OrderOutputDto outputDto = orderService.getOrder(inputDto);

        if (Objects.equals(PaymentType.DEFERRED_PAYMENT.getCode(), outputDto.getAdvancePayment())) {

            // プッシュ 会計PAD
            String message = String.format("注文があります。");
            fcmPush.getPushToken(inputDto.getStoreId(), message);
        }

        // アウトプット情報を作成する
        return beanMapper.map(outputDto, OrderOutputResource.class);
    }

    /**
     * 遷移先判断.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 遷移先判断
     */
    @PostMapping(UrlConstants.CSMB_ORDER_TRANSFER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public OrderTransferOutputResource orderTransfer(
        @Validated @RequestBody OrderTransferInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        OrderTransferInputDto inputDto = beanMapper.map(inputResource, OrderTransferInputDto.class);

        // サービス処理を実行する
        OrderTransferOutputDto outputDto = orderService.orderTransfer(inputDto);

        // アウトプット情報を作成する
        OrderTransferOutputResource outputResource = new OrderTransferOutputResource();

        // 店商品カテゴリー情報を設定する
        outputResource.setTransitionType(outputDto.getTransitionType());

        return outputResource;
    }

    /**
     * コース＆放题詳細.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース＆放题詳細
     */
    @PostMapping(UrlConstants.CSMB_COURSE_BUFFET_DETIMAL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CourseBuffetDetailOutputResource courseBuffetDetail(
        @Validated @RequestBody CourseBuffetDetailInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        CourseBuffetDetailInputDto inputDto = beanMapper
            .map(inputResource, CourseBuffetDetailInputDto.class);

        // サービス処理を実行する
        CourseBuffetDetailOutputDto outputDto = orderService.courseBuffetDetail(inputDto);

        // アウトプット情報を作成する
        CourseBuffetDetailOutputResource outputResource = new CourseBuffetDetailOutputResource();
        outputResource.setCourseBuffetName(outputDto.getCourseBuffetName());
        outputResource.setCourseBuffetPrice(outputDto.getCourseBuffetPrice());
        outputResource.setCourseIntroduction(outputDto.getCourseIntroduction());
        outputResource.setTaxCode(outputDto.getTaxCode());
        outputResource.setBuffetItemList(outputDto.getBuffetItemList());
        outputResource.setCourseItemList(outputDto.getCourseItemList());
        outputResource.setBuffetIntroduction(outputDto.getBuffetIntroduction());

        return outputResource;
    }

    /**
     * コース＆放题一览取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース＆放题一览
     */
    @PostMapping(UrlConstants.CSMB_COURSE_BUFFET_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CourseBuffetListOutputResource getCourseBuffetList(
        @Validated @RequestBody CourseBuffetListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        CourseBuffetListInputDto inputDto = beanMapper
            .map(inputResource, CourseBuffetListInputDto.class);

        // コース＆放题一览取得サービス処理を実行する
        CourseBuffetListOutputDto outputDto = orderService.getCourseBuffetList(inputDto);

        // インプット情報をDTOにセットする
        CourseBuffetListOutputResource outputResource = new CourseBuffetListOutputResource();
        outputResource.setCourseBuffetList(outputDto.getCourseBuffetList());

        // コース＆放题一覧リストを設定する
        return outputResource;
    }

    /**
     * コース＆放题注文確認取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース＆放题注文確認取得
     */
    @PostMapping(UrlConstants.CSMB_COURSE_BUFFET_CONFIRM)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CourseBuffetConfirmOutputResource courseBuffetConfirm(
        @Validated @RequestBody CourseBuffetConfirmInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        CourseBuffetConfirmInputDto inputDto = new CourseBuffetConfirmInputDto();
        inputDto.setStoreId(inputResource.getStoreId());
        inputDto.setItemList(inputResource.getItemList());

        // コース＆放题一览取得サービス処理を実行する
        CourseBuffetConfirmOutputDto outputDto = orderService.courseBuffetConfirm(inputDto);

        // コース＆放题一覧リストを設定する
        return beanMapper.map(outputDto, CourseBuffetConfirmOutputResource.class);
    }

    /**
     * コース＆放题注文確定.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return コース＆放题注文確定
     */
    @PostMapping(UrlConstants.CSMB_COURSE_BUFFET_REGIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public CourseBuffetRegistOutputResource courseBuffetRegist(
        @Validated @RequestBody CourseBuffetRegistInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        CourseBuffetRegistInputDto inputDto = beanMapper
            .map(inputResource, CourseBuffetRegistInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        // コース＆放题一览取得サービス処理を実行する
        CourseBuffetOrderOutputDto outputDto = orderService.courseBuffetRegist(inputDto);

        // インプット情報をDTOにセットする
        CourseBuffetRegistOutputResource outputResource = new CourseBuffetRegistOutputResource();
        outputResource.setOrderSummaryId(outputDto.getOrderSummaryId());
        outputResource.setOrderId(outputDto.getOrderId());

        // プッシュ 会計PAD
        String message = String.format("注文があります。");
        fcmPush.getPushToken(inputDto.getStoreId(), message);

        // コース＆放题一覧リストを設定する
        return outputResource;
    }
}
