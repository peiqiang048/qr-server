package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.GetPrintInputDto;
import com.cnc.qr.core.pc.model.GetPrintOutputDto;
import com.cnc.qr.core.pc.model.PrintDelInputDto;
import com.cnc.qr.core.pc.model.PrintListInputDto;
import com.cnc.qr.core.pc.model.PrintListOutputDto;
import com.cnc.qr.core.pc.model.RegistPrintInputDto;

/**
 * プリンターサービス.
 */
public interface PrintService {

    /**
     * プリンター一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    PrintListOutputDto getPrintList(PrintListInputDto inputDto);

    /**
     * プリンター情報取得.
     *
     * @param inputDto 取得条件
     */
    GetPrintOutputDto getPrint(GetPrintInputDto inputDto);

    /**
     * プリンター保存.
     *
     * @param inputDto 取得条件
     */
    void savePrint(RegistPrintInputDto inputDto);

    /**
     * プリンター削除.
     *
     * @param inputDto 取得条件
     */
    void delPrint(PrintDelInputDto inputDto);

}
