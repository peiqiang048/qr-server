package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.PSbResultDetail;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * SB支払結果明細テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface PSbResultDetailRepository extends JpaRepository<PSbResultDetail, Long> {

    /**
     * 支払結果データ更新.
     *
     * @param orderNo           注文番号
     * @param sbResultDetailId  SB支払結果明細ID
     * @param paymentMethodCode 支払方式コード
     * @param respCode          支付结果code
     * @param resTrackingId     処理トラッキングID
     * @param resPayinfoKey     顧客決済情報
     * @param resPaymentDate    完了処理日時
     * @param resErrCode        エラーコード
     * @param updOperCd         更新者
     * @param updDateTime       更新日時
     */
    @Modifying
    @Query(value = ""
        + "UPDATE PSbResultDetail ps "
        + "SET ps.paymentMethodCode = :paymentMethodCode, "
        + "ps.respCode = :respCode, "
        + "ps.resTrackingId = :resTrackingId, "
        + "ps.resPayinfoKey = :resPayinfoKey, "
        + "ps.resPaymentDate = :resPaymentDate, "
        + "ps.resErrCode = :resErrCode, "
        + "ps.updOperCd = :updOperCd, "
        + "ps.updDateTime = :updDateTime, "
        + "ps.version = ps.version + 1 "
        + "WHERE ps.storeId = :storeId "
        + "AND ps.orderNo = :orderNo "
        + "AND ps.sbResultDetailId = :sbResultDetailId")
    Integer updateResultDetail(@Param("storeId") String storeId,
        @Param("orderNo") String orderNo,
        @Param("sbResultDetailId") Integer sbResultDetailId,
        @Param("paymentMethodCode") String paymentMethodCode,
        @Param("respCode") String respCode,
        @Param("resTrackingId") String resTrackingId,
        @Param("resPayinfoKey") String resPayinfoKey,
        @Param("resPaymentDate") String resPaymentDate,
        @Param("resErrCode") String resErrCode,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 支払情報取得.
     *
     * @param storeId   店舗ID
     * @param orderNo   注文番号
     * @param payResult 支付结果
     * @return 支払情報
     */
    @Query(value = "SELECT ps "
        + "FROM PSbResultDetail ps "
        + "WHERE ps.storeId = :storeId "
        + "AND ps.orderNo = :orderNo "
        + "AND ps.respCode = :payResult "
        + "AND (ps.returnRespCode IS NULL OR ps.returnRespCode <> :payResult) "
        + "AND ps.delFlag = 0 ")
    PSbResultDetail getPaymentInfo(@Param("storeId") String storeId,
        @Param("orderNo") String orderNo,
        @Param("payResult") String payResult);

    /**
     * 返金結果更新.
     *
     * @param orderNo             注文番号
     * @param payResult           支払結果
     * @param resSpsTransactionId 処理 SBPS トランザクション ID
     * @param resProcessDate      処理完了日時
     * @param returnRespCode      返金結果code
     * @param resErrCode          エラーコード
     * @param updOperCd           更新者
     * @param updDateTime         更新日時
     */
    @Modifying
    @Query(value = "UPDATE PSbResultDetail ps "
        + "SET ps.resSpsTransactionId = :resSpsTransactionId,"
        + "ps.resProcessDate = :resProcessDate,"
        + "ps.returnRespCode = :returnRespCode,"
        + "ps.resErrCode = :resErrCode,"
        + "ps.updOperCd = :updOperCd, "
        + "ps.updDateTime = :updDateTime, "
        + "ps.version = ps.version + 1 "
        + "WHERE ps.storeId = :storeId "
        + "AND ps.orderNo = :orderNo "
        + "AND ps.respCode = :payResult "
        + "AND (ps.returnRespCode IS NULL OR ps.returnRespCode <> :payResult) "
        + "AND ps.delFlag = 0 ")
    Integer updateRefunds(@Param("storeId") String storeId,
        @Param("orderNo") String orderNo,
        @Param("payResult") String payResult,
        @Param("resSpsTransactionId") String resSpsTransactionId,
        @Param("resProcessDate") String resProcessDate,
        @Param("returnRespCode") String returnRespCode,
        @Param("resErrCode") String resErrCode,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 注文番号を取得する.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param orderId        注文ID
     * @return 注文番号
     */
    @Query(value = "SELECT order_no "
        + "FROM p_sb_result_detail "
        + "WHERE store_id = :storeId "
        + "AND order_summary_id = :orderSummaryId "
        + "AND order_id = :orderId "
        + "AND del_flag = 0 "
        + "ORDER BY order_no desc "
        + "LIMIT 1", nativeQuery = true)
    String getOrderSummaryNo(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId,
        @Param("orderId") Integer orderId);

    /**
     * 支払済み情報取得.
     *
     * @param storeId        店舗ID
     * @param orderSummaryId 注文サマリID
     * @param respCode       支払結果code
     * @return 支払済み情報
     */
    @Query(value = "SELECT psd "
        + "FROM PSbResultDetail psd "
        + "WHERE psd.storeId = :storeId "
        + "AND psd.orderSummaryId = :orderSummaryId "
        + "AND psd.respCode = :respCode "
        + "AND psd.delFlag = 0 ")
    List<PSbResultDetail> getPayInfo(@Param("storeId") String storeId,
        @Param("orderSummaryId") String orderSummaryId, @Param("respCode") String respCode);
}
