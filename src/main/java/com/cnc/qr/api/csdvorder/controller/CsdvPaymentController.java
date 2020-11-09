package com.cnc.qr.api.csdvorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.api.csdvorder.constants.CsdvConstants;
import com.cnc.qr.api.csdvorder.resource.GetPayUrlInputResource;
import com.cnc.qr.api.csdvorder.resource.GetPayUrlOutputResource;
import com.cnc.qr.api.csdvorder.resource.GetSbPaymentInfoInputResource;
import com.cnc.qr.api.csdvorder.resource.GetSbPaymentInfoOutputResource;
import com.cnc.qr.api.csdvorder.resource.SbPaymentCallBackInputResource;
import com.cnc.qr.api.csdvorder.resource.SbPaymentCallBackOutputResource;
import com.cnc.qr.api.csdvorder.resource.WeChatAliPayBackInputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.AppTokenStatus;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ResResult;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.entity.ODeliveryOrderSummary;
import com.cnc.qr.common.exception.AppTokenException;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.push.FcmPush;
import com.cnc.qr.common.repository.ODeliveryOrderSummaryRepository;
import com.cnc.qr.common.shared.model.TaxAmountInputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.acct.model.GetSbPaymentInfoInputDto;
import com.cnc.qr.core.acct.model.GetSbPaymentInfoOutputDto;
import com.cnc.qr.core.acct.model.SbPaymentCallBackInputDto;
import com.cnc.qr.core.acct.model.WeChatAliPayBackInputDto;
import com.cnc.qr.core.acct.service.PaymentService;
import com.cnc.qr.core.acct.service.WeChatAliPayService;
import com.cnc.qr.core.order.model.GetPayUrlInputDto;
import com.cnc.qr.core.order.model.GetPayUrlOutputDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.security.service.MailService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品情報コントローラ.
 */
@RestController
public class CsdvPaymentController {

    /**
     * 先払いサービス.
     */
    @Autowired
    private WeChatAliPayService wechatAliPayService;

    /**
     * 会計処理サービス.
     */
    @Autowired
    private PaymentService paymentService;

    /**
     * 注文サービス.
     */
    @Autowired
    private OrderService orderService;

    /**
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * メールサービス.
     */
    @Autowired
    private MailService mailService;

    /**
     * 出前注文サマリテーブルタリポジトリ.
     */
    @Autowired
    private ODeliveryOrderSummaryRepository deliveryOrderSummaryRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * プッシュ通知.
     */
    @Autowired
    private FcmPush fcmPush;

    /**
     * 支払いURL取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 支払いURL情報
     */
    @PostMapping(UrlConstants.CSDV_GET_PAY_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetPayUrlOutputResource getPayUrl(
        @Validated @RequestBody GetPayUrlInputResource inputResource, BindingResult result,
        HttpServletRequest request) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetPayUrlInputDto inputDto = beanMapper.map(inputResource, GetPayUrlInputDto.class);
        inputDto.setMaxId("01");
        inputDto.setNo("1");

        // 支払いデフォルトデータ設定
        Integer maxDetailId = wechatAliPayService.insetPayResultData(inputDto);
        // 最大明細ID
        inputDto.setMaxDetailId(maxDetailId);
        // 認証トークン
        inputDto.setToken(request.getHeader(CsdvConstants.AUTHORIZATION_HEADER).substring(7));

        // サービス処理を実行する
        GetPayUrlOutputDto outputDto = wechatAliPayService
            .getPayUrl(inputDto);

        // アウトプット情報を作成する
        GetPayUrlOutputResource outputResource = new GetPayUrlOutputResource();

        // 店商品カテゴリー情報を設定する
        outputResource.setPayUrl(outputDto.getPayUrl());

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(inputDto.getOrderSummaryId());
        taxAmountInputDto.setOrderId(inputDto.getOrderId());
        taxAmountInputDto.setStoreId(inputDto.getStoreId());
        taxAmountInputDto.setTaxAmountType(1);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        itemInfoSharedService.registTaxAmount(taxAmountInputDto);

        return outputResource;
    }

