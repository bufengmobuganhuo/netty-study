package chapter1.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;

/**
 * @author yuzhang
 * @date 2020/10/29 3:13 下午
 * 服务端启动类
 */
public class HttpServer {
    public void start(int port){
        /**
         * 第一步：配置线程模型
         *  1⃣️ 如果是单线程模型，只需要启动一个EventLoopGroup,并配置使用一个线程即可
         *      EventLoopGroup group = new NioEventLoopGroup(1);
         *      ServerBootstrap bootstrap = new ServerBootstrap();
         *      bootstrap.group(group);
         *  2⃣️ 如果是多线程模式，也只需要启动一个EventLoopGroup，Netty默认启动2倍CPU核数的线程
         *      EventLoopGroup group = new NioEventLoopGroup();
         *      ServerBootstrap bootstrap = new ServerBootstrap();
         *      bootstrap.group(group);
         *  3⃣️ 如果是主从多线程模式，即为下图形式
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                // 第二步，设置Channel类型，NioServerSocketChannel是最推荐使用的
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                // 向客户端的pipeline添加handler
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 第三步，注册ChannelHandler
                        socketChannel.pipeline()
                                // Http编解码处理器
                                .addLast("codec",new HttpServerCodec())
                                // HttpContent压缩处理器
                                .addLast("compressor",new HttpContentCompressor())
                                // Http消息聚合处理器
                                .addLast("aggregator",new HttpObjectAggregator(65536))
                                // 自定义业务逻辑处理器
                                .addLast("handler",new HttpServerHandler());
                        /**
                         * 当服务端收到请求后，会依次经过：Http编解码处理器，HttpContent压缩处理器，Http消息聚合处理器，自定义逻辑处理器
                         *  然后通过HttpContent压缩处理器，Http编解码处理器写回客户端
                         */
                    }
                })
/*
 * SO_KEEPALIVE	设置为 true 代表启用了 TCP SO_KEEPALIVE 属性，TCP 会主动探测连接状态，即连接保活
 * SO_BACKLOG	已完成三次握手的请求队列最大长度，同一时刻服务端可能会处理多个连接，在高并发海量连接的场景下，该参数应适当调大
 * TCP_NODELAY	设置为 true 标识 TCP 会将网络数据包累积到一定量才会发送，会造成一定的数据延迟。如果对数据传输延迟敏感，那么应该禁用该参数
 * SO_SNDBUF	TCP 数据发送缓冲区大小
 * SO_RCVBUF	TCP数据接收缓冲区大小，TCP数据接收缓冲区大小
 * SO_LINGER	设置延迟关闭的时间，等待缓冲区中的数据发送完成
 * CONNECT_TIMEOUT_MILLIS	建立连接的超时时间
 */
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        try {
            // 第四步，端口绑定，sync()会阻塞，直到整个启动过程完成
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println("Http Server starte, Listening on "+port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HttpServer().start(8088);
    }
}
