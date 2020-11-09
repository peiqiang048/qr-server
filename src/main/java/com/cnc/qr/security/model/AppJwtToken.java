package com.cnc.qr.security.model;

import com.cnc.qr.common.model.CommonOutputResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * JWT認証で本体として返すオブジェクト.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppJwtToken extends CommonOutputResource {

    /**
     * 認証トークン.
     */
    private String accessToken;

    /**
     * 受付ID.
     */
    private String receivablesId;

    /**
     * 受付番号.
     */
    private String receivablesNo;

}
