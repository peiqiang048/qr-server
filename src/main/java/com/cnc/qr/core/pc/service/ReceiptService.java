package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.GetReceiptInputDto;
import com.cnc.qr.core.pc.model.GetReceiptOutputDto;
import com.cnc.qr.core.pc.model.ReceiptListInputDto;
import com.cnc.qr.core.pc.model.ReceiptListOutputDto;
import com.cnc.qr.core.pc.model.RegistReceiptInputDto;

/**
 * レシートサービス.
 */
public interface ReceiptService {

    /**
     * レシート一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    ReceiptListOutputDto getReceiptList(ReceiptListInputDto inputDto);

    /**
     * レシート情報取得.
     *
     * @param inputDto 取得条件
     */
    GetReceiptOutputDto getReceipt(GetReceiptInputDto inputDto);

    /**
     * レシート保存.
     *
     * @param inputDto 取得条件
     */
    void saveReceipt(RegistReceiptInputDto inputDto);
}
