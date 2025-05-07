package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "store-owner")
public class StoreOwnerModel {
    @Id
    private UUID id;

    private String name;

    private String email;

    private String password;

    private Boolean active;

    private Boolean status;

    @DBRef
    private List<RoleModel> role = new ArrayList<>();
    
    public void addRole(RoleModel role) {
        this.role.add(role);
    }
}
