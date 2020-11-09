package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.pc.model.DeliveryFlagListDto;
import com.cnc.qr.core.pc.model.DeliveryTypeFlagListDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetKitchenListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeDto;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
import com.cnc.qr.core.pc.model.ItemTypeDto;
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
public class GetItemOutputResource extends CommonOutputResource {

    /**
     * 商品名.
     */
    private String itemName;

    /**
     * 商品説明.
     */
    private String itemInfo;

    /**
     * 商品単位ID.
     */
    private Integer itemUnitId;

    /**
     * カテゴリーIDリスト.
     */
    private List<GetCategoryIdListOutputDto> categoryIdList;

    /**
     * 商品単価.
     */
    private String itemPrice;

    /**
     * 税区分.
     */
    private Integer taxId;

    /**
     * キチンID.
     */
    private String kitchenId;

    /**
     * 商品大きな画像URL.
     */
    private String itemLargePicUrl;

    /**
     * 商品サムネイルURL.
     */
    private String itemSmallPicUrl;

    /**
     * 商品区分.
     */
    private String itemType;

    /**
     * 単位リスト.
     */
    private List<GetUnitListOutputDto> unitList;

    /**
     * カテゴリーリスト.
     */
    private List<GetCategoryList> categoryList;

    /**
     * 税リスト.
     */
    private List<GetTaxListOutputDto> taxList;

    /**
     * キチンリスト.
     */
    private List<GetKitchenListOutputDto> kitchenList;

    /**
     * オプションリスト.
     */
    private List<GetOptionTypeDto> optionTypeList;

    /**
     * 商品区分リスト.
     */
    private List<ItemTypeDto> itemTypeList;

    /**
     * 出前区分リスト.
     */
    private List<DeliveryFlagListDto> deliveryFlagList;

    /**
     * 出前仕方リスト.
     */
    private List<DeliveryTypeFlagListDto> deliveryTypeFlagList;

    /**
     * 出前区分.
     */
    private String deliveryFlag;

    /**
     * 出前仕方.
     */
    private String cateringTypeFlag;

    /**
     * 出前変更フラグ.
     */
    private String deliveryChangeFlag;

    /**
     * 商品ビデオURL.
     */
    private String itemVideo;
}
