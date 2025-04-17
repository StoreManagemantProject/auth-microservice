package com.example.demo.service;

import com.example.demo.model.AdminModel;
import com.example.demo.repository.AdminRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private AdminModel admin;

    @BeforeEach
    void setUp() {
        admin = new AdminModel();
        admin.setId(UUID.randomUUID()); // Supondo que o ID seja Long
        admin.setEmail("admin@example.com");
    }

    @Test
    void testFindByEmail_ReturnsAdmin() {
        // Arrange
        String email = "admin@example.com";
        when(adminRepository.findByEmail(email)).thenReturn(admin);

        // Act
        AdminModel result = adminService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(adminRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_ReturnsNull() {
        // Arrange
        String email = "notfound@example.com";
        when(adminRepository.findByEmail(email)).thenReturn(null);

        // Act
        AdminModel result = adminService.findByEmail(email);

        // Assert
        assertNull(result);
        verify(adminRepository, times(1)).findByEmail(email);
    }
}
