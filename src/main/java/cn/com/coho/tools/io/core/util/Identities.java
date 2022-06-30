package cn.com.coho.tools.io.core.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author scott
 */
public class Identities {

    private static SecureRandom random = new SecureRandom();

    public Identities() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }


    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

}
