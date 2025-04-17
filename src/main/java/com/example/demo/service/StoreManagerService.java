package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.StoreManagerModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreManagerRepository;
import com.example.demo.util.CPFValidator;

@Service
public class StoreManagerService {
    
    @Autowired
    private StoreManagerRepository repository;

    @Autowired 
    private RoleRepository role;
    
    public StoreManagerModel findByEmail(String email){        
        return repository.findByEmail(email);
    }

    public UUID create(StoreManagerModel manager){
        validateStoreManager(manager);
        manager.addRole(role.findByName("MANAGER"));
        return repository.save(manager).getId();
    }

    private void validateStoreManager(StoreManagerModel storeManager){
        if(storeManager == null){
            throw new IllegalArgumentException("Invalid Store Manager");
        }
        if(storeManager.getName().isBlank() || storeManager.getEmail().isBlank()){
            throw new IllegalArgumentException("Invalid Store Manager");
        }
        if(storeManager.getPassword().isBlank() || storeManager.getPassword().length() < 6){
            throw new IllegalArgumentException("Invalid Store Manager");
        }
        String REGEX_EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(storeManager.getEmail().isBlank() || !storeManager.getEmail().matches(REGEX_EMAIL_PATTERN) ){
            throw new IllegalArgumentException("Store Admin email is null or blank");
        }
        if(!CPFValidator.isValidCPF(storeManager.getCpf())){
            throw new IllegalArgumentException("Invalid Store Manager");
        }

    }
}
