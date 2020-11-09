package com.cnc.qr.core.pc.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 媒体DTO.
 */
@Data
public class StoreMediaDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 媒体ID.
     */
    private String mediaId;

    /**
     * 媒体種類.
     */
    @NotBlank
    private String mediaType;

    /**
     * 媒体用途区分.
     */
    @NotBlank
    private String mediaUse;

    /**
     * 媒体Url.
     */
    @NotBlank
    private String mediaUrl;
}
