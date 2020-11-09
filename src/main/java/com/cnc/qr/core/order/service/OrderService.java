package com.cnc.qr.core.order.service;

import com.cnc.qr.common.entity.OOrder;
import com.cnc.qr.common.entity.OOrderSummary;
import com.cnc.qr.core.acct.model.WeChatAliPayBackInputDto;
import com.cnc.qr.core.order.model.AmountSoldInputDto;
import com.cnc.qr.core.order.model.AmountSoldOutputDto;
import com.cnc.qr.core.order.model.AssignationTableInputDto;
import com.cnc.qr.core.order.model.AssignationTableOutputDto;
import com.cnc.qr.core.order.model.ChangeCustomerCountInputDto;
import com.cnc.qr.core.order.model.CourseBuffetConfirmInputDto;
import com.cnc.qr.core.order.model.CourseBuffetConfirmOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetDetailInputDto;
import com.cnc.qr.core.order.model.CourseBuffetDetailOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetListInputDto;
import com.cnc.qr.core.order.model.CourseBuffetListOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetOrderOutputDto;
import com.cnc.qr.core.order.model.CourseBuffetRegistInputDto;
import com.cnc.qr.core.order.model.DeleteItemInputDto;
import com.cnc.qr.core.order.model.DeliveryOrderInputDto;
import com.cnc.qr.core.order.model.DeliveryOrderOutputDto;
import com.cnc.qr.core.order.model.DiscardOrderInputDto;
import com.cnc.qr.core.order.model.EmptyItemInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderDetailInfoInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderDetailInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderEditInfoInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderEditInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderListInputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderListOutputDto;
import com.cnc.qr.core.order.model.GetDeliveryOrderOutputDto;
import com.cnc.qr.core.order.model.GetItemDetailInputDto;
import com.cnc.qr.core.order.model.GetItemDetailOutputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInfoInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInfoOutputDto;
import com.cnc.qr.core.order.model.GetOrderDetailInputDto;
import com.cnc.qr.core.order.model.GetOrderDetailOutputDto;
import com.cnc.qr.core.order.model.GetOrderFlagInputDto;
import com.cnc.qr.core.order.model.GetOrderFlagOutputDto;
import com.cnc.qr.core.order.model.GetOrderHistoryListInputDto;
import com.cnc.qr.core.order.model.GetOrderHistoryListOutputDto;
import com.cnc.qr.core.order.model.GetOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetOrderListInputDto;
import com.cnc.qr.core.order.model.GetOrderListOutputDto;
import com.cnc.qr.core.order.model.GetReturnReasonInputDto;
import com.cnc.qr.core.order.model.GetReturnReasonOutputDto;
import com.cnc.qr.core.order.model.GetSeatReleaseListInputDto;
import com.cnc.qr.core.order.model.GetSeatReleaseListOutputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListInputDto;
import com.cnc.qr.core.order.model.GetSelectedAreaListOutputDto;
import com.cnc.qr.core.order.model.GetShareOrderInfoInputDto;
import com.cnc.qr.core.order.model.GetShareOrderInfoOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderItemListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderItemListOutputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListInputDto;
import com.cnc.qr.core.order.model.GetUnCfmOrderListOutputDto;
import com.cnc.qr.core.order.model.InitOrderInputDto;
import com.cnc.qr.core.order.model.InitOrderOutputDto;
import com.cnc.qr.core.order.model.OrderInputDto;
import com.cnc.qr.core.order.model.OrderOutputDto;
import com.cnc.qr.core.order.model.OrderSummaryAndOrderIdDto;
import com.cnc.qr.core.order.model.OrderTransferInputDto;
import com.cnc.qr.core.order.model.OrderTransferOutputDto;
import com.cnc.qr.core.order.model.QrCodeIssueInputDto;
import com.cnc.qr.core.order.model.QrCodeIssueOutputDto;
import com.cnc.qr.core.order.model.ReceptionDisposalInputDto;
import com.cnc.qr.core.order.model.ReceptionInitInputDto;
import com.cnc.qr.core.order.model.ReceptionInitOutputDto;
import com.cnc.qr.core.order.model.RegistReturnInputDto;
import com.cnc.qr.core.order.model.RegistReturnOutputDto;
import com.cnc.qr.core.order.model.SeatReleaseInputDto;
import com.cnc.qr.core.order.model.SetTableInputDto;
import com.cnc.qr.core.order.model.SureOrderItemInputDto;
import com.cnc.qr.core.order.model.UpdateDeliveryOrderInputDto;
import java.math.BigDecimal;
import org.springframework.data.domain.Pageable;

/**
 * 注文確認サービス.
 */
public interface OrderService {

