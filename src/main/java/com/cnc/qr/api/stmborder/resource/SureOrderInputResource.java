package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.UnCfmItemInfoDto;
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
public class SureOrderInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String receivablesId;

    /**
     * 確認注文IDリスト.
     */
    @NotNull
    private List<@NotBlank @Pattern(regexp = RegexConstants.NUMERIC_VALIDATION) String> orderIdList;

    /**
     * 変更後の商品数.
     */
    private List<UnCfmItemInfoDto> itemList;

    /**
     * 小計.
     */
    @NotNull
    private String unconfirmedAmount;

}
