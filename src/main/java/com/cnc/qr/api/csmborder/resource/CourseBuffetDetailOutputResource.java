package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.ItemDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
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
public class CourseBuffetDetailOutputResource extends CommonOutputResource {

    /**
     * コース＆放题名称.
     */
    private String courseBuffetName;

    /**
     * コース＆放题金额.
     */
    private BigDecimal courseBuffetPrice;

    /**
     * 税区分.
     */
    private String taxCode;

    /**
     * コース商品紹介.
     */
    private String courseIntroduction;

    /**
     * コース商品.
     */
    private List<ItemDto> courseItemList;

    /**
     * 放題商品紹介.
     */
    private String buffetIntroduction;

    /**
     * 放題商品.
     */
    private List<ItemDto> buffetItemList;
}