    /**
     * DB状態更新.
     *
     * @param inputResource リクエストパラメータ
     * @return SUCCESS
     */
    @GetMapping(UrlConstants.CSDV_WECHATALI_PAY_BACKURL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    @Transactional
    public String weChatAliPayBack(@Valid WeChatAliPayBackInputResource inputResource) {

        // 認証トークン
        String token = JSONObject.parseObject(inputResource.getAttach()).getString("token");

        // 認証トークンチェック
        if (!Objects.equals(Md5Util.toMd5(CommonConstants.DEFAULT_PASSWORD), token)) {
            throw new AppTokenException(AppTokenStatus.APP_TOKEN_INVALID.getStatusCode());
        }

        // インプット情報をDTOにセットする
        WeChatAliPayBackInputDto inputDto = beanMapper
            .map(inputResource, WeChatAliPayBackInputDto.class);

        // 繰り返し処理判断
        Integer count = wechatAliPayService.getCount(inputDto);

        if (count != 0) {

            // 支払い状態更新
            wechatAliPayService.updatePayStatus(inputDto);

            if (inputResource.getRespCode().equals("00")) {

                // 支払い情報を作成する
                wechatAliPayService.insertPayment(inputDto);

                // 注文支払い状態を更新する
                orderService.changeDeliveryStatus(inputDto);

                // 店舗ID取得
                String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

                // 注文サマリID
                String orderSummaryId = inputDto.getOrderNum().substring(0, 20);

                // 税額テーブル登録
                TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
                taxAmountInputDto.setOrderSummaryId(orderSummaryId);
                taxAmountInputDto.setOrderId(Integer.valueOf(inputDto.getOrderNum()
                    .substring(20, inputDto.getOrderNum().length() - 2)));
                taxAmountInputDto.setStoreId(storeId);
                taxAmountInputDto.setTaxAmountType(2);
                taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
                Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
                String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
                if (userInfo.isPresent()) {
                    userOperCd = userInfo.get();
                }
                taxAmountInputDto.setInsUpdOperCd(userOperCd);
                itemInfoSharedService.registTaxAmount(taxAmountInputDto);

                // 受付ID取得
                ODeliveryOrderSummary deliveryOrderSummary = deliveryOrderSummaryRepository
                    .findByStoreIdAndOrderSummaryId(storeId, orderSummaryId);

                // メールタイトル
                String subject = "受付ID送信";
                // メール内容
                String content = deliveryOrderSummary.getCustomerName()
                    + "様<BR><BR>ご注文した受付IDは"
                    + deliveryOrderSummary.getReceivablesId() + "です。<BR>ご利用ください。";

                // メール再送信サービス処理を実行する
                mailService
                    .sendEmail(deliveryOrderSummary.getMailAddress(), subject, content, false,
                        true);
            } else {

                // 注文情報を回復する
                orderService.revertDeliveryOrder(inputDto);
            }

            return "SUCCESS";
        } else {

            return "FAILD";
        }
    }

    /**
     * SBペイメント情报取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 支払情報
     */
    @PostMapping(UrlConstants.CSDV_SB_PAYMENT_INFO)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.MOBILE + "\")")
    public GetSbPaymentInfoOutputResource getSbPaymentInfo(
        @Validated @RequestBody GetSbPaymentInfoInputResource inputResource, BindingResult result,
        HttpServletRequest request) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetSbPaymentInfoInputDto inputDto = beanMapper
            .map(inputResource, GetSbPaymentInfoInputDto.class);
        // 認証トークン
        inputDto.setToken(request.getHeader(CsdvConstants.AUTHORIZATION_HEADER).substring(7));
        inputDto.setNo("1");

