package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * キッチン情報.
 */
@Data
public class KitchenInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * キッチンID.
     */
    @NotBlank
    private String kitchenId;
}