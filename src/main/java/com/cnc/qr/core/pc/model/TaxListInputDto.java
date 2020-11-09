package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
   * 税設定情報取得条件.
 */
@Data
public class TaxListInputDto implements Serializable {

    /**
            * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
            * ビジネスID.
     */
    private String businessId;

}
