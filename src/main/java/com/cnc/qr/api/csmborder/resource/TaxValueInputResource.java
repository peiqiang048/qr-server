package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.ItemsDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxValueInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 就餐区分.
     */
    @NotBlank
    private String takeoutFlag;

    /**
     * 商品.
     */
    private List<ItemsDto> items;

}
