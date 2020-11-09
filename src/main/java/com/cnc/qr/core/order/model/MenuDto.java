package com.cnc.qr.core.order.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * メニュー結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;



    /**
     * メニューID.
     */
    private Integer menuId;

    /**
     * メニュー名.
     */
    private String menuName;

    /**
     * リンク.
     */
    private String menuLink;

    /**
     * メニューアイコン名.
     */
    private String menuIcon;
}
