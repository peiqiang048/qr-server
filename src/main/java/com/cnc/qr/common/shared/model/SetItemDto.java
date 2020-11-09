package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セット商品伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetItemDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * セット商品名.
     */
    private String setItemName;

    /**
     * セット個数.
     */
    private String setItemCount;

    /**
     * セット価格.
     */
    private String setItemPrice;

    /**
     * セット詳細リスト.
     */
    private List<SetItemDetailDto> setItemDetailList;

}
