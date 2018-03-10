package com.primeaeterna.netty_practice_one.server;

import com.primeaeterna.netty_practice_one.model.LoopBackTimeStamp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception
    {
        LoopBackTimeStamp ts = (LoopBackTimeStamp) msg;
        ts.receiveTimeStamp = System.nanoTime();
        System.out.println("loop delay in ms: " + 1.0 * ts.timeLapseInNanoSecond() / 1000000L);
    }

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, final Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            IdleStateEvent event = (IdleStateEvent) evt;
            if ( event.state() == IdleState.ALL_IDLE) // idle for no read or write
            {
                ctx.writeAndFlush(new LoopBackTimeStamp());
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception
    {
        cause.printStackTrace();
        ctx.close();
    }
}
