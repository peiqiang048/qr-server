package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザ区分情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 区分キー.
     */
    private String classificationKey;

    /**
     * 区分名.
     */
    private String classificationText;
}
