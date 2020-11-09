package com.cnc.qr.core.order.service;

import com.cnc.qr.common.dto.AcountDTO;
import com.cnc.qr.common.entity.MStore;
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
import com.cnc.qr.core.order.model.InspectionSettleInitInputDto;
import com.cnc.qr.core.order.model.InspectionSettleInitOutputDto;
import com.cnc.qr.core.order.model.InspectionSettleInputDto;
import com.cnc.qr.core.order.model.MenuInputDto;
import com.cnc.qr.core.order.model.MenuOutputDto;
import com.cnc.qr.core.order.model.PrintDeliverOrderInputDto;
import com.cnc.qr.core.order.model.StoreDeviceRegistInputDto;
import com.cnc.qr.core.order.model.StoreIdVerificationInputDto;
import com.cnc.qr.core.order.model.UserDto;
import com.cnc.qr.security.model.ControlDto;
import java.util.List;

/**
 * 店舗情報取得サービス.
 */
public interface StoreInfoService {

    /**
     * 店舗店員確認要否取得.
     *
     * @param storeId 取得条件
     * @return 店舗情報
     */
    String getStoreStaffCheck(String storeId);

    /**
     * 店舗情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗情報
     */
    GetStoreInfoOutputDto getStoreInfo(GetStoreInfoInputDto inputDto);

    /**
     * 店舗情報取得.
     *
     * @param businessId 取得条件
     * @return 店舗情報
     */
    List<MStore> getStore(String businessId);

    /**
     * 店舗言語情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗言語情報
     */
    GetActiveLanguageOutputDto getActiveLanguage(GetActiveLanguageInputDto inputDto);

    /**
     * 店舗媒体情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗媒体情報
     */
    GetStoreAdverPicOutputDto getStoreAdverPic(GetStoreAdverPicInputDto inputDto);

    /**
     * 支払方式取得.
     *
     * @param inputDto 取得条件
     * @return 支払方式情報
     */
    GetPaymentInfoOutputDto getPaymentType(GetPaymentInfoInputDto inputDto);

    /**
     * 店舗受付報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗言語情報
     */
    GetReceivablesInfoOutputDto getReceivablesInfo(GetReceivablesInfoInputDto inputDto);

    /**
     * 店舗ユーザ情報取得.
     *
     * @param storeId 取得条件
     * @return 店舗ユーザ情報
     */
    List<UserDto> getUser(String storeId);

    /**
     * 店舗メニュー情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗メニュー情報
     */
    MenuOutputDto getMenu(MenuInputDto inputDto);

    /**
     * トップ画面情報取得.
     *
     * @param inputDto 取得条件
     * @return トップ画面情報
     */
    GetHomePageInfoOutputDto getHomePageInfo(GetHomePageInfoInputDto inputDto);

    /**
     * 支払方式取得.
     *
     * @param inputDto 取得条件
     * @return 支払方式情報
     */
    GetPayLaterPaymentInfoOutputDto getPayLaterPaymentType(GetPayLaterPaymentInfoInputDto inputDto);

    /**
     * トークン認証時間取得.
     *
     * @param storeId 取得条件
     * @return トークン認証時間情報
     */
    Long getTokenValidityInMilliseconds(String storeId);

    /**
     * トークン認証時間取得.
     *
     * @param storeId 取得条件
     * @return トークン認証時間情報
     */
    Long getPrintPollMilliseconds(String storeId);

    /**
     * 店舗コントロール条件取得.
     *
     * @param storeId 取得条件
     * @return コントロール条件
     */
    ControlDto getStoreControl(String storeId);

    /**
     * ユーザ情報取得.
     *
     * @param storeId 店舗ID
     * @param loginId ユーザID
     * @return ユーザ情報
     */
    AcountDTO getUserWithAuthorities(String storeId, String loginId);

    /**
     * 店舗情報検証.
     *
     * @param inputDto 検証データ
     */
    void storeIdVerification(StoreIdVerificationInputDto inputDto);


    /**
     * 店舗端末情報設定.
     *
     * @param inputDto 検証データ
     */
    void storeDeviceRegist(StoreDeviceRegistInputDto inputDto);

    /**
     * 店舗トークン情報設定.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param accessToken   トークン
     */
    void storeTokenRegister(String storeId, String receivablesId, String accessToken);

    /**
     * 出前仕方フラグ取得.
     *
     * @param inputDto 検証データ
     */
    GetDeliveryTypeFlagOutputDto getDeliveryTypeFlag(GetDeliveryTypeFlagInputDto inputDto);

    /**
     * トークン認証取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return トークン認証時間情報
     */
    String getTokenByReceivablesId(String storeId, String receivablesId);

    /**
     * 会計レシート印刷.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 店舗情報
     */
    String accountingPrintPrint(String storeId, String receivablesId);


    /**
     * 会計レシート印刷.
     *
     * @param storeId 店舗ID
     * @return 店舗情報
     */
    String getOpenCashDoorIp(String storeId);


    /**
     * 点検精算初期化情報取得.
     *
     * @param inputDto 点検精算
     * @return 店舗情報
     */
    InspectionSettleInitOutputDto getInspectionSettleInit(InspectionSettleInitInputDto inputDto);

    /**
     * 点検精算初期化情報取得.
     *
     * @param inputDto 点検精算
     */
    void registInspectionSettle(InspectionSettleInputDto inputDto);

    /**
     * 出前情報取得.
     *
     * @param inputDto 出前情報
     * @return 店舗情報
     */
    String printDeliverOrder(PrintDeliverOrderInputDto inputDto);
}
