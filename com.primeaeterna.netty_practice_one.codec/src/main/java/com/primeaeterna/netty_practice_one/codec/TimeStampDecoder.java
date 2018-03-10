package com.primeaeterna.netty_practice_one.codec;

import com.primeaeterna.netty_practice_one.model.LoopBackTimeStamp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeStampDecoder extends ByteToMessageDecoder
{
    @Override
    protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception
    {
        final int messageLength = Long.SIZE/Byte.SIZE * 2;
        if (in.readableBytes() < messageLength)
        {
            return;
        }
        byte[] ba = new byte[messageLength];
        in.readBytes(ba, 0, messageLength); // block until reading 16 bytes from socket
        LoopBackTimeStamp loopBackTimeStamp = new LoopBackTimeStamp();
        loopBackTimeStamp.fromByteArray(ba);
        out.add(loopBackTimeStamp);
    }
}
