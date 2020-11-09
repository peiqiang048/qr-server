package com.cnc.qr.core.acct.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cnc.qr.common.constants.CodeConstants;
import com.cnc.qr.common.constants.CodeConstants.Company;
import com.cnc.qr.common.constants.CodeConstants.Flag;
import com.cnc.qr.common.constants.CodeConstants.PayResult;
import com.cnc.qr.common.constants.CommonConstants;
import com.cnc.qr.common.constants.UrlConstants;
import com.cnc.qr.common.entity.PPayment;
import com.cnc.qr.common.entity.PPaymentCompany;
import com.cnc.qr.common.entity.PPaymentDetail;
import com.cnc.qr.common.entity.PTrioResultDetail;
import com.cnc.qr.common.exception.BusinessException;
import com.cnc.qr.common.exception.ResultMessages;
import com.cnc.qr.common.repository.PPaymentCompanyRepository;
import com.cnc.qr.common.repository.PPaymentDetailRepository;
import com.cnc.qr.common.repository.PPaymentRepository;
import com.cnc.qr.common.repository.PTrioResultDetailRepository;
import com.cnc.qr.common.shared.model.GetSeqNoInputDto;
import com.cnc.qr.common.shared.model.GetSeqNoOutputDto;
import com.cnc.qr.common.shared.service.ItemInfoSharedService;
import com.cnc.qr.common.util.DateUtil;
import com.cnc.qr.core.acct.model.WeChatAliPayBackInputDto;
import com.cnc.qr.core.acct.service.WeChatAliPayService;
import com.cnc.qr.core.order.model.GetPayUrlInputDto;
import com.cnc.qr.core.order.model.GetPayUrlOutputDto;
import com.cnc.qr.core.order.service.OrderService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店員呼出サービス実装クラス.
 */
@Service
public class WeChatAliPayServiceImpl implements WeChatAliPayService {

    /**
     * 支払テーブル.
     */
    @Autowired
    private PPaymentRepository paymentRepository;

    /**
     * 支払明細テーブル.
     */
    @Autowired
    private PPaymentDetailRepository paymentDetailRepository;

    /**
     * トリオ支払結果明細テーブル.
     */
    @Autowired
    private PTrioResultDetailRepository trioResultDetailRepository;

    /**
     * トリオ支払テーブル.
     */
    @Autowired
    private PPaymentCompanyRepository trioCompanyRepository;

    /**
     * 注文サービス.
     */
    @Autowired
    private OrderService orderService;

    /**
     * 商品情報共有サービス.
     */
    @Autowired
    private ItemInfoSharedService itemInfoSharedService;

    /**
     * 環境変数.
     */
    @Autowired
    private Environment env;

