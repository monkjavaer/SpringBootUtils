package com.car.orbit.orbitutil.tools;

import java.util.Random;

/**
 * CreateDate：2018/5/22 <br/>
 * Author：NieLixiang <br/>
 * Description: utils to generate random String with given length
 **/
public class RandomStrGenerator {

    /**
     * generate random String with given length
     *
     * @param strLength needed String length
     * @return generated random String
     */
    public static String generateRandomStr(int strLength) {
        Random random = new Random(47);
        String seed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; //String seed
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < strLength; index++) {
            builder.append(seed.charAt(random.nextInt(seed.length())));
        }
        return builder.toString();
    }
}
