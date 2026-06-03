package com.carservice.controller;

import com.carservice.entity.*;
import com.carservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private VehicleService vehicleService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private ServiceTypeService serviceTypeService;

    private User getCurrentUser(UserDetails ud) {
        return userService.findByEmail(ud.getUsername()).orElseThrow();
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = getCurrentUser(ud);
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);
        List<Appointment> appointments = appointmentService.getAppointmentsByVehicles(vehicles);

        long pendingCount = appointments.stream()
                .filter(a -> a.getStatus() == Appointment.Status.PENDING).count();
        long completedCount = appointments.stream()
                .filter(a -> a.getStatus() == Appointment.Status.COMPLETED).count();

        model.addAttribute("user", user);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("appointments", appointments);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("completedCount", completedCount);
        return "user/dashboard";
    }

    // --- Vehicles ---
    @GetMapping("/vehicles")
    public String vehicles(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = getCurrentUser(ud);
        model.addAttribute("vehicles", vehicleService.getVehiclesByOwner(user));
        model.addAttribute("vehicle", new Vehicle());
        return "user/vehicles";
    }

    @PostMapping("/vehicles/add")
    public String addVehicle(@ModelAttribute Vehicle vehicle,
                              @AuthenticationPrincipal UserDetails ud,
                              RedirectAttributes attrs) {
        User user = getCurrentUser(ud);
        vehicle.setOwner(user);
        vehicleService.save(vehicle);
        attrs.addFlashAttribute("message", "Vehicle added successfully!");
        return "redirect:/user/vehicles";
    }

    @GetMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes attrs) {
        vehicleService.deleteById(id);
        attrs.addFlashAttribute("message", "Vehicle removed.");
        return "redirect:/user/vehicles";
    }

    // --- Appointments ---
    @GetMapping("/appointments")
    public String appointments(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = getCurrentUser(ud);
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);
        List<Appointment> appointments = appointmentService.getAppointmentsByVehicles(vehicles);
        model.addAttribute("appointments", appointments);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
        model.addAttribute("appointment", new Appointment());
        return "user/appointments";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam Long vehicleId,
                                   @RequestParam Long serviceTypeId,
                                   @RequestParam String appointmentDate,
                                   @RequestParam(required = false) String notes,
                                   RedirectAttributes attrs) {
        Appointment appt = new Appointment();
        appt.setVehicle(vehicleService.findById(vehicleId).orElseThrow());
        appt.setServiceType(serviceTypeService.findById(serviceTypeId).orElseThrow());
        appt.setAppointmentDate(java.time.LocalDate.parse(appointmentDate));
        appt.setNotes(notes);
        appt.setStatus(Appointment.Status.PENDING);
        appointmentService.save(appt);
        attrs.addFlashAttribute("message", "Appointment booked! Awaiting approval.");
        return "redirect:/user/appointments";
    }

    // --- Profile ---
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails ud, Model model) {
        User user = getCurrentUser(ud);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails ud,
                                 @RequestParam String name,
                                 @RequestParam String phone,
                                 RedirectAttributes attrs) {
        User user = getCurrentUser(ud);
        user.setName(name);
        user.setPhone(phone);
        userService.save(user);
        attrs.addFlashAttribute("message", "Profile updated successfully!");
        return "redirect:/user/profile";
    }

    // --- Services ---
    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
        return "user/services";
    }
}
