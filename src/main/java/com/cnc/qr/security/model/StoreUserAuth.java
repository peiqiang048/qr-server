package com.cnc.qr.security.model;

import com.cnc.qr.common.constants.RegexConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * インプットリソース.
 */
@Data
public class StoreUserAuth {

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * ユーザID.
     */
    @NotBlank
    @Size(min = 1, max = 50)
    private String userId;

    /**
     * パスワード.
     */
    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

    /**
     * パスワード.
     */
    private String businessId;

    /**
     * ログイン状態延長フラグ.
     */
    private Boolean rememberMe;
}
