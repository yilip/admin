package com.lip.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author
 * 
 */
public class SpringUtil {
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    private static ApplicationContext context;
    /**
     * 得到上下文
     * 
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 根据beanId得到bean
     * 
     * @param beanName
     *            beanId
     * @return bean实体
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 根据beanType得到bean
     * 
     * @param requiredType
     * @return bean实体
     */
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    /**
     * 直接传入applicationContext
     * 
     * @param actx
     *            应用程序上下文对象
     */
    public static void init(ApplicationContext actx) {
        context = actx;
    }
}