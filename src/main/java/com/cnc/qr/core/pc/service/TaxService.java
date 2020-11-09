package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.GetTaxInputDto;
import com.cnc.qr.core.pc.model.GetTaxOutputDto;
import com.cnc.qr.core.pc.model.RegistTaxInputDto;
import com.cnc.qr.core.pc.model.TaxDelInputDto;
import com.cnc.qr.core.pc.model.TaxListInputDto;
import com.cnc.qr.core.pc.model.TaxListOutputDto;

/**
 * 税設定サービス.
 */
public interface TaxService {

    /**
     * 税設定一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    TaxListOutputDto getTaxList(TaxListInputDto inputDto);

    /**
     * 税設定情報取得.
     *
     * @param inputDto 取得条件
     */
    GetTaxOutputDto getTax(GetTaxInputDto inputDto);

    /**
     * 税設定保存.
     *
     * @param inputDto 取得条件
     */
    void saveTax(RegistTaxInputDto inputDto);

    /**
     * 税設定削除.
     *
     * @param inputDto 取得条件
     */
    void delTax(TaxDelInputDto inputDto);

}
