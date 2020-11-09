package com.cnc.qr.common.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * md5暗号化ユーティリティ.
 */
public class Md5Util {

    /**
     * MD5暗号化アルゴリズム.
     *
     * @param plainText MD5コードに変換される文字列
     * @return 32ビットの16進数文字列を返します
     */
    public static String toMd5(String plainText) {
        String reMd5 = "";
        Logger log = LoggerFactory.getLogger(Md5Util.class);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();

            int i;

            StringBuilder buf = new StringBuilder("");
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            reMd5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
        }
        return reMd5;
    }

    /**
     * MD5.
     *
     * @param data MD5コードから変換される文字列
     * @return 文字列
     */
    public static String md5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * SHAに変換する.
     *
     * @param value 変換文字列
     * @param sha   暗号化アルゴリズム
     * @return 変換結果
     */
    public static String toSha(String value, String sha) {
        String shaData = StringUtils.EMPTY;
        try {
            MessageDigest digest = MessageDigest.getInstance(sha);
            digest.reset();
            digest.update(value.getBytes(StandardCharsets.UTF_8));
            return String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shaData;
    }

    /**
     * AES解密.
     *
     * @param encryptStr 密文
     * @param decryptKey 秘钥，必须为16个字符组成
     * @return 明文
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) {
        if (StringUtils.isEmpty(encryptStr) || StringUtils.isEmpty(decryptKey)) {
            return encryptStr;
        }

        try {
            byte[] encryptByte = Base64.getDecoder().decode(encryptStr);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptByte);
            return new String(decryptBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encryptStr;
    }

    /**
     * AES加密.
     *
     * @param content    明文
     * @param encryptKey 秘钥，必须为16个字符组成
     * @return 密文
     */
    public static String aesEncrypt(String content, String encryptKey) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(encryptKey)) {
            return content;
        }

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
            byte[] encryptStr = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptStr).replace("+", "xMl3Jk")
                .replace("/", "Por21Ld").replace("=", "Ml32");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }
}
