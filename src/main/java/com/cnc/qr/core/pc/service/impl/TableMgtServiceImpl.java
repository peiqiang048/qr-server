package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MTable;
import com.cnc.qr.common.entity.MTableIndex;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MTableIndexRepository;
import com.cnc.qr.common.repository.MTableRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.GetTableTypeInfoDto;
import com.cnc.qr.core.pc.model.AreaIdDto;
import com.cnc.qr.core.pc.model.AreaInfoDto;
import com.cnc.qr.core.pc.model.DeleteAreaInputDto;
import com.cnc.qr.core.pc.model.DeleteTableInputDto;
import com.cnc.qr.core.pc.model.GetAreaInputDto;
import com.cnc.qr.core.pc.model.GetAreaListInputDto;
import com.cnc.qr.core.pc.model.GetAreaListOutputDto;
import com.cnc.qr.core.pc.model.GetAreaOutputDto;
import com.cnc.qr.core.pc.model.GetItemList;
import com.cnc.qr.core.pc.model.GetTableInputDto;
import com.cnc.qr.core.pc.model.GetTableListInputDto;
import com.cnc.qr.core.pc.model.GetTableListOutputDto;
import com.cnc.qr.core.pc.model.GetTableOutputDto;
import com.cnc.qr.core.pc.model.RegisterAreaInputDto;
import com.cnc.qr.core.pc.model.RegisterTableInputDto;
import com.cnc.qr.core.pc.model.TableIdDto;
import com.cnc.qr.core.pc.model.TableInfoDto;
import com.cnc.qr.core.pc.service.TableMgtService;
import com.cnc.qr.security.until.SecurityUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * テーブル管理サービス実装クラス.
 */
@Service
@Transactional
public class TableMgtServiceImpl implements TableMgtService {

    /**
     * テーブルマスタリポジトリ.
     */
    @Autowired
    private MTableRepository tableRepository;

