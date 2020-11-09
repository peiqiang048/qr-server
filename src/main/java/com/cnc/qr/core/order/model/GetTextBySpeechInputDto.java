package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 音声認識文字列取得条件.
 */
@Data
public class GetTextBySpeechInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 言語.
     */
    private String languages;

    /**
     * ファイル.
     */
    private MultipartFile file;
}
