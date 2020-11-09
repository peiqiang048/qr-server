package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.StoreLanguageDto;
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
public class GetHomePageInfoOutputResource extends CommonOutputResource {

    /**
     * 店舗名.
     */
    private String storeName;

    /**
     * 店舗営業時間.
     */
    private String storeTime;

    /**
     * 店員確認フラグ.
     */
    private String staffCheckFlag;

    /**
     * 店舗言語.
     */
    private List<StoreLanguageDto> languageList;

    /**
     * 未確認の件数.
     */
    private String unCofimCount;

}
