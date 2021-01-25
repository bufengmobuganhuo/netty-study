package com.mengyu.rpcprovider.config;

import com.mengyu.rpcprovider.enumration.RegistryType;
import com.mengyu.rpcprovider.provider.RpcProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author yuzhang
 * @date 2021/1/18 下午5:07
 * TODO
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProviderAutoConfiguration {
    @Resource
    private RpcProperties rpcProperties;

    @Bean
    public RpcProvider init() {
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType());

    }
}
