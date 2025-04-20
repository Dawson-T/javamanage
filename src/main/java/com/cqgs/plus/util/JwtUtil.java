package com.cqgs.plus.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // 设置一个安全的密钥，建议用更复杂的形式保存，例如环境变量
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1小时

    /**
     * 注册用户时生成 JWT token
     *
     * @param userId 用户ID
     * @param username 用户名
     * @return token 字符串
     */
    public static String generateToken(String userId, String username) {
        String token = Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();

        return "Bearer " + token; // ✅ 加上前缀，方便直接用于 Authorization 头部
    }


    /**
     * 验证 JWT token 并解析其中的 payload
     *
     * @param token JWT 字符串
     * @return 解析结果（如用户ID等）
     * @throws JwtException 如果 token 无效或过期
     */
    public static Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
