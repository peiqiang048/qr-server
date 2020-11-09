package com.cnc.qr.core.acct.service;

import com.cnc.qr.core.acct.model.WeChatAliPayBackInputDto;
import com.cnc.qr.core.order.model.GetPayUrlInputDto;
import com.cnc.qr.core.order.model.GetPayUrlOutputDto;

/**
 * 商品情報取得サービス.
 */
public interface WeChatAliPayService {

    /**
     * 支払いデフォルトデータ設定.
     *
     * @param inputDto param
     */
    Integer insetPayResultData(GetPayUrlInputDto inputDto);

    /**
     * 支払いURL取得.
     *
     * @param inputDto 取得条件 　@return 支払いURL
     */
    GetPayUrlOutputDto getPayUrl(GetPayUrlInputDto inputDto);

    /**
     * DB支払い状態更新.
     *
     * @param inputDto param
     */
    void updatePayStatus(WeChatAliPayBackInputDto inputDto);

    /**
     * 繰り返し処理判断.
     *
     * @param inputDto param
     */
    Integer getCount(WeChatAliPayBackInputDto inputDto);

    /**
     * 支払い情報を作成する.
     *
     * @param inputDto param
     */
    void insertPayment(WeChatAliPayBackInputDto inputDto);
}
