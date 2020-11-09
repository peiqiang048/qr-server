package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.ChangeCourseItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeCourseItemStatusInputDto;
import com.cnc.qr.core.pc.model.ChangeItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeItemStatusInputDto;
import com.cnc.qr.core.pc.model.CourseDelInputDto;
import com.cnc.qr.core.pc.model.DeleteItemInputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemInputDto;
import com.cnc.qr.core.pc.model.GetCategoryItemOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCateringTypeFlagListOutputDto;
import com.cnc.qr.core.pc.model.GetCourseInputDto;
import com.cnc.qr.core.pc.model.GetCourseListInputDto;
import com.cnc.qr.core.pc.model.GetCourseListOutputDto;
import com.cnc.qr.core.pc.model.GetCourseOutputDto;
import com.cnc.qr.core.pc.model.GetDeliveryFlagListOutputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryInputDto;
import com.cnc.qr.core.pc.model.GetItemCategoryOutputDto;
import com.cnc.qr.core.pc.model.GetItemInputDto;
import com.cnc.qr.core.pc.model.GetItemListInputDto;
import com.cnc.qr.core.pc.model.GetItemListOutputDto;
import com.cnc.qr.core.pc.model.GetItemOutputDto;
import com.cnc.qr.core.pc.model.GetKitchenListOutputDto;
import com.cnc.qr.core.pc.model.GetOptionTypeDto;
import com.cnc.qr.core.pc.model.GetTaxListOutputDto;
import com.cnc.qr.core.pc.model.GetUnitListOutputDto;
import com.cnc.qr.core.pc.model.ItemListDto;
import com.cnc.qr.core.pc.model.RegistCourseInputDto;
import com.cnc.qr.core.pc.model.RegistItemInputDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * 商品情報取得サービス.
 */
public interface PcItemService {

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    GetItemListOutputDto getItem(GetItemListInputDto inputDto, Pageable pageable);

    /**
     * 商品明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    GetItemOutputDto getItemInfo(GetItemInputDto inputDto);

    /**
     * 商品カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetCategoryIdListOutputDto> getItemCategory(GetItemInputDto inputDto);

    /**
     * 単位リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetUnitListOutputDto> getUnitList(GetItemInputDto inputDto);

    /**
     * カテゴリーリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetCategoryList> getCategoryList(GetItemInputDto inputDto);

    /**
     * 税リスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetTaxListOutputDto> getTaxList(GetItemInputDto inputDto);

    /**
     * キチンリスト情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetKitchenListOutputDto> getKitchenList(GetItemInputDto inputDto);

    /**
     * オプション情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetOptionTypeDto> getOptionTypeList(GetItemInputDto inputDto);

    /**
     * 商品編集.
     *
     * @param inputDto 取得条件
     */
    void registItem(RegistItemInputDto inputDto);

    /**
     * 商品情報削除.
     *
     * @param inputDto 商品情報
     */
    void deleteItem(DeleteItemInputDto inputDto);

    /**
     * 商品ステータス更新.
     *
     * @param inputDto 商品情報
     */
    void changeItemStatus(ChangeItemStatusInputDto inputDto);

    /**
     * 商品カテゴリーリスト情報取得.
     *
     * @param inputDto カテゴリー情報
     */
    List<GetItemCategoryOutputDto> getItemCategoryList(GetItemCategoryInputDto inputDto);

    /**
     * カテゴリーの下に商品リスト情報取得.
     *
     * @param inputDto 商品情報
     */
    List<GetCategoryItemOutputDto> getCategoryItemList(GetCategoryItemInputDto inputDto);

    /**
     * 商品編集.
     *
     * @param inputDto 商品情報
     */
    void changeItemSortOrder(ChangeItemSortOrderInputDto inputDto);

    /**
     * コース情報取得.
     *
     * @param inputDto 取得条件
     * @return コース情報
     */
    GetCourseListOutputDto getCourseList(GetCourseListInputDto inputDto, Pageable pageable);

    /**
     * コース明細情報取得.
     *
     * @param inputDto 取得条件
     * @return コース情報
     */
    GetCourseOutputDto getCourseInfo(GetCourseInputDto inputDto);

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品リスト情報
     */
    List<ItemListDto> getSelectedItemList(GetCourseInputDto inputDto);

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品リスト情報
     */
    List<ItemListDto> getItemList(GetCourseInputDto inputDto);

    /**
     * カテゴリーリスト取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリーリスト情報
     */
    List<GetCategoryList> getHasItemCategoryList(GetCourseInputDto inputDto);

    /**
     * コース保存.
     *
     * @param inputDto 取得条件
     */
    void saveCourse(RegistCourseInputDto inputDto);

    /**
     * コース削除.
     *
     * @param inputDto 取得条件
     */
    void delCourse(CourseDelInputDto inputDto);

    /**
     * コース商品ステータス更新.
     *
     * @param inputDto 商品情報
     */
    void changeCourseItemStatus(ChangeCourseItemStatusInputDto inputDto);

    /**
     * コース商品順番管理.
     *
     * @param inputDto 商品情報
     */
    void changeCourseItemSortOrder(ChangeCourseItemSortOrderInputDto inputDto);

    /**
     * 出前区分リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetDeliveryFlagListOutputDto> getDeliveryFlagList(GetItemInputDto inputDto);

    /**
     * 出前仕方リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    List<GetCateringTypeFlagListOutputDto> getCateringTypeFlagList(GetItemInputDto inputDto);

}
