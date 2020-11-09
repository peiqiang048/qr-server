package com.cnc.qr.api.stpdorder.controller;

import com.alibaba.fastjson.JSON;
import com.cnc.qr.api.csmborder.resource.GetAccountInitInputResource;
import com.cnc.qr.api.stpdorder.resource.AgainRegistPaymentInputResource;
import com.cnc.qr.api.stpdorder.resource.DutchAccountPayLaterInputResource;
import com.cnc.qr.api.stpdorder.resource.DutchAccountPayLaterOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetAccountInitOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetRefundsListInputResource;
import com.cnc.qr.api.stpdorder.resource.GetRefundsListOutputResource;
import com.cnc.qr.api.stpdorder.resource.GetTaxAmountInputResource;
import com.cnc.qr.api.stpdorder.resource.GetTaxAmountOutputResource;
import com.cnc.qr.api.stpdorder.resource.PayLaterInputResource;
import com.cnc.qr.api.stpdorder.resource.PayLaterOutputResource;
import com.cnc.qr.api.stpdorder.resource.RefundsInputResource;
import com.cnc.qr.api.stpdorder.resource.RefundsOutputResource;
import com.cnc.qr.api.stpdorder.resource.RegistDutchAccountInputResource;
import com.cnc.qr.api.stpdorder.resource.RegistDutchAccountOutputResource;
import com.cnc.qr.api.stpdorder.resource.RegistPaymentInputResource;
import com.cnc.qr.api.stpdorder.resource.RegistPaymentOutputResource;
import com.cnc.qr.common.constants.AuthoritiesConstants;
import com.cnc.qr.common.constants.CodeConstants.AccountsType;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemType;
import com.cnc.qr.common.constants.CodeConstants.PayResult;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.model.SlipPrintDto;
import com.cnc.qr.common.shared.model.TaxAmountInputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.core.acct.model.AgainRegistPaymentInputDto;
import com.cnc.qr.core.acct.model.DutchAccountPayLaterInputDto;
import com.cnc.qr.core.acct.model.DutchAccountPayLaterOutputDto;
import com.cnc.qr.core.acct.model.GetRefundsListInputDto;
import com.cnc.qr.core.acct.model.GetRefundsListOutputDto;
import com.cnc.qr.core.acct.model.RefundsInputDto;
import com.cnc.qr.core.acct.model.RegistDutchAccountInputDto;
import com.cnc.qr.core.acct.model.RegistPaymentInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterOutputDto;
import com.cnc.qr.core.acct.model.SbPaymentRefundsInputDto;
import com.cnc.qr.core.acct.service.PaymentService;
import com.cnc.qr.core.order.model.GetOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetPayLaterPaymentInfoInputDto;
import com.cnc.qr.core.order.model.GetPayLaterPaymentInfoOutputDto;
import com.cnc.qr.core.order.model.ItemsDto;
import com.cnc.qr.core.order.model.OrderItemDetailInfoDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import io.github.jhipster.web.util.PaginationUtil;
import java.math.BigDecimal;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 会計処理コントローラ.
 */
@RestController
public class StpdPaymentController {

    /**
     * 会計処理サービス.
     */
    @Autowired
    private PaymentService paymentService;

    /**
     * 店舗情報取得サービス.
     */
    @Autowired
    private StoreInfoService storeInfoService;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

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
     * 伝票印刷.
     */
    @Autowired
    PrintDataSharedService printDataSharedService;

