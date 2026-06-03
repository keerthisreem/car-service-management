package com.carservice.service;

import com.carservice.entity.Appointment;
import com.carservice.entity.Vehicle;
import com.carservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByVehicles(List<Vehicle> vehicles) {
        return appointmentRepository.findByVehicleIn(vehicles);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    public void updateStatus(Long id, Appointment.Status status) {
        appointmentRepository.findById(id).ifPresent(a -> {
            a.setStatus(status);
            appointmentRepository.save(a);
        });
    }

    public long countByStatus(Appointment.Status status) {
        return appointmentRepository.countByStatus(status);
    }

    public long countAll() {
        return appointmentRepository.count();
    }
}
