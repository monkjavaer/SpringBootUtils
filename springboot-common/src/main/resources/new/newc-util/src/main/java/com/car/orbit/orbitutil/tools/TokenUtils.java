package com.car.orbit.orbitutil.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

/**
 * CreateDate：2018/5/17 <br/>
 * Author：NieLixiang <br/>
 * Description: token related utils
 **/
public class TokenUtils {

    private static final String LINK = "-·-";

    /**
     * generate a login token after login success
     *
     * @param userID logged in user ID
     * @param userName logged in user name
     * @param loginIP logged IP
     * @return a new login token
     */
    public static String generateToken(String userID, String userName, String loginIP) {
        String encryptText = DESUtils.encrypt(userID + LINK + userName + LINK + loginIP + LINK + System.currentTimeMillis());
        String str = encryptText.replaceAll("\\r", "").replaceAll("\\n", "");
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get login user ID from login token
     *
     * @param token login token
     * @return login user ID
     */
    public static String getUserID(String token) {
        String str = null;
        try {
            str = URLDecoder.decode(token, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (str != null) {
            return DESUtils.decrypt(str).split(LINK)[0];
        }
        return null;
    }

    /**
     * get login user name from login token
     *
     * @param token login token
     * @return login user name
     */
    public static String getUserName(String token) {
        try {
            return DESUtils.decrypt(URLDecoder.decode(token, "utf-8")).split(LINK)[1];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get login IP from login token
     *
     * @param token login token
     * @return login IP
     */
    public static String getLoginIP(String token) {
        return DESUtils.decrypt(token.replaceAll("%2B", "\\+")).split(LINK)[2];
    }

    /**
     * get login time from login token
     *
     * @param token login token
     * @return login time
     */
    public static Date getLoginTime(String token) {
        return new Date(Long.parseLong(DESUtils.decrypt(token.replaceAll("%2B", "\\+")).split(LINK)[3]));
    }

    /**
     * charge if given token is a legal token
     *
     * @param token given token
     * @return legal verify response
     */
    public static boolean isLegalToken(String token) {
        try {
            String text = DESUtils.decrypt(URLDecoder.decode(token, "utf-8"));
            return text.split(LINK).length - 1 == 3;
        } catch (Exception e) {
            return false;
        }
    }
}
