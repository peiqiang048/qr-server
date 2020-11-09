package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客用伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 客用伝票ラベル.
     */
    private CustomerOrderLabelDto customerOrderLabelDto;

    /**
     * 客用伝票.
     */
    private CustomerOrderDto customerOrderDto;

}
