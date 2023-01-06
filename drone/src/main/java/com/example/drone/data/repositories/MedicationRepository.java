package com.example.drone.data.repositories;

import com.example.drone.data.models.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends MongoRepository<Medication ,String> {
    Optional<Medication> findMedicationByName(String name);
}
