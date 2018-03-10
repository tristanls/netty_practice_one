package com.primeaeterna.netty_practice_one.client;

import com.primeaeterna.netty_practice_one.model.LoopBackTimeStamp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception
    {
        LoopBackTimeStamp ts = (LoopBackTimeStamp) msg;
        ctx.writeAndFlush(ts); // received message is sent back directly
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
