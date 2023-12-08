package com.my.device.service;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;
import com.my.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void addDevice(DeviceDTO device) throws Exception {
        if (!this.deviceRepository.existsById(device.id())) {
            this.deviceRepository.save(new Device(device));
        } else {
            throw new Exception("Device already exists! Try update it if it is the case.");
        }
    }

    @Override
    public Device getDeviceById(Long deviceId) {
        return this.deviceRepository.getReferenceById(deviceId);
    }
}
