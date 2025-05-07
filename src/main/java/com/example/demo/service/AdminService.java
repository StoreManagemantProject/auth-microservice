package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.AdminModel;
import com.example.demo.repository.AdminRepository;
import com.example.demo.util.Authorization;

@Service
public class AdminService {

    @Autowired 
    private AdminRepository adminRepository;

    public AdminModel findByEmail(String email) {
        return adminRepository.findByEmail(email);

    }
    public AdminModel login(LoginDTO loginDTO) throws IllegalArgumentException {
        AdminModel admin = adminRepository.findByEmail(loginDTO.getEmail());
        if (admin == null) {
            throw new IllegalArgumentException("Invalid Admin");
        }
        if (!Authorization.checkPassword(loginDTO.getPassword(), admin.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return admin;
    }
}
