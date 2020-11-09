package com.cnc.qr.api.csmborder.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アウトプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SbPaymentCallBackOutputResource {

    /**
     * 処理結果ステータス.
     */
    @JsonProperty("res_result")
    private String resResult;

    /**
     * メッセージ.
     */
    private String message;

}
