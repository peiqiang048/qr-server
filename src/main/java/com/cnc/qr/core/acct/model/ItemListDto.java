package com.cnc.qr.core.acct.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品情報取得検索結果.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemListDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * 注文ID.
     */
    @NotNull
    private Integer orderId;

    /**
     * 注文明細ID.
     */
    @NotNull
    private Integer orderDetailId;

    /**
     * 商品ID.
     */
    @NotNull
    private Integer itemId;

    /**
     * 商品数量.
     */
    @NotNull
    private Integer itemCount;

    /**
     * 商品金額.
     */
    @NotNull
    private BigDecimal itemPrice;
}
