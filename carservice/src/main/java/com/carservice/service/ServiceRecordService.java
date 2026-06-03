package com.carservice.service;

import com.carservice.entity.Appointment;
import com.carservice.entity.ServiceRecord;
import com.carservice.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceRecordService {

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    public ServiceRecord save(ServiceRecord record) {
        return serviceRecordRepository.save(record);
    }

    public void createRecord(Appointment appointment, String workDescription,
                              String partsReplaced, double totalCost, String mechanicNotes) {
        ServiceRecord record = new ServiceRecord();
        record.setAppointment(appointment);
        record.setWorkDescription(workDescription);
        record.setPartsReplaced(partsReplaced);
        record.setTotalCost(totalCost);
        record.setMechanicNotes(mechanicNotes);
        record.setCompletedDate(java.time.LocalDate.now());
        serviceRecordRepository.save(record);
    }
}