    /**
     * 会計PAD会計画面初期化取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 会計PAD初期化取得
     */
    @PostMapping(UrlConstants.STPD_GET_ACCOUNT_INIT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public GetAccountInitOutputResource getAccountInit(
        @Validated @RequestBody GetAccountInitInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetOrderItemListInputDto itemInputDto = beanMapper
            .map(inputResource, GetOrderItemListInputDto.class);

        //該当サマリを取得する
        OOrderSummary orderSummary = orderService.getOrderSummary(itemInputDto);

        // 取得結果チェック
        if (null == orderSummary) {

            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.049", (Object) null));
        }

        if (StringUtils.isNotBlank(inputResource.getTakeoutFlag())
            && orderSummary.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.051", (Object) null));
        }

        // インプット情報をDTOにセットする
        GetPayLaterPaymentInfoInputDto paymentInfoInputDto = beanMapper
            .map(inputResource, GetPayLaterPaymentInfoInputDto.class);

        // 支払い方式取得サービス処理を実行する
        GetPayLaterPaymentInfoOutputDto paymentOutfoInputDto = storeInfoService
            .getPayLaterPaymentType(paymentInfoInputDto);

        // 支払方式情報を設定する
        GetAccountInitOutputResource outputResource = new GetAccountInitOutputResource();
        outputResource.setPaymentTypeList(paymentOutfoInputDto.getPaymentMethodList());

        // 注文商品リスト取得サービス処理を実行する
        GetOrderItemListOutputDto outputDto = orderService.getOrderItemList(itemInputDto);
        // 先払いの場合
        if ("01".equals(inputResource.getPaymentDistinction())) {

            // 商品価格が0の場合、画面表示しない
            List<OrderItemDetailInfoDto> itemTempList = new ArrayList<>();
            itemTempList = outputDto.getItemList().stream().filter(orderItemDetailInfoDto ->
                BigDecimal.ZERO.compareTo(orderItemDetailInfoDto.getItemPrice()) != 0).collect(
                Collectors.toList());
            outputResource.setItemList(itemTempList);

            // 小計
            outputResource.setSubtotal(outputDto.getOrderAmount());

            if (orderSummary.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                // 外税金额取得
                BigDecimal foreignTax = orderService.getForeignTax(paymentInfoInputDto.getStoreId(),
                    orderSummary.getOrderSummaryId());

                // 外税
                outputResource.setForeignTax(foreignTax);
                // 合計
                outputResource
                    .setTotal((Objects.isNull(outputDto.getOrderAmount()) ? BigDecimal.ZERO
                        : outputDto.getOrderAmount()).add(foreignTax));
            } else {

                // 消費税取得
                GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
                taxValueInputDto.setStoreId(inputResource.getStoreId());

                if (StringUtils.isBlank(inputResource.getTakeoutFlag())) {

                    taxValueInputDto.setTakeoutFlag(orderSummary.getTakeoutFlag());
                } else {

                    taxValueInputDto.setTakeoutFlag(inputResource.getTakeoutFlag());
                }

                List<ItemsDto> itemList = new ArrayList<>();
                outputDto.getItemList().forEach(item -> {
                    ItemsDto itemsDto = new ItemsDto();
                    itemsDto.setItemId(item.getItemId());
                    if (Objects.equals(ItemType.NORMAL.getCode(), item.getItemClassification())) {
                        itemsDto.setItemPrice(
                            item.getItemPrice());
                    } else {
                        itemsDto.setItemPrice(
                            item.getItemPrice().negate());
                    }
                    itemList.add(itemsDto);
                });
                taxValueInputDto.setItemList(itemList);
                GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
                    .getTaxValue(taxValueInputDto);
                // 外税
                outputResource.setForeignTax(
                    taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));
                // 合計
                outputResource
                    .setTotal((Objects.isNull(outputDto.getOrderAmount()) ? BigDecimal.ZERO
                        : outputDto.getOrderAmount())
                        .add(taxValueOutputDto.getSotoTaxEight()
                            .add(taxValueOutputDto.getSotoTaxTen())));
            }

            // 割引额
            outputResource.setPriceDiscountAmount(orderSummary.getPriceDiscountAmount());

            // 割引率
            outputResource.setPriceDiscountRate(orderSummary.getPriceDiscountRate());

            if (null == orderSummary.getPriceDiscountAmount() && null == orderSummary
                .getPriceDiscountRate()) {

                // 未収
                outputResource.setUnpay(outputResource.getTotal().subtract(
                    orderSummary.getPaymentAmount()));
            }

            if (null != orderSummary.getPriceDiscountAmount() && null == orderSummary
                .getPriceDiscountRate()) {

                // 未収
                outputResource.setUnpay(outputResource.getTotal().subtract(
                    orderSummary.getPriceDiscountAmount()).subtract(
                    orderSummary.getPaymentAmount()));
            }

            if (null == orderSummary.getPriceDiscountAmount() && null != orderSummary
                .getPriceDiscountRate()) {

                // 未収
                outputResource.setUnpay(outputResource.getTotal().multiply(
                    (new BigDecimal("10").subtract(orderSummary.getPriceDiscountRate()))
                        .divide(new BigDecimal("10")))
                    .setScale(0, BigDecimal.ROUND_DOWN).subtract(orderSummary.getPaymentAmount()));
            }
        } else {
            // 後払いの場合
            List<OrderItemDetailInfoDto> itemList = new ArrayList<OrderItemDetailInfoDto>();
            // 返品の商品
            List<OrderItemDetailInfoDto> backItemList = new ArrayList<OrderItemDetailInfoDto>();

            for (int i = 0; i < outputDto.getItemList().size(); i++) {
                if (ItemType.RETURNS.getCode()
                    .equals(outputDto.getItemList().get(i).getItemClassification())) {
                    backItemList.add(outputDto.getItemList().get(i));
                } else {
                    itemList.add(outputDto.getItemList().get(i));
                }
            }

            for (int i = 0; i < itemList.size(); i++) {
                for (int j = 0; j < backItemList.size(); j++) {
                    if (itemList.get(i).getOrderDetailId()
                        .equals(backItemList.get(j).getReturnOrderDetailId())) {
                        itemList.get(i).setItemPrice(itemList.get(i).getItemPrice()
                            .subtract(backItemList.get(j).getItemPrice()));
                        itemList.get(i).setItemCount(
                            itemList.get(i).getItemCount() - backItemList.get(j).getItemCount());
                        if (itemList.get(i).getItemCount() == 0) {
                            itemList.remove(i);
                        }
                    }
                }
            }
            //商品の価格が０の場合、画面上に表示しないです
            itemList = itemList.stream().filter(orderItemDetailInfoDto ->
                BigDecimal.ZERO.compareTo(orderItemDetailInfoDto.getItemPrice()) != 0).collect(
                Collectors.toList());
            outputResource.setItemList(itemList);

            outputResource.setSubtotal(BigDecimal.ZERO);
            outputResource.setForeignTax(BigDecimal.ZERO);
            outputResource.setTotal(BigDecimal.ZERO);
            outputResource.setPriceDiscountAmount(null);
            outputResource.setPriceDiscountRate(null);
            outputResource.setUnpay(BigDecimal.ZERO);
        }

        // 飲食区分取得
        if (StringUtils.isEmpty(inputResource.getTakeoutFlag())) {
            outputResource.setTakeoutFlag(orderSummary.getTakeoutFlag());
        } else {
            outputResource.setTakeoutFlag(inputResource.getTakeoutFlag());
        }

        return outputResource;
    }

