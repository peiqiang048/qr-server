package com.cnc.qr.core.acct.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 飲食区分取得検索条件.
 */
@Data
public class GetRefundsListInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 受付番号.
     */
    private Integer receivablesNo;
    
    /**
     * 开始时间.
     */
    private String orderDateStart;
    
    /**
     * 结束时间.
     */
    private String orderDateEnd;
}
