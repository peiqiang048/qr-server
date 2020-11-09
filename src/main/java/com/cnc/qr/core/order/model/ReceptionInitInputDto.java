package com.cnc.qr.core.order.model;

import com.cnc.qr.common.constants.RegexConstants;
import java.io.Serializable;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * 受付選択可能テーブル情報取得条件.
 */
@Data
public class ReceptionInitInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 顧客人数.
     */
    private Integer customerCount;

}
