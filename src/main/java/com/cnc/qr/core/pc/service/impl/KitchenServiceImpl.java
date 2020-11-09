package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MKitchen;
import com.cnc.qr.common.entity.MPrint;
import com.cnc.qr.common.entity.RKitchenPrint;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.MKitchenRepository;
import com.cnc.qr.common.repository.MPrintRepository;
import com.cnc.qr.common.repository.RKitchenPrintRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.DeleteKitchenInputDto;
import com.cnc.qr.core.pc.model.GetKitchenInputDto;
import com.cnc.qr.core.pc.model.GetKitchenOutputDto;
import com.cnc.qr.core.pc.model.KitchenDto;
import com.cnc.qr.core.pc.model.KitchenInfoDto;
import com.cnc.qr.core.pc.model.KitchenListInputDto;
import com.cnc.qr.core.pc.model.KitchenListOutputDto;
import com.cnc.qr.core.pc.model.KitchenPrintDto;
import com.cnc.qr.core.pc.model.PrintInfoDto;
import com.cnc.qr.core.pc.model.RegistKitchenInputDto;
import com.cnc.qr.core.pc.service.KitchenService;
import com.cnc.qr.security.until.SecurityUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * キッチンサービス実装クラス.
 */
@Service
@Transactional
public class KitchenServiceImpl implements KitchenService {

    /**
     * キッチンリポジトリ.
     */
    @Autowired
    private MKitchenRepository kitchenRepository;

    /**
     * プリンターリポジトリ.
     */
    @Autowired
    private MPrintRepository printRepository;


    /**
     * キチンプリンタ関連テーブルリポジトリ.
     */
    @Autowired
    private RKitchenPrintRepository kitchenPrintRepository;


