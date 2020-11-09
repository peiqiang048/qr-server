package com.cnc.qr.core.order.service.impl;

import static com.cnc.qr.common.constants.CommonConstants.CODE_GROUP_PAYMENT;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.component.RedisLockUtil;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.AccountsType;
import com.cnc.qr.common.constants.CodeConstants.AreaType;
import com.cnc.qr.common.constants.CodeConstants.DeliveryStatus;
import com.cnc.qr.common.constants.CodeConstants.DeliveryTypeFlag;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemShowStatus;
import com.cnc.qr.common.constants.CodeConstants.ItemStatus;
import com.cnc.qr.common.constants.CodeConstants.ItemType;
import com.cnc.qr.common.constants.CodeConstants.Language;
import com.cnc.qr.common.constants.CodeConstants.MstItemType;
import com.cnc.qr.common.constants.CodeConstants.OptionType;
import com.cnc.qr.common.constants.CodeConstants.OrderShowStatus;
import com.cnc.qr.common.constants.CodeConstants.OrderStatus;
import com.cnc.qr.common.constants.CodeConstants.OrderTransfer;
import com.cnc.qr.common.constants.CodeConstants.OrderType;
import com.cnc.qr.common.constants.CodeConstants.PayStatus;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.PrintStatus;
import com.cnc.qr.common.constants.CodeConstants.ReservateClassification;
import com.cnc.qr.common.constants.CodeConstants.ReservateStatus;
import com.cnc.qr.common.constants.CodeConstants.StaffCheckFlag;
import com.cnc.qr.common.constants.CodeConstants.TakeoutFlag;
import com.cnc.qr.common.constants.CodeConstants.TaxType;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MControl;
import com.cnc.qr.common.entity.MItem;
import com.cnc.qr.common.entity.MSellOff;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.ODeliveryOrderSummary;
import com.cnc.qr.common.entity.OOrder;
import com.cnc.qr.common.entity.OOrderDetails;
import com.cnc.qr.common.entity.OOrderDetailsOption;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.common.entity.OPrintQueue;
import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.common.entity.RItem;
import com.cnc.qr.common.entity.RTable;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MControlRepository;
import com.cnc.qr.common.repository.MDeliveryAreaRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MLicenseRepository;
import com.cnc.qr.common.repository.MSellOffRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.MTableRepository;
import com.cnc.qr.common.repository.MTaxRepository;
import com.cnc.qr.common.repository.MTokenRepository;
import com.cnc.qr.common.repository.ODeliveryOrderSummaryRepository;
import com.cnc.qr.common.repository.OOrderDetailsOptionRepository;
import com.cnc.qr.common.repository.OOrderDetailsRepository;
import com.cnc.qr.common.repository.OOrderRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OPrintQueueRepository;
import com.cnc.qr.common.repository.OReceivablesRepository;
import com.cnc.qr.common.repository.PPaymentDetailRepository;
import com.cnc.qr.common.repository.RBuffetItemRepository;
import com.cnc.qr.common.repository.RItemRepository;
import com.cnc.qr.common.repository.RTableRepository;
import com.cnc.qr.common.shared.model.GetItemOptionInfoInputDto;
import com.cnc.qr.common.shared.model.GetItemOptionInfoOutputDto;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.acct.model.WeChatAliPayBackInputDto;
import com.cnc.qr.core.order.model.AmountSoldDto;
import com.cnc.qr.core.order.model.AmountSoldInputDto;
import com.cnc.qr.core.order.model.AmountSoldOutputDto;
import com.cnc.qr.core.order.model.AssignationTableDto;
import com.cnc.qr.core.order.model.AssignationTableInputDto;
import com.cnc.qr.core.order.model.AssignationTableOutputDto;
import com.cnc.qr.core.order.model.BlockListDto;
import com.cnc.qr.core.order.model.BuffetCourseItemDto;
import com.cnc.qr.core.order.model.BuffetInfoDto;
import com.cnc.qr.core.order.model.CateringTimeDto;
import com.cnc.qr.core.order.model.ChangeCustomerCountInputDto;
import com.cnc.qr.core.order.model.ChooseAbleTableDto;
import com.cnc.qr.core.order.model.CityListDto;
import com.cnc.qr.core.order.model.CourseBuffetConfirmInputDto;
import com.cnc.qr.core.order.model.CourseBuffetConfirmOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetDetailInputDto;
import com.cnc.qr.core.order.model.CourseBuffetDetailOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetDto;
import com.cnc.qr.core.order.model.CourseBuffetListInputDto;
import com.cnc.qr.core.order.model.CourseBuffetListOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetOrderOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetRegistInputDto;
import com.cnc.qr.core.order.model.CourseInfoDto;
import com.cnc.qr.core.order.model.DeleteItemInputDto;
import com.cnc.qr.core.order.model.DeliveryOrderInfoDto;
import com.cnc.qr.core.order.model.DeliveryOrderInputDto;
import com.cnc.qr.core.order.model.DeliveryOrderOutputDto;
import com.cnc.qr.core.order.model.DeliveryStatusDto;
import com.cnc.qr.core.order.model.DeliveryTimeListDto;
import com.cnc.qr.core.order.model.DeliveryTypeFlagDto;
import com.cnc.qr.core.order.model.DiscardOrderInputDto;
import com.cnc.qr.core.order.model.EmptyItemInputDto;
import com.cnc.qr.core.order.model.GetAreaDevInfoDto;
import com.cnc.qr.core.order.model.GetDefaultUseTimeDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderDetailInfoInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderDetailInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderEditInfoInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderEditInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderListInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderOutputDto;
import com.cnc.qr.core.order.model.GetItemDetailInputDto;
import com.cnc.qr.core.order.model.GetItemDetailOutputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInfoInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInfoOutputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailOutputDto;
import com.cnc.qr.core.order.model.GetOrderFlagInputDto;
import com.cnc.qr.core.order.model.GetOrderFlagOutputDto;
import com.cnc.qr.core.order.model.GetOrderHistoryListInputDto;
import com.cnc.qr.core.order.model.GetOrderHistoryListOutputDto;
import com.cnc.qr.core.order.model.GetOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetOrderListInputDto;
import com.cnc.qr.core.order.model.GetOrderListOutputDto;
import com.cnc.qr.core.order.model.GetReturnReasonDto;
import com.cnc.qr.core.order.model.GetReturnReasonInputDto;
import com.cnc.qr.core.order.model.GetReturnReasonOutputDto;
import com.cnc.qr.core.order.model.GetSeatReleaseListInputDto;
import com.cnc.qr.core.order.model.GetSeatReleaseListOutputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListOutputDto;
import com.cnc.qr.core.order.model.GetShareOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetShareOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListOutputDto;
import com.cnc.qr.core.order.model.InitOrderInputDto;
import com.cnc.qr.core.order.model.InitOrderOutputDto;
import com.cnc.qr.core.order.model.ItemDetailDto;
import com.cnc.qr.core.order.model.ItemDetailOptionDto;
import com.cnc.qr.core.order.model.ItemDetailOptionTypeDto;
import com.cnc.qr.core.order.model.ItemDto;
import com.cnc.qr.core.order.model.ItemInfoDto;
import com.cnc.qr.core.order.model.ItemOptionTypeDto;
import com.cnc.qr.core.order.model.ItemsDetailDto;
import com.cnc.qr.core.order.model.ItemsDto;
import com.cnc.qr.core.order.model.NoItemsInfoDto;
import com.cnc.qr.core.order.model.OrderAmountDto;
import com.cnc.qr.core.order.model.OrderBuffetCourseDto;
import com.cnc.qr.core.order.model.OrderDetailItemDataDto;
import com.cnc.qr.core.order.model.OrderIdDto;
import com.cnc.qr.core.order.model.OrderInfoDto;
import com.cnc.qr.core.order.model.OrderInputDto;
import com.cnc.qr.core.order.model.OrderItemDataDto;
import com.cnc.qr.core.order.model.OrderItemDetailDataDto;
import com.cnc.qr.core.order.model.OrderItemDetailInfoDto;
import com.cnc.qr.core.order.model.OrderItemDto;
import com.cnc.qr.core.order.model.OrderItemFullInfoDto;
import com.cnc.qr.core.order.model.OrderItemInfoDto;
import com.cnc.qr.core.order.model.OrderItemNumDto;
import com.cnc.qr.core.order.model.OrderItemOptionDto;
import com.cnc.qr.core.order.model.OrderItemsDto;
import com.cnc.qr.core.order.model.OrderOutputDto;
import com.cnc.qr.core.order.model.OrderSummaryAndOrderIdDto;
import com.cnc.qr.core.order.model.OrderTransferInputDto;
import com.cnc.qr.core.order.model.OrderTransferOutputDto;
import com.cnc.qr.core.order.model.OreceivablesGridDto;
import com.cnc.qr.core.order.model.PaymentMethodDto;
import com.cnc.qr.core.order.model.PaymentTypeListDto;
import com.cnc.qr.core.order.model.PrefectureListDto;
import com.cnc.qr.core.order.model.QrCodeIssueInputDto;
import com.cnc.qr.core.order.model.QrCodeIssueOutputDto;
import com.cnc.qr.core.order.model.ReceptionDisposalInputDto;
import com.cnc.qr.core.order.model.ReceptionInitInputDto;
import com.cnc.qr.core.order.model.ReceptionInitOutputDto;
import com.cnc.qr.core.order.model.ReceptionTableDto;
import com.cnc.qr.core.order.model.RedisNoItemsInfoDto;
import com.cnc.qr.core.order.model.RegistReturnInputDto;
import com.cnc.qr.core.order.model.RegistReturnOutputDto;
import com.cnc.qr.core.order.model.SeatReleaseDto;
import com.cnc.qr.core.order.model.SeatReleaseInputDto;
import com.cnc.qr.core.order.model.SetTableInputDto;
import com.cnc.qr.core.order.model.SureOrderItemInputDto;
import com.cnc.qr.core.order.model.TableSeatDto;
import com.cnc.qr.core.order.model.TakeoutTimeDto;
import com.cnc.qr.core.order.model.UnCfmItemInfoDto;
import com.cnc.qr.core.order.model.UnCfmOrderInfoDto;
import com.cnc.qr.core.order.model.UpdateDeliveryOrderInputDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注文確認サービス実装クラス.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    /**
     * Redis操作クラス.
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Redisロッククラス.
     */
    @Autowired
    private RedisLockUtil redisLockUtil;

    /**
     * 欠品テーブルタリポジトリ.
     */
    @Autowired
    private MSellOffRepository sellOffRepository;

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
     * 受付テーブルリポジトリ.
     */
    @Autowired
    private OReceivablesRepository oreceivablesRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * コントロールマスタリポジトリ.
     */
    @Autowired
    private MControlRepository controlRepository;

    /**
     * テーブルマスタリポジトリ.
     */
    @Autowired
    private MTableRepository tableRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * 商品テーブルリポジトリ.
     */
    @Autowired
    MItemRepository itemRepository;

    /**
     * 商品関連テーブルリポジトリ.
     */
    @Autowired
    RItemRepository ritemRepository;

    /**
     * 税テーブルリポジトリ.
     */
    @Autowired
    MTaxRepository taxRepository;

    /**
     * 受付テーブルリポジトリ.
     */
    @Autowired
    private OReceivablesRepository receivablesRepository;

    /**
     * 支払明細テーブルリポジトリ.
     */
    @Autowired
    private PPaymentDetailRepository paymentDetailRepository;

    /**
     * 放題商品選択テーブルリポジトリ.
     */
    @Autowired
    RBuffetItemRepository buffetItemRepository;

    /**
     * テーブル関連リポジトリ.
     */
    @Autowired
    private RTableRepository relationTableRepository;

    /**
     * トークンマスタリポジトリ.
     */
    @Autowired
    MTokenRepository tokenRepository;

    /**
     * 出前注文サマリテーブルタリポジトリ.
     */
    @Autowired
    private ODeliveryOrderSummaryRepository deliveryOrderSummaryRepository;

    /**
     * 配達区域テーブルタリポジトリ.
     */
    @Autowired
    private MDeliveryAreaRepository deliveryAreaRepository;

    /**
     * ライセンスマスタリポジトリ.
     */
    @Autowired
    private MLicenseRepository licenseRepository;

    /**
     * コードマスタリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * テーブル関連リポジトリ.
     */
    @Autowired
    private RTableRepository rtableRepository;

    /**
     * 伝票印刷.
     */
    @Autowired
    PrintDataSharedService printDataSharedService;


    /**
     * 共通部品プリンター.
     */
    @Autowired
    OPrintQueueRepository printQueueRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 注文確定.
     *
     * @param inputDto 注文確定情報
     * @return 処理結果
     */
    @Override
    public OrderOutputDto getOrder(OrderInputDto inputDto) {

        // ロックタイム設定
        String lockTime = String.valueOf(System.currentTimeMillis() + 10000L);

        try {

            // Redis保存keyを生成する
            String primaryKey = inputDto.getReceivablesId();

            // 注文確認情報取得
            List<String> redisOrderList = redisTemplate.opsForList().range(primaryKey, 0, -1);

            // データが存在しない場合、エラー処理
            if (CollectionUtils.isEmpty(redisOrderList)) {
                throw new BusinessException("2006",
                    ResultMessages.error().add("e.qr.ph.031", (Object) null));
            }

            // 画面の注文確認情報とredisの一致チェック
            String redisOrderVersion = redisTemplate.opsForValue().get(primaryKey + "version");
            if (!Objects.equals(redisOrderVersion, inputDto.getVersion())) {
                throw new BusinessException("2006",
                    ResultMessages.error().add("e.qr.ph.032", (Object) null));
            }

            // 他人操作チェック
            if (!redisLockUtil.lock(primaryKey, lockTime)) {
                throw new BusinessException("2002",
                    ResultMessages.error().add("e.qr.ph.032", (Object) null));
            }

            // 注文サマリテーブル登録＆更新処理
            // 注文テーブル登録処理
            // 注文明細テーブル登録処理
            // 注文明細オプッシュテーブル登録処理
            OOrder orderInfo = orderInsert(inputDto, CommonConstants.OPER_CD_MOBILE,
                OrderType.MOBILE.getCode());
            // 支払区分情報取得
            MControl control = controlRepository
                .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                    CODE_GROUP_PAYMENT, Flag.OFF.getCode());
            OrderOutputDto outputDto = beanMapper.map(orderInfo, OrderOutputDto.class);
            outputDto.setAdvancePayment(control.getControlCode());
            // 店舗情報取得
            MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());

            // 検索結果0件以外の場合、店員確認不要、後払払い、テーブルIDがある且つイートイン　又は テイクアウトの場合
            if (storeInfo != null && Objects
                .equals(StaffCheckFlag.NO.getCode(), storeInfo.getStaffCheck()) && Objects
                .equals(control.getControlCode(), PaymentType.DEFERRED_PAYMENT.getCode())
                && ((!Objects.isNull(inputDto.getTableId()) && Objects
                .equals(inputDto.getTakeoutFlag(), TakeoutFlag.EAT_IN.getCode())) || Objects
                .equals(inputDto.getTakeoutFlag(), TakeoutFlag.TAKE_OUT.getCode()))) {

                OPrintQueue orderData = new OPrintQueue();
                orderData.setStoreId(inputDto.getStoreId());
                orderData.setOrderSummaryId(orderInfo.getOrderSummaryId());
                orderData.setOrderId(orderInfo.getOrderId().toString());
                orderData.setPaymentAmount(inputDto.getOrderAmount());
                orderData.setPrintStatus(PrintStatus.UNPRINT.getCode());
                //1:後払い不要
                orderData.setStaffCheck("1");
                orderData.setDelFlag(Flag.OFF.getCode());
                orderData.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                ZonedDateTime dateTime = DateUtil.getNowDateTime();
                orderData.setInsDateTime(dateTime);
                orderData.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                orderData.setUpdDateTime(dateTime);
                orderData.setVersion(0);
                printQueueRepository.save(orderData);
            }

            return outputDto;
        } catch (Exception ex) {
            throw new BusinessException("2003",
                ResultMessages.error().add("e.qr.ph.035", (Object) null), ex);
        } finally {
            // ロック解除
            redisLockUtil.unlock(inputDto.getReceivablesId(), lockTime);
        }
    }

    /**
     * 注文確定のテーブル登録処理.
     *
     * @param inputDto  注文確定情報
     * @param userCd    登録更新者
     * @param orderType 登録更新者
     * @return 注文確定結果
     */
    private OOrder orderInsert(OrderInputDto inputDto, String userCd, String orderType) {
        try {

            // 欠品チェック
            List<MSellOff> sellOffList = sellOffRepository
                .findByStoreIdAndSellOffStartIsBeforeAndItemIdIsInAndDelFlag(
                    inputDto.getStoreId(), ZonedDateTime.now(),
                    inputDto.getItems().stream().map(OrderItemsDto::getItemId)
                        .collect(Collectors.toList()), Flag.OFF.getCode());

            if (CollectionUtils.isNotEmpty(sellOffList)) {
                throw new BusinessException("2004",
                    ResultMessages.error().add("e.qr.ph.034", (Object) null));
            }

            if (null != inputDto.getBuffetId() && StringUtils
                .isNotBlank(inputDto.getReceivablesId())) {

                // 放题明细
                MItem buffetInfo = itemRepository
                    .findByStoreIdAndItemIdAndDelFlag(inputDto.getStoreId(),
                        inputDto.getBuffetId(), Flag.OFF.getCode());

                // 注文时间
                Timestamp orderTime = orderDetailsRepository.getOrderTime(inputDto.getStoreId(),
                    inputDto.getReceivablesId(), inputDto.getBuffetId());

                LocalDateTime localDateTimeNoTimeZone = orderTime.toLocalDateTime();

                ZonedDateTime zonedOrderTime = localDateTimeNoTimeZone.atZone(
                    ZoneId.of(CommonConstants.TIMEZONE_TOKYO));

                // 剩余时间
                long timeDiff = Duration.between(DateUtil.getNowDateTime(),
                    zonedOrderTime.plusMinutes(buffetInfo.getBuffetTime())).getSeconds();

                if ((int) timeDiff / 60 <= 0) {

                    throw new BusinessException("2004",
                        ResultMessages.error().add("e.qr.ph.075", (Object) null));
                }
            }

            // 削除フラグ
            Integer delFlag = Flag.OFF.getCode();
            if (Objects.equals(PaymentType.ADVANCE_PAYMENT.getCode(), inputDto.getPaymentType())
                && 0 < BigDecimal.ZERO.compareTo(inputDto.getOrderAmount())) {
                delFlag = Flag.ON.getCode();
            }

            // 注文サマリテーブル登録
            OOrderSummary orderSummary = new OOrderSummary();
            // 店舗ID
            orderSummary.setStoreId(inputDto.getStoreId());
            // 受付id
            orderSummary.setReceivablesId(inputDto.getReceivablesId());
            // 削除フラグ
            orderSummary.setDelFlag(delFlag);
            // 受付時間を取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

            // 注文サマリテーブルに存在チェック
            String orderSummaryId = orderSummaryRepository
                .findByReceivablesId(orderSummary.getStoreId(), orderSummary.getReceivablesId());

            // 存在チェック
            if (StringUtils.isNotEmpty(orderSummaryId)) {
                if (Objects
                    .equals(PaymentType.DEFERRED_PAYMENT.getCode(), inputDto.getPaymentType())) {
                    // 更新処理
                    orderSummaryRepository.updateByPaymentAmountAndUpdDateTimeAndUpdOperCd(
                        orderSummary.getStoreId(),
                        orderSummary.getReceivablesId(),
                        inputDto.getOrderAmount(),
                        CommonConstants.OPER_CD_MOBILE, nowDateTime);
                }
            } else {

                // 登録処理
                OReceivables receivables = oreceivablesRepository
                    .findByStoreIdAndDelFlagAndReceivablesId(inputDto.getStoreId(),
                        Flag.OFF.getCode(), inputDto.getReceivablesId());

                // 受付ID取得
                orderSummaryId = itemInfoSharedService
                    .getReceivablesId(inputDto.getStoreId(), nowDateTime);
                // 注文サマリID
                orderSummary.setOrderSummaryId(orderSummaryId);
                // 顧客人数
                orderSummary.setCustomerCount(receivables.getCustomerCount());
                // テーブルID
                orderSummary.setTableId(inputDto.getTableId());
                // 注文額
                orderSummary.setOrderAmount(inputDto.getOrderAmount());
                // 支払額 スマホで注文確認する際に支払額がデフォルト「０」を設定する,会計するとき計算してください
                orderSummary.setPaymentAmount(BigDecimal.ZERO);
                // 支払区分
                orderSummary.setPaymentType(inputDto.getPaymentType());
                // テイクアウト区分
                orderSummary.setTakeoutFlag(
                    Objects.isNull(inputDto.getTakeoutFlag()) ? TakeoutFlag.EAT_IN.getCode()
                        : inputDto.getTakeoutFlag());
                // 注文状態
                if (inputDto.getTableId() == null) {
                    orderSummary.setOrderStatus(OrderStatus.TENTATIVE_ORDER.getCode());
                    orderSummary.setSeatRelease(Flag.ON.getCode().toString());
                } else {
                    orderSummary.setOrderStatus(OrderStatus.ORDER.getCode());
                    orderSummary.setSeatRelease(Flag.OFF.getCode().toString());
                }
                // 登録日時
                orderSummary.setInsDateTime(nowDateTime);
                // 登録者
                orderSummary.setInsOperCd(userCd);
                // 更新日時
                orderSummary.setUpdDateTime(nowDateTime);
                // 更新者
                orderSummary.setUpdOperCd(userCd);
                // バージョン
                orderSummary.setVersion(0);
                orderSummaryRepository.save(orderSummary);
            }

            // 注文テーブル登録
            OOrder order = new OOrder();
            // 店舗ID
            order.setStoreId(inputDto.getStoreId());
            // 注文サマリID
            order.setOrderSummaryId(orderSummaryId);
            // 削除フラグ
            order.setDelFlag(delFlag);

            // 注文IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_order"); // テーブル名
            getSeqNoInputDto.setItem("order_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 注文ID
            order.setOrderId(getSeqNo.getSeqNo());
            // 支払状態
            if (Objects.equals(inputDto.getPaymentType(), PaymentType.ADVANCE_PAYMENT.getCode())
                && BigDecimal.ZERO.compareTo(inputDto.getOrderAmount()) <= 0) {
                order.setPayStatus(PayStatus.PAY_ALREADY.getCode());
            } else {
                order.setPayStatus(PayStatus.PAY_NOT_ALREADY.getCode());
            }
            // 外税金額
            order.setForeignTax(inputDto.getForeignTax());
            // 注文日時
            order.setOrderTime(nowDateTime);
            // 注文区分
            order.setOrderType(OrderType.MOBILE.getCode());
            // 要望
            order.setComment(inputDto.getComment());
            // 登録日時
            order.setInsDateTime(nowDateTime);
            // 登録者
            order.setInsOperCd(userCd);
            // 更新日時
            order.setUpdDateTime(nowDateTime);
            // 更新者
            order.setUpdOperCd(userCd);
            // バージョン
            order.setVersion(0);
            orderRepository.save(order);

            // 商品状態
            String itemStatus = ItemStatus.UNCONFIRMED.getCode();

            // 店員注文の場合、確認不要
            if (Objects.equals(OrderType.PAD.getCode(), orderType) || Objects
                .equals(OrderType.FRONT.getCode(), orderType)) {
                // 商品状態
                itemStatus = ItemStatus.CONFIRMED.getCode();
            } else {
                // 店舗情報取得
                MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                    Flag.OFF.getCode());

                // 検索結果0件以外の場合
                if (storeInfo != null && Objects
                    .equals(StaffCheckFlag.NO.getCode(), storeInfo.getStaffCheck())) {
                    itemStatus = ItemStatus.CONFIRMED.getCode();
                }
            }

            for (OrderItemsDto orderItemsDto : inputDto.getItems()) {

                // 注文明細テーブル登録
                OOrderDetails orderDetails = new OOrderDetails();
                // 店舗ID
                orderDetails.setStoreId(inputDto.getStoreId());
                // 削除フラグ
                orderDetails.setDelFlag(delFlag);
                // 注文ID
                orderDetails.setOrderId(order.getOrderId());
                // 商品ID
                orderDetails.setItemId(orderItemsDto.getItemId());
                // 単価
                orderDetails.setItemPrice(
                    orderItemsDto.getItemPrice());
                // 商品個数
                orderDetails.setItemCount(Integer.valueOf(orderItemsDto.getItemCount()));
                // 商品区分
                orderDetails.setItemClassification(ItemType.NORMAL.getCode());
                // 商品状態
                orderDetails.setItemStatus(itemStatus);

                // 注文明細IDのシーケンスNo取得
                getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
                getSeqNoInputDto.setItem("order_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(userCd); // 登録更新者
                getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                //注文明細ID
                orderDetails.setOrderDetailId(getSeqNo.getSeqNo());
                // 登録日時
                orderDetails.setInsDateTime(nowDateTime);
                // 登録者
                orderDetails.setInsOperCd(userCd);
                // 更新日時
                orderDetails.setUpdDateTime(nowDateTime);
                // 更新者
                orderDetails.setUpdOperCd(userCd);
                // バージョン
                orderDetails.setVersion(0);
                orderDetails.setItemReturnId(null);
                orderDetailsRepository.save(orderDetails);

                // 注文明細オプションデータが存在の場合
                if (CollectionUtils.isNotEmpty(orderItemsDto.getOption())) {
                    for (OrderItemOptionDto orderItemOptionDto : orderItemsDto.getOption()) {
                        // 注文明細オプションテーブル登録
                        OOrderDetailsOption orderDetailsOption = new OOrderDetailsOption();
                        //店舗ID
                        orderDetailsOption.setStoreId(inputDto.getStoreId());
                        // 注文明細ID
                        orderDetailsOption.setOrderDetailId(orderDetails.getOrderDetailId());
                        // 商品オプション種類コード
                        orderDetailsOption
                            .setItemOptionTypeCode(orderItemOptionDto.getOptionTypeCd());
                        // 商品オプションコード
                        orderDetailsOption.setItemOptionCode(orderItemOptionDto.getOptionCode());
                        // 差額
                        orderDetailsOption.setDiffPrice(orderItemOptionDto.getOptionDiffPrice());
                        // 数量
                        orderDetailsOption.setItemOptionCount(orderItemOptionDto.getOptionCount());
                        // 削除フラグ
                        orderDetailsOption.setDelFlag(delFlag);
                        // 登録日時
                        orderDetailsOption.setInsDateTime(nowDateTime);
                        // 登録者
                        orderDetailsOption.setInsOperCd(userCd);
                        // 更新日時
                        orderDetailsOption.setUpdDateTime(nowDateTime);
                        // 更新者
                        orderDetailsOption.setUpdOperCd(userCd);
                        // バージョン
                        orderDetailsOption.setVersion(0);
                        orderDetailsOptionRepository.save(orderDetailsOption);
                    }
                }
            }

            return order;

        } catch (Exception ex) {
            throw new BusinessException("2003",
                ResultMessages.error().add("e.qr.ph.033", (Object) null), ex);
        }
    }

    /**
     * 注文情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文情報
     */
    @Override
    public GetOrderInfoOutputDto getOrderInfo(GetOrderInfoInputDto inputDto) {

        // 結果DTO初期化
        GetOrderInfoOutputDto outDto = new GetOrderInfoOutputDto();

        // 注文情報を取得する
        List<Map<String, Object>> orderData = orderSummaryRepository
            .findOrderInfoByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId(),
                inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderData)) {
            outDto.setPaidItemList(new ArrayList<>());
            outDto.setUnpaidItemList(new ArrayList<>());
            return outDto;
        }

        // 注文情報を編集する
        List<OrderItemDataDto> orderItemDataList = new ArrayList<>();
        orderData.forEach(stringObjectMap -> orderItemDataList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), OrderItemDataDto.class)));

        // 注文基本情報設定
        beanMapper.map(orderItemDataList.get(0), outDto);
        outDto.setOrderId(orderItemDataList.get(0).getOrderSummaryId());

        // 支払区分
        String paymentType = orderItemDataList.get(0).getPaymentType();

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderItemDataDto>> grpByOrderId = orderItemDataList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        // 商品情報リストを編集する
        List<OrderItemFullInfoDto> items = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDataDtos) -> {
            OrderItemFullInfoDto item = beanMapper
                .map(orderItemDataDtos.get(0), OrderItemFullInfoDto.class);
            orderItemDataDtos.forEach(orderItemDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDataDto.getOptionName())) {
                    String optionName = orderItemDataDto.getOptionName();
                    if (orderItemDataDto.getOptionItemCount() != null && 0 < orderItemDataDto
                        .getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDataDto.getOptionName(),
                            orderItemDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getOption())) {
                        item.setOption(optionName);
                    } else {
                        item.setOption(item.getOption() + "," + optionName);
                    }
                }
                if (ItemType.RETURNS.getCode().equals(orderItemDataDto.getItemClassification())) {
                    item.setItemPrice(item.getItemPrice().multiply(new BigDecimal("-1")));
                }
            });
            items.add(item);
        });

        // 支払済商品リストを編集する
        List<OrderItemFullInfoDto> paidItemFullList = items.stream()
            .filter(orderItemFullInfoDto -> Objects.equals(PayStatus.PAY_ALREADY.getCode(),
                orderItemFullInfoDto.getPayStatus()))
            .sorted(Comparator.comparing(OrderItemFullInfoDto::getOrderId,
                Comparator.nullsLast(String::compareTo))
                .thenComparing(OrderItemFullInfoDto::getItemId,
                    Comparator.nullsLast(String::compareTo))
                .thenComparing(OrderItemFullInfoDto::getOption,
                    Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList());

        // 未支払商品リストを編集する
        List<OrderItemFullInfoDto> unpaidItemFullList = items.stream()
            .filter(orderItemFullInfoDto -> Objects.equals(PayStatus.PAY_NOT_ALREADY.getCode(),
                orderItemFullInfoDto.getPayStatus()))
            .sorted(Comparator.comparing(OrderItemFullInfoDto::getOrderId,
                Comparator.nullsLast(String::compareTo))
                .thenComparing(OrderItemFullInfoDto::getItemId,
                    Comparator.nullsLast(String::compareTo))
                .thenComparing(OrderItemFullInfoDto::getOption,
                    Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList());

        // 未支払額を編集する
        BigDecimal unpaidPrice = unpaidItemFullList.stream()
            .map(orderItemFullInfoDto -> orderItemFullInfoDto.getItemPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 先払の場合
        BigDecimal paidPriceForeignTax = BigDecimal.ZERO;
        if (PaymentType.ADVANCE_PAYMENT.getCode().equals(paymentType)) {
            // 支払済外税金額を編集する
            Map<String, Optional<OrderItemFullInfoDto>> grpByOrderIdForTax = paidItemFullList
                .stream()
                .collect(Collectors.groupingBy(OrderItemFullInfoDto::getOrderId,
                    Collectors.maxBy(Comparator.comparing(OrderItemFullInfoDto::getForeignTax))));

            // 支払済外税金額を計算する
            List<BigDecimal> foreignTaxList = new ArrayList<>();
            grpByOrderIdForTax.forEach((s, orderItemFullInfoDto) -> {
                orderItemFullInfoDto
                    .ifPresent(
                        itemFullInfoDto -> foreignTaxList.add(itemFullInfoDto.getForeignTax()));
            });
            paidPriceForeignTax = foreignTaxList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            if (CollectionUtils.isNotEmpty(paidItemFullList)) {
                // 検索条件を設定する
                GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
                taxValueInputDto.setStoreId(inputDto.getStoreId());
                taxValueInputDto.setTakeoutFlag(orderItemDataList.get(0).getTakeoutFlag());
                List<ItemsDto> itemList = new ArrayList<>();
                paidItemFullList.forEach(orderItemFullInfoDto -> {
                    ItemsDto item = new ItemsDto();
                    item.setItemId(orderItemFullInfoDto.getItemId());
                    item.setItemPrice(orderItemFullInfoDto.getItemPrice());
                    itemList.add(item);
                });
                taxValueInputDto.setItemList(itemList);
                // 税情報を取得する
                GetTaxValueOutputDto outputDto = itemInfoSharedService
                    .getTaxValue(taxValueInputDto);
                paidPriceForeignTax = outputDto.getSotoTaxEight().add(outputDto.getSotoTaxTen());
            }
        }

        // 未支払外税金額を編集する
        BigDecimal unpaidPriceForeignTax = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(unpaidItemFullList)) {
            // 検索条件を設定する
            GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
            taxValueInputDto.setStoreId(inputDto.getStoreId());
            taxValueInputDto.setTakeoutFlag(orderItemDataList.get(0).getTakeoutFlag());
            List<ItemsDto> itemList = new ArrayList<>();
            unpaidItemFullList.forEach(orderItemFullInfoDto -> {
                ItemsDto item = new ItemsDto();
                item.setItemId(orderItemFullInfoDto.getItemId());
                item.setItemPrice(orderItemFullInfoDto.getItemPrice());
                itemList.add(item);
            });
            taxValueInputDto.setItemList(itemList);
            // 税情報を取得する
            GetTaxValueOutputDto outputDto = itemInfoSharedService.getTaxValue(taxValueInputDto);
            unpaidPriceForeignTax = outputDto.getSotoTaxEight().add(outputDto.getSotoTaxTen());
        }

        // 支払済商品リスト設定
        List<OrderItemInfoDto> paidItemList = new ArrayList<>();
        paidItemFullList.forEach(orderItemFullInfoDto -> paidItemList.add(beanMapper
            .map(orderItemFullInfoDto, OrderItemInfoDto.class)));
        outDto.setPaidItemList(paidItemList.stream().sorted(Comparator
            .comparing(OrderItemInfoDto::getItemClassification,
                Comparator.nullsLast(String::compareTo)).reversed()
            .thenComparing(OrderItemInfoDto::getItemId,
                Comparator.nullsLast(String::compareTo)).reversed())
            .collect(Collectors.toList()));

        // 未支払商品リスト設定
        List<OrderItemInfoDto> unpaidItemList = new ArrayList<>();
        unpaidItemFullList.forEach(orderItemFullInfoDto -> unpaidItemList.add(beanMapper
            .map(orderItemFullInfoDto, OrderItemInfoDto.class)));
        outDto.setUnpaidItemList(unpaidItemList.stream().sorted(Comparator
            .comparing(OrderItemInfoDto::getItemClassification,
                Comparator.nullsLast(String::compareTo)).reversed()
            .thenComparing(OrderItemInfoDto::getItemId,
                Comparator.nullsLast(String::compareTo)).reversed())
            .collect(Collectors.toList()));
        // 支払済金額を編集する
        BigDecimal paidPrice = paidItemFullList.stream().map(
            orderItemFullInfoDto -> orderItemFullInfoDto.getItemPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        // 小計金额設定
        outDto.setOrderAmount(paidPrice.add(unpaidPrice));

        // 外税金額設定
        outDto.setForeignTax(paidPriceForeignTax.add(unpaidPriceForeignTax));

        // 支払済金額設定
        outDto.setPaidPrice(paidPrice.add(paidPriceForeignTax));

        // 外税金額設定
        outDto.setUnpaidPrice(unpaidPrice.add(unpaidPriceForeignTax));

        return outDto;
    }

    /**
     * 共有注文情報取得.
     *
     * @param inputDto 取得条件
     * @return 共有注文情報
     */
    @Override
    public GetShareOrderInfoOutputDto getShareOrderInfo(GetShareOrderInfoInputDto inputDto) {

        // Redis保存keyを生成する
        String primaryKey = inputDto.getReceivablesId();

        // 共有データを取得
        List<String> shareData = redisTemplate.opsForList().range(primaryKey, 0, -1);

        // バージョン保存Keyを生成する
        String versionKey = inputDto.getReceivablesId() + "version";

        // バージョンを取得
        String version = redisTemplate.opsForValue().get(versionKey);

        // 結果DTO初期化
        GetShareOrderInfoOutputDto outDto = new GetShareOrderInfoOutputDto();

        // バージョンを設定
        outDto.setVersion(StringUtils.isEmpty(version) ? "0" : version);

        // データが存在しない場合
        if (CollectionUtils.isEmpty(shareData)) {
            outDto.setNoItems(new ArrayList<>());
            return outDto;
        }

        // データが存在する場合
        List<RedisNoItemsInfoDto> shareList = new ArrayList<>();
        shareData.forEach(s -> shareList.add(JSONObject.parseObject(s, RedisNoItemsInfoDto.class)));

        // 客の共有注文情報取得
        Map<String, List<RedisNoItemsInfoDto>> grpByNo = shareList.stream().collect(
            Collectors.groupingBy(RedisNoItemsInfoDto::getNo));

        // 客の商品情報リスト
        List<NoItemsInfoDto> noItems = new ArrayList<>();
        grpByNo.forEach((s, redisNoItemsInfoDtos) -> {
            NoItemsInfoDto noItemsInfoDto = new NoItemsInfoDto();
            noItemsInfoDto.setNo(s);
            List<ItemInfoDto> items = new ArrayList<>();
            redisNoItemsInfoDtos.forEach(redisNoItemsInfoDto -> {
                redisNoItemsInfoDto.getItems().forEach(itemInfoDto -> {
                    List<ItemOptionTypeDto> optionTypeList = new ArrayList<>();
                    itemInfoDto.getOptionTypeList().forEach(itemOptionTypeDto -> {
                        itemOptionTypeDto.getOptionList().forEach(itemOptionDto -> {
                            if (StringUtils.isNotEmpty(itemOptionDto.getOptionCode())
                                && itemOptionDto.getItemCount() == null) {
                                itemOptionDto.setItemCount(1);
                            }
                        });
                        if (StringUtils.isNotEmpty(itemOptionTypeDto.getOptionTypeName())
                            && StringUtils.isNotEmpty(itemOptionTypeDto.getClassification())) {
                            optionTypeList.add(itemOptionTypeDto);
                        }
                    });
                    itemInfoDto.setOptionTypeList(optionTypeList);
                });
                items.addAll(redisNoItemsInfoDto.getItems());
            });
            noItemsInfoDto.setItems(items.stream().sorted(Comparator
                .comparing(ItemInfoDto::getItemCategoryId, Comparator.nullsLast(String::compareTo))
                .thenComparing(ItemInfoDto::getVersion, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList()));
            noItems.add(noItemsInfoDto);
        });
        outDto.setNoItems(noItems.stream().sorted(Comparator.comparing(NoItemsInfoDto::getNo,
            (o1, o2) -> {
                if (StringUtils.isEmpty(o1) || StringUtils.isEmpty(o2)) {
                    return -1;
                } else {
                    return Objects
                        .compare(Integer.valueOf(o1), Integer.valueOf(o2), Integer::compareTo);
                }
            })).collect(Collectors.toList()));

        return outDto;
    }

    /**
     * 注文状態を変更する.
     *
     * @param inputDto 取得条件
     */
    @Override
    public Integer changeStatus(WeChatAliPayBackInputDto inputDto) {

        try {

            // 店舗ID
            String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNum().substring(0, 20);

            // 注文ID
            Integer orderId = Integer
                .valueOf(inputDto.getOrderNum().substring(20, inputDto.getOrderNum().length() - 2));

            // 注文金額
            BigDecimal paymentAmount = new BigDecimal(
                inputDto.getTransAmt().replaceAll("^(0+)", ""));

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 支払い金額を変更する
            Integer updateCount = orderSummaryRepository
                .updatePaymentAmountWithDelFlag(storeId, orderSummaryId, "TRIO", dateTime);

            // 更新０件の場合
            if (updateCount == 0) {
                orderSummaryRepository.updatePaymentAmount(storeId, orderSummaryId, paymentAmount,
                    "TRIO", dateTime);
            }

            // 注文状態を変更する
            orderRepository.updateStatusWithDelFlag(storeId, orderSummaryId, orderId,
                CodeConstants.PayStatus.PAY_ALREADY.getCode(), "TRIO", dateTime);

            // 注文明細状態更新
            orderDetailsRepository.updateDelFlagByOrderId(storeId, orderId, "TRIO", dateTime);

            // 注文明細オプション状態更新
            orderDetailsOptionRepository.updateDelFlagByOrderId(storeId, orderId, "TRIO", dateTime);

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
     * @param inputDto 取得条件
     */
    @Override
    public void revertOrder(WeChatAliPayBackInputDto inputDto) {

        try {

            // 店舗ID
            String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNum().substring(0, 20);

            // 注文ID
            Integer orderId = Integer
                .valueOf(inputDto.getOrderNum().substring(20, inputDto.getOrderNum().length() - 2));

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
     * 注文商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文商品情報
     */
    @Override
    public GetItemDetailOutputDto getOrderItemDetail(GetItemDetailInputDto inputDto) {

        // 結果DTO初期化
        GetItemDetailOutputDto outDto = new GetItemDetailOutputDto();

        Integer itemCount = 0;

        // 商品オプション情報取得の場合
        if (StringUtils.isEmpty(inputDto.getReceivablesId())) {
            // 商品オプション情報取得
            List<ItemDetailOptionTypeDto> optionTypeList = new ArrayList<>();
            // 検索条件を設定する
            GetItemOptionInfoInputDto itemOptionInfoInputDto = beanMapper
                .map(inputDto, GetItemOptionInfoInputDto.class);

            // 商品オプション類型情報取得
            GetItemOptionInfoOutputDto outputDto = itemInfoSharedService
                .getItemOptionInfo(itemOptionInfoInputDto);

            // 商品オプション情報編集
            outputDto.getOptionTypeList().forEach(itemOptionTypeDto -> {
                ItemDetailOptionTypeDto optionTypeDto = new ItemDetailOptionTypeDto();

                if (!Objects
                    .equals(Flag.ON.getCode().toString(), inputDto.getBuffetItemFlag())) {
                    optionTypeDto.setOptionTypeCd(itemOptionTypeDto.getOptionTypeCd());
                    optionTypeDto.setOptionTypeName(itemOptionTypeDto.getOptionTypeName());
                    optionTypeDto.setClassification(itemOptionTypeDto.getClassification());
                    List<ItemDetailOptionDto> optionList = new ArrayList<>();
                    itemOptionTypeDto.getOptionList().forEach(itemOptionDto -> {
                        ItemDetailOptionDto optionDto = new ItemDetailOptionDto();
                        optionDto.setOptionCode(itemOptionDto.getOptionCode());
                        optionDto.setOptionName(itemOptionDto.getOptionName());
                        optionDto.setDiffPrice(itemOptionDto.getDiffPrice());
                        optionList.add(optionDto);
                    });

                    optionTypeDto.setOptionList(optionList);
                }

                optionTypeList.add(optionTypeDto);
            });

            outDto.setOptionTypeList(optionTypeList);
            return outDto;
        }

        // 注文商品詳細情報取得
        List<ItemDetailDto> itemDetailList = orderSummaryRepository
            .findOrderItemDetailByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId(),
                inputDto.getOrderId(), inputDto.getOrderDetailId(), inputDto.getItemId());

        // データが存在しない場合
        if (CollectionUtils.isEmpty(itemDetailList)) {
            outDto.setOptionTypeList(new ArrayList<>());
            return outDto;
        }

        // 商品情報設定
        outDto.setItemName(JSONObject.parseObject(itemDetailList.get(0).getItemName())
            .getString(inputDto.getLanguages())); // 商品名
        outDto.setItemCount(itemDetailList.get(0).getItemCount()); // 商品数量
        outDto.setItemInfo(JSONObject.parseObject(itemDetailList.get(0).getItemInfo())
            .getString(inputDto.getLanguages())); // 商品備考

        // 注文商品返品数量情報取得
        Integer itemReturnCount = orderSummaryRepository
            .findOrderItemReturnCountByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(), inputDto.getOrderId(), inputDto.getOrderDetailId(),
                inputDto.getItemId());

        // 商品実際注文数量設定
        int itemRealCount = outDto.getItemCount() - (itemReturnCount == null ? 0 : itemReturnCount);
        outDto.setItemRealCount(Math.max(itemRealCount, 0));
        outDto.setOptionTypeList(new ArrayList<>());

        // 商品オプション情報設定
        if (Objects.equals(Flag.ON.getCode().toString(), itemDetailList.get(0).getOptionFlag())) {
            // 商品オプション情報取得
            // 検索条件を設定する
            GetItemOptionInfoInputDto itemOptionInfoInputDto = beanMapper
                .map(inputDto, GetItemOptionInfoInputDto.class);

            // 商品オプション類型情報取得
            GetItemOptionInfoOutputDto outputDto = itemInfoSharedService
                .getItemOptionInfo(itemOptionInfoInputDto);

            // 商品オプション情報編集
            List<ItemDetailOptionTypeDto> optionTypeList = new ArrayList<>();
            outputDto.getOptionTypeList().forEach(itemOptionTypeDto -> {
                ItemDetailOptionTypeDto optionTypeDto = new ItemDetailOptionTypeDto();

                if (!Objects.equals(Flag.ON.getCode().toString(), inputDto.getBuffetItemFlag())) {
                    optionTypeDto.setOptionTypeCd(itemOptionTypeDto.getOptionTypeCd());
                    optionTypeDto.setOptionTypeName(itemOptionTypeDto.getOptionTypeName());
                    optionTypeDto.setClassification(itemOptionTypeDto.getClassification());
                    List<ItemDetailOptionDto> optionList = new ArrayList<>();
                    itemOptionTypeDto.getOptionList().forEach(itemOptionDto -> {
                        ItemDetailOptionDto optionDto = new ItemDetailOptionDto();
                        optionDto.setOptionCode(itemOptionDto.getOptionCode());
                        optionDto.setOptionName(itemOptionDto.getOptionName());
                        optionDto.setDiffPrice(itemOptionDto.getDiffPrice());
                        optionDto.setOptionNumber(0);
                        Optional<ItemDetailDto> optionalData = itemDetailList.stream()
                            .filter(itemDetailDto -> Objects.equals(itemDetailDto.getOptionTypeCd(),
                                optionTypeDto.getOptionTypeCd()) && Objects
                                .equals(itemDetailDto.getOptionCode(), optionDto.getOptionCode()))
                            .findFirst();
                        optionalData.ifPresent(itemDetailDto -> {
                            if (Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                                optionTypeDto.getClassification())) {
                                optionDto.setOptionNumber(itemDetailDto.getOptionNumber());
                            }
                            optionDto.setSelectionStatus("1");
                        });
                        optionList.add(optionDto);
                    });
                    optionTypeDto.setOptionList(optionList);
                }

                optionTypeList.add(optionTypeDto);
            });
            outDto.setOptionTypeList(optionTypeList);
        }

        return outDto;
    }

    /**
     * 注文状態確認.
     *
     * @param inputDto 注文確認データ
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OOrderSummary sureOrderItem(SureOrderItemInputDto inputDto) {

        // 注文サマリーデータをロックする
        OOrderSummary orderSummary = orderSummaryRepository
            .findByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
                inputDto.getReceivablesId(), Flag.OFF.getCode());

        // ユーザID
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        if (!Objects.isNull(inputDto.getItemList()) && inputDto.getItemList().size() > 0) {
            for (UnCfmItemInfoDto unCfmItemInfoDto : inputDto.getItemList()) {
                // 注文状態を変更する
                orderDetailsRepository.updateItemDetails(inputDto.getStoreId(),
                    Integer.valueOf(unCfmItemInfoDto.getOrderDetailId()),
                    unCfmItemInfoDto.getItemCount(),
                    unCfmItemInfoDto.getItemPrice(),
                    userOperCd, ZonedDateTime.now());
            }

            // 当オーダーの小計金額取得
            List<Map<String, Object>> orderAmountMapList = orderSummaryRepository
                .findOrderItemsChange(inputDto.getStoreId(), orderSummary.getOrderSummaryId());

            List<OrderAmountDto> orderAmountList = new ArrayList<>();
            orderAmountMapList.forEach(stringObjectMap -> orderAmountList.add(
                JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                    OrderAmountDto.class)));
            //小計金額（返品含める）
            BigDecimal itemPrice = new BigDecimal(orderAmountList.stream()
                .mapToLong(orderAmountDto -> orderAmountDto.getItemPrice().longValue()).sum());

            //返品金額
            List<OrderAmountDto> orderAmountTempList = new ArrayList<>();
            orderAmountTempList = orderAmountList.stream()
                .filter(s -> !Objects.isNull(s.getReturnOrderDetailId())).collect(
                    Collectors.toList());
            BigDecimal itemTempPrice = BigDecimal.ZERO;
            if (!Objects.isNull(orderAmountTempList) && orderAmountTempList.size() > 0) {
                itemTempPrice = new BigDecimal(orderAmountTempList.stream()
                    .mapToLong(orderAmountDto -> orderAmountDto.getItemPrice().longValue()).sum());
            }

            orderSummaryRepository
                .updateOrderChangeAmount(inputDto.getStoreId(), orderSummary.getOrderSummaryId(),
                    itemPrice.subtract(itemTempPrice).subtract(itemTempPrice), userOperCd,
                    ZonedDateTime.now());
        }
        // 注文状態を変更する
        orderDetailsRepository.updateItemStatusByReceivablesId(inputDto.getStoreId(),
            inputDto.getReceivablesId(), ItemStatus.CONFIRMED.getCode(),
            inputDto.getOrderIdList(),
            userOperCd, ZonedDateTime.now());

        return orderSummaryRepository
            .findSummaryInfo(inputDto.getStoreId(), orderSummary.getOrderSummaryId());
    }


    /**
     * 注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 注文商品情報
     */
    @Override
    public GetOrderListOutputDto getOrderList(GetOrderListInputDto inputDto, Pageable pageable) {

        // 支払状態
        String payStatus = StringUtils.EMPTY;
        String itemStatus = StringUtils.EMPTY;
        if (Objects.equals(OrderShowStatus.UNCONFIRMED.getCode(), inputDto.getOrderStatus())) {
            itemStatus = ItemStatus.UNCONFIRMED.getCode();
        } else if (Objects
            .equals(OrderShowStatus.PAY_ALREADY.getCode(), inputDto.getOrderStatus())) {
            payStatus = PayStatus.PAY_ALREADY.getCode();
            itemStatus = ItemStatus.CONFIRMED.getCode();
        } else if (Objects
            .equals(OrderShowStatus.PAY_NOT_ALREADY.getCode(), inputDto.getOrderStatus())) {
            payStatus = PayStatus.PAY_NOT_ALREADY.getCode();
            itemStatus = ItemStatus.CONFIRMED.getCode();
        }

        // 検索終了日付設定
        String orderDateEnd = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(inputDto.getOrderDateEnd())) {
            orderDateEnd = inputDto.getOrderDateEnd() + " 23:59:59";
        }

        // 注文一覧情報取得
        Page<Map<String, Object>> orderListMap = orderSummaryRepository
            .findOrderInfoByStoreId(inputDto.getStoreId(),
                inputDto.getReceivablesNo() == null ? 0 : inputDto.getReceivablesNo(),
                StringUtils.trimToEmpty(inputDto.getOrderDateStart()), orderDateEnd,
                StringUtils.trimToEmpty(payStatus), StringUtils.trimToEmpty(itemStatus),
                inputDto.getTakeoutFlag(), pageable);

        // 注文一覧情報編集
        List<OrderInfoDto> orderList = new ArrayList<>();
        orderListMap.forEach(stringObjectMap -> {
            OrderInfoDto orderInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), OrderInfoDto.class);
            // 注文状態編集
            if (Objects.equals(ItemStatus.UNCONFIRMED.getCode(), orderInfo.getItemStatus())) {
                orderInfo.setOrderStatus(OrderShowStatus.UNCONFIRMED.getCode());
            } else if (Objects
                .equals(PayStatus.PAY_ALREADY.getCode(), orderInfo.getOrderStatus())) {
                orderInfo.setOrderStatus(OrderShowStatus.PAY_ALREADY.getCode());
            } else {
                orderInfo.setOrderStatus(OrderShowStatus.PAY_NOT_ALREADY.getCode());
            }
            orderList.add(orderInfo);
        });

        for (OrderInfoDto orderItem : orderList) {
            // 後払の場合
            if (Objects
                .equals(PaymentType.DEFERRED_PAYMENT.getCode(), orderItem.getPaymentType())) {
                // 注文商品情報取得
                List<Map<String, Object>> orderItemListMap = orderSummaryRepository
                    .findOrderItemsByOrderSummaryId(inputDto.getStoreId(),
                        orderItem.getOrderSummaryId());
                // 検索結果0件の場合
                if (CollectionUtils.isEmpty(orderItemListMap)) {
                    continue;
                }
                // 検索条件を設定する
                List<OrderItemDto> orderItemList = new ArrayList<>();
                orderItemListMap.forEach(stringObjectMap -> orderItemList.add(
                    JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                        OrderItemDto.class)));

                GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
                taxValueInputDto.setStoreId(inputDto.getStoreId());
                taxValueInputDto.setTakeoutFlag(orderItemList.get(0).getTakeoutFlag());
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
                taxValueInputDto.setItemList(itemList);
                // 税情報を取得する
                GetTaxValueOutputDto outputDto = itemInfoSharedService
                    .getTaxValue(taxValueInputDto);
                orderItem.setOrderSototaxAmount(
                    outputDto.getSotoTaxEight().add(outputDto.getSotoTaxTen()));
                orderItem.setOrderTotalAmount(
                    orderItem.getOrderSubtotal().add(orderItem.getOrderSototaxAmount()));
            }
        }

        // 結果DTO初期化
        GetOrderListOutputDto outDto = new GetOrderListOutputDto();

        // 注文一覧情報設定
        outDto.setOrderList(
            new PageImpl<>(orderList, orderListMap.getPageable(), orderListMap.getTotalPages()));

        // 注文総件数
        outDto.setOrderCount(orderListMap.getTotalElements());

        return outDto;
    }

    /**
     * 返品原因情報取得.
     *
     * @param inputDto 取得条件
     * @return 返品原因情報
     */
    @Override
    public GetReturnReasonOutputDto getReturnReasonList(GetReturnReasonInputDto inputDto) {

        // 返品原因情報取得
        List<Map<String, Object>> returnReasonList = orderDetailsRepository
            .getReturnReasonList(inputDto.getStoreId(), inputDto.getLanguages());

        // 結果DTO初期化
        GetReturnReasonOutputDto outDto = new GetReturnReasonOutputDto();

        // 返品原因リストを設定する
        List<GetReturnReasonDto> returnReasons = new ArrayList<>();

        returnReasonList.forEach(stringObjectMap -> returnReasons.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetReturnReasonDto.class)));

        outDto.setReturnReason(returnReasons);

        return outDto;
    }

    /**
     * 人数変更.
     *
     * @param inputDto 人数変更データ
     */
    @Override
    public void changeCustomerCount(ChangeCustomerCountInputDto inputDto) {

        // 注文サマリーデータをロックする
        orderSummaryRepository.findByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
            inputDto.getReceivablesId(), Flag.OFF.getCode());

        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 座席データを変更する
        orderSummaryRepository.updateCustomerCount(inputDto.getStoreId(),
            inputDto.getReceivablesId(), inputDto.getCustomerPeopleCount(), userOperCd,
            ZonedDateTime.now());

        receivablesRepository.updateByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
            inputDto.getReceivablesId(), inputDto.getCustomerPeopleCount());
    }

    /**
     * 返品登録.
     *
     * @param inputDto 返品登録データ
     */
    @Override
    public RegistReturnOutputDto registReturn(RegistReturnInputDto inputDto) {

        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 注文IDのシーケンスNo取得
        GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
        getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
        getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
        getSeqNoInputDto.setItem("order_detail_id"); // 項目
        getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_STORE_PAD); // 登録更新者
        GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

        // 注文明細を取得する
        Map<String, Object> orderDetailMap = orderDetailsRepository
            .findOrderDetailIdByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(), inputDto.getOrderId(), inputDto.getOrderDetailId());
        // 注文明細テーブル
        OOrderDetails orderDetail = JSONObject
            .parseObject(JSONObject.toJSONString(orderDetailMap), OOrderDetails.class);

        // 返品元注文明細ID
        orderDetail.setReturnOrderDetailId(orderDetail.getOrderDetailId());
        // 注文明細ID
        orderDetail.setOrderDetailId(getSeqNo.getSeqNo());
        // 商品個数
        orderDetail.setItemCount(inputDto.getReturnCount());
        orderDetail.setItemPrice(new BigDecimal(orderDetailMap.get("itemPrice").toString())
            .divide(new BigDecimal(orderDetailMap.get("itemCount").toString()))
            .multiply(new BigDecimal(inputDto.getReturnCount().toString())));
        // 商品区分
        orderDetail.setItemClassification(ItemType.RETURNS.getCode());
        // 商品状態
        orderDetail.setItemStatus(ItemStatus.CONFIRMED.getCode());
        // 返品原因コード
        orderDetail.setItemReturnId(inputDto.getReturnReasonCode());
        // 削除フラグ
        orderDetail.setDelFlag(Flag.OFF.getCode());
        // 登録者
        orderDetail.setInsOperCd(userOperCd);
        // 登録日時
        orderDetail.setInsDateTime(ZonedDateTime.now());
        // 更新者
        orderDetail.setUpdOperCd(userOperCd);
        // 更新日時
        orderDetail.setUpdDateTime(ZonedDateTime.now());
        // バージョン
        orderDetail.setVersion(0);

        // 注文明細データを登録する
        orderDetailsRepository.save(orderDetail);

        // 注文サマリの金額変更
        orderSummaryRepository
            .updateOrderAmount(inputDto.getStoreId(),
                orderDetailMap.get("orderSummaryId").toString(),
                orderDetail.getItemPrice(),
                userOperCd,
                ZonedDateTime.now());

        // 注文明細返品以外金額を取得する
        BigDecimal orderPaymentAmount = orderDetailsRepository
            .findItemCountByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(), inputDto.getOrderId());
        // 注文明細返品金額を取得する
        BigDecimal orderReturnPaymentAmount = orderDetailsRepository
            .findItemReturnCountByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(), inputDto.getOrderId());

        // 該当注文の全ての商品が返品される場合、支払状態を変更する
        RegistReturnOutputDto outputDto = new RegistReturnOutputDto();
        outputDto.setOrderSummaryId(orderDetailMap.get("orderSummaryId").toString());
        outputDto.setOrderDetailId(orderDetail.getOrderDetailId());
        return outputDto;
    }

    /**
     * 注文商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 注文商品リスト
     */
    @Override
    public GetOrderItemListOutputDto getOrderItemList(GetOrderItemListInputDto inputDto) {

        // 結果DTO初期化
        GetOrderItemListOutputDto outDto = new GetOrderItemListOutputDto();

        // 注文商品リストを取得する
        List<Map<String, Object>> orderItemData = orderSummaryRepository
            .findOrderItemListByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId(),
                inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderItemData)) {
            outDto.setItemList(new ArrayList<>());
            return outDto;
        }

        // 注文商品リストを編集する
        List<OrderItemDetailDataDto> orderItemList = new ArrayList<>();
        orderItemData.forEach(stringObjectMap -> orderItemList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                OrderItemDetailDataDto.class)));

        // 注文基本情報設定
        beanMapper.map(orderItemList.get(0), outDto);

        // 未確認商品取得
        Optional<OrderItemDetailDataDto> unConItem = orderItemList.stream().filter(
            orderItemDetailDataDto -> Objects.equals(ItemStatus.UNCONFIRMED.getCode(),
                orderItemDetailDataDto.getItemSureStatus())).findFirst();

        // 注文状態設定
        if (unConItem.isPresent()) {
            outDto.setOrderStatus(ItemStatus.UNCONFIRMED.getCode());
        } else {
            outDto.setOrderStatus(ItemStatus.CONFIRMED.getCode());
        }

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderItemDetailDataDto>> grpByOrderId = orderItemList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        // 商品情報リストを編集する
        List<OrderItemDetailInfoDto> itemList = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDetailDataDtos) -> {
            OrderItemDetailInfoDto item = beanMapper
                .map(orderItemDetailDataDtos.get(0), OrderItemDetailInfoDto.class);
            orderItemDetailDataDtos.forEach(orderItemDetailDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDetailDataDto.getOptionName())) {
                    String optionName = orderItemDetailDataDto.getOptionName();
                    if (orderItemDetailDataDto.getOptionItemCount() != null
                        && 0 < orderItemDetailDataDto.getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDetailDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDetailDataDto.getOptionName(),
                            orderItemDetailDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getItemOption())) {
                        item.setItemOption(optionName);
                    } else {
                        item.setItemOption(item.getItemOption() + "," + optionName);
                    }
                }
            });
            itemList.add(item);
        });

        // 商品リスト設定
        outDto.setItemList(itemList.stream().sorted(Comparator
            .comparing(OrderItemDetailInfoDto::getOrderId, Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList()));

        // 配席のみを除く
        List<OrderItemDetailInfoDto> itemOrderList = new ArrayList<>();
        outDto.getItemList().forEach(orderItemDetailInfoDto -> {
            if (StringUtils.isNotEmpty(orderItemDetailInfoDto.getOrderId())) {
                itemOrderList.add(orderItemDetailInfoDto);
            }
        });
        outDto.setItemList(itemOrderList.stream().sorted(Comparator
            .comparing(OrderItemDetailInfoDto::getItemClassification,
                Comparator.nullsLast(String::compareTo)).reversed()
            .thenComparing(OrderItemDetailInfoDto::getItemId,
                Comparator.nullsLast(String::compareTo)).reversed())
            .collect(Collectors.toList()));

        // 現金支払金額取得
        BigDecimal paymentAmount = paymentDetailRepository
            .getCashPayAmountByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId(),
                AccountsType.CASH.getCode());

        // 支払方式設定
        if (paymentAmount != null && BigDecimal.ZERO.compareTo(paymentAmount) < 0) {
            outDto.setAccountsType(AccountsType.CASH.getCode());
        }

        OOrderSummary orderSummary = orderSummaryRepository.findByStoreIdAndReceivablesIdAndDelFlag(
            inputDto.getStoreId(), inputDto.getReceivablesId(), Flag.OFF.getCode());

        if (null != orderSummary.getPaymentAmount()
            && orderSummary.getPaymentAmount().compareTo(BigDecimal.ZERO) != 0) {

            outDto.setPaymentDistinction("01");
        }

        return outDto;
    }

    /**
     * 会計PAD　注文確定.
     *
     * @param inputDto 画面項目
     */
    @Override
    public OrderOutputDto registOrder(OrderInputDto inputDto) {
        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 受付IDしない場合
        if (StringUtils.isEmpty(inputDto.getReceivablesId())) {

            // 指定店舗情報取得
            MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());

            // 検索結果0件の場合
            if (Objects.isNull(store)) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.003", (Object) null));
            }

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

            // 受付番号
            Integer receptionNo = oreceivablesRepository
                .getReceptionNoByReceptionTime(inputDto.getStoreId(), startTime, endTime);

            // 受付ID取得
            String receivablesId = itemInfoSharedService
                .getReceivablesId(inputDto.getStoreId(), nowDateTime);

            // 受付情報登録
            OReceivables receivables = new OReceivables();
            receivables.setStoreId(inputDto.getStoreId()); // 店舗ID
            receivables.setReceivablesId(receivablesId); // 受付ID
            receivables.setReceptionNo(receptionNo); // 受付番号
            receivables.setCustomerCount(inputDto.getCustomerCount()); // 顧客人数
            receivables.setReceptionTime(nowDateTime); // 受付日時
            receivables.setInsDateTime(nowDateTime); // 登録日時
            receivables.setInsOperCd(userOperCd); // 登録者
            receivables.setUpdDateTime(nowDateTime); // 更新日時
            receivables.setUpdOperCd(userOperCd); // 更新者
            receivables.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
            receivables.setVersion(0); // バージョン
            oreceivablesRepository.save(receivables);
            inputDto.setReceivablesId(receivablesId);
        }

        // 支払区分
        inputDto.setPaymentType(PaymentType.DEFERRED_PAYMENT.getCode());
        // 外税金額
        inputDto.setForeignTax(BigDecimal.ZERO);
        // 商オップシュ金額
        inputDto.getItems().forEach(orderItemsDto -> {
            if (orderItemsDto.getAttributeTotalMoney() == null) {
                orderItemsDto.setAttributeTotalMoney(BigDecimal.ZERO);
            }
        });

        // 注文サマリテーブル登録＆更新処理
        // 注文テーブル登録処理
        // 注文明細テーブル登録処理
        // 注文明細オプッシュテーブル登録処理
        OOrder order = orderInsert(inputDto, userOperCd, OrderType.PAD.getCode());
        OrderOutputDto outputDto = new OrderOutputDto();
        outputDto.setOrderSummaryId(order.getOrderSummaryId());
        outputDto.setOrderId(order.getOrderId());

        // 支払区分情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PAY_LATER, Flag.OFF.getCode());
        outputDto.setStaffAccountAbleFlag(Flag.OFF.getCode());
        if (control != null && Objects
            .equals(Flag.ON.getCode().toString(), control.getControlCode())) {
            outputDto.setStaffAccountAbleFlag(Flag.ON.getCode());

        }
        return outputDto;
    }

    /**
     * 配席.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void setTable(SetTableInputDto inputDto) {

        try {

            // ユーザID取得
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }

            // 注文サマリテーブルに存在チェック
            String orderSummaryId = orderSummaryRepository
                .findByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId());

            // 受付時間を取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

            // 存在チェック
            if (StringUtils.isNotEmpty(orderSummaryId)) {

                // 更新処理
                orderSummaryRepository
                    .updateTable(inputDto.getStoreId(), orderSummaryId,
                        inputDto.getTableId().get(0).getTableId(),
                        OrderStatus.ORDER.getCode(), Flag.OFF.getCode().toString(), userOperCd,
                        nowDateTime);
            } else {

                // 受付ID取得
                orderSummaryId = itemInfoSharedService
                    .getReceivablesId(inputDto.getStoreId(), nowDateTime);
                // 注文サマリテーブル登録
                OOrderSummary orderSummary = new OOrderSummary();
                // 店舗ID
                orderSummary.setStoreId(inputDto.getStoreId());
                // 受付id
                orderSummary.setReceivablesId(inputDto.getReceivablesId());
                // 削除フラグ
                orderSummary.setDelFlag(Flag.OFF.getCode());
                // 注文サマリID
                orderSummary.setOrderSummaryId(orderSummaryId);
                // 登録処理
                OReceivables receivables = oreceivablesRepository
                    .findByStoreIdAndDelFlagAndReceivablesId(inputDto.getStoreId(),
                        Flag.OFF.getCode(), inputDto.getReceivablesId());
                // 顧客人数
                orderSummary.setCustomerCount(receivables.getCustomerCount());
                // テーブルID
                orderSummary.setTableId(inputDto.getTableId().get(0).getTableId());
                // 注文額
                orderSummary.setOrderAmount(BigDecimal.ZERO);
                // 値引額
                orderSummary.setPriceDiscountAmount(null);
                // 値引率
                orderSummary.setPriceDiscountRate(null);
                // 支払額 スマホで注文確認する際に支払額がデフォルト「０」を設定する,会計するとき計算してください
                orderSummary.setPaymentAmount(BigDecimal.ZERO);
                // 支払区分情報取得
                MControl control = controlRepository
                    .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                        CODE_GROUP_PAYMENT, Flag.OFF.getCode());
                // 支払区分
                orderSummary.setPaymentType(control.getControlCode());
                // テイクアウト区分
                orderSummary.setTakeoutFlag(TakeoutFlag.EAT_IN.getCode());
                // 注文状態
                orderSummary.setOrderStatus(OrderStatus.ORDER.getCode());
                // 席解除フラグ
                if (inputDto.getTableId() != null) {
                    orderSummary.setSeatRelease(Flag.OFF.getCode().toString());
                } else {
                    orderSummary.setSeatRelease(Flag.ON.getCode().toString());
                }
                // 登録日時
                orderSummary.setInsDateTime(nowDateTime);
                // 登録者
                orderSummary.setInsOperCd(userOperCd);
                // 更新日時
                orderSummary.setUpdDateTime(nowDateTime);
                // 更新者
                orderSummary.setUpdOperCd(userOperCd);
                // バージョン
                orderSummary.setVersion(0);
                orderSummaryRepository.save(orderSummary);
            }

            // テイクアウト区分設定
            if (inputDto.getTableId() != null) {
                // テイクアウト区分保存Keyを生成する
                String takeoutFlagKey = inputDto.getReceivablesId() + "takeoutFlag";
                // テイクアウト区分設定
                redisTemplate.opsForValue().set(takeoutFlagKey, TakeoutFlag.EAT_IN.getCode());
                redisTemplate.expire(takeoutFlagKey, 10800L, TimeUnit.SECONDS);
            }

            if (CollectionUtils.isNotEmpty(inputDto.getTableId())) {
                List<RTable> tableReceivablesList = new ArrayList<>();
                GetSeqNoOutputDto getNo = new GetSeqNoOutputDto();
                if (inputDto.getTableId().size() > 1) {
                    // 注文明細IDのシーケンスNo取得
                    GetSeqNoInputDto getSeqNo = new GetSeqNoInputDto();
                    getSeqNo.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqNo.setTableName("r_table"); // テーブル名
                    getSeqNo.setItem("group_id"); // 項目
                    getSeqNo.setOperCd(userOperCd); // 登録更新者
                    getNo = itemInfoSharedService.getSeqNo(getSeqNo);
                    RTable tableReceivables = new RTable();
                    tableReceivables.setGroupId(getNo.getSeqNo());
                    tableReceivables.setStoreId(inputDto.getStoreId());
                    tableReceivables.setTableId(inputDto.getTableId().get(0).getTableId());
                    tableReceivables.setReceivablesId(inputDto.getReceivablesId());
                    tableReceivables.setRealReceivablesId(inputDto.getReceivablesId());
                    tableReceivables.setDelFlag(Flag.OFF.getCode());
                    tableReceivables.setInsDateTime(nowDateTime);
                    tableReceivables.setInsOperCd(userOperCd);
                    tableReceivables.setUpdDateTime(nowDateTime);
                    tableReceivables.setUpdOperCd(userOperCd);
                    tableReceivables.setVersion(0);
                    tableReceivablesList.add(tableReceivables);
                }

                List<Integer> tableIdList = new ArrayList<>();
                for (int i = 1; i < inputDto.getTableId().size(); i++) {
                    tableIdList.add(inputDto.getTableId().get(i).getTableId());
                }

                List<TableSeatDto> tableInfoList = tableRepository
                    .getTableSeatList(inputDto.getStoreId(),
                        tableIdList);

                String otherReceivablesId = "";
                Integer otherReceptionNo = null;

                // 指定店舗情報取得
                MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                    Flag.OFF.getCode());

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

                // 支払区分情報取得
                MControl control = controlRepository
                    .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                        CommonConstants.CODE_GROUP_PAYMENT, Flag.OFF.getCode());

                for (TableSeatDto tableSeatDto : tableInfoList) {
                    // 受付番号
                    otherReceptionNo = receivablesRepository
                        .getReceptionNoByReceptionTime(inputDto.getStoreId(), startTime, endTime);

                    // 受付ID取得
                    otherReceivablesId = itemInfoSharedService
                        .getReceivablesId(inputDto.getStoreId(), nowDateTime);

                    // 受付情報登録
                    OReceivables receivablesEntity = new OReceivables();
                    receivablesEntity.setStoreId(inputDto.getStoreId()); // 店舗ID
                    receivablesEntity.setReceivablesId(otherReceivablesId); // 受付ID
                    receivablesEntity.setReceptionNo(otherReceptionNo); // 受付番号
                    receivablesEntity.setCustomerCount(tableSeatDto.getTableSeatCount()); // 顧客人数
                    receivablesEntity.setReceptionTime(nowDateTime); // 受付日時
                    receivablesEntity.setInsDateTime(nowDateTime); // 登録日時
                    receivablesEntity.setInsOperCd(userOperCd); // 登録者
                    receivablesEntity.setUpdDateTime(nowDateTime); // 更新日時
                    receivablesEntity.setUpdOperCd(userOperCd); // 更新者
                    receivablesEntity.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
                    receivablesEntity.setVersion(0); // バージョン
                    receivablesRepository.save(receivablesEntity);

                    String orderOrderSummaryId = itemInfoSharedService
                        .getReceivablesId(inputDto.getStoreId(), DateUtil.getNowDateTime());

                    // 注文サマリテーブル登録
                    OOrderSummary orderSummary = new OOrderSummary();
                    // 店舗ID
                    orderSummary.setStoreId(inputDto.getStoreId());
                    // 受付id
                    orderSummary.setReceivablesId(otherReceivablesId);
                    // 削除フラグ
                    orderSummary.setDelFlag(Flag.OFF.getCode());
                    // 注文サマリID
                    orderSummary.setOrderSummaryId(orderOrderSummaryId);
                    // 顧客人数
                    orderSummary.setCustomerCount(receivablesEntity.getCustomerCount());
                    // テーブルID
                    orderSummary.setTableId(tableSeatDto.getTableId());
                    // 注文額
                    orderSummary.setOrderAmount(BigDecimal.ZERO);
                    // 値引額
                    orderSummary.setPriceDiscountAmount(null);
                    // 値引率
                    orderSummary.setPriceDiscountRate(null);
                    // 支払額 スマホで注文確認する際に支払額がデフォルト「０」を設定する,会計するとき計算してください
                    orderSummary.setPaymentAmount(BigDecimal.ZERO);
                    // 支払区分
                    orderSummary.setPaymentType(control.getControlCode());
                    // テイクアウト区分
                    orderSummary.setTakeoutFlag(TakeoutFlag.EAT_IN.getCode());
                    // 注文状態
                    orderSummary.setOrderStatus(OrderStatus.ORDER.getCode());
                    // 注文状態
                    orderSummary.setSeatRelease(Flag.OFF.getCode().toString());
                    // 登録日時
                    orderSummary.setInsDateTime(nowDateTime);
                    // 登録者
                    orderSummary.setInsOperCd(userOperCd);
                    // 更新日時
                    orderSummary.setUpdDateTime(nowDateTime);
                    // 更新者
                    orderSummary.setUpdOperCd(userOperCd);
                    // バージョン
                    orderSummary.setVersion(0);
                    orderSummaryRepository.save(orderSummary);

                    RTable tableReceivables = new RTable();
                    tableReceivables.setStoreId(inputDto.getStoreId());
                    tableReceivables.setGroupId(getNo.getSeqNo());
                    tableReceivables.setTableId(tableSeatDto.getTableId());
                    tableReceivables.setReceivablesId(otherReceivablesId);
                    tableReceivables.setRealReceivablesId(inputDto.getReceivablesId());
                    tableReceivables.setDelFlag(Flag.OFF.getCode());
                    tableReceivables.setInsDateTime(nowDateTime);
                    tableReceivables.setInsOperCd(userOperCd);
                    tableReceivables.setUpdDateTime(nowDateTime);
                    tableReceivables.setUpdOperCd(userOperCd);
                    tableReceivables.setVersion(0);
                    tableReceivablesList.add(tableReceivables);
                }

                if (tableReceivablesList.size() > 1) {
                    rtableRepository.saveAll(tableReceivablesList);
                }
            }

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.050", (Object) null), ex);
        }
    }


    /**
     * 会計PAD　配席印刷（受付IDにより注文サマリIDと注文ID）.
     *
     * @param inputDto 注文データ
     */
    @Override
    public OrderSummaryAndOrderIdDto getOrderSummaryAndOrderId(SetTableInputDto inputDto) {

        MStore mstore = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());
        OrderSummaryAndOrderIdDto outPutDto = null;

        // 受付IDにより注文サマリIDと注文ID取得する
        List<Map<String, Object>> orderSummaryList = orderSummaryRepository
            .findByOrderSummaryIdOrderId(inputDto.getStoreId(), inputDto.getReceivablesId());
        if (orderSummaryList.size() > 0) {

            if (Objects.equals(StaffCheckFlag.YES.getCode(), mstore.getStaffCheck())) {
                orderSummaryList = orderSummaryList.stream().filter(
                    o -> o.get("itemStatus").toString().equals(ItemStatus.CONFIRMED.getCode()))
                    .collect(Collectors.toList());
                if (orderSummaryList.size() == 0) {
                    return null;
                }
            }

            Map<String, List<Map<String, Object>>> orderSummaryListTemp = orderSummaryList
                .stream()
                .collect(Collectors.groupingBy(this::fetchItemGroupKey));

            outPutDto = new OrderSummaryAndOrderIdDto();
            List<Integer> orderIdList = new ArrayList<>();
            orderSummaryListTemp.forEach((key, orderListTemp) -> {
                orderIdList
                    .add(Integer.valueOf(orderListTemp.get(0).get("orderId").toString()));
            });

            // 注文ID
            outPutDto.setOrderIdList(orderIdList);

            //注文サマリID
            outPutDto
                .setOrderSummaryId(orderSummaryList.get(0).get("orderSummaryId").toString());
        }

        return outPutDto;
    }

    /**
     * 注文サマリを取得する.
     */
    @Override
    public OOrderSummary getOrderSummary(GetOrderItemListInputDto inputDto) {

        // 店铺ID
        String storeId = inputDto.getStoreId();

        // 受付ID
        String receivablesId = inputDto.getReceivablesId();

        // 注文サマリを取得する
        return orderSummaryRepository.getOrderSummary(storeId, receivablesId);

    }

    /**
     * 外税金額を取得する.
     */
    @Override
    public BigDecimal getForeignTax(String storeId, String orderSummaryId) {

        // 外税金额取得
        return orderRepository.getForeignTax(storeId, orderSummaryId);
    }

    /**
     * QR発行情報.
     *
     * @param inputDto 取得条件
     * @return QR発行情報
     */
    @Override
    public QrCodeIssueOutputDto qrCodeIssue(QrCodeIssueInputDto inputDto) {

        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 指定店舗情報取得
        MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (Objects.isNull(store)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

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
        if (nowHour < startTimeHour || (nowHour == startTimeHour && nowMinute < startTimeMinute)) {
            endTime = startTime;
            startTime = endTime.minusDays(1L);
        }

        // 受付番号
        Integer receptionNo = oreceivablesRepository
            .getReceptionNoByReceptionTime(inputDto.getStoreId(), startTime, endTime);

        // 受付ID取得
        String receivablesId = itemInfoSharedService
            .getReceivablesId(inputDto.getStoreId(), nowDateTime);

        // 受付情報登録
        OReceivables receivables = new OReceivables();
        receivables.setStoreId(inputDto.getStoreId()); // 店舗ID
        receivables.setReceivablesId(receivablesId); // 受付ID
        receivables.setReceptionNo(receptionNo); // 受付番号
        receivables.setCustomerCount(inputDto.getCustomerCount()); // 顧客人数
        receivables.setReceptionTime(nowDateTime); // 受付日時
        receivables.setInsDateTime(nowDateTime); // 登録日時
        receivables.setInsOperCd(userOperCd); // 登録者
        receivables.setUpdDateTime(nowDateTime); // 更新日時
        receivables.setUpdOperCd(userOperCd); // 更新者
        receivables.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
        receivables.setVersion(0); // バージョン
        oreceivablesRepository.save(receivables);
        if (Objects.isNull(inputDto.getTableId())) {

            // 注文サマリテーブルに登録する
            // 注文サマリテーブル登録
            OOrderSummary orderSummary = new OOrderSummary();
            // 店舗ID
            orderSummary.setStoreId(inputDto.getStoreId());
            // 受付id
            orderSummary.setReceivablesId(receivablesId);
            // 削除フラグ
            orderSummary.setDelFlag(Flag.OFF.getCode());
            // 注文サマリID
            orderSummary.setOrderSummaryId(itemInfoSharedService
                .getReceivablesId(inputDto.getStoreId(), DateUtil.getNowDateTime()));
            // 顧客人数
            orderSummary.setCustomerCount(inputDto.getCustomerCount());
            // テーブルID
            if (Objects.isNull(inputDto.getTableId())) {
                orderSummary.setTableId(inputDto.getTableId());
            }
            // 注文額
            orderSummary.setOrderAmount(BigDecimal.ZERO);
            // 値引額
            orderSummary.setPriceDiscountAmount(BigDecimal.ZERO);
            // 値引率
            orderSummary.setPriceDiscountRate(BigDecimal.ZERO);
            // 支払額 スマホで注文確認する際に支払額がデフォルト「０」を設定する,会計するとき計算してください
            orderSummary.setPaymentAmount(BigDecimal.ZERO);
            // 支払区分情報取得
            MControl control = controlRepository
                .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                    CODE_GROUP_PAYMENT, Flag.OFF.getCode());
            // 支払区分
            orderSummary.setPaymentType(control.getControlCode());
            // テイクアウト区分
            orderSummary.setTakeoutFlag(TakeoutFlag.EAT_IN.getCode());
            // 注文状態
            if (Objects.isNull(inputDto.getTableId())) {
                orderSummary.setOrderStatus(OrderStatus.TENTATIVE_ORDER.getCode());
                orderSummary.setSeatRelease(Flag.ON.getCode().toString());
            } else {
                orderSummary.setOrderStatus(OrderStatus.ORDER.getCode());
                orderSummary.setSeatRelease(Flag.OFF.getCode().toString());
            }
            // 登録日時
            orderSummary.setInsDateTime(nowDateTime);
            // 登録者
            orderSummary.setInsOperCd(userOperCd);
            // 更新日時
            orderSummary.setUpdDateTime(nowDateTime);
            // 更新者
            orderSummary.setUpdOperCd(userOperCd);
            // バージョン
            orderSummary.setVersion(0);
            orderSummaryRepository.save(orderSummary);
        }
        QrCodeIssueOutputDto outputDto = new QrCodeIssueOutputDto();
        outputDto.setReceptionId(receivablesId);
        return outputDto;
    }

    /**
     * 受付番号廃棄.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void receptionDisposal(ReceptionDisposalInputDto inputDto) {
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 受付論理削除
        oreceivablesRepository.updateByDelFlag(inputDto.getStoreId(), inputDto.getReceivablesId());
        // 注文サマリ論理削除
        orderSummaryRepository
            .updateOrderSummary(inputDto.getStoreId(), inputDto.getReceivablesId(), userOperCd,
                DateUtil.getNowDateTime());
        // トークン削除
        tokenRepository.deleteReceivables(inputDto.getStoreId(), inputDto.getReceivablesId());

    }

    /**
     * 受付番号選択可能のテーブル.
     *
     * @param inputDto 取得条件
     * @return 受付番号選択可能のテーブル.
     */
    @Override
    public ReceptionInitOutputDto getReceptionInit(ReceptionInitInputDto inputDto) {

        // 利用時間情報を取得する
        Map<String, Object> useTimeMap = storeRepository
            .findDefaultUseTimeByStoreId(inputDto.getStoreId());

        // 利用時間情報を変換する
        GetDefaultUseTimeDto defaultUseTimeDto = new GetDefaultUseTimeDto();
        defaultUseTimeDto = JSONObject
            .parseObject(JSONObject.toJSONString(useTimeMap), GetDefaultUseTimeDto.class);

        // 登録日時
        ZonedDateTime dateTime = DateUtil.getNowDateTime();

        // 受付番号選択可能のテーブル取得
        List<Map<String, Object>> chooseAbleTableMapList = tableRepository
            .findChooseAbleTable(inputDto.getStoreId(), PayStatus.PAY_NOT_ALREADY.getCode(),
                ReservateClassification.TABLE.getCode(), ReservateStatus.RESERVATING.getCode(),
                dateTime, defaultUseTimeDto.getDefaultUseTime());

        List<ChooseAbleTableDto> chooseAbleTableList = new ArrayList<>();
        chooseAbleTableMapList.forEach(stringObjectMap -> chooseAbleTableList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), ChooseAbleTableDto.class)));

        // グループ化
        Map<Integer, List<ChooseAbleTableDto>> tableInfoGroupMap = chooseAbleTableList.stream()
            .collect(Collectors.groupingBy(ChooseAbleTableDto::getTableId));

        // 空席テーブル
        List<ReceptionTableDto> vacantSeatTableTempList = new ArrayList<>();
        // ファイトテーブル
        List<ReceptionTableDto> fightTableTempList = new ArrayList<>();
        // グループを単位に合計取得
        tableInfoGroupMap.forEach((key, chooseAbleTabs) -> {
            Integer cunstomCn = chooseAbleTabs.stream()
                .mapToInt(ChooseAbleTableDto::getCustomerCount).sum();
            if (cunstomCn == 0 && inputDto.getCustomerCount() <= chooseAbleTabs.get(0)
                .getTableSeatCount()) {
                ReceptionTableDto receptionTableDto = new ReceptionTableDto();
                // テーブルID
                receptionTableDto.setTableId(chooseAbleTabs.get(0).getTableId());
                // テーブル名
                receptionTableDto.setTableName(chooseAbleTabs.get(0).getTableName());
                // 座席数
                receptionTableDto.setTableSeatCount(chooseAbleTabs.get(0).getTableSeatCount());
                // オーダー数
                receptionTableDto.setOrderCount(0);
                // 顧客人数
                receptionTableDto.setCustomerCount(cunstomCn);
                // 予約数
                receptionTableDto.setReservateCount(chooseAbleTabs.get(0).getReservateCount());
                vacantSeatTableTempList.add(receptionTableDto);
            } else if (cunstomCn != 0 && inputDto.getCustomerCount()
                <= chooseAbleTabs.get(0).getTableSeatCount() - cunstomCn) {
                ReceptionTableDto receptionTableDto = new ReceptionTableDto();
                // テーブルID
                receptionTableDto.setTableId(chooseAbleTabs.get(0).getTableId());
                // テーブル名
                receptionTableDto.setTableName(chooseAbleTabs.get(0).getTableName());
                // 空席数
                receptionTableDto
                    .setVacantSeat(chooseAbleTabs.get(0).getTableSeatCount() - cunstomCn);
                // 座席数
                receptionTableDto.setTableSeatCount(chooseAbleTabs.get(0).getTableSeatCount());
                // オーダー数
                receptionTableDto.setOrderCount(chooseAbleTabs.size());
                // 顧客人数
                receptionTableDto.setCustomerCount(cunstomCn);
                // 予約数
                receptionTableDto.setReservateCount(chooseAbleTabs.get(0).getReservateCount());
                fightTableTempList.add(receptionTableDto);
            }
        });
        // ソート
        // 空席テーブル
        List<ReceptionTableDto> vacantSeatTableList = new ArrayList<>();
        // ファイトテーブル
        List<ReceptionTableDto> fightTableList = new ArrayList<>();
        vacantSeatTableList = vacantSeatTableTempList.stream()
            .sorted(Comparator.comparing(ReceptionTableDto::getTableId))
            .collect(Collectors.toList());
        fightTableList = fightTableTempList.stream()
            .sorted(Comparator.comparing(ReceptionTableDto::getTableId))
            .collect(Collectors.toList());
        List<ReceptionTableDto> tableList = new ArrayList<>();
        tableList.addAll(vacantSeatTableList);
        tableList.addAll(fightTableList);
        ReceptionInitOutputDto receptionInitOutputDto = new ReceptionInitOutputDto();
        receptionInitOutputDto.setTableList(tableList);
        return receptionInitOutputDto;
    }

    /**
     * 配せき一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 配せき一覧情報取得.
     */
    @Override
    public AssignationTableOutputDto assignationTable(AssignationTableInputDto inputDto) {

        // 受付時間を取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        ZonedDateTime start = nowDateTime.minusDays(1L);
        // はいせき一覧取得
        List<OreceivablesGridDto> oreceivablesList = oreceivablesRepository
            .findByReception(inputDto.getStoreId(), start, TakeoutFlag.TAKE_OUT.getCode());

        List<AssignationTableDto> assignationTableList = new ArrayList<>();
        oreceivablesList.forEach(oreceivablesGridDto -> {
            AssignationTableDto assignationTableDto = new AssignationTableDto();
            // 受付ID
            assignationTableDto.setReceivablesId(oreceivablesGridDto.getReceivablesId());
            // 受付番号
            assignationTableDto
                .setReceptionNo(String.format("%04d", oreceivablesGridDto.getReceptionNo()));
            // 人数
            assignationTableDto.setCustomerCount(oreceivablesGridDto.getCustomerCount());
            // 受付時間
            assignationTableDto.setReceptionTime(DateUtil
                .getZonedDateString(oreceivablesGridDto.getReceptionTime(),
                    CommonConstants.DATE_FORMAT_DATETIME));
            // 待ち時間
            assignationTableDto.setStayTime(String.valueOf(
                (ZonedDateTime.now().toEpochSecond() - oreceivablesGridDto.getReceptionTime()
                    .toEpochSecond())
                    / 60) + CommonConstants.MIN);

            assignationTableList.add(assignationTableDto);
        });

        AssignationTableOutputDto outputDto = new AssignationTableOutputDto();
        outputDto.setAssignationTableList(assignationTableList);

        return outputDto;
    }

    /**
     * 注文詳細情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文詳細情報取得.
     */
    @Override
    public GetOrderDetailOutputDto getOrderDetail(GetOrderDetailInputDto inputDto) {

        // 注文詳細リスト取得
        List<Map<String, Object>> orderDetailData = oreceivablesRepository
            .findOrderInfo(inputDto.getStoreId(), inputDto.getReceivablesId(),
                inputDto.getLanguages());

        GetOrderDetailOutputDto outDto = new GetOrderDetailOutputDto();

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderDetailData)) {
            outDto.setItemList(new ArrayList<>());
            return outDto;
        }

        // 注文情報を編集する
        List<OrderDetailItemDataDto> orderItemDataList = new ArrayList<>();
        orderDetailData.forEach(stringObjectMap -> orderItemDataList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    OrderDetailItemDataDto.class)));

        // 注文基本情報設定
        beanMapper.map(orderItemDataList.get(0), outDto);

        // 受付時間
        outDto.setReceptionTime(orderItemDataList.get(0).getReceptionTime());
        // 受付番号
        outDto
            .setReceptionNo(String.format("%04d", orderItemDataList.get(0).getReceptionNo()));
        // テーブル名
        outDto
            .setTableName(orderItemDataList.get(0).getTableName());
        // 注文金額
        outDto
            .setSubtotal(orderItemDataList.get(0).getOrderAmount());
        // 割引率
        outDto
            .setDiscountTax(
                Objects.isNull(orderItemDataList.get(0).getPriceDiscountRate()) ? BigDecimal.ZERO
                    : orderItemDataList.get(0).getPriceDiscountRate());
        // 割引値
        outDto
            .setPriceDiscountAmount(
                Objects.isNull(orderItemDataList.get(0).getPriceDiscountAmount())
                    ? BigDecimal.ZERO : orderItemDataList.get(0).getPriceDiscountAmount());
        // 支払金額
        outDto
            .setPaymentAmount(orderItemDataList.get(0).getPaymentAmount());

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderDetailItemDataDto>> grpByOrderId = orderItemDataList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        // 商品情報リストを編集する
        List<ItemsDetailDto> itemList = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDetailDataDtos) -> {
            ItemsDetailDto item = beanMapper
                .map(orderItemDetailDataDtos.get(0), ItemsDetailDto.class);
            orderItemDetailDataDtos.forEach(orderItemDetailDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDetailDataDto.getOptionName())) {
                    String optionName = orderItemDetailDataDto.getOptionName();
                    if (orderItemDetailDataDto.getOptionItemCount() != null
                        && 0 < orderItemDetailDataDto.getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDetailDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDetailDataDto.getOptionName(),
                            orderItemDetailDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getItemOption())) {
                        item.setItemOption(optionName);
                    } else {
                        item.setItemOption(item.getItemOption() + "," + optionName);
                    }
                }
            });
            itemList.add(item);
        });

        // 注文IDをグルーピングする
        Map<String, List<ItemsDetailDto>> orderNumberByOrderId = itemList.stream().collect(
            Collectors.groupingBy(ItemsDetailDto::getOrderId));

        List<OrderItemNumDto> orderNumberList = new ArrayList<>();
        orderNumberByOrderId.forEach((s, itemsDetailDtos) -> {
            OrderItemNumDto orderItemNumDto = new OrderItemNumDto();
            orderItemNumDto.setOrderNumber(itemsDetailDtos.stream().sorted(Comparator
                .comparing(ItemsDetailDto::getItemId, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList()));
            orderNumberList.add(orderItemNumDto);
        });
        outDto.setItemList(orderNumberList);

        // 外税
        outDto.setForeignTax(BigDecimal.ZERO);

        List<ItemsDto> itemTaxList = new ArrayList<>();
        grpByOrderId.forEach((s, orderDetailItemDataDtos) -> {
            ItemsDto itemsDto = new ItemsDto();
            itemsDto.setItemId(orderDetailItemDataDtos.get(0).getItemId());
            if (Objects.equals(ItemType.NORMAL.getCode(),
                orderDetailItemDataDtos.get(0).getItemClassification())) {
                itemsDto.setItemPrice(orderDetailItemDataDtos.get(0).getItemPrice());
                outDto.setForeignTax(
                    outDto.getForeignTax().add(orderDetailItemDataDtos.get(0).getForeignTax()));
            } else {
                itemsDto.setItemPrice(orderDetailItemDataDtos.get(0).getItemPrice()
                    .multiply(new BigDecimal(-1)));
            }
            itemTaxList.add(itemsDto);
        });

        // 後払の場合
        if (Objects.equals(PaymentType.DEFERRED_PAYMENT.getCode(),
            orderItemDataList.get(0).getPaymentType())) {

            //税入力情報
            GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
            taxValueInputDto.setStoreId(inputDto.getStoreId());
            taxValueInputDto.setTakeoutFlag(orderItemDataList.get(0).getTakeoutFlag());
            taxValueInputDto.setItemList(itemTaxList);
            GetTaxValueOutputDto getTaxValueOutputDto = itemInfoSharedService
                .getTaxValue(taxValueInputDto);
            // 外税
            outDto.setForeignTax(
                getTaxValueOutputDto.getSotoTaxEight().add(getTaxValueOutputDto.getSotoTaxTen()));
        }

        //合計
        outDto.setTotal(orderItemDataList.get(0).getOrderAmount().add(outDto.getForeignTax()));

        return outDto;
    }

    /**
     * 注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文一覧情報
     */
    @Override
    public GetUnCfmOrderListOutputDto getUnCfmOrderList(GetUnCfmOrderListInputDto inputDto) {

        // 結果DTO初期化
        GetUnCfmOrderListOutputDto outDto = new GetUnCfmOrderListOutputDto();

        List<Map<String, Object>> orderListData = new ArrayList<>();

        if (inputDto.getSureFlag().equals(Flag.OFF.getCode().toString()) ||
            inputDto.getSureFlag().equals(Flag.ON.getCode().toString())) {
            // 注文一覧取得
            orderListData = orderSummaryRepository
                .findUnCfmOrderInfoByStoreId(inputDto.getStoreId(),
                    Objects.equals(Flag.OFF.getCode().toString(), inputDto.getSureFlag())
                        ? ItemStatus.UNCONFIRMED.getCode() : StringUtils.EMPTY,
                    StringUtils.trimToEmpty(inputDto.getTakeoutFlag()),
                    inputDto.getTableId() == null ? 0 : inputDto.getTableId(),
                    PayStatus.PAY_NOT_ALREADY.getCode());
        } else {
            orderListData = orderSummaryRepository
                .findHistoryOrderInfoByStoreId(inputDto.getStoreId(),
                    ItemStatus.UNCONFIRMED.getCode(),
                    ItemStatus.CONFIRMED.getCode(),
                    StringUtils.trimToEmpty(inputDto.getTakeoutFlag()),
                    inputDto.getTableId() == null ? 0 : inputDto.getTableId(),
                    PayStatus.PAY_NOT_ALREADY.getCode());
        }

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderListData)) {
            outDto.setOrderList(new ArrayList<>());
            return outDto;
        }

        // 注文一覧設定
        List<UnCfmOrderInfoDto> orderList = new ArrayList<>();
        orderListData.forEach(stringObjectMap -> orderList.add(JSONObject
            .parseObject(JSONObject.toJSONString(stringObjectMap), UnCfmOrderInfoDto.class)));
        outDto.setOrderList(orderList);

        return outDto;
    }

    /**
     * 注文確認未済商品クリア.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void emptyItem(EmptyItemInputDto inputDto) {
        String userOperCd = CommonConstants.OPER_CD_MOBILE;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 注文IDリストを編集する
        List<Integer> orderIdList = new ArrayList<>();
        for (OrderIdDto orderId : inputDto.getOrderIdList()) {
            orderIdList.add(orderId.getOrderId());
        }

        // 注文明細オプションを削除する
        for (Integer orderId : orderIdList) {
            // 注文明細IDを取得する
            List<Integer> idList = orderDetailsRepository
                .getDetailIdList(inputDto.getStoreId(), orderId);

            // 注文明細オプションを削除する
            orderDetailsOptionRepository.deleteOrderItemOptions(inputDto.getStoreId(), idList);
        }

        // 注文明細を削除する
        orderDetailsRepository.deleteOrderItemDetails(inputDto.getStoreId(), orderIdList);

        // 注文サマリIDを取得する
        String orderSummaryId = orderRepository
            .getOrderSummaryId(inputDto.getStoreId(), orderIdList.get(0));

        // 注文を削除する
        orderRepository.deleteOrders(inputDto.getStoreId(), orderIdList);

        // 注文サマリをロックする
        OOrderSummary orderSummary = orderSummaryRepository
            .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                Flag.OFF.getCode());

        // 注文サマリ情報がNULLである場合
        if (orderSummary == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.061", (Object) null));
        }

        // 注文サマリ注文金額を更新する
        orderSummaryRepository
            .updateOrderAmountWithDelFlag(inputDto.getStoreId(), orderSummaryId,
                inputDto.getDeleteItemAmount(),
                userOperCd, DateUtil.getNowDateTime());
    }

    /**
     * 注文確認未済商品削除.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void deleteItem(DeleteItemInputDto inputDto) {

        String userOperCd = CommonConstants.OPER_CD_MOBILE;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 注文明細データ取得
        OOrderDetails detailsData = orderDetailsRepository
            .findByStoreIdAndOrderDetailIdAndDelFlag(inputDto.getStoreId(),
                inputDto.getOrderDetailId(), Flag.OFF.getCode());

        // 注文明細情報がNULLである場合
        if (detailsData == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.061", (Object) null));
        }

        // 注文サマリIDを取得する
        String orderSummaryId = orderRepository
            .getOrderSummaryId(inputDto.getStoreId(), inputDto.getOrderId());

        // 注文明細オプションを削除する
        orderDetailsOptionRepository
            .deleteByStoreIdAndOrderDetailId(inputDto.getStoreId(), inputDto.getOrderDetailId());

        // 注文明細を削除する
        orderDetailsRepository
            .deleteByStoreIdAndOrderIdAndOrderDetailId(inputDto.getStoreId(), inputDto.getOrderId(),
                inputDto.getOrderDetailId());

        // 該当レコードを削除する
        Integer resultCount = orderRepository
            .deleteOrderWithDetails(inputDto.getStoreId(), inputDto.getOrderId());

        // 受付時間を取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // 注文明細を取得する
        List<Map<String, Object>> orderDetailMap = orderDetailsRepository
            .findOrderDetailInfoByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(), inputDto.getOrderId());

        // 注文明細テーブル
        List<OOrderDetails> orderDetailList = new ArrayList<>();
        orderDetailMap.forEach(stringObjectMap -> orderDetailList.add(JSONObject
            .parseObject(JSONObject.toJSONString(stringObjectMap), OOrderDetails.class)));

        // 注文明細が存在の場合
        if (resultCount <= 0) {

            // テイクアウトフラグを取得する
            String takeOutFlag = orderSummaryRepository
                .getTakeOutFlagByStoreIdAndOrderSummaryId(inputDto.getStoreId(), orderSummaryId);

            // 消費税取得
            List<ItemsDto> itemList = new ArrayList<>();
            orderDetailList.forEach(orderDetails -> {
                ItemsDto itemsDto = new ItemsDto();
                itemsDto.setItemId(orderDetails.getItemId().toString());
                itemsDto.setItemPrice(
                    orderDetails.getItemPrice());
                itemList.add(itemsDto);
            });

            GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
            taxValueInputDto.setStoreId(inputDto.getStoreId());
            taxValueInputDto.setTakeoutFlag(takeOutFlag);

            taxValueInputDto.setItemList(itemList);
            GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
                .getTaxValue(taxValueInputDto);

            // 外税を取得する
            BigDecimal foreignTaxNew = taxValueOutputDto.getSotoTaxEight()
                .add(taxValueOutputDto.getSotoTaxTen());

            // 該当レコードを更新する
            orderRepository
                .updateForeignTax(inputDto.getStoreId(), inputDto.getOrderId(), foreignTaxNew,
                    userOperCd, nowDateTime);
        }

        // 注文サマリをロックする
        OOrderSummary orderSummary = orderSummaryRepository
            .findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(), orderSummaryId,
                Flag.OFF.getCode());

        // 注文サマリ情報がNULLである場合
        if (orderSummary == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.061", (Object) null));
        }

        // 注文サマリ注文金額を更新する
        orderSummaryRepository
            .updateOrderAmountWithDelFlag(inputDto.getStoreId(), orderSummaryId,
                detailsData.getItemPrice(),
                userOperCd, nowDateTime);
    }

    /**
     * 受付情報設定.
     *
     * @param inputDto 取得条件
     */
    @Override
    public InitOrderOutputDto initOrder(InitOrderInputDto inputDto) {
        String userOperCd = CommonConstants.OPER_CD_MOBILE;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 受付時間を取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // 指定店舗情報取得
        MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (store == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

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

        // 受付番号
        Integer receptionNo = receivablesRepository
            .getReceptionNoByReceptionTime(inputDto.getStoreId(), startTime, endTime);

        // 受付ID取得
        String receivablesId = itemInfoSharedService
            .getReceivablesId(inputDto.getStoreId(), nowDateTime);

        // 受付情報登録
        OReceivables receivables = new OReceivables();
        receivables.setStoreId(inputDto.getStoreId()); // 店舗ID
        receivables.setReceivablesId(receivablesId); // 受付ID
        receivables.setReceptionNo(receptionNo); // 受付番号
        receivables.setCustomerCount(inputDto.getCustomerCount()); // 顧客人数
        receivables.setReceptionTime(nowDateTime); // 受付日時
        receivables.setInsDateTime(nowDateTime); // 登録日時
        receivables.setInsOperCd(userOperCd); // 登録者
        receivables.setUpdDateTime(nowDateTime); // 更新日時
        receivables.setUpdOperCd(userOperCd); // 更新者
        receivables.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
        receivables.setVersion(0); // バージョン
        receivablesRepository.save(receivables);

        String orderSummaryId = itemInfoSharedService
            .getReceivablesId(inputDto.getStoreId(), DateUtil.getNowDateTime());

        // 注文サマリテーブル登録
        OOrderSummary orderSummary = new OOrderSummary();
        // 店舗ID
        orderSummary.setStoreId(inputDto.getStoreId());
        // 受付id
        orderSummary.setReceivablesId(receivablesId);
        // 削除フラグ
        orderSummary.setDelFlag(Flag.OFF.getCode());
        // 注文サマリID
        orderSummary.setOrderSummaryId(orderSummaryId);
        // 顧客人数
        orderSummary.setCustomerCount(inputDto.getCustomerCount());
        // テーブルID
        orderSummary.setTableId(inputDto.getTableId());
        // 注文額
        orderSummary.setOrderAmount(BigDecimal.ZERO);
        // 支払額
        orderSummary.setPaymentAmount(BigDecimal.ZERO);
        // 支払区分
        orderSummary.setPaymentType(PaymentType.DEFERRED_PAYMENT.getCode());
        // テイクアウト区分
        orderSummary.setTakeoutFlag(inputDto.getTakeoutFlag());
        // 注文状態
        orderSummary.setOrderStatus(OrderStatus.ORDER.getCode());
        // 席解除フラグ
        if (inputDto.getTableId() != null) {
            orderSummary.setSeatRelease(Flag.OFF.getCode().toString());
        } else {
            orderSummary.setSeatRelease(Flag.ON.getCode().toString());
        }
        // 登録日時
        orderSummary.setInsDateTime(nowDateTime);
        // 登録者
        orderSummary.setInsOperCd(userOperCd);
        // 更新日時
        orderSummary.setUpdDateTime(nowDateTime);
        // 更新者
        orderSummary.setUpdOperCd(userOperCd);
        // バージョン
        orderSummary.setVersion(0);

        OOrderSummary insOrderSummaryData = orderSummaryRepository.save(orderSummary);

        if (CollectionUtils.isNotEmpty(inputDto.getBuffetId()) || CollectionUtils
            .isNotEmpty(inputDto.getCourseId())) {

            // 注文テーブル登録
            OOrder order = new OOrder();
            // 店舗ID
            order.setStoreId(inputDto.getStoreId());
            // 注文サマリID
            order.setOrderSummaryId(orderSummaryId);
            // 削除フラグ
            order.setDelFlag(Flag.OFF.getCode());

            // 注文IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_order"); // テーブル名
            getSeqNoInputDto.setItem("order_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 注文ID
            order.setOrderId(getSeqNo.getSeqNo());
            // 支払状態
            order.setPayStatus(PayStatus.PAY_NOT_ALREADY.getCode());
            // 外税金額
            order.setForeignTax(BigDecimal.ZERO);
            // 注文日時
            order.setOrderTime(nowDateTime);
            // 注文区分
            order.setOrderType(OrderType.FRONT.getCode());
            // 登録日時
            order.setInsDateTime(nowDateTime);
            // 登録者
            order.setInsOperCd(userOperCd);
            // 更新日時
            order.setUpdDateTime(nowDateTime);
            // 更新者
            order.setUpdOperCd(userOperCd);
            // バージョン
            order.setVersion(0);
            orderRepository.save(order);

            BigDecimal paymentAmount = BigDecimal.ZERO;

            // 放題データ登録
            if (CollectionUtils.isNotEmpty(inputDto.getBuffetId())) {
                for (BuffetInfoDto buffetInfo : inputDto.getBuffetId()) {
                    // 注文明細テーブル登録
                    OOrderDetails orderDetails = new OOrderDetails();
                    // 店舗ID
                    orderDetails.setStoreId(inputDto.getStoreId());
                    // 削除フラグ
                    orderDetails.setDelFlag(Flag.OFF.getCode());
                    // 注文ID
                    orderDetails.setOrderId(order.getOrderId());
                    // 商品ID
                    orderDetails.setItemId(buffetInfo.getBuffetId());
                    // 単価
                    orderDetails.setItemPrice(buffetInfo.getBuffetAmount());
                    // 商品個数
                    orderDetails.setItemCount(buffetInfo.getBuffetCount());
                    // 商品区分
                    orderDetails.setItemClassification(ItemType.NORMAL.getCode());
                    // 商品状態
                    orderDetails.setItemStatus(ItemStatus.CONFIRMED.getCode());

                    // 注文明細IDのシーケンスNo取得
                    getSeqNoInputDto = new GetSeqNoInputDto();
                    getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
                    getSeqNoInputDto.setItem("order_detail_id"); // 項目
                    getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                    getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                    //注文明細ID
                    orderDetails.setOrderDetailId(getSeqNo.getSeqNo());
                    // 登録日時
                    orderDetails.setInsDateTime(nowDateTime);
                    // 登録者
                    orderDetails.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                    // 更新日時
                    orderDetails.setUpdDateTime(nowDateTime);
                    // 更新者
                    orderDetails.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                    // バージョン
                    orderDetails.setVersion(0);
                    orderDetails.setItemReturnId(null);
                    orderDetailsRepository.save(orderDetails);
                    paymentAmount = paymentAmount
                        .add(buffetInfo.getBuffetAmount()
                            .multiply(BigDecimal.valueOf(buffetInfo.getBuffetCount())));
                }
            }

            // コースデータ登録
            if (CollectionUtils.isNotEmpty(inputDto.getCourseId())) {
                for (CourseInfoDto courseInfo : inputDto.getCourseId()) {
                    // 注文明細テーブル登録
                    OOrderDetails orderDetails = new OOrderDetails();
                    // 店舗ID
                    orderDetails.setStoreId(inputDto.getStoreId());
                    // 削除フラグ
                    orderDetails.setDelFlag(Flag.OFF.getCode());
                    // 注文ID
                    orderDetails.setOrderId(order.getOrderId());
                    // 商品ID
                    orderDetails.setItemId(courseInfo.getCourseId());
                    // 単価
                    orderDetails.setItemPrice(courseInfo.getCoursePrice());
                    // 商品個数
                    orderDetails.setItemCount(courseInfo.getCourseCount());
                    // 商品区分
                    orderDetails.setItemClassification(ItemType.NORMAL.getCode());
                    // 商品状態
                    orderDetails.setItemStatus(ItemStatus.CONFIRMED.getCode());

                    // 注文明細IDのシーケンスNo取得
                    getSeqNoInputDto = new GetSeqNoInputDto();
                    getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                    getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
                    getSeqNoInputDto.setItem("order_detail_id"); // 項目
                    getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                    getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                    //注文明細ID
                    orderDetails.setOrderDetailId(getSeqNo.getSeqNo());
                    // 登録日時
                    orderDetails.setInsDateTime(nowDateTime);
                    // 登録者
                    orderDetails.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                    // 更新日時
                    orderDetails.setUpdDateTime(nowDateTime);
                    // 更新者
                    orderDetails.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                    // バージョン
                    orderDetails.setVersion(0);
                    orderDetails.setItemReturnId(null);
                    orderDetailsRepository.save(orderDetails);
                    paymentAmount = paymentAmount
                        .add(courseInfo.getCoursePrice()
                            .multiply(BigDecimal.valueOf(courseInfo.getCourseCount())));
                }
            }

            // 注文サマリテーブル更新
            if (BigDecimal.ZERO.compareTo(paymentAmount) < 0) {
                insOrderSummaryData.setOrderAmount(
                    insOrderSummaryData.getPaymentAmount().add(paymentAmount));
                insOrderSummaryData.setUpdDateTime(nowDateTime);
                insOrderSummaryData.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                insOrderSummaryData.setVersion(insOrderSummaryData.getVersion() + 1);
                orderSummaryRepository.save(insOrderSummaryData);
            }
        }

        // テイクアウト区分設定
        if (StringUtils.isNotEmpty(inputDto.getTakeoutFlag())) {
            // テイクアウト区分保存Keyを生成する
            String takeoutFlagKey = receivablesId + "takeoutFlag";
            // テイクアウト区分設定
            redisTemplate.opsForValue().set(takeoutFlagKey, inputDto.getTakeoutFlag());
            redisTemplate.expire(takeoutFlagKey, 10800L, TimeUnit.SECONDS);
        }

        InitOrderOutputDto outputDto = new InitOrderOutputDto();

        outputDto.setReceivablesId(receivablesId);

        return outputDto;
    }

    /**
     * 注文履歴情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文履歴情報
     */
    @Override
    public GetOrderHistoryListOutputDto getOrderHistoryList(GetOrderHistoryListInputDto inputDto) {

        // 結果DTO初期化
        GetOrderHistoryListOutputDto outDto = new GetOrderHistoryListOutputDto();

        // 注文情報を取得する
        List<Map<String, Object>> orderData = orderSummaryRepository
            .findOrderInfoByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId(),
                inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderData)) {
            outDto.setOrderHistoryList(new ArrayList<>());
            return outDto;
        }

        if (!Objects.isNull(orderData.get(0).get("receptionNo"))) {
            outDto.setReceptionNo(orderData.get(0).get("receptionNo").toString());
        }

        if (!Objects.isNull(orderData.get(0).get("tableName"))) {
            outDto.setTableName(orderData.get(0).get("tableName").toString());
        }

        // 注文情報を編集する
        List<OrderItemDataDto> orderItemDataList = new ArrayList<>();
        orderData.forEach(stringObjectMap -> orderItemDataList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), OrderItemDataDto.class)));

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderItemDataDto>> grpByOrderIdTmp = orderItemDataList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        Map<String, List<OrderItemDataDto>> grpByOrderId = new TreeMap<>();

        for (String orderId : grpByOrderIdTmp.keySet()) {
            grpByOrderId.put(orderId, grpByOrderIdTmp.get(orderId));
        }

        // 商品情報リストを編集する
        List<OrderItemFullInfoDto> items = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDataDtos) -> {
            OrderItemFullInfoDto item = beanMapper
                .map(orderItemDataDtos.get(0), OrderItemFullInfoDto.class);
            orderItemDataDtos.forEach(orderItemDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDataDto.getOptionName())) {
                    String optionName = orderItemDataDto.getOptionName();
                    if (orderItemDataDto.getOptionItemCount() != null && 0 < orderItemDataDto
                        .getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDataDto.getOptionName(),
                            orderItemDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getOption())) {
                        item.setOption(optionName);
                    } else {
                        item.setOption(item.getOption() + "," + optionName);
                    }
                }
            });
            items.add(item);
        });

        // 注文額を初期化する
        BigDecimal orderAmount = BigDecimal.ZERO;
        // 注文履歴リストを初期化する
        List<OrderItemInfoDto> orderList = new ArrayList<>();

        for (OrderItemFullInfoDto item : items) {
            OrderItemInfoDto itemInfo = new OrderItemInfoDto();

            // 商品ID
            itemInfo.setItemId(item.getItemId());
            // 商品名
            itemInfo.setItemName(item.getItemName());
            if (item.getItemClassification().equals(ItemType.RETURNS.getCode())) {
                // 商品金額
                itemInfo.setItemPrice(BigDecimal.ZERO.subtract(item.getItemPrice()));
            } else {
                // 商品金額
                itemInfo.setItemPrice(item.getItemPrice());
            }

            // 商品個数
            itemInfo.setItemCount(item.getItemCount());
            // オプション
            itemInfo.setOption(item.getOption());

            itemInfo.setItemClassification(item.getItemClassification());

            orderAmount = orderAmount.add(itemInfo.getItemPrice());
            orderList.add(itemInfo);
        }

        // 注文額
        outDto.setOrderAmount(orderAmount);
        // 注文履歴リスト
        outDto.setOrderHistoryList(orderList.stream().sorted(Comparator
            .comparing(OrderItemInfoDto::getItemClassification,
                Comparator.nullsLast(String::compareTo)).reversed()
            .thenComparing(OrderItemInfoDto::getItemId,
                Comparator.nullsLast(String::compareTo)).reversed())
            .collect(Collectors.toList()));

        // 支払区分情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PAY_LATER, Flag.OFF.getCode());
        outDto.setStaffAccountAbleFlag(Flag.OFF.getCode());
        if (control != null && Objects
            .equals(Flag.ON.getCode().toString(), control.getControlCode())) {
            outDto.setStaffAccountAbleFlag(Flag.ON.getCode());
        }

        return outDto;
    }

    /**
     * 注文詳細情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文詳細情報取得.
     */
    @Override
    public GetOrderDetailInfoOutputDto getOrderDetailInfo(GetOrderDetailInfoInputDto inputDto) {

        // 結果DTO初期化
        GetOrderDetailInfoOutputDto outDto = new GetOrderDetailInfoOutputDto();

        // 注文情報を取得する
        Map<String, Object> orderData = receivablesRepository
            .findOrderInfoByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId());

        if (orderData != null) {
            outDto = JSONObject
                .parseObject(JSONObject.toJSONString(orderData), GetOrderDetailInfoOutputDto.class);
        }

        // インプット情報をDTOにセットする
        GetOrderItemListInputDto itemInputDto = beanMapper
            .map(inputDto, GetOrderItemListInputDto.class);
        itemInputDto.setLanguages(Language.JP.getCode());

        // 注文商品リスト取得サービス処理を実行する
        GetOrderItemListOutputDto outputDto = getOrderItemList(itemInputDto);

        // 小計
        outDto.setSubtotal(outputDto.getOrderAmount());

        // 消費税取得
        GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
        taxValueInputDto.setStoreId(inputDto.getStoreId());

        taxValueInputDto.setTakeoutFlag(outDto.getTakeoutFlag());

        // 外税
        outDto.setForeignTax(BigDecimal.ZERO);
        // 合計
        outDto.setTotal((Objects.isNull(outputDto.getOrderAmount()) ? BigDecimal.ZERO
            : outputDto.getOrderAmount()));

        if (CollectionUtils.isNotEmpty(outputDto.getItemList())) {
            List<ItemsDto> itemList = new ArrayList<>();
            outputDto.getItemList().forEach(item -> {
                ItemsDto itemsDto = new ItemsDto();
                itemsDto.setItemId(item.getItemId());
                if (Objects.equals(ItemType.NORMAL.getCode(), item.getItemClassification())) {
                    itemsDto.setItemPrice(
                        item.getItemPrice());
                } else {
                    itemsDto.setItemPrice(
                        item.getItemPrice()
                            .multiply(new BigDecimal(-1)));
                }
                itemList.add(itemsDto);
            });
            taxValueInputDto.setItemList(itemList);
            GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
                .getTaxValue(taxValueInputDto);
            // 外税
            outDto.setForeignTax(
                taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));
            // 合計
            outDto.setTotal((Objects.isNull(outputDto.getOrderAmount()) ? BigDecimal.ZERO
                : outputDto.getOrderAmount())
                .add(taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen())));
        }

        outDto.setPriceDiscountAmount(null);

        outDto.setPriceDiscountRate(null);

        outDto.setUnpay(outDto.getTotal());

        outDto.setReceivablesNo(outDto.getReceptionNo());

        return outDto;
    }

    /**
     * 席解除一覧取得.
     *
     * @param inputDto 取得条件
     * @return 席解除一覧情報
     */
    @Override
    public GetSeatReleaseListOutputDto getSeatReleaseList(GetSeatReleaseListInputDto inputDto) {

        // 結果DTO初期化
        GetSeatReleaseListOutputDto outDto = new GetSeatReleaseListOutputDto();

        // 席解除一覧情報取得
        List<Map<String, Object>> seatReleaseData = receivablesRepository
            .findSeatReleaseInfoByStoreId(inputDto.getStoreId(), Flag.OFF.getCode().toString(),
                PayStatus.PAY_NOT_ALREADY.getCode());

        // 席解除リスト
        List<SeatReleaseDto> seatReleaseList = new ArrayList<>();

        // 席解除リスト設定
        if (seatReleaseData != null) {
            seatReleaseData.forEach(stringObjectMap -> seatReleaseList.add(JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), SeatReleaseDto.class)));
        }
        outDto.setSeatReleaseList(seatReleaseList);

        return outDto;
    }

    /**
     * 席解除.
     *
     * @param inputDto 席解除データ
     */
    @Override
    public void seatRelease(SeatReleaseInputDto inputDto) {

        // 注文サマリーデータをロックする
        orderSummaryRepository.findByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
            inputDto.getReceivablesId(), Flag.OFF.getCode());

        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 席解除する
        orderSummaryRepository.updateSeatReleaseByReceivablesId(inputDto.getStoreId(),
            inputDto.getReceivablesId(), Flag.ON.getCode().toString(), inputDto.getTableId(),
            userOperCd, ZonedDateTime.now());

        List<String> receivablesIdList = relationTableRepository
            .getReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId());

        relationTableRepository.deleteByStoreIdAndRealReceivablesId(inputDto.getStoreId(),
            inputDto.getReceivablesId());

        receivablesRepository.deleteReceivables(inputDto.getStoreId(), receivablesIdList);

        orderSummaryRepository.deleteReceivables(inputDto.getStoreId(), receivablesIdList);

        tokenRepository.deleteReceivables(inputDto.getStoreId(), inputDto.getReceivablesId());
    }

    /**
     * 注文未確認商品一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文未確認商品一覧情報
     */
    @Override
    public GetUnCfmOrderItemListOutputDto getUnCfmOrderItemList(
        GetUnCfmOrderItemListInputDto inputDto) {

        // 結果DTO初期化
        GetUnCfmOrderItemListOutputDto outDto = new GetUnCfmOrderItemListOutputDto();

        // 注文商品リストを取得する
        List<Map<String, Object>> orderItemListData = orderSummaryRepository
            .findUnCfmOrderItemListByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(), inputDto.getLanguages(),
                ItemStatus.UNCONFIRMED.getCode());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderItemListData)) {
            outDto.setUnconfirmedAmount(BigDecimal.ZERO);
            outDto.setItemList(new ArrayList<>());
            return outDto;
        }

        // 注文商品リストを編集する
        List<OrderItemDetailDataDto> orderItemList = new ArrayList<>();
        orderItemListData.forEach(stringObjectMap -> orderItemList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                OrderItemDetailDataDto.class)));

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderItemDetailDataDto>> grpByOrderId = orderItemList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        // 商品情報リストを編集する
        List<UnCfmItemInfoDto> itemList = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDetailDataDtos) -> {
            UnCfmItemInfoDto item = beanMapper
                .map(orderItemDetailDataDtos.get(0), UnCfmItemInfoDto.class);
            orderItemDetailDataDtos.forEach(orderItemDetailDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDetailDataDto.getOptionName())) {
                    String optionName = orderItemDetailDataDto.getOptionName();
                    if (orderItemDetailDataDto.getOptionItemCount() != null
                        && 0 < orderItemDetailDataDto.getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDetailDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDetailDataDto.getOptionName(),
                            orderItemDetailDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getItemOption())) {
                        item.setItemOption(optionName);
                    } else {
                        item.setItemOption(item.getItemOption() + "," + optionName);
                    }
                }
            });
            itemList.add(item);
        });

        // 未確認金額設定
        outDto.setUnconfirmedAmount(itemList.stream().map(
            unCfmItemInfoDto -> unCfmItemInfoDto.getItemPrice())
            .reduce(BigDecimal.ZERO, BigDecimal::add));

        // 商品リスト設定
        outDto.setItemList(itemList.stream().sorted(Comparator
            .comparing(UnCfmItemInfoDto::getOrderId, Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList()));

        return outDto;
    }

    /**
     * 注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 注文商品情報
     */
    @Override
    public AmountSoldOutputDto amountSold(AmountSoldInputDto inputDto, Pageable pageable) {

        // 検索開始日付設定
        String orderStartDate = inputDto.getStartDate();
        String orderEndDate = inputDto.getEndDate() + " 23:59:59";

        // 注文一覧情報取得
        Page<Map<String, Object>> amountSaleListMap = orderSummaryRepository
            .findSaleOrderByStoreId(inputDto.getStoreId(),
                orderStartDate, orderEndDate, CODE_GROUP_PAYMENT, inputDto.getPaymentMethod(),
                pageable);

        // 注文一覧情報取得
        BigDecimal paymentAmount = orderSummaryRepository
            .findSaleSummaryOrderByStoreId(inputDto.getStoreId(),
                orderStartDate, orderEndDate, CODE_GROUP_PAYMENT, inputDto.getPaymentMethod());

        // 注文一覧情報編集
        List<AmountSoldDto> amountSoldList = new ArrayList<>();
        amountSaleListMap.forEach(stringObjectMap -> {
            AmountSoldDto amountSoldDto = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), AmountSoldDto.class);
            amountSoldList.add(amountSoldDto);
        });

        // 結果DTO初期化
        AmountSoldOutputDto outDto = new AmountSoldOutputDto();

        // 注文一覧情報設定
        outDto.setAmountSold(
            new PageImpl<>(amountSoldList, amountSaleListMap.getPageable(),
                amountSaleListMap.getTotalPages()));
        outDto.setTotalPay(paymentAmount == null ? BigDecimal.ZERO : paymentAmount);
        // 注文総件数
        outDto.setSoldOrderCount(amountSaleListMap.getTotalElements());
        return outDto;
    }

    /**
     * 注文フラグ取得.
     *
     * @param inputDto 取得条件
     * @return 注文フラグ取得.
     */
    @Override
    public GetOrderFlagOutputDto getOrderFlag(GetOrderFlagInputDto inputDto) {

        // 結果DTO初期化
        GetOrderFlagOutputDto outDto = new GetOrderFlagOutputDto();

        Integer count = relationTableRepository
            .checkGroupOrder(inputDto.getStoreId(), inputDto.getReceivablesId());

        if (count == 0) {

            outDto.setOrderFlag(Flag.ON.getCode());
        } else {

            outDto.setOrderFlag(Flag.OFF.getCode());
        }

        return outDto;
    }

    /**
     * 注文廃棄.
     *
     * @param inputDto 取得条件
     */
    @Override
    @Transactional
    public void discardOrder(DiscardOrderInputDto inputDto) {

        List<String> receivablesIdList = new ArrayList<String>();
        receivablesIdList.add(inputDto.getReceivablesId());
        // 清空受付
        receivablesRepository.deleteReceivables(inputDto.getStoreId(), receivablesIdList);
        // 清空orderSummary
        orderSummaryRepository.deleteReceivables(inputDto.getStoreId(), receivablesIdList);
        //查询组内桌台数
        Integer count = relationTableRepository
            .getGroupTableNumber(inputDto.getStoreId(), inputDto.getReceivablesId());
        // 查询组ID
        RTable tableInfo = relationTableRepository
            .findByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
                inputDto.getReceivablesId(), Flag.OFF.getCode());
        //删除组内该受付信息
        relationTableRepository
            .deleteReceivables(inputDto.getStoreId(), inputDto.getReceivablesId());
        if (count == 2 && tableInfo != null) {
            relationTableRepository.deleteGroup(inputDto.getStoreId(), tableInfo.getGroupId());
        }
        tokenRepository.deleteReceivables(inputDto.getStoreId(), inputDto.getReceivablesId());
    }

    /**
     * 遷移先判断.
     *
     * @param inputDto 取得条件
     * @return 遷移先判断.
     */
    @Override
    public OrderTransferOutputDto orderTransfer(OrderTransferInputDto inputDto) {

        // 結果DTO初期化
        OrderTransferOutputDto outDto = new OrderTransferOutputDto();

        // テイクアウト区分保存Keyを生成する
        String takeoutFlagKey = inputDto.getReceivablesId() + "takeoutFlag";
        String currentValue = redisTemplate.opsForValue().get(takeoutFlagKey);

        // テイクアウトの場合利用不可
        if (TakeoutFlag.TAKE_OUT.getCode().equals(currentValue)) {
            outDto.setTransitionType(OrderTransfer.ITEMLIST.getCode());
            return outDto;
        }

        List<String> itemStatus = new ArrayList<>();
        itemStatus.add(MstItemType.COURSE.getCode());
        itemStatus.add(MstItemType.BUFFET.getCode());
        Integer ordinaryCount = oreceivablesRepository
            .getOrderNotBuffetCourseCount(inputDto.getStoreId(), inputDto.getReceivablesId(),
                itemStatus);
        if (0 < ordinaryCount) {
            outDto.setTransitionType(OrderTransfer.ITEMLIST.getCode());
            return outDto;
        }

        // 获取已确认的放题コース数
        Integer confirmedCount = oreceivablesRepository
            .getOrderBuffetCourseCount(inputDto.getStoreId(),
                inputDto.getReceivablesId(), ItemStatus.CONFIRMED.getCode(),
                MstItemType.COURSE.getCode(), MstItemType.BUFFET.getCode());

        // 获取未确认的放题コース数
        Integer unconfirmedCount = oreceivablesRepository
            .getOrderBuffetCourseCount(inputDto.getStoreId(),
                inputDto.getReceivablesId(), ItemStatus.UNCONFIRMED.getCode(),
                MstItemType.COURSE.getCode(), MstItemType.BUFFET.getCode());

        // 获取店铺放题コース数
        Integer buffetCourseCount = itemRepository.getBuffetCourseCount(inputDto.getStoreId(),
            MstItemType.COURSE.getCode(), MstItemType.BUFFET.getCode(),
            ItemShowStatus.OVERHEAD.getCode());

        // 获取用户选择放题适用区分
        MStore store = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        if (unconfirmedCount != 0) {
            outDto.setTransitionType(OrderTransfer.PROMPT.getCode());
        } else if (confirmedCount != 0) {
            outDto.setTransitionType(OrderTransfer.ITEMLIST.getCode());
        } else if (buffetCourseCount != 0 && Objects
            .equals(Flag.ON.getCode().toString(), store.getCustomerSelectFlag())) {
            outDto.setTransitionType(OrderTransfer.BUFFETCCOURSELIST.getCode());
        } else {
            outDto.setTransitionType(OrderTransfer.ITEMLIST.getCode());
        }

        return outDto;
    }

    /**
     * コース＆放题詳細.
     *
     * @param inputDto 取得条件
     * @return コース＆放题詳細.
     */
    @Override
    public CourseBuffetDetailOutputDto courseBuffetDetail(CourseBuffetDetailInputDto inputDto) {

        // 結果DTO初期化
        CourseBuffetDetailOutputDto outDto = new CourseBuffetDetailOutputDto();

        // コース＆放题基本信息取得
        BuffetCourseItemDto item = itemRepository.getBuffetCourseItemInfo(inputDto.getStoreId(),
            inputDto.getCourseBuffetId());

        // コース＆放题名称
        outDto.setCourseBuffetName(JSONObject.parseObject(
            item.getItemName()).getString(inputDto.getLanguages()));

        // コース＆放题金额
        outDto.setCourseBuffetPrice(item.getItemPrice());

        // 税区分
        outDto.setTaxCode(TaxType.getTaxText(item.getTaxCode()));

        if (item.getItemType().equals(MstItemType.COURSE.getCode())) {

            // コース说明
            outDto.setCourseIntroduction(JSONObject.parseObject(
                item.getItemInfo()).getString(inputDto.getLanguages()));

            // コース商品取得
            List<ItemDto> itemList = buffetItemRepository.getItemList(inputDto.getStoreId(),
                inputDto.getCourseBuffetId());

            for (ItemDto itemDto : itemList) {
                itemDto.setItemName(JSONObject.parseObject(
                    itemDto.getItemName()).getString(inputDto.getLanguages()));
            }
            outDto.setCourseItemList(itemList);
        } else {

            // コース说明
            outDto.setBuffetIntroduction(JSONObject.parseObject(
                item.getItemInfo()).getString(inputDto.getLanguages()));

            // コース商品取得
            List<ItemDto> itemList = buffetItemRepository.getItemList(inputDto.getStoreId(),
                inputDto.getCourseBuffetId());

            for (ItemDto itemDto : itemList) {
                itemDto.setItemName(JSONObject.parseObject(
                    itemDto.getItemName()).getString(inputDto.getLanguages()));
            }

            outDto.setBuffetItemList(itemList);

            // 获取放题包含的コース的コースID
            RItem itemInfo = ritemRepository
                .findByStoreIdAndBuffetIdAndDelFlag(inputDto.getStoreId(),
                    inputDto.getCourseBuffetId(), Flag.OFF.getCode());

            if (null != itemInfo) {

                // コース商品取得
                List<ItemDto> courseItemList = buffetItemRepository
                    .getItemList(inputDto.getStoreId(), itemInfo.getItemId());

                for (ItemDto itemDto : courseItemList) {
                    itemDto.setItemName(JSONObject.parseObject(
                        itemDto.getItemName()).getString(inputDto.getLanguages()));
                }
                outDto.setCourseItemList(courseItemList);
            }
        }

        return outDto;
    }


    /**
     * コース＆放题一览取得.
     *
     * @param inputDto 取得条件
     * @return コース＆放题一览
     */
    @Override
    public CourseBuffetListOutputDto getCourseBuffetList(CourseBuffetListInputDto inputDto) {

        // コース＆放題一覧取得
        List<String> itemTypeList = new ArrayList<>();
        itemTypeList.add(MstItemType.COURSE.getCode());
        itemTypeList.add(MstItemType.BUFFET.getCode());
        List<Map<String, Object>> itemMapList = itemRepository
            .getCourseBuffetList(inputDto.getStoreId(), inputDto.getLanguages(), itemTypeList,
                ItemShowStatus.OVERHEAD.getCode());

        List<CourseBuffetDto> courseBuffetList = new ArrayList<>();
        itemMapList.forEach(stringObjectMap -> courseBuffetList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                CourseBuffetDto.class)));

        //コース＆放题一览取得
        CourseBuffetListOutputDto outDto = new CourseBuffetListOutputDto();
        outDto.setCourseBuffetList(courseBuffetList);
        return outDto;
    }

    /**
     * コース＆放题注文確認取得.
     *
     * @param inputDto 取得条件
     * @return コース＆放题注文確認取得.
     */
    @Override
    public CourseBuffetConfirmOutputDto courseBuffetConfirm(CourseBuffetConfirmInputDto inputDto) {

        List<ItemsDto> itemList = new ArrayList<>();
        inputDto.getItemList().forEach(item -> {
            ItemsDto itemsDto = new ItemsDto();
            itemsDto.setItemId(item.getItemId());
            itemsDto.setItemPrice(item.getItemPrice());
            itemList.add(itemsDto);
        });

        // 消費税取得
        GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
        taxValueInputDto.setStoreId(inputDto.getStoreId());
        taxValueInputDto.setTakeoutFlag(TakeoutFlag.EAT_IN.getCode());
        taxValueInputDto.setItemList(itemList);
        GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
            .getTaxValue(taxValueInputDto);

        // 小计金额初始化
        BigDecimal subTotal = BigDecimal.ZERO;

        for (ItemsDto itemsDto : itemList) {
            subTotal = subTotal.add(itemsDto.getItemPrice());
        }

        // 結果DTO初期化
        CourseBuffetConfirmOutputDto outDto = new CourseBuffetConfirmOutputDto();

        // 小計
        outDto.setSubtotalPrice(subTotal);
        // 外税
        outDto.setForeignTax(
            taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));
        // 合計
        outDto.setTotalPrice(outDto.getSubtotalPrice().add(outDto.getForeignTax()));

        return outDto;
    }

    /**
     * コース＆放题注文確定.
     *
     * @param inputDto 取得条件
     * @return コース＆放题注文確定
     */
    @Override
    public CourseBuffetOrderOutputDto courseBuffetRegist(CourseBuffetRegistInputDto inputDto) {

        try {

            // 注文サマリテーブル登録
            OOrderSummary orderSummary = new OOrderSummary();
            // 店舗ID
            orderSummary.setStoreId(inputDto.getStoreId());
            // 受付id
            orderSummary.setReceivablesId(inputDto.getReceivablesId());
            // 削除フラグ
            orderSummary.setDelFlag(Flag.OFF.getCode());
            // 受付時間を取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

            // 注文サマリテーブルに存在チェック
            String orderSummaryId = orderSummaryRepository
                .findByReceivablesId(orderSummary.getStoreId(), orderSummary.getReceivablesId());

            // 存在チェック
            if (StringUtils.isNotEmpty(orderSummaryId)) {

                // 更新処理
                orderSummaryRepository.updateByPaymentAmountAndUpdDateTimeAndUpdOperCd(
                    orderSummary.getStoreId(),
                    orderSummary.getReceivablesId(),
                    inputDto.getOrderAmount(),
                    CommonConstants.OPER_CD_MOBILE, nowDateTime);
            } else {

                // 登録処理
                OReceivables receivables = oreceivablesRepository
                    .findByStoreIdAndDelFlagAndReceivablesId(inputDto.getStoreId(),
                        Flag.OFF.getCode(), inputDto.getReceivablesId());

                // 受付ID取得
                orderSummaryId = itemInfoSharedService
                    .getReceivablesId(inputDto.getStoreId(), nowDateTime);
                // 注文サマリID
                orderSummary.setOrderSummaryId(orderSummaryId);
                // 顧客人数
                orderSummary.setCustomerCount(receivables.getCustomerCount());
                // テーブルID
                orderSummary.setTableId(inputDto.getTableId());
                // 注文額
                orderSummary.setOrderAmount(inputDto.getOrderAmount());
                // 支払額 スマホで注文確認する際に支払額がデフォルト「０」を設定する,会計するとき計算してください
                orderSummary.setPaymentAmount(BigDecimal.ZERO);
                // 支払区分
                orderSummary.setPaymentType(PaymentType.DEFERRED_PAYMENT.getCode());
                // テイクアウト区分
                orderSummary.setTakeoutFlag(TakeoutFlag.EAT_IN.getCode());
                // 注文状態
                if (inputDto.getTableId() == null) {
                    orderSummary.setOrderStatus(OrderStatus.TENTATIVE_ORDER.getCode());
                    orderSummary.setSeatRelease(Flag.ON.getCode().toString());
                } else {
                    orderSummary.setOrderStatus(OrderStatus.ORDER.getCode());
                    orderSummary.setSeatRelease(Flag.OFF.getCode().toString());
                }
                // 登録日時
                orderSummary.setInsDateTime(nowDateTime);
                // 登録者
                orderSummary.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                // 更新日時
                orderSummary.setUpdDateTime(nowDateTime);
                // 更新者
                orderSummary.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                // バージョン
                orderSummary.setVersion(0);
                orderSummaryRepository.save(orderSummary);
            }

            CourseBuffetOrderOutputDto outputDto = new CourseBuffetOrderOutputDto();
            outputDto.setOrderSummaryId(orderSummaryId);

            // テイクアウト区分設定
            // テイクアウト区分保存Keyを生成する
            String takeoutFlagKey = inputDto.getReceivablesId() + "takeoutFlag";
            // テイクアウト区分設定
            redisTemplate.opsForValue().set(takeoutFlagKey, TakeoutFlag.EAT_IN.getCode());
            redisTemplate.expire(takeoutFlagKey, 10800L, TimeUnit.SECONDS);

            // 注文テーブル登録
            OOrder order = new OOrder();
            // 店舗ID
            order.setStoreId(inputDto.getStoreId());
            // 注文サマリID
            order.setOrderSummaryId(orderSummaryId);
            // 削除フラグ
            order.setDelFlag(Flag.OFF.getCode());

            // 注文IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_order"); // テーブル名
            getSeqNoInputDto.setItem("order_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 注文ID
            order.setOrderId(getSeqNo.getSeqNo());
            // 支払状態
            order.setPayStatus(PayStatus.PAY_NOT_ALREADY.getCode());
            // 外税金額
            order.setForeignTax(BigDecimal.ZERO);
            // 注文日時
            order.setOrderTime(nowDateTime);
            // 注文区分
            order.setOrderType(OrderType.MOBILE.getCode());
            // 登録日時
            order.setInsDateTime(nowDateTime);
            // 登録者
            order.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            order.setUpdDateTime(nowDateTime);
            // 更新者
            order.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // バージョン
            order.setVersion(0);
            orderRepository.save(order);
            outputDto.setOrderId(order.getOrderId());

            // 商品状態
            String itemStatus = ItemStatus.UNCONFIRMED.getCode();

            // 店舗情報取得
            MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());

            // 検索結果0件以外の場合
            if (storeInfo != null && Objects
                .equals(StaffCheckFlag.NO.getCode(), storeInfo.getCourseBuffetCheck())) {
                itemStatus = ItemStatus.CONFIRMED.getCode();
            }

            for (OrderBuffetCourseDto orderItemsDto : inputDto.getItemList()) {

                // 注文明細テーブル登録
                OOrderDetails orderDetails = new OOrderDetails();
                // 店舗ID
                orderDetails.setStoreId(inputDto.getStoreId());
                // 削除フラグ
                orderDetails.setDelFlag(Flag.OFF.getCode());
                // 注文ID
                orderDetails.setOrderId(order.getOrderId());
                // 商品ID
                orderDetails.setItemId(orderItemsDto.getItemId());
                // 単価
                orderDetails.setItemPrice(orderItemsDto.getItemPrice());
                // 商品個数
                orderDetails.setItemCount(orderItemsDto.getItemCount());
                // 商品区分
                orderDetails.setItemClassification(ItemType.NORMAL.getCode());
                // 商品状態
                orderDetails.setItemStatus(itemStatus);

                // 注文明細IDのシーケンスNo取得
                getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
                getSeqNoInputDto.setItem("order_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                //注文明細ID
                orderDetails.setOrderDetailId(getSeqNo.getSeqNo());
                // 登録日時
                orderDetails.setInsDateTime(nowDateTime);
                // 登録者
                orderDetails.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                // 更新日時
                orderDetails.setUpdDateTime(nowDateTime);
                // 更新者
                orderDetails.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                // バージョン
                orderDetails.setVersion(0);
                orderDetails.setItemReturnId(null);
                orderDetailsRepository.save(orderDetails);
            }

            // 支払区分情報取得
            MControl control = controlRepository
                .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                    CODE_GROUP_PAYMENT, Flag.OFF.getCode());

            // 検索結果0件以外の場合
            if (storeInfo != null && Objects
                .equals(StaffCheckFlag.NO.getCode(), storeInfo.getCourseBuffetCheck()) && Objects
                .equals(control.getControlCode(), PaymentType.DEFERRED_PAYMENT.getCode())
                && !Objects.isNull(inputDto.getTableId())) {

                OPrintQueue orderData = new OPrintQueue();
                orderData.setStoreId(inputDto.getStoreId());
                orderData.setOrderSummaryId(orderSummaryId);
                orderData.setOrderId(order.getOrderId().toString());
                orderData.setPaymentAmount(inputDto.getOrderAmount());
                orderData.setPrintStatus(PrintStatus.UNPRINT.getCode());
                //1:後払い不要
                orderData.setStaffCheck("1");
                orderData.setDelFlag(Flag.OFF.getCode());
                orderData.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                ZonedDateTime dateTime = DateUtil.getNowDateTime();
                orderData.setInsDateTime(dateTime);
                orderData.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                orderData.setUpdDateTime(dateTime);
                orderData.setVersion(0);
                printQueueRepository.save(orderData);
            }

            return outputDto;
        } catch (Exception ex) {
            throw new BusinessException("2003",
                ResultMessages.error().add("e.qr.ph.033", (Object) null), ex);
        }
    }

    /**
     * 商品グループ化.
     *
     * @param mapItem 商品伝票印刷
     * @return グループ化条件
     */
    private String fetchItemGroupKey(Map<String, Object> mapItem) {
        return mapItem.get("orderSummaryId").toString() + mapItem.get("orderId").toString();
    }

    /**
     * 出前注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 出前注文情報
     */
    @Override
    public GetDeliveryOrderListOutputDto getDeliveryOrderList(GetDeliveryOrderListInputDto inputDto,
        Pageable pageable) {

        // 出前注文一覧情報取得
        Page<Map<String, Object>> deliveryOrderListMap = deliveryOrderSummaryRepository
            .findDeliveryOrderInfoByStoreId(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_TYPE_FLAG,
                CommonConstants.CODE_GROUP_DELIVERY_STATUS,
                inputDto.getDeliveryTypeFlag(), inputDto.getStatus(),
                StringUtils.trimToEmpty(inputDto.getDeliveryOrderTimeFrom()),
                StringUtils.trimToEmpty(inputDto.getDeliveryOrderTimeTo()),
                pageable);

        // 出前注文一覧情報を編集する
        List<DeliveryOrderInfoDto> deliveryOrderList = new ArrayList<>();
        deliveryOrderListMap.forEach(stringObjectMap -> {
            DeliveryOrderInfoDto deliveryOrderInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), DeliveryOrderInfoDto.class);
            deliveryOrderList.add(deliveryOrderInfo);
        });

        // 出前仕方フラグ情報を取得する
        List<Map<String, Object>> deliveryTypeFlagList = codeRepository
            .findDeliveryTypeFlagInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_TYPE_FLAG);

        // 出前仕方フラグ情報を変換する
        List<DeliveryTypeFlagDto> deliveryTypeFlagDtoList = new ArrayList<>();
        deliveryTypeFlagList.forEach(stringObjectMap -> deliveryTypeFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), DeliveryTypeFlagDto.class)));

        // 出前状態情報を取得する
        List<Map<String, Object>> deliveryStatusList = codeRepository
            .findDeliveryStatusInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_STATUS);

        // 出前状態情報を変換する
        List<DeliveryStatusDto> deliveryStatusDtoList = new ArrayList<>();
        deliveryStatusList.forEach(stringObjectMap -> deliveryStatusDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), DeliveryStatusDto.class)));

        // 結果DTO初期化
        GetDeliveryOrderListOutputDto outDto = new GetDeliveryOrderListOutputDto();

        // 出前注文一覧情報設定
        outDto.setDeliveryOrderList(
            new PageImpl<>(deliveryOrderList, deliveryOrderListMap.getPageable(),
                deliveryOrderListMap.getTotalPages()));

        // 出前注文総件数
        outDto.setDeliveryOrderCount(deliveryOrderListMap.getTotalElements());

        // 出前仕方フラグ情報を設定する
        outDto.setDeliveryTypeFlagList(deliveryTypeFlagDtoList);

        // 出前状態フラグ情報を設定する
        outDto.setStatusList(deliveryStatusDtoList);

        return outDto;
    }

    /**
     * 出前注文詳細情報取得.
     *
     * @param inputDto 取得条件
     * @return 出前注文詳細情報取得.
     */
    @Override
    public GetDeliveryOrderDetailInfoOutputDto getDeliveryOrderDetailInfo(
        GetDeliveryOrderDetailInfoInputDto inputDto) {

        // 結果DTO初期化
        GetDeliveryOrderDetailInfoOutputDto outDto = new GetDeliveryOrderDetailInfoOutputDto();

        // 出前注文情報を取得する
        Map<String, Object> orderData = deliveryOrderSummaryRepository
            .findDeliveryOrderDetailInfoByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(),
                CommonConstants.CODE_GROUP_DELIVERY_TYPE_FLAG,
                CommonConstants.CODE_GROUP_DELIVERY_STATUS, inputDto.getLanguages());

        if (orderData != null) {
            outDto = JSONObject
                .parseObject(JSONObject.toJSONString(orderData),
                    GetDeliveryOrderDetailInfoOutputDto.class);
        }

        // インプット情報をDTOにセットする
        GetOrderItemListInputDto itemInputDto = beanMapper
            .map(inputDto, GetOrderItemListInputDto.class);

        // 注文商品リスト取得サービス処理を実行する
        GetOrderItemListOutputDto outputDto = getDeliveryOrderItemList(itemInputDto);

        // 小計
        outDto.setOrderAmount(outputDto.getOrderAmount());

        // 消費税取得
        GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
        taxValueInputDto.setStoreId(inputDto.getStoreId());

        taxValueInputDto.setTakeoutFlag(TakeoutFlag.TAKE_OUT.getCode());

        // 外税
        outDto.setForeignTax(BigDecimal.ZERO);
        // 合計
        outDto.setTotalAmount((Objects.isNull(outputDto.getOrderAmount()) ? BigDecimal.ZERO
            : outputDto.getOrderAmount()));

        if (CollectionUtils.isNotEmpty(outputDto.getItemList())) {
            List<ItemsDto> itemList = new ArrayList<>();
            outputDto.getItemList().forEach(item -> {
                ItemsDto itemsDto = new ItemsDto();
                itemsDto.setItemId(item.getItemId());
                if (Objects.equals(ItemType.NORMAL.getCode(), item.getItemClassification())) {
                    itemsDto.setItemPrice(
                        item.getItemPrice());
                } else {
                    itemsDto.setItemPrice(
                        item.getItemPrice()
                            .multiply(new BigDecimal(-1)));
                }
                itemList.add(itemsDto);
            });
            taxValueInputDto.setItemList(itemList);
            GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
                .getTaxValue(taxValueInputDto);
            // 外税
            outDto.setForeignTax(
                taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));
            // 合計
            outDto.setTotalAmount((Objects.isNull(outputDto.getOrderAmount()) ? BigDecimal.ZERO
                : outputDto.getOrderAmount())
                .add(taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen())));
        }

        // 支払方式
        String paymentMethod = deliveryOrderSummaryRepository
            .findDeliveryPaymentMethodByOrderSummaryId(
                inputDto.getStoreId(), inputDto.getReceivablesId(),
                CommonConstants.CODE_GROUP_PAYMENT);

        outDto.setPaymentMethod(paymentMethod);

        List<ItemsDetailDto> itemList = new ArrayList<>();
        outputDto.getItemList().forEach(stringObjectMap -> itemList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), ItemsDetailDto.class)));

        outDto.setItemList(itemList);

        return outDto;
    }

    /**
     * 出前注文編集情報取得.
     *
     * @param inputDto 取得条件
     * @return 出前注文編集情報取得.
     */
    @Override
    public GetDeliveryOrderEditInfoOutputDto getDeliveryOrderEditInfo(
        GetDeliveryOrderEditInfoInputDto inputDto) {

        // 結果DTO初期化
        GetDeliveryOrderEditInfoOutputDto outDto = new GetDeliveryOrderEditInfoOutputDto();

        // 出前注文情報を取得する
        Map<String, Object> orderData = deliveryOrderSummaryRepository
            .findDeliveryOrderEditInfoByReceivablesId(inputDto.getStoreId(),
                inputDto.getOrderSummaryId());

        if (orderData != null) {
            outDto = JSONObject
                .parseObject(JSONObject.toJSONString(orderData),
                    GetDeliveryOrderEditInfoOutputDto.class);
        }

        // 指定店舗情報を取得する
        MStore storeData = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // システム時間
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // 配達時間
        Integer cateringIntervalTime = storeData.getCateringIntervalTime();

        // 間隔時間
        Integer intervalTime = storeData.getIntervalTime();

        // 営業開始時間
        ZonedDateTime startTime = DateUtil.getZonedDateByString(
            DateUtil.getZonedDateString(
                nowDateTime, "yyyy-MM-dd HH:mm:ss").split(" ")[0]
                + " " + storeData.getStartTime() + ":00", "yyyy-MM-dd HH:mm:ss");

        // 注文締め時間
        ZonedDateTime orderEndTime = DateUtil.getZonedDateByString(
            DateUtil.getZonedDateString(
                nowDateTime, "yyyy-MM-dd HH:mm:ss").split(" ")[0]
                + " " + storeData.getOrderEndTime() + ":59", "yyyy-MM-dd HH:mm:ss");

        if (startTime.isBefore(orderEndTime)) {
            if (nowDateTime.isBefore(startTime) || nowDateTime.isEqual(startTime)) {
                startTime = startTime.minusDays(1L);
                orderEndTime = orderEndTime.minusDays(1L);
            }
        } else {
            if (nowDateTime.isBefore(startTime) || nowDateTime.isEqual(startTime)) {
                startTime = startTime.minusDays(1L);
            } else {
                orderEndTime = orderEndTime.plusDays(1L);
            }
        }

        // 配達時間リストを作成する
        ZonedDateTime cateringStartTime = startTime.plusMinutes(cateringIntervalTime);
        ZonedDateTime cateringEndTime = cateringStartTime.plusMinutes(intervalTime);

        List<CateringTimeDto> cateringTimeList = new ArrayList<>();

        while (cateringStartTime.isBefore(orderEndTime) || cateringStartTime
            .isEqual(orderEndTime)) {
            String cateringTime = DateUtil.getZonedDateString(cateringStartTime, "HH:mm") + "～" +
                DateUtil.getZonedDateString(cateringEndTime, "HH:mm");

            CateringTimeDto cateringTimeData = new CateringTimeDto();
            cateringTimeData.setCateringTime(cateringTime);
            cateringTimeList.add(cateringTimeData);

            cateringStartTime = cateringEndTime;
            cateringEndTime = cateringEndTime.plusMinutes(intervalTime);
        }

        outDto.setCateringTimeList(cateringTimeList);

        // 持帰り時間
        Integer takeoutIntervalTime = storeData.getTakeoutIntervalTime();

        // 持帰り時間リストを作成する
        ZonedDateTime takeoutStartTime = startTime.plusMinutes(takeoutIntervalTime);
        ZonedDateTime takeoutEndTime = takeoutStartTime.plusMinutes(intervalTime);

        List<TakeoutTimeDto> takeoutTimeList = new ArrayList<>();

        while (takeoutStartTime.isBefore(orderEndTime) || takeoutStartTime.isEqual(orderEndTime)) {
            String takeoutTime = DateUtil.getZonedDateString(takeoutStartTime, "HH:mm") + "～" +
                DateUtil.getZonedDateString(takeoutEndTime, "HH:mm");

            TakeoutTimeDto takeoutTimeData = new TakeoutTimeDto();
            takeoutTimeData.setTakeoutTime(takeoutTime);
            takeoutTimeList.add(takeoutTimeData);

            takeoutStartTime = takeoutEndTime;
            takeoutEndTime = takeoutEndTime.plusMinutes(intervalTime);
        }

        outDto.setTakeoutTimeList(takeoutTimeList);

        // 出前仕方フラグ情報を取得する
        List<Map<String, Object>> deliveryTypeFlagList = codeRepository
            .findDeliveryTypeFlagInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_TYPE_FLAG);

        // 出前仕方フラグ情報を変換する
        List<DeliveryTypeFlagDto> deliveryTypeFlagDtoList = new ArrayList<>();
        deliveryTypeFlagList.forEach(stringObjectMap -> deliveryTypeFlagDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), DeliveryTypeFlagDto.class)));

        outDto.setDeliveryTypeFlagList(deliveryTypeFlagDtoList);

        // 出前状態情報を取得する
        List<Map<String, Object>> deliveryStatusList = codeRepository
            .findDeliveryStatusInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_DELIVERY_STATUS);

        // 出前状態情報を変換する
        List<DeliveryStatusDto> deliveryStatusDtoList = new ArrayList<>();
        deliveryStatusList.forEach(stringObjectMap -> deliveryStatusDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), DeliveryStatusDto.class)));

        outDto.setStatusList(deliveryStatusDtoList);

        // 配達都道府県リストを取得する
        List<Map<String, Object>> prefectureData =
            deliveryAreaRepository
                .findPrefectureByStoreId(inputDto.getStoreId(), inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(prefectureData)) {
            outDto.setPrefectureList(new ArrayList<>());
        }

        // 配達都道府県リストを編集する
        List<PrefectureListDto> prefectureList = new ArrayList<>();
        prefectureData.forEach(stringObjectMap -> prefectureList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                PrefectureListDto.class)));

        outDto.setPrefectureList(prefectureList);

        // 配達の場合
        if (DeliveryTypeFlag.CATERING.getCode().equals(outDto.getDeliveryTypeFlag())) {

            // 配達市区町村リストを取得する
            List<Map<String, Object>> cityData =
                deliveryAreaRepository.findCityByStoreIdAndPrefectureId(inputDto.getStoreId(),
                    outDto.getPrefectureId(), inputDto.getLanguages());

            // 検索結果0件の場合
            if (CollectionUtils.isEmpty(cityData)) {
                outDto.setCityList(new ArrayList<>());
            }

            // 配達市区町村リストを編集する
            List<CityListDto> cityList = new ArrayList<>();
            cityData.forEach(stringObjectMap -> cityList.add(
                JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                    CityListDto.class)));

            outDto.setCityList(cityList);

            // 配達町域番地リストを取得する
            List<Map<String, Object>> blockData =
                deliveryAreaRepository
                    .findCityByStoreIdAndPrefectureIdAndCityId(inputDto.getStoreId(),
                        outDto.getPrefectureId(), outDto.getCityId(), inputDto.getLanguages());

            // 検索結果0件の場合
            if (CollectionUtils.isEmpty(blockData)) {
                outDto.setBlockList(new ArrayList<>());
            }

            // 配達町域番地リストを編集する
            List<BlockListDto> blockList = new ArrayList<>();
            blockData.forEach(stringObjectMap -> blockList.add(
                JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                    BlockListDto.class)));

            outDto.setBlockList(blockList);
        }

        return outDto;
    }

    /**
     * 出前注文商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 注文商品リスト
     */
    public GetOrderItemListOutputDto getDeliveryOrderItemList(GetOrderItemListInputDto inputDto) {

        // 結果DTO初期化
        GetOrderItemListOutputDto outDto = new GetOrderItemListOutputDto();

        // 注文商品リストを取得する
        List<Map<String, Object>> orderItemData = deliveryOrderSummaryRepository
            .findDeliveryOrderItemListByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(),
                inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderItemData)) {
            outDto.setItemList(new ArrayList<>());
            return outDto;
        }

        // 注文商品リストを編集する
        List<OrderItemDetailDataDto> orderItemList = new ArrayList<>();
        orderItemData.forEach(stringObjectMap -> orderItemList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                OrderItemDetailDataDto.class)));

        // 注文基本情報設定
        beanMapper.map(orderItemList.get(0), outDto);

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderItemDetailDataDto>> grpByOrderId = orderItemList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        // 商品情報リストを編集する
        List<OrderItemDetailInfoDto> itemList = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDetailDataDtos) -> {
            OrderItemDetailInfoDto item = beanMapper
                .map(orderItemDetailDataDtos.get(0), OrderItemDetailInfoDto.class);
            orderItemDetailDataDtos.forEach(orderItemDetailDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDetailDataDto.getOptionName())) {
                    String optionName = orderItemDetailDataDto.getOptionName();
                    if (orderItemDetailDataDto.getOptionItemCount() != null
                        && 0 < orderItemDetailDataDto.getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDetailDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDetailDataDto.getOptionName(),
                            orderItemDetailDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getItemOption())) {
                        item.setItemOption(optionName);
                    } else {
                        item.setItemOption(item.getItemOption() + "," + optionName);
                    }
                }
            });
            itemList.add(item);
        });

        // 商品リスト設定
        outDto.setItemList(itemList.stream().sorted(Comparator
            .comparing(OrderItemDetailInfoDto::getOrderId, Comparator.nullsLast(String::compareTo)))
            .collect(Collectors.toList()));

        return outDto;
    }

    /**
     * 店舗指定区域取得.
     *
     * @param inputDto 取得条件
     * @return エリア情報
     */
    @Override
    public GetSelectedAreaListOutputDto getSelectedAreaList(GetSelectedAreaListInputDto inputDto) {

        // 店舗指定区域取得
        GetSelectedAreaListOutputDto outDto = new GetSelectedAreaListOutputDto();

        List<Map<String, Object>> areaList = new ArrayList<>();

        // 市区町村一覧取得
        if (AreaType.PREFECTURE.getCode().equals(inputDto.getAreaType())) {
            areaList = deliveryAreaRepository.findSelectedCityList(inputDto.getStoreId(),
                inputDto.getPrefectureId(), inputDto.getLanguages());
            // 町域番地一覧取得
        } else {
            areaList = deliveryAreaRepository.findSelectedBlockList(inputDto.getStoreId(),
                inputDto.getPrefectureId(), inputDto.getCityId(), inputDto.getLanguages());
        }

        // 店舗指定区域を変換する
        List<GetAreaDevInfoDto> areaInfoDtoList = new ArrayList<>();
        areaList.forEach(stringObjectMap -> areaInfoDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetAreaDevInfoDto.class)));

        outDto.setAreaList(areaInfoDtoList);

        // インプット情報をDTOにセットする
        return outDto;
    }

    /**
     * 出前注文編集.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void updateDeliveryOrder(UpdateDeliveryOrderInputDto inputDto) {
        // 時間取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // ユーザID
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        if (!"".equals(inputDto.getOrderSummaryId())) {

            // 出前注文サマリ情報をロックする
            ODeliveryOrderSummary deliveryOrderSummary = deliveryOrderSummaryRepository.
                findByStoreIdAndOrderSummaryIdAndDelFlag(inputDto.getStoreId(),
                    inputDto.getOrderSummaryId(), Flag.OFF.getCode());

            // 出前注文サマリ情報がNULLである場合
            if (deliveryOrderSummary == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // 支払済、または受付済の場合
            if (DeliveryStatus.PAID.getCode().equals(deliveryOrderSummary.getStatus())
                || DeliveryStatus.ACCEPTED.getCode().equals(deliveryOrderSummary.getStatus())) {
                // 出前開始時間
                String startTime = inputDto.getDeliveryTime()
                    .substring(0, inputDto.getDeliveryTime().indexOf("～"));
                // 出前終了時間
                String endTime = inputDto.getDeliveryTime()
                    .substring(inputDto.getDeliveryTime().indexOf("～") + 1);

                // 出前開始時間
                ZonedDateTime deliveryStartTime = DateUtil
                    .getZonedDateByString(
                        DateUtil.getZonedDateString(nowDateTime, "yyyy-MM-dd HH:mm:ss")
                            .split(" ")[0] + " "
                            + startTime + ":00", "yyyy-MM-dd HH:mm:ss");

                // 出前終了時間
                ZonedDateTime deliveryEndTime = DateUtil
                    .getZonedDateByString(
                        DateUtil.getZonedDateString(nowDateTime, "yyyy-MM-dd HH:mm:ss")
                            .split(" ")[0] + " "
                            + endTime + ":59", "yyyy-MM-dd HH:mm:ss");

                if (deliveryEndTime.isBefore(deliveryStartTime)) {
                    deliveryEndTime = deliveryEndTime.plusDays(1L);
                }

                if (nowDateTime.isAfter(deliveryStartTime)) {
                    deliveryStartTime = deliveryStartTime.plusDays(1L);
                    deliveryEndTime = deliveryEndTime.plusDays(1L);
                }

                // 出前注文サマリデータを変更する
                deliveryOrderSummaryRepository.
                    updateDeliveryOrder(inputDto.getStoreId(), inputDto.getOrderSummaryId(),
                        inputDto.getDeliveryTypeFlag(), inputDto.getStatus(), deliveryStartTime,
                        deliveryEndTime, inputDto.getCustomerName(), inputDto.getTelNumber(),
                        inputDto.getPrefectureId(), inputDto.getCityId(), inputDto.getBlockId(),
                        inputDto.getOther(),
                        inputDto.getMailAddress(), inputDto.getComment(), userOperCd, nowDateTime);
                // 配達中の場合
            } else if (DeliveryStatus.INDELIVERY.getCode()
                .equals(deliveryOrderSummary.getStatus())) {
                // 出前注文サマリデータを変更する
                deliveryOrderSummaryRepository.
                    updateDeliveryOrderInDelivery(inputDto.getStoreId(),
                        inputDto.getOrderSummaryId(),
                        inputDto.getDeliveryTypeFlag(), inputDto.getStatus(),
                        inputDto.getCustomerName(),
                        inputDto.getTelNumber(), inputDto.getPrefectureId(), inputDto.getCityId(),
                        inputDto.getBlockId(), inputDto.getOther(), inputDto.getMailAddress(),
                        inputDto.getComment(), userOperCd, nowDateTime);
            }
        }
    }

    /**
     * 注文確定.
     *
     * @param inputDto 注文確定情報
     * @return 処理結果
     */
    @Override
    public DeliveryOrderOutputDto registDeliveryOrder(DeliveryOrderInputDto inputDto) {

        DeliveryOrderOutputDto outDto = new DeliveryOrderOutputDto();

        try {

            // 欠品チェック
            List<MSellOff> sellOffList = sellOffRepository
                .findByStoreIdAndSellOffStartIsBeforeAndItemIdIsInAndDelFlag(
                    inputDto.getStoreId(), ZonedDateTime.now(),
                    inputDto.getItemList().stream().map(OrderItemsDto::getItemId)
                        .collect(Collectors.toList()), Flag.OFF.getCode());

            if (CollectionUtils.isNotEmpty(sellOffList)) {
                throw new BusinessException("2004",
                    ResultMessages.error().add("e.qr.ph.034", (Object) null));
            }

            // 指定店舗情報取得
            MStore store = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
                Flag.OFF.getCode());

            // 検索結果0件の場合
            if (store == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.003", (Object) null));
            }

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

            // 受付番号
            Integer receptionNo = receivablesRepository
                .getReceptionNoByReceptionTime(inputDto.getStoreId(), startTime, endTime);

            // 受付ID取得
            String receivablesId = itemInfoSharedService
                .getReceivablesId(inputDto.getStoreId(), nowDateTime);

            // 受付情報登録
            OReceivables receivables = new OReceivables();
            receivables.setStoreId(inputDto.getStoreId()); // 店舗ID
            receivables.setReceivablesId(receivablesId); // 受付ID
            receivables.setReceptionNo(receptionNo); // 受付番号
            receivables.setCustomerCount(1); // 顧客人数
            receivables.setReceptionTime(nowDateTime); // 受付日時
            receivables.setInsDateTime(nowDateTime); // 登録日時
            receivables.setInsOperCd(CommonConstants.OPER_CD_DELIVERY); // 登録者
            receivables.setUpdDateTime(nowDateTime); // 更新日時
            receivables.setUpdOperCd(CommonConstants.OPER_CD_DELIVERY); // 更新者
            receivables.setDelFlag(Flag.ON.getCode()); // 削除フラグ
            receivables.setVersion(0); // バージョン
            receivablesRepository.save(receivables);

            // 注文サマリID取得
            String orderSummaryId = itemInfoSharedService
                .getReceivablesId(inputDto.getStoreId(), nowDateTime);

            // 出前注文サマリテーブル登録
            ODeliveryOrderSummary deliveryOrderSummary = new ODeliveryOrderSummary();
            deliveryOrderSummary.setStoreId(inputDto.getStoreId());       // 店舗ID
            deliveryOrderSummary.setOrderSummaryId(orderSummaryId);       // 注文サマリID
            deliveryOrderSummary.setReceivablesId(receivablesId);         // 受付ID
            deliveryOrderSummary.setOrderAmount(inputDto.getOrderAmount());   // 注文金額
            deliveryOrderSummary.setCateringChargeAmount(inputDto.getCateringCharge()); // 配達費
            deliveryOrderSummary.setPaymentAmount(inputDto.getTotalAmount()); // 支払金額
            deliveryOrderSummary.setDeliveryTypeFlag(inputDto.getDeliveryTypeFlag()); // 出前区分
            deliveryOrderSummary.setStatus("1");   // 状態
            deliveryOrderSummary.setStartTime(inputDto.getDeliveryStartTime());  // 出前開始時間
            deliveryOrderSummary.setEndTime(inputDto.getDeliveryEndTime());   // 出前終了時間
            deliveryOrderSummary.setCustomerName(inputDto.getCustomerName());  // 氏名
            deliveryOrderSummary.setTelNumber(inputDto.getPhoneNumber());  // 電話番号
            deliveryOrderSummary.setPrefectureId(inputDto.getPrefectureId());  // 都道府県ID
            deliveryOrderSummary.setCityId(inputDto.getCityId()); // 市区町村ID
            deliveryOrderSummary.setBlockId(inputDto.getBlockId()); // 町域番地ID
            deliveryOrderSummary.setDeliveryOther(inputDto.getAddress());  // 住所
            deliveryOrderSummary.setMailAddress(inputDto.getEmail());  // メール
            deliveryOrderSummary.setComment(inputDto.getComment());  // 要望
            deliveryOrderSummary.setInsDateTime(nowDateTime); // 登録日時
            deliveryOrderSummary.setInsOperCd(CommonConstants.OPER_CD_DELIVERY); // 登録者
            deliveryOrderSummary.setUpdDateTime(nowDateTime); // 更新日時
            deliveryOrderSummary.setUpdOperCd(CommonConstants.OPER_CD_DELIVERY); // 更新者
            deliveryOrderSummary.setDelFlag(Flag.ON.getCode()); // 削除フラグ
            deliveryOrderSummary.setVersion(0); // バージョン
            deliveryOrderSummaryRepository.save(deliveryOrderSummary);

            // 注文テーブル登録
            OOrder order = new OOrder();
            // 店舗ID
            order.setStoreId(inputDto.getStoreId());
            // 注文サマリID
            order.setOrderSummaryId(orderSummaryId);
            // 削除フラグ
            order.setDelFlag(Flag.ON.getCode());

            // 注文IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_order"); // テーブル名
            getSeqNoInputDto.setItem("order_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_DELIVERY); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            Integer orderId = getSeqNo.getSeqNo();

            // 注文ID
            order.setOrderId(orderId);
            // 支払状態
            order.setPayStatus(PayStatus.PAY_ALREADY.getCode());
            // 外税金額
            order.setForeignTax(inputDto.getForeignTax());
            // 注文日時
            order.setOrderTime(nowDateTime);
            // 注文区分
            order.setOrderType(OrderType.DELIVERY.getCode());
            // 登録日時
            order.setInsDateTime(nowDateTime);
            // 登録者
            order.setInsOperCd(CommonConstants.OPER_CD_DELIVERY);
            // 更新日時
            order.setUpdDateTime(nowDateTime);
            // 更新者
            order.setUpdOperCd(CommonConstants.OPER_CD_DELIVERY);
            // バージョン
            order.setVersion(0);
            orderRepository.save(order);

            for (OrderItemsDto orderItemsDto : inputDto.getItemList()) {

                // 注文明細テーブル登録
                OOrderDetails orderDetails = new OOrderDetails();
                // 店舗ID
                orderDetails.setStoreId(inputDto.getStoreId());
                // 削除フラグ
                orderDetails.setDelFlag(Flag.ON.getCode());
                // 注文ID
                orderDetails.setOrderId(order.getOrderId());
                // 商品ID
                orderDetails.setItemId(orderItemsDto.getItemId());
                // 単価
                orderDetails.setItemPrice(
                    orderItemsDto.getItemPrice());
                // 商品個数
                orderDetails.setItemCount(Integer.valueOf(orderItemsDto.getItemCount()));
                // 商品区分
                orderDetails.setItemClassification(ItemType.NORMAL.getCode());
                // 商品状態
                orderDetails.setItemStatus(ItemStatus.CONFIRMED.getCode());

                // 注文明細IDのシーケンスNo取得
                getSeqNoInputDto = new GetSeqNoInputDto();
                getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNoInputDto.setTableName("o_order_details"); // テーブル名
                getSeqNoInputDto.setItem("order_detail_id"); // 項目
                getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_DELIVERY); // 登録更新者
                getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

                //注文明細ID
                orderDetails.setOrderDetailId(getSeqNo.getSeqNo());
                // 登録日時
                orderDetails.setInsDateTime(nowDateTime);
                // 登録者
                orderDetails.setInsOperCd(CommonConstants.OPER_CD_DELIVERY);
                // 更新日時
                orderDetails.setUpdDateTime(nowDateTime);
                // 更新者
                orderDetails.setUpdOperCd(CommonConstants.OPER_CD_DELIVERY);
                // バージョン
                orderDetails.setVersion(0);

                orderDetails.setItemReturnId(null);

                orderDetailsRepository.save(orderDetails);

                // 注文明細オプションデータが存在の場合
                if (CollectionUtils.isNotEmpty(orderItemsDto.getOption())) {
                    for (OrderItemOptionDto orderItemOptionDto : orderItemsDto.getOption()) {
                        // 注文明細オプションテーブル登録
                        OOrderDetailsOption orderDetailsOption = new OOrderDetailsOption();
                        //店舗ID
                        orderDetailsOption.setStoreId(inputDto.getStoreId());
                        // 注文明細ID
                        orderDetailsOption.setOrderDetailId(orderDetails.getOrderDetailId());
                        // 商品オプション種類コード
                        orderDetailsOption
                            .setItemOptionTypeCode(orderItemOptionDto.getOptionTypeCd());
                        // 商品オプションコード
                        orderDetailsOption.setItemOptionCode(orderItemOptionDto.getOptionCode());
                        // 差額
                        orderDetailsOption.setDiffPrice(orderItemOptionDto.getOptionDiffPrice());
                        // 数量
                        orderDetailsOption.setItemOptionCount(orderItemOptionDto.getOptionCount());
                        // 削除フラグ
                        orderDetailsOption.setDelFlag(Flag.ON.getCode());
                        // 登録日時
                        orderDetailsOption.setInsDateTime(nowDateTime);
                        // 登録者
                        orderDetailsOption.setInsOperCd(CommonConstants.OPER_CD_DELIVERY);
                        // 更新日時
                        orderDetailsOption.setUpdDateTime(nowDateTime);
                        // 更新者
                        orderDetailsOption.setUpdOperCd(CommonConstants.OPER_CD_DELIVERY);
                        // バージョン
                        orderDetailsOption.setVersion(0);

                        orderDetailsOptionRepository.save(orderDetailsOption);
                    }
                }
            }

            outDto.setReceivablesId(receivablesId);
            outDto.setOrderSummaryId(orderSummaryId);
            outDto.setOrderId(orderId);

            return outDto;

        } catch (Exception ex) {
            throw new BusinessException("2003",
                ResultMessages.error().add("e.qr.ph.035", (Object) null), ex);
        }
    }

    /**
     * 注文状態を変更する.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void changeDeliveryStatus(WeChatAliPayBackInputDto inputDto) {

        try {

            // 店舗ID
            String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNum().substring(0, 20);

            // 注文ID
            Integer orderId = Integer
                .valueOf(inputDto.getOrderNum().substring(20, inputDto.getOrderNum().length() - 2));

            // 注文金額
            BigDecimal paymentAmount = new BigDecimal(
                inputDto.getTransAmt().replaceAll("^(0+)", ""));

            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();

            // 支払い金額を変更する
            Integer updateCount = deliveryOrderSummaryRepository
                .updatePaymentAmountWithDelFlag(storeId, orderSummaryId, "TRIO", dateTime);

            // 受付ID取得
            ODeliveryOrderSummary deliveryOrderSummary = deliveryOrderSummaryRepository
                .findByStoreIdAndOrderSummaryId(
                    storeId, orderSummaryId);

            // 受付状態変更
            receivablesRepository
                .updateDelFlagByReceivablesId(storeId, deliveryOrderSummary.getReceivablesId());

            // 更新０件の場合
            if (updateCount == 0) {
                deliveryOrderSummaryRepository
                    .updatePaymentAmount(storeId, orderSummaryId, paymentAmount,
                        "TRIO", dateTime);
            }

            // 注文状態を変更する
            orderRepository.updateStatusWithDelFlag(storeId, orderSummaryId, orderId,
                CodeConstants.PayStatus.PAY_ALREADY.getCode(), "TRIO", dateTime);

            // 注文明細状態更新
            orderDetailsRepository.updateDelFlagByOrderId(storeId, orderId, "TRIO", dateTime);

            // 注文明細オプション状態更新
            orderDetailsOptionRepository.updateDelFlagByOrderId(storeId, orderId, "TRIO", dateTime);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.039", (Object) null), ex);
        }
    }

    /**
     * 注文情報を回復する.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void revertDeliveryOrder(WeChatAliPayBackInputDto inputDto) {

        try {

            // 店舗ID
            String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

            // 注文サマリID
            String orderSummaryId = inputDto.getOrderNum().substring(0, 20);

            // 注文ID
            Integer orderId = Integer
                .valueOf(inputDto.getOrderNum().substring(20, inputDto.getOrderNum().length() - 2));

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
     * 注文情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文情報
     */
    @Override
    public GetDeliveryOrderInfoOutputDto getDeliveryOrderInfo(GetOrderInfoInputDto inputDto) {

        // 結果DTO初期化
        GetDeliveryOrderInfoOutputDto outDto = new GetDeliveryOrderInfoOutputDto();

        BigDecimal foreignTax = null;

        // 注文情報を取得する
        List<Map<String, Object>> orderData = deliveryOrderSummaryRepository
            .findOrderInfoByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId(),
                inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(orderData)) {
            outDto.setItemList(new ArrayList<>());
            return outDto;
        }

        // 注文情報を編集する
        List<OrderItemDataDto> orderItemDataList = new ArrayList<>();
        orderData.forEach(stringObjectMap -> orderItemDataList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), OrderItemDataDto.class)));

        foreignTax = orderItemDataList.get(0).getForeignTax();

        // 注文ID + 注文明細ID + 商品IDをグルーピングする
        Map<String, List<OrderItemDataDto>> grpByOrderId = orderItemDataList.stream().collect(
            Collectors.groupingBy(
                o -> o.getOrderId() + "-" + o.getOrderDetailId() + "-" + o.getItemId()));

        // 商品情報リストを編集する
        List<OrderItemFullInfoDto> items = new ArrayList<>();
        grpByOrderId.forEach((s, orderItemDataDtos) -> {
            OrderItemFullInfoDto item = beanMapper
                .map(orderItemDataDtos.get(0), OrderItemFullInfoDto.class);
            orderItemDataDtos.forEach(orderItemDataDto -> {
                if (StringUtils.isNotEmpty(orderItemDataDto.getOptionName())) {
                    String optionName = orderItemDataDto.getOptionName();
                    if (orderItemDataDto.getOptionItemCount() != null && 0 < orderItemDataDto
                        .getOptionItemCount()
                        && Objects.equals(OptionType.QUANTITY_SELECTION.getCode(),
                        orderItemDataDto.getClassification())) {
                        optionName = String.format("%s*%s", orderItemDataDto.getOptionName(),
                            orderItemDataDto.getOptionItemCount());
                    }
                    if (StringUtils.isEmpty(item.getOption())) {
                        item.setOption(optionName);
                    } else {
                        item.setOption(item.getOption() + "," + optionName);
                    }
                }
            });
            items.add(item);
        });

        // 商品リスト設定
        List<OrderItemInfoDto> itemList = new ArrayList<>();

        items.forEach(orderItemFullInfoDto -> itemList.add(beanMapper
            .map(orderItemFullInfoDto, OrderItemInfoDto.class)));
        outDto.setItemList(itemList);

        // 外税金額
        outDto.setForeignTax(foreignTax);

        // 出前注文情報を取得する
        Map<String, Object> deliveryOrderSummary = deliveryOrderSummaryRepository
            .findDeliveryOrderDetailInfoByReceivablesId(inputDto.getStoreId(),
                inputDto.getReceivablesId(),
                CommonConstants.CODE_GROUP_DELIVERY_TYPE_FLAG,
                CommonConstants.CODE_GROUP_DELIVERY_STATUS, inputDto.getLanguages());

        GetDeliveryOrderDetailInfoOutputDto deliveryOrderData = new GetDeliveryOrderDetailInfoOutputDto();

        deliveryOrderData = JSONObject.parseObject(JSONObject.toJSONString(
            deliveryOrderSummary), GetDeliveryOrderDetailInfoOutputDto.class);

        // 注文金額
        outDto.setOrderAmount(deliveryOrderData.getOrderAmount());

        // 配達費
        outDto.setCateringCharge(deliveryOrderData.getCateringCharge());

        // 合計
        outDto.setTotalAmount(deliveryOrderData.getOrderAmount().add(foreignTax)
            .add(deliveryOrderData.getCateringCharge()));

        // 氏名
        outDto.setCustomerName(deliveryOrderData.getCustomerName());

        // 電話番号
        outDto.setPhoneNumber(deliveryOrderData.getTelNumber());

        // メール
        outDto.setEmail(deliveryOrderData.getMailAddress());

        // 出前時間
        outDto.setDeliveryTime(deliveryOrderData.getDeliveryTime());

        // 状態
        outDto.setStatus(deliveryOrderData.getStatus());
        // 支払方式
        String paymentMethod = deliveryOrderSummaryRepository
            .findDeliveryPaymentMethodByOrderSummaryId(
                inputDto.getStoreId(), inputDto.getReceivablesId(),
                CommonConstants.CODE_GROUP_PAYMENT);

        // 支払い方式
        outDto.setPaymentType(paymentMethod);

        // 住所
        outDto.setAddress(deliveryOrderData.getAddress());

        // 出前区分
        outDto.setDeliveryTypeFlag(deliveryOrderData.getDeliveryTypeFlag());
        outDto.setDeliveryType(deliveryOrderData.getDeliveryType());

        // 要望
        outDto.setComment(deliveryOrderData.getComment());

        return outDto;
    }

    /**
     * 注文新規編集情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文新規編集情報
     */
    @Override
    public GetDeliveryOrderOutputDto getDeliveryOrderNewEditInfo(
        GetDeliveryOrderInputDto inputDto) {

        // 結果DTO初期化
        GetDeliveryOrderOutputDto outDto = new GetDeliveryOrderOutputDto();

        // 指定店舗情報を取得する
        MStore storeData = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // システム時間
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // 配達時間
        Integer cateringIntervalTime = storeData.getCateringIntervalTime();

        // 持帰り時間
        Integer takeoutIntervalTime = storeData.getTakeoutIntervalTime();

        // 間隔時間
        Integer intervalTime = storeData.getIntervalTime();

        // 営業開始時間
        ZonedDateTime startTime = DateUtil.getZonedDateByString(
            DateUtil.getZonedDateString(
                nowDateTime, "yyyy-MM-dd HH:mm:ss").split(" ")[0]
                + " " + storeData.getStartTime() + ":00", "yyyy-MM-dd HH:mm:ss");

        // 注文締め時間
        ZonedDateTime orderEndTime = DateUtil.getZonedDateByString(
            DateUtil.getZonedDateString(
                nowDateTime, "yyyy-MM-dd HH:mm:ss").split(" ")[0]
                + " " + storeData.getOrderEndTime() + ":59", "yyyy-MM-dd HH:mm:ss");

        if (startTime.isBefore(orderEndTime)) {
            if (orderEndTime.isBefore(nowDateTime)) {
                startTime = startTime.plusDays(1L);
                orderEndTime = orderEndTime.plusDays(1L);
            }
        } else {
            if (orderEndTime.isBefore(nowDateTime)) {
                orderEndTime = orderEndTime.plusDays(1L);
            } else {
                startTime = startTime.minusDays(1L);
            }
        }

        List<DeliveryTimeListDto> deliveryTimeList = new ArrayList<>();

        // 配達の場合
        if (DeliveryTypeFlag.CATERING.getCode().equals(inputDto.getDeliveryTypeFlag())) {
            // 配達時間リストを作成する
            ZonedDateTime cateringStartTime = startTime.plusMinutes(cateringIntervalTime);
            ZonedDateTime cateringEndTime = cateringStartTime.plusMinutes(intervalTime);
            nowDateTime = nowDateTime.plusMinutes(cateringIntervalTime);

            if (nowDateTime.isBefore(cateringStartTime) || nowDateTime.isEqual(cateringStartTime)) {
                while (cateringStartTime.isBefore(orderEndTime)) {
                    String cateringTime =
                        DateUtil.getZonedDateString(cateringStartTime, "HH:mm") + "～" +
                            DateUtil.getZonedDateString(cateringEndTime, "HH:mm");

                    DeliveryTimeListDto deliveryTimeData = new DeliveryTimeListDto();
                    deliveryTimeData.setDeliveryTime(cateringTime);
                    deliveryTimeList.add(deliveryTimeData);

                    cateringStartTime = cateringEndTime;
                    cateringEndTime = cateringEndTime.plusMinutes(intervalTime);
                }
            } else {
                List<DeliveryTimeListDto> deliveryTimeTempList = new ArrayList<>();

                while ((cateringStartTime.isBefore(nowDateTime) && nowDateTime
                    .isBefore(cateringEndTime))
                    || nowDateTime.isEqual(cateringEndTime)
                    || cateringEndTime.isBefore(nowDateTime)) {
                    String cateringTimeTemp =
                        DateUtil.getZonedDateString(cateringStartTime, "HH:mm") + "～" +
                            DateUtil.getZonedDateString(cateringEndTime, "HH:mm");

                    DeliveryTimeListDto deliveryTimeTempData = new DeliveryTimeListDto();
                    deliveryTimeTempData.setDeliveryTime(cateringTimeTemp);
                    deliveryTimeTempList.add(deliveryTimeTempData);

                    cateringStartTime = cateringEndTime;
                    cateringEndTime = cateringEndTime.plusMinutes(intervalTime);
                }

                while (cateringStartTime.isBefore(orderEndTime)) {
                    String cateringTime =
                        DateUtil.getZonedDateString(cateringStartTime, "HH:mm") + "～" +
                            DateUtil.getZonedDateString(cateringEndTime, "HH:mm");

                    DeliveryTimeListDto deliveryTimeData = new DeliveryTimeListDto();
                    deliveryTimeData.setDeliveryTime(cateringTime);
                    deliveryTimeList.add(deliveryTimeData);

                    cateringStartTime = cateringEndTime;
                    cateringEndTime = cateringEndTime.plusMinutes(intervalTime);
                }

                deliveryTimeList.addAll(deliveryTimeTempList);
            }
            // 持帰りの場合
        } else {
            // 持帰り時間リストを作成する
            ZonedDateTime takeoutStartTime = startTime.plusMinutes(takeoutIntervalTime);
            ZonedDateTime takeoutEndTime = takeoutStartTime.plusMinutes(intervalTime);
            nowDateTime = nowDateTime.plusMinutes(takeoutIntervalTime);

            if (nowDateTime.isBefore(takeoutStartTime) || nowDateTime.isEqual(takeoutStartTime)) {
                while (takeoutStartTime.isBefore(orderEndTime)) {
                    String takeoutTime =
                        DateUtil.getZonedDateString(takeoutStartTime, "HH:mm") + "～" +
                            DateUtil.getZonedDateString(takeoutEndTime, "HH:mm");

                    DeliveryTimeListDto deliveryTimeData = new DeliveryTimeListDto();
                    deliveryTimeData.setDeliveryTime(takeoutTime);
                    deliveryTimeList.add(deliveryTimeData);

                    takeoutStartTime = takeoutEndTime;
                    takeoutEndTime = takeoutEndTime.plusMinutes(intervalTime);
                }
            } else {
                List<DeliveryTimeListDto> deliveryTimeTempList = new ArrayList<>();

                while (
                    (takeoutStartTime.isBefore(nowDateTime) && nowDateTime.isBefore(takeoutEndTime))
                        || nowDateTime.isEqual(takeoutEndTime)
                        || takeoutEndTime.isBefore(nowDateTime)) {
                    String takeoutTimeTemp =
                        DateUtil.getZonedDateString(takeoutStartTime, "HH:mm") + "～" +
                            DateUtil.getZonedDateString(takeoutEndTime, "HH:mm");

                    DeliveryTimeListDto deliveryTimeTempData = new DeliveryTimeListDto();
                    deliveryTimeTempData.setDeliveryTime(takeoutTimeTemp);
                    deliveryTimeTempList.add(deliveryTimeTempData);

                    takeoutStartTime = takeoutEndTime;
                    takeoutEndTime = takeoutEndTime.plusMinutes(intervalTime);
                }

                while (takeoutStartTime.isBefore(orderEndTime)) {
                    String cateringTime =
                        DateUtil.getZonedDateString(takeoutStartTime, "HH:mm") + "～" +
                            DateUtil.getZonedDateString(takeoutEndTime, "HH:mm");

                    DeliveryTimeListDto deliveryTimeData = new DeliveryTimeListDto();
                    deliveryTimeData.setDeliveryTime(cateringTime);
                    deliveryTimeList.add(deliveryTimeData);

                    takeoutStartTime = takeoutEndTime;
                    takeoutEndTime = takeoutEndTime.plusMinutes(intervalTime);
                }

                deliveryTimeList.addAll(deliveryTimeTempList);
            }
        }

        outDto.setDeliveryTimeList(deliveryTimeList);

        // 配達都道府県リストを取得する
        List<Map<String, Object>> prefectureData =
            deliveryAreaRepository
                .findPrefectureByStoreId(inputDto.getStoreId(), inputDto.getLanguages());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(prefectureData)) {
            outDto.setPrefectureList(new ArrayList<>());
        }

        // 配達都道府県リストを編集する
        List<PrefectureListDto> prefectureList = new ArrayList<>();
        prefectureData.forEach(stringObjectMap -> prefectureList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap),
                PrefectureListDto.class)));

        outDto.setPrefectureList(prefectureList);

        // 支払方式情報取得
        List<PaymentMethodDto> storePaymentList = licenseRepository
            .findStorePaymentByType(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PAYMENT);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storePaymentList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.007", (Object) null));
        }

        List<PaymentTypeListDto> paymentTypeList = new ArrayList<>();
        for (PaymentMethodDto paymentMethod : storePaymentList) {
            if (Integer.parseInt(paymentMethod.getPaymentCode()) < 21 && 0 < Integer
                .parseInt(paymentMethod.getPaymentCode())) {
                PaymentTypeListDto paymentTypeDto = beanMapper
                    .map(paymentMethod, PaymentTypeListDto.class);
                paymentTypeDto.setPaymentTypeId(paymentMethod.getPaymentCode());
                paymentTypeDto.setPaymentTypeName(paymentMethod.getPaymentName());
                paymentTypeList.add(paymentTypeDto);
            }
        }

        outDto.setPaymentTypeList(paymentTypeList);

        return outDto;
    }
}
