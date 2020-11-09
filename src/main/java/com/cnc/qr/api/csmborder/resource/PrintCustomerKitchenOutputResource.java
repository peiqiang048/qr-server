package com.cnc.qr.api.csmborder.resource;

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
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class PrintCustomerKitchenOutputResource extends CommonOutputResource {


    /**
     * 厨房アウトプット.
     */
    private KitchenOutputDto kitchenOutputDto;

    /**
     * 客用アウトプット.
     */
    private CustomerOrderInfoDto customerOrderInfoDto;

}