    /**
     * ログ出力.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 支払いデータ設定.
     *
     * @param inputDto 検索条件
     */
    @Override
    @Transactional
    public Integer insetPayResultData(GetPayUrlInputDto inputDto) {

        try {

            // トリオ支払結果明細IDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(inputDto.getStoreId()); // 店舗ID
            getSeqNoInputDto.setTableName("p_trio_result_detail"); // テーブル名
            getSeqNoInputDto.setItem("trio_result_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PTrioResultDetail resultInputDao = new PTrioResultDetail();
            // 店舗ID
            resultInputDao.setStoreId(inputDto.getStoreId());
            // トリオ支払結果明細ID
            resultInputDao.setTrioResultDetailId(getSeqNo.getSeqNo());
            // 注文サマリID
            resultInputDao.setOrderSummaryId(inputDto.getOrderSummaryId());
            // 注文ID
            resultInputDao.setOrderId(inputDto.getOrderId());
            // 注文番号
            if (inputDto.getOrderId() == 0) {

                resultInputDao.setOrderNo(inputDto.getOrderSummaryId() + inputDto.getMaxId());
            } else {

                resultInputDao.setOrderNo(inputDto.getOrderSummaryId()
                    + inputDto.getOrderId().toString() + inputDto.getMaxId());
            }
            // 支払い方式
            resultInputDao.setPaymentMethodCode(inputDto.getPaymentMethodCode());
            // 取消请求UUID
            resultInputDao.setCancelOrderId(null);
            // 退款请求UUID
            resultInputDao.setReturnOrderId(null);
            // 支付金额
            resultInputDao.setPayPrice(new BigDecimal(inputDto.getPayAmount()));
            // 支付结果code
            resultInputDao.setRespCode(PayResult.DEFAULT.getCode());
            // 取消结果code
            resultInputDao.setCancelRespCode(null);
            // 退款结果code
            resultInputDao.setReturnRespCode(null);
            // 削除フラグ
            resultInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            resultInputDao.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            resultInputDao.setInsDateTime(dateTime);
            // 更新者
            resultInputDao.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            resultInputDao.setUpdDateTime(dateTime);
            // バージョン
            resultInputDao.setVersion(0);

            // トリオ支払結果明細テーブル
            trioResultDetailRepository.save(resultInputDao);

            return getSeqNo.getSeqNo();
        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.036", (Object) null), ex);
        }
    }

    /**
     * 支払いURL取得.
     *
     * @param inputDto 取得条件
     */
    @Override
    public GetPayUrlOutputDto getPayUrl(GetPayUrlInputDto inputDto) {

        GetPayUrlOutputDto outPutDto = new GetPayUrlOutputDto();

        try {
            // 支付方式
            String paymentBrand = "";

            if (CodeConstants.AccountsType.WECHATPAY.getCode()
                .equals(inputDto.getPaymentMethodCode())) {

                paymentBrand = "WXP";
            } else {

                paymentBrand = "ALP";
            }

            JSONObject remarks = new JSONObject();
            remarks.put("maxDetailId", inputDto.getMaxDetailId().toString());
            remarks.put("storeId", inputDto.getStoreId());
            remarks.put("token", "37f347d87a3fd402a46b34d07cebbfd7");

            // 接口参数做成
            SortedMap<Object, Object> packageParams = new TreeMap<>();
            // メッセージID
            String msgId = UUID.randomUUID().toString().replaceAll("-", "");
            packageParams.put("msgId", msgId);
            packageParams
                .put("orderNum",
                    inputDto.getOrderSummaryId() + inputDto.getOrderId().toString() + "01");
            // トリオ支払テーブル情報取得
            PPaymentCompany trioCompany = trioCompanyRepository.findByStoreIdAndDelFlagAndCompanyId(
                inputDto.getStoreId(), Flag.OFF.getCode(), Company.TRIO.getCode());
            // 迅联分配或登记的门店号
            String storeId = trioCompany.getStoreCode();
            packageParams.put("storeId", storeId);
            // 后台回调方法URL
            String backUrl = env.getProperty("qr.env.nginx.service.url")
                + UrlConstants.CSMB_WECHATALI_PAY_BACKURL;
            packageParams.put("backUrl", backUrl);
            packageParams.put("paymentBrand", paymentBrand);
            // 日付時刻フォーマット
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_DATE_TIMES);
            // 現在時刻文字列取得
            String merTransTime = sdf.format(new Date()) + DateFormatUtils.format(new Date(), "Z");
            packageParams.put("merTransTime", merTransTime);
            // 前台回调方法URL
            String frontUrl =
                env.getProperty("qr.env.nginx.service.url") + "/#/mobile-placeOrder/payBack?"
                    + "storeId=" + inputDto.getStoreId() + "&receivablesId=" + inputDto
                    .getReceivablesId() + "&token=" + inputDto.getToken() + "&flgLanguage="
                    + inputDto.getLanguages() + "&no=" + inputDto.getNo();
            packageParams.put("frontUrl", frontUrl);
            // 支付金额
            String transAmt = String.format("%012d", 1);
            packageParams.put("transAmt", transAmt);
            // 货币单位
            String transCurrency = "JPY";
            packageParams.put("transCurrency", transCurrency);
            packageParams.put("attach", remarks.toString());
            // 请求方式
            String http = "POST";
            // 签名
            String sign = http + "\n";
            // 讯联分配的唯一标识商户号
            String sid = trioCompany.getSid();
            // 接口URL
            String urls = "/scanpay/mer/" + sid + "/wpay/v1";
            sign += urls + "\n";
            sign += merTransTime + "\n";
            // CIL分配的用于签名/验签的key
            String key = trioCompany.getKey();
            sign += key + "\n";
            String strdata = JSONObject.toJSONString(packageParams);
            sign += strdata;
            sign = getSha256(sign);
            // 第三方接口访问路径的域名
            String companyUrl = trioCompany.getUrl();
            // 接口URL
            String url = companyUrl + urls;
            log.info("TRIO API 決済開始: url={} data={} sign={} merTransTime={}", url, strdata, sign,
                merTransTime);
            // 接口调用
            String resStr = postDataSign(url, strdata, sign, merTransTime);
            log.info("TRIO API 決済結果: resStr={}", resStr);
            // 支付跳转链接
            String h5payUrl = null;

            // 注文情報回復方法のパラメータを作成する
            WeChatAliPayBackInputDto backInputDto = new WeChatAliPayBackInputDto();

            backInputDto
                .setOrderNum(
                    inputDto.getOrderSummaryId() + inputDto.getOrderId().toString() + "01");

            backInputDto.setTransAmt(transAmt);

            backInputDto.setAttach(remarks.toString());
            // 返回结果类型转化
            JSONObject returnData = JSONObject.parseObject(resStr);
            if (null != returnData) {

                if ("00".equals(returnData.get("respCode"))) {

                    h5payUrl = returnData.get("h5payURL").toString();
                } else {

                    h5payUrl = null;

                    // 注文情報を回復する
                    orderService.revertOrder(backInputDto);
                }
            } else {

                h5payUrl = null;

                // 注文情報を回復する
                orderService.revertOrder(backInputDto);
            }

            outPutDto.setPayUrl(h5payUrl);

        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.036", (Object) null), ex);
        }

        return outPutDto;
    }

