package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.PrintBrandDto;
import com.cnc.qr.core.pc.model.PrintConnectionMethodDto;
import com.cnc.qr.core.pc.model.PrintSizeDto;
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
public class GetPrintOutputResource extends CommonOutputResource {

    /**
     * プリンター名.
     */
    private String printName;

    /**
     * プリンタブランドCD.
     */
    private String brandCode;

    /**
     * 接続方法CD.
     */
    private String connectionMethodCode;

    /**
     * プリンターIP.
     */
    private String printIp;

    /**
     * ブルートゥース名.
     */
    private String blueToothName;

    /**
     * モデル.
     */
    private String printModel;

    /**
     * チケット幅CD.
     */
    private String printSizeCode;

    /**
     * ブランドリスト.
     */
    private List<PrintBrandDto> brandList;

    /**
     * 接続方法リスト.
     */
    private List<PrintConnectionMethodDto> connectionMethodList;

    /**
     * チケット幅リスト.
     */
    private List<PrintSizeDto> printSizeList;
}
