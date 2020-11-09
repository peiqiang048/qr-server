package com.cnc.qr.core.pc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.ResultCode;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.entity.MPrint;
import com.cnc.qr.common.entity.RKitchenPrint;
import com.cnc.qr.common.entity.RReceiptPrint;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.repository.MCodeRepository;
import com.cnc.qr.common.repository.MPrintRepository;
import com.cnc.qr.common.repository.RKitchenPrintRepository;
import com.cnc.qr.common.repository.RReceiptPrintRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.pc.model.GetPrintInputDto;
import com.cnc.qr.core.pc.model.GetPrintOutputDto;
import com.cnc.qr.core.pc.model.PrintBrandDto;
import com.cnc.qr.core.pc.model.PrintConnectionMethodDto;
import com.cnc.qr.core.pc.model.PrintDelInputDto;
import com.cnc.qr.core.pc.model.PrintDto;
import com.cnc.qr.core.pc.model.PrintInputDto;
import com.cnc.qr.core.pc.model.PrintListInputDto;
import com.cnc.qr.core.pc.model.PrintListOutputDto;
import com.cnc.qr.core.pc.model.PrintSizeDto;
import com.cnc.qr.core.pc.model.RegistPrintInputDto;
import com.cnc.qr.core.pc.service.PrintService;
import com.cnc.qr.security.until.SecurityUtils;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * プリンターサービス実装クラス.
 */
@Service
@Transactional
public class PrintServiceImpl implements PrintService {

    /**
     * プリンターリポジトリ.
     */
    @Autowired
    private MPrintRepository printRepository;

    /**
     * コードマスタリポジトリ.
     */
    @Autowired
    private MCodeRepository codeRepository;

    /**
     * 伝票プリンタ関連テーブルリポジトリ.
     */
    @Autowired
    private RReceiptPrintRepository receiptPrintRepository;

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
     * メッセージ.
     */
    @Autowired
    MessageSource messageSource;

