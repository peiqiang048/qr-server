package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 受付情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OreceivablesGridDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 受付番号.
     */
    private Integer receptionNo;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 受付時間.
     */
    private ZonedDateTime receptionTime;
}
