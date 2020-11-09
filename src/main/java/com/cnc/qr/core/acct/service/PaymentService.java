package com.cnc.qr.core.acct.service;

import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.core.acct.model.AgainRegistPaymentInputDto;
import com.cnc.qr.core.acct.model.DutchAccountPayLaterInputDto;
import com.cnc.qr.core.acct.model.DutchAccountPayLaterOutputDto;
import com.cnc.qr.core.acct.model.GetRefundsListInputDto;
import com.cnc.qr.core.acct.model.GetRefundsListOutputDto;
import com.cnc.qr.core.acct.model.GetSbPaymentInfoInputDto;
import com.cnc.qr.core.acct.model.GetSbPaymentInfoOutputDto;
import com.cnc.qr.core.acct.model.RefundsInputDto;
import com.cnc.qr.core.acct.model.RegistDutchAccountInputDto;
import com.cnc.qr.core.acct.model.RegistPaymentInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterInputDto;
import com.cnc.qr.core.acct.model.SbPayLaterOutputDto;
import com.cnc.qr.core.acct.model.SbPaymentCallBackInputDto;
import com.cnc.qr.core.acct.model.SbPaymentRefundsInputDto;
import com.cnc.qr.core.acct.model.TrioPayLaterInputDto;
import com.cnc.qr.core.acct.model.TrioPayLaterOutputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 会計処理サービス.
 */
public interface PaymentService {

    /**
     * 現金支払.
     *
     * @param inputDto 支払情報
     */
    String registPayment(RegistPaymentInputDto inputDto);

    /**
     * 再支払.
     *
     * @param inputDto 支払情報
     */
    OOrderSummary againRegistPayment(AgainRegistPaymentInputDto inputDto);

    /**
     * 返金一覧.
     *
     * @param inputDto パラメータ
     */
    Page<GetRefundsListOutputDto> getRefundsList(GetRefundsListInputDto inputDto,
        Pageable pageable);

    /**
     * 返金.
     *
     * @param inputDto 支払情報
     */
    void refunds(RefundsInputDto inputDto);

    /**
     * SBペイメント情报取得.
     *
     * @param inputDto 取得条件
     * @return ペイメント情报
     */
    GetSbPaymentInfoOutputDto getSbPaymentInfo(GetSbPaymentInfoInputDto inputDto);

    /**
     * 後払い.
     *
     * @param inputDto 支払情報
     */
    TrioPayLaterOutputDto payLater(TrioPayLaterInputDto inputDto);

    /**
     * SBペイメントコールバック.
     *
     * @param inputDto 支払データ
     */
    void sbPaymentCallBack(SbPaymentCallBackInputDto inputDto);


    /**
     * SBペイメントコールバック.
     *
     * @param inputDto 支払データ
     */
    void sbDeliveryPaymentCallBack(SbPaymentCallBackInputDto inputDto);

    /**
     * SBペイメント返金.
     *
     * @param inputDto 返金情報
     */
    void sbPaymentRefunds(SbPaymentRefundsInputDto inputDto);

    /**
     * SBペイメント後払.
     *
     * @param inputDto 支払情報
     * @return 支払結果
     */
    SbPayLaterOutputDto sbPayLater(SbPayLaterInputDto inputDto);

    /**
     * SBペイメント後払返金.
     *
     * @param inputDto 支払情報
     */
    void sbCpmRefunds(RefundsInputDto inputDto);

    /**
     * 返金（出前）.
     *
     * @param inputDto 支払情報
     */
    void refundsDelivery(RefundsInputDto inputDto);

    /**
     * SBペイメント返金（出前）.
     *
     * @param inputDto 返金情報
     */
    void sbPaymentRefundsDelivery(SbPaymentRefundsInputDto inputDto);

    /**
     * SBペイメント情报取得.
     *
     * @param inputDto 取得条件
     * @return ペイメント情报
     */
    GetSbPaymentInfoOutputDto getSbDvPaymentInfo(GetSbPaymentInfoInputDto inputDto);

    /**
     * 現金割勘.
     *
     * @param inputDto 支払情報
     */
    String registOrder(RegistDutchAccountInputDto inputDto);

    /**
     * 支払情報作成.
     *
     * @param inputDto 返金情報
     */
    void insertPayment(RegistDutchAccountInputDto inputDto, String orderSummaryId);

    /**
     * SBペイメント割勘後払.
     *
     * @param inputDto 支払情報
     * @return 支払結果
     */
    DutchAccountPayLaterOutputDto dutchAccountPayLater(DutchAccountPayLaterInputDto inputDto);

    /**
     * 出前注文状態変更.
     */
    void updateDeliveryStatus(String orderSummaryId, String storeId);
}