        // サービス処理を実行する
        GetSbPaymentInfoOutputDto outputDto = paymentService.getSbDvPaymentInfo(inputDto);

        return beanMapper.map(outputDto, GetSbPaymentInfoOutputResource.class);
    }

    /**
     * SBペイメントコールバック.
     *
     * @param inputData リクエストパラメータ
     * @return 処理結果
     */
    @PostMapping(value = UrlConstants.CSDV_SB_PAYMENT_CALLBACK,
        consumes = "application/x-www-form-urlencoded;charset=Shift_JIS")
    @ResponseStatus(HttpStatus.OK)
    public String sbPaymentCallBack(
        @RequestParam Map<String, String> inputData) {

        // リクエストパラメータ設定
        SbPaymentCallBackInputResource inputResource = JSONObject
            .parseObject(JSONObject.toJSONString(inputData), SbPaymentCallBackInputResource.class);

        // アウトプット情報を作成する
        SbPaymentCallBackOutputResource outputResource = new SbPaymentCallBackOutputResource();
        outputResource.setResResult(ResResult.OK.getCode());

        // 単項目チェック
        if (StringUtils.isBlank(inputResource.getOrderId()) || StringUtils
            .isBlank(inputResource.getAmount()) || StringUtils.isBlank(inputResource.getFree1())
            || StringUtils.isBlank(inputResource.getFree2()) || StringUtils
            .isBlank(inputResource.getFree3()) || StringUtils.isBlank(inputResource.getResResult())
            || StringUtils.isBlank(inputResource.getResTrackingId())) {
            outputResource.setResResult(ResResult.NG.getCode());
            outputResource.setMessage(messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
            return outputResource.getResResult() + "," + outputResource.getMessage();
        }

        // 認証トークン
        String token = inputResource.getFree2() + inputResource.getFree3();

        // 認証トークンチェック
        if (!Objects.equals(Md5Util.toMd5(CommonConstants.DEFAULT_PASSWORD), token)) {
            outputResource.setResResult(ResResult.NG.getCode());
            outputResource.setMessage(AppTokenStatus.APP_TOKEN_INVALID.getReasonPhrase());
            return outputResource.getResResult() + "," + outputResource.getMessage();
        }

        // インプット情報をDTOにセットする
        SbPaymentCallBackInputDto inputDto = beanMapper
            .map(inputResource, SbPaymentCallBackInputDto.class);

        // サービス処理を実行する
        try {
            paymentService.sbDeliveryPaymentCallBack(inputDto);

            // 税額テーブル登録
            // 注文情報
            String[] orderInfo = inputDto.getFree1().split(",");
            // 店舗ID
            String storeId = orderInfo[0];
            TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
            taxAmountInputDto.setOrderSummaryId(inputDto.getOrderId().substring(0, 20));
            taxAmountInputDto.setOrderId(Integer.valueOf(inputDto.getOrderId()
                .substring(20, inputDto.getOrderId().length() - 2)));
            taxAmountInputDto.setStoreId(storeId);
            taxAmountInputDto.setTaxAmountType(2);
            taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }
            taxAmountInputDto.setInsUpdOperCd(userOperCd);
            itemInfoSharedService.registTaxAmount(taxAmountInputDto);
            fcmPush.getPushToken(storeId, "出前の注文があります。");
        } catch (Exception e) {
            outputResource.setResResult(ResResult.NG.getCode());
            outputResource.setMessage(e.getMessage());
        }

        // ※結果 CGI の「res_result」が NG の場合には必ず OK を返却してください
        if (Objects.equals(ResResult.NG.getCode(), inputResource.getResResult())) {
            outputResource.setResResult(ResResult.OK.getCode());
        }

        if (Objects.equals(ResResult.NG.getCode(), inputResource.getResResult())) {
            return outputResource.getResResult() + "," + outputResource.getMessage();
        } else {

            return outputResource.getResResult();
        }
    }
}
