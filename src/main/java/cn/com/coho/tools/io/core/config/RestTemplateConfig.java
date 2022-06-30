package cn.com.coho.tools.io.core.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * restTemplate配置类
 */
@Configuration
public class RestTemplateConfig {

    @Value("${ok.http.connect-timeout}")
    private Integer connectTimeout;

    @Value("${ok.http.read-timeout}")
    private Integer readTimeout;

    @Value("${ok.http.write-timeout}")
    private Integer writeTimeout;

    @Value("${ok.http.max-idle-connections}")
    private Integer maxIdleConnections;

    @Value("${ok.http.keep-alive-duration}")
    private Long keepAliveDuration;


    /**
     * 声明 RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        ClientHttpRequestFactory factory = httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        // 可以添加消息转换
        //restTemplate.setMessageConverters(...);
        // 可以增加拦截器
        //restTemplate.setInterceptors(...);
        return restTemplate;
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new OkHttp3ClientHttpRequestFactory(okHttpConfigClient());
    }

    public OkHttpClient okHttpConfigClient(){
        return new OkHttpClient().newBuilder()
                .connectionPool(pool())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                // 拦截器
//                .addInterceptor()
                .build();
    }

    public ConnectionPool pool() {
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
    }

}