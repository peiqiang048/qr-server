package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 放题情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuffetDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 放题ID.
     */
    private Integer buffetId;

    /**
     * 放题名称.
     */
    private String buffetName;

}
