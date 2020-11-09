package com.cnc.qr.core.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ItemStatus;
import com.cnc.qr.common.constants.CodeConstants.ReservateClassification;
import com.cnc.qr.common.constants.CodeConstants.ReservateStatus;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MStore;
import com.cnc.qr.common.entity.MTableIndex;
import com.cnc.qr.common.repository.MControlRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.MTableIndexRepository;
import com.cnc.qr.common.repository.MTableRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.AreaTypeDto;
import com.cnc.qr.core.order.model.AreaTypeInputDto;
import com.cnc.qr.core.order.model.AreaTypeOutputDto;
import com.cnc.qr.core.order.model.ChangeTableInputDto;
import com.cnc.qr.core.order.model.GetAreaTableListOutputDto;
import com.cnc.qr.core.order.model.GetDefaultUseTimeDto;
import com.cnc.qr.core.order.model.GetTableInfoInputDto;
import com.cnc.qr.core.order.model.GetTableInfoOutputDto;
import com.cnc.qr.core.order.model.GetTableListInputDto;
import com.cnc.qr.core.order.model.GetTableListOutputDto;
import com.cnc.qr.core.order.model.OrderDto;
import com.cnc.qr.core.order.model.TableDto;
import com.cnc.qr.core.order.model.TableInfoDto;
import com.cnc.qr.core.order.model.TableInputDto;
import com.cnc.qr.core.order.model.TableListDto;
import com.cnc.qr.core.order.model.TableOrderDto;
import com.cnc.qr.core.order.model.TableOutputDto;
import com.cnc.qr.core.order.service.TableInfoService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 座席情報取得サービス実装クラス.
 */
@Service
@Transactional
public class TableInfoServiceImpl implements TableInfoService {

    /**
     * テーブルマスタリポジトリ.
     */
    @Autowired
    private MTableRepository tableRepository;

    /**
     * コントローラリポジトリ.
     */
    @Autowired
    private MControlRepository controlRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * 注文サマリテーブルタリポジトリ.
     */
    @Autowired
    private OOrderSummaryRepository orderSummaryRepository;


