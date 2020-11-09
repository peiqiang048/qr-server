package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セット詳細伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetItemDetailDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 商品個数.
     */
    private String itemCount;

}
