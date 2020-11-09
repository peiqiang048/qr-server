package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.common.shared.model.OrderAccountDto;
import com.cnc.qr.common.shared.model.OrderAccountLabelDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 会計用伝票印刷.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class OrderAccountOutResource extends CommonOutputResource {

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
