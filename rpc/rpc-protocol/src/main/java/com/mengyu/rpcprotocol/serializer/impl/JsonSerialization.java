package com.mengyu.rpcprotocol.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.mengyu.rpcprotocol.serializer.RpcSerialization;

/**
 * @author yuzhang
 * @date 2021/1/19 下午4:25
 * TODO
 */
public class JsonSerialization implements RpcSerialization {
    @Override
    public <T> byte[] serialize(T obj) throws Exception {
        if (obj==null){
            throw new NullPointerException();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws Exception {
        return null;
    }
}
