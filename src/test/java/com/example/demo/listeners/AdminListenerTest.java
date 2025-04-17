package com.example.demo.listeners;

import com.example.demo.model.AdminModel;
import com.example.demo.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminListenerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminListener adminListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleAdminRequest_ReturnsAdminModel() {
        // Arrange
        String email = "admin@example.com";
        AdminModel mockAdmin = new AdminModel();
        mockAdmin.setEmail(email);

        when(adminService.findByEmail(email)).thenReturn(mockAdmin);

        // Act
        AdminModel result = adminListener.handleAdminRequest(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(adminService, times(1)).findByEmail(email);
    }
}
