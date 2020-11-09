package com.cnc.qr.core.order.service;

import com.cnc.qr.core.order.model.AreaTypeInputDto;
import com.cnc.qr.core.order.model.AreaTypeOutputDto;
import com.cnc.qr.core.order.model.ChangeTableInputDto;
import com.cnc.qr.core.order.model.GetAreaTableListOutputDto;
import com.cnc.qr.core.order.model.GetTableInfoInputDto;
import com.cnc.qr.core.order.model.GetTableInfoOutputDto;
import com.cnc.qr.core.order.model.GetTableListInputDto;
import com.cnc.qr.core.order.model.GetTableListOutputDto;
import com.cnc.qr.core.order.model.TableInputDto;
import com.cnc.qr.core.order.model.TableOutputDto;

/**
 * 座席情報取得サービス.
 */
public interface TableInfoService {

    /**
     * 座席一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 座席一覧情報
     */
    GetTableListOutputDto getTableList(GetTableListInputDto inputDto);

    /**
     * 座席変更.
     *
     * @param inputDto 座席変更データ
     */
    void changeTable(ChangeTableInputDto inputDto);

    /**
     * エリア情報取得.
     *
     * @param inputDto エリア
     */
    AreaTypeOutputDto getAreaTypeList(AreaTypeInputDto inputDto);

    /**
     * テーブル情報取得.
     *
     * @param inputDto エリア
     */
    TableOutputDto getTable(TableInputDto inputDto);

    /**
     * 座席一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 座席一覧情報
     */
    GetAreaTableListOutputDto getAreaTableList(GetTableListInputDto inputDto);

    /**
     * テーブル情報取得.
     *
     * @param inputDto エリア
     * @return テーブル情報
     */
    GetTableInfoOutputDto getTableInfo(GetTableInfoInputDto inputDto);
}
