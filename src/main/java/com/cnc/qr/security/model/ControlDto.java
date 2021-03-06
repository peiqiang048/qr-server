package com.cnc.qr.security.model;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * コントロール条件取得Dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ControlDto {

    /**
     * 予約機能表示標識.
     */
    private String reservationDisplayFlag;

    /**
     * 出前機能表示標識.
     */
    private String deliveryDisplayFlag;

    /**
     * 前後支払標識.
     */
    private String beforeAfterPaymentFlag;

    /**
     * 客用スマホ使用可能標識.
     */
    private String smartPhonesAvailableFlag;

    /**
     * 音声注文使用可能標識.
     */
    private String voiceOrderAvailableFlag;

}
