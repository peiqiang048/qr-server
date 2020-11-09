package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * レシート情報取得検索結果.
 */
@Data
public class ReceiptListOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * レシート情報.
     */
    private List<ReceiptDto> receiptList;
}
