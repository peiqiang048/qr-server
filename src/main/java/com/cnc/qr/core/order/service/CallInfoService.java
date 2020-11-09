package com.cnc.qr.core.order.service;

import com.cnc.qr.core.order.model.CallInfoInputDto;
import com.cnc.qr.core.order.model.CallTebleInfoDto;
import com.cnc.qr.core.order.model.GetCallInputDto;
import com.cnc.qr.core.order.model.GetCallOutputDto;
import com.cnc.qr.core.order.model.ModifyCallStatusInputDto;

/**
 * 店員呼出サービス.
 */
public interface CallInfoService {

    /**
     * 店員呼出取得.
     *
     * @param inputDto 取得条件
     */
    CallTebleInfoDto callInfo(CallInfoInputDto inputDto);

    /**
     * 呼出中取得.
     *
     * @param inputDto 取得条件
     */
    GetCallOutputDto getCallList(GetCallInputDto inputDto);

    /**
     * 呼出状態変更.
     *
     * @param inputDto 呼出状態変更データ
     */
    void modifyCallStatus(ModifyCallStatusInputDto inputDto);
}
