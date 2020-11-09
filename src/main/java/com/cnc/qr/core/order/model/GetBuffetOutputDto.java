package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 放題情報取得検索結果.
 */
@Data
public class GetBuffetOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 放題ID.
     */
    private Integer buffetId;

    /**
     * 放題名称.
     */
    private String buffetName;

    /**
     * 放題金额.
     */
    private BigDecimal buffetAmount;

    /**
     * 放題份数.
     */
    private Integer buffetCount;

    /**
     * 剩余时间.
     */
    private Integer buffetRemainTime;

    /**
     * 人数.
     */
    private Integer customerCount;
}
