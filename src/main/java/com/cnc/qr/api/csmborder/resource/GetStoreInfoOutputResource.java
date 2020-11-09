package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.GetStoreInfoOutputDto;
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
public class GetStoreInfoOutputResource extends CommonOutputResource {

    /**
     * 店舗情報.
     */
    private GetStoreInfoOutputDto storeInfo;

    /**
     * 音声注文使用可能標識.
     */
    private String voiceOrderAvailableFlag;

}
