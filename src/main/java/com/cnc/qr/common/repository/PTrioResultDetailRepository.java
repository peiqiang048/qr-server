package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.PTrioResultDetail;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * トリオ支払結果明細テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface PTrioResultDetailRepository extends JpaRepository<PTrioResultDetail, Long> {

    /**
     * 支払テーブル登録.
     */
    @Modifying
    @Query(value = "" +
        "INSERT INTO p_trio_result_detail(" +
        "store_id," +
        "trio_result_detail_id," +
        "order_summary_id," +
        "order_id," +
        "order_no," +
        "payment_method_code," +
        "cancel_order_id," +
        "return_order_id," +
        "pay_price," +
        "resp_code," +
        "cancel_resp_code," +
        "return_resp_code," +
        "del_flag," +
        "ins_oper_cd," +
        "ins_date_time," +
        "upd_oper_cd," +
        "upd_date_time," +
        "version)" +
        "VALUES( " +
        ":storeId," +
        ":trioResultDetailId," +
        ":orderSummaryId," +
        ":orderId," +
        ":orderNo," +
        ":paymentMethodCode," +
        ":cancelOrderId," +
        ":returnOrderId," +
        ":payPrice," +
        ":respCode," +
        ":cancelRespCode," +
        ":returnRespCode," +
        ":delFlag," +
        ":insOperCd," +
        ":insDateTime," +
        ":updOperCd," +
        ":updDateTime," +
        ":version)", nativeQuery = true)
    void insert(@Param("storeId") String storeId,
        @Param("trioResultDetailId") Integer trioResultDetailId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("orderNo") String orderNo,
        @Param("paymentMethodCode") String paymentMethodCode,
        @Param("cancelOrderId") String cancelOrderId,
        @Param("returnOrderId") String returnOrderId,
        @Param("payPrice") BigDecimal payPrice,
        @Param("respCode") String respCode,
        @Param("cancelRespCode") String cancelRespCode,
        @Param("returnRespCode") String returnRespCode,
        @Param("delFlag") Integer delFlag,
        @Param("insOperCd") String insOperCd,
        @Param("insDateTime") ZonedDateTime insDateTime,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime,
        @Param("version") Integer version);

    /**
     * 支払结果更新.
     *
     * @param storeId            店舗ID
     * @param trioResultDetailId トリオ支払結果明細ID
     * @param orderNo            注文番号
     * @param respCode           支付结果code
     * @param updOperCd          更新者
     * @param updDateTime        更新日時
     */
    @Modifying
    @Query(value = "" +
        "UPDATE p_trio_result_detail " +
        "SET resp_code = :respCode," +
        "upd_oper_cd = :updOperCd," +
        "upd_date_time = :updDateTime " +
        "WHERE order_no = :orderNo " +
        "AND store_id = :storeId " +
        "AND trio_result_detail_id = :trioResultDetailId", nativeQuery = true)
    Integer updatePayStatus(@Param("storeId") String storeId,
        @Param("trioResultDetailId") Integer trioResultDetailId,
        @Param("orderNo") String orderNo,
        @Param("respCode") String respCode,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 支払取消结果更新.
     *
     * @param trioResultDetailId トリオ支払結果明細ID
     * @param orderNo            注文番号
     * @param respCode           支付结果code
     * @param cancelId           取消ID
     * @param cancelRespCode     取消结果code
     * @param updOperCd          更新者
     * @param updDateTime        更新日時
     */
    @Modifying
    @Query(value = "" +
        "UPDATE p_trio_result_detail " +
        "SET resp_code = :respCode," +
        "cancel_order_id = :cancelId," +
        "cancel_resp_code = :cancelRespCode," +
        "upd_oper_cd = :updOperCd," +
        "upd_date_time = :updDateTime " +
        "WHERE order_no = :orderNo " +
        "AND trio_result_detail_id = :trioResultDetailId", nativeQuery = true)
    Integer updatePayCancelStatus(@Param("trioResultDetailId") Integer trioResultDetailId,
        @Param("orderNo") String orderNo,
        @Param("respCode") String respCode,
        @Param("cancelId") String cancelId,
        @Param("cancelRespCode") String cancelRespCode,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * MaxDetailId取得.
     *
     * @param storeId 店舗ID
     * @return 明細ID
     */
    @Query(value = "SELECT t1.trio_result_detail_id " +
        "FROM p_trio_result_detail t1 " +
        "WHERE t1.store_id = :storeId " +
        "ORDER BY t1.trio_result_detail_id desc " +
        "LIMIT 1", nativeQuery = true)
    Integer getMaxDetailId(@Param("storeId") String storeId);

    /**
     * 支払回数取得.
     *
     * @param trioResultDetailId トリオ支払結果明細ID
     * @param orderNo            注文番号
     * @param respCode           支付结果code
     * @return 回数
     */
    @Query(value = "SELECT count(*) " +
        "FROM p_trio_result_detail t1 " +
        "WHERE t1.order_no = :orderNo " +
        "AND t1.trio_result_detail_id = :trioResultDetailId " +
        "AND t1.resp_code = :respCode", nativeQuery = true)
    Integer getCount(@Param("orderNo") String orderNo,
        @Param("trioResultDetailId") Integer trioResultDetailId,
        @Param("respCode") String respCode);

    /**
     * 返金一覧取得.
     *
     * @param storeId          店舗ID
     * @param receivablesNo    受付No
     * @param orderDateStart   注文開始日時
     * @param orderDateEnd     注文終了時
     * @param paymentGroupCode 支払方式
     * @param payResult        支付结果
     * @param pageable         ページ情報
     * @return 返金一覧
     */
    @Query(value = "select d.orderNo," +
        "d.paymentAmount," +
        "d.paymentCode," +
        "d.payTime," +
        "d.receivablesId," +
        "d.receivablesNo," +
        "d.tableName," +
        "d.paymentMethod " +
        "from o_order_summary tt1, ( " +
        "SELECT p.store_id as storeId,"
        + "p.order_no as orderNo," +
        "p.pay_price as paymentAmount," +
        "p.payment_method_code as paymentCode," +
        "to_char(p.ins_date_time,'yyyy-MM-dd hh24:mi:ss') as payTime," +
        "o.receivables_id as receivablesId," +
        "r.reception_no as receivablesNo, " +
        "t.table_name as tableName," +
        "c.code_name as paymentMethod, " +
        "p.ins_date_time as insDateTime " +
        "from p_trio_result_detail p " +
        "inner join o_order_summary o " +
        "on p.store_id = o.store_id " +
        "and p.order_summary_id = o.order_summary_id " +
        "and o.del_flag = 0 " +
        "inner join o_receivables r " +
        "on o.store_id = r.store_id " +
        "and o.receivables_id = r.receivables_id " +
        "and r.del_flag = 0 " +
        "left join m_table t " +
        "on o.store_id = t.store_id " +
        "and o.table_id = t.table_id " +
        "and t.del_flag = 0 " +
        "inner join m_code c " +
        "on p.store_id = c.store_id " +
        "and p.payment_method_code = c.code " +
        "and c.del_flag = 0 " +
        "and c.code_group = :paymentGroupCode " +
        "where p.del_flag = 0 " +
        "and p.resp_code = :payResult " +
        "and p.store_id = :storeId " +
        "and (p.return_resp_code is null or p.return_resp_code <> :payResult) " +
        "union " +
        "SELECT p.store_id as storeId,"
        + "p.order_no as orderNo," +
        "p.pay_price as paymentAmount," +
        "p.payment_method_code as paymentCode," +
        "to_char(p.ins_date_time,'yyyy-MM-dd hh24:mi:ss') as payTime," +
        "o.receivables_id as receivablesId," +
        "r.reception_no as receivablesNo, " +
        "t.table_name as tableName," +
        "c.code_name as paymentMethod, " +
        "p.ins_date_time as insDateTime " +
        "from p_sb_result_detail p " +
        "inner join o_order_summary o " +
        "on p.store_id = o.store_id " +
        "and p.order_summary_id = o.order_summary_id " +
        "and o.del_flag = 0 " +
        "inner join o_receivables r " +
        "on o.store_id = r.store_id " +
        "and o.receivables_id = r.receivables_id " +
        "and r.del_flag = 0 " +
        "left join m_table t " +
        "on o.store_id = t.store_id " +
        "and o.table_id = t.table_id " +
        "and t.del_flag = 0 " +
        "inner join m_code c " +
        "on p.store_id = c.store_id " +
        "and p.payment_method_code = c.code " +
        "and c.del_flag = 0 " +
        "and c.code_group = :paymentGroupCode " +
        "where p.del_flag = 0 " +
        "and p.resp_code = :payResult " +
        "and p.store_id = :storeId " +
        "and (p.return_resp_code is null or p.return_resp_code <> :payResult) " +
        ") d " +
        "where tt1.receivables_id = d.receivablesId "
        + "AND tt1.store_id = d.storeId "
        + "AND tt1.del_flag = 0 "
        + "AND (d.receivablesNo = :receivablesNo OR :receivablesNo = 0) "
        + "and (d.insDateTime > to_timestamp(:orderDateStart,'yyyy-MM-dd hh24:mi:ss') "
        + "OR :orderDateStart = '') "
        + "and (d.insDateTime < to_timestamp(:orderDateEnd,'yyyy-MM-dd hh24:mi:ss')"
        + " OR :orderDateEnd = '') ", nativeQuery = true)
    Page<Map<String, Object>> getRefundsList(@Param("storeId") String storeId,
        @Param("receivablesNo") Integer receivablesNo,
        @Param("orderDateStart") String orderDateStart,
        @Param("orderDateEnd") String orderDateEnd,
        @Param("paymentGroupCode") String paymentGroupCode,
        @Param("payResult") String payResult,
        Pageable pageable);

    /**
     * 支払情報取得.
     *
     * @param storeId   店舗ID
     * @param orderNo   注文番号
     * @param payResult 支付结果
     * @return 支払情報
     */
    @Query(value = "SELECT * " +
        "FROM p_trio_result_detail p " +
        "WHERE p.store_id = :storeId " +
        "AND p.order_no = :orderNo " +
        "AND p.resp_code = :payResult " +
        "AND (p.return_resp_code is null or p.return_resp_code <> :payResult) " +
        "AND p.del_flag = 0 ", nativeQuery = true)
    PTrioResultDetail getPaymentAmount(@Param("storeId") String storeId,
        @Param("orderNo") String orderNo,
        @Param("payResult") String payResult);

    /**
     * 返金.
     *
     * @param storeId     店舗ID
     * @param orderNo     注文番号
     * @param payResult   支付结果
     * @param refundId    返金ID
     * @param respCode    支付结果code
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "" +
        "UPDATE p_trio_result_detail " +
        "SET return_order_id = :refundId," +
        "return_resp_code = :respCode," +
        "upd_oper_cd = :updOperCd," +
        "upd_date_time = :updDateTime " +
        "WHERE store_id = :storeId " +
        "AND order_no = :orderNo " +
        "AND resp_code = :payResult " +
        "AND (return_resp_code is null or return_resp_code <> :payResult) " +
        "AND del_flag = 0", nativeQuery = true)
    Integer updateRefunds(@Param("storeId") String storeId,
        @Param("orderNo") String orderNo,
        @Param("payResult") String payResult,
        @Param("refundId") String refundId,
        @Param("respCode") String respCode,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文番号を取得する.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @param payResult      支付结果
     * @return 注文番号
     */
    @Query(value = "SELECT order_no " +
        "FROM p_trio_result_detail " +
        "WHERE store_id = :storeId " +
        "AND order_summary_id = :orderSummaryId " +
        "AND order_id = :orderId " +
        "AND resp_code = :payResult " +
        "AND del_flag = 0 " +
        "ORDER BY order_no desc " +
        "LIMIT 1", nativeQuery = true)
    String getOrderSummaryNo(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId,
        @Param("payResult") String payResult);
}
