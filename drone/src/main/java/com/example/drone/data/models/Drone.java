package com.example.drone.data.models;

import com.example.drone.data.enums.Model;
import com.example.drone.data.enums.State;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import java.util.List;

@Data
@Document
@Builder
public class Drone {
    @Id
    private String id;
    private String serialNumber;
    private Model model;
    @Max(500)
    private int weightLimit;
    private int batteryCapacity;
    private State droneState;
    private List<Medication> loadedMedications;
}
