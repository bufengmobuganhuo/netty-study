package com.mengyu.rpcprotocol.serializer.factory;

import com.mengyu.rpcprotocol.annotation.SerializationTypeEnum;
import com.mengyu.rpcprotocol.serializer.RpcSerialization;
import com.mengyu.rpcprotocol.serializer.impl.HessianSerialization;
import com.mengyu.rpcprotocol.serializer.impl.JsonSerialization;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:45
 * TODO
 */
public class SerializationFactory {
    public static RpcSerialization getRpcSerializer(byte serializationType) {
        SerializationTypeEnum typeEnum = SerializationTypeEnum.findByType(serializationType);
        switch (typeEnum) {
            case HESSIAN:
                return new HessianSerialization();
            case JSON:
                return new JsonSerialization();
            default:
                throw new IllegalArgumentException("serialization type is illegal, " + serializationType);
        }
    }
}
