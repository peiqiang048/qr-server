package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.core.order.model.ChangePrintStatusDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePrintStatusInputResource {

    /**
     * 印刷注文リスト.
     */
    @NotNull
    private List<@Valid ChangePrintStatusDto> orderList;
}
