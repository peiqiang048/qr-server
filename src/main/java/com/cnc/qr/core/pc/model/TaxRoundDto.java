package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 税端数処理区DTO.
 */
@Data
public class TaxRoundDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 税端数処理区分CD.
     */
    @NotBlank
    private String taxRoundTypeCode;

    /**
     * 税端数処理区分名.
     */
    @NotBlank
    private String taxRoundTypeName;
}
