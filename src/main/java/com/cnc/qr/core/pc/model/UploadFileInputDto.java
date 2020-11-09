package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * ファイルアップロードデータ.
 */
@Data
public class UploadFileInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 利用区分.
     */
    private String useType;

    /**
     * ファイル.
     */
    private MultipartFile file;

}