    /**
     * 共通部品サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * キッチン一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public KitchenListOutputDto getKitchenList(KitchenListInputDto inputDto) {

        // キッチン一覧情報取得
        List<Map<String, Object>> kitchenInfoList = kitchenRepository
            .findKitchenList(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PRINT_BRAND);

        // キッチン一覧情報を変換する
        List<KitchenDto> kitchenList = new ArrayList<>();
        kitchenInfoList.forEach(stringObjectMap -> kitchenList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), KitchenDto.class)));
        for (int i = 0; i < kitchenList.size(); i++) {
            kitchenList.get(i).setNum(i + 1);
        }

        // アウトプット
        KitchenListOutputDto outputDto = new KitchenListOutputDto();
        outputDto.setKitchenList(kitchenList);
        return outputDto;
    }

    /**
     * キッチン情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetKitchenOutputDto getKitchen(GetKitchenInputDto inputDto) {

        // キッチン情報を設定する
        GetKitchenOutputDto outputDto = new GetKitchenOutputDto();

        // 更新の場合
        if (inputDto.getKitchenId() != null) {
            // キッチン名称を取得する
            String kitchenName = kitchenRepository
                .findKitchenName(inputDto.getStoreId(), inputDto.getKitchenId());
            outputDto.setKitchenName(kitchenName);

            // キッチンプリンタ一覧情報取得
            List<Map<String, Object>> kitchenPrintInfoList = printRepository
                .findKitchenPrintInfo(inputDto.getStoreId(), inputDto.getKitchenId());

            // キッチンプリンタ一覧情報を変換する
            List<KitchenPrintDto> kitchenPrintList = new ArrayList<>();
            kitchenPrintInfoList.forEach(stringObjectMap -> kitchenPrintList.add(
                JSONObject
                    .parseObject(JSONObject.toJSONString(stringObjectMap), KitchenPrintDto.class)));
            outputDto.setKitchenPrintList(kitchenPrintList);
        }

        // プリンタ一覧情報を取得する
        List<MPrint> printList = printRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // プリンタ一覧情報を作成する
        List<PrintInfoDto> printInfoList = new ArrayList<>();

        printList.forEach(print -> {
            PrintInfoDto printInfoDto = new PrintInfoDto();
            printInfoDto.setPrintId(print.getPrintId().toString());
            printInfoDto.setPrintName(print.getPrintName());

            printInfoList.add(printInfoDto);
        });
        outputDto.setPrintList(printInfoList);

        return outputDto;
    }

    /**
     * キッチン編集.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void registKitchen(RegistKitchenInputDto inputDto) {

        // ユーザID取得
        String userOperCd = getUserOperCd();

        // システム日付
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // キッチンID
        Integer kitchenId = inputDto.getKitchenId();

        // キッチンIDがNULLである場合、キッチン情報を登録する
        if (Objects.isNull(inputDto.getKitchenId())) {
            // キッチンIDを採番する
            GetSeqNoInputDto seqNoInputDto = new GetSeqNoInputDto();

            seqNoInputDto.setStoreId(inputDto.getStoreId());
            seqNoInputDto.setTableName("m_kitchen");
            seqNoInputDto.setItem("kitchen_id");
            seqNoInputDto.setOperCd(userOperCd);

            GetSeqNoOutputDto seqNo = itemInfoSharedService.getSeqNo(seqNoInputDto);

            kitchenId = seqNo.getSeqNo();

            MKitchen kitchen = new MKitchen();
            // 店舗ID
            kitchen.setStoreId(inputDto.getStoreId());
            // キッチンID
            kitchen.setKitchenId(seqNo.getSeqNo());
            // キッチン名
            kitchen.setKitchenName(inputDto.getKitchenName());
            // 削除フラグ
            kitchen.setDelFlag(Flag.OFF.getCode());
            // 登録者コード
            kitchen.setInsOperCd(userOperCd);
            // 登録者日時
            kitchen.setInsDateTime(nowDateTime);
            // 更新者コード
            kitchen.setUpdOperCd(userOperCd);
            // 更新者日時
            kitchen.setUpdDateTime(nowDateTime);
            // バッジョン
            kitchen.setVersion(0);

            kitchenRepository.save(kitchen);
            // 上記以外の場合、キッチン情報を更新する
        } else {
            // 指定キッチン情報をロックする
            MKitchen kitchen = kitchenRepository.findKitchenForLock(inputDto.getStoreId(),
                inputDto.getKitchenId());

            // キッチン情報がNULLである場合
            if (kitchen == null) {
                throw new BusinessException("2001",
                    ResultMessages.error().add("e.qr.ph.061", (Object) null));
            }

            // キッチン情報を更新する
            kitchenRepository.updateKitchen(inputDto.getStoreId(), inputDto.getKitchenId(),
                inputDto.getKitchenName(), userOperCd, nowDateTime);
        }

        // プリンタID有無判断
        if (!Objects.isNull(inputDto.getKitchenPrintList())) {

            // キッチンプリンタIDリストを編集する
            List<Integer> kitchenPrintIdList = new ArrayList<>();
            for (KitchenPrintDto kitchenPrint : inputDto.getKitchenPrintList()) {
                kitchenPrintIdList.add(Integer.parseInt(kitchenPrint.getPrintId()));
            }

            List<Integer> kitchenIdList = new ArrayList<>();
            kitchenIdList.add(kitchenId);

            // キッチンプリンタ関連情報を削除する
            kitchenPrintRepository
                .deleteByStoreIdAndKitchenId(inputDto.getStoreId(), kitchenIdList);

            // キチンプリンタ関連情報を登録する
            for (Integer kitchenPrintId : kitchenPrintIdList) {
                RKitchenPrint kitchenPrint = new RKitchenPrint();
                kitchenPrint.setStoreId(inputDto.getStoreId());
                kitchenPrint.setKitchenId(kitchenId);
                kitchenPrint.setPrintId(kitchenPrintId);
                kitchenPrint.setDelFlag(Flag.OFF.getCode());
                kitchenPrint.setInsOperCd(userOperCd);
                kitchenPrint.setInsDateTime(nowDateTime);
                kitchenPrint.setUpdOperCd(userOperCd);
                kitchenPrint.setUpdDateTime(nowDateTime);
                kitchenPrint.setVersion(0);

                kitchenPrintRepository.save(kitchenPrint);
            }
        }
    }

    /**
     * キッチン情報削除.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void deleteKitchen(DeleteKitchenInputDto inputDto) {

        // キッチンリストを初期化する
        List<Integer> kitchenInfoList = new ArrayList<>();

        for (KitchenInfoDto kitchenInfo : inputDto.getKitchenList()) {
            kitchenInfoList.add(Integer.parseInt(kitchenInfo.getKitchenId()));
        }

        // 指定キッチンを削除する
        kitchenRepository.deleteByStoreIdAndKitChenId(inputDto.getStoreId(), kitchenInfoList);

        // 指定キッチンプリンタを削除する
        kitchenPrintRepository.deleteByStoreIdAndKitchenId(inputDto.getStoreId(), kitchenInfoList);
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
