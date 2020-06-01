package com.cxc.cblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by cxc Cotter on 2020/5/31.
 */
@Configuration
public class RabbitMqConfig {
    public final static String ES_QUEUE = "es_queue";
    public final static String ES_EXCHANGE = "es_exchange";
    public final static String ES_BIND_KEY = "es_bind_key";

    @Bean
    public Queue exQueue() {
        return new Queue(ES_QUEUE);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(ES_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(exQueue()).to(exchange()).with(ES_BIND_KEY);
    }
}
