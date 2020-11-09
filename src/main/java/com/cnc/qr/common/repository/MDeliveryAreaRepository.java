package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MDeliveryArea;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * デバイスマスタ.
 */
public interface MDeliveryAreaRepository extends JpaRepository<MDeliveryArea, String> {

    /**
     * 市区町村一覧情報取得.
     *
     * @param storeId      店舗ID
     * @param prefectureId 都道府県ID
     * @param languages    言語
     * @return 市区町村一覧情報
     */
    @Query(value = "SELECT DISTINCT                " +
        "t1.city_id AS areaId,                     " +
        "t2.city_name ->> :languages AS areaName   " +
        "from  m_delivery_area t1                  " +
        "inner join m_area t2                      " +
        "on t2.prefecture_id = t1.prefecture_id    " +
        "and t2.city_id = t1.city_id               " +
        "and t2.del_flag = 0                       " +
        "where t1.store_id = :storeId              " +
        "and t1.prefecture_id = :prefectureId      " +
        "and t1.del_flag = 0                       " +
        "order by t1.city_id asc ", nativeQuery = true)
    List<Map<String, Object>> findSelectedCityList(@Param("storeId") String storeId,
        @Param("prefectureId") String prefectureId, @Param("languages") String languages);

    /**
     * 町域番地一覧情報取得.
     *
     * @param storeId      店舗ID
     * @param prefectureId 都道府県ID
     * @param cityId       市区町村ID
     * @param languages    言語
     * @return 町域番地一覧情報
     */
    @Query(value = "SELECT DISTINCT                " +
        "t1.block_id AS areaId,                    " +
        "t2.block_name ->> :languages AS areaName  " +
        "from  m_delivery_area t1                  " +
        "inner join m_area t2                      " +
        "on t2.prefecture_id = t1.prefecture_id    " +
        "and t2.city_id = t1.city_id               " +
        "and t2.block_id = t1.block_id             " +
        "and t2.del_flag = 0                       " +
        "where t1.store_id = :storeId              " +
        "and t1.prefecture_id = :prefectureId      " +
        "and t1.city_id = :cityId                  " +
        "and t1.del_flag = 0                       " +
        "order by t1.block_id asc ", nativeQuery = true)
    List<Map<String, Object>> findSelectedBlockList(@Param("storeId") String storeId,
        @Param("prefectureId") String prefectureId, @Param("cityId") String cityId,
        @Param("languages") String languages);

    /**
     * 都道府県取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return 都道府県一覧情報
     */
    @Query(value = "SELECT DISTINCT                              "
        + "t1.prefecture_id AS prefectureId,                     "
        + "t2.prefecture_name ->> :languages AS prefectureName   "
        + "FROM m_delivery_area t1                               "
        + "INNER JOIN m_area t2                                  "
        + "ON t2.prefecture_id = t1.prefecture_id                "
        + "AND t2.del_flag = 0                                   "
        + "WHERE t1.store_id = :storeId                          "
        + "AND t1.del_flag = 0                                   "
        + "order by t1.prefecture_id ASC ", nativeQuery = true)
    List<Map<String, Object>> findPrefectureByStoreId(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * 指定した都道府県で市区町村取得.
     *
     * @param storeId      店舗ID
     * @param prefectureId 都道府県ID
     * @param languages    言語
     * @return 市区町村情報
     */
    @Query(value = "SELECT DISTINCT                              "
        + "t1.city_id AS cityId,                                 "
        + "t2.city_name ->> :languages AS cityName               "
        + "FROM m_delivery_area t1                               "
        + "INNER JOIN m_area t2                                  "
        + "ON t2.prefecture_id = t1.prefecture_id                "
        + "AND t2.city_id = t1.city_id                           "
        + "AND t2.del_flag = 0                                   "
        + "WHERE t1.store_id = :storeId                          "
        + "AND t1.prefecture_id = :prefectureId                  "
        + "AND t1.del_flag = 0                                   "
        + "order by t1.city_id ASC ", nativeQuery = true)
    List<Map<String, Object>> findCityByStoreIdAndPrefectureId(@Param("storeId") String storeId,
        @Param("prefectureId") String prefectureId, @Param("languages") String languages);

    /**
     * 指定した都道府県市区町村で町域番地取得.
     *
     * @param storeId      店舗ID
     * @param prefectureId 都道府県ID
     * @param cityId       市区町村ID
     * @param languages    言語
     * @return 町域番地情報
     */
    @Query(value = "SELECT DISTINCT                              "
        + "t1.block_id AS blockId,                               "
        + "t2.block_name ->> :languages AS blockName             "
        + "FROM m_delivery_area t1                               "
        + "INNER JOIN m_area t2                                  "
        + "ON t2.prefecture_id = t1.prefecture_id                "
        + "AND t2.city_id = t1.city_id                           "
        + "AND t2.block_id = t1.block_id                         "
        + "AND t2.del_flag = 0                                   "
        + "WHERE t1.store_id = :storeId                          "
        + "AND t1.prefecture_id = :prefectureId                  "
        + "AND t1.city_id = :cityId                              "
        + "AND t1.del_flag = 0                                   "
        + "order by t1.block_id ASC ", nativeQuery = true)
    List<Map<String, Object>> findCityByStoreIdAndPrefectureIdAndCityId(
        @Param("storeId") String storeId,
        @Param("prefectureId") String prefectureId, @Param("cityId") String cityId,
        @Param("languages") String languages);

    /**
     * エリア情報取得.
     *
     * @param storeId   店舗ID
     * @param languages 言語
     * @return エリア情報
     */
    @Query(value = "SELECT DISTINCT                           " +
        "t1.prefecture_id AS prefectureId,                    " +
        "t1.city_id AS cityId,                                " +
        "t1.block_id AS blockId,                              " +
        "(t2.prefecture_name ->> :languages) ||               " +
        "(t2.city_name ->> :languages) ||                     " +
        "(t2.block_name ->> :languages) AS areaText           " +
        "from m_delivery_area t1                              " +
        "inner join m_area t2                                 " +
        "on t2.prefecture_id = t1.prefecture_id               " +
        "and t2.city_id = t1.city_id                          " +
        "and t2.block_id = t1.block_id                        " +
        "and t2.del_flag = 0                                  " +
        "where t1.store_id = :storeId                         " +
        "and t1.del_flag = 0                                  " +
        "order by t1.prefecture_id asc, t1.city_id asc, t1.block_id asc ", nativeQuery = true)
    List<Map<String, Object>> getAreaList(@Param("storeId") String storeId,
        @Param("languages") String languages);

    /**
     * 配達区域削除.
     *
     * @param storeId 店舗ID
     */
    @Modifying
    @Query(value = ""
        + "delete from m_delivery_area "
        + "WHERE store_id = :storeId ", nativeQuery = true)
    void deleteByStoreId(@Param("storeId") String storeId);
}
