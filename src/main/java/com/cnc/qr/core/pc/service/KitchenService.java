package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.DeleteKitchenInputDto;
import com.cnc.qr.core.pc.model.GetKitchenInputDto;
import com.cnc.qr.core.pc.model.GetKitchenOutputDto;
import com.cnc.qr.core.pc.model.KitchenListInputDto;
import com.cnc.qr.core.pc.model.KitchenListOutputDto;
import com.cnc.qr.core.pc.model.RegistKitchenInputDto;

/**
 * キッチンサービス.
 */
public interface KitchenService {

    /**
     * キッチン一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    KitchenListOutputDto getKitchenList(KitchenListInputDto inputDto);

    /**
     * キッチン情報取得.
     *
     * @param inputDto 取得条件
     */
    GetKitchenOutputDto getKitchen(GetKitchenInputDto inputDto);

    /**
     * キッチン編集.
     *
     * @param inputDto 取得条件
     */
    void registKitchen(RegistKitchenInputDto inputDto);

    /**
     * キッチン削除.
     *
     * @param inputDto 取得条件
     */
    void deleteKitchen(DeleteKitchenInputDto inputDto);

}
