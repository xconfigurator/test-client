package liuyang.testclient.modules.app.jdk.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 加密工具类
 *
 * @author suhj
 * @version 1.0 20190216
 */
public class AesEncryptUtils {

    private AesEncryptUtils() {
    }

    private static final int NUM_TWO = 2;
    private static final int NUM_THREE = 3;
    private static final int NUM_TEN = 10;
    private static final int NUM_65 = 65;
    private static final int NUM_97 = 97;
    private static final int NUM_25 = 25;

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(NUM_THREE);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(random.nextDouble() * NUM_25 + NUM_65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(random.nextDouble() * NUM_25 + NUM_97);
                    sb.append((char) result);
                    break;
                case NUM_TWO:
                    sb.append(random.nextInt(NUM_TEN));
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

}
