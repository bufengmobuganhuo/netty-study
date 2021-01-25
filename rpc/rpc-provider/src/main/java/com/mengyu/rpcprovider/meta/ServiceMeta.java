package com.mengyu.rpcprovider.meta;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuzhang
 * @date 2021/1/18 下午7:44
 * TODO
 */
@Data
@AllArgsConstructor
public class ServiceMeta {
    private String serviceAddr;

    private int servicePort;

    private String serviceName;

    private String serviceVersion;
}
