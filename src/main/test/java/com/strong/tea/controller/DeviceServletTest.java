package com.strong.tea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strong.tea.entity.Device;
import com.strong.tea.exception.device.DeviceNotFoundException;
import com.strong.tea.service.DeviceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeviceServletTest {

    @Test
    void testDoPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String jsonDevice = """
            {"id":1,
            "name":"TestDevice",
            "type":"TestType",
            "manufacturer":"TestManufacturer",
            "model":"TestModel",
            "serialNumber":"TestSerial"}""
            """;
        BufferedReader reader = new BufferedReader(new StringReader(jsonDevice));
        when(request.getReader()).thenReturn(reader);

        DeviceService service = mock(DeviceService.class);

        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readValue(any(BufferedReader.class), eq(Device.class))).thenReturn(new Device());

        DeviceServlet servlet = new DeviceServlet();
        servlet.service = service;
        servlet.objectMapper = objectMapper;

        servlet.doPost(request, response);

        verify(service).save(any(Device.class));
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Mock
    private DeviceService deviceService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private DeviceServlet servlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servlet = new DeviceServlet();
        servlet.service = deviceService;
    }

    @Test
    public void testDoGetWithIdParameter() throws Exception {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("id")).thenReturn("1");

        Device device = new Device();
        device.setId(1);
        device.setName("Test Device");
        device.setType("Test Type");
        device.setManufacturer("Test Manufacturer");
        device.setModel("Test Model");
        device.setSerialNumber("Test Serial");

        when(deviceService.getDeviceById(1)).thenReturn(device);

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(deviceService).getDeviceById(1);

        String expectedJson = "{\"id\":1,\"name\":\"Test Device\",\"type\":\"Test Type\",\"manufacturer\":\"Test Manufacturer\",\"model\":\"Test Model\",\"serialNumber\":\"Test Serial\"}";
        assertEquals(expectedJson, stringWriter.toString().trim());
    }

}
