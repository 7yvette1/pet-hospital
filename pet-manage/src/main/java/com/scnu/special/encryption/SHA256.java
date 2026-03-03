package com.scnu.special.encryption;

import java.security.MessageDigest;

/** 
 * SHA256：不可逆加密的算法
 * SHA（Secure Hash Algorithm）系列算法是一组密码散列函数，用于将任意长度的数据映射为固定长度的散列值。S
 * HA系列算法由美国国家安全局（NSA）于1993年设计，目前共有SHA-1、SHA-2、SHA-3三种版本。
 * 其中SHA-1系列存在缺陷，已经不再被推荐使用。
 * SHA-2算法包括SHA-224、SHA-256、SHA-384和SHA-512四种散列函数，分别将任意长度的数据映射为224位、256位、384位和512位的散列值。
 * @author xuzhijun
 * @date 2024/9/26 14:09
 */
public class SHA256 {
    private static final String SHA_256_ALGORITHM = "SHA-256";
    public static String encrypt(String data) throws Exception {
        //获取SHA-256算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
        //计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转换为15进制字符串
        for (byte b : digest) {
            stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return stringBuilder.toString();
    }
}