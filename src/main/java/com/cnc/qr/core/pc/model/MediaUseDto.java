package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 媒体DTO.
 */
@Data
public class MediaUseDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 媒体用途Key.
     */
    private String useKey;

    /**
     * 媒体用途Text.
     */
    private String useText;
}
