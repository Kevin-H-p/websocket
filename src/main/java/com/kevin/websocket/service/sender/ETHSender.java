package com.kevin.websocket.service.sender;

import com.alibaba.fastjson.JSONObject;
import com.kevin.websocket.service.SendService;
import org.springframework.stereotype.Service;


@Service("eth")
public class ETHSender implements SendService {
    @Override
    public String send(JSONObject params) {
        return params.getString("value");
    }
}
