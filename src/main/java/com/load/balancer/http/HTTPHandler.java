package com.load.balancer.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

public class HTTPHandler extends ChannelInboundHandlerAdapter {

    private final String remoteHost;
    private final int remotePort;

    public HTTPHandler(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            // Logic to balance the request to a different backend server
            // This example forwards the request to a fixed remote host and port
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.content().writeBytes("Hello from load balancer".getBytes());
            ctx.writeAndFlush(response);
        } else {
            super.channelRead(ctx, msg);
        }
    }
}

