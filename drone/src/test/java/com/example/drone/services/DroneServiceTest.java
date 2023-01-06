package com.example.drone.services;

import com.example.drone.data.enums.Model;
import com.example.drone.data.models.Drone;
import com.example.drone.data.models.Medication;
import com.example.drone.dto.LoadDroneDto;
import com.example.drone.dto.RegisterDroneDto;
import com.example.drone.exceptions.DroneException;
import com.example.drone.exceptions.MedicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DroneServiceTest {
    @Autowired
    private DroneService droneService;
    @Test
    @DisplayName("Register a drone with right details and then a registered drone will be returned with an id")
    void testThatADroneCanBeRegistered() throws DroneException {
        RegisterDroneDto droneDto =  new RegisterDroneDto();
        droneDto.setSerialNumber("hatettea223");
        droneDto.setModel(Model.LIGHTWEIGHT.name());
        droneDto.setBatteryCapacity(100);
        droneDto.setWeightLimit(500);
        Drone drone = droneService.registerDrone(droneDto);
        assertThat(drone.getId()).isNotEmpty();
    }

    @Test
    @DisplayName("Register a drone with an empty or blank serial number and then an exception will be thrown")
    void testThatADroneCannotBeRegisteredBecauseSerialNumberIsBlank() throws DroneException {
        RegisterDroneDto droneDto =  new RegisterDroneDto();
        droneDto.setSerialNumber("");
        droneDto.setModel(Model.LIGHTWEIGHT.name());
        droneDto.setBatteryCapacity(100);
        droneDto.setWeightLimit(500);
        assertThrows(DroneException.class, ()->droneService.registerDrone(droneDto));
    }

    @Test
    @DisplayName("Register a drone with an empty or blank model and then an exception will be thrown")
    void testThatADroneCannotBeRegisteredBecauseModelIsBlank() throws DroneException {
        RegisterDroneDto droneDto =  new RegisterDroneDto();
        droneDto.setSerialNumber("hatettea223");
        droneDto.setModel("");
        droneDto.setBatteryCapacity(100);
        droneDto.setWeightLimit(500);
        assertThrows(DroneException.class, ()->droneService.registerDrone(droneDto));
    }

    @Test
    @DisplayName("Register a drone with a capacity value not set and then an exception will be thrown")
    void testThatADroneCannotBeRegisteredBecauseCapacityIsNotSet() throws DroneException {
        RegisterDroneDto droneDto =  new RegisterDroneDto();
        droneDto.setSerialNumber("hatettea223");
        droneDto.setModel(Model.LIGHTWEIGHT.name());
        droneDto.setWeightLimit(500);
        assertThrows(DroneException.class, ()->droneService.registerDrone(droneDto));
    }

    @Test
    @DisplayName("Register a drone when the weight limit value is not set and then an exception will be thrown")
    void testThatADroneCannotBeRegisteredBecauseWeightLimitIsNotSet() throws DroneException {
        RegisterDroneDto droneDto =  new RegisterDroneDto();
        droneDto.setSerialNumber("hatettea223");
        droneDto.setModel(Model.LIGHTWEIGHT.name());
        droneDto.setBatteryCapacity(100);
        assertThrows(DroneException.class, ()->droneService.registerDrone(droneDto));
    }
    @Test
    @DisplayName("Load a drone with the right serial number and then a drone with list of medications will be returned from the database")
    void testThatADroneCanBeLoadedWithTheMedications() throws MedicationException, DroneException {
        List<String> medications  = new ArrayList<>();
        medications.add("4thDrone");
        medications.add("5thDrone");
        LoadDroneDto loadDroneDto = new LoadDroneDto();
        loadDroneDto.setDroneSerialNumber("effdwtett23523");
        loadDroneDto.setMedicationNames(medications);
        Drone drone = droneService.loadDroneWithMedication(loadDroneDto);
        assertThat(drone.getLoadedMedications().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Check loaded medications in a particular drone with right drone id and then a list of medications will br returned from the database")
    void testThatLoadedMedicationsInDronesCanBeChecked() throws DroneException {
        List<Medication>medications = droneService.checkLoadedMedicationsInDrone("638008883fe07651c80d06c0");
        assertThat(medications.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Check available drones for loading and then an empty list will be returned")
    void testThatEmptyDronesAvailableForLoadingCanBeChecked() throws DroneException {
        List<Drone>drones = droneService.checkAvailableDronesForLoading();
        assertThat(drones).isEmpty();
    }
}