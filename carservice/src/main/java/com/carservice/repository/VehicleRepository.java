package com.carservice.repository;

import com.carservice.entity.Vehicle;
import com.carservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwner(User owner);
}
