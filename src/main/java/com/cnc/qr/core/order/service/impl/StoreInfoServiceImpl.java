package com.cnc.qr.core.order.service.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.AccountsType;
import com.cnc.qr.common.constants.CodeConstants.DeliverPrintType;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemStatus;
import com.cnc.qr.common.constants.CodeConstants.ItemType;
import com.cnc.qr.common.constants.CodeConstants.OrderStatus;
import com.cnc.qr.common.constants.CodeConstants.OrderType;
import com.cnc.qr.common.constants.CodeConstants.PayStatus;
import com.cnc.qr.common.constants.CodeConstants.PaymentType;
import com.cnc.qr.common.constants.CodeConstants.SettleType;
import com.cnc.qr.common.constants.CodeConstants.StaffCheckFlag;
import com.cnc.qr.common.constants.CodeConstants.TakeoutFlag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.dto.AcountDTO;
import com.cnc.qr.common.dto.StoreDTO;
import com.cnc.qr.common.entity.MControl;
import com.cnc.qr.common.entity.MDevice;
import com.cnc.qr.common.entity.MLicense;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.MStoreMedium;
import com.cnc.qr.common.entity.MToken;
import com.cnc.qr.common.entity.OInspectionSettle;
import com.cnc.qr.common.entity.OOrder;
import com.cnc.qr.common.entity.OOrderDetails;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.common.entity.RTable;
import com.cnc.qr.common.entity.RUser;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MControlRepository;
import com.cnc.qr.common.repository.MDeviceRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MLicenseRepository;
import com.cnc.qr.common.repository.MReceiptRepository;
import com.cnc.qr.common.repository.MStoreMediumRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.MTableRepository;
import com.cnc.qr.common.repository.MTokenRepository;
import com.cnc.qr.common.repository.OInspectionSettleRepository;
import com.cnc.qr.common.repository.OOrderDetailsRepository;
import com.cnc.qr.common.repository.OOrderRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OReceivablesRepository;
import com.cnc.qr.common.repository.PPaymentDetailRepository;
import com.cnc.qr.common.repository.RTableRepository;
import com.cnc.qr.common.repository.RUserRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.PrintDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.common.shared.model.SlipPrintDto;
import com.cnc.qr.common.shared.model.StoreOpenColseTimeDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.shared.service.PrintDataSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.common.util.Md5Util;
import com.cnc.qr.core.order.model.BuffetInfoDto;
import com.cnc.qr.core.order.model.CourseInfoDto;
import com.cnc.qr.core.order.model.GetActiveLanguageInputDto;
import com.cnc.qr.core.order.model.GetActiveLanguageOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryTypeFlagInputDto;
import com.cnc.qr.core.order.model.GetDeliveryTypeFlagOutputDto;
import com.cnc.qr.core.order.model.GetHomePageInfoInputDto;
import com.cnc.qr.core.order.model.GetHomePageInfoOutputDto;
import com.cnc.qr.core.order.model.GetPayLaterPaymentInfoInputDto;
import com.cnc.qr.core.order.model.GetPayLaterPaymentInfoOutputDto;
import com.cnc.qr.core.order.model.GetPaymentInfoInputDto;
import com.cnc.qr.core.order.model.GetPaymentInfoOutputDto;
import com.cnc.qr.core.order.model.GetReceivablesInfoInputDto;
import com.cnc.qr.core.order.model.GetReceivablesInfoOutputDto;
import com.cnc.qr.core.order.model.GetStoreAdverPicInputDto;
import com.cnc.qr.core.order.model.GetStoreAdverPicOutputDto;
import com.cnc.qr.core.order.model.GetStoreInfoInputDto;
import com.cnc.qr.core.order.model.GetStoreInfoOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListOutputDto;
import com.cnc.qr.core.order.model.InspectionSettleInitInputDto;
import com.cnc.qr.core.order.model.InspectionSettleInitOutputDto;
import com.cnc.qr.core.order.model.InspectionSettleInputDto;
import com.cnc.qr.core.order.model.ItemsDto;
import com.cnc.qr.core.order.model.MenuDto;
import com.cnc.qr.core.order.model.MenuInputDto;
import com.cnc.qr.core.order.model.MenuOutputDto;
import com.cnc.qr.core.order.model.PaymentMethodDto;
import com.cnc.qr.core.order.model.PrintDeliverOrderInputDto;
import com.cnc.qr.core.order.model.StoreDeviceRegistInputDto;
import com.cnc.qr.core.order.model.StoreIdVerificationInputDto;
import com.cnc.qr.core.order.model.StoreLanguageDto;
import com.cnc.qr.core.order.model.StorePicUrlDto;
import com.cnc.qr.core.order.model.TableSeatDto;
import com.cnc.qr.core.order.model.UserDto;
import com.cnc.qr.core.order.service.OrderService;
import com.cnc.qr.core.order.service.StoreInfoService;
import com.cnc.qr.security.model.ControlDto;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店舗情報取得サービス実装クラス.
 */
@Service
@Transactional
public class StoreInfoServiceImpl implements StoreInfoService {

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * 支払明細テーブルリポジトリ.
     */
    @Autowired
    private PPaymentDetailRepository paymentDetailRepository;

    /**
     * ライセンスマスタリポジトリ.
     */
    @Autowired
    private MLicenseRepository licenseRepository;

    /**
     * 店舗媒体マスタリポジトリ.
     */
    @Autowired
    private MStoreMediumRepository storeMediumRepository;

    /**
     * ユーザテーブルリポジトリ.
     */
    @Autowired
    private RUserRepository userRepository;

    /**
     * コントロールマスタリポジトリ.
     */
    @Autowired
    private MControlRepository controlRepository;

