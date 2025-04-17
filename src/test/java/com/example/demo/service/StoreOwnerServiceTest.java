package com.example.demo.service;

import com.example.demo.model.RoleModel;
import com.example.demo.model.StoreOwnerModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreOwnerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreOwnerServiceTest {

    @Mock
    private StoreOwnerRepository storeOwnerRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private StoreOwnerService storeOwnerService;

    private StoreOwnerModel validOwner;

    @BeforeEach
    void setUp() {
        validOwner = new StoreOwnerModel();
        validOwner.setName("Alice");
        validOwner.setEmail("alice@example.com");
        validOwner.setPassword("password123");
    }

    @Test
    void testCreate_ValidOwner_ReturnsId() {
        // Arrange
        RoleModel storeOwnerRole = new RoleModel();
        storeOwnerRole.setName("STORE_OWNER");

        when(roleRepository.findByName("STORE_OWNER")).thenReturn(storeOwnerRole);

        UUID generatedId = UUID.randomUUID();
        validOwner.setId(generatedId);

        when(storeOwnerRepository.save(any(StoreOwnerModel.class))).thenReturn(validOwner);

        // Act
        UUID result = storeOwnerService.create(validOwner);

        // Assert
        assertEquals(generatedId, result);
        verify(roleRepository, times(1)).findByName("STORE_OWNER");
        verify(storeOwnerRepository, times(1)).save(any(StoreOwnerModel.class));
    }

    @Test
    void testCreate_NullOwner_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> storeOwnerService.create(null)
        );

        assertEquals("Store Owner is null", exception.getMessage());
    }

    @Test
    void testCreate_InvalidEmail_ThrowsException() {
        validOwner.setEmail("invalid-email");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> storeOwnerService.create(validOwner)
        );

        assertEquals("Store Admin email is null or blank", exception.getMessage());
    }

    @Test
    void testCreate_BlankPassword_ThrowsException() {
        validOwner.setPassword("  ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> storeOwnerService.create(validOwner)
        );

        assertEquals("Store Admin password is null or blank", exception.getMessage());
    }

    @Test
    void testCreate_BlankName_ThrowsException() {
        validOwner.setName(" ");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> storeOwnerService.create(validOwner)
        );

        assertEquals("Store Admin name is null or blank", exception.getMessage());
    }
}
