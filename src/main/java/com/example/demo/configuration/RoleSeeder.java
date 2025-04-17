package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.RoleModel;
import com.example.demo.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Component
public class RoleSeeder {
    @Autowired
    private RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void seedRoles() {
        seedRole("ADMIN", "Administrator role");
        seedRole("STORE_OWNER", "Store Owner role");
        seedRole("MANAGER", "Store manager role");
        seedRole("USER", "Basic user role");
    }

    private void seedRole(String name, String description) {
        if (!roleRepository.existsByName(name)) {
            RoleModel role = new RoleModel();
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
        }
    }
}
