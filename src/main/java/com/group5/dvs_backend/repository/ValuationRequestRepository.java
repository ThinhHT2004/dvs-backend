package com.group5.dvs_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group5.dvs_backend.entity.ValuationRequest;

@Repository
public interface ValuationRequestRepository extends JpaRepository<ValuationRequest, Long> {
    List<ValuationRequest> findAllByConsultingStaffId(Long id);


    List<ValuationRequest> findByStatus(String status);

    @Query("SELECT vr FROM ValuationRequest vr " +
            "INNER JOIN vr.customer c " +
            "INNER JOIN vr.service " +
            "WHERE vr.status = 'WAITING'")
    List<ValuationRequest> findWaitingRequestWithDetails();

    @Query("SELECT vr FROM ValuationRequest vr " +
            "INNER JOIN vr.customer c " +
            "INNER JOIN vr.service" +
            " WHERE vr.consultingStaffId =?1 AND vr.status =?2")
    List<ValuationRequest> findByConsultingStaffIdAndStatus(Long consultingStaffId, String status);

    @Query("SELECT vr FROM ValuationRequest vr " +
            "INNER JOIN vr.customer c " +
            "INNER JOIN vr.service" +
            " WHERE vr.consultingStaffId =?1 AND vr.status !=?2")
    List<ValuationRequest> findByConsultingStaffAndNotStatus(Long consultingStaffId, String status);
    @Query("SELECT vr FROM ValuationRequest vr JOIN vr.customer c JOIN vr.service " +
            "WHERE vr.id=?1")
    Optional<ValuationRequest> findByIdWithDetails(Long id);
}