    /**
     * 注文確定.
     *
     * @param inputDto 画面項目
     */
    OrderOutputDto getOrder(OrderInputDto inputDto);

    /**
     * 注文情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文情報
     */
    GetOrderInfoOutputDto getOrderInfo(GetOrderInfoInputDto inputDto);

    /**
     * 共有注文情報取得.
     *
     * @param inputDto 取得条件
     * @return 共有注文情報
     */
    GetShareOrderInfoOutputDto getShareOrderInfo(GetShareOrderInfoInputDto inputDto);

    /**
     * 注文状態を変更する.
     *
     * @param inputDto param
     */
    Integer changeStatus(WeChatAliPayBackInputDto inputDto);

    /**
     * 注文情報を回復する.
     *
     * @param inputDto param
     */
    void revertOrder(WeChatAliPayBackInputDto inputDto);

    /**
     * 注文商品情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文商品情報
     */
    GetItemDetailOutputDto getOrderItemDetail(GetItemDetailInputDto inputDto);

    /**
     * 注文状態確認.
     *
     * @param inputDto 注文確認データ
     */
    OOrderSummary sureOrderItem(SureOrderItemInputDto inputDto);

    /**
     * 注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 注文商品情報
     */
    GetOrderListOutputDto getOrderList(GetOrderListInputDto inputDto, Pageable pageable);

    /**
     * 返品原因情報取得.
     *
     * @param inputDto 取得条件
     * @return 返品原因情報
     */
    GetReturnReasonOutputDto getReturnReasonList(GetReturnReasonInputDto inputDto);

    /**
     * 人数変更.
     *
     * @param inputDto 人数変更データ
     */
    void changeCustomerCount(ChangeCustomerCountInputDto inputDto);

    /**
     * 返品登録.
     *
     * @param inputDto 返品登録データ
     */
    RegistReturnOutputDto registReturn(RegistReturnInputDto inputDto);


    /**
     * 注文商品リスト取得.
     *
     * @param inputDto 取得条件
     * @return 注文商品リスト
     */
    GetOrderItemListOutputDto getOrderItemList(GetOrderItemListInputDto inputDto);


    /**
     * 会計PAD　注文.
     *
     * @param inputDto 注文データ
     */
    OrderOutputDto registOrder(OrderInputDto inputDto);

    /**
     * 会計PAD　配席.
     *
     * @param inputDto 注文データ
     */
    void setTable(SetTableInputDto inputDto);

    /**
     * 会計PAD　配席印刷（受付IDにより注文サマリIDと注文ID）.
     *
     * @param inputDto 注文データ
     */
    OrderSummaryAndOrderIdDto getOrderSummaryAndOrderId(SetTableInputDto inputDto);

    /**
     * 注文サマリを取得する.
     */
    OOrderSummary getOrderSummary(GetOrderItemListInputDto inputDto);

    /**
     * 外税金額を取得する.
     */
    BigDecimal getForeignTax(String storeId, String orderSummaryId);

    /**
     * QR発行情報.
     *
     * @param inputDto 取得条件
     * @return QR発行情報.
     */
    QrCodeIssueOutputDto qrCodeIssue(QrCodeIssueInputDto inputDto);

    /**
     * 受付番号廃棄.
     *
     * @param inputDto 取得条件
     */
    void receptionDisposal(ReceptionDisposalInputDto inputDto);

    /**
     * 受付番号選択可能のテーブル.
     *
     * @param inputDto 取得条件
     * @return 受付番号選択可能のテーブル.
     */
    ReceptionInitOutputDto getReceptionInit(ReceptionInitInputDto inputDto);

    /**
     * 配せき一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 配せき一覧情報取得.
     */
    AssignationTableOutputDto assignationTable(AssignationTableInputDto inputDto);

    /**
     * 注文詳細情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文詳細情報取得.
     */
    GetOrderDetailOutputDto getOrderDetail(GetOrderDetailInputDto inputDto);

    /**
     * 注文確認未済商品クリア.
     *
     * @param inputDto 取得条件
     */
    void emptyItem(EmptyItemInputDto inputDto);

    /**
     * 注文確認未済商品削除.
     *
     * @param inputDto 取得条件
     */
    void deleteItem(DeleteItemInputDto inputDto);

    /**
     * 受付情報設定.
     *
     * @param inputDto 取得条件
     */
    InitOrderOutputDto initOrder(InitOrderInputDto inputDto);

    /**
     * 注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文一覧情報
     */
    GetUnCfmOrderListOutputDto getUnCfmOrderList(GetUnCfmOrderListInputDto inputDto);

    /**
     * 注文未確認商品一覧情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文未確認商品一覧情報
     */
    GetUnCfmOrderItemListOutputDto getUnCfmOrderItemList(GetUnCfmOrderItemListInputDto inputDto);

