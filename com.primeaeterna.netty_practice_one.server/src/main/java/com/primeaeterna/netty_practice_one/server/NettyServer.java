package com.primeaeterna.netty_practice_one.server;

import com.primeaeterna.netty_practice_one.codec.TimeStampDecoder;
import com.primeaeterna.netty_practice_one.codec.TimeStampEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class NettyServer
{
    public static void main(String[] args) throws InterruptedException
    {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);

        /**
         * Define a separate thread pool to execute handlers with slow business
         * logic. e.g. database connection
         */
        final EventExecutorGroup group = new DefaultEventExecutorGroup(1500);

        bootstrap.childHandler(new ChannelInitializer<>()
            {
                @Override
                protected void initChannel(final Channel ch) throws Exception
                {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, 5));
                    pipeline.addLast(new TimeStampEncoder());
                    pipeline.addLast(new TimeStampDecoder());

                    /**
                     * Run handler with slow business logic in separate thread
                     * from I/O thread
                     */
                    pipeline.addLast(group, "serverHandler", new ServerHandler());
                }
            }
        );
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.bind(10010).sync();
    }
}
