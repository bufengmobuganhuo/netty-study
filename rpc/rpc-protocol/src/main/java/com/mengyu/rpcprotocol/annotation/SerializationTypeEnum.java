package com.mengyu.rpcprotocol.annotation;

public enum SerializationTypeEnum {
    HESSIAN((byte) 0x00),
    JSON((byte) 0x01);

    byte value;

    SerializationTypeEnum(byte value) {
        this.value = value;
    }

    public static SerializationTypeEnum findByType(byte type) {
        for (SerializationTypeEnum typeEnum : values()) {
            if (typeEnum.value == type) {
                return typeEnum;
            }
        }
        throw new IllegalArgumentException("no value :" + type + " mapped enum");
    }
}
