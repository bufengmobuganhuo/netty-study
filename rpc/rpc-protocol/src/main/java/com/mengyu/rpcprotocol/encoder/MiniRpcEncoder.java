package com.mengyu.rpcprotocol.encoder;

import com.mengyu.rpcprotocol.entity.MiniRpcProtocol;
import com.mengyu.rpcprotocol.entity.MsgHeader;
import com.mengyu.rpcprotocol.serializer.RpcSerialization;
import com.mengyu.rpcprotocol.serializer.factory.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yuzhang
 * @date 2021/1/19 下午4:28
 * 协议编码器
 */
public class MiniRpcEncoder extends MessageToByteEncoder<MiniRpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MiniRpcProtocol<Object> msg, ByteBuf byteBuf) throws Exception {
        MsgHeader header = msg.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getSerialization());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerializer(header.getSerialization());
        byte[] data = rpcSerialization.serialize(msg.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
