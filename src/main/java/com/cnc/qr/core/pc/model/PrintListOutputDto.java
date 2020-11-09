package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * プリンター情報取得検索結果.
 */
@Data
public class PrintListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンター情報.
     */
    private List<PrintDto> printList;
}
