package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.DeleteAreaInputDto;
import com.cnc.qr.core.pc.model.DeleteTableInputDto;
import com.cnc.qr.core.pc.model.GetAreaInputDto;
import com.cnc.qr.core.pc.model.GetAreaListInputDto;
import com.cnc.qr.core.pc.model.GetAreaListOutputDto;
import com.cnc.qr.core.pc.model.GetAreaOutputDto;
import com.cnc.qr.core.pc.model.GetTableInputDto;
import com.cnc.qr.core.pc.model.GetTableListInputDto;
import com.cnc.qr.core.pc.model.GetTableListOutputDto;
import com.cnc.qr.core.pc.model.GetTableOutputDto;
import com.cnc.qr.core.pc.model.RegisterAreaInputDto;
import com.cnc.qr.core.pc.model.RegisterTableInputDto;
import org.springframework.data.domain.Pageable;

/**
 * テーブル管理サービス.
 */
public interface TableMgtService {

    /**
     * テーブル一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return テーブル一覧情報
     */
    GetTableListOutputDto getTableList(GetTableListInputDto inputDto, Pageable pageable);

    /**
     * テーブル情報取得.
     *
     * @param inputDto 取得条件
     * @return テーブル情報
     */
    GetTableOutputDto getTable(GetTableInputDto inputDto);

    /**
     * テーブル情報編集.
     *
     * @param inputDto テーブル情報
     */
    void registerTable(RegisterTableInputDto inputDto);

    /**
     * テーブル情報削除.
     *
     * @param inputDto テーブル情報
     */
    void deleteTable(DeleteTableInputDto inputDto);

    /**
     * エリア一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return エリア一覧情報
     */
    GetAreaListOutputDto getAreaList(GetAreaListInputDto inputDto);

    /**
     * エリア情報取得.
     *
     * @param inputDto 取得条件
     * @return エリア情報
     */
    GetAreaOutputDto getArea(GetAreaInputDto inputDto);

    /**
     * エリア情報編集.
     *
     * @param inputDto テーブル情報
     */
    void registerArea(RegisterAreaInputDto inputDto);

    /**
     * エリア情報削除.
     *
     * @param inputDto テーブル情報
     */
    void deleteArea(DeleteAreaInputDto inputDto);
}
