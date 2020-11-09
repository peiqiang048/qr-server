package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 軽減税率適用区分DTO.
 */
@Data
public class TaxReliefApplyTypeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 軽減税率適用区分CD.
     */
    @NotBlank
    private String taxReliefApplyTypeCode;

    /**
     * 軽減税率適用区分名.
     */
    @NotBlank
    private String taxReliefApplyTypeName;
}
