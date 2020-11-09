package com.cnc.qr.api.csdvorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.PaymentMethodDto;
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
public class GetStorePaymentInfoOutputResource extends CommonOutputResource {

    /**
     * 前後支払区分 .
     */
    private String paymentType;

    /**
     * 支払方式リスト.
     */
    private List<PaymentMethodDto> paymentMethodList;

}
