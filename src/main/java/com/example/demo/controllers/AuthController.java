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

import com.example.demo.configuration.JwtTokenProvider;
import com.example.demo.dto.LoginDTO;
import com.example.demo.model.AdminModel;
import com.example.demo.model.StoreManagerModel;
import com.example.demo.model.StoreOwnerModel;
import com.example.demo.service.AdminService;
import com.example.demo.service.StoreManagerService;
import com.example.demo.service.StoreOwnerService;

@RestController
@RequestMapping("/auth")
public class AuthController{
    
    @Autowired
    private StoreOwnerService storeOwnerService;
    
    @Autowired
    private StoreManagerService storeManagerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/store-owner/create")
    public ResponseEntity<?> postStoreOwner(@RequestBody StoreOwnerModel storeOwner){
        try{
            UUID id = storeOwnerService.create(storeOwner);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Store Owner created with successfully", "id", id));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }
    @PostMapping("/store-manager/create")
    public ResponseEntity<?> postManager(@RequestBody StoreManagerModel storeOwner){
        try{
            UUID id = storeManagerService.create(storeOwner);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Store Owner created with successfully", "id", id));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }
    @PostMapping("/store-manager/login")
    public ResponseEntity<?> postManagerLogin(@RequestBody LoginDTO storeOwner){
        try{
            StoreManagerModel storeManager = storeManagerService.login(storeOwner);
            String token = jwtTokenProvider.generateStoreManagerToken(storeManager);
            return ResponseEntity.ok(Map.of("token", token));

        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }

    @PostMapping("/store-owner/login")
    public ResponseEntity<?> postOwnerLogin(@RequestBody LoginDTO login){
        try{
            StoreOwnerModel storeOwner = storeOwnerService.login(login);
            String token = jwtTokenProvider.generateStoreOwnerToken(storeOwner);
            return ResponseEntity.ok(Map.of("token", token));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }
    @PostMapping("admin/login")
    public ResponseEntity<?> postAdminLogin(@RequestBody LoginDTO login){
        try{
            AdminModel admin = adminService.login(login);
            String token = jwtTokenProvider.generateAdminToken(admin);
            return ResponseEntity.ok(Map.of("token", token));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }
}