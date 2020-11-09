package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.ItemIdDto;
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
public class ChangeItemStatusInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * ユーザーIDリスト.
     */
    @NotNull
    private List<@Valid ItemIdDto> itemList;

    /**
     * 区分.
     */
    @NotNull
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String salesClassification;
}
