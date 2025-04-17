package com.example.demo.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.StoreOwnerModel;
import com.example.demo.service.StoreOwnerService;

@Component
public class StoreOwnerListener {    
    
    @Autowired
    private StoreOwnerService service;

    @RabbitListener(queues = "store-owner.request.queue")
    public StoreOwnerModel handleStoreOwnerRequest(String email){
        return service.findByEmail(email);
    }
}
