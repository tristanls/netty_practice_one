package com.primeaeterna.netty_practice_one.client;

import com.primeaeterna.netty_practice_one.codec.TimeStampDecoder;
import com.primeaeterna.netty_practice_one.codec.TimeStampEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient
{
    public static void main(String[] args)
    {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);

        b.handler(new ChannelInitializer<>()
            {
                @Override
                protected void initChannel(final Channel ch) throws Exception
                {
                    ch.pipeline().addLast(new TimeStampEncoder(), new TimeStampDecoder(), new ClientHandler());
                }
            }
        );

        String serverIp = "127.0.0.1";
        b.connect(serverIp, 10010);
    }
}
