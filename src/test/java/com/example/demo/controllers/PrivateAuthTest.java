package com.example.demo.controllers;

import com.example.demo.model.EmployeeModel;
import com.example.demo.model.StoreManagerModel;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.StoreManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public class PrivateAuthTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private StoreManagerService storeManagerService;

    @InjectMocks
    private PrivateAuth privateAuth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um funcionário com sucesso")
    public void testPostEmployeeSuccess() {
        EmployeeModel employee = new EmployeeModel();
        employee.setEmail("employee@example.com");
        employee.setName("Funcionário Teste");

        UUID fakeId = UUID.randomUUID();
        when(employeeService.create(employee)).thenReturn(fakeId);

        ResponseEntity<?> response = privateAuth.postEmployee(employee);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("id"));
        assertEquals(fakeId.toString(), ((Map<?, ?>) response.getBody()).get("id").toString());

        verify(employeeService, times(1)).create(employee);
    }

    @Test
    @DisplayName("Deve criar um gerente com sucesso")
    public void testPostManagerSuccess() {
        StoreManagerModel manager = new StoreManagerModel();
        manager.setEmail("manager@example.com");
        manager.setName("Gerente Teste");

        UUID fakeId = UUID.randomUUID();
        when(storeManagerService.create(manager)).thenReturn(fakeId);

        ResponseEntity<?> response = privateAuth.postManager(manager);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("id"));
        assertEquals(fakeId.toString(), ((Map<?, ?>) response.getBody()).get("id").toString());

        verify(storeManagerService, times(1)).create(manager);
    }

    @Test
    @DisplayName("Deve retornar erro 400 se dados inválidos forem enviados para employee")
    public void testPostEmployeeBadRequest() {
        EmployeeModel employee = new EmployeeModel();
        when(employeeService.create(employee)).thenThrow(new IllegalArgumentException("Invalid data"));

        ResponseEntity<?> response = privateAuth.postEmployee(employee);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
        assertEquals("Invalid data", ((Map<?, ?>) response.getBody()).get("error"));

        verify(employeeService, times(1)).create(employee);
    }

    @Test
    @DisplayName("Deve retornar erro 500 se serviço lançar exceção para manager")
    public void testPostManagerServerError() {
        StoreManagerModel manager = new StoreManagerModel();
        when(storeManagerService.create(manager)).thenThrow(new RuntimeException("Internal Error"));

        ResponseEntity<?> response = privateAuth.postManager(manager);

        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
        assertEquals("Internal Server Error", ((Map<?, ?>) response.getBody()).get("error"));

        verify(storeManagerService, times(1)).create(manager);
    }
}
