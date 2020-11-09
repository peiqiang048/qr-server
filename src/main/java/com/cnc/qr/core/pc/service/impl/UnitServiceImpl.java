package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MUnit;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MUnitRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.BuffetDto;
import com.cnc.qr.core.pc.model.GetUnitInputDto;
import com.cnc.qr.core.pc.model.GetUnitOutputDto;
import com.cnc.qr.core.pc.model.RegistUnitInputDto;
import com.cnc.qr.core.pc.model.UnitDelInputDto;
import com.cnc.qr.core.pc.model.UnitDto;
import com.cnc.qr.core.pc.model.UnitInputDto;
import com.cnc.qr.core.pc.model.UnitListInputDto;
import com.cnc.qr.core.pc.model.UnitListOutputDto;
import com.cnc.qr.core.pc.service.UnitService;
import com.cnc.qr.security.until.SecurityUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单位サービス実装クラス.
 */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    /**
     * 单位リポジトリ.
     */
    @Autowired
    private MUnitRepository unitRepository;

    /**
     * 共通部品サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;
    
    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * 单位ー覧情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public UnitListOutputDto getUnitList(UnitListInputDto inputDto, Pageable pageable) {

        // 单位一覧情報を取得する
        Page<Map<String, Object>> unitList = unitRepository
            .findByStoreIdUnitInfo(inputDto.getStoreId(), inputDto.getLanguages(), pageable);

        // 单位一覧情報を作成する
        List<UnitDto> unitInfoList = new ArrayList<>();
        unitList.getContent().forEach(stringObjectMap -> unitInfoList.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), UnitDto.class)));

        // アウトプット
        UnitListOutputDto outputDto = new UnitListOutputDto();
        outputDto.setUnitList(unitInfoList);
        outputDto.setTotalCount(unitList.getTotalElements());
        return outputDto;
    }

    /**
     * 单位情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetUnitOutputDto getUnit(GetUnitInputDto inputDto) {

        // 单位情報を設定する
        GetUnitOutputDto outputDto = new GetUnitOutputDto();

        // 更新の場合
        if (inputDto.getUnitId() != null) {
            // 单位名称情報を取得する
            MUnit unit = unitRepository
                .findByStoreIdAndUnitIdAndDelFlag(inputDto.getStoreId(), inputDto.getUnitId(),
                    Flag.OFF.getCode());

            // 单位名称を設定する
            outputDto.setUnitName(unit.getUnitName());
        }

        return outputDto;
    }

    /**
     * 单位保存.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void saveUnit(RegistUnitInputDto inputDto) {

        // ユーザID取得
        String userOperCd = getUserOperCd();

        // システム日付
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        if (Objects.isNull(inputDto.getUnitId())) {
            //新規

            GetSeqNoInputDto seqNoInputDto = new GetSeqNoInputDto();
            seqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            seqNoInputDto.setTableName("m_unit"); // テーブル名
            seqNoInputDto.setItem("unit_id"); // 項目
            seqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto seqNo = itemInfoSharedService.getSeqNo(seqNoInputDto);

            // 单位情報を登録する
            unitRepository.insertUnit(inputDto.getStoreId(),
                seqNo.getSeqNo(), inputDto.getUnitName(), userOperCd, nowDateTime);
        } else {
            //変更

            // 指定单位IDのテーブル情報ロック
            MUnit unitInfo = unitRepository
                .findByUnitIdForLock(inputDto.getStoreId(), inputDto.getUnitId());

            // 検索結果0件の場合
            if (unitInfo == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // 单位情報を更新する
            unitRepository.updateUnit(inputDto.getStoreId(), inputDto.getUnitName(),
                Integer.valueOf(inputDto.getUnitId()), userOperCd, nowDateTime);
        }
    }

    /**
     * 单位削除.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void delUnit(UnitDelInputDto inputDto) {

        // 单位ID取得
        List<Integer> unitIdList = new ArrayList<>();
        for (UnitInputDto unitDto : inputDto.getUnitList()) {
            unitIdList.add(unitDto.getUnitId());
        }
        // 登録更新者
        String userOperCd = getUserOperCd();
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        
        Integer hasItemCount = itemRepository.getItemCount(
            inputDto.getStoreId(), unitIdList);

        if (hasItemCount != 0) {

            throw new BusinessException("6001",
                ResultMessages.error().add("e.qr.ph.069", (Object) null));
        }

        // 单位削除
        unitRepository.updateDelFlagByUnitId(inputDto.getStoreId(), unitIdList, userOperCd,
            nowDateTime);
    }

    /**
     * 登録更新者ID取得.
     */
    private String getUserOperCd() {
        // 登録更新者
        String userOperCd = CommonConstants.OPER_CD_STORE_PC;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }
        return userOperCd;
    }
}
