package com.cnc.qr.common.util;

import com.cnc.qr.common.constants.CommonConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * 現在時刻取得.
     *
     * @return 現在時刻
     */
    public static ZonedDateTime getNowDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.atZone(ZoneId.of(CommonConstants.TIMEZONE_TOKYO));
    }

    /**
     * 現在時刻文字列取得.
     *
     * @return 現在時刻文字列
     */
    public static String getNowDateTimeString(String dataFormat) {
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(dataFormat);
        return datetimeFormatter.format(ZonedDateTime.now());
    }

    /**
     * 文字列を日付型に変換して返却する.
     *
     * @param dateStr    日付文字列
     * @param dataFormat 日付フォーマット
     * @return 変換後日付
     */
    public static ZonedDateTime getZonedDateByString(String dateStr, String dataFormat) {
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(dataFormat)
            .withZone(ZoneId.of(CommonConstants.TIMEZONE_TOKYO));
        return ZonedDateTime.parse(dateStr, datetimeFormatter);
    }

    /**
     * 日付を文字列に変換して返却する.
     *
     * @param dateTime   日付
     * @param dataFormat 日付フォーマット
     * @return 日付文字列
     */
    public static String getZonedDateString(ZonedDateTime dateTime, String dataFormat) {
        DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern(dataFormat);
        return datetimeFormatter.format(dateTime);
    }
}
