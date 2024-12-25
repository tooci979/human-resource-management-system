package com.yinjie.service.impl;//package com.yinjie.service.impl;

import com.yinjie.service.MessageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceRabbitmqDirectImpl implements MessageService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMessage(Integer id) {
        System.out.println("待发送邮件已纳入处理队列（rabbitmq direct），id：" + id);
//                                        绑定交换机和队列
        amqpTemplate.convertAndSend("directExchange", "direct", id); //像消息队列里面传数据 传id
    }

    @Override
    public String doMessage() {
        return null;
    }
}