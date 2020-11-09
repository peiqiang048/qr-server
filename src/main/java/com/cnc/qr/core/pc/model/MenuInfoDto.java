package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * メニュー情報.
 */
@Data
public class MenuInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 親メニューID.
     */
    private String parentMenuId;

    /**
     * 親メニュー名.
     */
    private String parentMenuName;

    /**
     * 親メニューiconのurl.
     */
    private String parentMenuIconUrl;

    /**
     * 子メニューリスト.
     */
    private List<ChildrenMenuInfoDto> childrenList;
}
