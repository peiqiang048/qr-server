package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreOpenColseTimeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 営業開始時間.
     */
    private ZonedDateTime startTime;

    /**
     * 営業終了時間.
     */
    private ZonedDateTime endTime;

}
