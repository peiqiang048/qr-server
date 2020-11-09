package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.AreaListDto;
import com.cnc.qr.core.pc.model.BlockListDto;
import com.cnc.qr.core.pc.model.CateringPriceTypeListDto;
import com.cnc.qr.core.pc.model.CityListDto;
import com.cnc.qr.core.pc.model.DeliveryFlagListDto;
import com.cnc.qr.core.pc.model.DeliveryTypeFlagListDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingInputDto;
import com.cnc.qr.core.pc.model.GetDeliverySettingOutputDto;
import com.cnc.qr.core.pc.model.PrefectureListDto;
import com.cnc.qr.core.pc.model.RegistDelivertSettingInputDto;
import java.util.List;

//import java.util.List;
//import org.springframework.data.domain.Pageable;
//
//import com.cnc.qr.core.pc.model.ChangeCourseItemSortOrderInputDto;
//import com.cnc.qr.core.pc.model.ChangeCourseItemStatusInputDto;
//import com.cnc.qr.core.pc.model.ChangeItemSortOrderInputDto;
//import com.cnc.qr.core.pc.model.ChangeItemStatusInputDto;
//import com.cnc.qr.core.pc.model.CourseDelInputDto;
//import com.cnc.qr.core.pc.model.DeleteItemInputDto;
//import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
//import com.cnc.qr.core.pc.model.GetCategoryItemInputDto;
//import com.cnc.qr.core.pc.model.GetCategoryItemOutputDto;
//import com.cnc.qr.core.pc.model.GetCategoryList;
//import com.cnc.qr.core.pc.model.GetCateringTypeFlagListOutputDto;
//import com.cnc.qr.core.pc.model.GetCourseInputDto;
//import com.cnc.qr.core.pc.model.GetCourseListInputDto;
//import com.cnc.qr.core.pc.model.GetCourseListOutputDto;
//import com.cnc.qr.core.pc.model.GetCourseOutputDto;
//import com.cnc.qr.core.pc.model.GetDeliveryFlagListOutputDto;
//import com.cnc.qr.core.pc.model.GetItemCategoryInputDto;
//import com.cnc.qr.core.pc.model.GetItemCategoryOutputDto;
//import com.cnc.qr.core.pc.model.GetItemInputDto;
//import com.cnc.qr.core.pc.model.GetItemListInputDto;
//import com.cnc.qr.core.pc.model.GetItemListOutputDto;
//import com.cnc.qr.core.pc.model.GetItemOutputDto;
//import com.cnc.qr.core.pc.model.GetKitchenListOutputDto;
//import com.cnc.qr.core.pc.model.GetOptionTypeDto;
//import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
//import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
//import com.cnc.qr.core.pc.model.ItemListDto;
//import com.cnc.qr.core.pc.model.RegistCourseInputDto;
//import com.cnc.qr.core.pc.model.RegistItemInputDto;

/**
 * 出前設定取得サービス.
 */
public interface DeliveryService {

    /**
     * 出前設定取得.
     *
     * @param inputDto 取得条件
     * @return 出前設定
     */
    GetDeliverySettingOutputDto getDeliverySetting(GetDeliverySettingInputDto inputDto);

    /**
     * 都道府県リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 都道府県情報
     */
    List<PrefectureListDto> getPrefectureList(GetDeliverySettingInputDto inputDto);

    /**
     * 市区町村リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 都道府県情報
     */
    List<CityListDto> getCityList(GetDeliverySettingInputDto inputDto);

    /**
     * 町域番地リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 都道府県情報
     */
    List<BlockListDto> getBlockList(GetDeliverySettingInputDto inputDto);

    /**
     * エリアリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return エリア情報
     */
    List<AreaListDto> getAreaList(GetDeliverySettingInputDto inputDto);

    /**
     * 出前区分リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 出前区分リスト情報
     */
    List<DeliveryFlagListDto> getDeliveryFlagList(GetDeliverySettingInputDto inputDto);

    /**
     * 出前仕方リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 出前仕方リスト情報
     */
    List<DeliveryTypeFlagListDto> getDeliveryTypeFlagList(GetDeliverySettingInputDto inputDto);

    /**
     * 配達料区分リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 配達料区分リスト情報
     */
    List<CateringPriceTypeListDto> getCateringPriceTypeList(GetDeliverySettingInputDto inputDto);

    /**
     * 出前設定情報更新.
     *
     * @param inputDto 取得条件
     */
    void registDelivertSetting(RegistDelivertSettingInputDto inputDto);

}
