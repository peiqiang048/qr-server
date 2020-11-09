package com.cnc.qr.core.pc.service;

import com.cnc.qr.core.pc.model.ChangeStoreInputDto;
import com.cnc.qr.core.pc.model.GetHomePageInfoInputDto;
import com.cnc.qr.core.pc.model.GetHomePageInfoOutputDto;
import com.cnc.qr.core.pc.model.GetStoreInfoInputDto;
import com.cnc.qr.core.pc.model.GetStoreInfoOutputDto;
import com.cnc.qr.core.pc.model.GetStoreInputDto;
import com.cnc.qr.core.pc.model.GetStoreListInputDto;
import com.cnc.qr.core.pc.model.GetStoreListOutputDto;
import com.cnc.qr.core.pc.model.GetStoreOutputDto;
import com.cnc.qr.core.pc.model.UploadFileInputDto;
import com.cnc.qr.core.pc.model.UploadFileOutputDto;

/**
 * 店舗サービス.
 */
public interface StoreService {

    /**
     * 店舗一覧情報取得.
     *
     * @param inputDto 取得条件
     */
    GetStoreListOutputDto getStoreList(GetStoreListInputDto inputDto);

    /**
     * 店舗情報取得.
     *
     * @param inputDto 取得条件
     */
    GetStoreOutputDto getStore(GetStoreInputDto inputDto);

    /**
     * 店舗情報編集.
     *
     * @param inputDto 取得条件
     */
    void changeStore(ChangeStoreInputDto inputDto);

    /**
     * ホームページ情報取得.
     *
     * @param inputDto 取得条件
     * @return ホームページ情報
     */
    GetHomePageInfoOutputDto getHomePageInfo(GetHomePageInfoInputDto inputDto);

    /**
     * ファイルアップロード.
     *
     * @param inputDto 取得条件
     * @return アップロード結果
     */
    UploadFileOutputDto uploadFile(UploadFileInputDto inputDto);

    /**
     * 店舗情報取得.
     *
     * @param inputDto 取得条件
     * @return 店舗情報
     */
    GetStoreInfoOutputDto getStoreInfo(GetStoreInfoInputDto inputDto);
}
