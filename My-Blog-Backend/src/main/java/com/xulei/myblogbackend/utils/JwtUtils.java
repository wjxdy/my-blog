package com.xulei.myblogbackend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 自动生成符合 HS256 要求的密钥（256位）
    private static final Key SIGN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION = 86400000; // 24小时

    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SIGN_KEY)  // 使用安全密钥签名
                .compact();
    }

    // 解析 JWT 的方法（可选）
    public static Map<String, Object> parseJwt(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGN_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    //jwt验证

    public static boolean verify(String token){

        try {
            parseJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

}
