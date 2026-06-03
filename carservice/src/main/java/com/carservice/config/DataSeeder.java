package com.carservice.config;

import com.carservice.entity.ServiceType;
import com.carservice.entity.User;
import com.carservice.repository.ServiceTypeRepository;
import com.carservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private ServiceTypeRepository serviceTypeRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin
        if (!userRepository.existsByEmail("admin@carservice.com")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@carservice.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setPhone("9999999999");
            userRepository.save(admin);
            System.out.println("✅ Default admin created: admin@carservice.com / admin123");
        }

        // Seed service types
        if (serviceTypeRepository.count() == 0) {
            String[][] services = {
                {"Oil Change", "Full synthetic oil change with filter replacement", "800", "1"},
                {"Tyre Rotation", "Rotate all four tyres for even wear", "400", "1"},
                {"Brake Inspection", "Full brake pad and rotor check", "600", "2"},
                {"AC Service", "Refrigerant refill and compressor check", "1500", "3"},
                {"Full Service", "Comprehensive vehicle inspection and service", "3500", "6"},
                {"Battery Replacement", "Check and replace car battery", "1200", "1"},
                {"Engine Diagnostics", "Computer diagnostics scan", "500", "1"}
            };
            for (String[] s : services) {
                ServiceType st = new ServiceType();
                st.setName(s[0]);
                st.setDescription(s[1]);
                st.setEstimatedCost(Double.parseDouble(s[2]));
                st.setEstimatedDurationHours(Integer.parseInt(s[3]));
                serviceTypeRepository.save(st);
            }
            System.out.println("✅ Service types seeded.");
        }
    }
}
