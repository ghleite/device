package com.my.device.service;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;
import com.my.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Device> getAllDevices() {
        return this.deviceRepository.findAll();
    }

    @Override
    public Device updateDevice(Long deviceId, Device device) {
        Optional<Device> existingDevice = deviceRepository.findById(deviceId);

        if (existingDevice.isPresent()) {
            Device updatedDevice = existingDevice.get();
            updatedDevice.setName(device.getName());
            updatedDevice.setBrand(device.getBrand());
            updatedDevice.setCreationTime(LocalDateTime.now());

            return deviceRepository.save(updatedDevice);
        } else {
            throw new NotFoundException("Device not found with ID: " + deviceId);
        }
    }

    @Override
    public void deleteDevice(Long deviceId) {
        this.deviceRepository.deleteById(deviceId);
    }

    @Override
    public List<Device> searchDeviceByBrand(String brand) {
        return this.deviceRepository.findAllByBrand(brand);
    }

    @Override
    public List<DeviceDTO> deviceToDeviceDtoList(List<Device> allDevices) {
        return allDevices.stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }
}
