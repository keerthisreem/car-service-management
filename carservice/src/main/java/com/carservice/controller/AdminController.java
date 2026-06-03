package com.carservice.controller;

import com.carservice.entity.*;
import com.carservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AppointmentService appointmentService;
    @Autowired private UserService userService;
    @Autowired private VehicleService vehicleService;
    @Autowired private ServiceTypeService serviceTypeService;
    @Autowired private ServiceRecordService serviceRecordService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.countUsers());
        model.addAttribute("totalVehicles", vehicleService.countVehicles());
        model.addAttribute("totalAppointments", appointmentService.countAll());
        model.addAttribute("pendingCount", appointmentService.countByStatus(Appointment.Status.PENDING));
        model.addAttribute("inProgressCount", appointmentService.countByStatus(Appointment.Status.IN_PROGRESS));
        model.addAttribute("completedCount", appointmentService.countByStatus(Appointment.Status.COMPLETED));
        model.addAttribute("recentAppointments", appointmentService.getAllAppointments());
        return "admin/dashboard";
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "admin/appointments";
    }

    @GetMapping("/appointments/{id}/status")
    public String updateStatus(@PathVariable Long id,
                                @RequestParam String status,
                                RedirectAttributes attrs) {
        appointmentService.updateStatus(id, Appointment.Status.valueOf(status));
        attrs.addFlashAttribute("message", "Status updated to " + status);
        return "redirect:/admin/appointments";
    }

    @GetMapping("/appointments/{id}/complete")
    public String showCompleteForm(@PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.findById(id).orElseThrow());
        return "admin/complete-service";
    }

    @PostMapping("/appointments/{id}/complete")
    public String completeService(@PathVariable Long id,
                                   @RequestParam String workDescription,
                                   @RequestParam(required = false) String partsReplaced,
                                   @RequestParam double totalCost,
                                   @RequestParam(required = false) String mechanicNotes,
                                   RedirectAttributes attrs) {
        Appointment appt = appointmentService.findById(id).orElseThrow();
        serviceRecordService.createRecord(appt, workDescription, partsReplaced, totalCost, mechanicNotes);
        appointmentService.updateStatus(id, Appointment.Status.COMPLETED);
        attrs.addFlashAttribute("message", "Service marked as completed!");
        return "redirect:/admin/appointments";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/vehicles")
    public String vehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "admin/vehicles";
    }

    // Service Types CRUD
    @GetMapping("/service-types")
    public String serviceTypes(Model model) {
        model.addAttribute("serviceTypes", serviceTypeService.getAllServiceTypes());
        model.addAttribute("serviceType", new ServiceType());
        return "admin/service-types";
    }

    @PostMapping("/service-types/add")
    public String addServiceType(@ModelAttribute ServiceType serviceType, RedirectAttributes attrs) {
        serviceTypeService.save(serviceType);
        attrs.addFlashAttribute("message", "Service type added!");
        return "redirect:/admin/service-types";
    }

    @GetMapping("/service-types/delete/{id}")
    public String deleteServiceType(@PathVariable Long id, RedirectAttributes attrs) {
        serviceTypeService.deleteById(id);
        attrs.addFlashAttribute("message", "Service type deleted.");
        return "redirect:/admin/service-types";
    }
}
