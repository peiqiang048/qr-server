package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.constants.RegexConstants;
import com.cnc.qr.core.order.model.SelectBuffetDto;
import com.cnc.qr.core.order.model.SelectCourseDto;
import com.cnc.qr.core.order.model.SelectTableDto;
import java.util.List;
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
public class RegistReservateInputResource {

    /**
     * 店舗ID.
     */
    @NotBlank
    @Pattern(regexp = RegexConstants.ALPHANUMERIC_VALIDATION)
    private String storeId;

    /**
     * 予約ID.
     */
    private Integer reservateId;

    /**
     * 来店日時.
     */
    private String reservateTime;

    /**
     * 利用時間.
     */
    @NotBlank
    private String useTime;

    /**
     * 顧客名前.
     */
    @NotBlank
    private String customerName;

    /**
     * 電話番号.
     */
    @NotBlank
    private String telNumber;

    /**
     * 顧客人数.
     */
    @NotNull
    private Integer customerCount;

    /**
     * 予約区分.
     */
    @NotBlank
    private String reservateType;

    /**
     * コース選択リスト.
     */
    private List<SelectCourseDto> selectCourseList;

    /**
     * 放題選択リスト.
     */
    private List<SelectBuffetDto> selectBuffetList;

    /**
     * 席選択リスト.
     */
    private List<SelectTableDto> selectTableList;

    /**
     * 要望.
     */
    private String comment;

}
