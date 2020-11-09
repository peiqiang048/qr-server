package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 返品原因情報取得検索結果.
 */
@Data
public class GetReturnReasonOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 返品原因情報.
     */
    private List<GetReturnReasonDto> returnReason;
}