    /**
     * 受付テーブルリポジトリ.
     */
    @Autowired
    private OReceivablesRepository receivablesRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * 印刷情報共有サービス.
     */
    @Autowired
    private PrintDataSharedService printDataSharedService;


    /**
     * 注文確認サービス.
     */
    @Autowired
    private OrderService orderService;


    /**
     * 注文サマリリポジトリ.
     */
    @Autowired
    OOrderSummaryRepository orderSummaryRepository;

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
     * 商品テーブルリポジトリ.
     */
    @Autowired
    MItemRepository itemRepository;

    /**
     * テーブルリポジトリ.
     */
    @Autowired
    MTableRepository tableRepository;

    /**
     * 店舗端末リポジトリ.
     */
    @Autowired
    private MDeviceRepository deviceRepository;

    /**
     * テーブル関連リポジトリ.
     */
    @Autowired
    private RTableRepository rtableRepository;

    /**
     * トークンマスタリポジトリ.
     */
    @Autowired
    MTokenRepository tokenRepository;


    /**
     * 伝票マスタリポジトリ.
     */
    @Autowired
    MReceiptRepository receiptRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private OInspectionSettleRepository inspectionSettleRepository;

    /**
     * Redis操作クラス.
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * メッセージリソース.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * 店舗店員確認要否取得.
     *
     * @param storeId 取得条件
     * @return 店舗情報
     */
    @Override
    public String getStoreStaffCheck(String storeId) {
        // 店舗情報取得
        MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(storeId,
            Flag.OFF.getCode());
        // 検索結果0件の場合
        if (storeInfo == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }
        return storeInfo.getStaffCheck();
    }

    /**
     * 店舗情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗情報
     */
    @Override
    public GetStoreInfoOutputDto getStoreInfo(GetStoreInfoInputDto inputDto) {

        // 店舗情報取得
        MStore storeInfo = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (storeInfo == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 結果DTO初期化
        GetStoreInfoOutputDto outDto = new GetStoreInfoOutputDto();

        // 店舗情報設定
        // 店舗名
        outDto.setStoreName(storeInfo.getStoreName());
        // 営業時間
        String[] time = storeInfo.getEndTime().split(":");
        int hour = Integer.parseInt(time[0]);
        outDto.setStoreTime(String.format("%s ～ %s", storeInfo.getStartTime(),
            String.format("%02d:%s", 24 < hour ? hour - 24 : hour, time[1])));

        return outDto;
    }

    /**
     * 店舗言語情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗言語情報
     */
    @Override
    public GetActiveLanguageOutputDto getActiveLanguage(GetActiveLanguageInputDto inputDto) {

        // 店舗媒体情報取得
        List<StoreLanguageDto> storeLanguageList = licenseRepository
            .findStoreLanguageByCode(inputDto.getStoreId(), CommonConstants.CODE_GROUP_LANGUAGE);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storeLanguageList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 結果DTO初期化
        GetActiveLanguageOutputDto outDto = new GetActiveLanguageOutputDto();

        // 言語リストを設定する
        outDto.setLanguages(storeLanguageList);

        return outDto;
    }

    /**
     * 店舗媒体情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗媒体情報
     */
    @Override
    public GetStoreAdverPicOutputDto getStoreAdverPic(GetStoreAdverPicInputDto inputDto) {

        // 店舗媒体情報取得
        List<MStoreMedium> storeMediumList =
            storeMediumRepository
                .findByStoreIdAndMediumTypeAndTerminalDistinctionAndDelFlagOrderBySortOrderAsc(
                    inputDto.getStoreId(), inputDto.getMediumType(),
                    inputDto.getTerminalDistinction(),
                    Flag.OFF.getCode());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storeMediumList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 結果DTO初期化
        GetStoreAdverPicOutputDto outDto = new GetStoreAdverPicOutputDto();

        // 画像パスリストを設定する
        List<StorePicUrlDto> picUrls = new ArrayList<>();
        for (MStoreMedium storeMedium : storeMediumList) {
            StorePicUrlDto urlDto = beanMapper.map(storeMedium, StorePicUrlDto.class);
            urlDto.setSortNo(storeMedium.getSortOrder());
            urlDto.setUrl(storeMedium.getImagePath());
            picUrls.add(urlDto);
        }
        outDto.setPicUrls(picUrls);

        return outDto;
    }

    /**
     * 支払方式取得.
     *
     * @param inputDto 取得条件
     * @return 支払方式情報
     */
    @Override
    public GetPaymentInfoOutputDto getPaymentType(GetPaymentInfoInputDto inputDto) {

        // 支払区分情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PAYMENT, Flag.OFF.getCode());

        // 検索結果0件の場合
        if (control == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.006", (Object) null));
        }

        // 支払方式情報取得
        List<PaymentMethodDto> storePaymentList = licenseRepository
            .findStorePaymentByType(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PAYMENT);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storePaymentList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.007", (Object) null));
        }

        // 結果DTO初期化
        GetPaymentInfoOutputDto outDto = new GetPaymentInfoOutputDto();

        // 支払方式情報を設定する
        outDto.setPaymentType(control.getControlCode());
        outDto.setPaymentMethodList(storePaymentList);

        return outDto;
    }

