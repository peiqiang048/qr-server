package com.cnc.qr.core.order.service;

import com.cnc.qr.core.order.model.TaxInfoInputDto;
import com.cnc.qr.core.order.model.TaxInfoOutputDto;
import com.cnc.qr.core.order.model.TaxValueInputDto;
import com.cnc.qr.core.order.model.TaxValueOutputDto;
import java.math.BigDecimal;

/**
 * 税情報サービス.
 */
public interface TaxInfoService {

    /**
     * 税情報取得.
     *
     * @param inputDto 取得索条件
     * @return 税情報
     */
    TaxInfoOutputDto getTaxInfo(TaxInfoInputDto inputDto);

    /**
     * 税情報取得.
     *
     * @param inputDto 取得条件
     * @return 税情報
     */
    TaxValueOutputDto getTaxValue(TaxValueInputDto inputDto);

    /**
     * 配達費取得.
     *
     * @return 配達費
     */
    BigDecimal getCateringCharge(String storeId, BigDecimal taxTotal);
}
