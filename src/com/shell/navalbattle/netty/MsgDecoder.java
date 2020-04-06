/**
 * @author YC 04/06/2020
 */

package com.shell.navalbattle.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) return;

        byteBuf.markReaderIndex();

        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        int len = byteBuf.readInt();

        if (byteBuf.readableBytes() < len) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        Msg msg = (Msg) Class.forName("com.shell.navalbattle.netty." + msgType.toString() + "Msg")
                .getDeclaredConstructor()
                .newInstance();
        msg.parse(bytes);
        list.add(msg);
    }
}
