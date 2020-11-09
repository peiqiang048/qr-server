package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * テーブルアウトプット情報.
 */
@Data
public class GetDeliveryTypeFlagOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 出前仕方フラグ.
     */
    private String deliveryTypeFlag;
}
