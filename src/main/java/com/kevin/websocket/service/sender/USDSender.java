package com.kevin.websocket.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.kevin.websocket.service.SendService;
import org.springframework.stereotype.Service;


@Service("usd")
public class USDSender implements SendService  {
    @Override
    public String send(JSONObject params) {
        return params.getString("value");
    }
}
