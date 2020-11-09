package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * オプション詳細伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDetailDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * オプション.
     */
    private String optionName;
    /**
     * オプション個数.
     */
    private String optionCount;
}
