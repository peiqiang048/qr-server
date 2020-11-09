package com.cnc.qr.core.pc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBuffetOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 放題名称.
     */
    private String buffetName;

    /**
     * 放題说明.
     */
    private String buffetInfo;

    /**
     * 放題单价.
     */
    private BigDecimal buffetPrice;

    /**
     * 放題時間.
     */
    private Integer buffetTime;

    /**
     * 税区分.
     */
    private Integer taxId;

    /**
     * コース大きな画像URL.
     */
    private String buffetLargePicUrl;

    /**
     * コースサムネイルURL.
     */
    private String buffetSmallPicUrl;

    /**
     * 附加商品ID.
     */
    private Integer attachItemId;
}
