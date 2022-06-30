package cn.com.coho.tools.io.core.util;

/**
 * @author scott
 */
public class Constants {

    public static final String AUTH_KEY = "Authorization";

    public static final Long TOKEN_TIME_OUT = 3600L * 1000;

    public static final Long LENGTH_1M = 1024L * 1024L;

    public static final Long TIME_S = 1000L;

    public static final String JOB_GROUP_NAME = "任务链";


    /**
     * 加密方式
     */

    public static final String MD5 = "MD5";

    public static final String SHA_256 = "SHA256";

    public static final String SHA_1 = "SHA1";


    public static final String REQUEST_HEADER = "header";

    public static final String REQUEST_QUERY = "query";

    public static final String REQUEST_BODY = "body";

    public static final String REQUEST_RETURN = "return";


    public static final int THREAD_PAGE_NUM = 10;

}
