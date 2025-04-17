package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.StoreManagerModel;
import com.example.demo.repository.StoreManagerRepository;

@Service
public class StoreManagerService {
    
    @Autowired
    private StoreManagerRepository repository;

    public StoreManagerModel findByEmail(String email){        
        return repository.findByEmail(email);
    }
}
