package com.cnc.qr.core.order.model;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.common.shared.model.CustomerOrderInfoDto;
import com.cnc.qr.common.shared.model.KitchenOutputDto;
import com.cnc.qr.common.shared.model.OrderAccountInfoDto;
import com.cnc.qr.common.shared.model.QrCodeOutputDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 厨房客用伝票.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrintCustomerKitchenDto {


    /**
     * 厨房伝票.
     */
    private KitchenOutputDto kitchenOutputDto;

    /**
     * 客用伝票.
     */
    private CustomerOrderInfoDto customerOrderInfoDto;


}
