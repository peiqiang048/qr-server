package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * プリンターチケット幅DTO.
 */
@Data
public class PrintSizeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * チケット幅CD.
     */
    @NotBlank
    private String printSizeCode;
    
    /**
     * チケット幅.
     */
    @NotBlank
    private String printSize;
}
