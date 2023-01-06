package com.example.drone.data.repositories;

import com.example.drone.data.enums.State;
import com.example.drone.data.models.Drone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends MongoRepository<Drone,String> {
    Optional<Drone> findDroneBySerialNumber(String serialNumber);
    List<Drone> findDronesByDroneState(State  droneState);
}
