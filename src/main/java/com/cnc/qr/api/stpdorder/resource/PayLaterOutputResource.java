package com.cnc.qr.api.stpdorder.resource;

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
public class PayLaterOutputResource extends CommonOutputResource {

    /**
     * 支払結果.
     */
    private String respCode;

    /**
     * 取消結果.
     */
    private String cancelRespCode;

    /**
     * 印刷情報.
     */
    private String printInfo;
}
