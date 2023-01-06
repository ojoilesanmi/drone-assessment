package com.example.drone.dto;

import com.example.drone.data.enums.Model;
import com.example.drone.data.enums.State;
import lombok.Data;

@Data
public class RegisterDroneDto {
    private String serialNumber;
    private String model;
    private int weightLimit;
    private int batteryCapacity;
}
