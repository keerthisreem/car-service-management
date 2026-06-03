package com.carservice.repository;

import com.carservice.entity.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
}
