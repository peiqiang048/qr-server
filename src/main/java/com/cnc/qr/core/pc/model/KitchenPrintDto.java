package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * キッチンプリンタDTO.
 */
@Data
public class KitchenPrintDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * プリンタID.
     */
    @NotBlank
    private String printId;
}
