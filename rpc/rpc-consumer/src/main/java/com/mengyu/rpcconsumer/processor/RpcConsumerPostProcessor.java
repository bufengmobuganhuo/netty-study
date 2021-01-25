package com.mengyu.rpcconsumer.processor;

import com.mengyu.rpcconsumer.annotation.RpcReference;
import com.mengyu.rpcconsumer.constant.RpcConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:00
 * TODO
 */
@Slf4j
@Component
public class RpcConsumerPostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {
    private ApplicationContext context;
    private ClassLoader classLoader;
    private final Map<String, BeanDefinition> rpcRefBeanDefinitions = new LinkedHashMap<>();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Spring容器加载Bean的定义之后以及Bean实例化之前执行
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                // 修改BeanDefinition
                ReflectionUtils.doWithFields(clazz, this::parseRpcReference);
            }
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcRefBeanDefinitions.forEach((beanName, beanDefinition) -> {
            if (context.containsBean(beanName)){
                throw new IllegalArgumentException("spring context already has a bean named "+beanName);
            }
            // 构造完RpcReferenceBean后，将RpcReferenceBean的BeanDefinition重新注册到Spring容器中
            registry.registerBeanDefinition(beanName, rpcRefBeanDefinitions.get(beanName));
            log.info("registered RpcReferenceBean {} success", beanName);
        });
    }

    /**
     * 对每个bean的所有field进行检测，如果field被声明了@RpcReference注解，
     * 通过 BeanDefinitionBuilder 构造 RpcReferenceBean 的定义，
     * 并为 RpcReferenceBean 的成员变量赋值，包括服务类型 interfaceClass、
     * 服务版本 serviceVersion、注册中心类型 registryType、注册中心地址 registryAddr 以及超时时间 timeout
     *
     * @param field
     */
    private void parseRpcReference(Field field) {
        RpcReference annotion = AnnotationUtils.getAnnotation(field, RpcReference.class);
        if (annotion != null) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RpcReference.class);
            builder.setInitMethodName(RpcConstants.INIT_METHO_NAME);
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("serviceVersion", annotion.serviceVersion());
            builder.addPropertyValue("registryType", annotion.registryType());
            builder.addPropertyValue("registryAddr", annotion.registryAddress());
            builder.addPropertyValue("timeout", annotion.timeout());
            BeanDefinition beanDefinition = builder.getBeanDefinition();
            rpcRefBeanDefinitions.put(field.getName(), beanDefinition);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