    /**
     * テーブル索引マスタリポジトリ.
     */
    @Autowired
    private MTableIndexRepository tableIndexRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * コードマスタリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * テーブル一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return テーブル一覧情報
     */
    @Override
    public GetTableListOutputDto getTableList(GetTableListInputDto inputDto, Pageable pageable) {

        // テーブル一覧取得
        Page<Map<String, Object>> tableListMap = tableRepository
            .findStoreIdTableInfo(inputDto.getStoreId(), pageable);

        List<TableInfoDto> tableList = new ArrayList<>();
        tableListMap.getContent().forEach(stringObjectMap -> tableList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), TableInfoDto.class)));

        // 結果DTO初期化
        GetTableListOutputDto outDto = new GetTableListOutputDto();
        outDto.setTableList(tableList);
        outDto.setTotalCount(tableListMap.getTotalElements());

        return outDto;
    }

    /**
     * テーブル情報取得.
     *
     * @param inputDto 取得条件
     * @return テーブル情報
     */
    @Override
    public GetTableOutputDto getTable(GetTableInputDto inputDto) {

        // 結果DTO初期化
        GetTableOutputDto outDto = new GetTableOutputDto();

        // 更新の場合
        if (inputDto.getTableId() != null) {
            // テーブル情報取得
            MTable tableInfo = tableRepository
                .findByStoreIdAndTableIdAndDelFlag(inputDto.getStoreId(), inputDto.getTableId(),
                    Flag.OFF.getCode());

            // 検索結果0件の場合
            if (tableInfo == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            outDto.setTableName(tableInfo.getTableName());
            outDto.setAreaId(tableInfo.getTableIndexId());
            outDto.setTableSeatCount(tableInfo.getTableSeatCount());
            outDto.setTableType(tableInfo.getTableType());
        }

        // エリア情報取得
        List<AreaInfoDto> areaList = tableIndexRepository
            .findAreaInfoByStoreId(inputDto.getStoreId());
        outDto.setAreaList(areaList);

        // 席種類情報を取得する
        List<Map<String, Object>> tableTypeListMap = codeRepository
            .findTableTypeInfo(inputDto.getStoreId(), CommonConstants.CODE_GROUP_TABLE_TYPE);

        // 席種類情報を編集する
        List<GetTableTypeInfoDto> tableTypeList = new ArrayList<>();
        tableTypeListMap.forEach(stringObjectMap -> {
            GetTableTypeInfoDto tableTypeInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetTableTypeInfoDto.class);
            tableTypeList.add(tableTypeInfo);
        });

        outDto.setTableTypeList(tableTypeList);

        return outDto;
    }

    /**
     * テーブル情報編集.
     *
     * @param inputDto テーブル情報
     */
    @Override
    public void registerTable(RegisterTableInputDto inputDto) {

        // 現在日付
        ZonedDateTime nowTime = DateUtil.getNowDateTime();

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 編集の場合
        if (inputDto.getTableId() != null) {

            // 指定テーブルIDのテーブル情報ロック
            MTable tableInfo = tableRepository
                .findByTableIdForLock(inputDto.getStoreId(), inputDto.getTableId());

            // 検索結果0件の場合
            if (tableInfo == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // テーブル情報更新
            tableInfo.setTableName(inputDto.getTableName());
            tableInfo.setTableSeatCount(inputDto.getTableSeatCount());
            tableInfo.setTableIndexId(inputDto.getAreaId());
            tableInfo.setTableType(inputDto.getTableType());
            tableInfo.setUpdOperCd(userOperCd);
            tableInfo.setUpdDateTime(nowTime);
            tableInfo.setVersion(tableInfo.getVersion() + 1);
            tableRepository.save(tableInfo);

            // 登録の場合
        } else {

            // テーブルIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("m_table"); // テーブル名
            getSeqNoInputDto.setItem("table_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // テーブル情報登録
            MTable table = new MTable();
            table.setStoreId(inputDto.getStoreId());
            table.setTableId(getSeqNo.getSeqNo());
            table.setTableName(inputDto.getTableName());
            table.setTableSeatCount(inputDto.getTableSeatCount());
            table.setTableIndexId(inputDto.getAreaId());
            table.setTableType(inputDto.getTableType());
            table.setDelFlag(Flag.OFF.getCode());
            table.setInsDateTime(nowTime);
            table.setInsOperCd(userOperCd);
            table.setUpdDateTime(nowTime);
            table.setUpdOperCd(userOperCd);
            table.setVersion(0);
            tableRepository.save(table);
        }
    }

    /**
     * テーブル情報削除.
     *
     * @param inputDto テーブル情報
     */
    @Override
    public void deleteTable(DeleteTableInputDto inputDto) {

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // テーブルID取得
        List<Integer> tableIdList = new ArrayList<>();
        for (TableIdDto tableId : inputDto.getTableList()) {
            tableIdList.add(tableId.getTableId());
        }

        // 指定テーブルID削除
        tableRepository.updateDelFlagByTableId(inputDto.getStoreId(), tableIdList, userOperCd,
            DateUtil.getNowDateTime());
    }

    /**
     * エリア一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return エリア一覧情報
     */
    @Override
    public GetAreaListOutputDto getAreaList(GetAreaListInputDto inputDto) {

        // エリア情報取得
        List<AreaInfoDto> areaList = tableIndexRepository
            .findAreaInfoByStoreId(inputDto.getStoreId());
        for (int i = 0; i < areaList.size(); i++) {
            areaList.get(i).setNum(i + 1);
        }

        // 結果DTO初期化
        GetAreaListOutputDto outDto = new GetAreaListOutputDto();
        outDto.setAreaList(areaList);

        return outDto;
    }

    /**
     * エリア情報取得.
     *
     * @param inputDto 取得条件
     * @return エリア情報
     */
    @Override
    public GetAreaOutputDto getArea(GetAreaInputDto inputDto) {

        // エリア情報取得
        MTableIndex areaInfo = tableIndexRepository
            .findByStoreIdAndTableIndexIdAndDelFlag(inputDto.getStoreId(), inputDto.getAreaId(),
                Flag.OFF.getCode());

        // 検索結果0件の場合
        if (areaInfo == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.066", (Object) null));
        }

        // 結果DTO初期化
        GetAreaOutputDto outDto = new GetAreaOutputDto();
        outDto.setAreaName(areaInfo.getAreaName());

        return outDto;
    }

    /**
     * エリア情報編集.
     *
     * @param inputDto テーブル情報
     */
    @Override
    public void registerArea(RegisterAreaInputDto inputDto) {

        // 現在日付
        ZonedDateTime nowTime = DateUtil.getNowDateTime();

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 編集の場合
        if (inputDto.getAreaId() != null) {

            // 指定エリア情報ロック
            MTableIndex areaInfo = tableIndexRepository
                .findByAreaIdForLock(inputDto.getStoreId(), inputDto.getAreaId());

            // 検索結果0件の場合
            if (areaInfo == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.066", (Object) null));
            }

            // エリア情報更新
            areaInfo.setAreaName(inputDto.getAreaName());
            areaInfo.setUpdOperCd(userOperCd);
            areaInfo.setUpdDateTime(nowTime);
            areaInfo.setVersion(areaInfo.getVersion() + 1);
            tableIndexRepository.save(areaInfo);

            // 登録の場合
        } else {

            // エリアIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("m_table_index"); // テーブル名
            getSeqNoInputDto.setItem("table_index_id"); // 項目
            getSeqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // エリア情報登録
            MTableIndex tableIndex = new MTableIndex();
            tableIndex.setStoreId(inputDto.getStoreId());
            tableIndex.setTableIndexId(getSeqNo.getSeqNo());
            tableIndex.setAreaName(inputDto.getAreaName());
            tableIndex.setDelFlag(Flag.OFF.getCode());
            tableIndex.setInsDateTime(nowTime);
            tableIndex.setInsOperCd(userOperCd);
            tableIndex.setUpdDateTime(nowTime);
            tableIndex.setUpdOperCd(userOperCd);
            tableIndex.setVersion(0);
            tableIndexRepository.save(tableIndex);
        }
    }

    /**
     * エリア情報削除.
     *
     * @param inputDto テーブル情報
     */
    @Override
    public void deleteArea(DeleteAreaInputDto inputDto) {

        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // エリアID取得
        List<Integer> areaIdList = new ArrayList<>();
        for (AreaIdDto areaId : inputDto.getAreaList()) {
            areaIdList.add(areaId.getAreaId());
        }

        // 指定エリアID削除
        tableIndexRepository.updateDelFlagByTableId(inputDto.getStoreId(), areaIdList, userOperCd,
            DateUtil.getNowDateTime());
    }
}
