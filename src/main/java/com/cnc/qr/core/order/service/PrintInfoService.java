package com.cnc.qr.core.order.service;

import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.QrCodeInputDto;
import com.cnc.qr.common.shared.model.QrCodeOutputDto;
import com.cnc.qr.common.shared.model.SlipInputDto;
import com.cnc.qr.core.order.model.PrintCustomerKitchenDto;

/**
 * プリンター伝票サービス.
 */
public interface PrintInfoService {

    /**
     * QRコード印刷.
     *
     * @param inputDto 取得条件
     * @return QRコード印刷データ取得
     */
    QrCodeOutputDto getPrintQrCode(QrCodeInputDto inputDto);

    /**
     * 会計印刷.
     *
     * @param inputDto 取得条件
     * @return 会計印刷データ取得
     */
    OrderAccountInfoDto getPrintOrderAccount(SlipInputDto inputDto);

    /**
     * 客用厨房印刷.
     *
     * @param inputDto 取得条件
     * @return 客用厨房印刷データ取得
     */
    PrintCustomerKitchenDto getCustomerKitchen(SlipInputDto inputDto);

}
