package com.cnc.qr.common.constants;

public class PrintConstants {

    /**
     * エンコード形式.
     */
    public static final String ENCODING_FORMAT_SHIFT_JIS = "Shift_JIS";

    /**
     * 境界線.
     */
    public static final String DIVIDING_LINE = "************************************************";

    /**
     * 境界線.
     */
    public static final String UNDER_LINE = "------------------------------------------------";

    /**
     * LAN.
     */
    public static final String TCP = "TCP:";

    /**
     * Bluetooth.
     */
    public static final String BT = "BT:";

    /**
     * スペース.
     */
    public static final String SPACE = " ";

    /**
     * 改行.
     */
    public static final String NEWLINE = "\r\n";

    /**
     * 開始.
     */
    public static byte[] STAND_SIZE_COMMAND = new byte[]{0x1b, 0x1c, 0x70, 0x01, 0x00};

    /**
     * fron大小設定 后两位设置文字的大小 0x00, 0x00 最小　0x01, 0x01 最大.
     */
    public static byte[] FRONT_SIZE_MAX_COMMAND = new byte[]{0x06, 0x09, 0x1b, 0x69, 0x01, 0x01};

    /**
     * fron大小設定 后两位设置文字的大小 0x00, 0x00 最小　0x01, 0x01 最大.
     */
    public static byte[] FRONT_SIZE_NORMAL_COMMAND = new byte[]{0x06, 0x09, 0x1b, 0x69, 0x01, 0x00};

    /**
     * fron大小設定 后两位设置文字的大小 0x00, 0x00 最小　0x01, 0x01 最大.
     */
    public static byte[] FRONT_SIZE_MIN_COMMAND = new byte[]{0x06, 0x09, 0x1b, 0x69, 0x00, 0x00};

    /**
     * bold.
     */
    public static byte[] BOLD_COMMAND = new byte[]{0x1b, 0x45};
    public static byte[] BOLD_OFF_COMMAND = new byte[]{0x1b, 0x46};

    /**
     * 对齐方式： 左边对齐.
     */
    public static byte[] ALIGNMENT_LEFT_COMMAND = new byte[]{0x1b, 0x1d, 0x61, 0x00};
    /**
     * 对齐方式 ： 中间对齐.
     */
    public static byte[] ALIGNMENT_CENTER_COMMAND = new byte[]{0x1b, 0x1d, 0x61, 0x01};

    /**
     * 对齐方式 ： 右边对齐.
     */
    public static byte[] ALIGNMENT_RIGHT_COMMAND = new byte[]{0x1b, 0x1d, 0x61, 0x02};

    /**
     * Cut.
     */
    public static byte[] CUT_COMMAND = new byte[]{0x1b, 0x64, 0x02, 0x07};
}
