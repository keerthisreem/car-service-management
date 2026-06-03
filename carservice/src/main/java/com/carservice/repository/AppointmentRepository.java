package com.carservice.repository;

import com.carservice.entity.Appointment;
import com.carservice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByVehicleIn(List<Vehicle> vehicles);
    List<Appointment> findByStatus(Appointment.Status status);
    long countByStatus(Appointment.Status status);
}
