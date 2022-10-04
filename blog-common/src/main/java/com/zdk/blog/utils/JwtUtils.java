package com.zdk.blog.utils;

import com.zdk.blog.constant.AuthConstant;
import com.zdk.blog.exception.BusinessException;
import com.zdk.blog.security.Passport;
import com.zdk.blog.security.Payload;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Description Jwt工具类
 * @Author zdk
 * @Date 2022/8/25 10:49
 */
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成token 过期时间单位默认为 分钟
     * @param userInfo
     * @param privateKey
     * @param expire
     * @return
     */
    public static String generateToken(Object userInfo, String privateKey, long expire) {
        PrivateKey privateKeyObj = RsaUtils.getPrivateKey(privateKey);
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(expire);
        Date expireDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .claim(AuthConstant.JWT_PAYLOAD_USER_KEY, JsonUtils.toString(userInfo))
                .setId(createJti())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.RS256,privateKeyObj)
                .compact();
    }

    /**
     * 生成token 过期时间单位可选
     * @param userInfo
     * @param privateKey 私钥
     * @param expire 时间
     * @param unit 时间单位
     * @return
     */
    public static String generateToken(Object userInfo, String privateKey, long expire, ChronoUnit unit) {
        PrivateKey privateKeyObj = RsaUtils.getPrivateKey(privateKey);
        LocalDateTime localDateTime = LocalDateTime.now().plus(expire,unit);
        Date expireDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().claim(AuthConstant.JWT_PAYLOAD_USER_KEY, JsonUtils.toString(userInfo)).setId(createJti()).setExpiration(expireDate).signWith(SignatureAlgorithm.RS256,privateKeyObj).compact();
    }

    /**
     * 生成jwt id
     * @return
     */
    private static String createJti() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 解析token并返回
     * @param token
     * @param publicKey
     * @return
     * @throws ExpiredJwtException
     */
    private static Jws<Claims> parserToken(String token, String publicKey) throws ExpiredJwtException {
        PublicKey publicKeyObj = RsaUtils.getPublicKey(publicKey);
        return Jwts.parser().setSigningKey(publicKeyObj).setAllowedClockSkewSeconds(AuthConstant.ALLOWED_CLOCK_SKEW_SECONDS).parseClaimsJws(token);
    }

    /**
     * 检查token是否有效
     * @param token
     * @param publicKey
     * @return
     * @throws ExpiredJwtException
     */
    public static void checkToken(String token, String publicKey) {
        PublicKey publicKeyObj = RsaUtils.getPublicKey(publicKey);
        Jwts.parser().setSigningKey(publicKeyObj).setAllowedClockSkewSeconds(AuthConstant.ALLOWED_CLOCK_SKEW_SECONDS).parseClaimsJws(token);
    }

    /**
     * 获取Claims中的值
     * @param token
     * @param publicKey
     * @param userType
     * @return
     * @param <T>
     * @throws ExpiredJwtException
     */
    public static <T> Payload<T> getInfoFromToken(String token, String publicKey, Class<T> userType) throws ExpiredJwtException {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JsonUtils.toBean(body.get(AuthConstant.JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取Claims中的值
     * @param token
     * @param publicKey
     * @return
     * @param <T>
     * @throws ExpiredJwtException
     */
    public static <T> Payload<T> getInfoFromToken(String token, String publicKey) throws ExpiredJwtException {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        Payload<T> claims = new Payload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 刷新token过期时间(在过期之前)
     * @param token
     * @param publicKey
     * @param privateKey
     * @param expire
     * @return
     */
    public static String refreshTokenExpired(String token, String publicKey, String privateKey, int expire) {
        try {
            checkToken(token, publicKey);
        }catch (Exception e){
            throw new BusinessException("token已失效");
        }
        Payload<Passport> payload = getInfoFromToken(token, publicKey, Passport.class);
        Passport passport = payload.getUserInfo();
        return generateToken(passport, privateKey, expire);
    }
}
