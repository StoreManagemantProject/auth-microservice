package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.StoreOwnerModel;

public interface StoreOwnerRepository extends MongoRepository<StoreOwnerModel, UUID>{
    StoreOwnerModel findByEmail(String email);   
}
