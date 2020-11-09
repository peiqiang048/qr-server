package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * トークンマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MTokenRepository extends JpaRepository<MToken, Long> {

    /**
     * トークン取得.
     *
     * @param tokenId トークンID
     * @param delFlag 削除フラグ
     * @return トークン情報
     */
    MToken findByTokenIdAndDelFlag(String tokenId, Integer delFlag);

    /**
     * 指定受付ID削除.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     */
    @Modifying
    @Query(value = "delete from m_token "
        + "where store_id = :storeId "
        + "and receivables_id = :receivablesId", nativeQuery = true)
    void deleteReceivables(@Param("storeId") String storeId,
        @Param("receivablesId") String receivablesId);


    /**
     * トークン取得.
     *
     * @param storeId       店舗ID
     * @param receivablesId 受付ID
     * @param delFlag       削除フラグ
     * @return トークン情報
     */
    MToken findByStoreIdAndReceivablesIdAndDelFlag(String storeId, String receivablesId,
        Integer delFlag);


    /**
     * トークンチェック.
     *
     * @param tokenId トークンID
     * @return 件数
     */
    @Query(value = "select count(t.id) as counts "
        + "from MToken t "
        + "where t.tokenId = :tokenId "
        + "and t.delFlag = 0")
    Integer findCountByTokenId(@Param("tokenId") String tokenId);

}
