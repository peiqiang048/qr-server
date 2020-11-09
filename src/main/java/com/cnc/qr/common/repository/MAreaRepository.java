package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MArea;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 区域マスタリポジトリ.
 */
public interface MAreaRepository extends JpaRepository<MArea, String> {

    /**
     * 都道府県情報取得.
     *
     * @param languages 言語
     * @return 都道府県情報
     */
    @Query(value = "SELECT DISTINCT                                  " +
        "t1.prefecture_id AS prefectureId,                    " +
        "t1.prefecture_name ->> :languages AS prefectureName  " +
        "from  m_area t1                                      " +
        "where t1.del_flag = 0                                " +
        "order by t1.prefecture_id asc ", nativeQuery = true)
    List<Map<String, Object>> getPrefectureList(@Param("languages") String languages);

    /**
     * 市区町村情報取得.
     *
     * @param languages    言語
     * @param prefectureId 都道府县
     * @return 市区町村情報
     */
    @Query(value = "SELECT DISTINCT                                   " +
        "t1.city_id AS cityId,                                " +
        "t1.city_name ->> :languages AS cityName              " +
        "from  m_area t1                                      " +
        "where t1.del_flag = 0 " +
        "and t1.prefecture_id = :prefectureId             " +
        "order by t1.city_id asc ", nativeQuery = true)
    List<Map<String, Object>> getCityListByPrefecture(@Param("languages") String languages,
        @Param("prefectureId") String prefectureId);

    /**
     * 町域番地情報取得.
     *
     * @param languages    言語
     * @param prefectureId 都道府县
     * @param cityId       市区町村
     * @return 町域番地情報
     */
    @Query(value = "SELECT  DISTINCT                                  " +
        "t1.block_id AS blockId,                              " +
        "t1.block_name ->> :languages AS blockName            " +
        "from  m_area t1                                      " +
        "where t1.del_flag = 0                                " +
        "and t1.prefecture_id = :prefectureId             " +
        "and t1.city_id = :cityId             " +
        "order by t1.block_id asc ", nativeQuery = true)
    List<Map<String, Object>> getBlockListByPrefecture(@Param("languages") String languages,
        @Param("prefectureId") String prefectureId, @Param("cityId") String cityId);

    /**
     * 市区町村一覧取得.
     *
     * @param languages    言語
     * @param prefectureId 都道府县
     * @return 市区町村一覧
     */
    @Query(value = "SELECT DISTINCT                                   " +
        "t1.city_id AS areaId,                                " +
        "t1.city_name ->> :languages AS areaName              " +
        "from  m_area t1                                      " +
        "where t1.del_flag = 0 " +
        "and t1.prefecture_id = :prefectureId             " +
        "order by t1.city_id asc ", nativeQuery = true)
    List<Map<String, Object>> getCityListByChangePrefecture(@Param("languages") String languages,
        @Param("prefectureId") String prefectureId);

    /**
     * 町域番地一覧取得.
     *
     * @param languages    言語
     * @param prefectureId 都道府县
     * @param cityId       市区町村
     * @return 町域番地一覧
     */
    @Query(value = "SELECT  DISTINCT                                  " +
        "t1.block_id AS areaId,                              " +
        "t1.block_name ->> :languages AS areaName            " +
        "from  m_area t1                                      " +
        "where t1.del_flag = 0                                " +
        "and t1.prefecture_id = :prefectureId             " +
        "and t1.city_id = :cityId             " +
        "order by t1.block_id asc ", nativeQuery = true)
    List<Map<String, Object>> getBlockListByChangeCity(@Param("languages") String languages,
        @Param("prefectureId") String prefectureId, @Param("cityId") String cityId);


}
