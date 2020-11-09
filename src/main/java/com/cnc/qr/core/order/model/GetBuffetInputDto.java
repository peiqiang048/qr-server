package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 商品カテゴリー情報取得条件.
 */
@Data
public class GetBuffetInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    
    /**
     * 语言.
     */
    private String languages;

    /**
     * 受付ID.
     */
    private String receivablesId;
    
    /**
     * 放题ID.
     */
    private Integer buffetId;
}
