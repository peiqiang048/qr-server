package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.BlockListDto;
import com.cnc.qr.core.order.model.CityListDto;
import com.cnc.qr.core.order.model.DeliveryTimeListDto;
import com.cnc.qr.core.order.model.PaymentTypeListDto;
import com.cnc.qr.core.order.model.PrefectureListDto;
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
public class GetDeliveryOrderOutputResource extends CommonOutputResource {

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