    /**
     * 現金支払い.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 現金支払結果
     */
    @PostMapping(UrlConstants.REGIST_PAYMENT)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    @Transactional
    public RegistPaymentOutputResource registPayment(
        @Validated @RequestBody RegistPaymentInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        RegistPaymentInputDto inputDto = beanMapper.map(inputResource, RegistPaymentInputDto.class);
        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        // 現金支払サービス処理を実行する
        String orderSummaryId = paymentService.registPayment(inputDto);

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(orderSummaryId);
        taxAmountInputDto.setOrderId(null);
        taxAmountInputDto.setStoreId(inputDto.getStoreId());
        taxAmountInputDto.setTaxAmountType(0);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        if (0 == itemInfoSharedService.getTaxAmountCount(taxAmountInputDto)) {
            itemInfoSharedService.registTaxAmount(taxAmountInputDto);
        }

        // 印刷
        SlipInputDto slipInputDto = new SlipInputDto();
        slipInputDto.setOrderSummaryId(orderSummaryId);
        slipInputDto.setStoreId(inputDto.getStoreId());

        slipInputDto.setUserName(userOperCd);

        // 小計
        slipInputDto.setSubtotal(new BigDecimal(inputResource.getSubtotal()));
        slipInputDto.setSotoTax(new BigDecimal(inputResource.getSotoTax()));
        // 支払種類
        slipInputDto.setCashType(inputDto.getPaymentMethodCode());
        // 値引き
        slipInputDto.setPriceDiscountAmount(inputResource.getPriceDiscountAmount());
        //割引
        slipInputDto.setPriceDiscountRate(inputResource.getPriceDiscountRate());
        //支払金額
        slipInputDto.setPaymentAmount(new BigDecimal(inputResource.getPaymentAmount()));
        // お預かり金額
        slipInputDto.setCustody(new BigDecimal(inputResource.getCustody()));
        OrderAccountInfoDto orderAccountInfoDto = printDataSharedService
            .getOrderAccountPrintData(slipInputDto);
        SlipPrintDto slipPrintDto = new SlipPrintDto();

        slipPrintDto.setOrderAccountInfoDto(orderAccountInfoDto);

        RegistPaymentOutputResource outputResource = new RegistPaymentOutputResource();
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        return outputResource;
    }

