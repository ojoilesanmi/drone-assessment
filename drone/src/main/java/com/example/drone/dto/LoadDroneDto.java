package com.example.drone.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoadDroneDto {
    private String droneSerialNumber;
    private List<String>medicationNames;

}
