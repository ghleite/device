package com.my.device.entity;

import com.my.device.dto.DeviceDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "device")
@Entity(name = "Device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private LocalDateTime creationTime;

    public Device(DeviceDTO device) {
        this.id = device.id();
        this.name = device.name();
        this.brand = device.brand();
        this.creationTime = device.creationTime();
    }

}
