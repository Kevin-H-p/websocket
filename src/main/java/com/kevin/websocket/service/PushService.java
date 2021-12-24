package com.kevin.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/pushData")
@Component
public class PushService implements ApplicationContextAware {

    Logger log = LoggerFactory.getLogger(PushService.class);

    private static SendService sendService;
    private static Map<String,SendService> services;

    public static final String TYPE = "type";


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        this.sendMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            JSONObject query = JSON.parseObject(message);
            sendService = services.get(query.getString(TYPE));
            String send = sendService.send(query);
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(send);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e);
        }
    }

    /**
     *  注入bean
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        services = applicationContext.getBeansOfType(SendService.class);
    }
}