    /**
     * 店舗受付情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗言語情報
     */
    @Override
    public GetReceivablesInfoOutputDto getReceivablesInfo(GetReceivablesInfoInputDto inputDto) {

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
        // 店舗営業時間取得
        StoreOpenColseTimeDto storeOpenColseTimeDto = itemInfoSharedService
            .getStoreOpenColseTime(inputDto.getStoreId());

        // 受付番号
        Integer receptionNo = receivablesRepository
            .getReceptionNoByReceptionTime(inputDto.getStoreId(),
                storeOpenColseTimeDto.getStartTime(), storeOpenColseTimeDto.getEndTime());

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
        receivables.setInsOperCd(CommonConstants.OPER_CD_MOBILE); // 登録者
        receivables.setUpdDateTime(nowDateTime); // 更新日時
        receivables.setUpdOperCd(CommonConstants.OPER_CD_MOBILE); // 更新者
        receivables.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
        receivables.setVersion(0); // バージョン
        receivablesRepository.save(receivables);

        // 支払区分情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PAYMENT, Flag.OFF.getCode());

        if (CollectionUtils.isNotEmpty(inputDto.getTableId()) || CollectionUtils
            .isNotEmpty(inputDto.getBuffetId()) || CollectionUtils
            .isNotEmpty(inputDto.getCourseId())) {

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
            orderSummary.setCustomerCount(receivables.getCustomerCount());
            // テーブルID
            if (CollectionUtils.isNotEmpty(inputDto.getTableId())) {
                orderSummary.setTableId(inputDto.getTableId().get(0).getTableId());
            }
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
            // テーブル状態
            if (CollectionUtils.isEmpty(inputDto.getTableId())) {
                orderSummary.setSeatRelease(Flag.ON.getCode().toString());
            } else {
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
            OOrderSummary insOrderSummaryData = orderSummaryRepository.save(orderSummary);

            // テイクアウト区分保存Keyを生成する
            String takeoutFlagKey = receivablesId + "takeoutFlag";
            // テイクアウト区分設定
            redisTemplate.opsForValue().set(takeoutFlagKey, TakeoutFlag.EAT_IN.getCode());
            redisTemplate.expire(takeoutFlagKey, 10800L, TimeUnit.SECONDS);

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

                if (insOrderSummaryData.getPaymentType()
                    .equals(PaymentType.ADVANCE_PAYMENT.getCode())) {
                    GetTaxValueInputDto taxValueInputDto = new GetTaxValueInputDto();
                    taxValueInputDto.setStoreId(inputDto.getStoreId());
                    taxValueInputDto.setTakeoutFlag(insOrderSummaryData.getTakeoutFlag());
                    List<ItemsDto> itemList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(inputDto.getBuffetId())) {
                        inputDto.getBuffetId().forEach(buffetInfoDto -> {
                            ItemsDto itemsDto = new ItemsDto();
                            itemsDto.setItemId(buffetInfoDto.getBuffetId().toString());
                            itemsDto.setItemPrice(
                                buffetInfoDto.getBuffetAmount()
                                    .multiply(new BigDecimal(buffetInfoDto.getBuffetCount())));
                            itemList.add(itemsDto);
                            taxValueInputDto.setItemList(itemList);
                        });
                    }
                    if (CollectionUtils.isNotEmpty(inputDto.getCourseId())) {
                        inputDto.getCourseId().forEach(courseInfoDto -> {
                            ItemsDto itemsDto = new ItemsDto();
                            itemsDto.setItemId(courseInfoDto.getCourseId().toString());
                            itemsDto.setItemPrice(
                                courseInfoDto.getCoursePrice()
                                    .multiply(new BigDecimal(courseInfoDto.getCourseCount())));
                            itemList.add(itemsDto);
                            taxValueInputDto.setItemList(itemList);
                        });
                    }
                    GetTaxValueOutputDto taxValueOutputDto = itemInfoSharedService
                        .getTaxValue(taxValueInputDto);
                    // 外税金額
                    order.setForeignTax(
                        taxValueOutputDto.getSotoTaxEight().add(taxValueOutputDto.getSotoTaxTen()));
                } else {
                    // 外税金額
                    order.setForeignTax(BigDecimal.ZERO);
                }
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
        }

        // 結果DTO設定
        GetReceivablesInfoOutputDto outDto = new GetReceivablesInfoOutputDto();
        outDto.setReceivablesId(receivablesId);
        outDto.setReceivablesNo(String.format("%04d", receptionNo));

        if (CollectionUtils.isNotEmpty(inputDto.getTableId())) {
            List<RTable> tableReceivablesList = new ArrayList<>();
            GetSeqNoOutputDto getNo = new GetSeqNoOutputDto();
            if (inputDto.getTableId().size() > 1) {
                // 注文明細IDのシーケンスNo取得
                GetSeqNoInputDto getSeqNo = new GetSeqNoInputDto();
                getSeqNo.setStoreId(inputDto.getStoreId()); // 店舗ID
                getSeqNo.setTableName("r_table"); // テーブル名
                getSeqNo.setItem("group_id"); // 項目
                getSeqNo.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
                getNo = itemInfoSharedService.getSeqNo(getSeqNo);
                RTable tableReceivables = new RTable();
                tableReceivables.setGroupId(getNo.getSeqNo());
                tableReceivables.setStoreId(inputDto.getStoreId());
                tableReceivables.setTableId(inputDto.getTableId().get(0).getTableId());
                tableReceivables.setReceivablesId(receivablesId);
                tableReceivables.setRealReceivablesId(receivablesId);
                tableReceivables.setDelFlag(Flag.OFF.getCode());
                tableReceivables.setInsDateTime(nowDateTime);
                tableReceivables.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                tableReceivables.setUpdDateTime(nowDateTime);
                tableReceivables.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
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

            for (TableSeatDto tableSeatDto : tableInfoList) {
                // 受付番号
                otherReceptionNo = receivablesRepository
                    .getReceptionNoByReceptionTime(inputDto.getStoreId(),
                        storeOpenColseTimeDto.getStartTime(), storeOpenColseTimeDto.getEndTime());

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
                receivablesEntity.setInsOperCd(CommonConstants.OPER_CD_MOBILE); // 登録者
                receivablesEntity.setUpdDateTime(nowDateTime); // 更新日時
                receivablesEntity.setUpdOperCd(CommonConstants.OPER_CD_MOBILE); // 更新者
                receivablesEntity.setDelFlag(Flag.OFF.getCode()); // 削除フラグ
                receivablesEntity.setVersion(0); // バージョン
                receivablesRepository.save(receivablesEntity);

                String orderSummaryId = itemInfoSharedService
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
                orderSummary.setOrderSummaryId(orderSummaryId);
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
                orderSummary.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                // 更新日時
                orderSummary.setUpdDateTime(nowDateTime);
                // 更新者
                orderSummary.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                // バージョン
                orderSummary.setVersion(0);
                orderSummaryRepository.save(orderSummary);

                RTable tableReceivables = new RTable();
                tableReceivables.setStoreId(inputDto.getStoreId());
                tableReceivables.setGroupId(getNo.getSeqNo());
                tableReceivables.setTableId(tableSeatDto.getTableId());
                tableReceivables.setReceivablesId(otherReceivablesId);
                tableReceivables.setRealReceivablesId(receivablesId);
                tableReceivables.setDelFlag(Flag.OFF.getCode());
                tableReceivables.setInsDateTime(nowDateTime);
                tableReceivables.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
                tableReceivables.setUpdDateTime(nowDateTime);
                tableReceivables.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
                tableReceivables.setVersion(0);
                tableReceivablesList.add(tableReceivables);
            }

            if (tableReceivablesList.size() > 1) {
                rtableRepository.saveAll(tableReceivablesList);
            }
        }

        return outDto;
    }

    /**
     * 店舗ユーザ情報取得.
     *
     * @param storeId 取得条件
     * @return 店舗ユーザ情報
     */
    @Override
    public List<UserDto> getUser(String storeId) {

        List<String> userTypeList = new ArrayList<>();
        userTypeList.add(CodeConstants.UserType.FRONT.getCode());
        userTypeList.add(CodeConstants.UserType.FRONTANDBACK.getCode());
        // 店舗のフロントのすべてユーザ
        List<Map<String, Object>> userMapList = userRepository
            .getStoreIdAndUser(storeId,
                userTypeList);

        // ユーザ名設定
        List<UserDto> reUserList = new ArrayList<>();
        userMapList.forEach(user -> {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.get("login").toString());
            userDto.setUserName(user.get("username").toString());
            reUserList.add(userDto);
        });
        return reUserList;
    }

