package com.example.demo.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue adminRequestQueue() {
        return new Queue("admin.request.queue", false);
    }

    @Bean
    public Queue storeRequestQueue() {
        return new Queue("store.request.queue", false);
    }

    @Bean
    public Queue managerRequestQueue() {
        return new Queue("manager.request.queue", false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
