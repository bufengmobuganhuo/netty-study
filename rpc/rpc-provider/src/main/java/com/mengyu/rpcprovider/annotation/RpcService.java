package com.mengyu.rpcprovider.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuzhang
 * @date 2021/1/18 下午5:16
 * 用于标识一个需要发不到注册中心的服务
 * 消费者必须指定完全一样的属性才能正确调用
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    /**
     * 服务类型
     * @return
     */
    Class<?> serviceInterface() default Object.class;

    /**
     * 服务版本
     * @return
     */
    String serviceVersion() default "1.0";
}
