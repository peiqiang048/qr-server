package com.cnc.qr.core.pc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 機能情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 機能ID.
     */
    private String modelId;

    /**
     * 機能名.
     */
    private String modelName;

    /**
     * コンストラクタ.
     * @param modelId 機能ID
     * @param modelName 機能名
     */
    public ModelInfoDto(Integer modelId, String modelName) {
        this.modelId = modelId.toString();
        this.modelName = modelName;
    }
}
