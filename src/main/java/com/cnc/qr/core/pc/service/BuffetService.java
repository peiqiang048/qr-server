package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.BuffetDelInputDto;
import com.cnc.qr.core.pc.model.BuffetListInputDto;
import com.cnc.qr.core.pc.model.BuffetListOutputDto;
import com.cnc.qr.core.pc.model.ChangeBuffetItemSortOrderInputDto;
import com.cnc.qr.core.pc.model.ChangeBuffetItemStatusInputDto;
import com.cnc.qr.core.pc.model.GetBuffetInputDto;
import com.cnc.qr.core.pc.model.GetBuffetOutputDto;
import com.cnc.qr.core.pc.model.GetCategoryList;
import com.cnc.qr.core.pc.model.ItemListDto;
import com.cnc.qr.core.pc.model.RegistBuffetInputDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * 飲み放題サービス.
 */
public interface BuffetService {

    /**
     * 飲み放題一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    BuffetListOutputDto getBuffetList(BuffetListInputDto inputDto, Pageable pageable);

    /**
     * 飲み放題情報取得.
     *
     * @param inputDto 取得条件
     */
    GetBuffetOutputDto getBuffet(GetBuffetInputDto inputDto);

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品リスト情報
     */
    List<ItemListDto> getSelectedItemList(GetBuffetInputDto inputDto);

    /**
     * 商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 商品リスト情報
     */
    List<ItemListDto> getItemList(GetBuffetInputDto inputDto);

    /**
     * カテゴリーリスト取得.
     *
     * @param inputDto 取得条件
     * @return カテゴリーリスト情報
     */
    List<GetCategoryList> getHasItemCategoryList(GetBuffetInputDto inputDto);

    /**
     * 飲み放題保存.
     *
     * @param inputDto 取得条件
     */
    void saveBuffet(RegistBuffetInputDto inputDto);

    /**
     * 飲み放題削除.
     *
     * @param inputDto 取得条件
     */
    void delBuffet(BuffetDelInputDto inputDto);

    /**
     * 飲み放題商品ステータス更新.
     *
     * @param inputDto 商品情報
     */
    void changeBuffetItemStatus(ChangeBuffetItemStatusInputDto inputDto);

    /**
     * 飲み放題商品順番管理.
     *
     * @param inputDto 商品情報
     */
    void changeBuffetItemSortOrder(ChangeBuffetItemSortOrderInputDto inputDto);

}
