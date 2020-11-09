package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.ReservateIdDto;
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
public class CancelReservateInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 予約IDリスト.
     */
    @NotNull
    private List<@Valid ReservateIdDto> reservateIdList;

}
