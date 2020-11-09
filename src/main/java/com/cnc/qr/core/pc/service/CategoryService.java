package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.ChangeCategorySortOrderInputDto;
import com.cnc.qr.core.pc.model.DeleteCategoryInputDto;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryInputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.GetCategoryListInputDto;
import com.cnc.qr.core.pc.model.GetCategoryListOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryOutputDto;
import com.cnc.qr.core.pc.model.RegistCategoryInputDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * カテゴリー情報取得サービス.
 */
public interface CategoryService {

    /**
     * カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリー情報
     */
    GetCategoryListOutputDto getCategory(GetCategoryListInputDto inputDto, Pageable pageable);

    /**
     * カテゴリー明細情報取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリー明細情報
     */
    GetCategoryOutputDto getCategoryInfo(GetCategoryInputDto inputDto);

    /**
     * 親カテゴリーID情報取得.
     *
     * @param inputDto 取得条件
     * @return 親カテゴリーID情報
     */
    List<GetCategoryIdListOutputDto> getParentCategoryIdList(GetCategoryInputDto inputDto);

    /**
     * 親カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 親カテゴリー情報
     */
    List<GetCategoryList> getParentCategoryList(GetCategoryInputDto inputDto);

    /**
     * カテゴリー編集.
     *
     * @param inputDto 取得条件
     */
    void registCategory(RegistCategoryInputDto inputDto);

    /**
     * カテゴリー情報削除.
     *
     * @param inputDto カテゴリー情報
     */
    void deleteCategory(DeleteCategoryInputDto inputDto);

    /**
     * カテゴリー順番編集.
     *
     * @param inputDto 商品情報
     */
    void changeCategorySortOrder(ChangeCategorySortOrderInputDto inputDto);
}
