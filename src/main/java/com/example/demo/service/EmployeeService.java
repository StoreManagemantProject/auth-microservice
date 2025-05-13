package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.EmployeeModel;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.util.Authorization;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UUID create(EmployeeModel employee) {
        validateEmployeeData(employee);
        employee.setPassword(Authorization.hashPassword(employee.getPassword()));
        employee.addRole(roleRepository.findByName("EMPLOYEE"));
        EmployeeModel savedEmployee = employeeRepository.save(employee);
        
        return savedEmployee.getId();
    }


    public EmployeeModel login(LoginDTO loginDTO) {
        EmployeeModel employee = employeeRepository.findByEmail(loginDTO.getEmail());
        if (employee != null && Authorization.checkPassword(loginDTO.getPassword(), employee.getPassword())) {
            return employee;
        }
        return null;
    }


    private void validateEmployeeData(EmployeeModel employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (employee.getPhone() == null || employee.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (employee.getCpf() == null || employee.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }
        if (employee.getStoreId() == null) {
            throw new IllegalArgumentException("Store ID cannot be null");
        }
    }
}
