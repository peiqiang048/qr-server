package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * プリンター接続方法DTO.
 */
@Data
public class PrintConnectionMethodDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 接続方法CD.
     */
    @NotBlank
    private String connectionMethodCode;
    
    /**
     * 接続方法名.
     */
    @NotBlank
    private String connectionMethodName;
}
