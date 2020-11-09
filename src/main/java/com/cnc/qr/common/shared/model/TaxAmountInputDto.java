package com.cnc.qr.common.shared.model;

import com.cnc.qr.core.order.model.ItemsDto;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * シーケンスNo取得検索条件.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxAmountInputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 店舗ID.
     */
    private String storeId;
    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * 注文ID.
     */
    private Integer orderId;

    /**
     * 税金額区分　0:現金支払、1：電子支払、2：電子支払完了、3:退款.
     */
    private Integer taxAmountType;

    /**
     * 出前区分　1:出前、０：以外.
     */
    private Integer deliveryFlag;

    /**
     * 登録者/更新者.
     */
    private String insUpdOperCd;

    /**
     * 商品リスト.
     */
    private List<ItemsDto> itemList;
    /**
     * テイクアウト.
     */
    private String takeoutFlag;

}
