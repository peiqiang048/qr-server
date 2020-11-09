package com.cnc.qr.common.repository;

import com.cnc.qr.common.entity.MSequence;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * シーケンスマスタリポジトリ.
 */
@SuppressWarnings("unused")
@Repository
public interface MSequenceRepository extends JpaRepository<MSequence, Long> {

    /**
     * シーケンスNo取得.
     */
    @Query(value = "INSERT INTO m_sequence("
        + "store_id,"
        + "table_name,"
        + "item,"
        + "seq_no,"
        + "del_flag,"
        + "ins_oper_cd,"
        + "ins_date_time,"
        + "upd_oper_cd,"
        + "upd_date_time,"
        + "version)"
        + "VALUES( "
        + ":storeId,"
        + ":tableName,"
        + ":item,"
        + ":seqNo,"
        + ":delFlag,"
        + ":insOperCd,"
        + ":insDateTime,"
        + ":updOperCd,"
        + ":updDateTime,"
        + ":version)"
        + "ON conflict(store_id,table_name,item) "
        + "DO UPDATE SET seq_no = m_sequence.seq_no + 1,"
        + "upd_oper_cd = :updOperCd,"
        + "upd_date_time = :updDateTime,"
        + "version = m_sequence.version + 1 returning m_sequence.seq_no ", nativeQuery = true)
    Integer getSeqNo(@Param("storeId") String storeId,
        @Param("tableName") String tableName,
        @Param("item") String item,
        @Param("seqNo") Integer seqNo,
        @Param("delFlag") Integer delFlag,
        @Param("insOperCd") String insOperCd,
        @Param("insDateTime") ZonedDateTime insDateTime,
        @Param("updOperCd") String updOperCd,
        @Param("updDateTime") ZonedDateTime updDateTime,
        @Param("version") Integer version);

}