    /**
     * DB支払い状態更新.
     *
     * @param inputDto 更新条件
     */
    @Override
    public void updatePayStatus(WeChatAliPayBackInputDto inputDto) {

        try {

            PTrioResultDetail inputDao = new PTrioResultDetail();

            // トリオ支払結果明細ID
            inputDao.setTrioResultDetailId(Integer
                .valueOf(JSONObject.parseObject(inputDto.getAttach()).getString("maxDetailId")));
            // 注文サマリID
            inputDao.setOrderNo(inputDto.getOrderNum());
            // 支付结果code
            inputDao.setRespCode(inputDto.getRespCode());
            // 更新者
            inputDao.setUpdOperCd("TRIO");
            // 更新日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            inputDao.setUpdDateTime(dateTime);

            // 店舗ID取得
            String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

            // トリオ支払結果明細テーブル
            trioResultDetailRepository.updatePayStatus(storeId,
                inputDao.getTrioResultDetailId(),
                inputDao.getOrderNo(),
                inputDao.getRespCode(),
                inputDao.getUpdOperCd(),
                inputDao.getUpdDateTime());
        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.037", (Object) null), ex);
        }
    }

    /**
     * 繰り返し処理判断.
     *
     * @param inputDto 更新条件
     */
    @Override
    public Integer getCount(WeChatAliPayBackInputDto inputDto) {

        return trioResultDetailRepository.getCount(inputDto.getOrderNum(),
            Integer.valueOf(JSONObject.parseObject(inputDto.getAttach()).getString("maxDetailId")),
            PayResult.DEFAULT.getCode());
    }

