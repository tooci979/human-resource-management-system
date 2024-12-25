package com.yinjie.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigDirect {
    @Bean
    public Queue directQueue() { //定义的一个队列
        return new Queue("direct_queue_email");
    }

    @Bean
    public Queue directQueue2() {//定义的二个队列
        return new Queue("direct_queue2_email");
    }

    @Bean
    public DirectExchange directExchange() {
        //交换机
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding bindingDirect() { //directExchange交换机绑定队列1，directQueue()队列1
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct");
    }

    @Bean
    public Binding bindingDirect2() {//directExchange交换机绑定队列2
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("direct2");
    }
}