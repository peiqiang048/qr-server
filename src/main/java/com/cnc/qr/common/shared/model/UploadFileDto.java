package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ファイルアップロード結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDto implements Serializable {

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
