package com.wsh.config.websocket;


import com.google.gson.Gson;
import com.wsh.util.ResultUtil;
import com.wsh.util.Utils;
import com.wsh.zero.vo.WebSocketVO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jhz
 * @date 18-10-21 下午9:51
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GlobalUserUtil.channels.add(ctx.channel());
        log.info("与客户端建立连接，通道开启！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        GlobalUserUtil.channels.remove(ctx.channel());
        log.info("与客户端断开连接，通道关闭！");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String text = textWebSocketFrame.text();
        Gson gson = Utils.getGson();
        ResultUtil resultUtil;
        try {
            WebSocketVO webSocketVO = gson.fromJson(text, WebSocketVO.class);
            resultUtil = ResultUtil.success("成功", webSocketVO);

        } catch (Exception e) {
            resultUtil = ResultUtil.failed(1, "JSON格式化失败！！", text);
        }

        ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson(resultUtil)));

    }

    //关闭连接
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.warn("长时间未使用，关闭通道！！");
                GlobalUserUtil.channels.remove(ctx.channel());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
