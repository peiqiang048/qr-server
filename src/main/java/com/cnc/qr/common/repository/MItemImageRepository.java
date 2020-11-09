package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MItemImage;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 商品画像マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MItemImageRepository extends JpaRepository<MItemImage, Long> {

    /**
     * 商品画像サマリ更新処理.
     *
     * @param storeId         店舗ID
     * @param itemId          商品ID
     * @param itemLargePicUrl 商品大きな画像URL
     * @param itemSmallPicUrl 商品サムネイルURL
     * @param videoPath       商品ビデオURL
     * @param updOperCd       更新者
     * @param updDateTime     更新日時
     */
    @Modifying
    @Query(value = "update m_item_image "
        + "set image_path = :itemLargePicUrl, "
        + "short_image_path = :itemSmallPicUrl, "
        + "video_path = :videoPath, "
        + "upd_oper_cd = :updOperCd, "
        + "upd_date_time = :updDateTime, "
        + "version = version + 1 "
        + "where del_flag = 0 "
        + "and item_id = :itemId "
        + "and store_id = :storeId ", nativeQuery = true)
    Integer updateItemImage(@Param("storeId") String storeId,
        @Param("itemId") Integer itemId,
        @Param("itemLargePicUrl") String itemLargePicUrl,
        @Param("itemSmallPicUrl") String itemSmallPicUrl,
        @Param("videoPath") String videoPath,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime);

    /**
     * 指定商品ID削除.
     *
     * @param storeId     店舗ID
     * @param itemIdList  商品IDリスト
     * @param updOperCd   更新者
     * @param updDateTime 更新日時
     */
    @Modifying
    @Query(value = "UPDATE MItemImage t1 "
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
     * 商品画像削除.
     *
     * @param storeId 店舗ID
     * @param itemId  商品ID
     */
    @Modifying
    @Query(value = "delete from m_item_image where store_id = :storeId and item_id = :itemId", nativeQuery = true)
    void deleteItemImage(@Param("storeId") String storeId, @Param("itemId") Integer itemId);
}
