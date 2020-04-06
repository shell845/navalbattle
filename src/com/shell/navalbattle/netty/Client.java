/**
 * @author YC 04/06/2020
 */

package com.shell.navalbattle.netty;


import com.shell.navalbattle.NavalFrame;
import com.shell.navalbattle.utils.PropertyMgr;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Properties;

public class Client {
    public static final Client client_instance = new Client();

    private Channel channel = null;

    private Client() {}

    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap b = new Bootstrap();
        try {
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler((new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            channel = socketChannel;
                            socketChannel.pipeline()
                                    .addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder())
                                    .addLast(new MyHandler());
                        }
                    }));

            ChannelFuture f = b.connect("localhost", Integer.parseInt(PropertyMgr.getConfig("port"))).sync();
            System.out.println("connected to server");

            // wait for close signal
            f.channel().closeFuture().sync();
            System.out.println("client closed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void closeConnection() {
        channel.closeFuture();
    }

    public void send(Msg msg) {
        channel.writeAndFlush(msg);
    }

    static class MyHandler extends SimpleChannelInboundHandler<Msg> {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(new PlayerJoinMsg(NavalFrame.MAIN_FRAME.getGameModel().getMySubmarine()));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            System.out.println(msg);
            msg.handle();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connect();
    }
}
