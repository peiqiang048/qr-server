package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.DeliveryCategoryListDto;
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
public class GetItemCategoryOutputResource extends CommonOutputResource {

    /**
     * 商品カテゴリー情報.
     */
    private List<DeliveryCategoryListDto> itemCategoryList;
}
