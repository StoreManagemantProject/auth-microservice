package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.StoreManagerModel;

public interface StoreManagerRepository extends MongoRepository<StoreManagerModel, UUID>{
    StoreManagerModel findByEmail(String email);
}
