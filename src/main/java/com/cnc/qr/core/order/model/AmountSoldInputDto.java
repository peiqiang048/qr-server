package com.cnc.qr.core.order.model;

import com.cnc.qr.common.constants.RegexConstants;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * 売上リスト.
 */
@Data
public class AmountSoldInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 開始時間.
     */
    private String startDate;

    /**
     * 終了時間.
     */
    private String endDate;

    /**
     * 支払方式.
     */
    private String paymentMethod;


}
