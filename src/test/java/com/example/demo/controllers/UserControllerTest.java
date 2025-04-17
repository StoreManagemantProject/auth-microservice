package com.example.demo.controllers;

import com.example.demo.model.StoreManagerModel;
import com.example.demo.model.StoreOwnerModel;
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

    @InjectMocks
    private UserController userController;

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
}
