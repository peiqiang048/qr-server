package com.cnc.qr.api.pc.resource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * インプットリソース.
 */
@Data
public class ChangeStoreUserAuth {


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
     * ログイン状態延長フラグ.
     */
    private Boolean rememberMe;
}
