package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.OOrderDetails;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.common.shared.model.RgOrderSummaryDto;
import com.cnc.qr.common.shared.model.SlipDto;
import com.cnc.qr.core.order.model.ItemDetailDto;
import com.cnc.qr.core.order.model.TableOrderDto;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 注文サマリテーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface OOrderSummaryRepository extends JpaRepository<OOrderSummary, Long> {

    /**
     * 注文情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param languages     言語
     * @return 注文情報
     */
    @Query(value = "SELECT t1.order_summary_id AS orderSummaryId, "
        + "t1.customer_count AS customerCount, "
        + "t1.order_amount AS orderAmount, "
        + "t1.takeout_flag AS takeoutFlag, "
        + "t2.pay_status AS payStatus, "
        + "t2.foreign_tax AS foreignTax, "
        + "t3.order_id AS orderId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.item_id AS itemId, "
        + "t5.item_name ->> :languages AS itemName, "
        + "t3.item_price AS itemPrice, "
        + "t3.item_count AS itemCount, "
        + "t4.diff_price * COALESCE(t4.item_option_count, 1) AS diffPrice, "
        + "t4.item_option_count AS optionItemCount, "
        + "t6.option_name ->> :languages AS optionName, "
        + "t7.classification AS classification, "
        + "t3.item_classification AS itemClassification, "
        + "t1.payment_type AS paymentType, "
        + "t8.table_name AS tableName, "
        + "TO_CHAR( t9.reception_no, 'FM0000') AS receptionNo "
        + "FROM o_order_summary t1 "
        + "LEFT JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "AND t2.del_flag = 0 "
        + "LEFT JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "AND t3.del_flag = 0 "
        + "LEFT JOIN o_order_details_option t4 "
        + "ON t3.store_id = t4.store_id "
        + "AND t3.order_detail_id = t4.order_detail_id "
        + "AND t4.del_flag = 0 "
        + "LEFT JOIN m_item t5 "
        + "ON t3.store_id = t5.store_id "
        + "AND t3.item_id = t5.item_id "
        + "AND t5.del_flag = 0 "
        + "LEFT JOIN m_option t6 "
        + "ON t4.store_id = t6.store_id "
        + "AND t4.item_option_type_code = t6.option_type_cd "
        + "AND t4.item_option_code = t6.option_code "
        + "AND t6.del_flag = 0 "
        + "LEFT JOIN m_option_type t7 "
        + "ON t6.store_id = t7.store_id "
        + "AND t6.option_type_cd = t7.option_type_cd "
        + "AND t7.del_flag = 0 "

        + "LEFT JOIN m_table t8 "
        + "ON t8.store_id = t1.store_id "
        + "AND t8.table_id = t1.table_id "
        + "AND t8.del_flag = 0 "

        + "LEFT JOIN o_receivables t9 "
        + "ON t9.store_id = t1.store_id "
        + "AND t9.receivables_id = t1.receivables_id "
        + "AND t9.del_flag = 0 "

        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 "
        + "ORDER BY t3.item_id ASC, t4.item_option_type_code ASC, "
        + "t4.item_option_code ASC", nativeQuery = true)
    List<Map<String, Object>> findOrderInfoByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("languages") String languages);


    /**
     * 注文サマリ取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 注文サマリID
     */
    @Query(value = "select order_summary_id "
        + "from o_order_summary "
        + "where receivables_id = :receivablesId "
        + "and del_flag = 0 "
        + "and store_id = :storeId "
        + "for update nowait", nativeQuery = true)
    String findByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 注文サマリ更新処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderAmount   注文金額
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "update OOrderSummary "
        + "set orderAmount = orderAmount + :orderAmount, "
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "where delFlag = 0 "
        + "and receivablesId = :receivablesId "
        + "and storeId = :storeId ")
    void updateByPaymentAmountAndUpdDateTimeAndUpdOperCd(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 支払い金額更新処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    支払金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrderSummary "
        + "SET paymentAmount = paymentAmount + :orderAmount,"
        + "updOperCd = :updOperCd,"
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND delFlag = 0")
    void updatePaymentAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文金額回復処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    注文金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrderSummary "
        + "SET orderAmount = orderAmount - :orderAmount,"
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1  "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId ")
    void updateOrderAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);


    /**
     * 店員用スマホ商品変更すると小計金額変更処理
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    注文金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrderSummary "
        + "SET orderAmount = :orderAmount,"
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1  "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId ")
    void updateOrderChangeAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);


    /**
     * 注文サマリ回復処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param delFlag        削除フラグ
     */
    void deleteByStoreIdAndOrderSummaryIdAndDelFlag(String storeId, String orderSummaryId,
        Integer delFlag);


    /**
     * キッチンと客様用伝票データ取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderIds       注文IDリスト
     * @param orderDetailId  注文明細ID
     * @return キッチンと客様用伝票データ
     */
    @Query(value = "SELECT new com.cnc.qr.common.shared.model.SlipDto(" +
        "t1.orderSummaryId, " +
        "t1.orderAmount," +
        "t1.customerCount," +
        "t1.takeoutFlag," +
        "t1.priceDiscountAmount," +
        "t1.priceDiscountRate," +
        "t1.paymentAmount," +
        "t2.orderId," +
        "t2.comment," +
        "t2.foreignTax," +
        "t3.orderDetailId," +
        "t3.itemId," +
        "t3.itemPrice," +
        "t3.itemCount," +
        "t3.itemClassification," +
        "t3.returnOrderDetailId," +
        "t3.itemReturnId," +
        "t5.optionTypeName," +
        "t4.itemOptionTypeCode," +
        "t4.itemOptionCode," +
        "t4.itemOptionCount," +
        "t6.optionName," +
        "t7.itemName," +
        "t7.itemType," +
        "t7.optionFlag," +
        "t8.kitchenName," +
        "t10.printIp," +
        "t10.bluetoothName," +
        "t10.brandCode," +
        "t10.connectionMethodCode," +
        "t10.printSize," +
        "t11.tableName, " +
        "t5.classification, " +
        "t15.taxCode, " +
        "t15.taxReliefApplyType, " +
        "t12.receptionNo ) " +
        "from OOrderSummary t1 " +
        "INNER JOIN OOrder t2 " +
        "ON t2.orderSummaryId = t1.orderSummaryId " +
        "AND t2.storeId = t1.storeId " +
        "AND (t2.orderId in( :orderIds) OR :orderIds is null)" +
        "AND t2.delFlag = 0 " +
        "INNER JOIN OOrderDetails t3 " +
        "ON t3.storeId = t2.storeId " +
        "AND t3.orderId = t2.orderId " +
        "AND t3.delFlag = 0 " +
        "AND (t3.orderDetailId =  :orderDetailId or :orderDetailId is null) " +
        "INNER JOIN MItem t7 " +
        "ON t7.itemId = t3.itemId " +
        "AND t7.storeId = t1.storeId " +
        "AND t7.delFlag = 0 " +
        "INNER JOIN MStore t13 " +
        "ON t13.storeId = t7.storeId " +
        "AND t13.delFlag = 0 " +
        "INNER JOIN MBusiness t14 " +
        "ON t14.businessId = t13.businessId " +
        "AND t14.delFlag = 0 " +
        "INNER JOIN MTax t15 " +
        "ON t15.businessId = t14.businessId " +
        "AND t15.taxId = t7.taxId " +
        "AND t15.delFlag = 0 " +
        "INNER JOIN MKitchen t8 " +
        "ON t8.kitchenId = t7.kitchenId " +
        "AND t8.storeId = t7.storeId " +
        "AND t8.delFlag = 0 " +
        "INNER JOIN RKitchenPrint t9 " +
        "ON t9.kitchenId = t8.kitchenId " +
        "AND t9.storeId = t8.storeId " +
        "AND t9.delFlag = 0 " +
        "INNER JOIN MPrint t10 " +
        "ON t10.printId = t9.printId " +
        "AND t10.storeId = t9.storeId " +
        "AND t10.delFlag = 0 " +
        "LEFT JOIN MTable t11 " +
        "ON t11.tableId = t1.tableId " +
        "AND t11.storeId = t1.storeId " +
        "AND t11.delFlag = 0 " +
        "INNER JOIN OReceivables t12 " +
        "ON t12.receivablesId = t1.receivablesId " +
        "AND t12.storeId = t1.storeId " +
        "AND t12.delFlag = 0 " +
        "LEFT JOIN OOrderDetailsOption t4 " +
        "ON t4.orderDetailId = t3.orderDetailId " +
        "AND t4.storeId = t3.storeId " +
        "AND t4.delFlag = 0 " +
        "LEFT JOIN MOptionType t5 " +
        "ON t5.optionTypeCd = t4.itemOptionTypeCode " +
        "AND t5.storeId = t4.storeId " +
        "AND t5.delFlag = 0 " +
        "LEFT JOIN MOption t6 " +
        "ON t6.optionTypeCd = t4.itemOptionTypeCode " +
        "AND t6.optionCode = t4.itemOptionCode " +
        "AND t6.storeId = t4.storeId " +
        "AND t6.delFlag = 0 " +
        "WHERE t1.orderSummaryId = :orderSummaryId " +
        "AND t1.storeId = :storeId " +
        "AND t1.delFlag = 0 "
    )
    List<SlipDto> findByOrderSummaryIdAndByStoreId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderIds") List<Integer> orderIds, @Param("orderDetailId") Integer orderDetailId);


    /**
     * 受付IDにより注文サマリIDと注文ID取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 注文情報
     */
    @Query(value = "SELECT " +
        "t1.order_summary_id as orderSummaryId, " +
        "t2.order_id AS orderId, " +
        "t3.item_status AS itemStatus " +
        "from o_order_summary t1 " +
        "inner JOIN o_order t2 " +
        "ON t2.order_summary_id = t1.order_summary_id " +
        "AND t2.store_id = t1.store_id " +
        "AND t2.del_flag = 0 " +

        "inner JOIN o_order_details t3 " +
        "ON t3.order_id = t2.order_id " +
        "AND t3.store_id = t2.store_id " +
        "AND t3.del_flag = 0 " +

        "WHERE t1.receivables_id = :receivablesId " +
        "AND t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findByOrderSummaryIdOrderId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);


    /**
     * キッチンと客様用伝票データ取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderIds       注文IDリスト
     * @return キッチンと客様用伝票データ
     */
    @Query(value = "SELECT " +
        "t3.itemId," +                                 // t3.商品ID
        "t3.itemPrice," +                                 // t3.単価
        "t3.itemPrice " +                                 // t3.単価
        "from OOrderSummary t1 " +                          // 注文サマリテーブル
        "LEFT JOIN OOrder t2 " +                            // 注文テーブル
        "ON t2.orderSummaryId = t1.orderSummaryId " +       // t2.注文サマリID = t1.注文サマリID
        "AND t2.storeId = t1.storeId " +                    // t2.店舗ID = t1.店舗ID
        "AND t2.orderId in( :orderIds) " +                      // t2.注文ID = #{注文ID}
        "AND t2.delFlag = 0 " +                      // t2.削除フラグ = 0
        "LEFT JOIN OOrderDetails t3 " +                     // 注文明細テーブル
        "ON t3.storeId = t2.storeId " +                     // t3.店舗ID = t2.店舗ID
        "AND t3.orderId = t2.orderId " +                    // t3.注文ID = t2.注文ID
        "AND t3.delFlag = 0 " +                      // t3.削除フラグ = 0
        "WHERE t2.orderSummaryId = :orderSummaryId " +               // t1.注文サマリID = #{注文サマリID}
        "AND t1.storeId = :storeId " +                       // t1.店舗ID = #{店舗ID}
        "AND t1.delFlag = 0 "                         // t1.削除フラグ = 0
    )
    List<OOrderDetails> findByOrderSummaryIdAndByStoreIdAndByItem(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") List<Integer> orderIds);

    /**
     * 注文商品詳細情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderId       注文ID
     * @param orderDetailId 注文明細ID
     * @param itemId        商品ID
     * @return 注文商品詳細情報
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.ItemDetailDto(t5.itemName, "
        + "t3.itemCount, "
        + "t5.itemInfo, "
        + "t4.itemOptionTypeCode, "
        + "t4.itemOptionCode, "
        + "t4.itemOptionCount, "
        + "t5.optionFlag) "
        + "FROM OOrderSummary t1 "
        + "INNER JOIN OOrder t2 "
        + "ON t1.storeId = t2.storeId "
        + "AND t1.orderSummaryId = t2.orderSummaryId "
        + "INNER JOIN OOrderDetails t3 "
        + "ON t2.storeId = t3.storeId "
        + "AND t2.orderId = t3.orderId "
        + "LEFT JOIN OOrderDetailsOption t4 "
        + "ON t3.storeId = t4.storeId "
        + "AND (t3.orderDetailId = t4.orderDetailId OR t3.returnOrderDetailId = t4.orderDetailId) "
        + "AND t4.delFlag = 0 "
        + "LEFT JOIN MItem t5 "
        + "ON t3.storeId = t5.storeId "
        + "AND t3.itemId = t5.itemId "
        + "AND t5.delFlag = 0 "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.receivablesId = :receivablesId "
        + "AND t2.orderId = :orderId "
        + "AND t3.orderDetailId = :orderDetailId "
        + "AND t3.itemId = :itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "AND t3.delFlag = 0 ")
    List<ItemDetailDto> findOrderItemDetailByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("orderId") Integer orderId, @Param("orderDetailId") Integer orderDetailId,
        @Param("itemId") Integer itemId);

    /**
     * 注文商品返品数量情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param orderId       注文ID
     * @param orderDetailId 注文明細ID
     * @param itemId        商品ID
     * @return 返品数量
     */
    @Query(value = "SELECT SUM(t3.itemCount) AS itemCount "
        + "FROM OOrderSummary t1 "
        + "INNER JOIN OOrder t2 "
        + "ON t1.orderSummaryId = t2.orderSummaryId "
        + "AND t1.storeId = t2.storeId "
        + "INNER JOIN OOrderDetails t3 "
        + "ON t2.storeId = t3.storeId "
        + "AND t2.orderId = t3.orderId "
        + "WHERE t1.storeId = :storeId "
        + "AND t1.receivablesId = :receivablesId "
        + "AND t3.orderId = :orderId "
        + "AND t3.returnOrderDetailId = :orderDetailId "
        + "AND t3.itemId = :itemId "
        + "AND t1.delFlag = 0 "
        + "AND t2.delFlag = 0 "
        + "AND t3.delFlag = 0")
    Integer findOrderItemReturnCountByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("orderId") Integer orderId, @Param("orderDetailId") Integer orderDetailId,
        @Param("itemId") Integer itemId);

    /**
     * 注文サマリーデータロック.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param delFlag       削除フラグ
     * @return 返品数量
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    OOrderSummary findByStoreIdAndReceivablesIdAndDelFlag(String storeId, String receivablesId,
        Integer delFlag);

    /**
     * 座席変更処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param tableId       テーブルID
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderSummary "
        + "SET tableId = :tableId, "
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE delFlag = 0 "
        + "AND receivablesId = :receivablesId "
        + "AND storeId = :storeId ")
    void updateTableIdByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("tableId") Integer tableId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文一覧情報取得.
     *
     * @param storeId        店舗ID
     * @param receivablesNo  受付No
     * @param orderDateStart 開始日付
     * @param orderDateEnd   終了日付
     * @param payStatus      支払状態
     * @param itemStatus     商品状態
     * @param takeoutFlag    飲食区分
     * @param pageable       ページ情報
     * @return 注文一覧情報
     */
    @Query(value = "SELECT t1.order_summary_id AS orderSummaryId, "
        + "TO_CHAR(MAX(t2.order_time), 'YYYY-MM-DD HH24:MI:SS') AS orderDateTime, "
        + "MAX(t3.reception_no) AS receivablesNo, "
        + "MAX(t1.receivables_id) AS receivablesId, "
        + "MAX(t4.table_name) AS tableName, "
        + "MAX(t1.order_amount) AS orderSubtotal, "
        + "SUM(t2.foreign_tax) AS orderSototaxAmount, "
        + "MAX(t1.order_amount) + SUM(t2.foreign_tax) AS orderTotalAmount, "
        + "MAX(t1.price_discount_amount) AS orderDiscountAmount, "
        + "MAX(t1.price_discount_rate) AS orderDiscountRate, "
        + "MAX(t1.payment_amount) AS paymentAmount, "
        + "MIN(t2.item_status) AS itemStatus, "
        + "MAX(t2.pay_status) AS orderStatus, "
        + "MAX(t1.upd_oper_cd) AS operator, "
        + "MAX(t1.takeout_flag) AS takeoutFlag, "
        + "MAX(t1.table_id) AS tableId, "
        + "MAX(t1.payment_type) AS paymentType "
        + "FROM o_order_summary t1 "
        + "INNER JOIN ("
        + "  SELECT t11.store_id,"
        + "    t11.order_summary_id,"
        + "    t11.order_id,"
        + "    MAX(t11.order_time) AS order_time,"
        + "    MAX(t11.foreign_tax) AS foreign_tax, "
        + "    MAX(t11.pay_status) AS pay_status,"
        + "    MAX(t12.item_status) AS item_status"
        + "  FROM o_order t11, o_order_details t12"
        + "  WHERE t11.store_id = t12.store_id"
        + "  AND t11.order_id = t12.order_id"
        + "  AND t11.store_id = :storeId"
        + "  AND t12.item_classification = '0'"
        + "  AND t11.del_flag = 0"
        + "  AND t12.del_flag = 0"
        + "  AND (t11.pay_status = :payStatus OR :payStatus = '')"
        + "  AND (t12.item_status = :itemStatus OR :itemStatus = '')"
        + "  AND (TO_TIMESTAMP(:orderDateStart, 'YYYY-MM-DD') <= t11.order_time OR :orderDateStart = '')"
        + "  AND (t11.order_time <= TO_TIMESTAMP(:orderDateEnd, 'YYYY-MM-DD HH24:MI:SS') OR :orderDateEnd = '')"
        + "  GROUP BY t11.store_id, t11.order_summary_id, t11.order_id) t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "INNER JOIN o_receivables t3 "
        + "ON t1.store_id = t3.store_id "
        + "AND t1.receivables_id = t3.receivables_id "
        + "LEFT JOIN m_table t4 "
        + "ON t1.store_id = t4.store_id "
        + "AND t1.table_id = t4.table_id "
        + "AND t4.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND (t3.reception_no = :receivablesNo OR :receivablesNo = 0) "
        + "AND (t1.takeout_flag = :takeoutFlag OR :takeoutFlag = '2') "
        + "AND t1.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "GROUP BY t1.store_id, t1.order_summary_id "
        + "ORDER BY orderDateTime DESC", nativeQuery = true)
    Page<Map<String, Object>> findOrderInfoByStoreId(
        @Param("storeId") String storeId, @Param("receivablesNo") Integer receivablesNo,
        @Param("orderDateStart") String orderDateStart, @Param("orderDateEnd") String orderDateEnd,
        @Param("payStatus") String payStatus, @Param("itemStatus") String itemStatus,
        @Param("takeoutFlag") String takeoutFlag, Pageable pageable);

    /**
     * 注文商品情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文商品情報
     */
    @Query(value = "SELECT t1.takeout_flag AS takeoutFlag, "
        + "t3.item_id AS itemId,"
        + "t3.item_price AS itemPrice,"
        + "t3.item_count AS itemCount, "
        + "t3.item_classification AS itemClassification "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t2.order_summary_id = t1.order_summary_id "
        + "AND t2.store_id = t1.store_id "
        + "INNER JOIN o_order_details t3 "
        + "ON t3.store_id = t2.store_id "
        + "AND t3.order_id = t2.order_id "
        + "WHERE t2.order_summary_id = :orderSummaryId "
        + "AND t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findOrderItemsByOrderSummaryId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 支払情報を削除する.
     *
     * @param storeId             店舗ID
     * @param receivablesId       受付ID
     * @param priceDiscountAmount 值引额
     * @param priceDiscountRate   值引率
     * @param paymentAmount       支払い金額
     * @param takeoutFlag         就餐区分
     * @param updOperCd           更新者
     * @param updDateTime         更新日時
     */
    @Modifying
    @Query(value = "update OOrderSummary "
        + "set priceDiscountAmount = :priceDiscountAmount, "
        + "priceDiscountRate = :priceDiscountRate, "
        + "paymentAmount = paymentAmount + :paymentAmount, "
        + "takeoutFlag = :takeoutFlag, "
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "where delFlag = 0 "
        + "and receivablesId = :receivablesId "
        + "and storeId = :storeId ")
    void updatePayment(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("priceDiscountAmount") BigDecimal priceDiscountAmount,
        @Param("priceDiscountRate") BigDecimal priceDiscountRate,
        @Param("paymentAmount") BigDecimal paymentAmount,
        @Param("takeoutFlag") String takeoutFlag,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 人数変更処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param customerCount 人数
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderSummary "
        + "SET customerCount = :customerCount, "
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE delFlag = 0 "
        + "AND receivablesId = :receivablesId "
        + "AND storeId = :storeId ")
    void updateCustomerCount(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("customerCount") Integer customerCount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文商品リスト取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 注文サマリID
     * @param languages     言語
     * @return 注文商品リスト
     */
    @Query(value = "SELECT t1.customer_count AS customerCount, "
        + "t1.order_amount AS orderAmount, "
        + "t3.order_id AS orderId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.item_id AS itemId, "
        + "t5.item_name ->> :languages AS itemName, "
        + "t3.item_price AS itemPrice, "
        + "t3.item_count AS itemCount, "
        + "t4.diff_price * COALESCE(t4.item_option_count, 1) AS diffPrice, "
        + "t4.item_option_count AS optionItemCount, "
        + "t6.option_name ->> :languages AS optionName, "
        + "t7.classification AS classification, "
        + "t3.item_classification AS itemClassification, "
        + "t3.item_status AS itemSureStatus, "
        + "t3.return_order_detail_id AS returnOrderDetailId "
        + "FROM o_order_summary t1 "
        + "LEFT JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "AND t2.del_flag = 0 "
        + "LEFT JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "AND t3.del_flag = 0 "
        + "LEFT JOIN o_order_details_option t4 "
        + "ON t3.store_id = t4.store_id "
        + "AND (t3.order_detail_id = t4.order_detail_id OR t3.return_order_detail_id = t4.order_detail_id) "
        + "AND t4.del_flag = 0 "
        + "LEFT JOIN m_item t5 "
        + "ON t3.store_id = t5.store_id "
        + "AND t3.item_id = t5.item_id "
        + "AND t5.del_flag = 0 "
        + "LEFT JOIN m_option t6 "
        + "ON t4.store_id = t6.store_id "
        + "AND t4.item_option_type_code = t6.option_type_cd "
        + "AND t4.item_option_code = t6.option_code "
        + "AND t6.del_flag = 0 "
        + "LEFT JOIN m_option_type t7 "
        + "ON t6.store_id = t7.store_id "
        + "AND t6.option_type_cd = t7.option_type_cd "
        + "AND t7.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 "
        + "ORDER BY orderId ASC, orderDetailId ASC", nativeQuery = true)
    List<Map<String, Object>> findOrderItemListByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("languages") String languages);

    /**
     * 注文明細ID取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 注文サマリID
     * @return 注文情報
     */
    @Query(value = "SELECT t3.order_detail_id AS returnOrderDetailId, "
        + "t3.item_price AS itemPrice, "
        + "t3.item_classification AS itemClassification, "
        + "t3.item_status AS itemStatus "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t2.store_id = t1.store_id "
        + "AND t2.order_summary_id = t1.order_summary_id "
        + "INNER JOIN o_order_details t3 "
        + "ON t3.store_id = t2.store_id "
        + "AND t3.order_id = t2.order_id "
        + "where store_id = :storeId "
        + "and receivables_id = :receivablesId "
        + "and del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findOrderDetailIdByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 注文リスト取得処理.
     *
     * @param storeId     店舗ID
     * @param tableId     テーブルID
     * @param orderStatus 注文状態
     * @param payStatus   支払状態
     * @return 注文リスト
     */
    @Query(value = "SELECT new com.cnc.qr.core.order.model.TableOrderDto (" +
        "t1.receivablesId, " +
        "t2.receptionNo, " +
        "t1.customerCount, " +
        "t1.orderAmount, " +
        "t1.updDateTime, " +
        "t3.orderType, " +  // 注文区分 01：叫起订单 02：手机订单 03：pad订单 04：前台订单
        "t3.orderId, " +
        "t4.itemStatus ) " +    // 商品状態 01:未確認、02：確認済
        "FROM OOrderSummary t1 " +
        "INNER JOIN OReceivables t2 " +
        "ON t2.receivablesId = t1.receivablesId " +
        "AND t2.storeId = t1.storeId " +
        "AND t2.delFlag = 0 " +
        "INNER JOIN  OOrder t3 " +
        "ON t3.orderSummaryId = t1.orderSummaryId " +
        "AND t3.storeId = t1.storeId " +
        "AND t3.payStatus = :payStatus " +
        "AND t3.delFlag = 0 " +
        "INNER JOIN  OOrderDetails t4 " +
        "ON t4.orderId = t3.orderId " +
        "AND t4.storeId = t3.storeId " +
        "AND t4.delFlag = 0 " +
        "WHERE  t1.storeId = :storeId " +
        "AND t1.tableId = :tableId " +
        "AND t1.orderStatus = :orderStatus " +
        "AND t1.delFlag = 0 ")
    List<TableOrderDto> findByTableId(@Param("storeId") String storeId,
        @Param("tableId") Integer tableId,
        @Param("orderStatus") String orderStatus,
        @Param("payStatus") String payStatus);


    /**
     * 注文リスト取得処理.
     *
     * @param storeId     店舗ID
     * @param tableId     テーブルID
     * @param orderStatus 注文状態
     * @return 注文リスト
     */
    @Query(value = "SELECT " +
        "t1.receivables_id, " +
        "t2.reception_no, " +
        "t1.customer_count, " +
        "t1.upd_date_time, " +
        "t1.order_amount, '01' as orderType, null as orderId, null as itemStatus " +
        "FROM o_order_summary t1 " +
        "INNER JOIN o_receivables t2 " +
        "ON t2.receivables_id = t1.receivables_id " +
        "AND t2.store_id = t1.store_id " +
        "AND t2.del_flag = 0 " +
        "WHERE  t1.store_id = :storeId " +
        "AND t1.table_id = :tableId " +
        "AND t1.order_amount = 0 " +
        "AND t1.order_status = :orderStatus " +
        "AND NOT EXISTS( " +
        "SELECT ttt2.order_summary_id " +
        "FROM o_order ttt2 " +
        "WHERE ttt2.order_summary_id = t1.order_summary_id AND ttt2.store_id = :storeId  " +
        ")  " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findByTableIdNotOrder(@Param("storeId") String storeId,
        @Param("tableId") Integer tableId,
        @Param("orderStatus") String orderStatus);

    /**
     * 清空折扣数据.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order_summary "
        + "SET price_discount_amount = null,"
        + "price_discount_rate = null,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND payment_amount = 0 "
        + "AND del_flag = 0", nativeQuery = true)
    void clearDiscount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定注文サマリーIDデータロック.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリーID
     * @param delFlag        削除フラグ
     * @return 注文リスト
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    OOrderSummary findByStoreIdAndOrderSummaryIdAndDelFlag(String storeId, String orderSummaryId,
        Integer delFlag);


    /**
     * 注文サマリ情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリーID
     * @return 注文サマリ情報
     */
    @Query(value = "SELECT  t1 " +
        "FROM OOrderSummary t1 " +
        "WHERE  t1.storeId = :storeId " +
        "AND t1.orderSummaryId = :orderSummaryId " +
        "AND t1.delFlag = 0 ")
    OOrderSummary findSummaryInfo(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 注文サマリ情報.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 注文サマリ情報
     */
    @Query(value = "SELECT  t1 " +
        "FROM OOrderSummary t1 " +
        "WHERE  t1.storeId = :storeId " +
        "AND t1.receivablesId = :receivablesId " +
        "AND t1.delFlag = 0 ")
    OOrderSummary findSummaryInfoByreceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 配席.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param tableId        テーブルID
     * @param orderStatus    注文状態
     * @param seatRelease    配席フラグ
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order_summary "
        + "SET table_id = :tableId,"
        + "order_status = :orderStatus,"
        + "seat_release = :seatRelease,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND del_flag = 0", nativeQuery = true)
    void updateTable(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("tableId") Integer tableId,
        @Param("orderStatus") String orderStatus,
        @Param("seatRelease") String seatRelease,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文サマリデータ削除.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order_summary "
        + "SET del_flag = 1,"
        + "version = version + 1,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND receivables_id = :receivablesId "
        + "AND del_flag = 0", nativeQuery = true)
    void updateOrderSummary(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文商品取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文商品情報
     */
    @Query(value = "select d.item_id, "
        + "(d.item_price * d.item_count) as item_price, "
        + "from o_order_summary s,"
        + "o_order o,"
        + "o_order_details d "
        + "where s.store_id = o.store_id "
        + "and s.order_summary_id = o.order_summary_id "
        + "and o.store_id = d.store_id "
        + "and o.order_id = d.order_id "
        + "and s.del_flag = 0 "
        + "and o.del_flag = 0 "
        + "and d.del_flag = 0 "
        + "and s.store_id = :storeId "
        + "and s.order_summary_id = :orderSummaryId", nativeQuery = true)
    List<Map<String, Object>> getItemList(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 注文サマリ取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 注文サマリ情報
     */
    @Query(value = "select s "
        + "from OOrderSummary s "
        + "where s.storeId = :storeId "
        + "and s.receivablesId = :receivablesId")
    OOrderSummary getOrderSummary(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 支払い金額更新処理(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrderSummary "
        + "SET updOperCd = :updOperCd,"
        + "updDateTime = :updDateTime, "
        + "delFlag = 0, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND delFlag = 1 ")
    Integer updatePaymentAmountWithDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定注文サマリーIDデータロック(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 注文サマリ情報
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    OOrderSummary findByStoreIdAndOrderSummaryId(String storeId, String orderSummaryId);

    /**
     * 支払い金額更新処理(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    支払金額
     * @param orderId        注文ID
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order_summary "
        + "SET payment_amount = payment_amount + :orderAmount,"
        + "order_amount = order_amount + :orderAmount - ("
        + "SELECT foreign_tax FROM o_order WHERE store_id = :storeId AND order_id = :orderId), "
        + "version = version + 1,"
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND del_flag = 0", nativeQuery = true)
    void updatePaymentAmountWithDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("orderId") Integer orderId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文金額更新処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderAmount    注文金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order_summary "
        + "SET order_amount = order_amount - :orderAmount, "
        + "version = version + 1,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND del_flag = 0", nativeQuery = true)
    void updateOrderAmountWithDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * テイクアウトフラグ取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return テイクアウトフラグ
     */
    @Query(value = "select s.takeout_flag "
        + "from o_order_summary s "
        + "where s.store_id = :storeId "
        + "and s.order_summary_id = :orderSummaryId", nativeQuery = true)
    String getTakeOutFlagByStoreIdAndOrderSummaryId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

    /**
     * 注文一覧情報取得.
     *
     * @param storeId     店舗ID
     * @param payStatus   支払状態
     * @param itemStatus  商品状態
     * @param takeoutFlag 飲食区分
     * @param tableId     テーブルID
     * @return 注文一覧情報
     */
    @Query(value = "SELECT "
        + "TO_CHAR(MAX(t2.order_time), 'YYYY-MM-DD HH24:MI:SS') AS orderTime, "
        + "TO_CHAR(MAX(t3.reception_no),'FM0000') AS receivablesNo, "
        + "MAX(t1.receivables_id) AS receivablesId, "
        + "MAX(t4.table_name) AS tableName, "
        + "MAX(t1.order_amount) AS orderAmount "
        + "FROM o_order_summary t1 "
        + "INNER JOIN ("
        + "  SELECT t11.store_id,"
        + "    t11.order_summary_id,"
        + "    t11.order_id,"
        + "    MAX(t11.order_time) AS order_time"
        + "  FROM o_order t11, o_order_details t12"
        + "  WHERE t11.store_id = t12.store_id"
        + "  AND t11.order_id = t12.order_id"
        + "  AND t11.store_id = :storeId"
        + "  AND t12.item_classification = '0'"
        + "  AND t11.del_flag = 0"
        + "  AND t11.pay_status = :payStatus "
        + "  AND t12.del_flag = 0"
        + "  AND (t12.item_status = :itemStatus OR :itemStatus = '')"
        + "  GROUP BY t11.store_id, t11.order_summary_id, t11.order_id) t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "INNER JOIN o_receivables t3 "
        + "ON t1.store_id = t3.store_id "
        + "AND t1.receivables_id = t3.receivables_id "
        + "LEFT JOIN m_table t4 "
        + "ON t1.store_id = t4.store_id "
        + "AND t1.table_id = t4.table_id "
        + "AND t4.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND (t1.table_id = :tableId OR :tableId = 0) "
        + "AND (t1.takeout_flag = :takeoutFlag OR :takeoutFlag = '') "
        + "AND t1.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "GROUP BY t1.store_id, t1.order_summary_id "
        + "ORDER BY orderTime DESC", nativeQuery = true)
    List<Map<String, Object>> findUnCfmOrderInfoByStoreId(
        @Param("storeId") String storeId, @Param("itemStatus") String itemStatus,
        @Param("takeoutFlag") String takeoutFlag, @Param("tableId") Integer tableId,
        @Param("payStatus") String payStatus);

    /**
     * 注文一覧情報取得.
     *
     * @param storeId             店舗ID
     * @param itemStatusUnConfirm 商品状態
     * @param itemStatusConfirmed 商品状態
     * @param takeoutFlag         飲食区分
     * @param tableId             テーブルID
     * @param payStatus           支払状態
     * @return 注文一覧情報
     */
    @Query(value = "SELECT "
        + "TO_CHAR(MAX(t2.order_time), 'YYYY-MM-DD HH24:MI:SS') AS orderTime, "
        + "TO_CHAR(MAX(t3.reception_no),'FM0000') AS receivablesNo, "
        + "MAX(t1.receivables_id) AS receivablesId, "
        + "MAX(t4.table_name) AS tableName, "
        + "MAX(t1.order_amount) AS orderAmount "
        + "FROM o_order_summary t1 "
        + "INNER JOIN ("
        + "  SELECT t11.store_id,"
        + "    t11.order_summary_id,"
        + "    t11.order_id,"
        + "    MAX(t11.order_time) AS order_time"
        + "  FROM o_order t11, o_order_details t12"
        + "  WHERE t11.store_id = t12.store_id"
        + "  AND t11.order_id = t12.order_id"
        + "  AND t11.store_id = :storeId"
        + "  AND t12.item_classification = '0'"
        + "  AND t11.del_flag = 0"
        + "  AND t11.pay_status = :payStatus "
        + "  AND t12.del_flag = 0"
        + "  AND t12.item_status = :itemStatusConfirmed"
        + "  GROUP BY t11.store_id, t11.order_summary_id, t11.order_id) t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "INNER JOIN o_receivables t3 "
        + "ON t1.store_id = t3.store_id "
        + "AND t1.receivables_id = t3.receivables_id "
        + "INNER JOIN ("
        + "  select t21.order_summary_id,"
        + "    t21.store_id,"
        + "    count(t23.order_detail_id) as num"
        + "  FROM o_order_Summary t21"
        + "  INNER JOIN o_order t22"
        + "  on t21.store_id = t22.store_id"
        + "  and t21.order_summary_id = t22.order_summary_id"
        + "  and t21.store_id = :storeId"
        + "  and t22.pay_status = :payStatus"
        + "  and t21.del_flag = 0"
        + "  and t22.del_flag = 0"
        + "  left join o_order_details t23"
        + "  on t22.store_id = t23.store_id"
        + "  and t22.order_id = t23.order_id"
        + "  and t23.del_flag = 0"
        + "  and t23.item_status = :itemStatusUnConfirm"
        + "  GROUP BY t21.order_summary_id, t21.store_id) t5 "
        + "ON t1.store_id = t5.store_id "
        + "AND t1.order_summary_id = t5.order_summary_id "
        + "LEFT JOIN m_table t4 "
        + "ON t1.store_id = t4.store_id "
        + "AND t1.table_id = t4.table_id "
        + "AND t4.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND (t1.table_id = :tableId OR :tableId = 0) "
        + "AND (t1.takeout_flag = :takeoutFlag OR :takeoutFlag = '') "
        + "AND t1.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "AND t5.num = 0 "
        + "GROUP BY t1.store_id, t1.order_summary_id "
        + "ORDER BY orderTime DESC", nativeQuery = true)
    List<Map<String, Object>> findHistoryOrderInfoByStoreId(
        @Param("storeId") String storeId, @Param("itemStatusUnConfirm") String itemStatusUnConfirm,
        @Param("itemStatusConfirmed") String itemStatusConfirmed,
        @Param("takeoutFlag") String takeoutFlag, @Param("tableId") Integer tableId,
        @Param("payStatus") String payStatus);

    /**
     * 注文商品リスト取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param languages     言語
     * @param itemStatus    商品状態
     * @return 注文商品リスト
     */
    @Query(value = "SELECT t1.order_amount AS orderAmount, "
        + "t3.order_id AS orderId, "
        + "t3.order_detail_id AS orderDetailId, "
        + "t3.item_id AS itemId, "
        + "t5.item_name ->> :languages AS itemName, "
        + "t3.item_price AS itemPrice, "
        + "t3.item_count AS itemCount, "
        + "t4.diff_price * COALESCE(t4.item_option_count, 1) AS diffPrice, "
        + "t4.item_option_count AS optionItemCount, "
        + "t6.option_name ->> :languages AS optionName, "
        + "t7.classification AS classification "
        + "FROM o_order_summary t1 "
        + "INNER JOIN o_order t2 "
        + "ON t1.store_id = t2.store_id "
        + "AND t1.order_summary_id = t2.order_summary_id "
        + "INNER JOIN o_order_details t3 "
        + "ON t2.store_id = t3.store_id "
        + "AND t2.order_id = t3.order_id "
        + "LEFT JOIN o_order_details_option t4 "
        + "ON t3.store_id = t4.store_id "
        + "AND t3.order_detail_id = t4.order_detail_id "
        + "AND t4.del_flag = 0 "
        + "LEFT JOIN m_item t5 "
        + "ON t3.store_id = t5.store_id "
        + "AND t3.item_id = t5.item_id "
        + "AND t5.del_flag = 0 "
        + "LEFT JOIN m_option t6 "
        + "ON t4.store_id = t6.store_id "
        + "AND t4.item_option_type_code = t6.option_type_cd "
        + "AND t4.item_option_code = t6.option_code "
        + "AND t6.del_flag = 0 "
        + "LEFT JOIN m_option_type t7 "
        + "ON t6.store_id = t7.store_id "
        + "AND t6.option_type_cd = t7.option_type_cd "
        + "AND t7.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 "
        + "AND t2.del_flag = 0 "
        + "AND t3.del_flag = 0 "
        + "AND t3.item_status = :itemStatus "
        + "ORDER BY orderId ASC, orderDetailId ASC", nativeQuery = true)
    List<Map<String, Object>> findUnCfmOrderItemListByReceivablesId(
        @Param("storeId") String storeId, @Param("receivablesId") String receivablesId,
        @Param("languages") String languages, @Param("itemStatus") String itemStatus);

    /**
     * 受付情報設定.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param sustomerCount  人数
     * @param tableId        テーブルID
     * @param paymentType    支払区分
     * @param takeoutFlag    就餐区分
     * @param orderStatus    注文状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE o_order_summary "
        + "SET customer_count = :sustomerCount,"
        + "table_id = :tableId,"
        + "payment_type = :paymentType,"
        + "takeout_flag = :takeoutFlag,"
        + "order_status = :orderStatus,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND del_flag = 0", nativeQuery = true)
    void updateOrderSummaryTable(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("sustomerCount") Integer sustomerCount,
        @Param("tableId") Integer tableId,
        @Param("paymentType") String paymentType,
        @Param("takeoutFlag") String takeoutFlag,
        @Param("orderStatus") String orderStatus,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 席解除処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param seatRelease   席解フラグ
     * @param tableId       テーブルID
     * @param updOperCd     更新者
     * @param updDateTime   更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderSummary "
        + "SET seatRelease = :seatRelease, "
        + "updOperCd = :updOperCd, "
        + "updDateTime = :updDateTime, "
        + "version = version + 1 "
        + "WHERE delFlag = 0 "
        + "AND receivablesId = :receivablesId "
        + "AND tableId = :tableId "
        + "AND storeId = :storeId ")
    void updateSeatReleaseByReceivablesId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("seatRelease") String seatRelease,
        @Param("tableId") Integer tableId,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文一覧情報取得.
     *
     * @param storeId           店舗ID
     * @param orderDateStart    開始時間
     * @param orderDateEnd      終了時間
     * @param codeGroup         支払方式
     * @param paymentMethodCode 支払方式
     * @return 注文一覧情報
     */
    @Query(value = "SELECT  "
        + "TO_CHAR(Max(t2.ins_date_time), 'YYYY-MM-DD HH24:MI:SS') AS paymentTime, "
        + "sum(t2.payment_amount) AS paymentAmount, "
        + "MAX(t5.code_name) AS paymentMethod, "
        + "MAX(t3.store_name) AS storeName "
        + "FROM p_payment t1 "
        + "INNER JOIN p_payment_detail t2 "
        + "ON t2.payment_id = t1.payment_id "
        + "AND t2.del_flag = 0 "
        + "AND (t2.payment_method_code = :paymentMethodCode OR :paymentMethodCode = '') "
        + "AND t2.ins_date_time <= TO_TIMESTAMP(:orderDateEnd, 'YYYY-MM-DD HH24:MI:SS') "
        + "AND  TO_TIMESTAMP(:orderDateStart, 'YYYY-MM-DD HH24:MI:SS')<= t2.ins_date_time "
        + "INNER JOIN m_code t5 "
        + "ON t5.code_group = :codeGroup "
        + "AND t5.code = t2.payment_method_code "
        + "AND t5.store_id = t2.store_id "
        + "AND t5.del_flag = 0 "
        + "INNER JOIN m_store t3 "
        + "ON t3.store_id = t1.store_id "
        + "AND t3.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "group by t2.order_summary_id, t2.order_id, t2.payment_method_code "
        + "having sum(t2.payment_amount) > 0 ", nativeQuery = true)
    Page<Map<String, Object>> findSaleOrderByStoreId(
        @Param("storeId") String storeId,
        @Param("orderDateStart") String orderDateStart, @Param("orderDateEnd") String orderDateEnd,
        @Param("codeGroup") String codeGroup, @Param("paymentMethodCode") String paymentMethodCode,
        Pageable pageable);

    /**
     * 席解除処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param seatRelease    席解フラグ
     * @param payStatus      支払状態
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderSummary t1 "
        + "SET t1.seatRelease = :seatRelease, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = t1.version + 1 "
        + "WHERE t1.delFlag = 0 "
        + "AND t1.orderSummaryId = :orderSummaryId "
        + "AND t1.storeId = :storeId "
        + "AND NOT EXISTS ("
        + "  SELECT 1 "
        + "  FROM OOrder t2"
        + "  WHERE t1.storeId = t2.storeId"
        + "  AND t1.orderSummaryId = t2.orderSummaryId"
        + "  AND t2.payStatus = :payStatus"
        + "  AND t2.delFlag = 0) ")
    void updateSeatReleaseByOrderSummaryId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("payStatus") String payStatus,
        @Param("seatRelease") String seatRelease,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文一覧情報取得.
     *
     * @param storeId           店舗ID
     * @param orderDateStart    開始時間
     * @param orderDateEnd      終了時間
     * @param codeGroup         支払方式
     * @param paymentMethodCode 支払方式
     * @return 注文一覧情報
     */
    @Query(value = "SELECT  "
        + "sum(t2.payment_amount) AS paymentAmount "
        + "FROM p_payment t1 "
        + "INNER JOIN p_payment_detail t2 "
        + "ON t2.payment_id = t1.payment_id "
        + "AND t2.del_flag = 0 "
        + "AND (t2.payment_method_code = :paymentMethodCode OR :paymentMethodCode = '') "
        + "AND t2.ins_date_time <= TO_TIMESTAMP(:orderDateEnd, 'YYYY-MM-DD HH24:MI:SS') "
        + "AND  TO_TIMESTAMP(:orderDateStart, 'YYYY-MM-DD HH24:MI:SS')<= t2.ins_date_time "
        + "INNER JOIN m_code t5 "
        + "ON t5.code_group = :codeGroup "
        + "AND t5.code = t2.payment_method_code "
        + "AND t5.del_flag = 0 "
        + "AND t5.store_id = t2.store_id "
        + "INNER JOIN m_store t3 "
        + "ON t3.store_id = t1.store_id "
        + "AND t3.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0  ", nativeQuery = true)
    BigDecimal findSaleSummaryOrderByStoreId(
        @Param("storeId") String storeId,
        @Param("orderDateStart") String orderDateStart, @Param("orderDateEnd") String orderDateEnd,
        @Param("codeGroup") String codeGroup, @Param("paymentMethodCode") String paymentMethodCode);

    /**
     * 放题ID取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param itemType      商品区分
     * @return 放题ID
     */
    @Query(value = "select d.item_id "
        + "from o_order_summary s, o_order o, o_order_details d, m_item i "
        + "where s.store_id = o.store_id "
        + "and s.order_summary_id = o.order_summary_id "
        + "and o.store_id = d.store_id "
        + "and o.order_id = d.order_id "
        + "and d.store_id = i.store_id "
        + "and d.item_id = i.item_id "
        + "and s.del_flag = 0 "
        + "and o.del_flag = 0 "
        + "and d.del_flag = 0 "
        + "and i.del_flag = 0 "
        + "and s.store_id = :storeId "
        + "and s.receivables_id = :receivablesId "
        + "and i.item_type = :itemType", nativeQuery = true)
    List<Integer> getOrderBuffetId(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId,
        @Param("itemType") String itemType);

    /**
     * 注文商品数取得処理.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return 商品数
     */
    @Query(value = "select count(*) "
        + "from o_order_summary s, o_order o, o_order_details d "
        + "where s.store_id = o.store_id "
        + "and s.order_summary_id = o.order_summary_id "
        + "and o.store_id = d.store_id "
        + "and o.order_id = d.order_id "
        + "and s.del_flag = 0 "
        + "and o.del_flag = 0 "
        + "and d.del_flag = 0 "
        + "and s.store_id = :storeId "
        + "and s.receivables_id = :receivablesId ", nativeQuery = true)
    Integer getOrderItemCount(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 放题orderId取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param itemType       商品区分
     * @param payStatus      支払状態
     * @return 放题orderId
     */
    @Query(value = "select o.order_id "
        + "from o_order_summary s, o_order o, o_order_details d, m_item i "
        + "where s.store_id = o.store_id "
        + "and s.order_summary_id = o.order_summary_id "
        + "and o.store_id = d.store_id "
        + "and o.order_id = d.order_id "
        + "and d.store_id = i.store_id "
        + "and d.item_id = i.item_id "
        + "and s.del_flag = 0 "
        + "and o.del_flag = 0 "
        + "and d.del_flag = 0 "
        + "and i.del_flag = 0 "
        + "and o.pay_status = :payStatus "
        + "and s.store_id = :storeId "
        + "and s.order_summary_id = :orderSummaryId "
        + "and i.item_type = :itemType", nativeQuery = true)
    Integer getBuffetOrderId(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("itemType") String itemType,
        @Param("payStatus") String payStatus);

    /**
     * 指定受付ID削除.
     *
     * @param storeId           店舗ID
     * @param receivablesIdList 受付IDリスト
     */
    @Modifying
    @Query(value = "delete from o_order_summary "
        + "where store_id = :storeId and receivables_id in :receivablesIdList", nativeQuery = true)
    void deleteReceivables(@Param("storeId") String storeId,
        @Param("receivablesIdList") List<String> receivablesIdList);

    /**
     * 放题ID取得処理.
     *
     * @param storeId   店舗ID
     * @param startTime 営業開始時間
     * @param endTime   営業終了時間
     * @param payStatus 支払状態
     * @return 放题ID
     */
    @Query(value = "select new com.cnc.qr.common.shared.model.RgOrderSummaryDto ( "
        + "t1.orderSummaryId, "
        + "t1.customerCount, "
        + "t1.paymentAmount, "
        + "t1.priceDiscountAmount, "
        + "t1.priceDiscountRate,"
        + "t1.takeoutFlag, "
        + "t3.itemId, "
        + "t3.itemCount, "
        + "t3.itemPrice, "
        + "t3.itemClassification ) "
        + "from OOrderSummary t1 "
        + "INNER JOIN OOrder t2 "
        + "ON t2.storeId = t1.storeId "
        + "and t2.delFlag = 0 "
        + "and t2.orderSummaryId = t1.orderSummaryId "
        + "and t2.orderTime <= :endTime "
        + "and t2.orderTime >= :startTime "
        + "and t2.payStatus = :payStatus "
        + "INNER JOIN OOrderDetails t3 "
        + "ON t3.storeId = t2.storeId "
        + "and t3.delFlag = 0 "
        + "and t3.orderId = t2.orderId "
        + "where t1.storeId = :storeId "
        + "and t1.delFlag = 0 ")
    List<RgOrderSummaryDto> getSameDayOrder(@Param("storeId") String storeId,
        @Param("startTime") ZonedDateTime startTime,
        @Param("endTime") ZonedDateTime endTime, @Param("payStatus") String payStatus);


    /**
     * 注文一覧情報取得.
     *
     * @param storeId           店舗ID
     * @param orderDateStart    営業開始時間
     * @param orderDateEnd      営業終了時間
     * @param codeGroup         支払方式
     * @param paymentMethodCode 支払方式
     * @return 注文一覧情報
     */
    @Query(value = "SELECT  "
        + "TO_CHAR(Max(t2.ins_date_time), 'YYYY-MM-DD HH24:MI:SS') AS paymentTime, "
        + "sum(t2.payment_amount) AS paymentAmount, "
        + "MAX(t5.code_name) AS paymentMethod, "
        + "MAX(t3.store_name) AS storeName "
        + "FROM p_payment t1 "
        + "INNER JOIN p_payment_detail t2 "
        + "ON t2.payment_id = t1.payment_id "
        + "AND t2.del_flag = 0 "
        + "AND (t2.payment_method_code = :paymentMethodCode OR :paymentMethodCode = '') "
        + "AND t2.ins_date_time <= :orderDateEnd "
        + "AND  :orderDateStart<= t2.ins_date_time "
        + "INNER JOIN m_code t5 "
        + "ON t5.code_group = :codeGroup "
        + "AND t5.code = t2.payment_method_code "
        + "AND t5.store_id = t2.store_id "
        + "AND t5.del_flag = 0 "
        + "INNER JOIN m_store t3 "
        + "ON t3.store_id = t1.store_id "
        + "AND t3.del_flag = 0 "
        + "WHERE t1.store_id = :storeId "
        + "AND t1.del_flag = 0 "
        + "group by t2.order_summary_id, t2.order_id, t2.payment_method_code "
        + "having sum(t2.payment_amount) > 0 ", nativeQuery = true)
    List<Map<String, Object>> findSaleOrderSumByStoreId(
        @Param("storeId") String storeId,
        @Param("orderDateStart") ZonedDateTime orderDateStart,
        @Param("orderDateEnd") ZonedDateTime orderDateEnd,
        @Param("codeGroup") String codeGroup, @Param("paymentMethodCode") String paymentMethodCode);


    /**
     * 支払い金額更新処理(先払専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param paymentAmount  支払金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE OOrderSummary "
        + "SET updOperCd = :updOperCd,"
        + "paymentAmount = paymentAmount + :paymentAmount,"
        + "updDateTime = :updDateTime, "
        + "delFlag = 0, "
        + "version = version + 1 "
        + "WHERE storeId = :storeId "
        + "AND orderSummaryId = :orderSummaryId "
        + "AND delFlag = 1 ")
    Integer updatePaymentAmountAndDelFlag(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("paymentAmount") BigDecimal paymentAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * プッシュ情報取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @return プッシュ情報
     */
    @Query(value = "select t2.reception_no as receptionNo ,t3.table_name as tableName "
        + " from o_order_summary t1 "
        + "JOIN o_receivables t2 "
        + "ON t2.receivables_id = t1.receivables_id "
        + "AND t2.store_id = t1.store_id "
        + "AND t2.del_flag = 0 "

        + "LEFT JOIN m_table t3 "
        + "ON t3.table_id = t1.table_id "
        + "AND t3.store_id = t1.store_id "
        + "AND t3.del_flag = 0 "

        + "WHERE t1.store_id = :storeId "
        + "AND t1.receivables_id = :receivablesId "
        + "AND t1.del_flag = 0 ", nativeQuery = true)
    Map<String, Object> findByStoreIdTableAndReceptionNo(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);

    /**
     * 注文サマリ変更処理(割勘専用).
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param customerCount  人数
     * @param orderAmount    注文金額
     * @param updOperCd      更新者
     * @param updDateTime    更新日時
     */
    @Modifying
    @Query(value = "UPDATE OOrderSummary t1 "
        + "SET t1.customerCount = t1.customerCount - :customerCount, "
        + "t1.orderAmount = t1.orderAmount - :orderAmount, "
        + "t1.updOperCd = :updOperCd, "
        + "t1.updDateTime = :updDateTime, "
        + "t1.version = version + 1 "
        + "WHERE t1.delFlag = 0 "
        + "AND t1.orderSummaryId = :orderSummaryId "
        + "AND t1.storeId = :storeId ")
    void updateCustomerCountAndOrderAmount(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("customerCount") Integer customerCount,
        @Param("orderAmount") BigDecimal orderAmount,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);


    /**
     * オーダー商品取得取得処理.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @return オーダー商品取得
     */
    @Query(value = "SELECT " +
        "t3.item_id AS itemId, " +
        "t1.takeout_flag AS takeoutFlag, " +
        "t3.item_price AS itemPrice " +
        "from o_order_summary t1 " +
        "INNER JOIN  o_order t2 " +
        "ON t2.order_summary_id = t1.order_summary_id " +
        "AND t2.store_id = t1.store_id " +
        "AND (t2.order_id = :orderId OR :orderId = -1) " +
        "AND t2.del_flag = 0 " +
        "INNER JOIN o_order_details t3 " +
        "ON t3.store_id = t2.store_id " +
        "AND t3.order_id = t2.order_id " +
        "AND t3.del_flag = 0 " +
        "WHERE t1.order_summary_id = :orderSummaryId " +
        "AND t1.store_id = :storeId " +
        "AND t1.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> getOrderItem(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId
    );


    /**
     * 注文一覧情報取得.
     *
     * @param storeId   店舗ID
     * @param startTime 精算開始時間
     * @param endTime   精算終了時間
     * @return 注文一覧情報
     */
    @Query(value = " SELECT "
        + "     t1.order_summary_id AS orderSummaryId, "
        + " CASE "
        + "         WHEN t1.price_discount_amount IS NULL THEN "
        + "         0 ELSE t1.price_discount_amount  "
        + "     END AS priceDiscountAmount, "
        + " CASE  "
        + "     WHEN t1.price_discount_rate IS NULL THEN "
        + "         0 "
        + "     ELSE "
        + "         ROUND(t1.price_discount_rate/10 * t1.payment_amount ) "
        + " END AS  priceDiscountRate, "
        + "     t1.payment_amount AS orderPaymentAmount, "
        + "     t1.customer_count AS customerCount, "
        + "     t3.payment_amount AS paymentAmount, "
        + "     t3.payment_method_code AS paymentMethodCode, "
        + "     t2.consumption_amount AS consumptionAmount, "
        + "     t2.foreign_tax  AS foreignTax, "
        + "     t2.foreign_normal_amount AS foreignNormalAmount, "
        + "     t2.foreign_normal_object_amount AS foreignNormalObjectAmount, "
        + "     t2.foreign_relief_amount AS foreignReliefAmount, "
        + "     t2.foreign_relief_object_amount AS foreignReliefObjectAmount, "
        + "     t2.included_tax AS includedTax, "
        + "     t2.included_normal_amount AS includedNormalAmount, "
        + "     t2.included_normal_object_amount AS includedNormalObjectAmount, "
        + "     t2.included_relief_amount AS includedReliefAmount, "
        + "     t2.included_relief_object_amount AS  includedReliefObjectAmount "
        + " FROM "
        + "     o_order_summary t1 "
        + "     INNER JOIN p_payment_detail t3 ON t3.store_id = t1.store_id  "
        + "     AND t3.order_summary_id = t1.order_summary_id "
        + "     AND t3.del_flag = 0 "
        + "     AND t3.store_id = t1.store_id "
        + "     INNER JOIN o_tax_amount t2 "
        + " ON t2.store_id = t1.store_id "
        + " and t2.order_summary_id = t1.order_summary_id "
        + " and t2.del_flag = 0 "
        + " WHERE "
        + "     t1.store_id = :storeId"
        + "     and t3.ins_date_time <=:endTime "
        + "     and t3.ins_date_time >= :startTime "
        + "     AND t1.del_flag = 0", nativeQuery = true)
    List<Map<String, Object>> findSaleSummaryOrderAndTaxAmountAndPaymentByStoreId(
        @Param("storeId") String storeId,
        @Param("startTime") ZonedDateTime startTime, @Param("endTime") ZonedDateTime endTime);


    /**
     * 当オーダーの小計金額取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @return 小計金額
     */
    @Query(value =
        "SELECT t3.item_price AS itemPrice, t3.return_order_detail_id as returnOrderDetailId "
            + "FROM o_order_summary t1 "
            + "INNER JOIN o_order t2 "
            + "ON t2.order_summary_id = t1.order_summary_id "
            + "AND t2.store_id = t1.store_id "
            + "INNER JOIN o_order_details t3 "
            + "ON t3.store_id = t2.store_id "
            + "AND t3.order_id = t2.order_id "
            + "WHERE t2.order_summary_id = :orderSummaryId "
            + "AND t1.store_id = :storeId "
            + "AND t1.del_flag = 0 "
            + "AND t2.del_flag = 0 "
            + "AND t3.del_flag = 0 ", nativeQuery = true)
    List<Map<String, Object>> findOrderItemsChange(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId);

}
