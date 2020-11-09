package com.cnc.qr.api.stmborder.controller;

import com.alibaba.fastjson.JSON;
import com.cnc.qr.api.stmborder.resource.PayLaterInputResource;
import com.cnc.qr.api.stmborder.resource.PayLaterOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.PayResult;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.model.SlipPrintDto;
import com.cnc.qr.common.shared.model.TaxAmountInputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.core.acct.model.SbPayLaterInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterOutputDto;
import com.cnc.qr.core.acct.service.PaymentService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
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
 * 支払処理コントローラ.
 */
@RestController
public class StmbPaymentController {

    /**
     * 支払サービス.
     */
    @Autowired
    private PaymentService paymentService;

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
     * プリンターデータ取得.
     */
    @Autowired
    PrintDataSharedService printDataSharedService;
    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 後払い.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 後払結果
     */
    @PostMapping(UrlConstants.STMB_PAY_LATER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.STMB_ORDER + "\")")
    public PayLaterOutputResource payLater(
        @Validated @RequestBody PayLaterInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.getDefault()));
        }

        // 後払い情報をDTOにセットする
        SbPayLaterInputDto inputDto = beanMapper.map(inputResource, SbPayLaterInputDto.class);

        // 現金支払サービス処理を実行する
        SbPayLaterOutputDto outputDto = paymentService.sbPayLater(inputDto);

        // アウトプット情報を作成する
        PayLaterOutputResource outputResource = new PayLaterOutputResource();
        outputResource.setRespCode(outputDto.getRespCode());
        outputResource.setCancelRespCode(outputDto.getCancelRespCode());

        // 返却結果が成功の場合、印刷する
        if (Objects.equals(PayResult.SUCCESS.getCode(), outputDto.getRespCode())) {
            // 税額テーブル登録
            TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
            taxAmountInputDto.setOrderSummaryId(outputDto.getOrderSummaryId());
            taxAmountInputDto.setOrderId(null);
            taxAmountInputDto.setStoreId(inputResource.getStoreId());
            taxAmountInputDto.setTaxAmountType(0);
            taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }
            taxAmountInputDto.setInsUpdOperCd(userOperCd);
            if (0 == itemInfoSharedService.getTaxAmountCount(taxAmountInputDto)) {
                itemInfoSharedService.registTaxAmount(taxAmountInputDto);
            }
            // 印刷
            SlipInputDto slipInputDto = new SlipInputDto();
            slipInputDto.setOrderSummaryId(outputDto.getOrderSummaryId());
            slipInputDto.setStoreId(inputDto.getStoreId());

            slipInputDto.setUserName(userOperCd);

            // 小計
            slipInputDto.setSubtotal(new BigDecimal(inputResource.getSubtotal()));
            slipInputDto.setSotoTax(new BigDecimal(inputResource.getSotoTax()));

            // 値引き
            slipInputDto.setPriceDiscountAmount(inputResource.getPriceDiscountAmount());
            //割引
            slipInputDto.setPriceDiscountRate(inputResource.getPriceDiscountRate());
            //支払金額
            slipInputDto.setPaymentAmount(new BigDecimal(inputResource.getPayAmount()));

            slipInputDto.setTerminalType("1");
            OrderAccountInfoDto orderAccountInfoDto = printDataSharedService
                .getOrderAccountPrintData(slipInputDto);
            SlipPrintDto slipPrintDto = new SlipPrintDto();
            slipPrintDto.setOrderAccountInfoDto(orderAccountInfoDto);
            outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        }

        return outputResource;
    }
}
