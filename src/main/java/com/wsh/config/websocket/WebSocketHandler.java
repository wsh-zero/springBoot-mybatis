package com.wsh.config.websocket;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;

/**
 * @author jhz
 * @date 18-10-21 下午9:51
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GlobalUserUtil.channels.add(ctx.channel());
        System.out.println("与客户端建立连接，通道开启！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        GlobalUserUtil.channels.remove(ctx);
        System.out.println("与客户端断开连接，通道关闭！");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {
//        System.out.println("客户端收到服务器数据:" + textWebSocketFrame.text());
//        Scanner s = new Scanner(System.in);
//        while(true) {
//            String line = s.nextLine();
//            if(line.equals("exit")) {
//                channelHandlerContext.channel().close();
//                break;
//            }
//            String resp= "(" +channelHandlerContext.channel().remoteAddress() + ") ：" + line;
//            channelHandlerContext.writeAndFlush(new TextWebSocketFrame(resp));
//        }
//        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("服务器推送：" + textWebSocketFrame.text()));

//        if(object instanceof HttpRequest){
//            doHandlerHttpRequest(ctx,(HttpRequest) object);
//        }else if(object instanceof WebSocketFrame){
//            doHandlerWebSocketFrame(ctx,(WebSocketFrame) object);
//        }
        doHandlerWebSocketFrame(ctx,(WebSocketFrame) object);
    }

    /**
     * websocket消息处理
     * @param ctx
     * @param msg
     */
    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //判断msg 是哪一种类型  分别做出不同的反应
        if(msg instanceof CloseWebSocketFrame){
            System.out.println("【关闭】");
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
            return ;
        }
        if(msg instanceof PingWebSocketFrame){
            System.out.println("【ping】");
            PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(pong);
            return ;
        }
        if(msg instanceof PongWebSocketFrame){
            System.out.println("【pong】");
            PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(ping);
            return ;
        }
        if(!(msg instanceof TextWebSocketFrame)){
            System.out.println("【不支持二进制】");
            throw new UnsupportedOperationException("不支持二进制");
        }
        //可以对消息进行处理
        //群发
//        for (Channel channel : GlobalUserUtil.channels) {
//            channel.writeAndFlush(new TextWebSocketFrame(((TextWebSocketFrame) msg).text()));
//        }
        ctx.writeAndFlush(new TextWebSocketFrame(((TextWebSocketFrame) msg).text()));

    }

//
//    /**
//     * wetsocket第一次连接握手
//     * @param ctx
//     * @param msg
//     */
//    private void doHandlerHttpRequest(ChannelHandlerContext ctx, HttpRequest msg) {
//        // http 解码失败
//        if(!msg.getDecoderResult().isSuccess() || (!"websocket".equals(msg.headers().get("Upgrade")))){
//            sendHttpResponse(ctx, (FullHttpRequest) msg,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//        }
//        //可以获取msg的uri来判断
//        String uri = msg.getUri();
//        if(!uri.substring(1).equals("websocket")){
//            ctx.close();
//        }
//        ctx.attr(AttributeKey.valueOf("type")).set(uri);
//        //可以通过url获取其他参数
//        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
//                "ws://"+msg.headers().get("Host")+"/"+"websocket"+"",null,false
//        );
//        handshaker = factory.newHandshaker(msg);
//        if(handshaker == null){
//            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
//        }
//        //进行连接
//        handshaker.handshake(ctx.channel(), (FullHttpRequest) msg);
//        //可以做其他处理
//    }
}
