package com.cnc.qr.core.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ReservateClassification;
import com.cnc.qr.common.constants.CodeConstants.ReservateStatus;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MTableIndex;
import com.cnc.qr.common.entity.OReservate;
import com.cnc.qr.common.entity.RReservate;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MItemRepository;
import com.cnc.qr.common.repository.MStoreRepository;
import com.cnc.qr.common.repository.MTableIndexRepository;
import com.cnc.qr.common.repository.OReservateRepository;
import com.cnc.qr.common.repository.RReservateRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.BuffetDto;
import com.cnc.qr.core.order.model.CancelReservateInputDto;
import com.cnc.qr.core.order.model.CourseDto;
import com.cnc.qr.core.order.model.FinishReservateInputDto;
import com.cnc.qr.core.order.model.GetAllTableListInputDto;
import com.cnc.qr.core.order.model.GetAllTableListOutputDto;
import com.cnc.qr.core.order.model.GetAreaInfoDto;
import com.cnc.qr.core.order.model.GetDefaultUseTimeDto;
import com.cnc.qr.core.order.model.GetReservateInputDto;
import com.cnc.qr.core.order.model.GetReservateListInputDto;
import com.cnc.qr.core.order.model.GetReservateListOutputDto;
import com.cnc.qr.core.order.model.GetReservateOutputDto;
import com.cnc.qr.core.order.model.GetTableInfoDto;
import com.cnc.qr.core.order.model.GetTableTypeInfoDto;
import com.cnc.qr.core.order.model.RegistReservateInputDto;
import com.cnc.qr.core.order.model.ReservateIdDto;
import com.cnc.qr.core.order.model.ReservateInfoDto;
import com.cnc.qr.core.order.model.SelectBuffetDto;
import com.cnc.qr.core.order.model.SelectCourseDto;
import com.cnc.qr.core.order.model.SelectTableDto;
import com.cnc.qr.core.order.service.ReservateService;
import com.cnc.qr.security.until.SecurityUtils;
import com.github.dozermapper.core.Mapper;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 予約サービス実装クラス.
 */
@Service
@Transactional
public class ReservateServiceImpl implements ReservateService {

    /**
     * 予約テーブルリポジトリ.
     */
    @Autowired
    private OReservateRepository reservateRepository;

    /**
     * 予約関連テーブルリポジトリ.
     */
    @Autowired
    private RReservateRepository rreservateRepository;

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
     * テーブル索引マスタリポジトリ.
     */
    @Autowired
    private MTableIndexRepository tableIndexRepository;

    /**
     * 商品マスタリポジトリ.
     */
    @Autowired
    private MItemRepository itemRepository;

    /**
     * 店舗マスタリポジトリ.
     */
    @Autowired
    private MStoreRepository storeRepository;

    /**
     * Beanマッピング用Mapperクラス.
     */
    @Autowired
    @Qualifier("DozerBeanMapper")
    Mapper beanMapper;