    /**
     * 店舗メニュー情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗メニュー情報
     */
    @Override
    public MenuOutputDto getMenu(MenuInputDto inputDto) {

        // 店舗メニュー取得
        List<MenuDto> menuList = userRepository
            .findUserMenu(inputDto.getStoreId(), inputDto.getUserId(),
                CodeConstants.MenuType.FRONT.getCode());
        menuList = menuList.stream()
            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(
                Comparator.comparing(MenuDto::getMenuId))), ArrayList::new));

        List<MenuDto> menus = new ArrayList<>();
        menuList.forEach(menu -> {
            MenuDto menuDto = new MenuDto();
            menuDto.setMenuId(menu.getMenuId()); // メニューID
            menuDto.setMenuIcon(menu.getMenuIcon()); // メニューアイコン名
            menuDto.setMenuLink(menu.getMenuLink()); // リンク
            menuDto.setMenuName(JSONObject.parseObject(menu.getMenuName()) // メニュー名
                .getString(inputDto.getLanguages()));
            menus.add(menuDto);
        });

        // メニューリスト設定
        MenuOutputDto menuOutputDto = new MenuOutputDto();
        menuOutputDto.setMenuList(menus);
        return menuOutputDto;
    }

    /**
     * トップ画面情報取得.
     *
     * @param inputDto 取得条件
     * @return トップ画面情報
     */
    @Override
    public GetHomePageInfoOutputDto getHomePageInfo(GetHomePageInfoInputDto inputDto) {

        // 店舗情報取得
        MStore storeData = storeRepository.findByStoreIdAndDelFlag(inputDto.getStoreId(),
            Flag.OFF.getCode());

        // 検索結果0件の場合
        if (storeData == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 店舗媒体情報取得
        List<StoreLanguageDto> storeLanguageList = licenseRepository
            .findStoreLanguageByCode(inputDto.getStoreId(), CommonConstants.CODE_GROUP_LANGUAGE);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storeLanguageList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.003", (Object) null));
        }

        // 結果DTO初期化
        GetHomePageInfoOutputDto outDto = new GetHomePageInfoOutputDto();
        // 店舗名
        outDto.setStoreName(storeData.getStoreName());

        String checkFlag = StaffCheckFlag.YES.getCode();
        if (Objects.equals(StaffCheckFlag.NO.getCode(), storeData.getCourseBuffetCheck()) && Objects
            .equals(StaffCheckFlag.NO.getCode(), storeData.getStaffCheck())) {
            checkFlag = StaffCheckFlag.NO.getCode();
        }
        // 店員確認フラグ
        outDto.setStaffCheckFlag(checkFlag);
        // 営業時間
        String[] time = storeData.getEndTime().split(":");
        int hour = Integer.parseInt(time[0]);
        outDto.setStoreTime(String.format("%s ～ %s", storeData.getStartTime(),
            String.format("%02d:%s", 24 < hour ? hour - 24 : hour, time[1])));
        // 言語リストを設定する
        outDto.setLanguageList(storeLanguageList);
        // 未確認の件数取得
        GetUnCfmOrderListInputDto unCfmOrderListInputDto = new GetUnCfmOrderListInputDto();
        unCfmOrderListInputDto.setStoreId(inputDto.getStoreId());
        unCfmOrderListInputDto.setSureFlag(Flag.OFF.getCode().toString());
        GetUnCfmOrderListOutputDto unCfmOrderListOutputDto = orderService
            .getUnCfmOrderList(unCfmOrderListInputDto);
        outDto.setUnCofimCount(unCfmOrderListOutputDto.getOrderList().size());
        return outDto;
    }

    /**
     * 支払方式取得.
     *
     * @param inputDto 取得条件
     * @return 支払方式情報
     */
    @Override
    public GetPayLaterPaymentInfoOutputDto getPayLaterPaymentType(
        GetPayLaterPaymentInfoInputDto inputDto) {

        // 支払方式情報取得
        List<PaymentMethodDto> storePaymentList = licenseRepository
            .findStorePaymentByType(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PAYMENT);

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(storePaymentList)) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.007", (Object) null));
        }

        // 支払区分情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PAY_LATER, Flag.OFF.getCode());

        // 結果DTO初期化
        GetPayLaterPaymentInfoOutputDto outDto = new GetPayLaterPaymentInfoOutputDto();

        // 検索結果0件の場合
        List<PaymentMethodDto> paymentMethodList = new ArrayList<>();
        paymentMethodList
            .add(new PaymentMethodDto(AccountsType.CASH.getCode(),
                AccountsType.CASH.getValue()));
        if (control != null && Objects
            .equals(Flag.ON.getCode().toString(), control.getControlCode())) {
            // 支払方式情報を設定する
            for (PaymentMethodDto method : storePaymentList) {
                if (21 <= Integer.parseInt(method.getPaymentCode())
                    && Integer.parseInt(method.getPaymentCode()) <= 52) {
                    paymentMethodList.add(method);
                }
            }
        } else {
            // 支払方式情報を設定する
            paymentMethodList.add(new PaymentMethodDto(AccountsType.QR_CODE_PAY.getCode(),
                AccountsType.QR_CODE_PAY.getValue()));

        }
        outDto.setPaymentMethodList(paymentMethodList);

        return outDto;
    }

    /**
     * トークン認証時間取得.
     *
     * @param storeId 取得条件
     * @return トークン認証時間情報
     */
    @Override
    public Long getTokenValidityInMilliseconds(String storeId) {

        // トークン情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(storeId, CommonConstants.CODE_GROUP_TOKEN,
                Flag.OFF.getCode());

        // 検索結果0件の場合
        if (control == null || StringUtils.isEmpty(control.getControlCode())) {
            return null;
        }

        return Long.valueOf(control.getControlCode());
    }

    /**
     * トークン認証時間取得.
     *
     * @param storeId 取得条件
     * @return トークン認証時間情報
     */
    @Override
    public Long getPrintPollMilliseconds(String storeId) {

        // トークン情報取得
        MControl control = controlRepository
            .findByStoreIdAndControlTypeAndDelFlag(storeId, CommonConstants.CONTROL_TYPE_POLL,
                Flag.OFF.getCode());

        // 検索結果0件の場合
        if (control == null || StringUtils.isEmpty(control.getControlCode())) {
            return null;
        }

        return Long.valueOf(control.getControlCode());
    }

    /**
     * 店舗コントロール条件取得.
     *
     * @param storeId 取得条件
     * @return コントロール条件
     */
    @Override
    public ControlDto getStoreControl(String storeId) {

        // ライセンスのコントロール取得
        List<MLicense> licenseList = licenseRepository
            .findByStoreIdAndDelFlag(storeId,
                Flag.OFF.getCode());

        // 検索結果0件の場合
        if (licenseList == null || licenseList.size() == 0) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.082", (Object) null), null);
        }
        // コントロール条件取得
        ControlDto controlDto = new ControlDto();
        // 予約機能表示フラグ取得
        controlDto.setReservationDisplayFlag(
            itemInfoSharedService.getLicenseCode(licenseList, CommonConstants.RESERVATION_DISPLAY));
        // 出前機能表示フラグ取得
        controlDto.setDeliveryDisplayFlag(
            itemInfoSharedService.getLicenseCode(licenseList, CommonConstants.DELIVERY_DISPLAY));

        // 客用スマホ使用可能標識取得
        controlDto.setSmartPhonesAvailableFlag(
            itemInfoSharedService
                .getLicenseCode(licenseList, CommonConstants.SMART_PHONES_AVAILABLE));
        // 音声注文使用可能標識
        controlDto.setVoiceOrderAvailableFlag(
            itemInfoSharedService
                .getLicenseCode(licenseList, CommonConstants.VOICE_ORDER_AVAILABLE));
        // コントロール情報取得
        List<MControl> controlList = controlRepository
            .findByStoreIdAndDelFlag(storeId,
                Flag.OFF.getCode());
        // 支払方式取得
        List<MControl> paymentMethodDto = controlList.stream().filter(control -> Objects
            .equals(control.getControlType(), CommonConstants.CODE_GROUP_PAYMENT)).collect(
            Collectors.toList());
        if (paymentMethodDto.size() > 0) {
            controlDto.setBeforeAfterPaymentFlag(paymentMethodDto.get(0).getControlCode());
        }
        return controlDto;
    }

    /**
     * ユーザ情報取得.
     *
     * @param businessId 店舗ID
     * @param loginId    ユーザID
     * @return ユーザ情報
     */
    @Override
    public AcountDTO getUserWithAuthorities(String businessId, String loginId) {

        // ユーザ情報取得
        List<RUser> userInfoList = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(businessId, loginId, Flag.OFF.getCode());

        // ユーザ情報存在しない場合
        if (userInfoList.size() == 0) {
            throw new UsernameNotFoundException(messageSource.getMessage("e.qr.ph.029",
                new String[]{loginId}, Locale.JAPAN));
        }

        // 事業者ログイン判断
        List<RUser> userList = userRepository
            .findByBusinessIdAndLoginIdAndDelFlag(businessId, loginId,
                Flag.OFF.getCode());
        List<Map<String, Object>> userStoreMapList = userRepository
            .findStoreByUserId(businessId, userList.get(0).getUserId());
        List<StoreDTO> userStoreList = new ArrayList<>();
        for (Map<String, Object> map : userStoreMapList) {
            StoreDTO storeDto = new StoreDTO();
            storeDto.setStoreId(map.get("store_id").toString());
            storeDto.setStoreName(map.get("store_name").toString());
            userStoreList.add(storeDto);
        }

        userStoreList = userStoreList.stream()
            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(
                Comparator.comparing(StoreDTO::getStoreId))), ArrayList::new));

        // ユーザ情報設定
        AcountDTO acountDto = new AcountDTO();
        acountDto.setLogin(userInfoList.get(0).getLoginId());
        acountDto.setFirstName(userInfoList.get(0).getUserName());
        acountDto.setLangKey("ja_JP");
        acountDto.setActivated(true);
        List<StoreDTO> storeList = new ArrayList<>();
        userStoreList.forEach(store -> {
            StoreDTO storeDto = new StoreDTO();
            storeDto.setStoreId(store.getStoreId());
            storeDto.setStoreName(store.getStoreName());
            storeList.add(storeDto);
        });

        acountDto.setStoreList(storeList);
        return acountDto;
    }

    /**
     * 店舗情報検証.
     *
     * @param inputDto 検証データ
     */
    @Override
    public void storeIdVerification(StoreIdVerificationInputDto inputDto) {

        // 店舗情報検証
        MStore storeInfo = storeRepository
            .findByStoreIdAndStorePasswordAndDelFlag(inputDto.getStoreId(),
                Md5Util.toMd5(inputDto.getPassword()), Flag.OFF.getCode());

        // 店舗情報存在しない場合
        if (storeInfo == null) {
            throw new UsernameNotFoundException(messageSource.getMessage("e.qr.ph.081",
                new String[]{inputDto.getStoreId()}, Locale.JAPAN));
        }
    }

    /**
     * 店舗端末情報設定.
     *
     * @param inputDto 検証データ
     */
    @Override
    public void storeDeviceRegist(StoreDeviceRegistInputDto inputDto) {
        // 店舗情報検証
        List<MDevice> deviceList = deviceRepository
            .findByStoreIdAndDelFlagAndDeviceToken(inputDto.getStoreId(), Flag.OFF.getCode(),
                inputDto.getToken());
        if (deviceList.size() == 0) {

            // ユーザID
            String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
            Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
            if (userInfo.isPresent()) {
                userOperCd = userInfo.get();
            }
            // IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("m_device"); // テーブル名
            getSeqNoInputDto.setItem("device_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);
            MDevice device = new MDevice();
            device.setStoreId(inputDto.getStoreId());
            device.setDeviceId(getSeqNo.getSeqNo());
            device.setDeviceToken(inputDto.getToken());
            device.setDeviceModel(inputDto.getModel());
            device.setDelFlag(Flag.OFF.getCode());
            // 時間取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
            // 登録日時
            device.setInsDateTime(nowDateTime);
            // 登録者
            device.setInsOperCd(userOperCd);
            // 更新日時
            device.setUpdDateTime(nowDateTime);
            // 更新者
            device.setUpdOperCd(userOperCd);
            // バージョン
            device.setVersion(0);
            deviceRepository.save(device);
        }
    }

    /**
     * 店舗トークン情報設定.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param accessToken   トークン
     */
    @Override
    public void storeTokenRegister(String storeId, String receivablesId, String accessToken) {

        // ユーザID
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        MToken token = new MToken();
        token.setStoreId(storeId);
        token.setReceivablesId(receivablesId);
        token.setTokenId(accessToken);
        token.setDelFlag(Flag.OFF.getCode());
        // 時間取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        token.setInsDateTime(nowDateTime);
        token.setInsOperCd(userOperCd);
        token.setUpdDateTime(nowDateTime);
        token.setUpdOperCd(userOperCd);
        token.setVersion(0);
        tokenRepository.save(token);
    }

    /**
     * 店舗取得.
     */
    @Override
    public List<MStore> getStore(String businessId) {
        // 店舗情報検証
        List<MStore> storeList = storeRepository
            .findByBusinessIdAndDelFlagOrderByStoreIdAsc(businessId, Flag.OFF.getCode());
        return storeList;
    }

    /**
     * 出前仕方フラグ取得.
     */
    @Override
    public GetDeliveryTypeFlagOutputDto getDeliveryTypeFlag(GetDeliveryTypeFlagInputDto inputDto) {

        GetDeliveryTypeFlagOutputDto outDto = new GetDeliveryTypeFlagOutputDto();

        // 店舗情報取得
        MStore store = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        outDto.setDeliveryTypeFlag(store.getDeliveryTypeFlag());

        return outDto;
    }

    /**
     * トークン認証取得.
     *
     * @param storeId       取得条件
     * @param receivablesId 受付ID
     * @return トークン認証時間情報
     */
    @Override
    public String getTokenByReceivablesId(String storeId, String receivablesId) {
        MToken token = tokenRepository
            .findByStoreIdAndReceivablesIdAndDelFlag(storeId, receivablesId, Flag.OFF.getCode());
        if (token != null) {
            return token.getTokenId();
        }
        return null;
    }


    /**
     * 会計レシート印刷.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 店舗情報
     */
    @Override
    public String accountingPrintPrint(String storeId, String receivablesId) {
        // 印刷
        OOrderSummary orderSummaryDto = orderSummaryRepository
            .findSummaryInfoByreceivablesId(storeId, receivablesId);
        SlipInputDto slipInputDto = new SlipInputDto();
        slipInputDto.setOrderSummaryId(orderSummaryDto.getOrderSummaryId());
        slipInputDto.setStoreId(orderSummaryDto.getStoreId());
        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        slipInputDto.setUserName(userOperCd);

        // 小計
        slipInputDto.setSubtotal(orderSummaryDto.getOrderAmount());

        if (!Objects.isNull(orderSummaryDto.getPriceDiscountAmount())) {
            // 値引き
            slipInputDto
                .setPriceDiscountAmount(String.valueOf(orderSummaryDto.getPriceDiscountAmount()));
            slipInputDto.setSotoTax(
                orderSummaryDto.getPaymentAmount().add(orderSummaryDto.getPriceDiscountAmount())
                    .subtract(orderSummaryDto.getOrderAmount()));
        } else if (!Objects.isNull(orderSummaryDto.getPriceDiscountRate())) {
            //割引
            slipInputDto
                .setPriceDiscountRate(String.valueOf(orderSummaryDto.getPriceDiscountRate()));
            slipInputDto.setSotoTax(orderSummaryDto.getPaymentAmount()
                .divide(orderSummaryDto.getPriceDiscountRate(), BigDecimal.ROUND_HALF_UP)
                .subtract(orderSummaryDto.getOrderAmount()));
        } else {
            slipInputDto.setSotoTax(
                orderSummaryDto.getPaymentAmount().subtract(orderSummaryDto.getOrderAmount()));
        }
        //支払金額
        slipInputDto.setPaymentAmount(orderSummaryDto.getPaymentAmount());
        slipInputDto.setTerminalType("1");
        slipInputDto.setAccountingPrintFlag(Flag.ON.getCode());
        OrderAccountInfoDto orderAccountInfoDto = printDataSharedService
            .getOrderAccountPrintData(slipInputDto);
        SlipPrintDto slipPrintDto = new SlipPrintDto();
        slipPrintDto.setOrderAccountInfoDto(orderAccountInfoDto);
        return JSON.toJSONString(slipPrintDto);
    }

    /**
     * キャッシュトーアオーペン.
     *
     * @param storeId 店舗ID
     * @return 店舗情報
     */
    @Override
    public String getOpenCashDoorIp(String storeId) {

        // プリンター情報を取得する
        PrintDto printDto = receiptRepository
            .getPrintInfo(storeId, CommonConstants.ORDER_ACCOUNT_PRINT);
        if (Objects.isNull(printDto)) {
            throw new UsernameNotFoundException(
                messageSource.getMessage("e.qr.ph.082", null, Locale.JAPAN));
        }
        return printDto.getPrintIp();
    }


    /**
     * 点検精算初期化情報取得.
     *
     * @param inputDto 点検精算
     * @return 店舗情報
     */
    @Override
    public InspectionSettleInitOutputDto getInspectionSettleInit(
        InspectionSettleInitInputDto inputDto) {

        InspectionSettleInitOutputDto outputDto = new InspectionSettleInitOutputDto();
        // 営業時間取得
        StoreOpenColseTimeDto storeOpenColseTimeDto = itemInfoSharedService
            .getStoreOpenColseTime(inputDto.getStoreId());
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        Comparator<ZonedDateTime> comparator = Comparator.comparing(
            zdt -> zdt.truncatedTo(ChronoUnit.MINUTES));
        // 点検の場合
        if (Objects.equals(inputDto.getSettleType(), SettleType.INSPECTION.getCode())) {
            // 営業時間内の場合
            if (comparator.compare(storeOpenColseTimeDto.getStartTime(), nowDateTime) == -1
                && comparator.compare(storeOpenColseTimeDto.getEndTime(), nowDateTime) >= 0) {
                // 現金点検精算金額取得
                BigDecimal appointedDayFluctuationAmount = paymentDetailRepository
                    .getInspectionSettleCashPayAmount(inputDto.getStoreId(),
                        storeOpenColseTimeDto.getStartTime(), storeOpenColseTimeDto.getEndTime(),
                        AccountsType.CASH.getCode());
                // 本日増減額
                outputDto.setTodayFluctuationAmount(
                    Objects.isNull(appointedDayFluctuationAmount) ? BigDecimal.ZERO
                        : appointedDayFluctuationAmount);

            } else {
                // 営業時間外の場合
                outputDto.setTodayFluctuationAmount(BigDecimal.ZERO);
            }
            // 翌日繰越金
            Map<String, Object> inspectionSettleMap = inspectionSettleRepository
                .getNextDayTransferredAmountAndInspectionSettleDate(inputDto.getStoreId(),
                    SettleType.SETTLE.getCode());
            // 前日繰越金
            outputDto.setPreviousDayTransferredAmount(
                new BigDecimal(
                    Objects.isNull(inspectionSettleMap.get("nextDayTransferredAmount")) ? "0"
                        : inspectionSettleMap.get("nextDayTransferredAmount").toString()));

        } else {

            // 精算の場合、
            if (StringUtils.isEmpty(inputDto.getInspectionSettleDate())) {
                // 確認回数
                //当日の精算回数取得
                List<String> settleList = inspectionSettleRepository
                    .getSettleDate(inputDto.getStoreId(), inputDto.getSettleType(),
                        storeOpenColseTimeDto.getStartTime(),
                        nowDateTime);
                if (settleList.size() > 0) {
                    // 1回以上の場合
                    outputDto.setInspectionSettleDate(settleList.get(0));
                } else {
                    //0回の場合
                    BigDecimal appointedDayFluctuationAmount = paymentDetailRepository
                        .getInspectionSettleCashPayAmount(inputDto.getStoreId(),
                            storeOpenColseTimeDto.getStartTime(),
                            storeOpenColseTimeDto.getEndTime(),
                            AccountsType.CASH.getCode());
                    outputDto.setTodayFluctuationAmount(
                        Objects.isNull(appointedDayFluctuationAmount) ? BigDecimal.ZERO
                            : appointedDayFluctuationAmount);
                    // 翌日繰越金
                    Map<String, Object> inspectionSettleMap = inspectionSettleRepository
                        .getNextDayTransferredAmountAndInspectionSettleDate(inputDto.getStoreId(),
                            inputDto.getSettleType());
                    outputDto.setPreviousDayTransferredAmount(
                        new BigDecimal(
                            Objects.isNull(inspectionSettleMap.get("nextDayTransferredAmount"))
                                ? "0"
                                : inspectionSettleMap.get("nextDayTransferredAmount").toString()));
                }
            } else {
                // 前回精算削除
                inspectionSettleRepository
                    .updateSettleDelFlag(inputDto.getStoreId(), inputDto.getSettleType(),
                        inputDto.getInspectionSettleDate());
                // 翌日繰越金
                Map<String, Object> inspectionSettleMap = inspectionSettleRepository
                    .getNextDayTransferredAmountAndInspectionSettleDate(inputDto.getStoreId(),
                        inputDto.getSettleType());
                //0回の場合
                BigDecimal appointedDayFluctuationAmount = paymentDetailRepository
                    .getInspectionSettleCashPayAmount(inputDto.getStoreId(),
                        storeOpenColseTimeDto.getStartTime(), storeOpenColseTimeDto.getEndTime(),
                        AccountsType.CASH.getCode());
                outputDto.setTodayFluctuationAmount(
                    Objects.isNull(appointedDayFluctuationAmount) ? BigDecimal.ZERO
                        : appointedDayFluctuationAmount);

                outputDto.setPreviousDayTransferredAmount(
                    new BigDecimal(
                        Objects.isNull(inspectionSettleMap.get("nextDayTransferredAmount")) ? "0"
                            : inspectionSettleMap.get("nextDayTransferredAmount").toString()));
            }
        }
        return outputDto;
    }

    /**
     * 点検精算.
     *
     * @param inputDto 点検精算
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registInspectionSettle(InspectionSettleInputDto inputDto) {
        OInspectionSettle inspectionSettleDto = new OInspectionSettle();
        // 時間取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // 店舗ID
        inspectionSettleDto.setStoreId(inputDto.getStoreId());
        // 点検精算日
        inspectionSettleDto.setInspectionSettleDate(nowDateTime);
        // 前日繰越金
        inspectionSettleDto
            .setPreviousDayTransferredAmount(inputDto.getPreviousDayTransferredAmount());
        // 本日増減額
        inspectionSettleDto
            .setTodayFluctuationAmount(inputDto.getTodayFluctuationAmount());
        // 実際レジ内現金金額
        inspectionSettleDto
            .setPracticalCashRegisterAmount(inputDto.getPracticalCashRegisterAmount());
        // 計算上レジ内現金金額
        inspectionSettleDto
            .setCalculationCashRegisterAmount(inputDto.getCalculationCashRegisterAmount());
        // 現金差異
        inspectionSettleDto.setCashDifferenceAmount(inputDto.getCashDifferenceAmount());
        // 出金
        inspectionSettleDto.setOutAmount(inputDto.getOutAmount());
        // 銀行預入額
        inspectionSettleDto.setBankDepositAmount(inputDto.getBankDepositAmount());
        // 翌日繰越金
        inspectionSettleDto.setNextDayTransferredAmount(inputDto.getNextDayTransferredAmount());
        // 本日仕入（原価）
        inspectionSettleDto.setPurchasingCost(inputDto.getPurchasingCost());
        // 精算区分
        inspectionSettleDto.setSettleType(inputDto.getSettleType());
        // 削除フラグ
        inspectionSettleDto.setDelFlag(Flag.OFF.getCode());
        // 登録日時
        inspectionSettleDto.setInsDateTime(nowDateTime);
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            // 登録者
            inspectionSettleDto.setInsOperCd(userInfo.get());
            // 更新者
            inspectionSettleDto.setUpdOperCd(userInfo.get());
        }
        // 更新日時
        inspectionSettleDto.setUpdDateTime(nowDateTime);
        // バージョン
        inspectionSettleDto.setVersion(0);
        inspectionSettleRepository.save(inspectionSettleDto);


    }


    /**
     * 出前情報取得.
     *
     * @param inputDto 出前情報
     * @return 店舗情報
     */
    @Override
    public String printDeliverOrder(PrintDeliverOrderInputDto inputDto) {
        SlipInputDto slipInputDto = new SlipInputDto();
        slipInputDto.setStoreId(inputDto.getStoreId());
        slipInputDto.setOrderSummaryId(inputDto.getOrderSummaryId());
        slipInputDto.setDeliveryFlag(OrderType.DELIVERY.getCode());
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            slipInputDto.setUserName(userInfo.get());
        }

        SlipPrintDto slipPrintDto = new SlipPrintDto();
        if (Objects.equals(inputDto.getDeliverPrintType(), DeliverPrintType.ACCOUNTING.getCode())) {

            //会計印刷
            slipPrintDto.setOrderAccountInfoDto(
                printDataSharedService.getOrderAccountPrintData(slipInputDto));
        } else if (Objects
            .equals(inputDto.getDeliverPrintType(), DeliverPrintType.KITCHEN.getCode())) {

            //キッチンリスト印刷
            slipPrintDto.setKitchenDto(printDataSharedService.getKitchenPrintData(slipInputDto));
        } else {
            slipInputDto.setPaymentAmount(inputDto.getPaymentAmount());
            //領収書
            slipPrintDto
                .setReceiptInfoDto(printDataSharedService.getReceiptPrintData(slipInputDto));
        }

        return JSON.toJSONString(slipPrintDto);
    }
}
