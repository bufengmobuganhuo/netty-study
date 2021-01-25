package com.mengyu.rpcconsumer.factory;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author yuzhang
 * @date 2021/1/18 下午7:55
 * TODO
 */
public class RpcReferenceBean implements FactoryBean<Object> {
    private Class<?> interfaceClass;
    private String serviceVersion;
    private String registryType;
    private String registryAddr;
    private long timeout;
    private Object object;

    @Override
    public Object getObject() throws Exception {
        return object;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    public void init(){
        // todo 生成动态代理对象并赋值给object
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddr(String registryAddr) {
        this.registryAddr = registryAddr;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
