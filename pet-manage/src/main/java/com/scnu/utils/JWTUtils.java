package com.scnu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static String secretKey = "token!Q@W3e4r";
    /**
     * 生成token
     * @param map  //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE,60*24);
        builder.withExpiresAt(instance.getTime());
        builder.withIssuedAt(new Date());//签发时间
        return builder.sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
    }

    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
    }


    /**
     * 获取全部有效负载 Payload（用户信息和其他数据）
     *
     * @param token 令牌
     */
    public static Map<String, Claim> parseForPayload(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getClaims();
    }


    /**
     * 获取有效负载Payload中指定value（用户信息和其他数据）
     *
     * @param token 令牌
     */
    public static <T> T parseForValue(String token, String key, Class<T> clazz) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token)
                .getClaims()
                .get(key)
                .as(clazz);
    }
}