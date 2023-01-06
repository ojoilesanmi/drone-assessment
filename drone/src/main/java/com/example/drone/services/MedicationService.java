package com.example.drone.services;

import com.example.drone.data.models.Medication;
import com.example.drone.data.repositories.MedicationRepository;
import com.example.drone.exceptions.MedicationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public Medication saveMedication(Medication medication) throws MedicationException {
        validateMedicationToBeSaved(medication);
        return medicationRepository.save(medication);
    }

    private void validateMedicationToBeSaved(Medication medication) throws MedicationException {
        if (medication.getName().isBlank() || medication.getName().isEmpty()){
            throw new MedicationException("Medication name cannot be blank or empty");
        }
        if (medication.getWeight() <= 0){
            throw new MedicationException("Medication weight cannot be less than or equals to zero");
        }
        if (medication.getCode().isEmpty()|| medication.getCode().isBlank()){
            throw new MedicationException("Medication code cannot be blank or empty");
        }
        if (medication.getImageUrl().isEmpty()|| medication.getImageUrl().isBlank()){
            throw new MedicationException("Medication image cannot be blank or empty");
        }
    }

    public Medication findMedicationByName(String medicationName) throws MedicationException {
        if(medicationName.isEmpty() || medicationName.isBlank()){
            throw new MedicationException("Medication name cannot be blank or empty");
        }

        Optional<Medication> medication = medicationRepository.findMedicationByName(medicationName);
        if (medication.isEmpty()){
            throw new MedicationException("Medication not found");
        }
        return medication.get();
    }
}
