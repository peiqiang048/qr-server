package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.GetItemBySpeechDto;
import com.cnc.qr.core.order.model.GetItemDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
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
public class GetItemBySpeechOutputResource extends CommonOutputResource {

    /**
     * 商品リスト.
     */
    private List<GetItemBySpeechDto> items;

    /**
     * 認識結果.
     */
    private String transcript;
}
