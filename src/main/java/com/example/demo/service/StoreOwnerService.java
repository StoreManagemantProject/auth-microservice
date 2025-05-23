package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.StoreOwnerModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreOwnerRepository;
import com.example.demo.util.Authorization;

@Service
public class StoreOwnerService {
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    public StoreOwnerModel findByEmail(String email){
        return storeOwnerRepository.findByEmail(email);
    }

    public UUID create(StoreOwnerModel owner) throws IllegalArgumentException{
        validateStoreAdmin(owner);
        owner.setPassword(Authorization.hashPassword(owner.getPassword()));
        owner.addRole(roleRepository.findByName("STORE_OWNER"));
        return storeOwnerRepository.save(owner).getId();
    }
    
    private void validateStoreAdmin(StoreOwnerModel storeAdminEntity) throws IllegalArgumentException{
        if(storeAdminEntity == null){
            throw new IllegalArgumentException("Store Owner is null");
        }
        if(storeAdminEntity.getName() == null || storeAdminEntity.getName().isBlank()){
            throw new IllegalArgumentException("Store Admin name is null or blank");
        }
        String REGEX_EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(storeAdminEntity.getEmail() == null || storeAdminEntity.getEmail().isBlank() || !storeAdminEntity.getEmail().matches(REGEX_EMAIL_PATTERN) ){
            throw new IllegalArgumentException("Store Admin email is null or blank");
        }
        if(storeAdminEntity.getPassword() == null || storeAdminEntity.getPassword().isBlank()){
            throw new IllegalArgumentException("Store Admin password is null or blank");
        }

    }
    public StoreOwnerModel login(LoginDTO storeOwner) throws IllegalArgumentException{
        StoreOwnerModel storeOwnerEntity = storeOwnerRepository.findByEmail(storeOwner.getEmail());
        if(storeOwnerEntity == null){
            throw new IllegalArgumentException("Store Owner not found");
        }
        if(!Authorization.checkPassword(storeOwner.getPassword(), storeOwnerEntity.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }
        return storeOwnerEntity;
    }
}
