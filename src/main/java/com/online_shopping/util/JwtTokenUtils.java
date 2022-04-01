package com.online_shopping.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yeye
 * @date 2022/4/1  23:42
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    public static final String SECRET = "jwtsecret";
    public static final String ISS = "echisan";


    private static final long EXPIRATION = 1000 * 60 * 60;
    private static final long EXPIRATION_REMEMBER = 1000 * 60 * 60 * 24 * 7;

    /**
     * 创建token
     *
     * @param username 用户名
     * @param id id
     * @param isRememberMe 是否记住我
     * @return
     */
    public static String createToken(String username,int id, boolean isRememberMe){
        return createToken(username,id,isRememberMe,new HashMap<>());
    }

    public static String createToken(String username, int id, Boolean isRememberME, Map<String, Object> map){

        long expiration = isRememberME ? EXPIRATION_REMEMBER : EXPIRATION;

        map.put("username", username);
        map.put("id", id);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .addClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();

    }

    /**
     * 从token中获取username
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }


    /**
     * 获取用户id
     * @param token
     * @return
     */
    public static int getUserId(String token){
        return Integer.parseInt(getTokenBody(token).get("id").toString());
    }

    /**
     * 获取附加数据
     * @param token
     * @param key
     * @return
     */
    public static Object getAttachData(String token, String key){
        return getTokenBody(token).get(key);
    }

    /**
     * 是否已过期
     * @param token token字符串
     * @return 是否过期？
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }


    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(JwtTokenUtils.TOKEN_PREFIX, ""))
                .getBody();
    }

}


