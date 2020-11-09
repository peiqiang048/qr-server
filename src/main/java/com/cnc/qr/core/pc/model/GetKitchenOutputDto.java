package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * キッチン編集取得検索結果.
 */
@Data
public class GetKitchenOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * キッチン名.
     */
    private String kitchenName;

    /**
     * キッチンプリンターリスト.
     */
    private List<KitchenPrintDto> kitchenPrintList;

    /**
     * プリンタID.
     */
    private List<PrintInfoDto> printList;


}
