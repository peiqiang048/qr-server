package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 出前QRコード印刷入力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePrintStatusInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 印刷注文リスト.
     */
    private List<ChangePrintStatusDto> orderList;
}
