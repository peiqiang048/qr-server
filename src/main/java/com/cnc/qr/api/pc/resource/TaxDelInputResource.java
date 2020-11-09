package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.TaxInputDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 税設定削除インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDelInputResource {

    /**
            *ビジネスID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String businessId;

    /**
            * 税IDリスト.
     */
    @NotNull
    private List<@Valid TaxInputDto> taxIdList;
}