    /**
     * 予約一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 予約情報
     */
    @Override
    public GetReservateListOutputDto getReservateList(GetReservateListInputDto inputDto,
        Pageable pageable) {

        // 予約一覧情報取得
        Page<Map<String, Object>> reservateListMap = reservateRepository
            .findReservateInfoByStoreId(inputDto.getStoreId(), inputDto.getCustomerName(),
                inputDto.getTelNumber(), inputDto.getStatus(),
                StringUtils.trimToEmpty(inputDto.getReservateTimeFrom()),
                StringUtils.trimToEmpty(inputDto.getReservateTimeTo()),
                pageable);

        // 予約一覧情報を編集する
        List<ReservateInfoDto> reservateList = new ArrayList<>();
        reservateListMap.forEach(stringObjectMap -> {
            ReservateInfoDto reservateInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), ReservateInfoDto.class);
            reservateList.add(reservateInfo);
        });

        // 結果DTO初期化
        GetReservateListOutputDto outDto = new GetReservateListOutputDto();

        // 予約一覧情報設定
        outDto.setReservateList(
            new PageImpl<>(reservateList, reservateListMap.getPageable(),
                reservateListMap.getTotalPages()));

        // 予約総件数
        outDto.setReservateCount(reservateListMap.getTotalElements());

        return outDto;
    }

    /**
     * 取消.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void cancelReservate(CancelReservateInputDto inputDto) {
        // 時間取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // ユーザID
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 取消処理を行う
        for (ReservateIdDto reservateId : inputDto.getReservateIdList()) {
            // 予約情報をロックする
            OReservate reservate = reservateRepository
                .findByStoreIdAndDelFlagAndReservateId(inputDto.getStoreId(), Flag.OFF.getCode(),
                    reservateId.getReservateId());

            // 予約情報がNULLである場合
            if (reservate == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // 予約状態を変更する
            reservateRepository.updateStatus(inputDto.getStoreId(),
                reservateId.getReservateId(), ReservateStatus.CANCEL.getCode(),
                userOperCd, nowDateTime);
        }
    }

    /**
     * 来店.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void finishReservate(FinishReservateInputDto inputDto) {
        // 時間取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // ユーザID
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        // 予約情報をロックする
        OReservate reservate = reservateRepository
            .findByStoreIdAndDelFlagAndReservateId(inputDto.getStoreId(), Flag.OFF.getCode(),
                inputDto.getReservateId());

        // 予約情報がNULLである場合
        if (reservate == null) {
            throw new BusinessException("2001",
                ResultMessages.error().add("e.qr.ph.061", (Object) null));
        }

        // 予約状態を変更する
        reservateRepository.updateStatus(inputDto.getStoreId(),
            inputDto.getReservateId(), ReservateStatus.RESERVATED.getCode(),
            userOperCd, nowDateTime);
    }

    /**
     * 予約情報取得.
     *
     * @param inputDto 取得条件
     * @return 予約情報
     */
    @Override
    public GetReservateOutputDto getReservate(GetReservateInputDto inputDto) {

        // 結果DTO初期化
        GetReservateOutputDto outDto = new GetReservateOutputDto();

        // 利用時間情報を取得する
        Map<String, Object> useTimeMap = storeRepository
            .findDefaultUseTimeByStoreId(inputDto.getStoreId());

        // 利用時間情報を変換する
        GetDefaultUseTimeDto defaultUseTimeDto = JSONObject
            .parseObject(JSONObject.toJSONString(useTimeMap), GetDefaultUseTimeDto.class);

        String reservateTime = "";
        BigDecimal useTime = BigDecimal.ZERO;

        // 予約IDが空白ではない場合
        if (!Objects.isNull(inputDto.getReservateId())) {
            // 予約情報を取得する
            Map<String, Object> reservateMap = reservateRepository
                .findReservateInfoByStoreIdAndReservateId(inputDto.getStoreId(),
                    inputDto.getReservateId());

            // 予約情報を編集する
            OReservate rservateData = JSONObject
                .parseObject(JSONObject.toJSONString(reservateMap), OReservate.class);

            outDto.setReservateTime(DateUtil
                .getZonedDateString(rservateData.getReservateTime(), CommonConstants.DATE_FORMAT_DATETIME));
            outDto.setUseTime(rservateData.getUseTime().toString());
            outDto.setCustomerName(rservateData.getCustomerName());
            outDto.setTelNumber(rservateData.getTelNumber());
            outDto.setCustomerCount(rservateData.getCustomerCount());
            outDto.setReservateType(rservateData.getReservateType());

            // コース選択リストを取得する
            List<Map<String, Object>> selectCourseListMap = rreservateRepository
                .findCourseInfoByStoreIdAndReservateId(inputDto.getStoreId(),
                    inputDto.getReservateId());

            // コース選択一覧情報を編集する
            List<SelectCourseDto> courseList = new ArrayList<>();
            selectCourseListMap.forEach(stringObjectMap -> {
                SelectCourseDto courseInfo = JSONObject
                    .parseObject(JSONObject.toJSONString(stringObjectMap), SelectCourseDto.class);
                courseList.add(courseInfo);
            });

            outDto.setSelectCourseList(courseList);

            // 放題選択リストを取得する
            List<Map<String, Object>> selectBuffetListMap = rreservateRepository
                .findBuffetInfoByStoreIdAndReservateId(inputDto.getStoreId(),
                    inputDto.getReservateId());

            // 放題選択一覧情報を編集する
            List<SelectBuffetDto> buffetList = new ArrayList<>();
            selectBuffetListMap.forEach(stringObjectMap -> {
                SelectBuffetDto buffetInfo = JSONObject
                    .parseObject(JSONObject.toJSONString(stringObjectMap), SelectBuffetDto.class);
                buffetList.add(buffetInfo);
            });

            outDto.setSelectBuffetList(buffetList);

            // 席選択リストを取得する
            List<Map<String, Object>> selectTableListMap = rreservateRepository
                .findTableInfoByStoreIdAndReservateId(inputDto.getStoreId(),
                    inputDto.getReservateId());

            // 席選択一覧情報を編集する
            List<SelectTableDto> tableList = new ArrayList<>();
            selectTableListMap.forEach(stringObjectMap -> {
                SelectTableDto tableInfo = JSONObject
                    .parseObject(JSONObject.toJSONString(stringObjectMap), SelectTableDto.class);
                tableList.add(tableInfo);
            });

            outDto.setSelectTableList(tableList);
            outDto.setComment(rservateData.getComment());

            // 予約日時
            reservateTime = DateUtil
                .getZonedDateString(rservateData.getReservateTime(), "yyyy-MM-dd HH:mm:ss");
            // 利用時間
            useTime = rservateData.getUseTime();
        } else {
            // 時間取得
            ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

            outDto.setReservateTime(DateUtil
                .getZonedDateString(nowDateTime, "yyyy-MM-dd HH:mm:ss"));
            outDto.setUseTime(String.format("%.1f", defaultUseTimeDto.getDefaultUseTime()));

            // 予約日時
            reservateTime = DateUtil
                .getZonedDateString(nowDateTime, "yyyy-MM-dd HH:mm:ss");
            // 利用時間
            useTime = defaultUseTimeDto.getDefaultUseTime();
        }

        // エリアリストを取得する
        List<MTableIndex> tableIndexList =
            tableIndexRepository
                .findByStoreIdAndDelFlagOrderByTableIndexIdAsc(inputDto.getStoreId(),
                    Flag.OFF.getCode());

        List<GetAreaInfoDto> areaInfoList = new ArrayList<>();
        for (MTableIndex tableIndex : tableIndexList) {
            GetAreaInfoDto areaInfo = new GetAreaInfoDto();

            areaInfo.setAreaId(tableIndex.getTableIndexId());
            areaInfo.setAreaName(tableIndex.getAreaName());

            areaInfoList.add(areaInfo);
        }

        // エリアリストを設定する
        outDto.setAreaList(areaInfoList);

        // 席一覧情報を取得する
        List<Map<String, Object>> allTableListMap = reservateRepository
            .findAllTableInfoByStoreIdAndReservateTimeAndUseTime(inputDto.getStoreId(),
                reservateTime, useTime, CommonConstants.CODE_GROUP_TABLE_TYPE,
                defaultUseTimeDto.getDefaultUseTime());

        // 席一覧情報を編集する
        List<GetTableInfoDto> allTableList = new ArrayList<>();
        allTableListMap.forEach(stringObjectMap -> {
            GetTableInfoDto tableInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetTableInfoDto.class);
            allTableList.add(tableInfo);
        });

        // 席リストを設定する
        outDto.setTableList(allTableList);

        // コースリストを取得する
        List<Map<String, Object>> courseListMap = itemRepository
            .findCourseInfoByStoreId(inputDto.getStoreId(),
                inputDto.getLanguages());

        // コース情報を編集する
        List<CourseDto> courseList = new ArrayList<>();
        courseListMap.forEach(stringObjectMap -> {
            CourseDto courseInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), CourseDto.class);
            courseList.add(courseInfo);
        });

        // コースリストを設定する
        outDto.setCourseList(courseList);

        // 放題リストを取得する
        List<Map<String, Object>> buffetListMap = itemRepository
            .findBuffetInfoByStoreId(inputDto.getStoreId(),
                inputDto.getLanguages());

        // 放題情報を編集する
        List<BuffetDto> buffetList = new ArrayList<>();
        buffetListMap.forEach(stringObjectMap -> {
            BuffetDto buffetInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), BuffetDto.class);
            buffetList.add(buffetInfo);
        });

        // 放題リストを設定する
        outDto.setBuffetList(buffetList);

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

        // 席種類リストを設定する
        outDto.setTableTypeList(tableTypeList);

        return outDto;
    }

    /**
     * 席一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 席一覧情報
     */
    @Override
    public GetAllTableListOutputDto getAllTableList(GetAllTableListInputDto inputDto) {

        String reservateTime = inputDto.getReservateTime();

        if ("".equals(reservateTime)) {
            reservateTime = DateUtil
                .getZonedDateString(DateUtil.getNowDateTime(), "yyyy-MM-dd HH:mm:ss");
        }

        // 利用時間情報を取得する
        Map<String, Object> useTimeMap = storeRepository
            .findDefaultUseTimeByStoreId(inputDto.getStoreId());

        // 利用時間情報を変換する
        GetDefaultUseTimeDto defaultUseTimeDto = new GetDefaultUseTimeDto();
        defaultUseTimeDto = JSONObject
            .parseObject(JSONObject.toJSONString(useTimeMap), GetDefaultUseTimeDto.class);

        // 席一覧情報を取得する
        List<Map<String, Object>> allTableListMap = reservateRepository
            .findAllTableInfoByStoreIdAndReservateTimeAndUseTime(inputDto.getStoreId(),
                reservateTime, inputDto.getUseTime(), CommonConstants.CODE_GROUP_TABLE_TYPE,
                defaultUseTimeDto.getDefaultUseTime());

        // 席一覧情報を編集する
        List<GetTableInfoDto> allTableList = new ArrayList<>();
        allTableListMap.forEach(stringObjectMap -> {
            GetTableInfoDto tableInfo = JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), GetTableInfoDto.class);
            allTableList.add(tableInfo);
        });

        // 結果DTO初期化
        GetAllTableListOutputDto outDto = new GetAllTableListOutputDto();

        outDto.setTableList(allTableList);

        return outDto;
    }

    /**
     * 予約.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void registReservate(RegistReservateInputDto inputDto) {
        // 時間取得
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        // ユーザID
        String userOperCd = CommonConstants.OPER_CD_STORE_PAD;
        Optional<String> userInfo = SecurityUtils.getCurrentUserLogin();
        if (userInfo.isPresent()) {
            userOperCd = userInfo.get();
        }

        Integer reservateId = inputDto.getReservateId();

        if (Objects.isNull(inputDto.getReservateId())) {

            // 予約IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("o_reservate"); // テーブル名
            getSeqNoInputDto.setItem("reservate_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            reservateId = getSeqNo.getSeqNo();

            // 予約テーブル登録
            OReservate reservate = new OReservate();
            // 店舗ID
            reservate.setStoreId(inputDto.getStoreId());
            // 予約ID
            reservate.setReservateId(reservateId);
            // 来店日時
            if (!"".equals(inputDto.getReservateTime())) {
                reservate.setReservateTime(DateUtil
                    .getZonedDateByString(inputDto.getReservateTime(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                reservate.setReservateTime(nowDateTime);
            }

            // 利用時間
            reservate.setUseTime(new BigDecimal(inputDto.getUseTime()));
            // 顧客名前
            reservate.setCustomerName(inputDto.getCustomerName());
            // 電話番号
            reservate.setTelNumber(inputDto.getTelNumber());
            // 顧客人数
            reservate.setCustomerCount(inputDto.getCustomerCount());
            // 予約区分
            reservate.setReservateType(inputDto.getReservateType());
            // 要望
            reservate.setComment(inputDto.getComment());
            // 状態
            reservate.setStatus(ReservateStatus.RESERVATING.getCode());
            // 削除フラグ
            reservate.setDelFlag(Flag.OFF.getCode());
            // 登録日時
            reservate.setInsDateTime(nowDateTime);
            // 登録者
            reservate.setInsOperCd(userOperCd);
            // 更新日時
            reservate.setUpdDateTime(nowDateTime);
            // 更新者
            reservate.setUpdOperCd(userOperCd);
            // バージョン
            reservate.setVersion(0);

            reservateRepository.save(reservate);
        } else {
            // 予約情報をロックする
            OReservate reservate = reservateRepository
                .findByStoreIdAndDelFlagAndReservateId(inputDto.getStoreId(), Flag.OFF.getCode(),
                    inputDto.getReservateId());

            // 予約情報がNULLである場合
            if (reservate == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // 来店日時
            ZonedDateTime reservateTime = nowDateTime;

            if (!"".equals(inputDto.getReservateTime())) {
                reservateTime = DateUtil
                    .getZonedDateByString(inputDto.getReservateTime(), "yyyy-MM-dd HH:mm:ss");
            }

            // 予約状態を変更する
            reservateRepository.updateReservate(inputDto.getStoreId(),
                inputDto.getReservateId(), reservateTime,
                new BigDecimal(inputDto.getUseTime()), inputDto.getCustomerName(),
                inputDto.getTelNumber(), inputDto.getCustomerCount(),
                inputDto.getReservateType(), inputDto.getComment(),
                userOperCd, nowDateTime);
        }

        // 予約関連データを削除する
        rreservateRepository.deleteByStoreIdAndReservateId(inputDto.getStoreId(),
            reservateId);

        List<SelectCourseDto> selectCourseList = new ArrayList<>();

        // 重複なコースを合併する
        for (SelectCourseDto selectCourseInput : inputDto.getSelectCourseList()) {
            String flag = Flag.OFF.getValue();

            for (SelectCourseDto selectCourseTemp : selectCourseList) {
                if (selectCourseInput.getCourseId().equals(selectCourseTemp.getCourseId())) {
                    SelectCourseDto selectCourse = new SelectCourseDto();
                    selectCourse = selectCourseTemp;
                    selectCourse.setCourseCount(
                        selectCourseInput.getCourseCount() + selectCourseTemp.getCourseCount());
                    selectCourseList.remove(selectCourseTemp);
                    selectCourseList.add(selectCourse);
                    flag = Flag.ON.getValue();
                    break;
                }
            }

            if (Flag.OFF.getValue().equals(flag)) {
                selectCourseList.add(selectCourseInput);
            }
        }

        for (SelectCourseDto selectCourse : selectCourseList) {

            // 予約関連テーブル登録
            RReservate rreservate = new RReservate();
            // 店舗ID
            rreservate.setStoreId(inputDto.getStoreId());
            // 予約ID
            rreservate.setReservateId(reservateId);
            // 分類
            rreservate.setClassification(ReservateClassification.COURSE.getCode());
            // コード
            rreservate.setCode(selectCourse.getCourseId());
            // 数量
            rreservate.setCount(selectCourse.getCourseCount());
            // 削除フラグ
            rreservate.setDelFlag(Flag.OFF.getCode());
            // 登録日時
            rreservate.setInsDateTime(nowDateTime);
            // 登録者
            rreservate.setInsOperCd(userOperCd);
            // 更新日時
            rreservate.setUpdDateTime(nowDateTime);
            // 更新者
            rreservate.setUpdOperCd(userOperCd);
            // バージョン
            rreservate.setVersion(0);

            rreservateRepository.save(rreservate);
        }

        List<SelectBuffetDto> selectBuffetList = new ArrayList<>();

        // 重複な放題を合併する
        for (SelectBuffetDto selectBuffetInput : inputDto.getSelectBuffetList()) {
            String flag = Flag.OFF.getValue();

            for (SelectBuffetDto selectBuffetTemp : selectBuffetList) {
                if (selectBuffetInput.getBuffetId().equals(selectBuffetTemp.getBuffetId())) {
                    SelectBuffetDto selectBuffet = new SelectBuffetDto();
                    selectBuffet = selectBuffetTemp;
                    selectBuffet.setBuffetCount(
                        selectBuffetInput.getBuffetCount() + selectBuffetTemp.getBuffetCount());
                    selectBuffetList.remove(selectBuffetTemp);
                    selectBuffetList.add(selectBuffet);
                    flag = Flag.ON.getValue();
                    break;
                }
            }

            if (Flag.OFF.getValue().equals(flag)) {
                selectBuffetList.add(selectBuffetInput);
            }
        }

        for (SelectBuffetDto selectBuffet : selectBuffetList) {

            // 予約関連テーブル登録
            RReservate rreservate = new RReservate();
            // 店舗ID
            rreservate.setStoreId(inputDto.getStoreId());
            // 予約ID
            rreservate.setReservateId(reservateId);
            // 分類
            rreservate.setClassification(ReservateClassification.BUFFET.getCode());
            // コード
            rreservate.setCode(selectBuffet.getBuffetId());
            // 数量
            rreservate.setCount(selectBuffet.getBuffetCount());
            // 削除フラグ
            rreservate.setDelFlag(Flag.OFF.getCode());
            // 登録日時
            rreservate.setInsDateTime(nowDateTime);
            // 登録者
            rreservate.setInsOperCd(userOperCd);
            // 更新日時
            rreservate.setUpdDateTime(nowDateTime);
            // 更新者
            rreservate.setUpdOperCd(userOperCd);
            // バージョン
            rreservate.setVersion(0);

            rreservateRepository.save(rreservate);
        }

        for (SelectTableDto selectTable : inputDto.getSelectTableList()) {

            // 予約関連テーブル登録
            RReservate rreservate = new RReservate();
            // 店舗ID
            rreservate.setStoreId(inputDto.getStoreId());
            // 予約ID
            rreservate.setReservateId(reservateId);
            // 分類
            rreservate.setClassification(ReservateClassification.TABLE.getCode());
            // コード
            rreservate.setCode(selectTable.getTableId());
            // 数量
            rreservate.setCount(1);
            // 削除フラグ
            rreservate.setDelFlag(Flag.OFF.getCode());
            // 登録日時
            rreservate.setInsDateTime(nowDateTime);
            // 登録者
            rreservate.setInsOperCd(userOperCd);
            // 更新日時
            rreservate.setUpdDateTime(nowDateTime);
            // 更新者
            rreservate.setUpdOperCd(userOperCd);
            // バージョン
            rreservate.setVersion(0);

            rreservateRepository.save(rreservate);
        }
    }

}
