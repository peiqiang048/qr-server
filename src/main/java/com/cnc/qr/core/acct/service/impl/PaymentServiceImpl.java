package com.cnc.qr.core.acct.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.AccountsType;
import com.cnc.qr.common.constants.CodeConstants.Company;
import com.cnc.qr.common.constants.CodeConstants.CompanyMethod;
import com.cnc.qr.common.constants.CodeConstants.DeliveryStatus;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemStatus;
import com.cnc.qr.common.constants.CodeConstants.ItemType;
import com.cnc.qr.common.constants.CodeConstants.MediumType;
import com.cnc.qr.common.constants.CodeConstants.MstItemType;
import com.cnc.qr.common.constants.CodeConstants.OrderStatus;
import com.cnc.qr.common.constants.CodeConstants.OrderType;
import com.cnc.qr.common.constants.CodeConstants.PayResult;
import com.cnc.qr.common.constants.CodeConstants.PayStatus;
import com.cnc.qr.common.constants.CodeConstants.PayType;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.PrintStatus;
import com.cnc.qr.common.constants.CodeConstants.ResResult;
import com.cnc.qr.common.constants.CodeConstants.SbOrderStatus;
import com.cnc.qr.common.constants.CodeConstants.SbRefundsId;
import com.cnc.qr.common.constants.CodeConstants.ServiceType;
import com.cnc.qr.common.constants.CodeConstants.StaffCheckFlag;
import com.cnc.qr.common.constants.CodeConstants.TerminalDistinction;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.MStoreMedium;
import com.cnc.qr.common.entity.ODeliveryOrderSummary;
import com.cnc.qr.common.entity.OOrder;
import com.cnc.qr.common.entity.OOrderDetails;
import com.cnc.qr.common.entity.OOrderDetailsOption;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.common.entity.OPrintQueue;
import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.common.entity.PPayment;
import com.cnc.qr.common.entity.PPaymentCompany;
import com.cnc.qr.common.entity.PPaymentDetail;
import com.cnc.qr.common.entity.PSbResultDetail;
import com.cnc.qr.common.entity.PTrioResultDetail;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.push.FcmPush;
import com.cnc.qr.common.repository.MLicenseRepository;
import com.cnc.qr.common.repository.MStoreMediumRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.MTokenRepository;
import com.cnc.qr.common.repository.ODeliveryOrderSummaryRepository;
import com.cnc.qr.common.repository.OOrderDetailsOptionRepository;
import com.cnc.qr.common.repository.OOrderDetailsRepository;
import com.cnc.qr.common.repository.OOrderRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OPrintQueueRepository;
import com.cnc.qr.common.repository.OReceivablesRepository;
import com.cnc.qr.common.repository.PPaymentCompanyRepository;
import com.cnc.qr.common.repository.PPaymentDetailRepository;
import com.cnc.qr.common.repository.PPaymentRepository;
import com.cnc.qr.common.repository.PSbResultDetailRepository;
import com.cnc.qr.common.repository.PTrioResultDetailRepository;
import com.cnc.qr.common.repository.RTableRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.TaxAmountInputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.acct.model.AgainRegistPaymentInputDto;
import com.cnc.qr.core.acct.model.DutchAccountPayLaterInputDto;
import com.cnc.qr.core.acct.model.DutchAccountPayLaterOutputDto;
import com.cnc.qr.core.acct.model.GetRefundsListInputDto;
import com.cnc.qr.core.acct.model.GetRefundsListOutputDto;
import com.cnc.qr.core.acct.model.GetSbPaymentInfoInputDto;
import com.cnc.qr.core.acct.model.GetSbPaymentInfoOutputDto;
import com.cnc.qr.core.acct.model.RefundsInputDto;
import com.cnc.qr.core.acct.model.RegistDutchAccountInputDto;
import com.cnc.qr.core.acct.model.RegistPaymentInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterOutputDto;
import com.cnc.qr.core.acct.model.SbPaymentCallBackInputDto;
import com.cnc.qr.core.acct.model.SbPaymentRefundsInputDto;
import com.cnc.qr.core.acct.model.SpsApiRequestDto;
import com.cnc.qr.core.acct.model.SpsApiResponseDto;
import com.cnc.qr.core.acct.model.SpsGateWayRefundResultDto;
import com.cnc.qr.core.acct.model.SpsGateWayResultDto;
import com.cnc.qr.core.acct.model.TrioPayLaterInputDto;
import com.cnc.qr.core.acct.model.TrioPayLaterOutputDto;
import com.cnc.qr.core.acct.service.PaymentService;
import com.cnc.qr.core.acct.service.WeChatAliPayService;
import com.cnc.qr.core.order.model.GetPayUrlInputDto;
import com.cnc.qr.core.order.model.ItemsDto;
import com.cnc.qr.core.order.model.OrderItemDto;
import com.cnc.qr.core.order.model.PaymentMethodDto;
import com.cnc.qr.security.service.MailService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * 会計処理サービス実装クラス.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    /**
     * 注文サマリテーブルタリポジトリ.
     */
    @Autowired
    private OOrderSummaryRepository orderSummaryRepository;

    /**
     * 注文テーブルタリポジトリ.
     */
    @Autowired
    private OOrderRepository orderRepository;

    /**
     * 注文明細テーブルタリポジトリ.
     */
    @Autowired
    private OOrderDetailsRepository orderDetailsRepository;

    /**
     * 注文明細オプションテーブルタリポジトリ.
     */
    @Autowired
    private OOrderDetailsOptionRepository orderDetailsOptionRepository;

    /**
     * 支払テーブル.
     */
    @Autowired
    private PPaymentRepository paymentRepository;

    /**
     * 支払明細テーブル.
     */
    @Autowired
    private PPaymentDetailRepository paymentDetailRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * トリオ支払結果明細テーブル.
     */
    @Autowired
    private PTrioResultDetailRepository trioResultDetailRepository;

    /**
     * トリオ支払テーブル.
     */
    @Autowired
    private PPaymentCompanyRepository paymentCompanyRepository;

    /**
     * 先払いサービス.
     */
    @Autowired
    private WeChatAliPayService wechatAliPayService;

    /**
     * ライセンスマスタリポジトリ.
     */
    @Autowired
    private MLicenseRepository licenseRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * SB支払結果明細テーブルリポジトリ.
     */
    @Autowired
    private PSbResultDetailRepository sbResultDetailRepository;

    /**
     * 店舗媒体マスタリポジトリ.
     */
    @Autowired
    private MStoreMediumRepository storeMediumRepository;

    /**
     * テーブル関連リポジトリ.
     */
    @Autowired
    private RTableRepository rtableRepository;

    /**
     * 受付リポジトリ.
     */
    @Autowired
    private OReceivablesRepository rreceivablesRepository;

    /**
     * トークンマスタリポジトリ.
     */
    @Autowired
    MTokenRepository tokenRepository;

    /**
     * 受付テーブルリポジトリ.
     */
    @Autowired
    private OReceivablesRepository receivablesRepository;

    /**
     * 出前注文サマリテーブルタリポジトリ.
     */
    @Autowired
    private ODeliveryOrderSummaryRepository deliveryOrderSummaryRepository;

    /**
     * 共通部品プリンター.
     */
    @Autowired
    OPrintQueueRepository printQueueRepository;

    /**
     * メールサービス.
     */
    @Autowired
    private MailService mailService;

    /**
     * 環境変数.
     */
    @Autowired
    private Environment env;

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
     * sbPaymentサーバーへ送信操作クラス.
     */
    @Qualifier("httpsRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    /**
     * ログ出力.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 現金支払.
     *
     * @param inputDto 支払情報
     */
    @Override
    public String registPayment(RegistPaymentInputDto inputDto) {

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 店舗ID
            String storeId = inputDto.getStoreId();

            // 受付ID
            String receivablesId = inputDto.getReceivablesId();

            // 支払い方式コード
            String paymentMethodCode = inputDto.getPaymentMethodCode();

            OOrderSummary order = orderSummaryRepository
                .findByStoreIdAndReceivablesIdAndDelFlag(storeId, receivablesId,
                    Flag.OFF.getCode());

            if (null == order) {

                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.049", (Object) null));
            }

            // 支払方式
            String paymentType = order.getPaymentType();

            if (paymentType.equals(PaymentType.ADVANCE_PAYMENT.getCode())
                && paymentMethodCode.equals(CodeConstants.AccountsType.CASH.getCode())) {

                throw new Exception();
            }

            // 支払いIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment"); // テーブル名
            getSeqNoInputDto.setItem("payment_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPayment inputDao = new PPayment();
            // 店舗ID
            inputDao.setStoreId(storeId);
            // 支払いID
            inputDao.setPaymentId(getSeqNo.getSeqNo());
            // 注文サマリID
            inputDao.setOrderSummaryId(order.getOrderSummaryId());
            // 支払い金額
            inputDao
                .setPaymentAmount(inputDto.getPaymentAmount());
            // 削除フラグ
            inputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            inputDao.setInsOperCd(userOperCd);
            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            inputDao.setInsDateTime(dateTime);
            // 更新者
            inputDao.setUpdOperCd(userOperCd);
            // 更新日時
            inputDao.setUpdDateTime(dateTime);
            // バージョン
            inputDao.setVersion(0);

            // 支払テーブル
            Integer paymentId = paymentRepository
                .insertOrUpdate(inputDao.getStoreId(), inputDao.getPaymentId(),
                    inputDao.getOrderSummaryId(),
                    inputDao.getPaymentAmount(),
                    inputDao.getDelFlag(),
                    inputDao.getInsOperCd(),
                    inputDao.getInsDateTime(),
                    inputDao.getUpdOperCd(),
                    inputDao.getUpdDateTime(),
                    inputDao.getVersion());

            // 支払い明細IDのシーケンスNo取得
            getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
            getSeqNoInputDto.setItem("payment_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPaymentDetail detailInputDao = new PPaymentDetail();
            // 店舗ID
            detailInputDao.setStoreId(storeId);
            // 支払い明細
            detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
            // 支払いID
            detailInputDao.setPaymentId(paymentId);
            // 注文サマリID
            detailInputDao.setOrderSummaryId(order.getOrderSummaryId());
            // 注文ID
            detailInputDao.setOrderId(0);
            // 支払い方式
            detailInputDao.setPaymentMethodCode(paymentMethodCode);
            // 支払い金額
            detailInputDao.setPaymentAmount(inputDto.getPaymentAmount());
            // 削除フラグ
            detailInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            detailInputDao.setInsOperCd(userOperCd);
            // 登録日時
            detailInputDao.setInsDateTime(dateTime);
            // 更新者
            detailInputDao.setUpdOperCd(userOperCd);
            // 更新日時
            detailInputDao.setUpdDateTime(dateTime);
            // バージョン
            detailInputDao.setVersion(0);
            // 支払明細テーブル
            paymentDetailRepository.save(detailInputDao);

            if (null == inputDto.getPriceDiscountAmount() && null == inputDto
                .getPriceDiscountRate()) {

                inputDto.setPriceDiscountAmount(BigDecimal.ZERO);
            }

            // 支払情報を更新する
            orderSummaryRepository.updatePayment(storeId, receivablesId,
                inputDto.getPriceDiscountAmount(), inputDto.getPriceDiscountRate(),
                inputDto.getPaymentAmount(), inputDto.getTakeoutFlag(), userOperCd, dateTime);

            // 外税初始化
            BigDecimal foreignTax;

            // 当前订单为先支付
            if (order.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                // 外税金额取得
                foreignTax = orderRepository.getForeignTax(storeId, order.getOrderSummaryId());
            } else {

                // 注文商品情報取得
                List<Map<String, Object>> orderItemListMap = orderSummaryRepository
                    .findOrderItemsByOrderSummaryId(inputDto.getStoreId(),
                        order.getOrderSummaryId());

                List<OrderItemDto> orderItemList = new ArrayList<>();
                orderItemListMap.forEach(stringObjectMap -> orderItemList.add(
                    JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                        OrderItemDto.class)));

                List<ItemsDto> itemList = new ArrayList<>();
                orderItemList.forEach(orderItemDto -> {
                    ItemsDto item = new ItemsDto();
                    item.setItemId(orderItemDto.getItemId().toString());
                    if (Objects.equals(ItemType.NORMAL.getCode(),
                        orderItemDto.getItemClassification())) {
                        item.setItemPrice(orderItemDto.getItemPrice());
                    } else {
                        item.setItemPrice(orderItemDto.getItemPrice()
                            .multiply(new BigDecimal(-1)));
                    }
                    itemList.add(item);
                });

                if (itemList.size() == 0) {

                    foreignTax = BigDecimal.ZERO;
                } else {

                    //调用共同方法获取税金
                    GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
                    taxValueInputDto.setStoreId(storeId);
                    taxValueInputDto.setTakeoutFlag(inputDto.getTakeoutFlag());
                    taxValueInputDto.setItemList(itemList);
                    GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
                        .getTaxValue(taxValueInputDto);
                    foreignTax = taxValueOutputDto.getSotoTaxEight()
                        .add(taxValueOutputDto.getSotoTaxTen());

                }
            }

            // 支払完了判断
            boolean checkPaymenyed = false;

            if (null == inputDto.getPriceDiscountAmount() && null == inputDto
                .getPriceDiscountRate()) {

                if (inputDto.getPaymentAmount().add(order.getPaymentAmount())
                    .compareTo(order.getOrderAmount().add(foreignTax)) == 0) {

                    checkPaymenyed = true;
                }
            }

            if (null != inputDto.getPriceDiscountAmount() && null == inputDto
                .getPriceDiscountRate()) {

                if (inputDto.getPaymentAmount().add(order.getPaymentAmount())
                    .add(inputDto.getPriceDiscountAmount())
                    .compareTo(order.getOrderAmount().add(foreignTax)) == 0) {

                    checkPaymenyed = true;
                }
            }

            if (null == inputDto.getPriceDiscountAmount() && null != inputDto
                .getPriceDiscountRate()) {

                if (inputDto.getPaymentAmount().add(order.getPaymentAmount()).compareTo(
                    (order.getOrderAmount().add(foreignTax))
                        .multiply((new BigDecimal("10").subtract(inputDto.getPriceDiscountRate())))
                        .divide(new BigDecimal("10"), 0, RoundingMode.HALF_UP)) == 0) {

                    checkPaymenyed = true;
                }
            }

            if (checkPaymenyed) {

                // 支払状態を更新する
                orderRepository.updatePaymentStatus(storeId, order.getOrderSummaryId(),
                    PayStatus.PAY_ALREADY.getCode(), userOperCd, dateTime);

                // 席解除処理
                orderSummaryRepository
                    .updateSeatReleaseByOrderSummaryId(storeId, order.getOrderSummaryId(),
                        PayStatus.PAY_NOT_ALREADY.getCode(), Flag.ON.getCode().toString(),
                        userOperCd, dateTime);

                List<String> receivablesIdList = rtableRepository
                    .getReceivablesId(storeId, receivablesId);

                rtableRepository.deleteByStoreIdAndRealReceivablesId(storeId, receivablesId);

                rreceivablesRepository.deleteReceivables(storeId, receivablesIdList);

                orderSummaryRepository.deleteReceivables(storeId, receivablesIdList);

                tokenRepository.deleteReceivables(storeId, receivablesId);
            }
            return order.getOrderSummaryId();
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.043", (Object) null), ex);
        }
    }

    /**
     * 再支払.
     *
     * @param inputDto 支払情報
     */
    @Override
    @Transactional
    public OOrderSummary againRegistPayment(AgainRegistPaymentInputDto inputDto) {

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 店舗ID
            String storeId = inputDto.getStoreId();

            // 受付ID
            String receivablesId = inputDto.getReceivablesId();

            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            OOrderSummary order = orderSummaryRepository.findByStoreIdAndReceivablesIdAndDelFlag(
                storeId, receivablesId, Flag.OFF.getCode());

            // 取得結果チェック
            if (null == order) {

                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.049", (Object) null));
            }

            // 現金支払金額取得
            BigDecimal refundsMoney = paymentDetailRepository
                .getCashPayAmount(inputDto.getStoreId(),
                    order.getOrderSummaryId(), CodeConstants.AccountsType.CASH.getCode());

            // 更新支付表支付金额
            Integer paymentId = paymentRepository
                .updatePaymentAmount(inputDto.getStoreId(), order.getOrderSummaryId(),
                    refundsMoney, userOperCd, dateTime);

            // 支払い明細IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
            getSeqNoInputDto.setItem("payment_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPaymentDetail detailInputDao = new PPaymentDetail();
            // 店舗ID
            detailInputDao.setStoreId(inputDto.getStoreId());
            // 支払い明細
            detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
            // 支払いID
            detailInputDao.setPaymentId(paymentId);
            // 注文サマリID
            detailInputDao.setOrderSummaryId(order.getOrderSummaryId());
            // 注文ID
            detailInputDao.setOrderId(0);
            // 支払い方式
            detailInputDao.setPaymentMethodCode(CodeConstants.AccountsType.CASH.getCode());
            // 支払い金額
            detailInputDao.setPaymentAmount(refundsMoney.multiply(new BigDecimal(-1)));
            // 削除フラグ
            detailInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            detailInputDao.setInsOperCd(userOperCd);
            // 登録日時
            detailInputDao.setInsDateTime(dateTime);
            // 更新者
            detailInputDao.setUpdOperCd(userOperCd);
            // 更新日時
            detailInputDao.setUpdDateTime(dateTime);
            // バージョン
            detailInputDao.setVersion(0);
            // 支払明細テーブル
            paymentDetailRepository.save(detailInputDao);

            // 支払状態を更新する
            orderRepository.updatePaymentStatus(inputDto.getStoreId(), order.getOrderSummaryId(),
                PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);

            // 支払情報を更新する
            orderSummaryRepository
                .updatePaymentAmount(inputDto.getStoreId(), order.getOrderSummaryId(),
                    refundsMoney.multiply(new BigDecimal(-1)), userOperCd, dateTime);

            // 支付金额为0则清空折扣信息
            orderSummaryRepository.clearDiscount(inputDto.getStoreId(), order.getOrderSummaryId(),
                userOperCd, dateTime);

            if (null != order.getTableId()) {
                orderSummaryRepository.updateSeatReleaseByReceivablesId(
                    inputDto.getStoreId(), order.getReceivablesId(),
                    Flag.OFF.getCode().toString(),
                    order.getTableId(), userOperCd, dateTime);
            }
            return order;
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.042", (Object) null), ex);
        }
    }

    /**
     * 返金一覧.
     *
     * @param inputDto パラメータ
     */
    @Override
    public Page<GetRefundsListOutputDto> getRefundsList(GetRefundsListInputDto inputDto,
        Pageable pageable) {

        // アウトプットDTO初期化
        List<GetRefundsListOutputDto> orderList = new ArrayList<>();

        // 开始时间
        String startTime = "";

        // 结束时间
        String endTime = "";

        if (null == inputDto.getOrderDateStart() || "".equals(inputDto.getOrderDateStart())) {

            startTime = "";
        } else {

            startTime = inputDto.getOrderDateStart() + " " + CommonConstants.START_DATE_TIME;
        }

        if (null == inputDto.getOrderDateEnd() || "".equals(inputDto.getOrderDateEnd())) {

            endTime = "";
        } else {

            endTime = inputDto.getOrderDateEnd() + " " + CommonConstants.END_DATE_TIME;
        }

        // 返金一覧取得
        Page<Map<String, Object>> orderMap = trioResultDetailRepository
            .getRefundsList(inputDto.getStoreId(),
                inputDto.getReceivablesNo() == null ? 0 : inputDto.getReceivablesNo(), startTime,
                endTime, CommonConstants.CODE_GROUP_PAYMENT, PayResult.SUCCESS.getCode(), pageable);

        orderMap.forEach(stringObjectMap -> orderList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                GetRefundsListOutputDto.class)));

        return new PageImpl<>(orderList, orderMap.getPageable(), orderMap.getTotalPages());
    }

    /**
     * 返金.
     *
     * @param inputDto 支払情報
     */
    @Override
    @Transactional
    public void refunds(RefundsInputDto inputDto) {

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNo().substring(0, 20);

            // 注文ID
            String orderId = inputDto.getOrderNo()
                .substring(20, inputDto.getOrderNo().length() - 2);

            OOrderSummary order = orderSummaryRepository
                .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                    Flag.OFF.getCode());

            // 判断是否为先支付
            if (order.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                // 获取先支付且未支付的数据
                Integer count = orderRepository
                    .getUnPayOrderCount(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode());

                // 存在未支付的数据
                if (count != 0) {

                    // 提示不能退款
                    throw new BusinessException("2001",
                        ResultMessages.error().add("e.qr.ph.046", (Object) null));
                }
            }

            // 获取支付信息
            PTrioResultDetail payData = trioResultDetailRepository
                .getPaymentAmount(inputDto.getStoreId(),
                    inputDto.getOrderNo(), PayResult.SUCCESS.getCode());

            if (null == payData) {

                // 提示支付数据获取异常
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.047", (Object) null));
            }

            // トリオ支払テーブル情報取得
            PPaymentCompany trioCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyId(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.TRIO.getCode());

            // 迅联分配或登记的门店号
            String storeId = trioCompany.getStoreCode();

            // 支付金额
            String transAmt = String.format("%012d", payData.getPayPrice().intValue());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            // 当前时间
            String refundTime = sdf.format(new Date()) + DateFormatUtils.format(new Date(), "Z");

            String tradeFrom = "openapi";

            String http = "POST";

            // CIL分配的用于签名/验签的key
            String key = trioCompany.getKey();

            // 第三方接口访问路径的域名
            String companyUrl = trioCompany.getUrl();

            // 讯联分配的唯一标识商户号
            String sid = trioCompany.getSid();

            String refundUrls = "/scanpay/mer/" + sid + "/refd/v0";

            // メッセージID
            String refundMsgId = UUID.randomUUID().toString().replaceAll("-", "");

            // 退款单号
            String refundId = inputDto.getOrderNo() + "refunds";

            SortedMap<Object, Object> refundPackageParams = new TreeMap<Object, Object>();
            refundPackageParams.put("msgId", refundMsgId);
            refundPackageParams.put("orderNum", refundId);
            refundPackageParams.put("storeId", storeId);
            refundPackageParams.put("origOrderNum", inputDto.getOrderNo());
            refundPackageParams.put("transAmt", transAmt);
            refundPackageParams.put("merTransTime", refundTime);
            refundPackageParams.put("tradeFrom", tradeFrom);

            String refundStrdata = JSONObject.toJSONString(refundPackageParams);

            String refundSign = http + "\n";
            refundSign += refundUrls + "\n";
            refundSign += refundTime + "\n";
            refundSign += key + "\n";
            refundSign += refundStrdata;
            refundSign = getSha256(refundSign);

            String refundUrl = companyUrl + refundUrls;
            log.info("TRIO API 返金開始: refundUrl={} data={} refundSign={} refundTime={}", refundUrl,
                refundStrdata, refundSign, refundTime);
            String refundResStr = postDataSign(refundUrl, refundStrdata, refundSign, refundTime);
            log.info("TRIO API 返金結果: resStr={}", refundResStr);

            // 返回结果类型转化
            JSONObject refundReturnData = JSONObject.parseObject(refundResStr);

            String respCode = refundReturnData.get("respCode").toString();

            // 更新支付履历表
            trioResultDetailRepository.updateRefunds(inputDto.getStoreId(),
                inputDto.getOrderNo(), PayResult.SUCCESS.getCode(), refundId,
                respCode, userOperCd, dateTime);

            // 退款成功的场合
            if (PayResult.SUCCESS.getCode().equals(respCode)) {

                // 更新支付表支付金额
                Integer paymentId = paymentRepository
                    .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                        payData.getPayPrice(), userOperCd, dateTime);

                // 支払い明細IDのシーケンスNo取得
                GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
                getSeqNoInputDto.setItem("payment_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                // 支払い方式コード
                String paymentMethodCode = payData.getPaymentMethodCode();

                PPaymentDetail detailInputDao = new PPaymentDetail();
                // 店舗ID
                detailInputDao.setStoreId(inputDto.getStoreId());
                // 支払い明細
                detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
                // 支払いID
                detailInputDao.setPaymentId(paymentId);
                // 注文サマリID
                detailInputDao.setOrderSummaryId(orderSummaryId);
                // 注文ID
                if (StringUtils.isNotEmpty(orderId)) {

                    detailInputDao.setOrderId(Integer.valueOf(orderId));
                } else {

                    detailInputDao.setOrderId(0);
                }
                // 支払い方式
                detailInputDao.setPaymentMethodCode(paymentMethodCode);
                // 支払い金額
                detailInputDao.setPaymentAmount(payData.getPayPrice().multiply(new BigDecimal(-1)));
                // 削除フラグ
                detailInputDao.setDelFlag(Flag.OFF.getCode());
                // 登録者
                detailInputDao.setInsOperCd(userOperCd);
                // 登録日時
                detailInputDao.setInsDateTime(dateTime);
                // 更新者
                detailInputDao.setUpdOperCd(userOperCd);
                // 更新日時
                detailInputDao.setUpdDateTime(dateTime);
                // バージョン
                detailInputDao.setVersion(0);
                // 支払明細テーブル
                paymentDetailRepository.save(detailInputDao);

                if (StringUtils.isNotEmpty(orderId)) {

                    // 支払状態を更新する
                    orderRepository.updateStatus(inputDto.getStoreId(), orderSummaryId,
                        Integer.valueOf(orderId),
                        CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                } else {

                    // 支払状態を更新する
                    orderRepository.updatePaymentStatus(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                }

                // 支払情報を更新する
                orderSummaryRepository.updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                    payData.getPayPrice().multiply(new BigDecimal(-1)), userOperCd, dateTime);

                // 支付金额为0则清空折扣信息
                orderSummaryRepository.clearDiscount(inputDto.getStoreId(), orderSummaryId,
                    userOperCd, dateTime);
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.045", (Object) null), ex);
        }
    }

    /**
     * SBペイメント情报取得.
     *
     * @param inputDto 取得条件
     * @return ペイメント情报
     */
    @Override
    @Transactional
    public GetSbPaymentInfoOutputDto getSbPaymentInfo(GetSbPaymentInfoInputDto inputDto) {

        // トリオ支払テーブル情報取得
        PPaymentCompany sbCompany = paymentCompanyRepository
            .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(inputDto.getStoreId(),
                Flag.OFF.getCode(), Company.SB.getCode(), CompanyMethod.PAYMENT.getCode());

        // 店舗情報取得
        MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (storeInfo == null) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 支払方式情報取得
        PaymentMethodDto storePaymentInfo = licenseRepository
            .findStorePaymentByCode(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PAYMENT,
                inputDto.getPaymentMethodCode());

        // 検索結果0件の場合
        if (storePaymentInfo == null) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.007", (Object) null));
        }

        // 店舗媒体情報取得
        List<MStoreMedium> storeMediumList = storeMediumRepository
            .findByStoreIdAndMediumTypeAndTerminalDistinctionAndDelFlagOrderBySortOrderAsc(
                inputDto.getStoreId(), MediumType.LOGO.getCode(),
                TerminalDistinction.CSMB.getCode(), Flag.OFF.getCode());

        // 支払履歴ID
        Integer maxDetailId = insetSbResultDetail(inputDto.getStoreId(),
            inputDto.getOrderSummaryId(), inputDto.getOrderId(), inputDto.getPayAmount(),
            inputDto.getPaymentMethodCode(), "01");
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

        // 前台回调方法URL
        String frontUrl =
            env.getProperty("qr.env.nginx.service.url") + "/#/mobile-placeOrder/payBack?"
                + "storeId=" + inputDto.getStoreId() + "&receivablesId=" + inputDto
                .getReceivablesId() + "&token=" + inputDto.getToken() + "&flgLanguage="
                + inputDto.getLanguages() + "&no=" + inputDto.getNo();

        // 結果DTO初期化
        GetSbPaymentInfoOutputDto outDto = new GetSbPaymentInfoOutputDto();

        // ペイメントUrl
        outDto.setActionUrl(sbCompany.getUrl());
        // 支払方法
        outDto.setPayMethod(storePaymentInfo.getPaymentName().toLowerCase());
        // マーチャントID
        outDto.setMerchantId(sbCompany.getStoreCode());
        // サービスID
        outDto.setServiceId(sbCompany.getSid());
        // 顧客ID
        outDto.setCustCode(inputDto.getReceivablesId() + "-" + maxDetailId);
        // 購入ID
        outDto.setOrderId(inputDto.getOrderSummaryId() + inputDto.getOrderId() + "01");
        // リクエスト日時
        String requestDate = DateUtil.getNowDateTimeString(CommonConstants.DATE_FORMAT_DATE_TIMES);
        // 商品ID
        outDto.setItemId(storeInfo.getStoreId() + "-" + requestDate + "-" + maxDetailId);
        // 商品名称
        outDto.setItemName(storeInfo.getStoreName());
        // 金額（税込）
        outDto.setAmount(inputDto.getPayAmount());
        // 購入タイプ 0：都度課金
        outDto.setPayType("0");
        // サービスタイプ 0：売上（購入）
        outDto.setServiceType(ServiceType.PURCHASE.getCode());
        // 顧客利用端末タイプ 0：PC
        outDto.setTerminalType("0");
        // 決済完了時URL
        outDto.setSuccessUrl(frontUrl + "&resResult=0");
        // 決済キャンセル時URL
        outDto.setCancelUrl(frontUrl + "&resResult=1");
        // エラー時URL
        outDto.setErrorUrl(frontUrl + "&resResult=1");
        // 決済通知用CGI
        outDto.setPageconUrl(
            env.getProperty("qr.env.nginx.service.url") + UrlConstants.CSMB_SB_PAYMENT_CALLBACK);
        // 自由欄１
        outDto.setFree1(
            inputDto.getStoreId() + "," + maxDetailId + "," + inputDto.getPaymentMethodCode());
        // 自由欄２
        outDto.setFree2("37f347d87a3fd402a46b");
        // 自由欄３
        outDto.setFree3("34d07cebbfd7");
        // フリー項目
        // LinePayの場合
        if (Objects.equals(AccountsType.LINEPAY.getCode(), inputDto.getPaymentMethodCode())
            && CollectionUtils.isNotEmpty(storeMediumList)) {
            outDto.setFreeCsv("PRODUCT_IMAGE_URL=" + storeMediumList.get(0).getImagePath());
        } else {
            outDto.setFreeCsv(StringUtils.EMPTY);
        }
        // リクエスト日時
        outDto.setRequestDate(requestDate);
        // リクエスト許容時間
        outDto.setLimitSecond("600");
        // ハッシュキー
        outDto.setHashKey(sbCompany.getKey());

        return outDto;
    }

    /**
     * 後払い.
     *
     * @param inputDto 支払情報
     */
    @Override
    @Transactional
    public TrioPayLaterOutputDto payLater(TrioPayLaterInputDto inputDto) {

        String respCode = "";

        String cancelRespCode = "";

        TrioPayLaterOutputDto outputDto = new TrioPayLaterOutputDto();

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 店舗ID
            String storeId = inputDto.getStoreId();

            // 受付ID
            String receivablesId = inputDto.getReceivablesId();

            //該当サマリIDを取得する
            OOrderSummary orderSummary = orderSummaryRepository
                .findByStoreIdAndReceivablesIdAndDelFlag(storeId,
                    receivablesId, Flag.OFF.getCode());

            if (null == orderSummary) {

                // 注文情報取得異常
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.049", (Object) null));
            }

            Integer orderId;

            // 支払方式
            String paymentType = orderSummary.getPaymentType();

            if (paymentType.equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                orderId = orderRepository
                    .getUnPayOrderId(inputDto.getStoreId(), orderSummary.getOrderSummaryId(),
                        PayStatus.PAY_NOT_ALREADY.getCode());

                if (null != inputDto.getPriceDiscountAmount() || null != inputDto
                    .getPriceDiscountRate()) {

                    throw new Exception();
                }
            } else {

                orderId = 0;
            }

            // 注文番号を取得する
            String orderSummaryNo = trioResultDetailRepository
                .getOrderSummaryNo(inputDto.getStoreId(),
                    orderSummary.getOrderSummaryId(), orderId, PayResult.SUCCESS.getCode());

            // 注文番号編集
            String maxId = "01";
            if (StringUtils.isNotEmpty(orderSummaryNo)) {

                if (Integer.parseInt(orderSummaryNo.substring(orderSummaryNo.length() - 2)) + 1
                    > 99) {

                    throw new Exception();
                }

                maxId = String.format("%02d",
                    Integer.parseInt(orderSummaryNo.substring(orderSummaryNo.length() - 2)) + 1);
            }

            // 插入支付履历表参数做成
            GetPayUrlInputDto inputPayDetailDto = new GetPayUrlInputDto();
            inputPayDetailDto.setStoreId(storeId);
            inputPayDetailDto.setOrderSummaryId(orderSummary.getOrderSummaryId());
            inputPayDetailDto.setOrderId(orderId);
            inputPayDetailDto.setMaxId(maxId);
            inputPayDetailDto.setPaymentMethodCode(AccountsType.WECHATPAY.getCode());
            inputPayDetailDto.setPayAmount(inputDto.getPayAmount().toString());

            // 插入支付履历表
            Integer maxDetailId = wechatAliPayService.insetPayResultData(inputPayDetailDto);

            // トリオ支払テーブル情報取得
            PPaymentCompany trioCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyId(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.TRIO.getCode());

            // メッセージID
            String msgId = UUID.randomUUID().toString().replaceAll("-", "");

            // 日付時刻フォーマット
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_DATE_TIMES);

            // 注文番号初期化
            String orderNo = "";

            // 注文番号設定
            if (orderId == 0) {

                orderNo = orderSummary.getOrderSummaryId() + maxId;
            } else {

                orderNo = orderSummary.getOrderSummaryId() + orderId + maxId;
            }

            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            packageParams.put("msgId", msgId);
            packageParams.put("orderNum", orderNo);
            // 迅联分配或登记的门店号
            String storeCd = trioCompany.getStoreCode();
            packageParams.put("storeId", storeCd);
            // 支付金额
            String transAmt = String.format("%012d", 1);
            packageParams.put("transAmt", transAmt);
            // 货币单位
            String transCurrency = "JPY";
            packageParams.put("transCurrency", transCurrency);
            packageParams.put("scanCodeId", inputDto.getPayCodeId());
            String tradeFrom = "openapi";
            packageParams.put("tradeFrom", tradeFrom);
            // 現在時刻文字列取得
            String merTransTime = sdf.format(new Date()) + DateFormatUtils.format(new Date(), "Z");
            packageParams.put("merTransTime", merTransTime);
            // 第三方接口访问路径的域名
            String companyUrl = trioCompany.getUrl();

            // 讯联分配的唯一标识商户号
            String sid = trioCompany.getSid();

            String urls = "/scanpay/mer/" + sid + "/purc/v0";

            // 请求方式
            String http = "POST";

            String sign = http + "\n";
            sign += urls + "\n";
            sign += merTransTime + "\n";
            // CIL分配的用于签名/验签的key
            String key = trioCompany.getKey();
            sign += key + "\n";
            String strdata = JSONObject.toJSONString(packageParams);
            sign += strdata;
            sign = getSha256(sign);

            String url = companyUrl + urls;
            log.info("TRIO API 決済開始: url={} data={} sign={} merTransTime={}", url, strdata, sign,
                merTransTime);
            String resStr = postDataSign(url, strdata, sign, merTransTime);
            log.info("TRIO API 決済結果: resStr={}", resStr);
            // 返回结果类型转化
            JSONObject returnData = JSONObject.parseObject(resStr);

            if (null != returnData) {

                respCode = returnData.get("respCode").toString();

                if (!"09".equals(respCode)) {

                    // 更新日時
                    ZonedDateTime dateTime = DateUtil.getNowDateTime();

                    // トリオ支払結果明細テーブル
                    trioResultDetailRepository
                        .updatePayStatus(storeId, maxDetailId, orderNo, respCode, userOperCd,
                            dateTime);

                    if ("00".equals(respCode)) {

                        // 成功处理
                        RegistPaymentInputDto registDto = new RegistPaymentInputDto();
                        registDto.setStoreId(storeId);
                        registDto.setReceivablesId(receivablesId);
                        registDto.setPaymentMethodCode(AccountsType.WECHATPAY.getCode());
                        registDto.setPaymentAmount(inputDto.getPayAmount());
                        registDto.setTakeoutFlag(inputDto.getTakeOutFlag());
                        registDto.setPriceDiscountAmount(inputDto.getPriceDiscountAmount());
                        registDto.setPriceDiscountRate(inputDto.getPriceDiscountRate());
                        registPayment(registDto);
                    }
                } else {

                    int i = 0;

                    String searchUrls = "/scanpay/mer/" + sid + "/inqy/v0";

                    String searchTime = "";

                    String searchId = "";

                    String searchMsgId = "";

                    while ("09".equals(respCode) && i < 10) {

                        searchId = orderNo + "search";
                        searchMsgId = UUID.randomUUID().toString().replaceAll("-", "");

                        SortedMap<Object, Object> searchPackageParams = new TreeMap<Object, Object>();
                        searchPackageParams.put("msgId", searchMsgId);
                        searchPackageParams.put("orderNum", searchId);
                        searchPackageParams.put("tradeFrom", tradeFrom);
                        searchPackageParams.put("origOrderNum", orderNo);
                        searchPackageParams.put("storeId", storeCd);

                        searchTime =
                            sdf.format(new Date()) + DateFormatUtils.format(new Date(), "Z");

                        String searchSign = http + "\n";
                        searchSign += searchUrls + "\n";
                        searchSign += searchTime + "\n";
                        searchSign += key + "\n";
                        String searchStrdata = JSONObject.toJSONString(searchPackageParams);
                        searchSign += searchStrdata;
                        searchSign = getSha256(searchSign);

                        String searhUrl = companyUrl + searchUrls;

                        log.info("TRIO API 決済履歴検索開始: searchUrl={} data={} sign={} searchTime={}",
                            searhUrl, searchStrdata, searchSign, searchTime);
                        String searchResStr = postDataSign(searhUrl, searchStrdata, searchSign,
                            searchTime);
                        log.info("TRIO API 決済履歴検索結果: searchResStr={}", searchResStr);
                        JSONObject searchReturnData = JSONObject.parseObject(searchResStr);

                        respCode = searchReturnData.get("respCode").toString();

                        if (!"09".equals(respCode)) {

                            // 更新日時
                            ZonedDateTime dateTime = DateUtil.getNowDateTime();

                            // トリオ支払結果明細テーブル
                            trioResultDetailRepository
                                .updatePayStatus(storeId, maxDetailId, orderNo, respCode,
                                    userOperCd,
                                    dateTime);

                            if ("00".equals(respCode)) {

                                // 成功处理
                                RegistPaymentInputDto registDto = new RegistPaymentInputDto();
                                registDto.setStoreId(storeId);
                                registDto.setReceivablesId(receivablesId);
                                registDto.setPaymentMethodCode(AccountsType.WECHATPAY.getCode());
                                registDto.setPaymentAmount(inputDto.getPayAmount());
                                registDto.setTakeoutFlag(inputDto.getTakeOutFlag());
                                registDto.setPriceDiscountAmount(inputDto.getPriceDiscountAmount());
                                registDto.setPriceDiscountRate(inputDto.getPriceDiscountRate());
                                registPayment(registDto);
                            }
                        } else {

                            i = i + 1;
                        }
                    }

                    if ("09".equals(respCode)) {

                        String cancelId = orderNo + "cancel";
                        String cancelTime =
                            sdf.format(new Date()) + DateFormatUtils.format(new Date(), "Z");
                        String cancelMsgId = UUID.randomUUID().toString().replaceAll("-", "");

                        SortedMap<Object, Object> cancelPackageParams = new TreeMap<Object, Object>();
                        cancelPackageParams.put("msgId", cancelMsgId);
                        cancelPackageParams.put("orderNum", cancelId);
                        cancelPackageParams.put("storeId", storeCd);
                        cancelPackageParams.put("origOrderNum", orderNo);
                        cancelPackageParams.put("merTransTime", cancelTime);
                        cancelPackageParams.put("tradeFrom", tradeFrom);

                        String cancelUrls = "/scanpay/mer/" + sid + "/canc/v0";
                        String cancelSign = http + "\n";
                        cancelSign += cancelUrls + "\n";
                        cancelSign += cancelTime + "\n";
                        cancelSign += key + "\n";
                        String cancelStrdata = JSONObject.toJSONString(cancelPackageParams);
                        cancelSign += cancelStrdata;
                        cancelSign = getSha256(cancelSign);

                        String cancelUrl = companyUrl + cancelUrls;
                        log.info("TRIO API 決済取消開始: cancelUrl={} data={} sign={} cancelTime={}",
                            cancelUrl, cancelStrdata, cancelSign, cancelTime);
                        String cancelResStr = postDataSign(cancelUrl, cancelStrdata, cancelSign,
                            cancelTime);
                        log.info("TRIO API 決済取消結果: cancelResStr={}", cancelResStr);
                        JSONObject cancelReturnData = JSONObject.parseObject(cancelResStr);

                        cancelRespCode = cancelReturnData.get("respCode").toString();

                        // 更新日時
                        ZonedDateTime dateTime = DateUtil.getNowDateTime();

                        // トリオ支払結果明細テーブル
                        trioResultDetailRepository.updatePayCancelStatus(maxDetailId,
                            orderNo, respCode, cancelId, cancelRespCode, userOperCd, dateTime);
                    }
                }
            } else {

                throw new Exception();
            }

            outputDto.setRespCode(respCode);
            outputDto.setCancelRespCode(cancelRespCode);

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.048", (Object) null), ex);
        }

        return outputDto;
    }

    /**
     * SBペイメントコールバック.
     *
     * @param inputDto 支払データ
     */
    @Override
    @Transactional
    public void sbPaymentCallBack(SbPaymentCallBackInputDto inputDto) {

        // 注文情報
        String[] orderInfo = inputDto.getFree1().split(",");
        // 店舗ID
        String storeId = orderInfo[0];

        // 指定注文サマリーIDデータロック
        OOrderSummary orderSummaryData = orderSummaryRepository
            .findByStoreIdAndOrderSummaryId(storeId, inputDto.getOrderId().substring(0, 20));

        // 注文データ存在しない場合
        if (orderSummaryData == null) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.040", (Object) null));
        }

        // 支払状態更新
        updateResultDetail(inputDto, storeId, orderInfo[2], orderInfo[1]);

        // 処理結果ステータス OK以外の場合
        if (Objects.equals(ResResult.OK.getCode(), inputDto.getResResult())) {
            // 支払い情報を作成する
            insertPaymentData(inputDto, storeId, orderInfo[2]);
            // 注文状態変更
            Integer buffetOrderId = changeOrderStatus(storeId, inputDto.getOrderId(),
                inputDto.getAmount());

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderId().substring(0, 20);

            // 注文ID
            String orderId = inputDto.getOrderId()
                .substring(20, inputDto.getOrderId().length() - 2);

            if (null != buffetOrderId) {

                orderId = buffetOrderId.toString() + "," + orderId;
            }

            OPrintQueue orderData = new OPrintQueue();
            orderData.setStoreId(storeId);
            orderData.setOrderSummaryId(orderSummaryId);
            orderData.setOrderId(orderId);
            orderData.setPaymentAmount(inputDto.getAmount());
            orderData.setPrintStatus(PrintStatus.UNPRINT.getCode());
            orderData.setStaffCheck(StaffCheckFlag.NO.getCode());
            orderData.setDelFlag(Flag.OFF.getCode());
            orderData.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            orderData.setInsDateTime(dateTime);
            orderData.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            orderData.setUpdDateTime(dateTime);
            orderData.setVersion(0);
            printQueueRepository.save(orderData);
        } else {
            // 注文情報を回復する
            revertOrderInfo(storeId, inputDto.getOrderId(), inputDto.getAmount());
        }
    }

    /**
     * SBペイメント返金.
     *
     * @param inputDto 返金情報
     */
    @Override
    @Transactional
    public void sbPaymentRefunds(SbPaymentRefundsInputDto inputDto) {
        try {

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNo().substring(0, 20);

            // 注文ID
            String orderId = inputDto.getOrderNo()
                .substring(20, inputDto.getOrderNo().length() - 2);

            // 注文サマリーデータ取得
            OOrderSummary order = orderSummaryRepository
                .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                    Flag.OFF.getCode());

            // 先払の場合未支払データ存在チェック
            if (order.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                // 未支払データを取得する
                Integer count = orderRepository
                    .getUnPayOrderCount(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode());

                // データが存在の場合
                if (count != 0) {
                    throw new BusinessException("2001",
                        ResultMessages.error().add("e.qr.ph.046", (Object) null));
                }
            }

            // 支払情報取得
            PSbResultDetail payData = sbResultDetailRepository
                .getPaymentInfo(inputDto.getStoreId(),
                    inputDto.getOrderNo(), PayResult.SUCCESS.getCode());

            // データが存在しない場合
            if (payData == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.047", (Object) null));
            }

            // トリオ支払テーブル情報取得
            PPaymentCompany sbCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.SB.getCode(),
                    CompanyMethod.REFUNDS.getCode());

            // 返金要求データ作成
            SpsApiRequestDto requestDto = new SpsApiRequestDto();

            // 機能ID
            requestDto.setId(SbRefundsId.getSbRefundsId(payData.getPaymentMethodCode()));

            // マーチャントID
            requestDto.setMerchantId(sbCompany.getStoreCode());

            // サービスID
            requestDto.setServiceId(sbCompany.getSid());

            // 処理トラッキングID
            requestDto.setResTrackingId(payData.getResTrackingId());

            // リクエスト日時
            requestDto.setRequestDate(
                DateUtil.getNowDateTimeString(CommonConstants.DATE_FORMAT_DATE_TIMES));

            // リクエスト許容時間
            requestDto.setLimitSecond("600");

            // 送信情報データ連結
            String hashData = requestDto.getMerchantId() + requestDto.getServiceId() + requestDto
                .getResTrackingId() + requestDto.getRequestDate() + requestDto.getLimitSecond()
                + sbCompany.getKey();

            // チェックサム
            requestDto.setSpsHashcode(Md5Util.toSha(hashData, "SHA-1"));

            // 返金要求
            log.info("SB前払 API 返金要求開始: request={} url={}", requestDto, sbCompany.getUrl());
            SpsApiResponseDto spsApiResponse = spsPayRequest(requestDto, sbCompany.getUrl(),
                sbCompany.getStoreCode() + sbCompany.getSid(), sbCompany.getKey());
            log.info("SB前払 API 返金要求結果: response={}", spsApiResponse);
            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 処理結果ステータス
            String respCode = PayResult.SUCCESS.getCode();
            if (!Objects.equals(ResResult.OK.getCode(), spsApiResponse.getResResult())) {
                respCode = PayResult.FAILED.getCode();
            }

            // 返金結果更新
            sbResultDetailRepository.updateRefunds(inputDto.getStoreId(), inputDto.getOrderNo(),
                PayResult.SUCCESS.getCode(), spsApiResponse.getResSpsTransactionId(),
                spsApiResponse.getResProcessDate(), respCode, spsApiResponse.getResErrCode(),
                userOperCd, dateTime);

            // 正常系レスポンスの場合
            if (Objects.equals(ResResult.OK.getCode(), spsApiResponse.getResResult())) {

                // 支払い明細IDのシーケンスNo取得
                GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
                getSeqNoInputDto.setItem("payment_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                // 支払い方式コード
                String paymentMethodCode = payData.getPaymentMethodCode();

                PPaymentDetail detailInputDao = new PPaymentDetail();
                // 店舗ID
                detailInputDao.setStoreId(inputDto.getStoreId());
                // 支払い明細
                detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
                // 更新支付表支付金额
                Integer paymentId = paymentRepository
                    .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                        payData.getPayPrice(), userOperCd, dateTime);
                // 支払いID
                detailInputDao.setPaymentId(paymentId);
                // 注文サマリID
                detailInputDao.setOrderSummaryId(orderSummaryId);
                // 注文ID
                if (StringUtils.isNotEmpty(orderId)) {
                    detailInputDao.setOrderId(Integer.valueOf(orderId));
                } else {
                    detailInputDao.setOrderId(0);
                }
                // 支払い方式
                detailInputDao.setPaymentMethodCode(paymentMethodCode);
                // 支払い金額
                detailInputDao.setPaymentAmount(payData.getPayPrice().multiply(new BigDecimal(-1)));
                // 削除フラグ
                detailInputDao.setDelFlag(Flag.OFF.getCode());
                // 登録者
                detailInputDao.setInsOperCd(userOperCd);
                // 登録日時
                detailInputDao.setInsDateTime(dateTime);
                // 更新者
                detailInputDao.setUpdOperCd(userOperCd);
                // 更新日時
                detailInputDao.setUpdDateTime(dateTime);
                // バージョン
                detailInputDao.setVersion(0);
                // 支払明細テーブル
                paymentDetailRepository.save(detailInputDao);

                if (StringUtils.isNotEmpty(orderId)) {

                    // 支払状態を更新する
                    orderRepository.updateStatus(inputDto.getStoreId(), orderSummaryId,
                        Integer.valueOf(orderId),
                        CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                } else {

                    // 支払状態を更新する
                    orderRepository.updatePaymentStatus(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                }

                // 支払情報を更新する
                orderSummaryRepository.updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                    payData.getPayPrice().multiply(new BigDecimal(-1)), userOperCd, dateTime);

                // 支付金额が0の場合、值引クリア
                orderSummaryRepository.clearDiscount(inputDto.getStoreId(), orderSummaryId,
                    userOperCd, dateTime);

                if (null != order.getTableId()) {
                    orderSummaryRepository.updateSeatReleaseByReceivablesId(
                        inputDto.getStoreId(), order.getReceivablesId(),
                        Flag.OFF.getCode().toString(),
                        order.getTableId(), userOperCd, dateTime);
                }
            } else {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.045", (Object) null));
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.045", (Object) null), ex);
        }
    }

    /**
     * SBペイメント後払.
     *
     * @param inputDto 支払情報
     * @return 支払結果
     */
    @Override
    @Transactional
    public SbPayLaterOutputDto sbPayLater(SbPayLaterInputDto inputDto) {

        try {

            // 該当サマリIDのデータを取得する
            OOrderSummary orderSummary = orderSummaryRepository
                .findByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
                    inputDto.getReceivablesId(), Flag.OFF.getCode());

            // 検索0件の場合
            if (orderSummary == null) {
                // 注文情報取得異常
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.049", (Object) null));
            }

            // 注文ID
            Integer orderId = 0;

            // 支払方式
            String paymentType = orderSummary.getPaymentType();

            // 先払の場合
            if (paymentType.equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                // 注文ID取得
                orderId = orderRepository
                    .getUnPayOrderId(inputDto.getStoreId(), orderSummary.getOrderSummaryId(),
                        PayStatus.PAY_NOT_ALREADY.getCode());

                // 值引チェック
                if (inputDto.getPriceDiscountAmount() != null
                    || inputDto.getPriceDiscountRate() != null) {
                    throw new BusinessException("2001",
                        ResultMessages.error().add("e.qr.ph.048", (Object) null));
                }
            }

            // 注文番号を取得する
            String orderSummaryNo = sbResultDetailRepository
                .getOrderSummaryNo(inputDto.getStoreId(), orderSummary.getOrderSummaryId(),
                    orderId);

            // 注文番号編集
            String maxId = "01";
            if (StringUtils.isNotEmpty(orderSummaryNo)) {

                // 次の注文番号
                int nextId =
                    Integer.parseInt(orderSummaryNo.substring(orderSummaryNo.length() - 2)) + 1;

                // 最大超えた場合
                if (99 < nextId) {
                    throw new BusinessException("2001",
                        ResultMessages.error().add("e.qr.ph.048", (Object) null));
                }

                maxId = String.format("%02d", nextId);
            }

            // 支払テーブル情報取得
            PPaymentCompany sbCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.SB.getCode(),
                    CompanyMethod.CPM_PAY.getCode());

            // QR端末処理通番
            String orderNo = orderSummary.getOrderSummaryId() + orderId + maxId;

            // 注文番号
            if (orderId == 0) {
                orderNo = orderSummary.getOrderSummaryId() + maxId;
            }

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 現在日付
            ZonedDateTime nowTime = DateUtil.getNowDateTime();

            // 結果DTO初期化
            SbPayLaterOutputDto outDto = new SbPayLaterOutputDto();

            // 決済要求
            // リクエストのパラメーターを設定する
            JSONObject payData = new JSONObject();
            payData.put("clientOrderNo", orderNo);
            payData.put("orderPrice", inputDto.getPayAmount());
            payData.put("orderSubject", "Purchased Item");
            payData.put("payType", PayType.CPM.getCode());
            payData.put("brandType", AccountsType.ANY.getCode());
            payData.put("authCode", inputDto.getPayCodeId());

            // サイン値生成
            String signature = Md5Util.toMd5(
                "authCode=" + inputDto.getPayCodeId() + "&brandType=" + AccountsType.ANY.getCode()
                    + "&clientOrderNo=" + orderNo + "&orderPrice=" + inputDto.getPayAmount()
                    + "&orderSubject=Purchased Item" + "&payType=" + PayType.CPM.getCode() + "&key="
                    + sbCompany.getKey()).toUpperCase();

            // 決済結果編集
            SpsGateWayResultDto payResultData = null;
            // 支払結果
            String respCode = PayResult.FAILED.getCode();
            // エラーコード
            String resErrCode = StringUtils.EMPTY;

            try {

                // 決済結果
                log.info("SB後払 API 決済開始: payData={} url={}", payData, sbCompany.getUrl());
                String payResult = sbPsQrGateWayRequest(payData, sbCompany.getUrl(),
                    sbCompany.getTermId(),
                    signature);
                log.info("SB後払 API 決済結果: payResult={}", payResult);
                // 決済結果編集
                payResultData = JSONObject.parseObject(payResult, SpsGateWayResultDto.class);

                // 正常の場合
                if (Objects.equals("0000", payResultData.getResultCode())) {
                    // エンドユーザー支払い中の場合
                    if (Objects.equals(SbOrderStatus.PAYING.getCode(),
                        payResultData.getResult().getOrderStatus())) {
                        // 決済機関にステータス参照10秒おき
                        payResultData = retryGetOrderInfo(orderNo, inputDto.getStoreId());
                    }

                    // 決済成功
                    if (Objects.equals("1", payResultData.getResult().getOrderStatus())) {
                        respCode = PayResult.SUCCESS.getCode();
                        // 決済結果登録
                        RegistPaymentInputDto registerDto = new RegistPaymentInputDto();
                        registerDto.setStoreId(inputDto.getStoreId());
                        registerDto.setReceivablesId(inputDto.getReceivablesId());
                        registerDto.setPaymentMethodCode(String.format("%02d",
                            Integer.parseInt(payResultData.getResult().getBrandType()) + 20));
                        registerDto.setPaymentAmount(inputDto.getPayAmount());
                        registerDto.setTakeoutFlag(inputDto.getTakeoutFlag());
                        registerDto.setPriceDiscountAmount(inputDto.getPriceDiscountAmount());
                        registerDto.setPriceDiscountRate(inputDto.getPriceDiscountRate());
                        registPayment(registerDto);
                    }
                } else {
                    // エラーコード
                    resErrCode = payResultData.getResultCode();
                }

                // 支払結果設定
                outDto.setRespCode(respCode);
                // メッセージ
                outDto.setMessage(payResultData.getResultMessage());

                // 決済タイムアウト
            } catch (ResourceAccessException | BusinessException ignored) {
                // 決済取消
                log.info("SB後払 API 決済取消開始: orderNo={} storeId={}", orderNo, inputDto.getStoreId());
                String cancelResult = sbPsOrderCancel(orderNo, inputDto.getStoreId());
                log.info("SB後払 API 決済取消結果: cancelResult={}", cancelResult);
                // 決済取消結果編集
                payResultData = JSONObject.parseObject(cancelResult, SpsGateWayResultDto.class);
                respCode = "09";
                // 支払結果設定
                outDto.setRespCode(respCode);
                outDto.setCancelRespCode(payResultData.getResultCode());
                outDto.setMessage(payResultData.getResultMessage());
            }

            // 支払い方法
            String paymentMethodCode = AccountsType.ANY.getCode();
            // 処理トラッキングID
            String resTrackingId = StringUtils.EMPTY;
            // 顧客決済情報
            String resPayinfoKey = StringUtils.EMPTY;

            // 決済要求結果
            if (payResultData.getResult() != null) {
                // 支払い方法再編集
                if (StringUtils.isNotEmpty(payResultData.getResult().getBrandType())) {
                    if (!Objects.equals("0000", payResultData.getResultCode())) {
                        paymentMethodCode = String.format("%02d",
                            Integer.parseInt(payResultData.getResult().getBrandType()));
                    } else {
                        paymentMethodCode = String.format("%02d",
                            Integer.parseInt(payResultData.getResult().getBrandType()) + 20);
                    }
                }
                resTrackingId = payResultData.getResult().getGwOrderNo();
                resPayinfoKey = payResultData.getResult().getBrandOrderNo();
            }

            // 支払結果データ作成
            insetQrResultData(inputDto.getStoreId(), orderSummary.getOrderSummaryId(), orderId,
                inputDto.getPayAmount(), paymentMethodCode, respCode, orderNo, resTrackingId,
                resPayinfoKey, userOperCd, nowTime, resErrCode);
            // 注文サマリID
            outDto.setOrderSummaryId(orderSummary.getOrderSummaryId());
            return outDto;
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.048", (Object) null), ex);
        }
    }

    /**
     * SBペイメント後払返金.
     *
     * @param inputDto 支払情報
     */
    @Override
    @Transactional
    public void sbCpmRefunds(RefundsInputDto inputDto) {

        try {

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNo().substring(0, 20);

            // 注文ID
            String orderId = inputDto.getOrderNo()
                .substring(20, inputDto.getOrderNo().length() - 2);

            // 注文サマリーデータ取得
            OOrderSummary order = orderSummaryRepository
                .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                    Flag.OFF.getCode());

            // 先払の場合未支払データ存在チェック
            if (order.getPaymentType().equals(PaymentType.ADVANCE_PAYMENT.getCode())) {

                // 未支払データを取得する
                Integer count = orderRepository
                    .getUnPayOrderCount(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode());

                // データが存在の場合
                if (count != 0) {
                    throw new BusinessException("2001",
                        ResultMessages.error().add("e.qr.ph.046", (Object) null));
                }
            }

            // 支払情報取得
            PSbResultDetail payData = sbResultDetailRepository
                .getPaymentInfo(inputDto.getStoreId(),
                    inputDto.getOrderNo(), PayResult.SUCCESS.getCode());

            // データが存在しない場合
            if (payData == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.047", (Object) null));
            }

            // 返金要求
            log.info(
                "SB後払 API 返金要求開始: orderNo={} storeId={} resTrackingId={} resPayinfoKey={} payPrice={}",
                inputDto.getOrderNo(), inputDto.getStoreId(), payData.getResTrackingId(),
                payData.getResPayinfoKey(), payData.getPayPrice());
            String orderRefund = sbPsOrderRefund(inputDto.getOrderNo(), inputDto.getStoreId(),
                payData.getResTrackingId(), payData.getResPayinfoKey(), payData.getPayPrice());
            log.info("SB後払 API 返金要求結果: orderRefund={}", orderRefund);
            // 決済結果編集
            SpsGateWayRefundResultDto orderRefundData = JSONObject
                .parseObject(orderRefund, SpsGateWayRefundResultDto.class);

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 処理結果ステータス
            String respCode = PayResult.FAILED.getCode();
            if (Objects.equals("0000", orderRefundData.getResultCode())) {
                // 返金中 銀聯は即時返金不可、返金受付した状態、「1.返金成功」になるまで返金履歴検索をしてください
                if (Objects.equals("2", orderRefundData.getResult().getRefundStatus())) {
                    // 決済機関にステータス参照10秒おき
                    orderRefundData = retryGetRefundInfo(inputDto.getOrderNo(),
                        inputDto.getStoreId());
                }

                // 返金成功
                if (Objects.equals("1", orderRefundData.getResult().getRefundStatus())) {
                    respCode = PayResult.SUCCESS.getCode();

                    // 更新支付表支付金额
                    Integer paymentId = paymentRepository
                        .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                            payData.getPayPrice(), userOperCd, dateTime);

                    // 支払い明細IDのシーケンスNo取得
                    GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                    getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
                    getSeqNoInputDto.setItem("payment_detail_id"); // 項目
                    getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                    GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                    // 支払い方式コード
                    String paymentMethodCode = payData.getPaymentMethodCode();

                    PPaymentDetail detailInputDao = new PPaymentDetail();
                    // 店舗ID
                    detailInputDao.setStoreId(inputDto.getStoreId());
                    // 支払い明細
                    detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
                    // 支払いID
                    detailInputDao.setPaymentId(paymentId);
                    // 注文サマリID
                    detailInputDao.setOrderSummaryId(orderSummaryId);
                    // 注文ID
                    if (StringUtils.isNotEmpty(orderId)) {
                        detailInputDao.setOrderId(Integer.valueOf(orderId));
                    } else {
                        detailInputDao.setOrderId(0);
                    }
                    // 支払い方式
                    detailInputDao.setPaymentMethodCode(paymentMethodCode);
                    // 支払い金額
                    detailInputDao
                        .setPaymentAmount(payData.getPayPrice().multiply(new BigDecimal(-1)));
                    // 削除フラグ
                    detailInputDao.setDelFlag(Flag.OFF.getCode());
                    // 登録者
                    detailInputDao.setInsOperCd(userOperCd);
                    // 登録日時
                    detailInputDao.setInsDateTime(dateTime);
                    // 更新者
                    detailInputDao.setUpdOperCd(userOperCd);
                    // 更新日時
                    detailInputDao.setUpdDateTime(dateTime);
                    // バージョン
                    detailInputDao.setVersion(0);
                    // 支払明細テーブル
                    paymentDetailRepository.save(detailInputDao);

                    // 注文IDが存在の場合
                    if (StringUtils.isNotEmpty(orderId)) {

                        // 支払状態を更新する
                        orderRepository.updateStatus(inputDto.getStoreId(), orderSummaryId,
                            Integer.valueOf(orderId),
                            CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd,
                            dateTime);
                    } else {

                        // 支払状態を更新する
                        orderRepository.updatePaymentStatus(inputDto.getStoreId(), orderSummaryId,
                            PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                    }

                    // 支払情報を更新する
                    orderSummaryRepository
                        .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                            payData.getPayPrice().multiply(new BigDecimal(-1)), userOperCd,
                            dateTime);

                    // 支付金额が0の場合、值引クリア
                    orderSummaryRepository.clearDiscount(inputDto.getStoreId(), orderSummaryId,
                        userOperCd, dateTime);

                    if (null != order.getTableId()) {
                        orderSummaryRepository.updateSeatReleaseByReceivablesId(
                            inputDto.getStoreId(), order.getReceivablesId(),
                            Flag.OFF.getCode().toString(),
                            order.getTableId(), userOperCd, dateTime);
                    }
                }
            } else {
                throw new BusinessException("2001", orderRefundData.getResultMessage());
            }

            // 処理トラッキングID
            String resTrackingId = StringUtils.EMPTY;

            // 決済要求結果
            if (orderRefundData.getResult() != null) {
                resTrackingId = orderRefundData.getResult().getGwOrderNo();
            }

            // 返金結果更新
            sbResultDetailRepository.updateRefunds(inputDto.getStoreId(), inputDto.getOrderNo(),
                PayResult.SUCCESS.getCode(), resTrackingId,
                DateUtil.getZonedDateString(dateTime, CommonConstants.DATE_FORMAT_DATE_TIMES),
                respCode, orderRefundData.getResultCode(), userOperCd, dateTime);

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.045", (Object) null), ex);
        }
    }

    /**
     * 利用Apache的工具类实现SHA-256加密.
     *
     * @param str 加密后的报文
     */
    private String getSha256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    private String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte byteData : bytes) {
            temp = Integer.toHexString(byteData & 0xFF);
            if (temp.length() == 1) {
                //1ｵﾃｵｽﾒｻﾎｻｵﾄｽﾐｲｹ0ｲﾙﾗ・
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 接口调用.
     *
     * @param urlStr   接口URL
     * @param data     接口参数
     * @param sign     签名
     * @param dateTime 当前时间
     */
    private String postDataSign(String urlStr, String data, String sign, String dateTime) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Authorization", sign);
            conn.setRequestProperty("DateTime", dateTime);
            conn.setRequestProperty("SignType", "SHA256");
            conn.setRequestProperty("Method", "POST");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            if (data == null) {
                data = "";
            }
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                //
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SB支払結果明細データ作成.
     *
     * @param storeId           店舗ID
     * @param orderSummaryId    注文サマリID
     * @param orderId           注文ID
     * @param payAmount         支払い金額
     * @param paymentMethodCode 支払い方法
     * @param maxId             最大ID
     * @return 最大明細ID
     */
    public Integer insetSbResultDetail(String storeId, String orderSummaryId, Integer orderId,
        BigDecimal payAmount, String paymentMethodCode, String maxId) {

        try {
            // SB支払結果明細IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_sb_result_detail"); // テーブル名
            getSeqNoInputDto.setItem("sb_result_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // SB支払履歴データ
            PSbResultDetail resultInputDao = new PSbResultDetail();
            // 店舗ID
            resultInputDao.setStoreId(storeId);
            // SB支払結果明細ID
            resultInputDao.setSbResultDetailId(getSeqNo.getSeqNo());
            // 注文サマリID
            resultInputDao.setOrderSummaryId(orderSummaryId);
            // 注文ID
            resultInputDao.setOrderId(orderId);
            // 注文番号
            resultInputDao.setOrderNo(orderSummaryId + orderId + maxId);
            // 支払い方式
            resultInputDao.setPaymentMethodCode(paymentMethodCode);
            // 支付金额
            resultInputDao.setPayPrice(payAmount);
            // 支付结果code
            resultInputDao.setRespCode(PayResult.DEFAULT.getCode());
            // 削除フラグ
            resultInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            resultInputDao.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            resultInputDao.setInsDateTime(dateTime);
            // 更新者
            resultInputDao.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            resultInputDao.setUpdDateTime(dateTime);
            // バージョン
            resultInputDao.setVersion(0);

            // SB支払結果明細テーブル
            sbResultDetailRepository.save(resultInputDao);

            return getSeqNo.getSeqNo();
        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.036", (Object) null), ex);
        }
    }

    /**
     * SB支払結果明細データ更新.
     *
     * @param inputDto          支払結果データ
     * @param storeId           店舗ID
     * @param paymentMethodCode 支払方法
     * @param sbResultDetailId  SB支払結果明細ID
     */
    private void updateResultDetail(SbPaymentCallBackInputDto inputDto, String storeId,
        String paymentMethodCode, String sbResultDetailId) {

        try {

            // 処理結果ステータス
            String respCode = PayResult.SUCCESS.getCode();
            if (!Objects.equals(ResResult.OK.getCode(), inputDto.getResResult())) {
                respCode = PayResult.FAILED.getCode();
            }

            // SB支払結果明細データ更新
            sbResultDetailRepository
                .updateResultDetail(storeId, inputDto.getOrderId(),
                    Integer.valueOf(sbResultDetailId),
                    paymentMethodCode, respCode, inputDto.getResTrackingId(),
                    inputDto.getResPayinfoKey(), inputDto.getResPaymentDate(),
                    inputDto.getResErrCode(), CommonConstants.OPER_CD_MOBILE,
                    DateUtil.getNowDateTime());

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.037", (Object) null), ex);
        }
    }

    /**
     * 支払情報を作成する.
     *
     * @param inputDto          支払結果データ
     * @param storeId           店舗ID
     * @param paymentMethodCode 支払方法
     */
    private void insertPaymentData(SbPaymentCallBackInputDto inputDto, String storeId,
        String paymentMethodCode) {

        try {

            // 支払いIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment"); // テーブル名
            getSeqNoInputDto.setItem("payment_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPayment inputDao = new PPayment();
            // 店舗ID
            inputDao.setStoreId(storeId);
            // 支払いID
            inputDao.setPaymentId(getSeqNo.getSeqNo());
            // 注文サマリID
            inputDao.setOrderSummaryId(inputDto.getOrderId().substring(0, 20));
            // 支払い金額
            inputDao.setPaymentAmount(inputDto.getAmount());
            // 削除フラグ
            inputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            inputDao.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            inputDao.setInsDateTime(dateTime);
            // 更新者
            inputDao.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            inputDao.setUpdDateTime(dateTime);
            // バージョン
            inputDao.setVersion(0);

            // 支払テーブル
            Integer paymentId = paymentRepository
                .insertOrUpdate(inputDao.getStoreId(), inputDao.getPaymentId(),
                    inputDao.getOrderSummaryId(), inputDao.getPaymentAmount(),
                    inputDao.getDelFlag(), inputDao.getInsOperCd(), inputDao.getInsDateTime(),
                    inputDao.getUpdOperCd(), inputDao.getUpdDateTime(), inputDao.getVersion());

            // 支払い明細IDのシーケンスNo取得
            getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
            getSeqNoInputDto.setItem("payment_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPaymentDetail detailInputDao = new PPaymentDetail();
            // 店舗ID
            detailInputDao.setStoreId(storeId);
            // 支払い明細
            detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
            // 支払いID
            detailInputDao.setPaymentId(paymentId);
            // 注文サマリID
            detailInputDao.setOrderSummaryId(inputDto.getOrderId().substring(0, 20));
            // 注文ID
            detailInputDao.setOrderId(Integer
                .valueOf(
                    inputDto.getOrderId().substring(20, inputDto.getOrderId().length() - 2)));
            // 支払い方式
            detailInputDao.setPaymentMethodCode(paymentMethodCode);
            // 支払い金額
            detailInputDao.setPaymentAmount(inputDto.getAmount());
            // 削除フラグ
            detailInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            detailInputDao.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 登録日時
            detailInputDao.setInsDateTime(dateTime);
            // 更新者
            detailInputDao.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            detailInputDao.setUpdDateTime(dateTime);
            // バージョン
            detailInputDao.setVersion(0);
            // 支払明細テーブル
            paymentDetailRepository.save(detailInputDao);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.038", (Object) null), ex);
        }
    }

    /**
     * 注文状態変更.
     *
     * @param storeId       店舗ID
     * @param orderNum      購入ID
     * @param paymentAmount 支払い金額
     */
    private Integer changeOrderStatus(String storeId, String orderNum, BigDecimal paymentAmount) {

        try {

            // 注文サマリID
            String orderSummaryId = orderNum.substring(0, 20);

            // 注文ID
            Integer orderId = Integer.valueOf(orderNum.substring(20, orderNum.length() - 2));

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 支払い金額を変更する
            Integer updateCount = orderSummaryRepository
                .updatePaymentAmountAndDelFlag(storeId, orderSummaryId, paymentAmount,
                    CommonConstants.OPER_CD_MOBILE, dateTime);

            // 更新０件の場合
            if (updateCount == 0) {
                orderSummaryRepository
                    .updatePaymentAmountWithDelFlag(storeId, orderSummaryId, paymentAmount,
                        orderId,
                        CommonConstants.OPER_CD_MOBILE, dateTime);
            }

            // 注文状態を変更する
            orderRepository.updateStatusWithDelFlag(storeId, orderSummaryId, orderId,
                CodeConstants.PayStatus.PAY_ALREADY.getCode(), CommonConstants.OPER_CD_MOBILE,
                dateTime);

            // 注文明細状態更新
            orderDetailsRepository.updateDelFlagByOrderId(storeId, orderId,
                CommonConstants.OPER_CD_MOBILE, dateTime);

            // 注文明細オプション状態更新
            orderDetailsOptionRepository.updateDelFlagByOrderId(storeId, orderId,
                CommonConstants.OPER_CD_MOBILE, dateTime);

            // 放题orderId取得
            Integer buffetOrderId = orderSummaryRepository.getBuffetOrderId(storeId, orderSummaryId,
                MstItemType.BUFFET.getCode(), CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode());

            if (null != buffetOrderId) {

                // 注文状態を変更する
                orderRepository.updateStatusWithDelFlag(storeId, orderSummaryId, buffetOrderId,
                    CodeConstants.PayStatus.PAY_ALREADY.getCode(), "TRIO", dateTime);
            }

            return buffetOrderId;

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.039", (Object) null), ex);
        }
    }

    /**
     * 注文情報を回復する.
     *
     * @param storeId     店舗ID
     * @param orderNum    購入ID
     * @param orderAmount 支払い金額
     */
    private void revertOrderInfo(String storeId, String orderNum, BigDecimal orderAmount) {

        try {

            // 注文サマリID
            String orderSummaryId = orderNum.substring(0, 20);

            // 注文ID
            Integer orderId = Integer.valueOf(orderNum.substring(20, orderNum.length() - 2));

            // 注文サマリを回復する
            orderSummaryRepository.deleteByStoreIdAndOrderSummaryIdAndDelFlag(storeId,
                orderSummaryId, Flag.ON.getCode());

            // 注文を回復する
            orderRepository
                .deleteByStoreIdAndOrderSummaryIdAndOrderId(storeId, orderSummaryId, orderId);

            // 注文DetailIDを取得する
            List<Integer> idList = orderDetailsRepository.getDetailIdList(storeId, orderId);

            // 注文商品を回復する
            orderDetailsRepository.deleteByStoreIdAndOrderId(storeId, orderId);

            // 注文商品オプションを回復する
            orderDetailsOptionRepository.deleteOrderItemOptions(storeId, idList);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.040", (Object) null), ex);
        }
    }

    /**
     * 決済機関にステータス参照(10秒おき).
     *
     * @param orderNo QR端末処理通番
     * @param storeId 決済会社情報
     * @return 処理結果
     */
    private SpsGateWayResultDto retryGetOrderInfo(String orderNo, String storeId) {

        // リトライ
        RetryTemplate retryTemplate = new RetryTemplate();

        // 再試行間隔
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1000L);

        // 再試行できる例外
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(BusinessException.class, true);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5, retryableExceptions);
        retryTemplate.setRetryPolicy(retryPolicy);

        // 決済機関にステータス参照
        return retryTemplate
            .execute((RetryCallback<SpsGateWayResultDto, BusinessException>) context -> {
                // 決済履歴検索結果
                log.info("SB後払 API 決済履歴検索開始: orderNo={}", orderNo);
                String orderInfo = sbPsOrderInfo(orderNo, storeId);
                log.info("SB後払 API 決済履歴結果: orderInfo={}", orderInfo);
                // 決済結果編集
                SpsGateWayResultDto orderInfoData = JSONObject
                    .parseObject(orderInfo, SpsGateWayResultDto.class);

                // 正常以外の場合
                if (!Objects.equals("0000", orderInfoData.getResultCode())) {
                    throw new BusinessException("2005",
                        ResultMessages.error().add("e.qr.ph.062", (Object) null));
                }

                // エンドユーザー支払い中以外の場合
                if (orderInfoData.getResult() == null || Objects
                    .equals(SbOrderStatus.PAYING.getCode(),
                        orderInfoData.getResult().getOrderStatus())) {
                    throw new BusinessException("2005",
                        ResultMessages.error().add("e.qr.ph.062", (Object) null));
                }

                return orderInfoData;
            });
    }

    /**
     * 銀聯は即時返金不可、返金受付した状態、「1.返金成功」になるまで返金履歴検索(10秒おき).
     *
     * @param clientRefundNo QR端末返金処理通番
     * @param storeId        決済会社情報
     * @return 処理結果
     */
    private SpsGateWayRefundResultDto retryGetRefundInfo(String clientRefundNo, String storeId) {

        // リトライ
        RetryTemplate retryTemplate = new RetryTemplate();

        // 再試行間隔
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(1000L);

        // 再試行できる例外
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(BusinessException.class, true);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(10, retryableExceptions);
        retryTemplate.setRetryPolicy(retryPolicy);

        // 決済機関にステータス参照
        return retryTemplate
            .execute((RetryCallback<SpsGateWayRefundResultDto, BusinessException>) context -> {
                // 決済履歴検索結果
                log.info("SB後払 API 決済履歴検索開始: clientRefundNo={}", clientRefundNo);
                String orderInfo = sbPsRefundInfo(clientRefundNo, storeId);
                log.info("SB後払 API 決済履歴結果: orderInfo={}", orderInfo);
                // 決済結果編集
                SpsGateWayRefundResultDto refundInfoData = JSONObject
                    .parseObject(orderInfo, SpsGateWayRefundResultDto.class);

                // 正常以外の場合
                if (!Objects.equals("0000", refundInfoData.getResultCode())) {
                    throw new BusinessException("2005",
                        ResultMessages.error().add("e.qr.ph.062", (Object) null));
                }

                // 返金成功以外の場合
                if (refundInfoData.getResult() == null || !Objects
                    .equals("1", refundInfoData.getResult().getRefundStatus())) {
                    throw new BusinessException("2005",
                        ResultMessages.error().add("e.qr.ph.062", (Object) null));
                }

                return refundInfoData;
            });
    }

    /**
     * 返金履歴検索.
     *
     * @param clientRefundNo QR端末返金処理通番
     * @param storeId        決済会社情報
     * @return 処理結果
     */
    private String sbPsRefundInfo(String clientRefundNo, String storeId) {

        // 支払テーブル情報取得
        PPaymentCompany sbCompany = paymentCompanyRepository
            .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                storeId, Flag.OFF.getCode(), Company.SB.getCode(),
                CompanyMethod.CPM_REFUND_INFO.getCode());

        // リクエストのパラメーターを設定する
        JSONObject orderCancelData = new JSONObject();
        orderCancelData.put("clientRefundNo", clientRefundNo);

        // サイン値生成
        String signature = Md5Util
            .toMd5("clientRefundNo=" + clientRefundNo + "&key=" + sbCompany.getKey()).toUpperCase();

        // 決済結果
        return sbPsQrGateWayRequest(orderCancelData, sbCompany.getUrl(), sbCompany.getTermId(),
            signature);
    }

    /**
     * 決済履歴検索.
     *
     * @param orderNo QR端末処理通番
     * @param storeId 決済会社情報
     * @return 処理結果
     */
    private String sbPsOrderInfo(String orderNo, String storeId) {

        // 支払テーブル情報取得
        PPaymentCompany sbCompany = paymentCompanyRepository
            .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                storeId, Flag.OFF.getCode(), Company.SB.getCode(),
                CompanyMethod.CPM_PAY_INFO.getCode());

        // リクエストのパラメーターを設定する
        JSONObject orderCancelData = new JSONObject();
        orderCancelData.put("clientOrderNo", orderNo);

        // サイン値生成
        String signature = Md5Util.toMd5("clientOrderNo=" + orderNo + "&key=" + sbCompany.getKey())
            .toUpperCase();

        // 決済結果
        return sbPsQrGateWayRequest(orderCancelData, sbCompany.getUrl(), sbCompany.getTermId(),
            signature);
    }

    /**
     * 決済取消.
     *
     * @param orderNo QR端末処理通番
     * @param storeId 店舗ID
     * @return 処理結果
     */
    private String sbPsOrderCancel(String orderNo, String storeId) {

        // 支払テーブル情報取得
        PPaymentCompany sbCompany = paymentCompanyRepository
            .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                storeId, Flag.OFF.getCode(), Company.SB.getCode(),
                CompanyMethod.CPM_PAY_CANCEL.getCode());

        // リクエストのパラメーターを設定する
        JSONObject orderCancelData = new JSONObject();
        orderCancelData.put("clientOrderNo", orderNo);

        // サイン値生成
        String signature = Md5Util.toMd5("clientOrderNo=" + orderNo + "&key=" + sbCompany.getKey())
            .toUpperCase();

        // 決済結果
        return sbPsQrGateWayRequest(orderCancelData, sbCompany.getUrl(), sbCompany.getTermId(),
            signature);
    }

    /**
     * 返金要求.
     *
     * @param orderNo QR端末処理通番
     * @param storeId 店舗ID
     * @return 処理結果
     */
    private String sbPsOrderRefund(String orderNo, String storeId, String gwOrderNo,
        String clientRefundNo, BigDecimal refundPrice) {

        // 支払テーブル情報取得
        PPaymentCompany sbCompany = paymentCompanyRepository
            .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                storeId, Flag.OFF.getCode(), Company.SB.getCode(),
                CompanyMethod.CPM_REFUND.getCode());

        // リクエストのパラメーターを設定する
        JSONObject orderRefundData = new JSONObject();
        orderRefundData.put("clientOrderNo", orderNo);
        orderRefundData.put("gwOrderNo", gwOrderNo);
        orderRefundData.put("clientRefundNo", clientRefundNo);
        orderRefundData.put("refundPrice", refundPrice);

        // サイン値生成
        String signature = Md5Util.toMd5(
            "clientOrderNo=" + orderNo + "&clientRefundNo=" + clientRefundNo + "&gwOrderNo="
                + gwOrderNo + "&refundPrice=" + refundPrice + "&key=" + sbCompany.getKey())
            .toUpperCase();

        // 決済結果
        return sbPsQrGateWayRequest(orderRefundData, sbCompany.getUrl(), sbCompany.getTermId(),
            signature);
    }

    /**
     * 返金要求.
     *
     * @param requestDto 返金情報
     * @param url        APIのUrl
     * @param username   Basic認証ID
     * @param password   Basic認証Password
     * @return 処理結果
     */
    private SpsApiResponseDto spsPayRequest(SpsApiRequestDto requestDto, String url,
        String username, String password) {

        // HttpHeadersを使用してリクエストヘッダーを設定する
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> param = new HashMap<>();
        param.put("charset", "shift_jis");
        headers.setContentType(new MediaType(MediaType.APPLICATION_XML, param));
        headers.setBasicAuth(username, password);

        // HttpEntityを設定する
        HttpEntity<Object> request = new HttpEntity<>(requestDto, headers);

        // SB返金要求APIを呼び出し
        ResponseEntity<SpsApiResponseDto> result = restTemplate
            .exchange(url, HttpMethod.POST, request, SpsApiResponseDto.class);

        return result.getBody();
    }

    /**
     * SB PSのQR GateWay接続要求.
     *
     * @param data        要求情報
     * @param url         APIのUrl
     * @param accessToken 認証Token
     * @param signature   サイン
     * @return 処理結果
     */
    private String sbPsQrGateWayRequest(JSONObject data, String url, String accessToken,
        String signature) {

        // HttpHeadersを使用してリクエストヘッダーを設定する
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-QRGW-AccessToken", accessToken);
        headers.add("X-QRGW-Signature", signature);

        // HttpEntityを設定する
        HttpEntity<Object> request = new HttpEntity<>(data, headers);

        // QR GateWay接続要求APIを呼び出し
        ResponseEntity<String> result = restTemplate
            .exchange(url, HttpMethod.POST, request, String.class);

        return result.getBody();
    }

    /**
     * SB QR支払結果明細データ作成.
     *
     * @param storeId           店舗ID
     * @param orderSummaryId    注文サマリID
     * @param orderId           注文ID
     * @param payAmount         支払い金額
     * @param paymentMethodCode 支払い方法
     * @param respCode          支付结果
     */
    public void insetQrResultData(String storeId, String orderSummaryId, Integer orderId,
        BigDecimal payAmount, String paymentMethodCode, String respCode, String orderNo,
        String resTrackingId, String resPayinfoKey, String userOperCd, ZonedDateTime nowTime,
        String resErrCode) {

        try {
            // SB支払結果明細IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_sb_result_detail"); // テーブル名
            getSeqNoInputDto.setItem("sb_result_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // SB支払履歴データ
            PSbResultDetail resultInputDao = new PSbResultDetail();
            // 店舗ID
            resultInputDao.setStoreId(storeId);
            // SB支払結果明細ID
            resultInputDao.setSbResultDetailId(getSeqNo.getSeqNo());
            // 注文サマリID
            resultInputDao.setOrderSummaryId(orderSummaryId);
            // 注文ID
            resultInputDao.setOrderId(orderId);
            // 注文番号
            resultInputDao.setOrderNo(orderNo);
            // 支払い方式
            resultInputDao.setPaymentMethodCode(paymentMethodCode);
            // 支付金额
            resultInputDao.setPayPrice(payAmount);
            // 支付结果code
            resultInputDao.setRespCode(respCode);
            // 処理トラッキングID
            resultInputDao.setResTrackingId(resTrackingId);
            // 顧客決済情報
            resultInputDao.setResPayinfoKey(resPayinfoKey);
            // 完了処理日時
            resultInputDao.setResPaymentDate(DateUtil
                .getZonedDateString(nowTime, CommonConstants.DATE_FORMAT_DATE_TIMES));
            // エラーコード
            resultInputDao.setResErrCode(resErrCode);
            // 削除フラグ
            resultInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            resultInputDao.setInsOperCd(userOperCd);
            // 登録日時
            resultInputDao.setInsDateTime(nowTime);
            // 更新者
            resultInputDao.setUpdOperCd(userOperCd);
            // 更新日時
            resultInputDao.setUpdDateTime(nowTime);
            // バージョン
            resultInputDao.setVersion(0);

            // SB支払結果明細テーブル
            sbResultDetailRepository.save(resultInputDao);
        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.036", (Object) null), ex);
        }
    }

    /**
     * SBペイメントコールバック.
     *
     * @param inputDto 支払データ
     */
    @Override
    @Transactional
    public void sbDeliveryPaymentCallBack(SbPaymentCallBackInputDto inputDto) {

        // 注文情報
        String[] orderInfo = inputDto.getFree1().split(",");
        // 店舗ID
        String storeId = orderInfo[0];

        // 指定注文サマリーIDデータロック
        ODeliveryOrderSummary orderSummaryData = deliveryOrderSummaryRepository
            .findByStoreIdAndOrderSummaryId(storeId, inputDto.getOrderId().substring(0, 20));

        // 注文データ存在しない場合
        if (orderSummaryData == null) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.040", (Object) null));
        }

        // 支払状態更新
        updateResultDetail(inputDto, storeId, orderInfo[2], orderInfo[1]);

        // 処理結果ステータス OK以外の場合
        if (Objects.equals(ResResult.OK.getCode(), inputDto.getResResult())) {
            // 支払い情報を作成する
            insertPaymentData(inputDto, storeId, orderInfo[2]);
            // 注文状態変更
            changeDeliveryOrderStatus(storeId, inputDto.getOrderId(), inputDto.getAmount());

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderId().substring(0, 20);

            // 受付ID取得
            ODeliveryOrderSummary deliveryOrderSummary = deliveryOrderSummaryRepository.
                findByStoreIdAndOrderSummaryId(storeId, orderSummaryId);

            // メールタイトル
            String subject = "受付ID送信";
            // メール内容
            String content = deliveryOrderSummary.getCustomerName()
                + "様<BR><BR>ご注文した受付IDは" + deliveryOrderSummary.getReceivablesId()
                + "です。<BR>ご利用ください。";

            // メール再送信サービス処理を実行する
            mailService
                .sendEmail(deliveryOrderSummary.getMailAddress(), subject, content, false, true);
        } else {
            // 注文情報を回復する
            revertDeliveryOrderInfo(storeId, inputDto.getOrderId(), inputDto.getAmount());
        }
    }

    /**
     * 注文状態変更.
     *
     * @param storeId       店舗ID
     * @param orderNum      購入ID
     * @param paymentAmount 支払い金額
     */
    private void changeDeliveryOrderStatus(String storeId, String orderNum,
        BigDecimal paymentAmount) {

        try {

            // 注文サマリID
            String orderSummaryId = orderNum.substring(0, 20);

            // 注文ID
            Integer orderId = Integer.valueOf(orderNum.substring(20, orderNum.length() - 2));

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 受付ID取得
            ODeliveryOrderSummary deliveryOrderSummary = deliveryOrderSummaryRepository
                .findByStoreIdAndOrderSummaryId(
                    storeId, orderSummaryId);

            // 受付状態変更
            receivablesRepository
                .updateDelFlagByReceivablesId(storeId, deliveryOrderSummary.getReceivablesId());

            // 支払い金額を変更する
            Integer updateCount = deliveryOrderSummaryRepository
                .updatePaymentAmountWithDelFlag(storeId, orderSummaryId,
                    CommonConstants.OPER_CD_MOBILE, dateTime);

            // 更新０件の場合
            if (updateCount == 0) {
                deliveryOrderSummaryRepository
                    .updatePaymentAmountWithDelFlag(storeId, orderSummaryId, paymentAmount,
                        orderId,
                        CommonConstants.OPER_CD_MOBILE, dateTime);
            }

            // 注文状態を変更する
            orderRepository.updateStatusWithDelFlag(storeId, orderSummaryId, orderId,
                CodeConstants.PayStatus.PAY_ALREADY.getCode(), CommonConstants.OPER_CD_MOBILE,
                dateTime);

            // 注文明細状態更新
            orderDetailsRepository.updateDelFlagByOrderId(storeId, orderId,
                CommonConstants.OPER_CD_MOBILE, dateTime);

            // 注文明細オプション状態更新
            orderDetailsOptionRepository.updateDelFlagByOrderId(storeId, orderId,
                CommonConstants.OPER_CD_MOBILE, dateTime);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.039", (Object) null), ex);
        }
    }

    /**
     * 注文情報を回復する.
     *
     * @param storeId     店舗ID
     * @param orderNum    購入ID
     * @param orderAmount 支払い金額
     */
    private void revertDeliveryOrderInfo(String storeId, String orderNum, BigDecimal orderAmount) {

        try {

            // 注文サマリID
            String orderSummaryId = orderNum.substring(0, 20);

            // 注文ID
            Integer orderId = Integer.valueOf(orderNum.substring(20, orderNum.length() - 2));

            // 受付ID取得
            ODeliveryOrderSummary deliveryOrderSummary = deliveryOrderSummaryRepository
                .findByStoreIdAndOrderSummaryId(
                    storeId, orderSummaryId);

            // 受付を回復する
            receivablesRepository
                .deleteByStoreIdAndReceivablesId(storeId, deliveryOrderSummary.getReceivablesId());

            // 注文サマリを回復する
            deliveryOrderSummaryRepository.deleteByStoreIdAndOrderSummaryIdAndDelFlag(storeId,
                orderSummaryId, Flag.ON.getCode());

            // 注文を回復する
            orderRepository
                .deleteByStoreIdAndOrderSummaryIdAndOrderId(storeId, orderSummaryId, orderId);

            // 注文DetailIDを取得する
            List<Integer> idList = orderDetailsRepository.getDetailIdList(storeId, orderId);

            // 注文商品を回復する
            orderDetailsRepository.deleteByStoreIdAndOrderId(storeId, orderId);

            // 注文商品オプションを回復する
            orderDetailsOptionRepository.deleteOrderItemOptions(storeId, idList);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.040", (Object) null), ex);
        }
    }

    /**
     * 返金（出前）.
     *
     * @param inputDto 支払情報
     */
    @Override
    @Transactional
    public void refundsDelivery(RefundsInputDto inputDto) {

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNo().substring(0, 20);

            // 注文ID
            String orderId = inputDto.getOrderNo()
                .substring(20, inputDto.getOrderNo().length() - 2);

            deliveryOrderSummaryRepository
                .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                    Flag.OFF.getCode());

            // 获取先支付且未支付的数据
            Integer count = orderRepository
                .getUnPayOrderCount(inputDto.getStoreId(), orderSummaryId,
                    PayStatus.PAY_NOT_ALREADY.getCode());

            // 存在未支付的数据
            if (count != 0) {

                // 提示不能退款
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.046", (Object) null));
            }

            // 获取支付信息
            PTrioResultDetail payData = trioResultDetailRepository
                .getPaymentAmount(inputDto.getStoreId(),
                    inputDto.getOrderNo(), PayResult.SUCCESS.getCode());

            if (null == payData) {

                // 提示支付数据获取异常
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.047", (Object) null));
            }

            // トリオ支払テーブル情報取得
            PPaymentCompany trioCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyId(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.TRIO.getCode());

            // 迅联分配或登记的门店号
            String storeId = trioCompany.getStoreCode();

            // 支付金额
            String transAmt = String.format("%012d", payData.getPayPrice().intValue());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            // 当前时间
            String refundTime = sdf.format(new Date()) + DateFormatUtils.format(new Date(), "Z");

            String tradeFrom = "openapi";

            String http = "POST";

            // CIL分配的用于签名/验签的key
            String key = trioCompany.getKey();

            // 第三方接口访问路径的域名
            String companyUrl = trioCompany.getUrl();

            // 讯联分配的唯一标识商户号
            String sid = trioCompany.getSid();

            String refundUrls = "/scanpay/mer/" + sid + "/refd/v0";

            // メッセージID
            String refundMsgId = UUID.randomUUID().toString().replaceAll("-", "");

            // 退款单号
            String refundId = inputDto.getOrderNo() + "refunds";

            SortedMap<Object, Object> refundPackageParams = new TreeMap<Object, Object>();
            refundPackageParams.put("msgId", refundMsgId);
            refundPackageParams.put("orderNum", refundId);
            refundPackageParams.put("storeId", storeId);
            refundPackageParams.put("origOrderNum", inputDto.getOrderNo());
            refundPackageParams.put("transAmt", transAmt);
            refundPackageParams.put("merTransTime", refundTime);
            refundPackageParams.put("tradeFrom", tradeFrom);

            String refundStrdata = JSONObject.toJSONString(refundPackageParams);

            String refundSign = http + "\n";
            refundSign += refundUrls + "\n";
            refundSign += refundTime + "\n";
            refundSign += key + "\n";
            refundSign += refundStrdata;
            refundSign = getSha256(refundSign);

            String refundUrl = companyUrl + refundUrls;
            log.info("TRIO API 返金（出前）開始: url={} data={} sign={} merTransTime={}", refundUrl,
                refundStrdata, refundSign, refundTime);
            String refundResStr = postDataSign(refundUrl, refundStrdata, refundSign, refundTime);
            log.info("TRIO API 返金（出前）結果: refundResStr={}", refundResStr);
            // 返回结果类型转化
            JSONObject refundReturnData = JSONObject.parseObject(refundResStr);

            String respCode = refundReturnData.get("respCode").toString();

            // 更新支付履历表
            trioResultDetailRepository.updateRefunds(inputDto.getStoreId(),
                inputDto.getOrderNo(), PayResult.SUCCESS.getCode(), refundId,
                respCode, userOperCd, dateTime);

            // 退款成功的场合
            if (PayResult.SUCCESS.getCode().equals(respCode)) {

                // 更新支付表支付金额
                Integer paymentId = paymentRepository
                    .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                        payData.getPayPrice(), userOperCd, dateTime);

                // 支払い明細IDのシーケンスNo取得
                GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
                getSeqNoInputDto.setItem("payment_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                // 支払い方式コード
                String paymentMethodCode = payData.getPaymentMethodCode();

                PPaymentDetail detailInputDao = new PPaymentDetail();
                // 店舗ID
                detailInputDao.setStoreId(inputDto.getStoreId());
                // 支払い明細
                detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
                // 支払いID
                detailInputDao.setPaymentId(paymentId);
                // 注文サマリID
                detailInputDao.setOrderSummaryId(orderSummaryId);
                // 注文ID
                if (StringUtils.isNotEmpty(orderId)) {

                    detailInputDao.setOrderId(Integer.valueOf(orderId));
                } else {

                    detailInputDao.setOrderId(0);
                }
                // 支払い方式
                detailInputDao.setPaymentMethodCode(paymentMethodCode);
                // 支払い金額
                detailInputDao.setPaymentAmount(payData.getPayPrice().multiply(new BigDecimal(-1)));
                // 削除フラグ
                detailInputDao.setDelFlag(Flag.OFF.getCode());
                // 登録者
                detailInputDao.setInsOperCd(userOperCd);
                // 登録日時
                detailInputDao.setInsDateTime(dateTime);
                // 更新者
                detailInputDao.setUpdOperCd(userOperCd);
                // 更新日時
                detailInputDao.setUpdDateTime(dateTime);
                // バージョン
                detailInputDao.setVersion(0);
                // 支払明細テーブル
                paymentDetailRepository.save(detailInputDao);

                if (StringUtils.isNotEmpty(orderId)) {

                    // 支払状態を更新する
                    orderRepository.updateStatus(inputDto.getStoreId(), orderSummaryId,
                        Integer.valueOf(orderId),
                        CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                } else {

                    // 支払状態を更新する
                    orderRepository.updatePaymentStatus(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                }

                // 支払情報を更新する
                deliveryOrderSummaryRepository
                    .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                        payData.getPayPrice().multiply(new BigDecimal(-1)), userOperCd, dateTime);
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.045", (Object) null), ex);
        }
    }

    /**
     * SBペイメント返金（出前）.
     *
     * @param inputDto 返金情報
     */
    @Override
    @Transactional
    public void sbPaymentRefundsDelivery(SbPaymentRefundsInputDto inputDto) {
        try {

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNo().substring(0, 20);

            // 注文ID
            String orderId = inputDto.getOrderNo()
                .substring(20, inputDto.getOrderNo().length() - 2);

            // 出前注文サマリーデータ取得
            deliveryOrderSummaryRepository
                .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                    Flag.OFF.getCode());

            // 支払情報取得
            PSbResultDetail payData = sbResultDetailRepository
                .getPaymentInfo(inputDto.getStoreId(),
                    inputDto.getOrderNo(), PayResult.SUCCESS.getCode());

            // データが存在しない場合
            if (payData == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.047", (Object) null));
            }

            // トリオ支払テーブル情報取得
            PPaymentCompany sbCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.SB.getCode(),
                    CompanyMethod.REFUNDS.getCode());

            // 返金要求データ作成
            SpsApiRequestDto requestDto = new SpsApiRequestDto();

            // 機能ID
            requestDto.setId(SbRefundsId.getSbRefundsId(payData.getPaymentMethodCode()));

            // マーチャントID
            requestDto.setMerchantId(sbCompany.getStoreCode());

            // サービスID
            requestDto.setServiceId(sbCompany.getSid());

            // 処理トラッキングID
            requestDto.setResTrackingId(payData.getResTrackingId());

            // リクエスト日時
            requestDto.setRequestDate(
                DateUtil.getNowDateTimeString(CommonConstants.DATE_FORMAT_DATE_TIMES));

            // リクエスト許容時間
            requestDto.setLimitSecond("600");

            // 送信情報データ連結
            String hashData = requestDto.getMerchantId() + requestDto.getServiceId() + requestDto
                .getResTrackingId() + requestDto.getRequestDate() + requestDto.getLimitSecond()
                + sbCompany.getKey();

            // チェックサム
            requestDto.setSpsHashcode(Md5Util.toSha(hashData, "SHA-1"));

            // 返金要求
            log.info("SB後払 API 出前返金要求開始: request={} url={}", requestDto, sbCompany.getUrl());
            SpsApiResponseDto spsApiResponse = spsPayRequest(requestDto, sbCompany.getUrl(),
                sbCompany.getStoreCode() + sbCompany.getSid(), sbCompany.getKey());
            log.info("SB後払 API 出前返金要求結果: response={}", spsApiResponse);

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 処理結果ステータス
            String respCode = PayResult.SUCCESS.getCode();
            if (!Objects.equals(ResResult.OK.getCode(), spsApiResponse.getResResult())) {
                respCode = PayResult.FAILED.getCode();
            }

            // 返金結果更新
            sbResultDetailRepository.updateRefunds(inputDto.getStoreId(), inputDto.getOrderNo(),
                PayResult.SUCCESS.getCode(), spsApiResponse.getResSpsTransactionId(),
                spsApiResponse.getResProcessDate(), respCode, spsApiResponse.getResErrCode(),
                userOperCd, dateTime);

            // 正常系レスポンスの場合
            if (Objects.equals(ResResult.OK.getCode(), spsApiResponse.getResResult())) {

                // 更新支付表支付金额
                Integer paymentId = paymentRepository
                    .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                        payData.getPayPrice(), userOperCd, dateTime);

                // 支払い明細IDのシーケンスNo取得
                GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
                getSeqNoInputDto.setItem("payment_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                // 支払い方式コード
                String paymentMethodCode = payData.getPaymentMethodCode();

                PPaymentDetail detailInputDao = new PPaymentDetail();
                // 店舗ID
                detailInputDao.setStoreId(inputDto.getStoreId());
                // 支払い明細
                detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
                // 支払いID
                detailInputDao.setPaymentId(paymentId);
                // 注文サマリID
                detailInputDao.setOrderSummaryId(orderSummaryId);
                // 注文ID
                if (StringUtils.isNotEmpty(orderId)) {
                    detailInputDao.setOrderId(Integer.valueOf(orderId));
                } else {
                    detailInputDao.setOrderId(0);
                }
                // 支払い方式
                detailInputDao.setPaymentMethodCode(paymentMethodCode);
                // 支払い金額
                detailInputDao.setPaymentAmount(payData.getPayPrice().multiply(new BigDecimal(-1)));
                // 削除フラグ
                detailInputDao.setDelFlag(Flag.OFF.getCode());
                // 登録者
                detailInputDao.setInsOperCd(userOperCd);
                // 登録日時
                detailInputDao.setInsDateTime(dateTime);
                // 更新者
                detailInputDao.setUpdOperCd(userOperCd);
                // 更新日時
                detailInputDao.setUpdDateTime(dateTime);
                // バージョン
                detailInputDao.setVersion(0);
                // 支払明細テーブル
                paymentDetailRepository.save(detailInputDao);

                if (StringUtils.isNotEmpty(orderId)) {

                    // 支払状態を更新する
                    orderRepository.updateStatus(inputDto.getStoreId(), orderSummaryId,
                        Integer.valueOf(orderId),
                        CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                } else {

                    // 支払状態を更新する
                    orderRepository.updatePaymentStatus(inputDto.getStoreId(), orderSummaryId,
                        PayStatus.PAY_NOT_ALREADY.getCode(), userOperCd, dateTime);
                }

                // 支払情報を更新する
                deliveryOrderSummaryRepository
                    .updatePaymentAmount(inputDto.getStoreId(), orderSummaryId,
                        payData.getPayPrice().multiply(new BigDecimal(-1)), userOperCd, dateTime);
            } else {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.045", (Object) null));
            }

        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.045", (Object) null), ex);
        }
    }

    /**
     * SBペイメント情报取得.
     *
     * @param inputDto 取得条件
     * @return ペイメント情报
     */
    @Override
    public GetSbPaymentInfoOutputDto getSbDvPaymentInfo(GetSbPaymentInfoInputDto inputDto) {
        // トリオ支払テーブル情報取得
        PPaymentCompany sbCompany = paymentCompanyRepository
            .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(inputDto.getStoreId(),
                Flag.OFF.getCode(), Company.SB.getCode(), CompanyMethod.PAYMENT.getCode());

        // 店舗情報取得
        MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (storeInfo == null) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 支払方式情報取得
        PaymentMethodDto storePaymentInfo = licenseRepository
            .findStorePaymentByCode(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PAYMENT,
                inputDto.getPaymentMethodCode());

        // 検索結果0件の場合
        if (storePaymentInfo == null) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.007", (Object) null));
        }

        // 店舗媒体情報取得
        List<MStoreMedium> storeMediumList = storeMediumRepository
            .findByStoreIdAndMediumTypeAndTerminalDistinctionAndDelFlagOrderBySortOrderAsc(
                inputDto.getStoreId(), MediumType.LOGO.getCode(),
                TerminalDistinction.CSMB.getCode(), Flag.OFF.getCode());

        // 支払履歴ID
        Integer maxDetailId = insetSbResultDetail(inputDto.getStoreId(),
            inputDto.getOrderSummaryId(), inputDto.getOrderId(), inputDto.getPayAmount(),
            inputDto.getPaymentMethodCode(), "01");

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

        // 前台回调方法URL
        String frontUrl =
            env.getProperty("qr.env.nginx.service.url") + "/delivery/#/mobile-placeOrder/payBack?"
                + "storeId=" + inputDto.getStoreId() + "&receivablesId=" + inputDto
                .getReceivablesId() + "&token=" + inputDto.getToken() + "&flgLanguage="
                + inputDto.getLanguages();

        // 結果DTO初期化
        GetSbPaymentInfoOutputDto outDto = new GetSbPaymentInfoOutputDto();

        // ペイメントUrl
        outDto.setActionUrl(sbCompany.getUrl());
        // 支払方法
        outDto.setPayMethod(storePaymentInfo.getPaymentName().toLowerCase());
        // マーチャントID
        outDto.setMerchantId(sbCompany.getStoreCode());
        // サービスID
        outDto.setServiceId(sbCompany.getSid());
        // 顧客ID
        outDto.setCustCode(inputDto.getReceivablesId() + "-" + maxDetailId);
        // 購入ID
        outDto.setOrderId(inputDto.getOrderSummaryId() + inputDto.getOrderId() + "01");
        // リクエスト日時
        String requestDate = DateUtil.getNowDateTimeString(CommonConstants.DATE_FORMAT_DATE_TIMES);
        // 商品ID
        outDto.setItemId(storeInfo.getStoreId() + "-" + requestDate + "-" + maxDetailId);
        // 商品名称
        outDto.setItemName(storeInfo.getStoreName());
        // 金額（税込）
        outDto.setAmount(inputDto.getPayAmount());
        // 購入タイプ 0：都度課金
        outDto.setPayType("0");
        // サービスタイプ 0：売上（購入）
        outDto.setServiceType(ServiceType.PURCHASE.getCode());
        // 顧客利用端末タイプ 0：PC
        outDto.setTerminalType("0");
        // 決済完了時URL
        outDto.setSuccessUrl(frontUrl + "&resResult=0");
        // 決済キャンセル時URL
        outDto.setCancelUrl(frontUrl + "&resResult=1");
        // エラー時URL
        outDto.setErrorUrl(frontUrl + "&resResult=1");
        // 決済通知用CGI
        outDto.setPageconUrl(
            env.getProperty("qr.env.nginx.service.url") + UrlConstants.CSDV_SB_PAYMENT_CALLBACK);
        // 自由欄１
        outDto.setFree1(
            inputDto.getStoreId() + "," + maxDetailId + "," + inputDto.getPaymentMethodCode());
        // 自由欄２
        outDto.setFree2("37f347d87a3fd402a46b");
        // 自由欄３
        outDto.setFree3("34d07cebbfd7");
        // フリー項目
        // LinePayの場合
        if (Objects.equals(AccountsType.LINEPAY.getCode(), inputDto.getPaymentMethodCode())
            && CollectionUtils.isNotEmpty(storeMediumList)) {
            outDto.setFreeCsv("PRODUCT_IMAGE_URL=" + storeMediumList.get(0).getImagePath());
        } else {
            outDto.setFreeCsv(StringUtils.EMPTY);
        }
        // リクエスト日時
        outDto.setRequestDate(requestDate);
        // リクエスト許容時間
        outDto.setLimitSecond("600");
        // ハッシュキー
        outDto.setHashKey(sbCompany.getKey());

        return outDto;
    }

    /**
     * 現金割勘.
     */
    @Override
    public String registOrder(RegistDutchAccountInputDto inputDto) {

        String orderSummaryId = "";

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 指定店舗情報取得
            MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());

            // 受付時間を取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

            // 現在時刻
            String nowTime = DateUtil
                .getZonedDateString(nowDateTime, CommonConstants.DATE_FORMAT_TIME);

            // 営業開始時間
            String[] startTimeArray = store.getStartTime().split(":");
            int startTimeHour = Integer.parseInt(startTimeArray[0]);
            int startTimeMinute = Integer.parseInt(startTimeArray[1]);

            // 現在時間
            String[] nowTimeArray = nowTime.split(":");
            int nowHour = Integer.parseInt(nowTimeArray[0]);
            int nowMinute = Integer.parseInt(nowTimeArray[1]);

            // 現在日付
            String nowDate = DateUtil
                .getZonedDateString(nowDateTime, CommonConstants.DATE_FORMAT_DATE);

            // 営業開始時間
            ZonedDateTime startTime = DateUtil
                .getZonedDateByString(nowDate + " " + store.getStartTime() + ":00",
                    CommonConstants.DATE_FORMAT_DATETIME);

            // 営業締め時間
            ZonedDateTime endTime = startTime.plusDays(1L);

            // 昨日の場合
            if (nowHour < startTimeHour || (nowHour == startTimeHour
                && nowMinute < startTimeMinute)) {
                endTime = startTime;
                startTime = endTime.minusDays(1L);
            }

            // 受付ID取得
            String newReceivablesId = "";

            if (null == inputDto.getNewReceivablesId() || ""
                .equals(inputDto.getNewReceivablesId())) {
                newReceivablesId = itemInfoSharedService
                    .getReceivablesId(inputDto.getStoreId(), nowDateTime);
            } else {
                newReceivablesId = inputDto.getNewReceivablesId();
            }

            // 受付情報登録
            OReceivables receivables = new OReceivables();
            receivables.setStoreId(inputDto.getStoreId()); // 店舗ID
            receivables.setReceivablesId(newReceivablesId); // 受付ID
            // 受付番号
            Integer receptionNo = rreceivablesRepository
                .getReceptionNoByReceptionTime(inputDto.getStoreId(), startTime, endTime);
            receivables.setReceptionNo(receptionNo); // 受付番号
            receivables.setCustomerCount(inputDto.getCustomerCount()); // 顧客人数
            receivables.setReceptionTime(nowDateTime); // 受付日時
            receivables.setInsDateTime(nowDateTime); // 登録日時
            receivables.setInsOperCd(userOperCd); // 登録者
            receivables.setUpdDateTime(nowDateTime); // 更新日時
            receivables.setUpdOperCd(userOperCd); // 更新者
            receivables.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
            receivables.setVersion(0); // バージョン
            rreceivablesRepository.save(receivables);

            if (null == inputDto.getNewOrderSummaryId() || ""
                .equals(inputDto.getNewOrderSummaryId())) {
                // 受付ID取得
                orderSummaryId = itemInfoSharedService
                    .getReceivablesId(inputDto.getStoreId(), nowDateTime);
            } else {
                orderSummaryId = inputDto.getNewOrderSummaryId();
            }

            // 注文サマリテーブル登録
            OOrderSummary orderSummary = new OOrderSummary();
            orderSummary.setStoreId(inputDto.getStoreId()); // 店舗ID
            orderSummary.setReceivablesId(newReceivablesId); // 受付id
            orderSummary.setOrderSummaryId(orderSummaryId); // 注文サマリID
            OOrderSummary orderSummaryData = orderSummaryRepository
                .findByStoreIdAndReceivablesIdAndDelFlag(
                    inputDto.getStoreId(), inputDto.getReceivablesId(), Flag.OFF.getCode());
            orderSummary
                .setOriginalOrderSummaryId(orderSummaryData.getOrderSummaryId()); // 元注文サマリID
            orderSummary.setCustomerCount(receivables.getCustomerCount()); // 顧客人数
            orderSummary.setTableId(inputDto.getTableId()); // テーブルID
            orderSummary.setOrderAmount(inputDto.getSubtotal()); // 小計
            orderSummary.setPriceDiscountAmount(inputDto.getPriceDiscountAmount()); // 割引額
            orderSummary.setPriceDiscountRate(inputDto.getPriceDiscountRate()); // 割引率
            orderSummary.setPaymentAmount(inputDto.getPaymentAmount()); // 支払金額
            orderSummary.setTakeoutFlag(inputDto.getTakeoutFlag()); // テイクアウト区分
            orderSummary.setOrderStatus(OrderStatus.ORDER.getCode()); // 注文状態
            orderSummary.setSeatRelease(Flag.ON.getCode().toString()); // 席解除フラグ
            orderSummary.setPaymentType(PaymentType.DEFERRED_PAYMENT.getCode()); // 支払区分
            orderSummary.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
            orderSummary.setInsDateTime(nowDateTime); // 登録日時
            orderSummary.setInsOperCd(userOperCd); // 登録者
            orderSummary.setUpdDateTime(nowDateTime); // 更新日時
            orderSummary.setUpdOperCd(userOperCd); // 更新者
            orderSummary.setVersion(0); // バージョン
            orderSummaryRepository.save(orderSummary);

            // 注文IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_order"); // テーブル名
            getSeqNoInputDto.setItem("order_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 注文テーブル登録
            OOrder order = new OOrder();
            order.setStoreId(inputDto.getStoreId()); // 店舗ID
            order.setOrderSummaryId(orderSummaryId); // 注文サマリID
            order.setDelFlag(Flag.OFF.getCode()); // 削除フラグ

            order.setOrderId(getSeqNo.getSeqNo()); // 注文ID
            order.setPayStatus(PayStatus.PAY_ALREADY.getCode()); // 支払状態
            order.setForeignTax(BigDecimal.ZERO); // 外税金額
            order.setOrderTime(nowDateTime); // 注文日時
            order.setOrderType(OrderType.PAD.getCode()); // 注文区分
            order.setInsDateTime(nowDateTime); // 登録日時
            order.setInsOperCd(userOperCd); // 登録者
            order.setUpdDateTime(nowDateTime); // 更新日時
            order.setUpdOperCd(userOperCd); // 更新者
            order.setVersion(0);
            orderRepository.save(order); // バージョン

            List<OOrderDetailsOption> orderDetailsOptionList = orderDetailsOptionRepository
                .getOrderOptionList(
                    inputDto.getStoreId(), orderSummaryData.getOrderSummaryId());

            List<OOrderDetails> newOrderDetailList = new ArrayList<OOrderDetails>();
            List<OOrderDetailsOption> newOrderDetailOptionList = new ArrayList<OOrderDetailsOption>();

            for (int i = 0; i < inputDto.getItemList().size(); i++) {

                OOrderDetails newOrderDetail = new OOrderDetails();
                newOrderDetail.setStoreId(inputDto.getStoreId()); // 店舗ID
                newOrderDetail.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
                newOrderDetail.setOrderId(order.getOrderId()); // 注文ID
                newOrderDetail.setItemId(inputDto.getItemList().get(i).getItemId()); // 商品ID
                newOrderDetail.setItemPrice(inputDto.getItemList().get(i).getItemPrice()); // 単価
                newOrderDetail.setItemCount(inputDto.getItemList().get(i).getItemCount()); // 商品個数
                newOrderDetail.setItemClassification(ItemType.NORMAL.getCode()); // 商品区分
                newOrderDetail.setItemStatus(ItemStatus.CONFIRMED.getCode()); // 商品状態

                // 注文明細IDのシーケンスNo取得
                getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
                getSeqNoInputDto.setItem("order_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
                getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                newOrderDetail.setOrderDetailId(getSeqNo.getSeqNo()); //注文明細ID
                newOrderDetail.setInsDateTime(nowDateTime); // 登録日時
                newOrderDetail.setInsOperCd(userOperCd); // 登録者
                newOrderDetail.setUpdDateTime(nowDateTime); // 更新日時
                newOrderDetail.setUpdOperCd(userOperCd); // 更新者
                newOrderDetail.setVersion(0); // バージョン
                newOrderDetail.setItemReturnId(null);
                newOrderDetailList.add(newOrderDetail);

                for (int j = 0; j < orderDetailsOptionList.size(); j++) {
                    if (orderDetailsOptionList.get(j).getOrderDetailId().intValue()
                        == inputDto.getItemList().get(i).getOrderDetailId().intValue()) {
                        // 注文明細オプションテーブル登録
                        OOrderDetailsOption newOrderDetailsOption = new OOrderDetailsOption();
                        newOrderDetailsOption.setStoreId(inputDto.getStoreId()); //店舗ID
                        newOrderDetailsOption
                            .setOrderDetailId(newOrderDetail.getOrderDetailId()); // 注文明細ID
                        newOrderDetailsOption.setItemOptionTypeCode(
                            orderDetailsOptionList.get(j).getItemOptionTypeCode()); // 商品オプション種類コード
                        newOrderDetailsOption.setItemOptionCode(
                            orderDetailsOptionList.get(j).getItemOptionCode()); // 商品オプションコード
                        newOrderDetailsOption
                            .setDiffPrice(orderDetailsOptionList.get(j).getDiffPrice()); // 差額
                        newOrderDetailsOption.setItemOptionCount(
                            orderDetailsOptionList.get(j).getItemOptionCount()); // 数量
                        newOrderDetailsOption.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
                        newOrderDetailsOption.setInsDateTime(nowDateTime); // 登録日時
                        newOrderDetailsOption.setInsOperCd(userOperCd); // 登録者
                        newOrderDetailsOption.setUpdDateTime(nowDateTime); // 更新日時
                        newOrderDetailsOption.setUpdOperCd(userOperCd); // 更新者
                        newOrderDetailsOption.setVersion(0); // バージョン
                        newOrderDetailOptionList.add(newOrderDetailsOption);
                    }
                }
            }

            // 新規注文の注文商品を作成する
            orderDetailsRepository.saveAll(newOrderDetailList);
            // 新規注文の注文商品オプションを作成する
            orderDetailsOptionRepository.saveAll(newOrderDetailOptionList);

            List<OOrderDetails> orderDetailsList = orderDetailsRepository.getOrderDetailList(
                inputDto.getStoreId(), orderSummaryData.getOrderSummaryId());

            List<OOrderDetails> oldOrderDetailsList = new ArrayList<OOrderDetails>();

            for (OOrderDetails orderDetails : orderDetailsList) {

                boolean checkHasItem = false;

                for (int j = 0; j < inputDto.getItemList().size(); j++) {

                    if (orderDetails.getOrderId().intValue() == inputDto.getItemList()
                        .get(j).getOrderId().intValue()
                        && orderDetails.getOrderDetailId().intValue() == inputDto
                        .getItemList().get(j).getOrderDetailId().intValue()) {
                        checkHasItem = true;
                        OOrderDetails oldOrderDetail = new OOrderDetails();
                        oldOrderDetail = orderDetails;
                        // 商品価格
                        oldOrderDetail.setItemPrice(new BigDecimal(
                            oldOrderDetail.getItemCount() - inputDto.getItemList().get(j)
                                .getItemCount()).multiply(oldOrderDetail.getItemPrice()
                            .divide(new BigDecimal(oldOrderDetail.getItemCount()))));
                        // 商品個数　★商品の価格と個数の順が修正できない
                        oldOrderDetail.setItemCount(
                            oldOrderDetail.getItemCount() - inputDto.getItemList().get(j)
                                .getItemCount());
                        oldOrderDetailsList.add(oldOrderDetail);
                        break;
                    }
                }

                if (!checkHasItem) {

                    oldOrderDetailsList.add(orderDetails);
                }
            }

            // 注文商品変更
            orderDetailsRepository.saveAll(oldOrderDetailsList);
            // 注文商品の詳細IDを取得する
            List<Integer> orderDetailIdList = orderDetailsRepository
                .getOrdertailId(inputDto.getStoreId(), orderSummaryData.getOrderSummaryId());
            // 注文商品を削除する
            orderDetailsRepository
                .deleteItemDetails(inputDto.getStoreId(), orderDetailIdList, userOperCd,
                    nowDateTime);
            // 注文商品オプションを削除する
            orderDetailsOptionRepository
                .deleteItemDetails(inputDto.getStoreId(), orderDetailIdList, userOperCd,
                    nowDateTime);
            // 注文情報を削除する
            orderRepository.deleteOrder(inputDto.getStoreId(), orderSummaryData.getOrderSummaryId(),
                userOperCd, nowDateTime);
            // 注文人数と金額を変更する
            orderSummaryRepository.updateCustomerCountAndOrderAmount(inputDto.getStoreId(),
                orderSummaryData.getOrderSummaryId(),
                inputDto.getCustomerCount(), inputDto.getSubtotal(), userOperCd, nowDateTime);
        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.040", (Object) null), ex);
        }

        return orderSummaryId;
    }

    /**
     * 現金割勘.
     */
    public void insertPayment(RegistDutchAccountInputDto inputDto, String orderSummaryId) {

        try {
            // 支払いIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment"); // テーブル名
            getSeqNoInputDto.setItem("payment_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_STORE_PAD); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPayment inputDao = new PPayment();
            // 店舗ID
            inputDao.setStoreId(inputDto.getStoreId());
            // 支払いID
            inputDao.setPaymentId(getSeqNo.getSeqNo());
            // 注文サマリID
            inputDao.setOrderSummaryId(orderSummaryId);
            // 支払い金額
            inputDao
                .setPaymentAmount(inputDto.getPaymentAmount());
            // 削除フラグ
            inputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            inputDao.setInsOperCd(CommonConstants.OPER_CD_STORE_PAD);
            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            inputDao.setInsDateTime(dateTime);
            // 更新者
            inputDao.setUpdOperCd(CommonConstants.OPER_CD_STORE_PAD);
            // 更新日時
            inputDao.setUpdDateTime(dateTime);
            // バージョン
            inputDao.setVersion(0);

            // 支払い明細IDのシーケンスNo取得
            getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDao.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
            getSeqNoInputDto.setItem("payment_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_STORE_PAD); // 登録更新者
            getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPaymentDetail detailInputDao = new PPaymentDetail();
            // 店舗ID
            detailInputDao.setStoreId(inputDao.getStoreId());
            // 支払い明細
            detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
            // 支払いID
            // 支払テーブル
            Integer paymentId = paymentRepository
                .insertOrUpdate(inputDao.getStoreId(), inputDao.getPaymentId(),
                    inputDao.getOrderSummaryId(),
                    inputDao.getPaymentAmount(),
                    inputDao.getDelFlag(),
                    inputDao.getInsOperCd(),
                    inputDao.getInsDateTime(),
                    inputDao.getUpdOperCd(),
                    inputDao.getUpdDateTime(),
                    inputDao.getVersion());
            detailInputDao.setPaymentId(paymentId);
            // 注文サマリID
            detailInputDao.setOrderSummaryId(orderSummaryId);
            // 注文ID
            detailInputDao.setOrderId(0);
            // 支払い方式
            detailInputDao.setPaymentMethodCode(inputDto.getPaymentMethodCode());
            // 支払い金額
            detailInputDao.setPaymentAmount(inputDto.getPaymentAmount());
            // 削除フラグ
            detailInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            detailInputDao.setInsOperCd(CommonConstants.OPER_CD_STORE_PAD);
            // 登録日時
            detailInputDao.setInsDateTime(dateTime);
            // 更新者
            detailInputDao.setUpdOperCd(CommonConstants.OPER_CD_STORE_PAD);
            // 更新日時
            detailInputDao.setUpdDateTime(dateTime);
            // バージョン
            detailInputDao.setVersion(0);
            // 支払明細テーブル
            paymentDetailRepository.save(detailInputDao);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.040", (Object) null), ex);
        }
    }

    /**
     * SBペイメント割勘後払.
     *
     * @param inputDto 支払情報
     * @return 支払結果
     */
    @Override
    public DutchAccountPayLaterOutputDto dutchAccountPayLater(
        DutchAccountPayLaterInputDto inputDto) {

        try {

            // 受付時間を取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

            // 注文サマリID取得
            String orderSummaryId = itemInfoSharedService
                .getReceivablesId(inputDto.getStoreId(), nowDateTime);

            // 注文ID
            Integer orderId = 0;

            // 注文番号を取得する
            String orderSummaryNo = sbResultDetailRepository
                .getOrderSummaryNo(inputDto.getStoreId(), orderSummaryId,
                    orderId);

            // 注文番号編集
            String maxId = "01";
            if (StringUtils.isNotEmpty(orderSummaryNo)) {

                // 次の注文番号
                int nextId =
                    Integer.parseInt(orderSummaryNo.substring(orderSummaryNo.length() - 2)) + 1;

                // 最大超えた場合
                if (99 < nextId) {
                    throw new BusinessException("2001",
                        ResultMessages.error().add("e.qr.ph.048", (Object) null));
                }

                maxId = String.format("%02d", nextId);
            }

            // QR端末処理通番
            String orderNo = orderSummaryId + orderId + maxId;

            // 注文番号
            if (orderId == 0) {
                orderNo = orderSummaryId + maxId;
            }

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 結果DTO初期化
            DutchAccountPayLaterOutputDto outDto = new DutchAccountPayLaterOutputDto();

            // 決済要求
            // リクエストのパラメーターを設定する
            JSONObject payData = new JSONObject();
            payData.put("clientOrderNo", orderNo);
            payData.put("orderPrice", inputDto.getPaymentAmount());
            payData.put("orderSubject", "Purchased Item");
            payData.put("payType", PayType.CPM.getCode());
            payData.put("brandType", AccountsType.ANY.getCode());
            payData.put("authCode", inputDto.getPayCodeId());

            // 支払テーブル情報取得
            PPaymentCompany sbCompany = paymentCompanyRepository
                .findByStoreIdAndDelFlagAndCompanyIdAndCompanyMethod(
                    inputDto.getStoreId(), Flag.OFF.getCode(), Company.SB.getCode(),
                    CompanyMethod.CPM_PAY.getCode());

            // サイン値生成
            String signature = Md5Util.toMd5(
                "authCode=" + inputDto.getPayCodeId() + "&brandType=" + AccountsType.ANY.getCode()
                    + "&clientOrderNo=" + orderNo + "&orderPrice=" + inputDto.getPaymentAmount()
                    + "&orderSubject=Purchased Item" + "&payType=" + PayType.CPM.getCode() + "&key="
                    + sbCompany.getKey()).toUpperCase();

            // 決済結果編集
            SpsGateWayResultDto payResultData = null;
            // 支払結果
            String respCode = PayResult.FAILED.getCode();
            // エラーコード
            String resErrCode = StringUtils.EMPTY;

            try {

                // 決済結果
                log.info("SB後払 API 決済(割勘)開始: payData={} url={}", payData, sbCompany.getUrl());
                String payResult = sbPsQrGateWayRequest(payData, sbCompany.getUrl(),
                    sbCompany.getTermId(),
                    signature);
                log.info("SB後払 API 決済(割勘)結果: payResult={}", payResult);
                // 決済結果編集
                payResultData = JSONObject.parseObject(payResult, SpsGateWayResultDto.class);

                // 正常の場合
                if (Objects.equals("0000", payResultData.getResultCode())) {
                    // エンドユーザー支払い中の場合
                    if (Objects.equals(SbOrderStatus.PAYING.getCode(),
                        payResultData.getResult().getOrderStatus())) {
                        // 決済機関にステータス参照10秒おき
                        payResultData = retryGetOrderInfo(orderNo, inputDto.getStoreId());
                    }

                    // 決済成功
                    if (Objects.equals("1", payResultData.getResult().getOrderStatus())) {
                        respCode = PayResult.SUCCESS.getCode();
                    }
                } else {
                    // エラーコード
                    resErrCode = payResultData.getResultCode();
                }

                // 支払結果設定
                outDto.setRespCode(respCode);

                // 決済タイムアウト
            } catch (ResourceAccessException | BusinessException ignored) {
                // 決済取消
                log.info("SB後払 API 決済(割勘)取消開始: orderNo={} storeId={}", orderNo,
                    inputDto.getStoreId());
                String cancelResult = sbPsOrderCancel(orderNo, inputDto.getStoreId());
                log.info("SB後払 API 決済(割勘)取消結果: cancelResult={}", cancelResult);
                // 決済取消結果編集
                payResultData = JSONObject.parseObject(cancelResult, SpsGateWayResultDto.class);
                respCode = "09";
                // 支払結果設定
                outDto.setRespCode(respCode);
                outDto.setCancelRespCode(payResultData.getResultCode());
            }

            // 支払い方法
            String paymentMethodCode = AccountsType.ANY.getCode();
            // 処理トラッキングID
            String resTrackingId = StringUtils.EMPTY;
            // 顧客決済情報
            String resPayinfoKey = StringUtils.EMPTY;

            // 決済要求結果
            if (payResultData.getResult() != null) {
                // 支払い方法再編集
                if (StringUtils.isNotEmpty(payResultData.getResult().getBrandType())) {
                    if (!Objects.equals("0000", payResultData.getResultCode())) {
                        paymentMethodCode = String.format("%02d",
                            Integer.parseInt(payResultData.getResult().getBrandType()));
                    } else {
                        paymentMethodCode = String.format("%02d",
                            Integer.parseInt(payResultData.getResult().getBrandType()) + 20);
                    }
                }
                resTrackingId = payResultData.getResult().getGwOrderNo();
                resPayinfoKey = payResultData.getResult().getBrandOrderNo();
            }

            // 支払結果データ作成
            insetQrResultData(inputDto.getStoreId(), orderSummaryId, orderId,
                inputDto.getPaymentAmount(), paymentMethodCode, respCode, orderNo, resTrackingId,
                resPayinfoKey, userOperCd, nowDateTime, resErrCode);
            // 注文サマリID
            outDto.setOrderSummaryId(orderSummaryId);
            // 受付ID取得
            String newReceivablesId = itemInfoSharedService
                .getReceivablesId(inputDto.getStoreId(), nowDateTime);
            outDto.setReceivablesId(newReceivablesId);
            outDto.setPaymentMethodCode(paymentMethodCode);
            return outDto;
        } catch (Exception ex) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.048", (Object) null), ex);
        }
    }

    /**
     * 出前注文状態変更.
     */
    @Override
    @Transactional
    public void updateDeliveryStatus(String orderSummaryId, String storeId) {

        try {

            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 出前注文状態変更
            deliveryOrderSummaryRepository.updateDeliveryStatus(storeId, orderSummaryId,
                DeliveryStatus.REFUND.getCode(), CommonConstants.OPER_CD_STORE_PAD, dateTime);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.037", (Object) null), ex);
        }
    }
}
