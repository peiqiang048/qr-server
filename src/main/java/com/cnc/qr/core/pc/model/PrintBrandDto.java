package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * プリンターブランドDTO.
 */
@Data
public class PrintBrandDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    
    /**
     * プリンタブランドCD.
     */
    @NotBlank
    private String brandCode;
    
    /**
     * プリンタブランド名.
     */
    @NotBlank
    private String brandName;
}
