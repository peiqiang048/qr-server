package com.cnc.qr.api.stpdorder.resource;

import javax.validation.constraints.Email;
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
public class ResendMailInputResource {

    /**
     * 受付ID.
     */
    @NotBlank
    private String receivablesId;

    /**
     * 氏名.
     */
    @NotBlank
    private String customerName;

    /**
     * メール.
     */
    @NotBlank
    @Email
    private String mailAddress;

}
