package com.mengyu.rpcprotocol.enumration;

/**
 * @author yuzhang
 */

public enum MsgStatus {
    /**
     * 成功
     */
    SUCCESS((byte)0x00),
    /**
     * 失败
     */
    FAIL((byte)0x01);

    private final byte value;

    public byte getValue() {
        return value;
    }

    MsgStatus(byte value) {
        this.value = value;
    }
}
