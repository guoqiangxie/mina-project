package com.xgq.demo1;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by xiegq on 14-11-30 下午4:32.
 * Macbook Air 2014.
 * Intellij idea 13.
 */
public class Demo1Server {
    private static Logger logger = Logger.getLogger(Demo1Server.class);
    private static int PORT = 3005;

    public static void main(String[] args) {
        IoAcceptor acceptor = null; // 创建连接
        try {
            // 创建一个非阻塞的server端的Socket
            acceptor = new NioSocketAcceptor();

            // 设置过滤器（使用Mina提供的文本换行符编解码器）
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                            LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue()))); //添加消息过滤器 这个是文本消息过滤器，还可以有对象消息
            acceptor.getSessionConfig().setReadBufferSize(2048); // 设置读取数据的缓冲区大小
            // 读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

            // 绑定逻辑处理器
            acceptor.setHandler(new Demo1ServerHandler()); // 添加业务处理
            // 绑定端口
            acceptor.bind(new InetSocketAddress(PORT));
            logger.info("服务端启动成功...     端口号为：" + PORT);
        } catch (Exception e) {
            logger.error("服务端启动异常....", e);
            e.printStackTrace();
        }
    }
}
