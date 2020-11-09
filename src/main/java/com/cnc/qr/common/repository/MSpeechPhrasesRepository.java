package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MSpeechPhrases;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 音声認識マスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MSpeechPhrasesRepository extends JpaRepository<MSpeechPhrases, Long> {

    /**
     * 音声認識連語取得.
     *
     * @param storeId 店舗ID
     * @return 連語情報
     */
    @Query(value = "SELECT " +
        "t1.phrasesId " +
        "FROM MSpeechPhrases t1 " +
        "WHERE t1.storeId = :storeId " +
        "AND t1.delFlag = 0 ")
    List<String> findSpeechPhrases(@Param("storeId") String storeId);

}
