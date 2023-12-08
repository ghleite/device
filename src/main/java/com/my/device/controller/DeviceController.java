package com.my.device.controller;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;
import com.my.device.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Create device", description = "To create a new device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Object> addDevice(@RequestBody DeviceDTO device) {
        try {
            this.deviceService.addDevice(device);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not create device due to error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get device", description = "To get device by ID")
    @GetMapping(value = "/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDeviceById(@PathVariable Long deviceId) {
        try {
            Device device = this.deviceService.getDeviceById(deviceId);
            return new ResponseEntity<>(new DeviceDTO(device), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Device not found!", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<Device> allDevices = this.deviceService.getAllDevices();
        return new ResponseEntity<>(this.deviceService.deviceToDeviceDtoList(allDevices),
                HttpStatus.OK);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<Object> updateDevice(@PathVariable Long deviceId, @RequestBody Device device) {
        this.deviceService.updateDevice(deviceId, device);
        return new ResponseEntity<>(new DeviceDTO(device), HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Object> deleteDevice(@PathVariable Long deviceId) {
        this.deviceService.deleteDevice(deviceId);
        return new ResponseEntity<>("Device of ID: " + deviceId + " has been deleted!",
                HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeviceDTO>> searchDeviceByBrand(@RequestParam String brand) {
        List<Device> devicesByBrand = this.deviceService.searchDeviceByBrand(brand);
        return new ResponseEntity<>(this.deviceService.deviceToDeviceDtoList(devicesByBrand),
                HttpStatus.OK);
    }
}
