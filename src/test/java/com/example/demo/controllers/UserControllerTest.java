package com.example.demo.controllers;

import com.example.demo.configuration.JwtTokenProvider;
import com.example.demo.dto.LoginDTO;
import com.example.demo.model.AdminModel;
import com.example.demo.model.StoreManagerModel;
import com.example.demo.model.StoreOwnerModel;
import com.example.demo.service.AdminService;
import com.example.demo.service.StoreManagerService;
import com.example.demo.service.StoreOwnerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class UserControllerTest {

    @Mock
    private StoreOwnerService storeOwnerService;

    @Mock
    private StoreManagerService storeManagerService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AdminService adminService;


    @InjectMocks
    private AuthController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostStoreOwner_Success() {
        StoreOwnerModel owner = new StoreOwnerModel();
        owner.setName("Alice");
        owner.setEmail("alice@example.com");
        owner.setPassword("password");

        UUID mockId = UUID.randomUUID();
        when(storeOwnerService.create(owner)).thenReturn(mockId);

        ResponseEntity<?> response = userController.postStoreOwner(owner);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("id"));
        verify(storeOwnerService, times(1)).create(owner);
    }

    @Test
    void testPostStoreManager_Success() {
        StoreManagerModel manager = new StoreManagerModel();
        manager.setName("Bob");
        manager.setEmail("bob@example.com");
        manager.setPassword("password");
        manager.setCpf("12345678901");

        UUID mockId = UUID.randomUUID();
        when(storeManagerService.create(manager)).thenReturn(mockId);

        ResponseEntity<?> response = userController.postManager(manager);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("id"));
        verify(storeManagerService, times(1)).create(manager);
    }

    @Test
    void testPostStoreOwner_Invalid() {
        StoreOwnerModel owner = new StoreOwnerModel();
        when(storeOwnerService.create(owner)).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<?> response = userController.postStoreOwner(owner);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
    }

    @Test
    void testPostStoreManager_InternalError() {
        StoreManagerModel manager = new StoreManagerModel();
        when(storeManagerService.create(manager)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = userController.postManager(manager);

        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
    }
    @Test
    void testPostManagerLogin_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("bob@example.com", "password");
        StoreManagerModel manager = new StoreManagerModel();
        manager.setId(UUID.randomUUID());
        manager.setEmail("bob@example.com");
    
        when(storeManagerService.login(loginDTO)).thenReturn(manager);
        when(jwtTokenProvider.generateStoreManagerToken(manager)).thenReturn("mocked-token");
    
        ResponseEntity<?> response = userController.postManagerLogin(loginDTO);
    
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("token"));
    }
    
    @Test
    void testPostOwnerLogin_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("alice@example.com", "password");
        StoreOwnerModel owner = new StoreOwnerModel();
        owner.setId(UUID.randomUUID());
        owner.setEmail("alice@example.com");
    
        when(storeOwnerService.login(loginDTO)).thenReturn(owner);
        when(jwtTokenProvider.generateStoreOwnerToken(owner)).thenReturn("mocked-token");
    
        ResponseEntity<?> response = userController.postOwnerLogin(loginDTO);
    
        assertEquals(200, response.getStatusCode().value());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("token"));
    }
    
    @Test
    void testPostAdminLogin_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("admin@example.com", "password");
        AdminModel admin = new AdminModel();
        admin.setId(UUID.randomUUID());
        admin.setEmail("admin@example.com");
    
        when(adminService.login(loginDTO)).thenReturn(admin);
        when(jwtTokenProvider.generateAdminToken(admin)).thenReturn("mocked-token");
    
        ResponseEntity<?> response = userController.postAdminLogin(loginDTO);
    
        assertEquals(200, response.getStatusCode().value());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("token"));
    }
    
    @Test
    void testPostManagerLogin_BadRequest() throws Exception {
        LoginDTO loginDTO = new LoginDTO("bob@example.com", "wrongpassword");
    
        when(storeManagerService.login(loginDTO)).thenThrow(new IllegalArgumentException("Invalid credentials"));
    
        ResponseEntity<?> response = userController.postManagerLogin(loginDTO);
    
        assertEquals(400, response.getStatusCode().value());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
    }
    
    @Test
    void testPostOwnerLogin_InternalServerError() {
        LoginDTO loginDTO = new LoginDTO("alice@example.com", "password");
    
        when(storeOwnerService.login(loginDTO)).thenThrow(new RuntimeException());
    
        ResponseEntity<?> response = userController.postOwnerLogin(loginDTO);
    
        assertEquals(500, response.getStatusCode().value());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
    }
    
}
