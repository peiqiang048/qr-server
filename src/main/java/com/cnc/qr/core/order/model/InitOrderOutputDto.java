package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class InitOrderOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * プリンター.
     */
    private String printInfo;
}
