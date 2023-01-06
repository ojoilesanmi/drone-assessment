package com.example.drone.services;

import com.example.drone.data.enums.Model;
import com.example.drone.data.enums.State;
import com.example.drone.data.models.Drone;
import com.example.drone.data.models.Medication;
import com.example.drone.data.repositories.DroneRepository;
import com.example.drone.dto.LoadDroneDto;
import com.example.drone.dto.RegisterDroneDto;
import com.example.drone.exceptions.DroneException;
import com.example.drone.exceptions.MedicationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DroneService{
    private final DroneRepository droneRepository;
    private final MedicationService medicationService;
    public Drone registerDrone(RegisterDroneDto droneDto) throws DroneException {
        validateDroneRegistrationDto(droneDto);
        Drone droneToRegister = Drone.builder()
                .serialNumber(droneDto.getSerialNumber())
                .model(checkDroneModel(droneDto.getModel()))
                .weightLimit(droneDto.getWeightLimit())
                .batteryCapacity(droneDto.getBatteryCapacity())
                .droneState(State.IDLE)
                .build();
        return droneRepository.save(droneToRegister);
    }

    private void validateDroneRegistrationDto(RegisterDroneDto registerDroneDto) throws DroneException {
        if (registerDroneDto.getSerialNumber().isEmpty() || registerDroneDto.getSerialNumber().isBlank()){
            throw new DroneException("Serial number cannot be blank or empty");
        }
        if (registerDroneDto.getSerialNumber().length() > 100){
            throw new DroneException("Serial number cannot be more than 100 characters");
        }
        if (registerDroneDto.getModel().isBlank() || registerDroneDto.getModel().isEmpty()){
            throw new DroneException("Drone model cannot be blank or empty");
        }
        if (!registerDroneDto.getModel().equalsIgnoreCase("Lightweight") || registerDroneDto.getModel().equalsIgnoreCase("Middleweight") ||registerDroneDto.getModel().equalsIgnoreCase("Cruiserweight") ||registerDroneDto.getModel().equalsIgnoreCase("Heavyweight")){
            throw new DroneException("Drone model is invalid!");
        }
        if (registerDroneDto.getWeightLimit() >500){
            throw new DroneException("Weight is higher than normal");
        }
        if (registerDroneDto.getWeightLimit() <= 0){
            throw new DroneException("Weight is invalid");
        }
        if (registerDroneDto.getBatteryCapacity() <=0){
            throw new DroneException("Drone battery is too low");
        }
        if (registerDroneDto.getBatteryCapacity() > 100){
            throw new DroneException("Drone battery capacity is invalid");
        }
    }

    private Model checkDroneModel(String model){
        if (model.equalsIgnoreCase("LIGHTWEIGHT")){
            return Model.LIGHTWEIGHT;
        }
        if (model.equalsIgnoreCase("MIDDLEWEIGHT")){
            return Model.MIDDLEWEIGHT;
        }
        if (model.equalsIgnoreCase("CRUISERWEIGHT")){
            return Model.CRUISERWEIGHT;
        }
        if (model.equalsIgnoreCase("HEAVYWEIGHT")){
            return Model.HEAVYWEIGHT;
        }
        return null;
    }

    public Drone loadDroneWithMedication(LoadDroneDto loadDroneDto) throws MedicationException, DroneException {
        List<Medication> medications = new ArrayList<>();
        int weightCount = 0;
        for (String name : loadDroneDto.getMedicationNames()){
            if (name.isBlank() || name.isEmpty()){
                throw new MedicationException("Medication name cannot be blank or empty");
            }
            Medication med = medicationService.findMedicationByName(name);
            weightCount += med.getWeight();
            medications.add(med);
        }
        Drone drone = findDroneBySerialNumber(loadDroneDto.getDroneSerialNumber());
        if(weightCount > 500){
            throw new DroneException("Drone cannot carry this medications");
        }
        drone.setLoadedMedications(medications);
        drone.setDroneState(State.LOADED);
        return droneRepository.save(drone);
    }

    private Drone findDroneBySerialNumber(String serialNumber) throws DroneException {
        if (serialNumber.isEmpty() || serialNumber.isBlank()){
            throw new DroneException("Drone serialNumber cannot be blank or empty");
        }
        Optional<Drone> drone = droneRepository.findDroneBySerialNumber(serialNumber);
        if (drone.isEmpty()){
            throw new DroneException("Drone serialNumber is not valid");
        }
        return drone.get();
    }

    public List<Medication> checkLoadedMedicationsInDrone(String droneId) throws DroneException {
        if(droneId.isBlank() || droneId.isEmpty()){
            throw new DroneException("Drone id cannot be empty or blank");
        }
        Optional<Drone> drone = droneRepository.findById(droneId);
        if (drone.isEmpty()){
            throw new DroneException("Drone not found");
        }
        return drone.get().getLoadedMedications();
    }

    public List<Drone> checkAvailableDronesForLoading(){
        return droneRepository.findDronesByDroneState(State.IDLE);
    }

    public String checkDroneBattery(String droneId) throws DroneException {
        if(droneId.isBlank() || droneId.isEmpty()){
            throw new DroneException("Drone id cannot be empty or blank");
        }
        Optional<Drone> drone = droneRepository.findById(droneId);
        if (drone.isEmpty()){
            throw new DroneException("Drone not found");
        }

        return ""+drone.get().getBatteryCapacity()+"%";
    }
}
