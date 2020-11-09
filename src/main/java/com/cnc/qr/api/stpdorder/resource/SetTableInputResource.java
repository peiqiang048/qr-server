package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.TableIdDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class SetTableInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付ID.
     */

    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String receivablesId;

    /**
     * 桌号.
     */
    @NotNull
    private List<@Valid TableIdDto> tableId;
}
