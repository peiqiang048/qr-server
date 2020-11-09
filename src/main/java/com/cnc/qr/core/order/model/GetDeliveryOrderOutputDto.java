package com.cnc.qr.core.order.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 注文編集取得検索結果.
 */
@Data
public class GetDeliveryOrderOutputDto implements Serializable {

    /**
     * シリアルバージョンID.
     */
    private static final long serialVersionUID = -1L;
    
    /**
     * 出前時間リスト.
     */
    private List<DeliveryTimeListDto> deliveryTimeList;

    /**
     * 都道府県リスト.
     */
    private List<PrefectureListDto> prefectureList;
    
    /**
     * 市区町村リスト.
     */
    private List<CityListDto> cityList;
    
    /**
     * 町域番地リスト.
     */
    private List<BlockListDto> blockList;
    
    /**
     * 支付方式リスト.
     */
    private List<PaymentTypeListDto> paymentTypeList;
}
