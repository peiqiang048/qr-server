package com.cnc.qr.common.shared.service;

import com.cnc.qr.common.entity.MLicense;
import com.cnc.qr.common.shared.model.GetItemOptionInfoInputDto;
import com.cnc.qr.common.shared.model.GetItemOptionInfoOutputDto;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.model.GetTaxValueInputDto;
import com.cnc.qr.common.shared.model.GetTaxValueOutputDto;
import com.cnc.qr.common.shared.model.StoreOpenColseTimeDto;
import com.cnc.qr.common.shared.model.TaxAmountInputDto;
import com.cnc.qr.common.shared.model.UploadFileDto;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品情報共有サービス.
 */
public interface ItemInfoSharedService {

    /**
     * 税情報取得.
     *
     * @param inputDto 検索条件
     * @return 税情報
     */
    GetTaxValueOutputDto getTaxValue(GetTaxValueInputDto inputDto);

    /**
     * 商品オプション類型情報取得.
     *
     * @param inputDto 検索条件
     * @return 商品オプション類型情報
     */
    GetItemOptionInfoOutputDto getItemOptionInfo(GetItemOptionInfoInputDto inputDto);

    /**
     * シーケンスNo取得.
     *
     * @param inputDto 検索条件
     * @return シーケンスNo
     */
    GetSeqNoOutputDto getSeqNo(GetSeqNoInputDto inputDto);

    /**
     * 受付ID取得.
     *
     * @param storeId     店舗ID
     * @param nowDateTime 受付時間
     * @return 受付ID
     */
    String getReceivablesId(String storeId, ZonedDateTime nowDateTime);

    /**
     * ファイルアップロード.
     *
     * @param storeId 店舗ID
     * @param useType 利用区分
     * @param file    ファイル
     * @return ファイルパス
     */
    UploadFileDto uploadFiles(String storeId, String useType, MultipartFile file);

    /**
     * 税額登録.
     *
     * @param inputDto 税額情報
     */
    void registTaxAmount(TaxAmountInputDto inputDto);


    /**
     * 税額取得.
     *
     * @param inputDto 税額情報
     */
    Integer getTaxAmountCount(TaxAmountInputDto inputDto);

    /**
     * 店舗営業時間取得.
     *
     * @param storeId 店舗ID
     */
    StoreOpenColseTimeDto getStoreOpenColseTime(String storeId);

    /**
     * ライセンス許可取得.
     *
     * @param licenseList 全部のライセンスリスト
     */
    String getLicenseCode(List<MLicense> licenseList, String licenseType);
}
