package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 呼出中情報取得検索結果.
 */
@Data
public class GetCallOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 呼出中情報.
     */
    private List<GetCallDto> callList;
}
