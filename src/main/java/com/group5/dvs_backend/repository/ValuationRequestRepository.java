package com.group5.dvs_backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT vr FROM ValuationRequest vr WHERE vr.status = ?1 OR vr.status = ?2")
    List<ValuationRequest> findByTwoStatus(String status1, String status2);

    @Query("SELECT vr FROM ValuationRequest vr WHERE vr.status = ?1 OR vr.status = ?2 OR vr.status = ?3")
    List<ValuationRequest> findByThreeStatus(String status1, String status2, String status3);

    @Query("SELECT vr FROM ValuationRequest vr " +
            "INNER JOIN vr.customer c " +
            "INNER JOIN vr.service" +
            " WHERE vr.consultingStaffId =?1 AND (vr.status =?2 OR vr.status =?3)")
    List<ValuationRequest> findByConsultingStaffIdAndTwoStatus(Long consultingStaffId, String status1, String status2);
    @Query("SELECT vr FROM ValuationRequest vr " +
            "INNER JOIN vr.customer c " +
            "INNER JOIN vr.service" +
            " WHERE vr.consultingStaffId =?1 AND (vr.status =?2 OR vr.status =?3 OR vr.status = ?4)")
    List<ValuationRequest> findByConsultingStaffIdAndThreeStatus(Long consultingStaffId, String status1, String status2, String status3);

    List<ValuationRequest> findByCustomerId(Long id);

    @Query("SELECT vr FROM ValuationRequest vr " +
            "WHERE CAST(vr.requestDate AS date) >= :from AND CAST(vr.requestDate AS date) <= :to")
    List<ValuationRequest> findInRange(@Param("from") LocalDate from,@Param("to") LocalDate to);
}