    /**
     * 支払い情報を作成する.
     *
     * @param inputDto 検索条件
     */
    @Override
    public void insertPayment(WeChatAliPayBackInputDto inputDto) {

        try {

            // 店舗ID取得
            String storeId = JSONObject.parseObject(inputDto.getAttach()).getString("storeId");

            // 支払いIDのシーケンスNo取得
            GetSeqNoInputDto getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment"); // テーブル名
            getSeqNoInputDto.setItem("payment_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            GetSeqNoOutputDto getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            PPayment inputDao = new PPayment();
            // 店舗ID
            inputDao.setStoreId(storeId);
            // 支払いID
            inputDao.setPaymentId(getSeqNo.getSeqNo());
            // 注文サマリID
            inputDao.setOrderSummaryId(inputDto.getOrderNum().substring(0, 20));
            // 支払い金額
            inputDao
                .setPaymentAmount(new BigDecimal(inputDto.getTransAmt().replaceAll("^(0+)", "")));
            // 削除フラグ
            inputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            inputDao.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 登録日時
            ZonedDateTime dateTime = DateUtil.getNowDateTime();
            inputDao.setInsDateTime(dateTime);
            // 更新者
            inputDao.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            inputDao.setUpdDateTime(dateTime);
            // バージョン
            inputDao.setVersion(0);

            // 支払い明細IDのシーケンスNo取得
            getSeqNoInputDto = new GetSeqNoInputDto();
            getSeqNoInputDto.setStoreId(storeId); // 店舗ID
            getSeqNoInputDto.setTableName("p_payment_detail"); // テーブル名
            getSeqNoInputDto.setItem("payment_detail_id"); // 項目
            getSeqNoInputDto.setOperCd(CommonConstants.OPER_CD_MOBILE); // 登録更新者
            getSeqNo = itemInfoSharedService.getSeqNo(getSeqNoInputDto);

            // 支払い方式
            String paymentMethod = inputDto.getPaymentBrand();

            // 支払い方式コード
            String paymentMethodCode = "";
            if (paymentMethod.equals("WXP")) {
                paymentMethodCode = CodeConstants.AccountsType.WECHATPAY.getCode();
            } else {
                paymentMethodCode = CodeConstants.AccountsType.ALIPAY.getCode();
            }

            PPaymentDetail detailInputDao = new PPaymentDetail();
            // 店舗ID
            detailInputDao.setStoreId(storeId);
            // 支払い明細
            detailInputDao.setPaymentDetailId(getSeqNo.getSeqNo());
            // 支払いID
            Integer paymentId = paymentRepository
                .insertOrUpdate(inputDao.getStoreId(), inputDao.getPaymentId(),
                    inputDao.getOrderSummaryId(), inputDao.getPaymentAmount(),
                    inputDao.getDelFlag(), inputDao.getInsOperCd(), inputDao.getInsDateTime(),
                    inputDao.getUpdOperCd(), inputDao.getUpdDateTime(), inputDao.getVersion());
            detailInputDao.setPaymentId(paymentId);
            // 注文サマリID
            detailInputDao.setOrderSummaryId(inputDto.getOrderNum().substring(0, 20));
            // 注文ID
            detailInputDao.setOrderId(Integer
                .valueOf(
                    inputDto.getOrderNum().substring(20, inputDto.getOrderNum().length() - 2)));
            // 支払い方式
            detailInputDao.setPaymentMethodCode(paymentMethodCode);
            // 支払い金額
            detailInputDao
                .setPaymentAmount(new BigDecimal(inputDto.getTransAmt().replaceAll("^(0+)", "")));
            // 削除フラグ
            detailInputDao.setDelFlag(Flag.OFF.getCode());
            // 登録者
            detailInputDao.setInsOperCd(CommonConstants.OPER_CD_MOBILE);
            // 登録日時
            detailInputDao.setInsDateTime(dateTime);
            // 更新者
            detailInputDao.setUpdOperCd(CommonConstants.OPER_CD_MOBILE);
            // 更新日時
            detailInputDao.setUpdDateTime(dateTime);
            // バージョン
            detailInputDao.setVersion(0);
            // 支払明細テーブル
            paymentDetailRepository.save(detailInputDao);
        } catch (Exception ex) {
            throw new BusinessException("2005",
                ResultMessages.error().add("e.qr.ph.038", (Object) null), ex);
        }
    }

    /**
     * 利用Apache的工具类实现SHA-256加密.
     *
     * @param str 加密后的报文
     */
    private String getSha256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    private String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte byteData : bytes) {
            temp = Integer.toHexString(byteData & 0xFF);
            if (temp.length() == 1) {
                //1ｵﾃｵｽﾒｻﾎｻｵﾄｽﾐｲｹ0ｲﾙﾗ・
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 接口调用.
     *
     * @param urlStr   接口URL
     * @param data     接口参数
     * @param sign     签名
     * @param dateTime 当前时间
     */
    private String postDataSign(String urlStr, String data, String sign, String dateTime) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("content-type", "application/json");
            conn.setRequestProperty("Authorization", sign);
            conn.setRequestProperty("DateTime", dateTime);
            conn.setRequestProperty("SignType", "SHA256");
            conn.setRequestProperty("Method", "POST");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            if (data == null) {
                data = "";
            }
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                //
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
