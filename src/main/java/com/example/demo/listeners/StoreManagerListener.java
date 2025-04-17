package com.example.demo.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.StoreManagerModel;
import com.example.demo.service.StoreManagerService;

@Component
public class StoreManagerListener {
    @Autowired
    private StoreManagerService service;


    @RabbitListener(queues = "manager.request.queue")
    public StoreManagerModel handleStoreManagerRequest(String email){
        return service.findByEmail(email);
    }
}
