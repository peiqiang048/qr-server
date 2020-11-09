package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.RModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 機能テーブルリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface RModelRepository extends JpaRepository<RModel, Long> {

    /**
     * 機能一覧.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return 機能一覧
     */
    List<RModel> findByStoreIdAndDelFlag(String storeId, Integer delFlag);

}
