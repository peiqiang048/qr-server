package com.cnc.qr.core.order.model;

import com.cnc.qr.common.constants.RegexConstants;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * 受付番号廃棄.
 */
@Data
public class InspectionSettleInitInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 精算区分.
     */
    private String settleType;

    /**
     * 点検精算日.
     */
    private String inspectionSettleDate;
}
