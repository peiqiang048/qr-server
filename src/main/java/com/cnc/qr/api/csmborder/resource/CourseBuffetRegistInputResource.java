package com.cnc.qr.api.csmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.OrderBuffetCourseDto;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * インプットリソース.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseBuffetRegistInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 受付ID.
     */
    @NotBlank
    private String receivablesId;

    /**
     * テーブルID.
     */
    private String tableId;

    /**
     * 商品リスト.
     */
    @NotNull
    private List<@Valid OrderBuffetCourseDto> itemList;

    /**
     * 注文額.
     */
    @NotBlank
    private String orderAmount;
}
