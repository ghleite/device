package com.my.device.service;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;

public interface DeviceService {
    void addDevice(DeviceDTO device) throws Exception;
    Device getDeviceById(Long deviceId);
}
