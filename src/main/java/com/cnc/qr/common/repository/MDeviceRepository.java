package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MDevice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * デバイスマスタ.
 */
public interface MDeviceRepository extends JpaRepository<MDevice, String> {

    /**
     * デバイス取得.
     *
     * @param storeId 店舗ID
     * @param delFlag 削除フラグ
     * @return デバイス情報
     */
    List<MDevice> findByStoreIdAndDelFlag(String storeId, Integer delFlag);

    /**
     * 店舗情報検証情報取得.
     *
     * @param storeId     店舗ID
     * @param delFlag     削除フラグ
     * @param deviceToken デバイストークン
     * @return 検証情報
     */
    List<MDevice> findByStoreIdAndDelFlagAndDeviceToken(String storeId, Integer delFlag,
        String deviceToken);
}
