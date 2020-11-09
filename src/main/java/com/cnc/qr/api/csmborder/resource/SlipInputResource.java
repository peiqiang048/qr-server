package com.cnc.qr.api.csmborder.resource;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 印刷入力情報.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlipInputResource {

    /**
     * 店舗ID.
     */
    private String storeId;

    /**
     * 注文サマリID.
     */
    private String orderSummaryId;

    /**
     * ユーザ名.
     */
    private String userName;

    /**
     * 注文ID.
     */
    private List<Integer> orderIdList;
}
