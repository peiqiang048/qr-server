package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.KitchenPrintDto;
import com.cnc.qr.core.pc.model.PrintInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class GetKitchenOutputResource extends CommonOutputResource {

    /**
     * キッチン名.
     */
    private String kitchenName;

    /**
     * キッチンプリンタリスト.
     */
    private List<KitchenPrintDto> kitchenPrintList;

    /**
     * プリンタ.
     */
    private List<PrintInfoDto> printList;
}
