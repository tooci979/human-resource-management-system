package com.yinjie.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
//        监听器 监听哪个队列消费；direct_queue队列名
    @RabbitListener(queues = "direct_queue_email")
    public void receive(String id) {
//            当队列中有消息 就会立马消费
        System.out.println("已完成邮件发送业务(rabbitmq direct)，id：" + id);
    }
}
