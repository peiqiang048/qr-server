package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 税区分DTO.
 */
@Data
public class TaxTypeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 税区分CD.
     */
    @NotBlank
    private String taxCode;

    /**
     * 税区分名.
     */
    @NotBlank
    private String taxName;
}
