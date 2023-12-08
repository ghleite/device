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

    private static final Object ERROR_MESSAGE = "Could not create device due to error: ";

    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Create device", description = "To create a new device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Object> addDevice(@RequestBody DeviceDTO device) {
        try {
            this.deviceService.addDevice(device);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_MESSAGE + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get device", description = "To get device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found"),
            @ApiResponse(responseCode = "204", description = "No such a device with this ID")
    })
    @GetMapping(value = "/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDeviceById(@PathVariable Long deviceId) {
        try {
            Device device = this.deviceService.getDeviceById(deviceId);
            return new ResponseEntity<>(new DeviceDTO(device), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Device not found!", HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Get all devices", description = "To get all devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All devices showed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        try {
            List<Device> allDevices = this.deviceService.getAllDevices();
            return new ResponseEntity<>(this.deviceService.deviceToDeviceDtoList(allDevices),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update device", description = "To update a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{deviceId}")
    public ResponseEntity<Object> updateDevice(@PathVariable Long deviceId, @RequestBody Device device) {
        try {
            this.deviceService.updateDevice(deviceId, device);
            return new ResponseEntity<>(new DeviceDTO(device), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_MESSAGE + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete device", description = "To delete device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Object> deleteDevice(@PathVariable Long deviceId) {
        try {
            this.deviceService.deleteDevice(deviceId);
            return new ResponseEntity<>("Device of ID: " + deviceId + " has been deleted!",
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ERROR_MESSAGE + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all devices by brand", description = "To get all devices by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All devices by brand showed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<DeviceDTO>> searchDeviceByBrand(@RequestParam String brand) {
        try {
            List<Device> devicesByBrand = this.deviceService.searchDeviceByBrand(brand);
            return new ResponseEntity<>(this.deviceService.deviceToDeviceDtoList(devicesByBrand),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
