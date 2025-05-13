package com.example.demo.controllers;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.EmployeeModel;
import com.example.demo.model.StoreManagerModel;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.StoreManagerService;

@RestController
@RequestMapping("/api/private/auth")
public class PrivateAuth {
    
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private StoreManagerService storeManagerService;

    @PostMapping("/employee/create")
    public ResponseEntity<?> postEmployee(@RequestBody EmployeeModel employee){
        try{
            UUID id = employeeService.create(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Employee created with successfully", "id", id));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }

    @PostMapping("/manager/create")
    public ResponseEntity<?> postManager(@RequestBody StoreManagerModel storeManager){
        try{
            UUID id = storeManagerService.create(storeManager);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Manager created with successfully", "id", id));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }
}
