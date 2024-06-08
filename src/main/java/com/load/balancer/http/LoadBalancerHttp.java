package com.load.balancer.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class LoadBalancerHttp {

   private final int port;

   public LoadBalancerHttp(int port) {
      this.port = port;
   }

   public void start() throws InterruptedException {
      EventLoopGroup bossGroup = new NioEventLoopGroup();
      EventLoopGroup workerGroup = new NioEventLoopGroup();
      try {
         ServerBootstrap bootstrap = new ServerBootstrap();
         bootstrap.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                       ch.pipeline().addLast(new HttpRequestDecoder());
                       ch.pipeline().addLast(new HttpResponseEncoder());
                       ch.pipeline().addLast(new HTTPHandler("localhost"/*local host or remote host*/, 8080));
                    }
                 });

         bootstrap.bind(port).sync().channel().closeFuture().sync();
      } finally {
         bossGroup.shutdownGracefully();
         workerGroup.shutdownGracefully();
      }
   }

   public static void main(String[] args) throws InterruptedException {
      new LoadBalancerHttp(8080).start();
   }
}
