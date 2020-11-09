package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * レシート編集取得検索結果.
 */
@Data
public class GetReceiptOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 票据名.
     */
    private String receiptName;

    /**
     * プリンターID.
     */
    private String printId;
    
    /**
     * プリンターリスト.
     */
    private List<PrintListDto> printList;




}
