package com.mengyu.rpcprovider.provider;

import com.mengyu.rpcprovider.annotation.RpcService;
import com.mengyu.rpcprovider.meta.ServiceMeta;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhang
 * @date 2021/1/18 下午7:38
 * TODO
 */
@Slf4j
public class RpcProvider implements InitializingBean, BeanPostProcessor {
    /**
     * 存放服务初始化后对应的Bean
     */
    private final Map<String, Object> rpcServiceMap = new HashMap<>();

    private final int servicePort;
    private final RegistryService

    public RpcProvider(int servicePort) {
        this.servicePort = servicePort;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Bean被实例化后调用，如果bean被@RpcService注解修饰，则说明这个Bean是一个要被发布的服务，将其缓存到Map中
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            String serviceName = rpcService.serviceInterface().getName();
            String serviceVersion = rpcService.serviceVersion();
            try {
                // todo
                ServiceMeta serviceMeta = new ServiceMeta("", servicePort, serviceName, serviceVersion);
                rpcServiceMap.put("", bean);
            } catch (Exception e) {
                log.error("failed to register service {}#{}", serviceName, serviceVersion, e);
            }
        }
        return bean;
    }
}
