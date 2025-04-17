package com.example.demo.service;

import com.example.demo.model.RoleModel;
import com.example.demo.model.StoreManagerModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StoreManagerRepository;

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
class StoreManagerServiceTest {

    @Mock
    private StoreManagerRepository repository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private StoreManagerService service;

    private StoreManagerModel validManager;

    @BeforeEach
    void setup() {
        validManager = new StoreManagerModel();
        validManager.setName("John Doe");
        validManager.setEmail("john@example.com");
        validManager.setPassword("secure123");
        validManager.setCpf("12345678909"); // um CPF válido conforme seu validador
    }

    @Test
    void testCreate_ValidManager_ReturnsId() {
        RoleModel managerRole = new RoleModel();
        managerRole.setName("MANAGER");

        when(roleRepository.findByName("MANAGER")).thenReturn(managerRole);

        UUID generatedId = UUID.randomUUID();
        validManager.setId(generatedId);

        when(repository.save(any(StoreManagerModel.class))).thenReturn(validManager);

        UUID resultId = service.create(validManager);

        assertEquals(generatedId, resultId);
        verify(roleRepository, times(1)).findByName("MANAGER");
        verify(repository, times(1)).save(any(StoreManagerModel.class));
    }

    @Test
    void testCreate_InvalidEmail_ThrowsException() {
        validManager.setEmail("invalid-email");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.create(validManager)
        );

        assertEquals("Store Admin email is null or blank", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void testCreate_InvalidCpf_ThrowsException() {
        validManager.setCpf("12345678900"); // inválido

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.create(validManager)
        );

        assertEquals("Invalid Store Manager", exception.getMessage());
        verify(repository, never()).save(any());
    }
}
