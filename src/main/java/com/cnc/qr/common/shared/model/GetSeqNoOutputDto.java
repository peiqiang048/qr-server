package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * シーケンスNo取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSeqNoOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * シーケンスNo.
     */
    private Integer seqNo;
}
