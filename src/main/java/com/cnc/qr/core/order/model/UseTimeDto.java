package com.cnc.qr.core.order.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 利用時間DTO.
 */
@Data
public class UseTimeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 利用時間CD.
     */
    @NotBlank
    private String useTimeCode;
    
    /**
     * 利用時間.
     */
    @NotBlank
    private String useTime;
}
