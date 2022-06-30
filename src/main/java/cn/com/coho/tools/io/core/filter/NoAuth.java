package cn.com.coho.tools.io.core.filter;

import java.lang.annotation.*;

/**
 * @author scott
 * token验证注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuth {
}
