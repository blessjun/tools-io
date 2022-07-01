package com.sunfujun.tools.io.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
public class Swagger2Config {

  @Bean
  public Docket createRestApi(Environment environment) {
    // 设置要显示swagger的环境
    Profiles of = Profiles.of("localdev", "local", "dev");
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("cn.com.coho")
        .apiInfo(apiInfo())
        .enable(environment.acceptsProfiles(of))
        .select()
        // 扫描的包所在位置
        .apis(RequestHandlerSelectors.basePackage("cn.com.coho.tools.io.web"))
        // 扫描的 URL 规则
        .paths(PathSelectors.any())
        .build();
  }


  private ApiInfo apiInfo() {
    // 联系信息
    return new ApiInfoBuilder()
        // 大标题
        .title("io")
        // 描述
//        .description("描述")
        // 版本
        .version("0.0.1-SNAPSHOT")
        .build();
  }


  @Bean
  public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
    return new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
          customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
        }
        return bean;
      }

      private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
        List<T> copy = mappings.stream()
                .filter(mapping -> mapping.getPatternParser() == null)
                .collect(Collectors.toList());
        mappings.clear();
        mappings.addAll(copy);
      }

      @SuppressWarnings("unchecked")
      private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
        try {
          Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
          field.setAccessible(true);
          return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
        } catch (IllegalArgumentException | IllegalAccessException e) {
          throw new IllegalStateException(e);
        }
      }
    };
  }
}
