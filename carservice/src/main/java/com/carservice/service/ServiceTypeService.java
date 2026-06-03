package com.carservice.service;

import com.carservice.entity.ServiceType;
import com.carservice.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public ServiceType save(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public Optional<ServiceType> findById(Long id) {
        return serviceTypeRepository.findById(id);
    }

    public void deleteById(Long id) {
        serviceTypeRepository.deleteById(id);
    }
}
