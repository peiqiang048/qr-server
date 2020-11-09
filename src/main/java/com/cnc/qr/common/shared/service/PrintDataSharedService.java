package com.cnc.qr.common.shared.service;

import com.cnc.qr.common.shared.model.CustomerOrderInfoDto;
import com.cnc.qr.common.shared.model.KitchenOutputDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.QrCodeInputDto;
import com.cnc.qr.common.shared.model.ReceiptInfoDto;
import com.cnc.qr.common.shared.model.RgDailyReportDto;
import com.cnc.qr.common.shared.model.RgDailyReportInputDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.core.order.model.ChangePrintStatusInputDto;
import com.cnc.qr.core.order.model.GetPrintOrderListInputDto;
import com.cnc.qr.core.order.model.QrCodeDeliveryInputDto;
import java.util.List;

/**
 * プリンターデータ情報共有サービス.
 */
public interface PrintDataSharedService {

    /**
     * QR報取得.
     *
     * @param inputDto 検索条件
     * @return QR情報
     */
    String getQrCodePrintData(QrCodeInputDto inputDto);

    /**
     * 出前QR報取得.
     *
     * @param inputDto 検索条件
     * @return 出前QR情報
     */
    String getQrCodeDeliveryPrintData(QrCodeDeliveryInputDto inputDto);

    /**
     * キッチン報取得.
     *
     * @param inputDto 検索条件
     * @return キッチン情報
     */
    KitchenOutputDto getKitchenPrintData(SlipInputDto inputDto);

    /**
     * 客用報取得.
     *
     * @param inputDto 検索条件
     * @return 客用情報
     */
    CustomerOrderInfoDto getCustomerOrderPrintData(SlipInputDto inputDto);

    /**
     * 会計報取得.
     *
     * @param inputDto 検索条件
     * @return 会計情報
     */
    OrderAccountInfoDto getOrderAccountPrintData(SlipInputDto inputDto);


    /**
     * 領収書報取得.
     *
     * @param inputDto 検索条件
     * @return 領収書情報
     */
    ReceiptInfoDto getReceiptPrintData(SlipInputDto inputDto);

    /**
     * 印刷注文取得.
     *
     * @param inputDto 検索条件
     * @return 印刷注文情報
     */
    List<String> getPrintOrderList(GetPrintOrderListInputDto inputDto);

    /**
     * 印刷状態変更.
     *
     * @param inputDto 検索条件
     */
    void changePrintStatus(ChangePrintStatusInputDto inputDto);

    /**
     * 点検精算印刷.
     *
     * @param storeId    店舗ID
     * @param settleType 点検精算区分
     * @return 点検精算印刷情報
     */
    String inspectionSettlePrint(String storeId, String settleType);


    /**
     * 龍高飯店日報.
     *
     * @param inputDto 検索条件
     * @return 龍高飯店日報
     */
    RgDailyReportDto getDailyReportPrintData(RgDailyReportInputDto inputDto);
}
