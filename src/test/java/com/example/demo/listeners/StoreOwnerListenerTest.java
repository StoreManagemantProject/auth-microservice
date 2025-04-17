package com.example.demo.listeners;

import com.example.demo.model.StoreOwnerModel;
import com.example.demo.service.StoreOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StoreOwnerListenerTest {

    @Mock
    private StoreOwnerService storeOwnerService;

    @InjectMocks
    private StoreOwnerListener storeOwnerListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleStoreOwnerRequest_ReturnsStoreOwnerModel() {
        // Arrange
        String email = "owner@example.com";
        StoreOwnerModel mockOwner = new StoreOwnerModel();
        mockOwner.setEmail(email);
        mockOwner.setName("Store Owner Test");

        when(storeOwnerService.findByEmail(email)).thenReturn(mockOwner);

        // Act
        StoreOwnerModel result = storeOwnerListener.handleStoreOwnerRequest(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals("Store Owner Test", result.getName());
        verify(storeOwnerService, times(1)).findByEmail(email);
    }
}
