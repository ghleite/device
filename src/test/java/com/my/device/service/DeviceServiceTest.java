package com.my.device.service;

import com.my.device.dto.DeviceDTO;
import com.my.device.entity.Device;
import com.my.device.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @Test
    void should_ReturnSuccess_when_addDevice() throws Exception {
        DeviceDTO deviceDTO =
                new DeviceDTO(1L, "TestDevice", "TestBrand", LocalDateTime.now());
        Mockito.when(deviceRepository.existsById(anyLong())).thenReturn(false);

        deviceService.addDevice(deviceDTO);

        Mockito.verify(deviceRepository, Mockito.times(1)).save(any(Device.class));
    }

    @Test
    void should_ExceptionThrown_when_DeviceAlreadyExists_in_addDevice() {
        DeviceDTO deviceDTO = new DeviceDTO(1L, "TestDevice", "TestBrand", LocalDateTime.now());
        Mockito.when(deviceRepository.existsById(anyLong())).thenReturn(true);

        assertThrows(Exception.class, () -> deviceService.addDevice(deviceDTO));
    }

    @Test
    void should_ReturnDevice_when_getDeviceById() {
        Device device = new Device(1L, "TestDevice", "TestBrand", LocalDateTime.now());
        Mockito.when(deviceRepository.getReferenceById(anyLong())).thenReturn(device);

        Device result = deviceService.getDeviceById(1L);

        assertEquals(device, result);
    }

    @Test
    void should_ReturnDeviceList_when_getAllDevices() {
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device1", "Brand1", LocalDateTime.now()),
                new Device(2L, "Device2", "Brand2", LocalDateTime.now())
        );
        Mockito.when(deviceRepository.findAll()).thenReturn(devices);

        List<Device> result = deviceService.getAllDevices();

        assertEquals(devices, result);
    }

    @Test
    void should_succeed_updateDevice_when_DeviceExists() {
        Long deviceId = 1L;
        Device existingDevice = new Device(deviceId, "Device1", "Brand1", LocalDateTime.now());
        Device updatedDevice = new Device(deviceId, "UpdatedDevice", "UpdatedBrand", LocalDateTime.now());

        Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        Mockito.when(deviceRepository.save(any(Device.class))).thenReturn(updatedDevice);

        Device result = deviceService.updateDevice(deviceId, updatedDevice);

        assertEquals(updatedDevice, result);
    }

    @Test
    void should_ExceptionThrown_when_DeviceNotFound_in_updateDevice() {
        Long deviceId = 1L;
        Device updatedDevice = new Device(deviceId, "UpdatedDevice", "UpdatedBrand", LocalDateTime.now());

        Mockito.when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> deviceService.updateDevice(deviceId, updatedDevice));
    }

    @Test
    void should_DeleteDevice_when_DeviceExists_in_deleteDevice() {
        Long deviceId = 1L;

        deviceService.deleteDevice(deviceId);

        Mockito.verify(deviceRepository, Mockito.times(1)).deleteById(deviceId);
    }

    @Test
    void should_ReturnDeviceList_when_searchDeviceByBrand() {
        String brand = "Brand1";
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device1", brand, LocalDateTime.now()),
                new Device(2L, "Device2", brand, LocalDateTime.now())
        );

        Mockito.when(deviceRepository.findAllByBrand(brand)).thenReturn(devices);

        List<Device> result = deviceService.searchDeviceByBrand(brand);

        assertEquals(devices, result);
    }

    @Test
    void should_ReturnDeviceDTOList_when_deviceToDeviceDtoList() {
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device1", "Brand1", LocalDateTime.now()),
                new Device(2L, "Device2", "Brand2", LocalDateTime.now())
        );

        List<DeviceDTO> result = deviceService.deviceToDeviceDtoList(devices);

        assertEquals(devices.size(), result.size());
        assertEquals(devices.get(0).getName(), result.get(0).name());
        assertEquals(devices.get(0).getBrand(), result.get(0).brand());
        assertEquals(devices.get(1).getName(), result.get(1).name());
        assertEquals(devices.get(1).getBrand(), result.get(1).brand());
    }
}
