package com.mengyu.rpcprotocol.enumration;

import lombok.Data;

/**
 * @author yuzhang
 * @date 2021/1/19 下午4:53
 * TODO
 */
public enum MsgType {
    /**
     * 消息类型为响应
     */
    RESPONSE((byte) 0x00),
    /**
     * 消息类型为请求
     */
    REQUEST((byte) 0x01),
    /**
     * 消息类型为心跳
     */
    HEARTBEAT((byte) 0x02);

    private final byte value;

    MsgType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static MsgType findByType(byte msgType) {
        for (MsgType type : values()) {
            if (msgType == type.value) {
                return type;
            }
        }
        throw new IllegalArgumentException();
    }
}
