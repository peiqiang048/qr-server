package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 飲み放題ステータス更新データ.
 */
@Data
public class ChangeBuffetItemStatusInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 飲み放題IDリスト.
     */
    private List<BuffetIdDto> buffetList;

    /**
     * 销售区分.
     */
    private String salesClassification;
}
