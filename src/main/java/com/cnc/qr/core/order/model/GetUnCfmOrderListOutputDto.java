package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 注文一覧情報取得結果.
 */
@Data
public class GetUnCfmOrderListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**`
     * 注文一覧情報.
     */
    private List<UnCfmOrderInfoDto> orderList;
}
