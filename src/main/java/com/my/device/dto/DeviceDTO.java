package com.my.device.dto;

import com.my.device.entity.Device;

import java.time.LocalDateTime;

public record DeviceDTO(
    Long id,
    String name,
    String brand,
    LocalDateTime creationTime) {

    public DeviceDTO(Device device) {
        this(device.getId(), device.getName(), device.getBrand(), device.getCreationTime());
    }
}
