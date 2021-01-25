package com.mengyu.rpcprovider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yuzhang
 * @date 2021/1/18 下午4:53
 * TODO
 */
@Data
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {
    private int servicePort;
    private String registryAddr;
    private String registryType;
}
