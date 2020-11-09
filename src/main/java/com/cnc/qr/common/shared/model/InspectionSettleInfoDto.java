package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点検精算伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSettleInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 点検精算伝票ラベル.
     */
    private InspectionSettleLabelDto inspectionSettleLabelDto;

    /**
     * 点検精算伝票.
     */
    private InspectionSettleDto inspectionSettleDto;

}