    /**
     * テーブル索引マスタリポジトリ.
     */
    @Autowired
    private MTableIndexRepository tableIndexRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 座席一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 座席一覧情報
     */
    @Override
    public GetTableListOutputDto getTableList(GetTableListInputDto inputDto) {

        // 結果DTO初期化
        GetTableListOutputDto outDto = new GetTableListOutputDto();

        // 利用時間情報を取得する
        Map<String, Object> useTimeMap = storeRepository
            .findDefaultUseTimeByStoreId(inputDto.getStoreId());

        // 利用時間情報を変換する
        GetDefaultUseTimeDto defaultUseTimeDto = JSONObject
            .parseObject(JSONObject.toJSONString(useTimeMap), GetDefaultUseTimeDto.class);

        // 登録日時
        ZonedDateTime dateTime = DateUtil.getNowDateTime();

        // 座席一覧情報を取得する
        List<Map<String, Object>> tableData = tableRepository
            .findTableListByAreaId(inputDto.getStoreId(),
                inputDto.getAreaId() == null ? -1 : inputDto.getAreaId(),
                ItemStatus.UNCONFIRMED.getCode(), Flag.OFF.getCode().toString(),
                ReservateClassification.TABLE.getCode(), ReservateStatus.RESERVATING.getCode(),
                dateTime, defaultUseTimeDto.getDefaultUseTime());

        // 検索結果0件の場合
        if (CollectionUtils.isEmpty(tableData)) {
            outDto.setTableList(new ArrayList<>());
            return outDto;
        }

        // 注文情報を設定する
        List<TableInfoDto> tableDataList = new ArrayList<>();
        tableData.forEach(stringObjectMap -> tableDataList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), TableInfoDto.class)));
        outDto.setTableList(tableDataList);

        return outDto;
    }

    /**
     * 座席変更.
     *
     * @param inputDto 座席変更データ
     */
    @Override
    public void changeTable(ChangeTableInputDto inputDto) {

        // 注文サマリーデータをロックする
        orderSummaryRepository.findByStoreIdAndReceivablesIdAndDelFlag(inputDto.getStoreId(),
            inputDto.getReceivablesId(), Flag.OFF.getCode());

        // ユーザID取得
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 座席データを変更する
        orderSummaryRepository.updateTableIdByReceivablesId(inputDto.getStoreId(),
            inputDto.getReceivablesId(), inputDto.getNewTableId(), userOperCd,
            ZonedDateTime.now());
    }

    /**
     * エリア.
     *
     * @param inputDto エリア
     */
    @Override
    public AreaTypeOutputDto getAreaTypeList(AreaTypeInputDto inputDto) {

        // 店舗情報取得
        MStore store = storeRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // テーブルエリア取得
        List<MTableIndex> tableIndexList = tableIndexRepository
            .findByStoreIdAndDelFlagOrderByTableIndexIdAsc(inputDto.getStoreId(),
                Flag.OFF.getCode());

        List<AreaTypeDto> areaTypeList = new ArrayList<>();
        tableIndexList.forEach(tableIndex -> {
            AreaTypeDto areaTypeDto = new AreaTypeDto();
            areaTypeDto.setAreaId(tableIndex.getTableIndexId()); // エリアID
            areaTypeDto.setAreaName(tableIndex.getAreaName()); // エリア名
            areaTypeList.add(areaTypeDto);
        });

        // アウトプット
        AreaTypeOutputDto areaTypeOutputDto = new AreaTypeOutputDto();

        // 店舗名
        areaTypeOutputDto.setStoreName(store.getStoreName());

        // テーブルエリア
        areaTypeOutputDto.setAreaTypeList(areaTypeList);

        return areaTypeOutputDto;
    }

    /**
     * テーブル情報.
     *
     * @param inputDto テーブル情報
     */
    @Override
    public TableOutputDto getTable(TableInputDto inputDto) {

        // テーブル情報取得
        TableDto tableDto = tableRepository
            .findTableByTableId(inputDto.getStoreId(), inputDto.getTableId());
        // テーブルアウトプット情報
        TableOutputDto tableOutputDto = new TableOutputDto();
        // テーブル名
        tableOutputDto.setTableName(tableDto.getTableName());
        // テーブルエリア名
        tableOutputDto.setAreaTypeName(tableDto.getAreaName());
        // テーブルエリア名
        tableOutputDto.setTableSeatCount(tableDto.getTableSeatCount());

        // テーブルを単位に注文リスト取得
        List<TableOrderDto> tableOrderList = orderSummaryRepository
            .findByTableId(inputDto.getStoreId(), inputDto.getTableId(),
                CodeConstants.OrderStatus.ORDER.getCode(),
                CodeConstants.PayStatus.PAY_NOT_ALREADY.getCode());

        // テーブルを単位に注文リスト取得
        List<Map<String, Object>> tableNotOrderList = orderSummaryRepository
            .findByTableIdNotOrder(inputDto.getStoreId(), inputDto.getTableId(),
                CodeConstants.OrderStatus.ORDER.getCode());

        List<TableOrderDto> tableNotOrderListTemp = new ArrayList<>();
        tableNotOrderList.forEach(stringObjectMap -> tableNotOrderListTemp.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), TableOrderDto.class)));

        tableOrderList.addAll(tableOrderList.size(), tableNotOrderListTemp);

        // 受付IDでグループ化
        Map<String, List<TableOrderDto>> tableOrderGroupMap = tableOrderList.stream()
            .collect(Collectors.groupingBy(TableOrderDto::getReceivablesId));

        List<OrderDto> tableOrderInfoList = new ArrayList<>();
        tableOutputDto.setCustomerCount(0);
        // オーダー数
        tableOutputDto.setOrderCount(tableOrderGroupMap.size());
        tableOrderGroupMap.forEach((key, itemInfoList) -> {
            OrderDto orderDto = new OrderDto();
            //受付ID
            orderDto.setReceivablesId(itemInfoList.get(0).getReceivablesId());
            //受付No
            orderDto.setReceivablesNo(String.format("%04d", itemInfoList.get(0).getReceptionNo()));
            //注文金額
            orderDto.setOrderPrice(itemInfoList.get(0).getOrderAmount());
            // 客数
            tableOutputDto.setCustomerCount(
                tableOutputDto.getCustomerCount() + itemInfoList.get(0).getCustomerCount());

            // 注文ステータス 発行のオーダー
            if (Objects.isNull(itemInfoList.get(0).getItemStatus())
                && BigDecimal.ZERO.compareTo(itemInfoList.get(0).getOrderAmount()) == 0) {
                orderDto.setOrderStatus(ItemStatus.CONFIRMED.getCode());
            } else {

                // 注文ステータス 正常注文のオーダー
                if (itemInfoList.stream()
                    .anyMatch(m -> m.getItemStatus().equals(ItemStatus.UNCONFIRMED.getCode()))) {
                    orderDto.setOrderStatus(ItemStatus.UNCONFIRMED.getCode());
                } else {
                    orderDto.setOrderStatus(ItemStatus.CONFIRMED.getCode());
                }
            }

            // 注文タイプ (ラストのレコード)
            itemInfoList.sort(Comparator.comparing(TableOrderDto::getOrderId));
            orderDto.setOrderType(itemInfoList.get(0).getOrderType());

            //順番用項目

            orderDto.setUpdDateTime(itemInfoList.get(0).getUpdDateTime());
            tableOrderInfoList.add(orderDto);
        });

        tableOrderInfoList.sort(Comparator.comparing(OrderDto::getUpdDateTime).reversed());
        tableOutputDto.setOrderList(tableOrderInfoList);
        return tableOutputDto;
    }

    /**
     * 座席一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 座席一覧情報
     */
    @Override
    public GetAreaTableListOutputDto getAreaTableList(GetTableListInputDto inputDto) {

        // 結果DTO初期化
        GetAreaTableListOutputDto outDto = new GetAreaTableListOutputDto();

        // 桌台一览取得
        List<TableListDto> tableList = tableRepository.getTableList(inputDto.getStoreId(),
            inputDto.getAreaId());

        outDto.setTableList(tableList);

        return outDto;
    }

    /**
     * テーブル情報取得.
     *
     * @param inputDto エリア
     * @return テーブル情報
     */
    @Override
    public GetTableInfoOutputDto getTableInfo(GetTableInfoInputDto inputDto) {

        // テーブル情報取得
        TableListDto tableInfo = tableRepository
            .getTableInfoByReceivablesId(inputDto.getStoreId(), inputDto.getReceivablesId());

        // 結果DTO初期化
        GetTableInfoOutputDto outDto = new GetTableInfoOutputDto();

        // テーブル情報設定
        if (tableInfo != null) {
            outDto.setTableId(tableInfo.getTableId());
            outDto.setTableName(tableInfo.getTableName());
        }

        return outDto;
    }
}
