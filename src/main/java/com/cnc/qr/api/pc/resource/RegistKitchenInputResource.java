package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.pc.model.KitchenPrintDto;

import java.util.List;

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
public class RegistKitchenInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * キッチンID.
     */
    private String kitchenId;

    /**
     * キッチン名.
     */
    @NotBlank
    private String kitchenName;

    /**
     * キッチンプリンタ.
     */
    @NotNull
    private List<KitchenPrintDto> kitchenPrintList;

}
