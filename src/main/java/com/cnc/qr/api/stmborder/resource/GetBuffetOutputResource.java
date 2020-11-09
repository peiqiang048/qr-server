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
public class GetBuffetOutputResource extends CommonOutputResource {

    /**
     * 放題ID.
     */
    private String buffetId;
    
    /**
     * 放題名称.
     */
    private String buffetName;
    
    /**
     * 放題金额.
     */
    private String buffetAmount;
    
    /**
     * 放題份数.
     */
    private String buffetCount;
    
    /**
     * 剩余时间.
     */
    private String buffetRemainTime;
}
