package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.RoleIdDto;
import com.cnc.qr.core.pc.model.UserIdDto;
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
public class ChangeRoleStatusInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 役割IDリスト.
     */
    @NotNull
    private List<@Valid RoleIdDto> roleList;

    /**
     * 区分.
     */
    @NotNull
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String classification;
}