    /**
     * プリンター覧情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public PrintListOutputDto getPrintList(PrintListInputDto inputDto) {

        // プリンター一覧情報取得
        List<Map<String, Object>> printInfoList = printRepository
            .findPrintInfoList(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PRINT_BRAND,
                CommonConstants.CODE_GROUP_PRINTER_CONNECTION_METHOD,
                CommonConstants.CODE_GROUP_PRINT_SIZE);

        // プリンター一覧情報を変換する
        List<PrintDto> printList = new ArrayList<>();
        printInfoList.forEach(stringObjectMap -> printList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), PrintDto.class)));
        for (int i = 0; i < printList.size(); i++) {
            printList.get(i).setNum(i + 1);
        }

        // アウトプット
        PrintListOutputDto outputDto = new PrintListOutputDto();
        outputDto.setPrintList(printList);
        return outputDto;
    }

    /**
     * プリンター情報取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetPrintOutputDto getPrint(GetPrintInputDto inputDto) {
        GetPrintOutputDto outputDto = new GetPrintOutputDto();
        if (StringUtils.isNotEmpty(inputDto.getPrintId())) {
            // プリンター情報を取得する
            Map<String, Object> printInfo = printRepository
                .findPrintInfo(inputDto.getStoreId(), Integer.valueOf(inputDto.getPrintId()));

            // プリンター情報を設定する
            outputDto = JSONObject
                .parseObject(JSONObject.toJSONString(printInfo), GetPrintOutputDto.class);
        }

        // プリンターブランド情報を取得する
        List<Map<String, Object>> printBrandList = codeRepository
            .findBrandInfo(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PRINT_BRAND);

        // プリンターブランド情報を変換する
        List<PrintBrandDto> printBrandDtoList = new ArrayList<>();
        printBrandList.forEach(stringObjectMap -> printBrandDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), PrintBrandDto.class)));

        // プリンター接続方法情報を取得する
        List<Map<String, Object>> printConnectionMethodList = codeRepository
            .findConnectionMethodInfo(inputDto.getStoreId(),
                CommonConstants.CODE_GROUP_PRINTER_CONNECTION_METHOD);

        // プリンター接続方法情報を変換する
        List<PrintConnectionMethodDto> printConnectionMethodDtoList = new ArrayList<>();
        printConnectionMethodList.forEach(stringObjectMap -> printConnectionMethodDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap),
                    PrintConnectionMethodDto.class)));

        // プリンターチケット幅情報を取得する
        List<Map<String, Object>> printSizeList = codeRepository
            .findPrintSizeInfo(inputDto.getStoreId(), CommonConstants.CODE_GROUP_PRINT_SIZE);

        // プリンターチケット幅情報を変換する
        List<PrintSizeDto> printSizeDtoList = new ArrayList<>();
        printSizeList.forEach(stringObjectMap -> printSizeDtoList.add(
            JSONObject
                .parseObject(JSONObject.toJSONString(stringObjectMap), PrintSizeDto.class)));

        // プリンターブランド情報を設定する
        outputDto.setBrandList(printBrandDtoList);
        // プリンター接続方法情報を設定する
        outputDto.setConnectionMethodList(printConnectionMethodDtoList);
        // プリンターチケット幅情報を設定する
        outputDto.setPrintSizeList(printSizeDtoList);

        return outputDto;
    }

    /**
     * キッチン保存.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void savePrint(RegistPrintInputDto inputDto) {

        // ユーザID取得
        String userOperCd = getUserOperCd();

        // システム日付
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();
        if (Objects.isNull(inputDto.getPrintId())) {

            GetSeqNoInputDto seqNoInputDto = new GetSeqNoInputDto();
            seqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            seqNoInputDto.setTableName("m_print"); // テーブル名
            seqNoInputDto.setItem("print_id"); // 項目
            seqNoInputDto.setOperCd(userOperCd); // 登録更新者
            GetSeqNoOutputDto seqNo = itemInfoSharedService.getSeqNo(seqNoInputDto);
            // 新規
            MPrint print = new MPrint();
            // 店舗ID
            print.setStoreId(inputDto.getStoreId());
            // プリンターID
            print.setPrintId(seqNo.getSeqNo());
            // プリンター名
            print.setPrintName(inputDto.getPrintName());
            // プリンタIP
            print.setPrintIp(inputDto.getPrintIp());
            // ブルートゥース名
            print.setBluetoothName(inputDto.getBlueToothName());
            // プリンタブランドCD
            print.setBrandCode(inputDto.getBrandCode());
            // 型番
            print.setPrintModel(inputDto.getPrintModel());
            // 接続方式CD
            print.setConnectionMethodCode(inputDto.getConnectionMethodCode());
            // 伝票幅CD
            print.setPrintSize(inputDto.getPrintSizeCode());
            //削除フラグ
            print.setDelFlag(Flag.OFF.getCode());
            print.setInsOperCd(userOperCd);
            print.setInsDateTime(nowDateTime);
            print.setUpdOperCd(userOperCd);
            print.setUpdDateTime(nowDateTime);
            print.setVersion(0);
            printRepository.save(print);
        } else {
            //変更
            //プリンター情報保存
            printRepository
                .printUpdate(inputDto.getPrintName(), inputDto.getBrandCode(),
                    inputDto.getConnectionMethodCode(),
                    inputDto.getPrintIp(), inputDto.getBlueToothName(), inputDto.getPrintModel(),
                    inputDto.getPrintSizeCode(),
                    userOperCd, nowDateTime, inputDto.getStoreId(),
                    Integer.valueOf(inputDto.getPrintId()));
        }
    }

    /**
     * プリンター削除.
     *
     * @param inputDto 取得条件
     */
    @Override
    public void delPrint(PrintDelInputDto inputDto) {

        // プリンターID取得
        List<Integer> printIdList = new ArrayList<>();
        inputDto.getPrintList().forEach(printDto -> {
            printIdList.add(printDto.getPrintId());
        });

        // 登録更新者
        String userOperCd = getUserOperCd();
        ZonedDateTime nowDateTime = DateUtil.getNowDateTime();

        // 伝票プリンタ関連情報取得
        List<RReceiptPrint> receiptPrintList = receiptPrintRepository
            .findByStoreIdAndPrintIdInAndDelFlag(
                inputDto.getStoreId(), printIdList, Flag.OFF.getCode());

        // 伝票プリンタ関連検索結果0件以外の場合
        if (receiptPrintList.size() > 0) {
            List<Integer> printIdTempList = new ArrayList<>();
            receiptPrintList.forEach(receiptPrint -> {
                printIdTempList.add(receiptPrint.getPrintId());
            });
            receiptPrintRepository.delFlagByprintIdAndReceiptId(
                inputDto.getStoreId(), printIdTempList);
        }

        // キチンプリンタ関連情報取得
        List<RKitchenPrint> kitchenPrintList = kitchenPrintRepository
            .findByStoreIdAndPrintIdInAndDelFlag(
                inputDto.getStoreId(), printIdList, Flag.OFF.getCode());

        // キチンプリンタ関連検索結果0件以外の場合
        if (kitchenPrintList.size() > 0) {
            List<Integer> printIdTempList = new ArrayList<>();
            kitchenPrintList.forEach(receiptPrint -> {
                printIdTempList.add(receiptPrint.getPrintId());
            });
            kitchenPrintRepository.delFlagByprintIdAndKitchenId(
                inputDto.getStoreId(), printIdTempList);
        }

        // プリンター削除
        printRepository.updateDelFlagByprintId(inputDto.getStoreId(), printIdList, userOperCd,
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
