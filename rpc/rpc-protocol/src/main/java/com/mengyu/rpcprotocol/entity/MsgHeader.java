package com.mengyu.rpcprotocol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:32
 * TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgHeader {
    /**
     * 魔数
     */
    private short magic;
    /**
     * 协议版本号
     */
    private byte version;
    /**
     * 序列化算法
     */
    private byte serialization;
    /**
     * 报文类型
     */
    private byte msgType;
    /**
     * 状态
     */
    private byte status;
    /**
     * 消息ID
     */
    private long requestId;
    /**
     * 数据长度
     */
    private int msgLen;
}
