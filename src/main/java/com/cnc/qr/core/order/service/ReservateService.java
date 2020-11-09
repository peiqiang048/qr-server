package com.cnc.qr.core.order.service;

import com.cnc.qr.core.order.model.CancelReservateInputDto;
import com.cnc.qr.core.order.model.FinishReservateInputDto;
import com.cnc.qr.core.order.model.GetAllTableListInputDto;
import com.cnc.qr.core.order.model.GetAllTableListOutputDto;
import com.cnc.qr.core.order.model.GetReservateInputDto;
import com.cnc.qr.core.order.model.GetReservateListInputDto;
import com.cnc.qr.core.order.model.GetReservateListOutputDto;
import com.cnc.qr.core.order.model.GetReservateOutputDto;
import com.cnc.qr.core.order.model.RegistReservateInputDto;
import org.springframework.data.domain.Pageable;

/**
 * 予約サービス.
 */
public interface ReservateService {

    /**
     * 予約一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 予約情報
     */
    GetReservateListOutputDto getReservateList(GetReservateListInputDto inputDto,
        Pageable pageable);

    /**
     * 取消.
     *
     * @param inputDto 予約データ
     */
    void cancelReservate(CancelReservateInputDto inputDto);

    /**
     * 来店.
     *
     * @param inputDto 予約データ
     */
    void finishReservate(FinishReservateInputDto inputDto);

    /**
     * 予約情報取得.
     *
     * @param inputDto 取得条件
     * @return 予約情報
     */
    GetReservateOutputDto getReservate(GetReservateInputDto inputDto);

    /**
     * 席一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 席一覧情報
     */
    GetAllTableListOutputDto getAllTableList(GetAllTableListInputDto inputDto);

    /**
     * 予約.
     *
     * @param inputDto 予約データ
     */
    void registReservate(RegistReservateInputDto inputDto);
}