    /**
     * 注文履歴情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文履歴情報
     */
    GetOrderHistoryListOutputDto getOrderHistoryList(GetOrderHistoryListInputDto inputDto);

    /**
     * 注文詳細情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文詳細情報取得.
     */
    GetOrderDetailInfoOutputDto getOrderDetailInfo(GetOrderDetailInfoInputDto inputDto);

    /**
     * 売上一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 注文商品情報
     */
    AmountSoldOutputDto amountSold(AmountSoldInputDto inputDto, Pageable pageable);

    /**
     * 席解除一覧取得.
     *
     * @param inputDto 取得条件
     * @return 席解除一覧情報
     */
    GetSeatReleaseListOutputDto getSeatReleaseList(GetSeatReleaseListInputDto inputDto);

    /**
     * 席解除.
     *
     * @param inputDto 席解除データ
     */
    void seatRelease(SeatReleaseInputDto inputDto);

    /**
     * 注文フラグ取得.
     *
     * @param inputDto 検証データ
     */
    GetOrderFlagOutputDto getOrderFlag(GetOrderFlagInputDto inputDto);

    /**
     * 注文廃棄.
     *
     * @param inputDto 検証データ
     */
    void discardOrder(DiscardOrderInputDto inputDto);

    /**
     * 遷移先判断.
     *
     * @param inputDto 検証データ
     */
    OrderTransferOutputDto orderTransfer(OrderTransferInputDto inputDto);

    /**
     * コース＆放题詳細.
     *
     * @param inputDto 検証データ
     */
    CourseBuffetDetailOutputDto courseBuffetDetail(CourseBuffetDetailInputDto inputDto);

    /**
     * コース＆放题.
     *
     * @param inputDto コース＆放题
     */
    CourseBuffetListOutputDto getCourseBuffetList(CourseBuffetListInputDto inputDto);

    /**
     * コース＆放题注文確認取得.
     *
     * @param inputDto コース＆放题
     */
    CourseBuffetConfirmOutputDto courseBuffetConfirm(CourseBuffetConfirmInputDto inputDto);

    /**
     * コース＆放题注文確定.
     *
     * @param inputDto コース＆放题
     */
    CourseBuffetOrderOutputDto courseBuffetRegist(CourseBuffetRegistInputDto inputDto);

    /**
     * 出前注文一覧情報取得.
     *
     * @param inputDto 取得条件
     * @param pageable ページ情報
     * @return 出前注文情報
     */
    GetDeliveryOrderListOutputDto getDeliveryOrderList(GetDeliveryOrderListInputDto inputDto,
        Pageable pageable);

    /**
     * 出前注文詳細情報取得.
     *
     * @param inputDto 取得条件
     * @return 出前注文詳細情報取得.
     */
    GetDeliveryOrderDetailInfoOutputDto getDeliveryOrderDetailInfo(
        GetDeliveryOrderDetailInfoInputDto inputDto);

    /**
     * 出前注文編集情報取得.
     *
     * @param inputDto 取得条件
     * @return 出前注文編集情報取得.
     */
    GetDeliveryOrderEditInfoOutputDto getDeliveryOrderEditInfo(
        GetDeliveryOrderEditInfoInputDto inputDto);

    /**
     * エリア情報取得.
     *
     * @param inputDto 取得索条件
     * @return エリア情報
     */
    GetSelectedAreaListOutputDto getSelectedAreaList(GetSelectedAreaListInputDto inputDto);

    /**
     * 出前注文編集.
     *
     * @param inputDto 出前注文データ
     */
    void updateDeliveryOrder(UpdateDeliveryOrderInputDto inputDto);

    /**
     * 注文状態を変更する.
     *
     * @param inputDto param
     */
    void changeDeliveryStatus(WeChatAliPayBackInputDto inputDto);

    /**
     * 注文情報を回復する.
     *
     * @param inputDto param
     */
    void revertDeliveryOrder(WeChatAliPayBackInputDto inputDto);


    /**
     * 注文確定.
     *
     * @param inputDto 画面項目
     */
    DeliveryOrderOutputDto registDeliveryOrder(DeliveryOrderInputDto inputDto);

    /**
     * 注文情報取得.
     *
     * @param inputDto 画面項目
     */
    GetDeliveryOrderInfoOutputDto getDeliveryOrderInfo(GetOrderInfoInputDto inputDto);

    /**
     * 注文新規編集情報取得.
     *
     * @param inputDto 取得条件
     * @return 注文新規編集取得
     */
    GetDeliveryOrderOutputDto getDeliveryOrderNewEditInfo(GetDeliveryOrderInputDto inputDto);
}
