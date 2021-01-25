package com.mengyu.rpcprotocol.serializer;

import org.springframework.util.ClassUtils;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:38
 * TODO
 */
public interface RpcSerialization {
    <T> byte[] serialize(T obj) throws Exception;

    <T> T deserialize(byte[] data, Class<T> clz) throws Exception;
}
