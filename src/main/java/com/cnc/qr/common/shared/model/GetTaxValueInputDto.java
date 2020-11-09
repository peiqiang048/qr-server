package com.cnc.qr.common.shared.model;

import com.cnc.qr.core.order.model.ItemsDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTaxValueInputDto {

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 就餐区分.
     */
    private String takeoutFlag;

    /**
     * 商品.
     */
    private List<ItemsDto> itemList;

}
