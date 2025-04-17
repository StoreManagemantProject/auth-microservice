package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdminModel;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired 
    private AdminRepository adminRepository;

    public AdminModel findByEmail(String email) {
        return adminRepository.findByEmail(email);

    }
}
