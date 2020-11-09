package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * プリンターリストDTO.
 */
@Data
public class PrintListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンターID.
     */
    @NotBlank
    private String printId;
    
    /**
     * プリンター名.
     */
    @NotBlank
    private String printName;
}
