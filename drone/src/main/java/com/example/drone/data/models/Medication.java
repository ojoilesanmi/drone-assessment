package com.example.drone.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Medication {
    @Id
    private String id;
    private String droneId;
    private String name;
    private int weight;
    private String code;
    private String imageUrl;
}
