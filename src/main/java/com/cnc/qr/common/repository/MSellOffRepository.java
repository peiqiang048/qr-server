package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MSellOff;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品欠品マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MSellOffRepository extends JpaRepository<MSellOff, Long> {

    /**
     * 欠品テーブル情報取得.
     *
     * @param storeId      店舗ID
     * @param sellOffStart 売り切り開始日時
     * @param itemId       商品IDリスト
     * @param delFlag      削除フラグ
     * @return 欠品情報
     */
    List<MSellOff> findByStoreIdAndSellOffStartIsBeforeAndItemIdIsInAndDelFlag(String storeId,
        ZonedDateTime sellOffStart, List<Integer> itemId, Integer delFlag);
}
