package com.cnc.qr.api.stmborder.resource;

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
public class InitOrderOutputResource extends CommonOutputResource {

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * プリンター.
     */
    private String printInfo;
}