    /**
     * 再会計（現金だけ）.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 再支払結果
     */
    @PostMapping(UrlConstants.AGAIN_REGIST_PAYMENT)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public RegistPaymentOutputResource againRegistPayment(
        @Validated @RequestBody AgainRegistPaymentInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        AgainRegistPaymentInputDto inputDto = beanMapper
            .map(inputResource, AgainRegistPaymentInputDto.class);

        // 現金支払サービス処理を実行する
        OOrderSummary orderSummaryDto = paymentService.againRegistPayment(inputDto);

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(orderSummaryDto.getOrderSummaryId());
        taxAmountInputDto.setOrderId(null);
        taxAmountInputDto.setStoreId(inputDto.getStoreId());
        taxAmountInputDto.setTaxAmountType(3);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        itemInfoSharedService.registTaxAmount(taxAmountInputDto);

        return new RegistPaymentOutputResource();
    }

    /**
     * 返金一覧.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 返金一覧情報
     */
    @PostMapping(UrlConstants.REFUNDS_LIST)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public ResponseEntity<GetRefundsListOutputResource> getRefundsList(
        @Validated @RequestBody GetRefundsListInputResource inputResource, BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // インプット情報をDTOにセットする
        GetRefundsListInputDto inputDto = beanMapper
            .map(inputResource, GetRefundsListInputDto.class);

        // 返金一覧取得サービス処理を実行する
        Page<GetRefundsListOutputDto> page = paymentService.getRefundsList(inputDto, PageRequest
            .of(0 < inputResource.getPage() ? inputResource.getPage() - 1 : 0,
                inputResource.getPageCount()));

        // アウトプット情報を作成する
        GetRefundsListOutputResource outputResource = new GetRefundsListOutputResource();
        outputResource.setOrderList(page.getContent());

        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(outputResource, headers, HttpStatus.OK);
    }

    /**
     * 返金（電子だけ）.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 返金結果
     */
    @PostMapping(UrlConstants.REFUNDS)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public RefundsOutputResource refunds(
        @Validated @RequestBody RefundsInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // 先払の場合
        if (Integer.parseInt(inputResource.getPaymentCode()) < 21) {
            // WeChatPayの場合
            if (Objects.equals(AccountsType.WECHATPAY.getCode(), inputResource.getPaymentCode())) {
                RefundsInputDto inputDto = beanMapper.map(inputResource, RefundsInputDto.class);
                paymentService.refunds(inputDto);
                // SBペイメントの場合
            } else {
                SbPaymentRefundsInputDto inputDto = beanMapper
                    .map(inputResource, SbPaymentRefundsInputDto.class);
                paymentService.sbPaymentRefunds(inputDto);
            }
            // 後払場合
        } else {
            RefundsInputDto inputDto = beanMapper.map(inputResource, RefundsInputDto.class);
            paymentService.sbCpmRefunds(inputDto);
        }

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(inputResource.getOrderNo().substring(0, 20));
        taxAmountInputDto.setOrderId(StringUtils.isNotEmpty(inputResource.getOrderNo()
            .substring(20, inputResource.getOrderNo().length() - 2)) ? Integer
            .valueOf(inputResource.getOrderNo()
                .substring(20, inputResource.getOrderNo().length() - 2)) : null);
        taxAmountInputDto.setStoreId(inputResource.getStoreId());
        taxAmountInputDto.setTaxAmountType(3);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        itemInfoSharedService.registTaxAmount(taxAmountInputDto);

        return new RefundsOutputResource();
    }

