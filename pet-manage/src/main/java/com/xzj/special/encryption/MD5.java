package com.xzj.special.encryption;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 * MD5：不可逆加密的算法
 * MD5，全称为“Message-Digest Algorithm 5”，翻译过来叫“信息摘要算法”。
 * 它可以将任意长度的数据通过散列算法，生成一个固定长度的散列值。MD5算法的输出长度为128位，通常用32个16进制数表示。
 * @author xuzhijun
 * @date 2024/9/26 14:04
 */
public class MD5 {
    private static final String MD5_ALGORITHM = "MD5";
    public static String encrypt(String data) throws Exception {
        // 获取MD5算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
        // 计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        Formatter formatter = new Formatter();
        // 补齐前导0，并格式化
        for (byte b : digest) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}