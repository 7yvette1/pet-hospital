package com.xzj.special.encryption;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author xuzhijun
 * @date 2024年09月26日 14:00
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String data = "Hello World";
        String encryptedData = MD5.encrypt(data);
        System.out.println("MD5加密后的数据：" + encryptedData);

        String encryptedData1 = SHA256.encrypt(data);
        System.out.println("SHA256加密后的数据：" + encryptedData1);

        String key = "12345678";
        String encryptedData2 = DES.encrypt(data, key);
        System.out.println("DES加密后的数据：" + encryptedData2);
        String decryptedData2 = DES.decrypt(encryptedData2, key);
        System.out.println("DES解密后的数据：" + decryptedData2);

        String encryptedData3 = AES.encrypt(data);
        System.out.println("AES加密后的数据：" + encryptedData3);
        String decryptedData3 = AES.decrypt(encryptedData3);
        System.out.println("AES解密后的数据：" + decryptedData3);

        KeyPair keyPair = RSA.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String encryptedData4 = RSA.encrypt(data, publicKey);
        System.out.println("RSA加密后的数据：" + encryptedData4);
        String decryptedData4 = RSA.decrypt(encryptedData4, privateKey);
        System.out.println("RSA解密后的数据：" + decryptedData4);
    }

}
