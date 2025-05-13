package com.example.demo.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employee")
public class EmployeeModel {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private UUID storeId;
    private String cpf;

    @DBRef
    private List<RoleModel> role;
    
    private boolean isActive;
    private boolean status;
    private Date createdAt;
    private Date updatedAt;

    public void addRole(RoleModel role) {
        this.role.add(role);
    }
}
