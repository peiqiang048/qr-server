package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MPrint;
import com.cnc.qr.common.entity.RReceiptPrint;
import com.cnc.qr.common.repository.MPrintRepository;
import com.cnc.qr.common.repository.MReceiptRepository;
import com.cnc.qr.common.repository.RReceiptPrintRepository;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.GetReceiptInputDto;
import com.cnc.qr.core.pc.model.GetReceiptOutputDto;
import com.cnc.qr.core.pc.model.PrintListDto;
import com.cnc.qr.core.pc.model.ReceiptDto;
import com.cnc.qr.core.pc.model.ReceiptListInputDto;
import com.cnc.qr.core.pc.model.ReceiptListOutputDto;
import com.cnc.qr.core.pc.model.RegistReceiptInputDto;
import com.cnc.qr.core.pc.service.ReceiptService;
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
 * レシートサービス実装クラス.
 */
@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

    /**
     * レシートリポジトリ.
     */
    @Autowired
    private MReceiptRepository receiptRepository;

    /**
     * プリンタマスタリポジトリ.
     */
    @Autowired
    private MPrintRepository printRepository;

    /**
     * 伝票プリンタ関連テーブルリポジトリ.
     */
    @Autowired
    private RReceiptPrintRepository receiptPrintRepository;

    /**
     * レシートー覧情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public ReceiptListOutputDto getReceiptList(ReceiptListInputDto inputDto) {

        // レシート一覧情報取得
        List<Map<String, Object>> receiptInfoList = receiptRepository
            .findReceiptInfoList(inputDto.getStoreId());

        // レシート一覧情報を変換する
        List<ReceiptDto> receiptList = new ArrayList<>();
        receiptInfoList.forEach(stringObjectMap -> receiptList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), ReceiptDto.class)));
        // アウトプット
        ReceiptListOutputDto outputDto = new ReceiptListOutputDto();
        outputDto.setReceiptList(receiptList);
        return outputDto;
    }

    /**
     * レシート情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetReceiptOutputDto getReceipt(GetReceiptInputDto inputDto) {

        // レシート情報を取得する
        Map<String, Object> receiptInfo = receiptRepository
            .findReceiptInfo(inputDto.getStoreId(), Integer.valueOf(inputDto.getReceiptId()));

        // レシート情報を設定する
        GetReceiptOutputDto outputDto = JSONObject
            .parseObject(JSONObject.toJSONString(receiptInfo), GetReceiptOutputDto.class);

        // プリンタリスト情報を取得する
        List<MPrint> printList = printRepository
            .findByStoreIdAndDelFlag(inputDto.getStoreId(), Flag.OFF.getCode());

        // プリンタリスト情報を作成する
        List<PrintListDto> printInfoList = new ArrayList<>();

        printList.forEach(print -> {
            PrintListDto printInfoDto = new PrintListDto();
            printInfoDto.setPrintId(print.getPrintId().toString());
            printInfoDto.setPrintName(print.getPrintName());
            printInfoList.add(printInfoDto);
        });

        // プリンタリスト情報を設定する
        outputDto.setPrintList(printInfoList);

        return outputDto;
    }

    /**
     * レシート保存.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void saveReceipt(RegistReceiptInputDto inputDto) {

        // ユーザID取得
        String userOperCd = getUserOperCd();

        // システム日付
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        if (!Objects.isNull(inputDto.getReceiptId()) && !Objects.isNull(inputDto.getPrintId())) {
            // 伝票プリンタ関連テーブル取得
            List<RReceiptPrint> receiptPrintList = receiptPrintRepository
                .findByStoreIdAndReceiptIdAndDelFlag(inputDto.getStoreId(),
                    Integer.valueOf(inputDto.getReceiptId()), Flag.OFF.getCode());
            // 件数＞0の場合
            if (receiptPrintList.size() > 0) {
                receiptPrintRepository.updateReceiptPrint(inputDto.getStoreId(),
                    Integer.valueOf(inputDto.getReceiptId()),
                    Integer.valueOf(inputDto.getPrintId()), userOperCd, nowDateTime);
            } else {
                RReceiptPrint receiptPrintDto = new RReceiptPrint();
                receiptPrintDto.setStoreId(inputDto.getStoreId());
                receiptPrintDto.setReceiptId(Integer.valueOf(inputDto.getReceiptId()));
                receiptPrintDto.setPrintId(Integer.valueOf(inputDto.getPrintId()));
                receiptPrintDto.setDelFlag(Flag.OFF.getCode());
                receiptPrintDto.setInsOperCd(userOperCd);
                receiptPrintDto.setInsDateTime(nowDateTime);
                receiptPrintDto.setUpdOperCd(userOperCd);
                receiptPrintDto.setUpdDateTime(nowDateTime);
                receiptPrintDto.setVersion(0);
                receiptPrintRepository.save(receiptPrintDto);
            }
        }
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
