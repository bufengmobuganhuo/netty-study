package com.mengyu.rpcprotocol.decoder;

import com.mengyu.rpcprotocol.constant.ProtocolConstants;
import com.mengyu.rpcprotocol.entity.MiniRpcProtocol;
import com.mengyu.rpcprotocol.entity.MiniRpcRequest;
import com.mengyu.rpcprotocol.entity.MiniRpcResponse;
import com.mengyu.rpcprotocol.entity.MsgHeader;
import com.mengyu.rpcprotocol.enumration.MsgType;
import com.mengyu.rpcprotocol.serializer.RpcSerialization;
import com.mengyu.rpcprotocol.serializer.factory.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author yuzhang
 * @date 2021/1/19 下午4:32
 * 协议解码器
 */
public class MiniRpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 只有当ByteBuf中内容大于协议头Header的固定的18字节时，才开始读取数据
        if (in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            return;
        }
        // 标记读指针位置，
        in.markReaderIndex();
        // 按顺序读取header的字段
        short magic = in.readShort();
        if (magic != ProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }
        byte version = in.readByte();
        byte serializeType = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        long requestId = in.readLong();
        int dataLength = in.readInt();
        // 当ByteBuf中可读字节的长度小于协议体Body的长度时，重置读指针位置
        // 说明现在ByteBuf中可读字节还不够一个完整的数据包
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        MsgType msgTypeEnum = MsgType.findByType(msgType);
        MsgHeader header = new MsgHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serializeType);
        header.setMsgType(msgType);
        header.setStatus(status);
        header.setRequestId(requestId);
        header.setMsgLen(dataLength);
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerializer(serializeType);
        switch (msgTypeEnum) {
            // 在RPC请求调用的情况下，服务提供者需要将协议体内容反序列化成MiniRpcRequest对象
            case REQUEST:
                MiniRpcRequest request = rpcSerialization.deserialize(data, MiniRpcRequest.class);
                if (request != null) {
                    MiniRpcProtocol<MiniRpcRequest> protocol = new MiniRpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(request);
                    out.add(protocol);
                }
                break;
            // 在RPC结果响应的情况下，服务消费者需要将协议内容反序列化成MiniRpcResponse对象
            case RESPONSE:
                MiniRpcResponse response = rpcSerialization.deserialize(data, MiniRpcResponse.class);
                if (response != null) {
                    MiniRpcProtocol<MiniRpcResponse> protocol = new MiniRpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(response);
                    out.add(protocol);
                }
                break;
            case HEARTBEAT:
                // todo
                break;
            default:
                throw new IllegalArgumentException();

        }
    }
}
