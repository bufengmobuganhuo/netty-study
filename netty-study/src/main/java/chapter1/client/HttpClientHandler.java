package chapter1.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

import javax.xml.ws.spi.http.HttpContext;

/**
 * @author yuzhang
 * @date 2020/10/29 4:09 下午
 * TODO
 */
public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpContent){
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(CharsetUtil.UTF_8));
            buf.release();
        }
    }
}
