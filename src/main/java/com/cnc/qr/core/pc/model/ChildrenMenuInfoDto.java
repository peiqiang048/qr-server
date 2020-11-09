package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 子メニュー情報.
 */
@Data
public class ChildrenMenuInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 子メニューID.
     */
    private String childrenMenuId;

    /**
     * 子メニュー名.
     */
    private String childrenMenuName;

    /**
     * 子メニューurl.
     */
    private String childrenMenuUrl;

    /**
     * 子メニューiconのurl.
     */
    private String childrenMenuIconUrl;
}
