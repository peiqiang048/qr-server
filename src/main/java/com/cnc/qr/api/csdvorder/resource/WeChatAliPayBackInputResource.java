package com.cnc.qr.api.csdvorder.resource;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatAliPayBackInputResource {

    /**
     * 注文Number.
     */
    @NotBlank
    private String orderNum;

    /**
     * 支払い結果コード.
     */
    @NotBlank
    private String respCode;

    /**
     * 備考.
     */
    @NotBlank
    private String attach;

    /**
     * 金額.
     */
    @NotBlank
    private String transAmt;

    /**
     * 支払い方式.
     */
    @NotBlank
    private String paymentBrand;
}
