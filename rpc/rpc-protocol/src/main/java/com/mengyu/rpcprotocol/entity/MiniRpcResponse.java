package com.mengyu.rpcprotocol.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yuzhang
 * @date 2021/1/18 下午8:35
 * TODO
 */
@Data
public class MiniRpcResponse implements Serializable {
    /**
     * 请求结果
     */
    private Object data;
    /**
     * 错误信息
     */
    private String message;
}
