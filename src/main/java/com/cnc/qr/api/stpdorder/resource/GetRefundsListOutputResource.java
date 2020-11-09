package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.acct.model.GetRefundsListOutputDto;
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
public class GetRefundsListOutputResource extends CommonOutputResource {

    /**
     * 返金一覧.
     */
    private List<GetRefundsListOutputDto> orderList;
}
