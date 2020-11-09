package com.cnc.qr.api.stpdorder.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.cnc.qr.core.order.model.BlockListDto;
import com.cnc.qr.core.order.model.CateringTimeDto;
import com.cnc.qr.core.order.model.CityListDto;
import com.cnc.qr.core.order.model.DeliveryStatusDto;
import com.cnc.qr.core.order.model.DeliveryTypeFlagDto;
import com.cnc.qr.core.order.model.PrefectureListDto;
import com.cnc.qr.core.order.model.TakeoutTimeDto;
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
public class GetDeliveryOrderEditInfoOutputResource extends CommonOutputResource {

    /**
     * 氏名.
     */
    private String customerName;

    /**
     * 電話番号.
     */
    private String telNumber;

    /**
     * 出前時間.
     */
    private String deliveryTime;

    /**
     * メール.
     */
    private String mailAddress;

    /**
     * 都道府県ID.
     */
    private String prefectureId;

    /**
     * 市区町村ID.
     */
    private String cityId;

    /**
     * 町域番地ID.
     */
    private String blockId;

    /**
     * 住所.
     */
    private String other;

    /**
     * 状態.
     */
    private String status;

    /**
     * 出前仕方.
     */
    private String deliveryTypeFlag;

    /**
     * 要望.
     */
    private String comment;

    /**
     * 配達時間リスト.
     */
    private List<CateringTimeDto> cateringTimeList;

    /**
     * 持帰り時間リスト.
     */
    private List<TakeoutTimeDto> takeoutTimeList;

    /**
     * 出前仕方リスト.
     */
    private List<DeliveryTypeFlagDto> deliveryTypeFlagList;

    /**
     * 状態リスト.
     */
    private List<DeliveryStatusDto> statusList;

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
}
