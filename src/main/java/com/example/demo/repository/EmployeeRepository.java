package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.EmployeeModel;

public interface EmployeeRepository extends MongoRepository<EmployeeModel, UUID> {
    EmployeeModel findByEmail(String email);
}
