package com.example.demo.model;

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
@Document(collection = "store-manager")
public class StoreManagerModel {
    @Id
    private UUID id;

    private String email;
    
    private String password;

    private Boolean active;

    private Boolean status;

    private String name;

    private String cpf;
    
    @DBRef
    private List<RoleModel> roles;

    public void addRole(RoleModel role){
        roles.add(role);
    }
}
