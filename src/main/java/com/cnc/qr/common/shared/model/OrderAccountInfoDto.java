package com.cnc.qr.common.shared.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会計用伝票印刷.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAccountInfoDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    /**
     * 注文会計伝票ラベル.
     */
    private OrderAccountLabelDto orderAccountLabelDto;


    /**
     * 注文会計印刷.
     */
    private OrderAccountDto orderAccountDto;
}
