package com.my.device.controller;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;
import com.my.device.service.DeviceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeviceControllerTest {

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @Test
    void should_Success_when_addDevice() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO(1L, "TestDevice", "TestBrand", LocalDateTime.now());

        ResponseEntity<Object> response = deviceController.addDevice(deviceDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(deviceService, Mockito.times(1)).addDevice(deviceDTO);
    }

    @Test
    void should_InternalServerError_when_addDevice_ExceptionThrown() throws Exception {
        DeviceDTO deviceDTO = new DeviceDTO(1L, "TestDevice", "TestBrand", LocalDateTime.now());
        Mockito.doThrow(new RuntimeException("Error")).when(deviceService).addDevice(deviceDTO);

        ResponseEntity<Object> response = deviceController.addDevice(deviceDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void should_Success_when_getDeviceById_DeviceFound() {
        Long deviceId = 1L;
        Device device = new Device(deviceId, "TestDevice", "TestBrand", LocalDateTime.now());
        Mockito.when(deviceService.getDeviceById(deviceId)).thenReturn(device);

        ResponseEntity<Object> response = deviceController.getDeviceById(deviceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new DeviceDTO(device), response.getBody());
    }

    @Test
    void should_Success_when_getAllDevices() {
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device1", "Brand1", LocalDateTime.now()),
                new Device(2L, "Device2", "Brand2", LocalDateTime.now())
        );
        Mockito.when(deviceService.getAllDevices()).thenReturn(devices);

        ResponseEntity<List<DeviceDTO>> response = deviceController.getAllDevices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void should_Success_when_updateDevice() {
        Long deviceId = 1L;
        Device device = new Device(deviceId, "UpdatedDevice", "UpdatedBrand", LocalDateTime.now());

        ResponseEntity<Object> response = deviceController.updateDevice(deviceId, device);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new DeviceDTO(device), response.getBody());
        Mockito.verify(deviceService, Mockito.times(1)).updateDevice(deviceId, device);
    }

    @Test
    void should_InternalServerError_when_updateDevice_ExceptionThrown() {
        Long deviceId = 1L;
        Device device = new Device(deviceId, "UpdatedDevice", "UpdatedBrand", LocalDateTime.now());
        Mockito.doThrow(new RuntimeException("Error")).when(deviceService).updateDevice(deviceId, device);

        ResponseEntity<Object> response = deviceController.updateDevice(deviceId, device);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void should_Success_when_deleteDevice() {
        Long deviceId = 1L;

        ResponseEntity<Object> response = deviceController.deleteDevice(deviceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(deviceService, Mockito.times(1)).deleteDevice(deviceId);
    }

    @Test
    void should_InternalServerError_when_deleteDevice_ExceptionThrown() {
        Long deviceId = 1L;
        Mockito.doThrow(new RuntimeException("Error")).when(deviceService).deleteDevice(deviceId);

        ResponseEntity<Object> response = deviceController.deleteDevice(deviceId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void should_Success_when_searchDeviceByBrand() {
        String brand = "Brand1";
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device1", brand, LocalDateTime.now()),
                new Device(2L, "Device2", brand, LocalDateTime.now())
        );
        Mockito.when(deviceService.searchDeviceByBrand(brand)).thenReturn(devices);

        ResponseEntity<List<DeviceDTO>> response = deviceController.searchDeviceByBrand(brand);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
