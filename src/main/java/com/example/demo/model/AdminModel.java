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
@Document(collection = "admin")
public class AdminModel {
    @Id
    private UUID id;
    private String email;
    private String password;

    @DBRef
    private List<RoleModel> role = new ArrayList<>();
}
