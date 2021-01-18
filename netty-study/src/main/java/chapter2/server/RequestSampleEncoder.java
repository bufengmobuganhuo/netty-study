package chapter2.server;

import chapter2.ResponseSample;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author yuzhang
 * @date 2020/11/19 下午6:36
 * 负责客户端的数据处理，并通过调用ctx.channel().writeAndFlush()向客户端返回ResponseSample对象
 */
public class RequestSampleEncoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String data = ((ByteBuf)msg).toString(CharsetUtil.UTF_8);
        ResponseSample response = new ResponseSample("OK",data,System.currentTimeMillis());
        ctx.channel().writeAndFlush(response);
    }
}
