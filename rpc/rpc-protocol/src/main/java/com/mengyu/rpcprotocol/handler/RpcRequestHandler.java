package com.mengyu.rpcprotocol.handler;

import com.mengyu.rpcprotocol.entity.MiniRpcProtocol;
import com.mengyu.rpcprotocol.entity.MiniRpcRequest;
import com.mengyu.rpcprotocol.entity.MiniRpcResponse;
import com.mengyu.rpcprotocol.entity.MsgHeader;
import com.mengyu.rpcprotocol.enumration.MsgStatus;
import com.mengyu.rpcprotocol.enumration.MsgType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.beans.PersistenceDelegate;
import java.util.Map;

/**
 * @author yuzhang
 * @date 2021/1/19 下午5:20
 * 服务提供者的解码器将二进制解码成MiniRpcProtocol<MiniRpcRequest>对象后，
 * 将其传递给RpcRequestHandler进行RPC的请求调用
 */
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<MiniRpcProtocol<MiniRpcRequest>> {
    private final Map<String, Object> rpcServiceMap;

    public RpcRequestHandler(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MiniRpcProtocol<MiniRpcRequest> msg) throws Exception {
        MiniRpcProtocol<MiniRpcResponse> resProtocol = new MiniRpcProtocol<>();
        MiniRpcResponse response = new MiniRpcResponse();
        MsgHeader header = msg.getHeader();
        header.setMsgType(MsgType.RESPONSE.getValue());
        try {
            // todo 调用RPC服务
            Object result = null;
            response.setData(result);
            header.setStatus(MsgStatus.SUCCESS.getValue());
            resProtocol.setHeader(header);
            resProtocol.setBody(response);
        } catch (Throwable throwable) {
            header.setStatus(MsgStatus.FAIL.getValue());
            response.setMessage(throwable.toString());
            log.error("process request [] error", header.getRequestId(), throwable);
        }
        ctx.writeAndFlush(resProtocol);
    }
}
