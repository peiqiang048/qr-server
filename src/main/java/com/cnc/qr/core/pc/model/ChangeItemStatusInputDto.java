package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ユーザステータス更新データ.
 */
@Data
public class ChangeItemStatusInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザーIDリスト.
     */
    private List<ItemIdDto> itemList;

    /**
     * 区分.
     */
    private String salesClassification;
}