package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.ItemDetailOptionTypeDto;
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
public class GetItemDetailOutputResource extends CommonOutputResource {

    /**
     * 商品ID.
     */
    private String itemId;

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品数量.
     */
    private Integer itemCount;

    /**
     * 商品実際注文数量.
     */
    private Integer itemRealCount;

    /**
     * 商品備考.
     */
    private String itemInfo;

    /**
     * 商品オプションリスト.
     */
    private List<ItemDetailOptionTypeDto> optionTypeList;

}
