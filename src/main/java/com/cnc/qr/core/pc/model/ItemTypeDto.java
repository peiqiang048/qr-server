package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 商品区分DTO.
 */
@Data
public class ItemTypeDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     *  商品区分.
     */
    @NotBlank
    private String itemType;

    /**
     *  商品区分名.
     */
    @NotBlank
    private String itemTypeName;
}
