package com.base.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @Title: DESUtils
 * @Package: com.base.springboot.utils
 * @Description: 提供DES加密与解密工具
 * @Author: monkjavaer
 * @Date: 2019/8/6 17:47
 * @Version: V1.0
 */
public class DESUtils {
    /** 日志对象 **/
    private static Logger logger = LoggerFactory.getLogger(DESUtils.class);

    /** 加密算法 */
    private final static String DES = "DES";

    /** 编码格式 */
    private final static String ENCODE = "UTF-8";

    /** 私钥 */
    private final static String KEY = "CF3941B518AADF56E8900516F48EB1A3";

    /**
     * 对输入文本进行DES加密
     *
     * @param text 输入明文文本
     * @return String 输入文本对应的DES密文
     */
    public static String encrypt(String text) {
        try {
            //生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            //从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(KEY.getBytes(ENCODE));

            //创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            byte[] bytes = cipher.doFinal(text.getBytes(ENCODE));

            //返回DES密文
            return new BASE64Encoder().encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对输入密文进行DES解密
     *
     * @param ciphertext 输入密文
     * @return String 输入密文对应的解密后文本
     */
    public static String decrypt(String ciphertext) {
        try {
            //生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            //从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(KEY.getBytes(ENCODE));

            //创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            //Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            byte[] buffer = new BASE64Decoder().decodeBuffer(ciphertext);
            byte[] bytes = cipher.doFinal(buffer);

            //返回解密后的文本
            return new String(bytes, ENCODE);
        } catch (Exception e) {
            logger.warn("unable to decrypt text:" + ciphertext);
            throw new RuntimeException(e);
        }
    }
}
