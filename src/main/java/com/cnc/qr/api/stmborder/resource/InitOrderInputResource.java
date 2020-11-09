package com.cnc.qr.api.stmborder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.BuffetInfoDto;
import com.cnc.qr.core.order.model.CourseInfoDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
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
public class InitOrderInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 人数.
     */
    private Integer customerCount;

    /**
     * 就餐区分.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.NUMERIC_VALIDATION)
    private String takeoutFlag;

    /**
     * 桌号.
     */
    private Integer tableId;

    /**
     * 放題ID.
     */
    private List<BuffetInfoDto> buffetId;

    /**
     * コースID.
     */
    private List<CourseInfoDto> courseId;

}
