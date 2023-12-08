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
}
