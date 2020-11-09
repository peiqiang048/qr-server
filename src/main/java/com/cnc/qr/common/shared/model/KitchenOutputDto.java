package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 厨房用印刷出力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KitchenOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文厨房伝票ラベル.
     */
    private KitchenSlipLabelDto kitchenSlipLabelDto;

    /**
     * 注文厨房伝票印刷.
     */
    private KitchenSlipDto kitchenSlipDto;


}
