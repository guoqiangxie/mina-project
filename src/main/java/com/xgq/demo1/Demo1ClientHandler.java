package com.xgq.demo1;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by xiegq on 14-11-30 下午9:13.
 * Macbook Air 2014.
 * Intellij idea 13.
 */
public class Demo1ClientHandler extends IoHandlerAdapter {
    public static Logger logger = Logger.getLogger(Demo1ClientHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String msg = message.toString();
        logger.info("客户端接收到的信息为：" + msg);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("客户端与服务端创建连接...");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("客户端与服务端连接打开...");
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        logger.info("客户端发送信息成功...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("客户端关闭session.");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.info("客户端进入空闲状态...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.error("客户端发送异常...", cause);
    }
}
