package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザメニュー情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuInfoDataDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 親メニューID.
     */
    private Integer parentMenuId;

    /**
     * 親メニュー名.
     */
    private String parentMenuName;

    /**
     * 親メニューiconのurl.
     */
    private String parentMenuIconUrl;

    /**
     * 子メニューID.
     */
    private Integer childrenMenuId;

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
