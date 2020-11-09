package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 都道府県情報.
 */
@Data
public class PrefectureListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 都道府県ID.
     */
    private String prefectureId;

    /**
     * 都道府県名称.
     */
    private String prefectureName;
}
