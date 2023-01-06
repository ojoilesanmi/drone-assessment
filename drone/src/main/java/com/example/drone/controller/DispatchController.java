package com.example.drone.controller;

import com.example.drone.data.models.Medication;
import com.example.drone.dto.LoadDroneDto;
import com.example.drone.dto.RegisterDroneDto;
import com.example.drone.exceptions.DroneException;
import com.example.drone.exceptions.MedicationException;
import com.example.drone.services.DroneService;
import com.example.drone.services.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/dispatch")
@AllArgsConstructor
public class DispatchController {
    private final DroneService droneService;
    private final MedicationService medicationService;
    @PostMapping("/drone/register")
    public ResponseEntity<?> registerDrone(@RequestBody RegisterDroneDto droneDto){
        try {
            return ResponseEntity.ok().body(droneService.registerDrone(droneDto));
        } catch (DroneException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
    @PostMapping("/save/medication")
    public ResponseEntity<?> saveMedication(@RequestBody Medication medication){
        try {
            return ResponseEntity.ok().body(medicationService.saveMedication(medication));
        } catch (MedicationException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @PutMapping("/drone/load")
    public ResponseEntity<?> loadDrone(@RequestBody LoadDroneDto loadDroneDto){
        try {
            return ResponseEntity.ok().body(droneService.loadDroneWithMedication(loadDroneDto));
        } catch (DroneException | MedicationException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/drone/loadedItems/{droneId}")
    public ResponseEntity<?> loadMedicationItems(@PathVariable String droneId){
        try {
            return ResponseEntity.ok().body(droneService.checkLoadedMedicationsInDrone(droneId));
        } catch (DroneException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/drone/available")
    public ResponseEntity<?> loadAvailableDrones(){
        return ResponseEntity.ok().body(droneService.checkAvailableDronesForLoading());
    }

    @GetMapping("/drone/battery/{droneId}")
    public ResponseEntity<?>checkDroneBatteryLevel(@PathVariable String droneId){
        try {
            return ResponseEntity.ok().body(droneService.checkDroneBattery(droneId));
        } catch (DroneException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
}
