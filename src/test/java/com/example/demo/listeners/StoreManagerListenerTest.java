package com.example.demo.listeners;

import com.example.demo.model.StoreManagerModel;
import com.example.demo.service.StoreManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StoreManagerListenerTest {

    @Mock
    private StoreManagerService storeManagerService;

    @InjectMocks
    private StoreManagerListener storeManagerListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleStoreManagerRequest_ReturnsStoreManagerModel() {
        // Arrange
        String email = "manager@example.com";
        StoreManagerModel mockManager = new StoreManagerModel();
        mockManager.setEmail(email);
        mockManager.setName("Manager Test");

        when(storeManagerService.findByEmail(email)).thenReturn(mockManager);

        // Act
        StoreManagerModel result = storeManagerListener.handleStoreManagerRequest(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals("Manager Test", result.getName());
        verify(storeManagerService, times(1)).findByEmail(email);
    }
}
