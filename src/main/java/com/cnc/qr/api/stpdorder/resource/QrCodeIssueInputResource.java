package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.BuffetInfoDto;
import com.cnc.qr.core.order.model.CourseInfoDto;
import com.cnc.qr.core.order.model.TableIdDto;
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
public class QrCodeIssueInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 人数.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String customerCount;

    /**
     * テーブルID.
     */
    private List<TableIdDto> tableId;

    /**
     * 放題ID.
     */
    private List<BuffetInfoDto> buffetId;

    /**
     * コースID.
     */
    private List<CourseInfoDto> courseId;

}
