package com.cnc.qr.core.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.CallStatus;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MTable;
import com.cnc.qr.common.entity.OCall;
import com.cnc.qr.common.entity.OReceivables;
import com.cnc.qr.common.repository.MTableRepository;
import com.cnc.qr.common.repository.OCallRepository;
import com.cnc.qr.common.repository.OOrderSummaryRepository;
import com.cnc.qr.common.repository.OReceivablesRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.order.model.CallInfoInputDto;
import com.cnc.qr.core.order.model.CallTebleInfoDto;
import com.cnc.qr.core.order.model.GetCallDto;
import com.cnc.qr.core.order.model.GetCallInputDto;
import com.cnc.qr.core.order.model.GetCallOutputDto;
import com.cnc.qr.core.order.model.ModifyCallStatusInputDto;
import com.cnc.qr.core.order.service.CallInfoService;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店員呼出サービス実装クラス.
 */
@Service
@Transactional
public class CallInfoServiceImpl implements CallInfoService {

    /**
     * 受付テーブルリポジトリ.
     */
    @Autowired
    private OReceivablesRepository receivablesRepository;

    /**
     * 呼出マスタリポジトリ.
     */
    @Autowired
    private OCallRepository callRepository;


    /**
     * テーブルマスタリポジトリ.
     */
    @Autowired
    private MTableRepository tableRepository;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * 注文サマリテーブルタリポジトリ.
     */
    @Autowired
    private OOrderSummaryRepository orderSummaryRepository;

    /**
     * 店員呼出.
     *
     * @param inputDto 取得条件
     */
    @Override
    public CallTebleInfoDto callInfo(CallInfoInputDto inputDto) {
        OCall call = new OCall();
        // 店舗ID
        call.setStoreId(inputDto.getStoreId());

        // 呼出IDのシーケンスNo取得
        GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
        getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
        getSeqNoInputDto.setTableName("o_call"); // テーブル名
        getSeqNoInputDto.setItem("call_id"); // 項目
        getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
        GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

        // 呼出ID
        call.setCallId(getSeqNo.getSeqNo());
        // 受付ID
        call.setReceivablesId(inputDto.getReceivablesId());
        // 呼叫次数
        call.setCallNumber(1);
        // 呼出対応状況
        call.setCallStatus(CallStatus.NOT_COMPATIBLE.getCode());
        // テーブルID
        call.setTableId(inputDto.getTableId());
        // 削除フラグ
        call.setDelFlag(Flag.OFF.getCode());
        // 登録者
        call.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
        // 登録日時
        ZonedDateTime dateTime = DateUtil.getNowDateTime();
        call.setInsDateTime(dateTime);
        // 更新者
        call.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
        // 更新日時
        call.setUpdDateTime(dateTime);
        // バージョン
        call.setVersion(0);

        // 呼出テーブル
        callRepository.insertOrUpdate(call.getStoreId(),
            call.getCallId(),
            call.getReceivablesId(),
            call.getCallNumber(),
            call.getCallStatus(),
            call.getTableId(),
            call.getDelFlag(),
            call.getInsOperCd(),
            call.getInsDateTime(),
            call.getUpdOperCd(),
            call.getUpdDateTime(),
            call.getVersion());

        OReceivables receivables = receivablesRepository
            .findByStoreIdAndDelFlagAndReceivablesId(inputDto.getStoreId(),
                Flag.OFF.getCode(), inputDto.getReceivablesId());
        CallTebleInfoDto callTebleInfoDto = new CallTebleInfoDto();

        callTebleInfoDto.setReceptionNo(receivables.getReceptionNo());
        if (!Objects.isNull(inputDto.getTableId())) {
            MTable table = tableRepository
                .findByStoreIdAndTableIdAndDelFlag(inputDto.getStoreId(), inputDto.getTableId(),
                    Flag.OFF.getCode());
            callTebleInfoDto.setTableName(table.getTableName());
        }
        return callTebleInfoDto;

    }

    /**
     * 呼出中取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetCallOutputDto getCallList(GetCallInputDto inputDto) {

        // 呼出中情報取得
        List<Map<String, Object>> callList = callRepository.getCallList(inputDto.getStoreId(),
            inputDto.getReceivablesNo() == null ? -1 : inputDto.getReceivablesNo());

        // 結果DTO初期化
        GetCallOutputDto outDto = new GetCallOutputDto();

        // 呼出中リストを設定する
        List<GetCallDto> calls = new ArrayList<>();

        callList.forEach(stringObjectMap -> calls.add(
            JSONObject.parseObject(JSONObject.toJSONString(stringObjectMap), GetCallDto.class)));

        outDto.setCallList(calls);

        return outDto;
    }

    /**
     * 呼出状態変更.
     *
     * @param inputDto 呼出状態変更データ
     */
    @Override
    public void modifyCallStatus(ModifyCallStatusInputDto inputDto) {

        // 呼出データをロックする
        callRepository
            .findByStoreIdAndDelFlagAndReceivablesId(inputDto.getStoreId(), Flag.OFF.getCode(),
                inputDto.getReceivablesId());

        // 呼出対応状況データを変更する
        callRepository.updateCallStatus(inputDto.getStoreId(),
            inputDto.getReceivablesId(), inputDto.getUserId(),
            ZonedDateTime.now());
    }
}
