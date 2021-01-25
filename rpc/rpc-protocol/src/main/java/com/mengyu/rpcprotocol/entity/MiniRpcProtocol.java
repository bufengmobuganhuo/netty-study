package com.mengyu.rpcprotocol.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:31
 * TODO
 */
@Data
public class MiniRpcProtocol<T> implements Serializable {
    /**
     * 协议头
     */
    private MsgHeader header;
    /**
     * 协议体
     */
    private T body;
}
