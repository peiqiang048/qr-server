package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.RegisterRoleInfoDto;
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
public class RegisterUserInputResource {

    /**
     * ビジネスID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String businessId;

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * ユーザID.
     */
    private String userId;

    /**
     * ログインID.
     */
    @NotBlank
    private String loginId;

    /**
     * ユーザ名.
     */
    @NotBlank
    private String userName;

    /**
     * 区分.
     */
    @NotBlank
    private String classification;

    /**
     * 選択した役割.
     */
    @NotNull
    private List<@Valid RegisterRoleInfoDto> checkedRoleList;
}
