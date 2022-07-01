package com.sunfujun.tools.io.core.groovy;


public class BeanGroovyUtils {

    /**
     * 判断class 是否实现接口
     * @param cls
     * @param parentClass
     * @return
     */
    public static boolean isInherit(Class cls, Class parentClass) {
        return parentClass.isAssignableFrom(cls);
    }
}
