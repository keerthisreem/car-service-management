package com.carservice.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    private LocalDate appointmentDate;

    private String notes;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private ServiceRecord serviceRecord;

    public enum Status { PENDING, APPROVED, IN_PROGRESS, COMPLETED, CANCELLED }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public ServiceRecord getServiceRecord() { return serviceRecord; }
    public void setServiceRecord(ServiceRecord serviceRecord) { this.serviceRecord = serviceRecord; }
}
