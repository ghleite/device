package com.my.device.service;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;

import java.util.List;

public interface DeviceService {
    void addDevice(DeviceDTO device) throws Exception;
    Device getDeviceById(Long deviceId);
    List<Device> getAllDevices();
    Device updateDevice(Long deviceId, Device device);
    void deleteDevice(Long deviceId);
    List<Device> searchDeviceByBrand(String brand);

    List<DeviceDTO> deviceToDeviceDtoList(List<Device> allDevices);
}