    /**
     * 後払い（微信）.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 後払結果
     */
    @PostMapping(UrlConstants.PAY_LATER)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
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

    /**
     * 返金（出前　電子）.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return 返金結果
     */
    @PostMapping(UrlConstants.STPD_REFUND_DELIVERY_ORDER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public RefundsOutputResource refundDeliveryOrder(
        @Validated @RequestBody RefundsInputResource inputResource,
        BindingResult result) {

        // 注文番号
        inputResource
            .setOrderNo(inputResource.getOrderSummaryId() + inputResource.getOrderNo() + "01");

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        // WeChatPayの場合
        if (Objects.equals(AccountsType.WECHATPAY.getCode(), inputResource.getPaymentCode())) {
            RefundsInputDto inputDto = beanMapper.map(inputResource, RefundsInputDto.class);
            paymentService.refundsDelivery(inputDto);
            // SBペイメントの場合
        } else {
            SbPaymentRefundsInputDto inputDto = beanMapper
                .map(inputResource, SbPaymentRefundsInputDto.class);
            paymentService.sbPaymentRefundsDelivery(inputDto);
        }

        paymentService
            .updateDeliveryStatus(inputResource.getOrderSummaryId(), inputResource.getStoreId());

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(inputResource.getOrderNo().substring(0, 20));
        taxAmountInputDto.setOrderId(StringUtils.isNotEmpty(inputResource.getOrderNo()
            .substring(20, inputResource.getOrderNo().length() - 2)) ? Integer
            .valueOf(inputResource.getOrderNo()
                .substring(20, inputResource.getOrderNo().length() - 2)) : null);
        taxAmountInputDto.setStoreId(inputResource.getStoreId());
        taxAmountInputDto.setTaxAmountType(3);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        itemInfoSharedService.registTaxAmount(taxAmountInputDto);

        return new RefundsOutputResource();
    }

    /**
     * 現金割勘.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター情報
     */
    @PostMapping(UrlConstants.STPD_REGIST_DUTCH_ACCOUNT_URL)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public RegistDutchAccountOutputResource registDutchAccount(
        @Validated @RequestBody RegistDutchAccountInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        RegistDutchAccountInputDto inputDto = beanMapper
            .map(inputResource, RegistDutchAccountInputDto.class);
        inputDto.setItemList(inputResource.getItemList());
        String orderSummaryId = "";
        // 商品リスト
        List<OrderItemDetailInfoDto> itemTempList = new ArrayList<>();
        if (inputDto.getLastOrderFlag().equals(Flag.OFF.getCode().toString())) {

            orderSummaryId = paymentService.registOrder(inputDto);

            paymentService.insertPayment(inputDto, orderSummaryId);
            GetOrderItemListInputDto orderItemListInputDto = new GetOrderItemListInputDto();
            orderItemListInputDto.setReceivablesId(inputResource.getReceivablesId());
            orderItemListInputDto.setStoreId(inputResource.getStoreId());
            orderItemListInputDto.setLanguages(inputResource.getLanguages());
            // 注文商品リスト取得サービス処理を実行する
            GetOrderItemListOutputDto outputDto = orderService
                .getOrderItemList(orderItemListInputDto);
            itemTempList = outputDto.getItemList().stream().filter(orderItemDetailInfoDto ->
                BigDecimal.ZERO.compareTo(orderItemDetailInfoDto.getItemPrice()) != 0).collect(
                Collectors.toList());
        } else {

            RegistPaymentInputDto paymentInputDto = beanMapper
                .map(inputDto, RegistPaymentInputDto.class);

            // 現金支払サービス処理を実行する
            orderSummaryId = paymentService.registPayment(paymentInputDto);
        }

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(orderSummaryId);
        taxAmountInputDto.setOrderId(null);
        taxAmountInputDto.setStoreId(inputResource.getStoreId());
        taxAmountInputDto.setTaxAmountType(0);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        taxAmountInputDto.setTakeoutFlag(inputResource.getTakeoutFlag());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        List<ItemsDto> itemList = new ArrayList<>();
        inputResource.getItemList().forEach(itemDto -> {
            ItemsDto itemsDto = new ItemsDto();
            itemsDto.setItemId(itemDto.getItemId().toString());
            itemsDto.setItemPrice(itemDto.getItemPrice());
            itemList.add(itemsDto);
        });
        taxAmountInputDto.setItemList(itemList);
        itemInfoSharedService.registTaxAmount(taxAmountInputDto);

        // 印刷
        SlipInputDto slipInputDto = new SlipInputDto();
        slipInputDto.setOrderSummaryId(orderSummaryId);
        slipInputDto.setStoreId(inputDto.getStoreId());
        slipInputDto.setUserName(userOperCd);
        // 小計
        slipInputDto.setSubtotal(new BigDecimal(inputResource.getSubtotal()));
        slipInputDto.setSotoTax(new BigDecimal(inputResource.getSotoTax()));
        // 支払種類
        slipInputDto.setCashType(inputDto.getPaymentMethodCode());
        // 値引き
        slipInputDto.setPriceDiscountAmount(inputResource.getPriceDiscountAmount());
        //割引
        slipInputDto.setPriceDiscountRate(inputResource.getPriceDiscountRate());
        //支払金額
        slipInputDto.setPaymentAmount(new BigDecimal(inputResource.getPaymentAmount()));
        // お預かり金額
        slipInputDto.setCustody(new BigDecimal(inputResource.getCustody()));
        OrderAccountInfoDto orderAccountInfoDto = printDataSharedService
            .getOrderAccountPrintData(slipInputDto);
        SlipPrintDto slipPrintDto = new SlipPrintDto();

        slipPrintDto.setOrderAccountInfoDto(orderAccountInfoDto);

        RegistDutchAccountOutputResource outputResource = new RegistDutchAccountOutputResource();
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        outputResource.setItemList(itemTempList);
        return outputResource;
    }

    /**
     * QRコード後払割勘.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター情報
     */
    @PostMapping(UrlConstants.STPD_DUTCH_ACCOUNT_PAY_LATER_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    @Transactional
    public DutchAccountPayLaterOutputResource dutchAccountPayLater(
        @Validated @RequestBody DutchAccountPayLaterInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        DutchAccountPayLaterInputDto inputDto = beanMapper
            .map(inputResource, DutchAccountPayLaterInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        // QRコード後払割勘サービス処理を実行する
        SbPayLaterOutputDto payLaterOutputDto = new SbPayLaterOutputDto();

        DutchAccountPayLaterOutputDto outputDto = new DutchAccountPayLaterOutputDto();
        String orderSummaryId = "";
        List<OrderItemDetailInfoDto> itemTempList = new ArrayList<>();

        if (inputDto.getLastOrderFlag().equals(Flag.OFF.getCode().toString())) {

            outputDto = paymentService.dutchAccountPayLater(inputDto);

            if (outputDto.getRespCode().equals(PayResult.SUCCESS.getCode())) {

                RegistDutchAccountInputDto regisOrderInputDto =
                    beanMapper.map(inputDto, RegistDutchAccountInputDto.class);
                regisOrderInputDto.setNewReceivablesId(outputDto.getReceivablesId());
                regisOrderInputDto.setNewOrderSummaryId(outputDto.getOrderSummaryId());
                regisOrderInputDto.setPaymentMethodCode(outputDto.getPaymentMethodCode());
                regisOrderInputDto.setCustody(inputDto.getPaymentAmount());
                orderSummaryId = paymentService.registOrder(regisOrderInputDto);
                paymentService.insertPayment(regisOrderInputDto, orderSummaryId);
            }

            // 注文商品リスト取得サービス処理を実行する
            GetOrderItemListInputDto orderItemListInputDto = new GetOrderItemListInputDto();
            orderItemListInputDto.setStoreId(inputResource.getStoreId());
            orderItemListInputDto.setReceivablesId(inputResource.getReceivablesId());
            orderItemListInputDto.setLanguages(inputResource.getLanguages());
            GetOrderItemListOutputDto orderItemListOutputDto = orderService.getOrderItemList(orderItemListInputDto);
            // 商品価格が0の場合、画面表示しない
            itemTempList = orderItemListOutputDto.getItemList().stream().filter(orderItemDetailInfoDto ->
                BigDecimal.ZERO.compareTo(orderItemDetailInfoDto.getItemPrice()) != 0).collect(
                Collectors.toList());


        } else {

            // 後払い情報をDTOにセットする
            SbPayLaterInputDto paymentInputDto = beanMapper.map(inputDto, SbPayLaterInputDto.class);
            paymentInputDto.setPayAmount(inputDto.getPaymentAmount());

            payLaterOutputDto = paymentService.sbPayLater(paymentInputDto);
        }

        // 税額テーブル登録
        TaxAmountInputDto taxAmountInputDto = new TaxAmountInputDto();
        taxAmountInputDto.setOrderSummaryId(orderSummaryId);
        taxAmountInputDto.setOrderId(null);
        taxAmountInputDto.setStoreId(inputResource.getStoreId());
        taxAmountInputDto.setTaxAmountType(0);
        taxAmountInputDto.setDeliveryFlag(Flag.OFF.getCode());
        taxAmountInputDto.setTakeoutFlag(inputResource.getTakeoutFlag());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        taxAmountInputDto.setInsUpdOperCd(userOperCd);
        List<ItemsDto> itemList = new ArrayList<>();
        inputResource.getItemList().forEach(itemDto -> {
            ItemsDto itemsDto = new ItemsDto();
            itemsDto.setItemId(itemDto.getItemId().toString());
            itemsDto.setItemPrice(itemDto.getItemPrice());
            itemList.add(itemsDto);
        });
        taxAmountInputDto.setItemList(itemList);
        itemInfoSharedService.registTaxAmount(taxAmountInputDto);

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
        slipInputDto.setPaymentAmount(new BigDecimal(inputResource.getPaymentAmount()));

        slipInputDto.setTerminalType("1");
        OrderAccountInfoDto orderAccountInfoDto = printDataSharedService
            .getOrderAccountPrintData(slipInputDto);
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        slipPrintDto.setOrderAccountInfoDto(orderAccountInfoDto);

        DutchAccountPayLaterOutputResource outputResource = new DutchAccountPayLaterOutputResource();
        if (inputDto.getLastOrderFlag().equals(Flag.OFF.getCode().toString())) {
            outputResource.setRespCode(outputDto.getRespCode());
            outputResource.setCancelRespCode(outputDto.getCancelRespCode());
        } else {
            outputResource.setRespCode(payLaterOutputDto.getRespCode());
            outputResource.setCancelRespCode(payLaterOutputDto.getCancelRespCode());
        }
        outputResource.setPrintInfo(JSON.toJSONString(slipPrintDto));
        outputResource.setItemList(itemTempList);
        return outputResource;
    }

    /**
     * 注文金額取得.
     *
     * @param inputResource リクエストパラメータ
     * @param result        バインド結果
     * @return プリンター情報
     */
    @PostMapping(UrlConstants.STPD_GET_TAX_AMOUNT_URL)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ACCOUNT + "\")")
    public GetTaxAmountOutputResource getTaxAmount(
        @Validated @RequestBody GetTaxAmountInputResource inputResource,
        BindingResult result) {

        // 単項目チェック
        if (result.hasErrors()) {
            throw new BusinessException(ResultCode.VALIDATE_FAILED.getResultCode(),
                messageSource.getMessage("e.qr.ph.025", null, Locale.JAPAN));
        }

        GetTaxValueInputDto inputDto = beanMapper.map(inputResource, GetTaxValueInputDto.class);
        inputDto.setItemList(inputResource.getItemList());

        GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
            .getTaxValue(inputDto);

        GetTaxAmountOutputResource outputResource = new GetTaxAmountOutputResource();
        // 外税
        outputResource.setForeignTax(
            taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));

        BigDecimal orderAmount = BigDecimal.ZERO;

        for (int i = 0; i < inputDto.getItemList().size(); i++) {
            orderAmount = orderAmount.add(inputDto.getItemList().get(i).getItemPrice());
        }

        outputResource.setOrderAmount(orderAmount);

        outputResource.setTotal(orderAmount.add(outputResource.getForeignTax()));

        return outputResource;
    }
}
