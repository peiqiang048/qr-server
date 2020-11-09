package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.pc.model.GetSelectedAreaListOutputDto;

/**
 * エリア情報サービス.
 */
public interface AreaInfoService {

    /**
     * エリア情報取得.
     *
     * @param inputDto 取得索条件
     * @return エリア情報
     */
    GetSelectedAreaListOutputDto getSelectedAreaList(GetSelectedAreaListInputDto inputDto);
}
