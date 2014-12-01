package com.xgq.demo1;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by xiegq on 14-11-30 下午4:32.
 * Macbook Air 2014.
 * Intellij idea 13.
 */
public class Demo1Client {
    private static Logger logger = Logger.getLogger(Demo1Client.class);
    private static int PORT = 3005;
    private static String HOST = "127.0.0.1";

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector(); // 创建连接
        connector.setConnectTimeoutMillis(30000);//设置超时链接时间 毫秒
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue())));
        connector.setHandler(new Demo1ClientHandler());
        IoSession session = null;
        try {
            ConnectFuture future = connector.connect(new InetSocketAddress(HOST, PORT));// 创建连接
            future.awaitUninterruptibly();// 等待连接创建完成
            session = future.getSession();// 获得session
            while (true) {
                System.out.println("请输入字符串：");
                Scanner scanner = new Scanner(System.in);
                session.write(scanner.next());// 发送消息
            }
        } catch (Exception e) {
            logger.error("客户端链接异常...", e);
        }
        session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
        connector.dispose();
    }
}
