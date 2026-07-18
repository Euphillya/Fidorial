package fr.euphyllia.fidorial.server.network;

import fr.euphyllia.fidorial.server.FidorialServer;
import fr.euphyllia.fidorial.server.network.codec.FrameDecoder;
import fr.euphyllia.fidorial.server.network.codec.FrameEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public final class NettyServer {

    private final FidorialServer server;
    private final int port;
    private final MultiThreadIoEventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());
    private final MultiThreadIoEventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
    private Channel channel;

    public NettyServer(FidorialServer server, int port) {
        this.server = server;
        this.port = port;
    }

    public void bind() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast("frame-decoder", new FrameDecoder())
                                .addLast("frame-encoder", new FrameEncoder())

                                .addLast("handler", new ClientConnection(server));
                    }
                });
        this.channel = bootstrap.bind(port).sync().channel();
    }

    public void shutdown() {
        if (channel != null) channel.close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
