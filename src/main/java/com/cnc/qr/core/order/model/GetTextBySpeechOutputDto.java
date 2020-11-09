package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 音声認識文字列取得結果.
 */
@Data
public class GetTextBySpeechOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 認識結果.
     */
    private String transcript;
}
