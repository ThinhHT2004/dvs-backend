package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Long> {

    @Query("SELECT (sp.initPrice + (?2 - sp.sizeFrom) * sp.priceUnit) " +
            "FROM ServicePrice sp " +
            "WHERE sp.serviceId = ?1 AND (?2 >= sp.sizeFrom AND ?2 < sp.sizeTo)")
    Double findPriceByIdAndSize(Long serviceId, Double size);
}