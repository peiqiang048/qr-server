package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RItemOption;
import com.cnc.qr.core.pc.model.GetCategoryIdListOutputDto;
import com.cnc.qr.core.pc.model.GetItemOptionDto;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品オプション関連テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RItemOptionRepository extends JpaRepository<RItemOption, Long> {

    /**
     * 商品オプション情報取得.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     * @return オプション情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetItemOptionDto(" +
        "t1.optionCode, " +
        "t1.optionTypeCode, " +
        "t1.diffPrice) " +
        "from RItemOption t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId = :itemId ")
    List<GetItemOptionDto> getCheckOption(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId);

    /**
     * 商品オプション関連情報削除.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     */
    @Modifying
    @Query(value = "delete from r_item_option "
        + "where store_id = :storeId "
        + "and item_id = :itemId", nativeQuery = true)
    void deleteItemOption(@Param("storeId") String storeId, @Param("itemId") Integer itemId);

    /**
     * 指定商品ID削除.
     *
     * @param storeId    店舗ID
     * @param itemIdList 商品IDリスト
     */
    @Modifying
    @Query(value = "delete from r_item_option "
        + "where store_id = :storeId "
        + "and item_id in :itemIdList", nativeQuery = true)
    void deleteByItemIdList(@Param("storeId") String storeId,
        @Param("itemIdList") List<Integer> itemIdList);

    /**
     * 指定オプション種類ID削除.
     *
     * @param storeId          店舗ID
     * @param optionTypeCdList オプション種類CDリスト
     */
    @Modifying
    @Query(value = "delete from r_item_option "
        + "where store_id = :storeId "
        + "and option_type_code in :optionTypeCdList", nativeQuery = true)
    void deleteByOptionTypeList(@Param("storeId") String storeId,
        @Param("optionTypeCdList") List<String> optionTypeCdList);

    /**
     * 商品ID取得.
     *
     * @param storeId      店舗ID
     * @param optionCdList オプションコードリスト
     * @return 商品IDリスト
     */
    @Query(value = "SELECT distinct item_id " +
        "from r_item_option t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 " +
        "AND t1.option_code in :optionCdList ", nativeQuery = true)
    List<Integer> getItemList(@Param("storeId") String storeId,
        @Param("optionCdList") List<String> optionCdList);

    /**
     * 商品ID取得.
     *
     * @param storeId 店舗ID
     * @return 商品IDリスト
     */
    @Query(value = "SELECT distinct item_id " +
        "from r_item_option t1 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Integer> gethasOptionItemList(@Param("storeId") String storeId);

    /**
     * 指定オプション種類ID削除.
     *
     * @param storeId      店舗ID
     * @param optionCdList オプションコードリスト
     */
    @Modifying
    @Query(value = "delete from r_item_option "
        + "where store_id = :storeId "
        + "and option_code in :optionCdList", nativeQuery = true)
    void deleteItemOptionCode(@Param("storeId") String storeId,
        @Param("optionCdList") List<String> optionCdList);
}
