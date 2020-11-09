package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserPasswordInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * ユーザーID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String userId;

    /**
     * パスワード.
     */
    @NotBlank
    private String oldPassword;

    /**
     * 新パスワード.
     */
    @NotBlank
    @Size(min = 6, max = 10)
    private String newPassword;

    /**
     * 確認パスワード.
     */
    @NotBlank
    @Size(min = 6, max = 10)
    private String surePassword;
}
