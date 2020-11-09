package com.cnc.qr.core.order.service;

import com.cnc.qr.core.order.model.CheckOrderItemInputDto;
import com.cnc.qr.core.order.model.CheckOrderItemOutputDto;
import com.cnc.qr.core.order.model.GetBuffetInputDto;
import com.cnc.qr.core.order.model.GetBuffetListInputDto;
import com.cnc.qr.core.order.model.GetBuffetListOutputDto;
import com.cnc.qr.core.order.model.GetBuffetOutputDto;
import com.cnc.qr.core.order.model.GetCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryCategoryInputDto;
import com.cnc.qr.core.order.model.GetDeliveryCategoryListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryItemDto;
import com.cnc.qr.core.order.model.GetDeliveryItemInputDto;
import com.cnc.qr.core.order.model.GetItemBySpeechInputDto;
import com.cnc.qr.core.order.model.GetItemBySpeechOutputDto;
import com.cnc.qr.core.order.model.GetItemCategoryInputDto;
import com.cnc.qr.core.order.model.GetItemDto;
import com.cnc.qr.core.order.model.GetItemInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeInputDto;
import com.cnc.qr.core.order.model.GetItemOptionTypeOutputDto;
import com.cnc.qr.core.order.model.GetItemOutputDto;
import com.cnc.qr.core.order.model.GetTextBySpeechInputDto;
import com.cnc.qr.core.order.model.GetTextBySpeechOutputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 商品情報取得サービス.
 */
public interface ItemService {

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    Page<GetItemDto> getItem(GetItemInputDto inputDto, Pageable pageable);

    /**
     * 商品カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    GetCategoryListOutputDto getItemCategoryList(GetItemCategoryInputDto inputDto);

    /**
     * 商品オプション種類情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品オプション類型情報
     */
    GetItemOptionTypeOutputDto getItemOptionType(GetItemOptionTypeInputDto inputDto);

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    GetItemOutputDto getItemList(GetItemInputDto inputDto);

    /**
     * 放題一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 放題情報
     */
    GetBuffetListOutputDto getBuffetList(GetBuffetListInputDto inputDto);

    /**
     * 放題明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 放題情報
     */
    GetBuffetOutputDto getBuffetInfo(GetBuffetInputDto inputDto);

    /**
     * 放題明細情報取得.
     *
     * @param inputDto 取得条件
     * @return 放題情報
     */
    GetBuffetOutputDto getStorePadBuffetInfo(GetBuffetInputDto inputDto);

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報取得
     */
    Page<GetDeliveryItemDto> getDeliveryItemList(GetDeliveryItemInputDto inputDto,
        Pageable pageable);

    /**
     * 出前商品カテゴリー情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品カテゴリー情報
     */
    GetDeliveryCategoryListOutputDto getDeliveryCategoryList(GetDeliveryCategoryInputDto inputDto);

    /**
     * 音声認識文字列取得.
     *
     * @param inputDto 取得条件
     * @return 文字列情報
     */
    GetTextBySpeechOutputDto getTextBySpeech(GetTextBySpeechInputDto inputDto);

    /**
     * 商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 商品情報
     */
    GetItemBySpeechOutputDto getItemBySpeech(GetItemBySpeechInputDto inputDto);
    
    /**
     * チェック注文商品.
     *
     * @param inputDto 取得条件
     * @return チェック結果
     */
    CheckOrderItemOutputDto checkOrderItem(CheckOrderItemInputDto inputDto);
}
