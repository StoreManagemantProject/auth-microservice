package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.AdminModel;

public interface AdminRepository extends MongoRepository<AdminModel, UUID>{
    AdminModel findByEmail(String email);
}
