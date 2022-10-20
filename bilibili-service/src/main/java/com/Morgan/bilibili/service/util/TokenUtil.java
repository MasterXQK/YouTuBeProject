package com.Morgan.bilibili.service.util;

import com.Morgan.bilibili.domain.exception.ConditionException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    private static final String ISSUER = "java_master_xqk";

    /*
    根据UserID生成Token
     */
    public static String generateToken(Long userId) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 30); // 30s expiration
        return JWT.create().withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(algorithm);
    }

    /*
    验证Token
     */
    public static Long verifyToken(String token) {
        try {
            // 这里不能简单返回异常 token有可能过期
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();// 根据token 反向获得UserID
            return Long.valueOf(userId);
        } catch (TokenExpiredException e) {
            // token过期 特殊报错码 刷新token
            throw new ConditionException("555", "token过期！", "Token expired!");
        } catch (Exception e) {
            throw new ConditionException("400", "非法用户token！", "Illegal user token!");
        }
    }

}

