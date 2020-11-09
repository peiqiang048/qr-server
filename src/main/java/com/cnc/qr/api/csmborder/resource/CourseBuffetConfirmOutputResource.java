package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
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
public class CourseBuffetConfirmOutputResource extends CommonOutputResource {

    /**
     * 小計.
     */
    private String subtotalPrice;
    
    /**
     * 外税.
     */
    private String foreignTax;
    
    /**
     * 合計.
     */
    private String totalPrice;
}
