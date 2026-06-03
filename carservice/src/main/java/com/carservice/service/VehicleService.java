package com.carservice.service;

import com.carservice.entity.User;
import com.carservice.entity.Vehicle;
import com.carservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehiclesByOwner(User owner) {
        return vehicleRepository.findByOwner(owner);
    }

    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public long countVehicles() {
        return vehicleRepository.count();
    }
}
