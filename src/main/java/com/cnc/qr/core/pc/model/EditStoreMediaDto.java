package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 媒体DTO.
 */
@Data
public class EditStoreMediaDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 媒体用途区分.
     */
    private String mediaUse;

    /**
     * 店舗媒体.
     */
    private List<StoreMediaDto> storeMediaList;
}
