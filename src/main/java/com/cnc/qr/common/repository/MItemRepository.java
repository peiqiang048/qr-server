package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MItem;
import com.cnc.qr.common.shared.model.TaxInfoDto;
import com.cnc.qr.core.order.model.BuffetCourseItemDto;
import com.cnc.qr.core.order.model.BuffetListDto;
import com.cnc.qr.core.pc.model.GetBuffetOutputDto;
import com.cnc.qr.core.pc.model.GetCourseOutputDto;
import com.cnc.qr.core.pc.model.GetItemOutputDto;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MItemRepository extends JpaRepository<MItem, Long> {

    /**
     * 商品情報取得.
     *
     * @param storeId      店舗ID
     * @param categoryId   カテゴリーID
     * @param languages    言語
     * @param searchInfo   検索内容
     * @param sellOffStart 売り切り開始日時
     * @param pageable     ページ情報
     * @param idList       商品区分リスト
     * @param status       商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT t1.store_id, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t1.status = :status " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.del_flag = 0 " +
        "ORDER BY t5.sort_order ASC", nativeQuery = true)
    Page<Map<String, Object>> getItem(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, @Param("sellOffStart") ZonedDateTime sellOffStart,
        Pageable pageable, @Param("idList") List<String> idList, @Param("status") String status);

    /**
     * 商品（放題含み）情報取得.
     *
     * @param storeId      店舗ID
     * @param categoryId   カテゴリーID
     * @param languages    言語
     * @param searchInfo   検索内容
     * @param sellOffStart 売り切り開始日時
     * @param buffetId     放題商品ID
     * @param pageable     ページ情報
     * @param idList       商品区分リスト
     * @param status       商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.imagePath, " +
        "d.shortImagePath, " +
        "d.videoPath, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'1' AS buffetFlag " +
        "FROM m_item t7 " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t7.store_id " +
        "AND t6.buffet_id = t7.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t7.item_type = '04' " +
        "AND t7.item_id = :buffetId " +
        "AND t1.del_flag = 0 " +
        "UNION " +
        "SELECT t1.store_id AS storeId, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* FROM r_buffet_item t6 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t6.buffet_id = :buffetId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId " +
        "AND tt1.status = :status ORDER BY d.buffetFlag DESC ", nativeQuery = true)
    Page<Map<String, Object>> getItemBuffetAndNormal(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, @Param("sellOffStart") ZonedDateTime sellOffStart,
        @Param("buffetId") Integer buffetId, @Param("idList") List<String> idList,
        Pageable pageable, @Param("status") String status);

    /**
     * 受付で放題商品情報件数取得.
     *
     * @param storeId       店舗ID
     * @param languages     言語
     * @param searchInfo    検索内容
     * @param receivablesId 受付ID
     * @return 商品情報
     */
    @Query(value = "SELECT COUNT(t1) " +
        "FROM o_order_summary t7 " +
        "INNER JOIN o_order t8 " +
        "ON t8.store_id = t7.store_id " +
        "AND t8.order_summary_id = t7.order_summary_id " +
        "INNER JOIN o_order_details t9 " +
        "ON t9.store_id = t8.store_id " +
        "AND t9.order_id = t8.order_id " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t9.store_id " +
        "AND t1.item_id = t9.item_id " +
        "AND t1.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t7.receivables_id = :receivablesId " +
        "AND t1.item_type = '04' " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Integer getItemByReceivableBuffetCount(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("searchInfo") String searchInfo,
        @Param("receivablesId") String receivablesId);

    /**
     * 受付で商品（放題含み）情報取得.
     *
     * @param storeId       店舗ID
     * @param categoryId    カテゴリーID
     * @param languages     言語
     * @param searchInfo    検索内容
     * @param sellOffStart  売り切り開始日時
     * @param receivablesId 受付ID
     * @param pageable      ページ情報
     * @param idList        商品区分リスト
     * @param status        商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.imagePath, " +
        "d.shortImagePath, " +
        "d.videoPath, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'1' AS buffetFlag " +
        "FROM o_order_summary t7 " +
        "INNER JOIN o_order t8 " +
        "ON t8.store_id = t7.store_id " +
        "AND t8.order_summary_id = t7.order_summary_id " +
        "INNER JOIN o_order_details t9 " +
        "ON t9.store_id = t8.store_id " +
        "AND t9.order_id = t8.order_id " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t9.store_id " +
        "AND t6.item_id = t9.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t7.receivables_id = :receivablesId " +
        "AND t1.del_flag = 0 " +
        "UNION " +
        "SELECT t1.store_id AS storeId, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* " +
        "               FROM o_order_summary t7 " +
        "               INNER JOIN o_order t8 " +
        "               ON t8.store_id = t7.store_id " +
        "               AND t8.order_summary_id = t7.order_summary_id " +
        "               INNER JOIN o_order_details t9 " +
        "               ON t9.store_id = t8.store_id " +
        "               AND t9.order_id = t8.order_id " +
        "               INNER JOIN r_buffet_item t6 " +
        "               ON t6.store_id = t9.store_id " +
        "               AND t6.item_id = t9.item_id " +
        "               AND t6.del_flag = 0 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t7.receivables_id = :receivablesId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId " +
        "AND tt1.status = :status ORDER BY d.buffetFlag DESC ", nativeQuery = true)
    Page<Map<String, Object>> getItemByReceivableBuffetAndNormal(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, @Param("sellOffStart") ZonedDateTime sellOffStart,
        @Param("receivablesId") String receivablesId, @Param("idList") List<String> idList,
        Pageable pageable, @Param("status") String status);

    /**
     * 放題商品情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param searchInfo   検索内容
     * @param sellOffStart 売り切り開始日時
     * @param buffetId     放題商品ID
     * @param pageable     ページ情報
     * @param status       商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.imagePath, " +
        "d.shortImagePath, " +
        "d.videoPath, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t1.item_id AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'1' AS buffetFlag " +
        "FROM m_item t7 " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t7.store_id " +
        "AND t6.buffet_id = t7.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t7.item_type = '04' " +
        "AND t7.item_id = :buffetId " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    Page<Map<String, Object>> getItemBuffet(@Param("storeId") String storeId,
        @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, @Param("sellOffStart") ZonedDateTime sellOffStart,
        @Param("buffetId") Integer buffetId, Pageable pageable, @Param("status") String status);

    /**
     * 放題以外商品情報取得.
     *
     * @param storeId      店舗ID
     * @param categoryId   カテゴリーID
     * @param languages    言語
     * @param searchInfo   検索内容
     * @param sellOffStart 売り切り開始日時
     * @param buffetId     放題商品IDリスト
     * @param pageable     ページ情報
     * @param idList       商品区分リスト
     * @param status       商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.imagePath, " +
        "d.shortImagePath, " +
        "d.videoPath, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* FROM r_buffet_item t6 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t6.buffet_id IN :buffetId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    Page<Map<String, Object>> getItemNormal(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, @Param("sellOffStart") ZonedDateTime sellOffStart,
        @Param("buffetId") List<Integer> buffetId, @Param("idList") List<String> idList,
        Pageable pageable, @Param("status") String status);

    /**
     * 受付で放題商品情報取得.
     *
     * @param storeId       店舗ID
     * @param languages     言語
     * @param searchInfo    検索内容
     * @param sellOffStart  売り切り開始日時
     * @param receivablesId 受付ID
     * @param pageable      ページ情報
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.imagePath, " +
        "d.shortImagePath, " +
        "d.videoPath, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t1.item_id AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'1' AS buffetFlag " +
        "FROM o_order_summary t7 " +
        "INNER JOIN o_order t8 " +
        "ON t8.store_id = t7.store_id " +
        "AND t8.order_summary_id = t7.order_summary_id " +
        "INNER JOIN o_order_details t9 " +
        "ON t9.store_id = t8.store_id " +
        "AND t9.order_id = t8.order_id " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t9.store_id " +
        "AND t6.item_id = t9.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t7.receivables_id = :receivablesId " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId ", nativeQuery = true)
    Page<Map<String, Object>> getItemByReceivableBuffet(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("searchInfo") String searchInfo,
        @Param("sellOffStart") ZonedDateTime sellOffStart,
        @Param("receivablesId") String receivablesId, Pageable pageable);

    /**
     * 受付で放題以外商品情報取得.
     *
     * @param storeId       店舗ID
     * @param categoryId    カテゴリーID
     * @param languages     言語
     * @param searchInfo    検索内容
     * @param sellOffStart  売り切り開始日時
     * @param receivablesId 受付ID
     * @param pageable      ページ情報
     * @param idList        商品区分リスト
     * @param status        商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.imagePath, " +
        "d.shortImagePath, " +
        "d.videoPath, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* " +
        "               FROM o_order_summary t7 " +
        "               INNER JOIN o_order t8 " +
        "               ON t8.store_id = t7.store_id " +
        "               AND t8.order_summary_id = t7.order_summary_id " +
        "               INNER JOIN o_order_details t9 " +
        "               ON t9.store_id = t8.store_id " +
        "               AND t9.order_id = t8.order_id " +
        "               INNER JOIN r_buffet_item t6 " +
        "               ON t6.store_id = t9.store_id " +
        "               AND t6.item_id = t9.item_id " +
        "               AND t6.del_flag = 0 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t7.receivables_id = :receivablesId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    Page<Map<String, Object>> getItemByReceivableNormal(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, @Param("sellOffStart") ZonedDateTime sellOffStart,
        @Param("receivablesId") String receivablesId, @Param("idList") List<String> idList,
        Pageable pageable, @Param("status") String status);

    /**
     * 税情報.
     *
     * @param businessId ビジネスID
     * @param storeId    店舗ID
     * @param applyDate  適用開始日時
     * @param itemIds    商品IDリスト
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.common.shared.model.TaxInfoDto(" +
        "t2.storeId," +
        "t1.itemId," +
        "t3.taxId," +
        "t3.taxCode," +
        "t3.taxRoundType," +
        "t3.taxReliefApplyType, " +
        "t3.taxRateNormal," +
        "t3.taxRateRelief) " +
        "from MItem t1 " +
        "INNER JOIN MStore t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t2.delFlag = 0 " +
        "INNER JOIN MTax t3 " +
        "ON t3.businessId = t2.businessId " +
        "AND t1.taxId = t3.taxId " +
        "AND t3.delFlag = 0 " +
        "AND t3.applyDate <= :applyDate " +
        "WHERE t2.businessId = :businessId " +
        "AND t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId in (:itemIds) ")
    List<TaxInfoDto> getTaxInfo(@Param("businessId") String businessId,
        @Param("storeId") String storeId, @Param("applyDate") ZonedDateTime applyDate,
        @Param("itemIds") List<Integer> itemIds);

    /**
     * 商品情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param languages  言語
     * @param idList     商品区分リスト
     * @param status     商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT t1.store_id, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t1.status = :status " +
        "AND t1.del_flag = 0 ORDER BY t4.sort_order ASC", nativeQuery = true)
    List<Map<String, Object>> getItemList(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("idList") List<String> idList, @Param("status") String status);

    /**
     * 商品（放題含み）情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param languages  言語
     * @param buffetId   放題商品ID
     * @param idList     商品区分リスト
     * @param status     商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "'1' AS buffetFlag " +
        "FROM m_item t7 " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t7.store_id " +
        "AND t6.buffet_id = t7.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t7.item_type = '04' " +
        "AND t7.item_id = :buffetId " +
        "AND t1.del_flag = 0 " +
        "UNION " +
        "SELECT t1.store_id AS storeId, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* FROM r_buffet_item t6 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t6.buffet_id = :buffetId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId " +
        "AND tt1.status = :status ORDER BY d.buffetFlag DESC ", nativeQuery = true)
    List<Map<String, Object>> getItemBuffetAndNormalList(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("buffetId") Integer buffetId, @Param("idList") List<String> idList,
        @Param("status") String status);

    /**
     * 受付で放題商品情報件数取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 放題商品情報件数
     */
    @Query(value = "SELECT COUNT(t1) " +
        "FROM o_order_summary t7 " +
        "INNER JOIN o_order t8 " +
        "ON t8.store_id = t7.store_id " +
        "AND t8.order_summary_id = t7.order_summary_id " +
        "INNER JOIN o_order_details t9 " +
        "ON t9.store_id = t8.store_id " +
        "AND t9.order_id = t8.order_id " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t9.store_id " +
        "AND t1.item_id = t9.item_id " +
        "AND t1.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t7.receivables_id = :receivablesId " +
        "AND t1.item_type = '04'" +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Integer getItemByReceivableBuffetListCount(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 受付で商品（放題含み）情報取得.
     *
     * @param storeId       店舗ID
     * @param categoryId    カテゴリーID
     * @param languages     言語
     * @param receivablesId 受付ID
     * @param idList        商品区分リスト
     * @param status        商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "'1' AS buffetFlag " +
        "FROM o_order_summary t7 " +
        "INNER JOIN o_order t8 " +
        "ON t8.store_id = t7.store_id " +
        "AND t8.order_summary_id = t7.order_summary_id " +
        "INNER JOIN o_order_details t9 " +
        "ON t9.store_id = t8.store_id " +
        "AND t9.order_id = t8.order_id " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t9.store_id " +
        "AND t6.item_id = t9.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t7.receivables_id = :receivablesId " +
        "AND t1.del_flag = 0 " +
        "UNION " +
        "SELECT t1.store_id AS storeId, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* " +
        "               FROM o_order_summary t7 " +
        "               INNER JOIN o_order t8 " +
        "               ON t8.store_id = t7.store_id " +
        "               AND t8.order_summary_id = t7.order_summary_id " +
        "               INNER JOIN o_order_details t9 " +
        "               ON t9.store_id = t8.store_id " +
        "               AND t9.order_id = t8.order_id " +
        "               INNER JOIN r_buffet_item t6 " +
        "               ON t6.store_id = t9.store_id " +
        "               AND t6.item_id = t9.item_id " +
        "               AND t6.del_flag = 0 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t7.receivables_id = :receivablesId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId " +
        "AND tt1.status = :status ORDER BY d.buffetFlag DESC ", nativeQuery = true)
    List<Map<String, Object>> getItemByReceivableBuffetUnionNormalList(
        @Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("receivablesId") String receivablesId, @Param("idList") List<String> idList,
        @Param("status") String status);

    /**
     * 放題商品情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @param buffetId  放題商品ID
     * @param status    商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t1.item_id AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "'1' AS buffetFlag " +
        "FROM m_item t7 " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t7.store_id " +
        "AND t6.buffet_id = t7.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t7.item_type = '04' " +
        "AND t7.item_id = :buffetId " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    List<Map<String, Object>> getItemBuffetList(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("buffetId") Integer buffetId,
        @Param("status") String status);

    /**
     * 放題以外商品情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param languages  言語
     * @param buffetId   放題商品IDリスト
     * @param idList     商品区分リスト
     * @param status     商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* FROM r_buffet_item t6 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t6.buffet_id IN :buffetId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    List<Map<String, Object>> getItemNormalList(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("buffetId") List<Integer> buffetId, @Param("idList") List<String> idList,
        @Param("status") String status);

    /**
     * 受付で放題商品情報取得.
     *
     * @param storeId       店舗ID
     * @param languages     言語
     * @param receivablesId 受付ID
     * @param status        商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t1.item_id AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "'1' AS buffetFlag " +
        "FROM o_order_summary t7 " +
        "INNER JOIN o_order t8 " +
        "ON t8.store_id = t7.store_id " +
        "AND t8.order_summary_id = t7.order_summary_id " +
        "INNER JOIN o_order_details t9 " +
        "ON t9.store_id = t8.store_id " +
        "AND t9.order_id = t8.order_id " +
        "INNER JOIN r_buffet_item t6 " +
        "ON t6.store_id = t9.store_id " +
        "AND t6.item_id = t9.item_id " +
        "AND t6.del_flag = 0 " +
        "INNER JOIN m_item t1 " +
        "ON t1.store_id = t6.store_id " +
        "AND t1.item_id = t6.item_id " +
        "AND t1.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t7.receivables_id = :receivablesId " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    List<Map<String, Object>> getItemByReceivableBuffetList(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("receivablesId") String receivablesId,
        @Param("status") String status);

    /**
     * 受付で放題以外商品情報取得.
     *
     * @param storeId       店舗ID
     * @param categoryId    カテゴリーID
     * @param languages     言語
     * @param receivablesId 受付ID
     * @param idList        商品区分リスト
     * @param status        商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT d.storeId, " +
        "d.sortOrder, " +
        "d.itemId, " +
        "d.itemName, " +
        "d.itemPrice, " +
        "d.itemUnitName, " +
        "d.itemInfo, " +
        "d.itemSelloffFlag, " +
        "d.optionFlag, " +
        "d.buffetFlag " +
        "FROM m_item tt1, ( " +
        "SELECT t1.store_id AS storeId, " +
        "t4.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "'0' AS buffetFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.category_id = :categoryId " +
        "AND t4.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND NOT EXISTS(SELECT t6.* " +
        "               FROM o_order_summary t7 " +
        "               INNER JOIN o_order t8 " +
        "               ON t8.store_id = t7.store_id " +
        "               AND t8.order_summary_id = t7.order_summary_id " +
        "               INNER JOIN o_order_details t9 " +
        "               ON t9.store_id = t8.store_id " +
        "               AND t9.order_id = t8.order_id " +
        "               INNER JOIN r_buffet_item t6 " +
        "               ON t6.store_id = t9.store_id " +
        "               AND t6.item_id = t9.item_id " +
        "               AND t6.del_flag = 0 " +
        "               WHERE t6.store_id = t1.store_id " +
        "               AND t7.receivables_id = :receivablesId " +
        "               AND t6.item_id = t1.item_id " +
        "               AND t6.del_flag = 0) " +
        "AND t1.del_flag = 0 " +
        ") d WHERE tt1.store_id = d.storeId AND tt1.item_id = d.itemId AND tt1.status = :status ", nativeQuery = true)
    List<Map<String, Object>> getItemByReceivableNormalList(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("receivablesId") String receivablesId, @Param("idList") List<String> idList,
        @Param("status") String status);

    /**
     * PC商品一覧情報取得.
     *
     * @param storeId       店舗ID
     * @param categoryId    カテゴリーID
     * @param languages     言語
     * @param itemName      商品名
     * @param codeGroup     商品ステータス
     * @param selectStatus  状態
     * @param selectKitchen キチンID
     * @param pageable      ページ情報
     * @return 商品情報
     */
    @Query(value = "SELECT mi.item_id as itemId, " +
        "mi.item_name ->> :languages as itemName, " +
        "mi.item_price as itemPrice, " +
        "mco.code_name as itemStatus, " +
        "mk.kitchen_name as kitchenName," +
        "ROW_NUMBER() OVER(ORDER BY rc.sort_order ASC) AS num " +
        "FROM m_item mi, r_category_item rc,m_code mco,m_kitchen mk " +
        "where mi.store_id = rc.store_id " +
        "and mi.item_id = rc.item_id " +
        "and mi.status = mco.code " +
        "and mi.store_id = mco.store_id " +
        "and mco.code_group = :codeGroup " +
        "and mi.store_id = mk.store_id " +
        "and mi.kitchen_id = mk.kitchen_id " +
        "and mi.del_flag = 0 " +
        "and rc.del_flag = 0 " +
        "and mco.del_flag = 0 " +
        "and mk.del_flag = 0 " +
        "and rc.category_id = :categoryId " +
        "and mi.store_id = :storeId " +
        "and (:selectStatus = '' OR mi.status = :selectStatus) " +
        "and (:selectKitchen = -1 OR mi.kitchen_id = :selectKitchen) " +
        "and ((mi.item_name ->> :languages) LIKE  '%'||:itemName||'%' OR :itemName = '') " +
        "order by rc.sort_order", nativeQuery = true)
    Page<Map<String, Object>> getItemData(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("itemName") String itemName, @Param("codeGroup") String codeGroup,
        @Param("selectStatus") String selectStatus, @Param("selectKitchen") Integer selectKitchen,
        Pageable pageable);

    /**
     * 税情報.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     * @return 税情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetItemOutputDto(" +
        "t1.itemName," +
        "t1.itemInfo," +
        "t1.unitId," +
        "t1.itemPrice," +
        "t1.taxId," +
        "t1.kitchenId, " +
        "t1.deliveryFlag, " +
        "t1.deliveryTypeFlag, " +
        "t2.imagePath," +
        "t2.shortImagePath, " +
        "t1.itemType," +
        "t2.videoPath)" +
        "from MItem t1 " +
        "LEFT JOIN MItemImage t2 " +
        "ON t1.storeId = t2.storeId " +
        "AND t1.itemId = t2.itemId " +
        "AND t2.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId = :itemId ")
    GetItemOutputDto getItemInfo(@Param("storeId") String storeId, @Param("itemId") Integer itemId);

    /**
     * 商品サマリ更新処理.
     *
     * @param storeId          店舗ID
     * @param itemId           商品ID
     * @param unitId           単位ID
     * @param kitchenId        キチンID
     * @param optionFlag       オプション有フラグ
     * @param itemName         商品名
     * @param itemPrice        商品单价
     * @param itemInfo         商品说明
     * @param taxId            税ID
     * @param deliveryFlag     出前フラグ
     * @param deliveryTypeFlag 出前仕方フラグ
     * @param updOperCd        更新者
     * @param updDateTime      更新日時
     */
    @Modifying
    @Query(value = "update m_item "
        + "set unit_id = :unitId, "
        + "kitchen_id = :kitchenId, "
        + "option_flag = :optionFlag, "
        + "item_name = CAST(:itemName AS JSONB), "
        + "item_price = :itemPrice, "
        + "item_info = CAST(:itemInfo AS JSONB), "
        + "tax_id = :taxId, "
        + "delivery_flag = :deliveryFlag, "
        + "delivery_type_flag = :deliveryTypeFlag, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and item_id = :itemId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateItem(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("unitId") Integer unitId,
        @Param("kitchenId") Integer kitchenId,
        @Param("optionFlag") String optionFlag,
        @Param("itemName") String itemName,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("itemInfo") String itemInfo,
        @Param("taxId") Integer taxId,
        @Param("deliveryFlag") String deliveryFlag,
        @Param("deliveryTypeFlag") String deliveryTypeFlag,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 商品マスタテーブル登録.
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO m_item(" +
        "store_id," +
        "item_id," +
        "unit_id," +
        "kitchen_id," +
        "option_flag," +
        "item_name," +
        "item_price," +
        "item_info," +
        "status," +
        "item_type," +
        "tax_id," +
        "delivery_flag," +
        "delivery_type_flag," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":itemId," +
        ":unitId," +
        ":kitchenId," +
        ":optionFlag," +
        "CAST(:itemName AS JSONB)," +
        ":itemPrice," +
        "CAST(:itemInfo AS JSONB)," +
        ":status," +
        ":itemType," +
        ":taxId," +
        ":deliveryFlag," +
        ":deliveryTypeFlag," +
        "0," +
        ":operCd," +
        ":dateTime," +
        ":operCd," +
        ":dateTime," +
        "0)", nativeQuery = true)
    void insertItem(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("unitId") Integer unitId,
        @Param("kitchenId") Integer kitchenId,
        @Param("optionFlag") String optionFlag,
        @Param("itemName") String itemName,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("itemInfo") String itemInfo,
        @Param("status") String status,
        @Param("itemType") String itemType,
        @Param("taxId") Integer taxId,
        @Param("deliveryFlag") String deliveryFlag,
        @Param("deliveryTypeFlag") String deliveryTypeFlag,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 指定商品ID削除.
     *
     * @param storeId     店舗ID
     * @param itemIdList  商品IDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE MItem t1 "
        + "SET t1.delFlag = 1, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.itemId IN :itemIdList ")
    void updateDelFlagByItemId(@Param("storeId") String storeId,
        @Param("itemIdList") List<Integer> itemIdList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定商品ステータス更新.
     *
     * @param storeId             店舗ID
     * @param itemIdList          飲み放題IDリスト
     * @param salesClassification 销售区分
     * @param updOperCd           更新者
     * @param updDateTime         更新日時
     */
    @Modifying
    @Query(value = "UPDATE MItem t1 "
        + "SET t1.status = :salesClassification, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.itemId IN :itemIdList "
        + "AND t1.delFlag = 0 ")
    void updateStatusByItemId(@Param("storeId") String storeId,
        @Param("itemIdList") List<Integer> itemIdList,
        @Param("salesClassification") String salesClassification,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);


    /**
     * 商品情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return 商品情報
     */
    @Query(value = "SELECT t1.item_id AS itemId, "
        + "t1.item_name ->> :languages AS itemName "
        + "FROM m_item t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0", nativeQuery = true)
    List<Map<String, Object>> getItemListByStoreId(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * 放題一覧情報取得.
     *
     * @param storeId  店舗ID
     * @param itemType 商品区分
     * @param status   商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.BuffetListDto("
        + "t1.itemId, "
        + "t1.itemName,"
        + "t1.itemPrice) "
        + "from MItem t1 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.itemType = :itemType "
        + "AND t1.status = :status "
        + "AND t1.delFlag = 0 ")
    List<BuffetListDto> getBuffetList(@Param("storeId") String storeId,
        @Param("itemType") String itemType, @Param("status") String status);

    /**
     * 放题商品取得.
     *
     * @param storeId  店舗ID
     * @param buffetId 商品ID
     * @param code     削除フラグ
     * @return 商品情報
     */
    MItem findByStoreIdAndItemIdAndDelFlag(String storeId, Integer buffetId, Integer code);

    /**
     * 放题商品情報取得.
     *
     * @param storeId   店舗ID
     * @param buffetId  放题商品IDリスト
     * @param courseId  コース商品IDリスト
     * @param languages 言語
     * @return 商品情報
     */
    @Query(value = "SELECT DISTINCT t2.item_id AS itemId, "
        + "t3.item_info ->> :languages AS itemName "
        + "FROM m_item t1 "
        + "INNER JOIN r_item t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.item_id = t2.buffet_id "
        + "INNER JOIN m_item t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.item_id = t3.item_id "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.item_id IN :buffetId "
        + "AND (t2.item_id NOT IN :courseId OR :courseId IS NULL) "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "ORDER BY t2.item_id ASC", nativeQuery = true)
    List<Map<String, Object>> getBuffetInfoByStoreId(@Param("storeId") String storeId,
        @Param("buffetId") List<Integer> buffetId, @Param("courseId") List<Integer> courseId,
        @Param("languages") String languages);


    /**
     * 商品情報取得.
     *
     * @param storeId    店舗ID
     * @param categoryId カテゴリーID
     * @param languages  言語
     * @param searchInfo 検索内容
     * @param pageable   ページ情報
     * @param idList     商品区分リスト
     * @param status     商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT t1.store_id, " +
        "t5.item_id AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "0 AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "'0' AS itemSelloffFlag, " +
        "'0' AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath, " +
        "t4.video_path AS videoPath, " +
        "'1' AS courseFlag " +
        "FROM m_item t1 " +
        "INNER JOIN r_buffet_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.buffet_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t1.status = :status " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Page<Map<String, Object>> getCourseItem(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo, Pageable pageable,
        @Param("idList") List<String> idList, @Param("status") String status);


    /**
     * コース商品情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param languages     言語
     * @param itemType      商品区分
     * @return 商品情報
     */
    @Query(value = "SELECT DISTINCT t4.item_id AS itemId, "
        + "t4.item_info ->> :languages AS itemInfo "
        + "FROM o_order_summary t1, o_order t2, o_order_details t3, m_item t4 "
        + "WHERE t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "AND t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "AND t3.store_id = t4.store_id "
        + "AND t3.item_id = t4.item_id "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "AND t4.del_flag = 0 "
        + "AND t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t4.item_type = :itemType "
        + "ORDER BY t4.item_id ASC", nativeQuery = true)
    List<Map<String, Object>> getCourseInfoByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId, @Param("languages") String languages,
        @Param("itemType") String itemType);

    /**
     * 放题商品情報取得.
     *
     * @param storeId  店舗ID
     * @param buffetId 放题商品IDリスト
     * @return 商品情報
     */
    @Query(value = "SELECT MIN(t3.buffetId) AS buffetId "
        + "FROM ("
        + "    SELECT string_agg(CAST(t2.item_id AS VARCHAR), '') AS itemId,"
        + "           t2.buffet_id AS buffetId"
        + "    FROM ( SELECT t1.buffet_id, t1.item_id "
        + "        FROM r_buffet_item t1 "
        + "        WHERE t1.store_id = :storeId "
        + "        AND t1.buffet_id IN :buffetId "
        + "        AND t1.del_flag = 0 "
        + "        ORDER BY t1.buffet_id ASC, t1.item_id ASC) t2 "
        + "    GROUP BY t2.buffet_id) t3 "
        + "GROUP BY t3.itemId ", nativeQuery = true)
    List<Integer> getBuffetItemByStoreId(@Param("storeId") String storeId,
        @Param("buffetId") List<Integer> buffetId);

    /**
     * コース商品情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return 商品情報
     */
    @Query(value = "SELECT t1.item_id AS courseId, "
        + "t1.item_name ->> :languages AS courseName "
        + "FROM m_item t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.item_type = '03' "
        + "AND t1.status = '02' "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.item_id ASC", nativeQuery = true)
    List<Map<String, Object>> findCourseInfoByStoreId(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * 放題商品情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return 商品情報
     */
    @Query(value = "SELECT t1.item_id AS buffetId, "
        + "t1.item_name ->> :languages AS buffetName "
        + "FROM m_item t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.item_type = '04' "
        + "AND t1.status = '02' "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t1.item_id ASC", nativeQuery = true)
    List<Map<String, Object>> findBuffetInfoByStoreId(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * 放題商品情報取得.
     *
     * @param storeId 店舗ID
     * @param course  商品区分
     * @param buffet  商品区分
     * @param display 商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT count(*) "
        + "FROM m_item t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.status = :display "
        + "AND (t1.item_type = :course OR t1.item_type = :buffet) "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Integer getBuffetCourseCount(@Param("storeId") String storeId,
        @Param("course") String course, @Param("buffet") String buffet,
        @Param("display") String display);

    /**
     * コース＆放题基本信息取得.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.BuffetCourseItemDto("
        + "t1.itemName, "
        + "t1.itemPrice, "
        + "t3.taxCode, "
        + "t1.itemInfo, "
        + "t1.itemType) "
        + "from MItem t1 "
        + "INNER JOIN MStore t2 "
        + "ON t1.storeId = t2.storeId "
        + "INNER JOIN MTax t3 "
        + "ON t2.businessId = t3.businessId "
        + "AND t1.taxId = t3.taxId "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.itemId = :itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "AND t3.delFlag = 0 ")
    BuffetCourseItemDto getBuffetCourseItemInfo(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId);

    /**
     * 放题商品情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param itemTypeList 商品区分リスト
     * @param status       商品状態
     * @return 商品情報
     */
    @Query(value = "SELECT DISTINCT t1.item_id AS courseBuffetId, "
        + "t1.item_name ->> :languages AS courseBuffetName, "
        + "t1.item_price AS courseBuffetPrice, "
        + "t1.item_type AS itemType, "
        + "t2.image_path AS imagePath,"
        + "t2.short_image_path AS shortImagePath, "
        + "t2.video_path AS videoPath, "
        + "t3.sort_order "
        + "FROM m_item t1 "
        + "LEFT JOIN m_item_image t2 "
        + "ON t2.store_id = t1.store_id "
        + "AND t2.item_id = t1.item_id "
        + "AND t2.del_flag = 0 "
        + "INNER JOIN r_category_item t3 "
        + "ON t1.store_id = t3.store_id "
        + "AND t1.item_id = t3.item_id "
        + "AND t3.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "AND t1.status = :status "
        + "AND t1.item_type IN (:itemTypeList)  order by t1.item_type asc, t3.sort_order asc ", nativeQuery = true)
    List<Map<String, Object>> getCourseBuffetList(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("itemTypeList") List<String> itemTypeList,
        @Param("status") String status);

    /**
     * PCコース一覧情報取得.
     *
     * @param storeId      店舗ID
     * @param courseStatus 商品区分リスト
     * @param languages    言語
     * @param courseName   コース名
     * @param codeGroup    商品ステータス
     * @param categoryId   基本分類
     * @param itemType     商品区分
     * @param pageable     ページ情報
     * @return 商品情報
     */
    @Query(value = "SELECT mi.item_id as courseId, " +
        "mi.item_name ->> :languages as courseName, " +
        "mi.item_price as coursePrice, " +
        "mco.code_name as courseStatus, " +
        "mu.unit_name ->> :languages as courseUnitName," +
        "ROW_NUMBER() OVER(ORDER BY rc.sort_order ASC) AS num " +
        "FROM m_item mi, m_code mco,m_unit mu, r_category_item rc " +
        "where mi.status = mco.code " +
        "and mi.store_id = mco.store_id " +
        "and mco.code_group = :codeGroup " +
        "and mi.store_id = mu.store_id " +
        "and mi.unit_id = mu.unit_id " +
        "and mi.store_id = rc.store_id " +
        "and mi.item_id = rc.item_id " +
        "and mi.del_flag = 0 " +
        "and mco.del_flag = 0 " +
        "and mu.del_flag = 0 " +
        "and rc.del_flag = 0 " +
        "and mi.store_id = :storeId " +
        "and (mi.status = :courseStatus OR :courseStatus = '') " +
        "and mi.item_type = :itemType " +
        "and rc.category_id = :categoryId  " +
        "and ((mi.item_name ->> :languages) LIKE  '%'||:courseName||'%' OR :courseName = '') " +
        "order by rc.sort_order asc", nativeQuery = true)
    Page<Map<String, Object>> getCourseData(@Param("storeId") String storeId,
        @Param("courseStatus") String courseStatus, @Param("languages") String languages,
        @Param("courseName") String courseName, @Param("codeGroup") String codeGroup,
        @Param("categoryId") Integer categoryId, @Param("itemType") String itemType,
        Pageable pageable);

    /**
     * 税情報.
     *
     * @param storeId  店舗ID
     * @param courseId コースID
     * @return 税情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetCourseOutputDto(" +
        "t1.itemName as courseName," +
        "t1.itemInfo as courseInfo," +
        "t1.unitId as courseUnitId," +
        "t1.itemPrice as coursePrice," +
        "t1.taxId," +
        "t2.imagePath as courseLargePicUrl," +
        "t2.shortImagePath as courseSmallPicUrl) " +
        "from MItem t1 " +
        "LEFT JOIN MItemImage t2 " +
        "on t1.storeId = t2.storeId " +
        "AND t1.itemId = t2.itemId " +
        "AND t2.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId = :courseId ")
    GetCourseOutputDto getCourseInfo(@Param("storeId") String storeId,
        @Param("courseId") Integer courseId);

    /**
     * PC飲み放題一覧情報取得.
     *
     * @param storeId      店舗ID
     * @param buffetStatus 商品区分リスト
     * @param languages    言語
     * @param buffetName   放題名
     * @param codeGroup    商品ステータス
     * @param categoryId   基本分類
     * @param itemType     商品区分
     * @param pageable     ページ情報
     * @return 商品情報
     */
    @Query(value = "SELECT mi.item_id as buffetId, " +
        "mi.item_name ->> :languages as buffetName, " +
        "mi.item_price as buffetPrice, " +
        "mco.code_name as buffetStatus," +
        "ROW_NUMBER() OVER(ORDER BY rc.sort_order ASC) AS num  " +
        "FROM m_item mi, m_code mco, r_category_item rc " +
        "where mi.status = mco.code " +
        "and mi.store_id = mco.store_id " +
        "and mco.code_group = :codeGroup " +
        "and mi.store_id = rc.store_id " +
        "and mi.item_id = rc.item_id " +
        "and mi.del_flag = 0 " +
        "and mco.del_flag = 0 " +
        "and rc.del_flag = 0 " +
        "and mi.store_id = :storeId " +
        "and rc.category_id = :categoryId  " +
        "and (mi.status = :buffetStatus OR :buffetStatus = '') " +
        "and mi.item_type = :itemType " +
        "and ((mi.item_name ->> :languages) LIKE  '%'||:buffetName||'%' OR :buffetName = '') " +
        "order by rc.sort_order asc", nativeQuery = true)
    Page<Map<String, Object>> getBuffetData(@Param("storeId") String storeId,
        @Param("buffetStatus") String buffetStatus, @Param("languages") String languages,
        @Param("buffetName") String buffetName, @Param("codeGroup") String codeGroup,
        @Param("categoryId") Integer categoryId, @Param("itemType") String itemType,
        Pageable pageable);

    /**
     * 飲み放題一覧情報.
     *
     * @param storeId  店舗ID
     * @param buffetId コースID
     * @return 商品情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.pc.model.GetBuffetOutputDto(" +
        "t1.itemName as buffetName," +
        "t1.itemInfo as buffetInfo," +
        "t1.itemPrice as buffetPrice," +
        "t1.buffetTime as buffetTime," +
        "t1.taxId," +
        "t2.imagePath as buffetLargePicUrl," +
        "t2.shortImagePath as buffetSmallPicUrl, " +
        "t3.itemId as attachItemId) " +
        "from MItem t1 " +
        "LEFT JOIN MItemImage t2 " +
        "on t1.storeId = t2.storeId " +
        "AND t1.itemId = t2.itemId " +
        "AND t2.delFlag = 0 " +
        "LEFT JOIN RItem t3 " +
        "on t1.storeId = t3.storeId " +
        "AND t1.itemId = t3.buffetId " +
        "AND t3.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 " +
        "AND t1.itemId = :buffetId ")
    GetBuffetOutputDto getBuffetInfo(@Param("storeId") String storeId,
        @Param("buffetId") Integer buffetId);

    /**
     * 商品マスタテーブル( 飲み放題登録).
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO m_item(" +
        "store_id," +
        "item_id," +
        "unit_id," +
        "kitchen_id," +
        "option_flag," +
        "item_name," +
        "item_price," +
        "item_info," +
        "status," +
        "item_type," +
        "tax_id," +
        "buffet_time," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":itemId," +
        ":unitId," +
        ":kitchenId," +
        ":optionFlag," +
        "CAST(:itemName AS JSONB)," +
        ":itemPrice," +
        "CAST(:itemInfo AS JSONB)," +
        ":status," +
        ":itemType," +
        ":taxId," +
        ":buffetTime," +
        "0," +
        ":operCd," +
        ":dateTime," +
        ":operCd," +
        ":dateTime," +
        "0)", nativeQuery = true)
    void insertBuffet(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("optionFlag") String optionFlag,
        @Param("itemName") String itemName,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("unitId") Integer unitId,
        @Param("kitchenId") Integer kitchenId,
        @Param("itemInfo") String itemInfo,
        @Param("status") String status,
        @Param("itemType") String itemType,
        @Param("taxId") Integer taxId,
        @Param("buffetTime") Integer buffetTime,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 商品サマリ更新処理(飲み放題).
     *
     * @param storeId     店舗ID
     * @param itemId      商品ID
     * @param itemName    商品名
     * @param itemPrice   商品单价
     * @param itemInfo    商品说明
     * @param taxId       税ID
     * @param buffetTime  放題時間
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update m_item "
        + "set item_name = CAST(:itemName AS JSONB), "
        + "item_price = :itemPrice, "
        + "item_info = CAST(:itemInfo AS JSONB), "
        + "tax_id = :taxId, "
        + "buffet_time = :buffetTime, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and item_id = :itemId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateBuffet(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("itemName") String itemName,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("itemInfo") String itemInfo,
        @Param("taxId") Integer taxId,
        @Param("buffetTime") Integer buffetTime,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 商品マスタテーブル( コース登録).
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO m_item(" +
        "store_id," +
        "item_id," +
        "unit_id," +
        "kitchen_id," +
        "option_flag," +
        "item_name," +
        "item_price," +
        "item_info," +
        "status," +
        "item_type," +
        "tax_id," +
        "buffet_time," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":itemId," +
        ":unitId," +
        ":kitchenId," +
        ":optionFlag," +
        "CAST(:itemName AS JSONB)," +
        ":itemPrice," +
        "CAST(:itemInfo AS JSONB)," +
        ":status," +
        ":itemType," +
        ":taxId," +
        "null," +
        "0," +
        ":operCd," +
        ":dateTime," +
        ":operCd," +
        ":dateTime," +
        "0)", nativeQuery = true)
    void insertCourse(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("unitId") Integer unitId,
        @Param("kitchenId") Integer kitchenId,
        @Param("optionFlag") String optionFlag,
        @Param("itemName") String itemName,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("itemInfo") String itemInfo,
        @Param("status") String status,
        @Param("itemType") String itemType,
        @Param("taxId") Integer taxId,
        @Param("operCd") String operCd,
        @Param("dateTime") ZonedDateTime dateTime);

    /**
     * 商品サマリ更新処理(コース).
     *
     * @param storeId     店舗ID
     * @param itemId      商品ID
     * @param itemName    商品名
     * @param itemPrice   商品单价
     * @param itemInfo    商品说明
     * @param taxId       税ID
     * @param unitId      単位ID
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "update m_item "
        + "set item_name = CAST(:itemName AS JSONB), "
        + "unit_id = :unitId, "
        + "item_price = :itemPrice, "
        + "item_info = CAST(:itemInfo AS JSONB), "
        + "tax_id = :taxId, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and item_id = :itemId "
        + "and store_id = :storeId ", nativeQuery = true)
    void updateCourse(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("itemName") String itemName,
        @Param("itemPrice") BigDecimal itemPrice,
        @Param("itemInfo") String itemInfo,
        @Param("taxId") Integer taxId,
        @Param("unitId") Integer unitId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 放題商品ID取得.
     *
     * @param storeId  店舗ID
     * @param itemType 商品区分
     * @return 商品情報
     */
    @Query(value = "SELECT  t2.itemId " +
        "FROM MItem t1 " +
        "INNER JOIN RBuffetItem t2 " +
        "ON t2.buffetId = t1.itemId " +
        "AND t2.delFlag = 0 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.itemType = :itemType " +
        "AND t1.delFlag = 0 ")
    List<Integer> findBuffetItemByStoreId(@Param("storeId") String storeId,
        @Param("itemType") String itemType);

    /**
     * 出前商品情報取得.
     *
     * @param storeId          店舗ID
     * @param categoryId       カテゴリーID
     * @param languages        言語
     * @param searchInfo       検索内容
     * @param deliveryTypeFlag 出前仕方フラグ
     * @param deliveryFlag     出前フラグ
     * @param sellOffStart     売り切り開始日時
     * @param pageable         ページ情報
     * @param idList           商品区分リスト
     * @return 商品情報
     */
    @Query(value = "SELECT t1.store_id, " +
        "t5.sort_order AS sortOrder, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t2.unit_name ->> :languages AS itemUnitName, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "CASE WHEN t3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "t1.option_flag AS optionFlag, " +
        "t4.image_path AS imagePath, " +
        "t4.short_image_path AS shortImagePath " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t5 " +
        "ON t1.store_id = t5.store_id " +
        "AND t1.item_id = t5.item_id " +
        "AND t5.category_id = :categoryId " +
        "AND t5.del_flag = 0 " +
        "LEFT JOIN m_unit t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.unit_id = t2.unit_id " +
        "AND t2.del_flag = 0 " +
        "LEFT JOIN m_sell_off t3 " +
        "ON t1.store_id = t3.store_id " +
        "AND t1.item_id = t3.item_id " +
        "AND t3.sell_off_start <= :sellOffStart " +
        "AND t3.del_flag = 0 " +
        "LEFT JOIN m_item_image t4 " +
        "ON t1.store_id = t4.store_id " +
        "AND t1.item_id = t4.item_id " +
        "AND t4.del_flag = 0 " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t1.delivery_flag = :deliveryFlag " +
        "AND t1.delivery_type_flag IN :deliveryTypeFlag " +
        "AND ((t1.item_name ->> :languages) LIKE  '%'||:searchInfo||'%' OR :searchInfo = '') " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    Page<Map<String, Object>> getDeliveryItem(@Param("storeId") String storeId,
        @Param("categoryId") Integer categoryId, @Param("languages") String languages,
        @Param("searchInfo") String searchInfo,
        @Param("deliveryTypeFlag") List<String> deliveryTypeFlag,
        @Param("deliveryFlag") String deliveryFlag,
        @Param("sellOffStart") ZonedDateTime sellOffStart, Pageable pageable,
        @Param("idList") List<String> idList);


    /**
     * 商品情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param searchInfo   検索内容
     * @param sellOffStart 売り切り開始日時
     * @param idList       商品区分リスト
     * @return 商品情報
     */
    @Query(value = "SELECT tts.sortOrder, " +
        "tts.itemId, " +
        "tts.itemName, " +
        "tts.itemPrice, " +
        "tts.itemUnitName, " +
        "tts.itemInfo, " +
        "tts.itemSelloffFlag, " +
        "tts.optionFlag, " +
        "tts.imagePath, " +
        "tts.shortImagePath, " +
        "tts.buffetFlag, " +
        "tts.itemCount, " +
        "tts.itemCategoryId " +
        "FROM ( " +
        "SELECT row_number() over(partition by tt1.itemId order by tt1.sortOrder desc) as rownum, "
        +
        "tt1.sortOrder, " +
        "tt1.itemId, " +
        "tt1.itemName, " +
        "tt1.itemPrice, " +
        "tt2.unit_name ->> :languages AS itemUnitName, " +
        "tt1.itemInfo, " +
        "CASE WHEN tt3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "tt1.optionFlag, " +
        "tt4.image_path AS imagePath, " +
        "tt4.short_image_path AS shortImagePath, " +
        "'0' AS buffetFlag, " +
        "'0' AS itemCount, " +
        "tt1.itemCategoryId " +
        "FROM (SELECT pgroonga_score(t3.tableoid, t3.ctid) AS sortOrder, " +
        "t1.store_id, " +
        "t1.unit_id, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "t1.option_flag AS optionFlag, " +
        "t2.category_id AS itemCategoryId " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.item_id = t2.item_id " +
        "INNER JOIN m_category t3 " +
        "ON t2.store_id = t3.store_id " +
        "AND t2.category_id = t3.category_id " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t3.category_name &@~ pgroonga_query_expand('synonym_groups', 'synonyms', 'synonyms', :searchInfo) "
        +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0 " +
        "UNION ALL " +
        "SELECT pgroonga_score(t1.tableoid, t1.ctid) AS sortOrder, " +
        "t1.store_id, " +
        "t1.unit_id, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "t1.option_flag AS optionFlag, " +
        "t2.category_id AS itemCategoryId " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.item_id = t2.item_id " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t1.item_name &@~ pgroonga_query_expand('synonym_groups', 'synonyms', 'synonyms', :searchInfo) "
        +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0) tt1 " +
        "LEFT JOIN m_unit tt2 " +
        "ON tt1.store_id = tt2.store_id " +
        "AND tt1.unit_id = tt2.unit_id " +
        "AND tt2.del_flag = 0 " +
        "LEFT JOIN m_sell_off tt3 " +
        "ON tt1.store_id = tt3.store_id " +
        "AND tt1.itemId = tt3.item_id " +
        "AND tt3.sell_off_start <= :sellOffStart " +
        "AND tt3.del_flag = 0 " +
        "LEFT JOIN m_item_image tt4 " +
        "ON tt1.store_id = tt4.store_id " +
        "AND tt1.itemId = tt4.item_id " +
        "AND tt4.del_flag = 0) tts " +
        "WHERE tts.rownum = 1 " +
        "ORDER BY tts.sortOrder DESC, tts.itemCategoryId ASC, tts.itemId ASC ", nativeQuery = true)
    List<Map<String, Object>> getItemBySpeech(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("searchInfo") String searchInfo,
        @Param("sellOffStart") ZonedDateTime sellOffStart, @Param("idList") List<String> idList);

    /**
     * 商品情報取得.
     *
     * @param storeId      店舗ID
     * @param languages    言語
     * @param searchInfo   検索内容
     * @param sellOffStart 売り切り開始日時
     * @param idList       商品区分リスト
     * @return 商品情報
     */
    @Query(value = "WITH buffidList AS (" +
        "SELECT t5.item_id AS itemId, t4.item_id AS categoryId " +
        "FROM o_order_summary t1, o_order t2, o_order_details t3, m_item t4, r_buffet_item t5 " +
        "WHERE t1.store_id = t2.store_id " +
        "AND t1.order_summary_id = t2.order_summary_id " +
        "AND t2.store_id = t3.store_id " +
        "AND t2.order_id = t3.order_id " +
        "AND t3.store_id = t4.store_id " +
        "AND t3.item_id = t4.item_id " +
        "AND t5.store_id = t4.store_id " +
        "AND t5.buffet_id = t4.item_id " +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0 " +
        "AND t3.del_flag = 0 " +
        "AND t4.del_flag = 0 " +
        "AND t5.del_flag = 0 " +
        "AND t1.store_id = :storeId " +
        "AND t1.receivables_id = :receivablesId " +
        "AND t4.item_type = :itemType) " +
        "SELECT tts.sortOrder, " +
        "tts.itemId, " +
        "tts.itemName, " +
        "CASE WHEN tts.itemId IN (SELECT itemId FROM buffidList) THEN 0 ELSE tts.itemPrice END AS itemPrice, " +
        "tts.itemUnitName, " +
        "tts.itemInfo, " +
        "tts.itemSelloffFlag, " +
        "tts.optionFlag, " +
        "tts.imagePath, " +
        "tts.shortImagePath, " +
        "tts.buffetFlag, " +
        "tts.itemCount, " +
        "CASE WHEN tts.itemId IN (SELECT itemId FROM buffidList) THEN (SELECT categoryId FROM buffidList WHERE itemId = tts.itemId LIMIT 1) ELSE tts.itemCategoryId END AS itemCategoryId " +
        "FROM ( " +
        "SELECT row_number() over(partition by tt1.itemId order by tt1.sortOrder desc) as rownum, " +
        "tt1.sortOrder, " +
        "tt1.itemId, " +
        "tt1.itemName, " +
        "tt1.itemPrice, " +
        "tt2.unit_name ->> :languages AS itemUnitName, " +
        "tt1.itemInfo, " +
        "CASE WHEN tt3.sell_off_id IS NULL THEN '0' ELSE '1' END AS itemSelloffFlag, " +
        "tt1.optionFlag, " +
        "tt4.image_path AS imagePath, " +
        "tt4.short_image_path AS shortImagePath, " +
        "'0' AS buffetFlag, " +
        "'0' AS itemCount, " +
        "tt1.itemCategoryId " +
        "FROM (SELECT 0 AS sortOrder, " +
        "t1.store_id, " +
        "t1.unit_id, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "t1.option_flag AS optionFlag, " +
        "t2.category_id AS itemCategoryId " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.item_id = t2.item_id " +
        "INNER JOIN m_category t3 " +
        "ON t2.store_id = t3.store_id " +
        "AND t2.category_id = t3.category_id " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t3.category_name ->> :languages SIMILAR TO :searchInfo " +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0 " +
        "UNION ALL " +
        "SELECT 1 AS sortOrder, " +
        "t1.store_id, " +
        "t1.unit_id, " +
        "t1.item_id AS itemId, " +
        "t1.item_name ->> :languages AS itemName, " +
        "t1.item_price AS itemPrice, " +
        "t1.item_Info ->> :languages AS itemInfo, " +
        "t1.option_flag AS optionFlag, " +
        "t2.category_id AS itemCategoryId " +
        "FROM m_item t1 " +
        "INNER JOIN r_category_item t2 " +
        "ON t1.store_id = t2.store_id " +
        "AND t1.item_id = t2.item_id " +
        "WHERE t1.store_id = :storeId " +
        "AND t1.item_type IN :idList " +
        "AND t1.item_name ->> :languages SIMILAR TO :searchInfo " +
        "AND t1.del_flag = 0 " +
        "AND t2.del_flag = 0) tt1 " +
        "LEFT JOIN m_unit tt2 " +
        "ON tt1.store_id = tt2.store_id " +
        "AND tt1.unit_id = tt2.unit_id " +
        "AND tt2.del_flag = 0 " +
        "LEFT JOIN m_sell_off tt3 " +
        "ON tt1.store_id = tt3.store_id " +
        "AND tt1.itemId = tt3.item_id " +
        "AND tt3.sell_off_start <= :sellOffStart " +
        "AND tt3.del_flag = 0 " +
        "LEFT JOIN m_item_image tt4 " +
        "ON tt1.store_id = tt4.store_id " +
        "AND tt1.itemId = tt4.item_id " +
        "AND tt4.del_flag = 0) tts " +
        "WHERE tts.rownum = 1 " +
        "ORDER BY tts.sortOrder DESC, tts.itemCategoryId ASC, tts.itemId ASC ", nativeQuery = true)
    List<Map<String, Object>> getItemBySpeechWithSql(@Param("storeId") String storeId,
        @Param("languages") String languages, @Param("searchInfo") String searchInfo,
        @Param("sellOffStart") ZonedDateTime sellOffStart, @Param("idList") List<String> idList,
        @Param("receivablesId") String receivablesId, @Param("itemType") String itemType);


    /**
     * 同義語情報取得.
     *
     * @param storeId    店舗ID
     * @param searchInfo 検索内容
     * @return 商品情報
     */
    @Query(value = "SELECT t1.synonyms_key "
        + "FROM m_synonyms t1 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.synonyms_key SIMILAR TO :searchInfo "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    List<String> getSynonyms(@Param("storeId") String storeId,
        @Param("searchInfo") String searchInfo);

    /**
     * 商品オプションフラグ変更.
     *
     * @param storeId              店舗ID
     * @param deleteBeforeItemList 削除前商品IDリスト
     * @param deleteAfterItemList  削除後商品IDリスト
     * @param updOperCd            更新者
     * @param updDateTime          更新日時
     */
    @Modifying
    @Query(value = "update m_item "
        + "set option_flag = 0, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and store_id = :storeId "
        + "and item_id in :deleteBeforeItemList "
        + "and item_id not in :deleteAfterItemList ", nativeQuery = true)
    void updateItemOptionFlag(@Param("storeId") String storeId,
        @Param("deleteBeforeItemList") List<Integer> deleteBeforeItemList,
        @Param("deleteAfterItemList") List<Integer> deleteAfterItemList,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * サブカテゴリー数量取得.
     *
     * @param storeId    店舗ID
     * @param unitIdList 単位IDリスト
     * @return 数量
     */
    @Query(value = "SELECT count(*) " +
        "FROM m_item mi " +
        "where mi.store_id = :storeId " +
        "and mi.unit_id in :unitIdList " +
        "and mi.del_flag = 0 ", nativeQuery = true)
    Integer getItemCount(@Param("storeId") String storeId,
        @Param("unitIdList") List<Integer> unitIdList);

    /**
     * チェック注文商品.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param idList        商品区分リスト
     * @return 数量
     */
    @Query(value = "SELECT count(*) " +
        "FROM o_order_summary oos, o_order oo, o_order_details ood, m_item mi " +
        "where oos.store_id = oo.store_id " +
        "and oos.order_summary_id = oo.order_summary_id " +
        "and oo.store_id = ood.store_id " +
        "and oo.order_id = ood.order_id " +
        "and ood.store_id = mi.store_id " +
        "and ood.item_id = mi.item_id " +
        "and oos.store_id = :storeId " +
        "and oos.receivables_id = :receivablesId " +
        "and mi.item_type in :idList " +
        "and oos.del_flag = 0 " +
        "and oo.del_flag = 0 " +
        "and ood.del_flag = 0 " +
        "and mi.del_flag = 0 ", nativeQuery = true)
    Integer getOrderItemNumber(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("idList") List<String> idList);
}
