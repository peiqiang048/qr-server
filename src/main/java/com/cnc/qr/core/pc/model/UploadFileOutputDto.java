package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * ファイルアップロード結果.
 */
@Data
public class UploadFileOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ファイルパス.
     */
    private String filePath;

    /**
     * 圧縮ファイルパス.
     */
    private String fileSmallPath;
}
