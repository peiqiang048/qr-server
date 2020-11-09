package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 配達料区分リストDTO.
 */
@Data
public class CateringPriceTypeListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 配達料区分コード.
     */
    private String cateringPriceTypeCd;

    /**
     * 配達料区分名.
     */
    private String cateringPriceTypeName;
}
