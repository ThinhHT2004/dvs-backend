package com.group5.dvs_backend.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.group5.dvs_backend.entity.*;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class ValuationRequestService {
    @Autowired
    private final ValuationRequestRepository valuationRequestRepository;
    @Autowired
    private final ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Autowired
    private final ValuationReportRepository valuationReportRepository;
    @Autowired
    private final FormRepository formRepository;
    @Autowired
    private final StaffRepository staffRepository;
    @Autowired
    private final EmailService emailService;

    public List<ValuationRequest> getAll() {
        return valuationRequestRepository.findAll();
    }

    public List<ValuationRequest> getByConsultingStaffId(Long id) {
        return valuationRequestRepository.findAllByConsultingStaffId(id);
    }


    public void saveValuationRequestInfor(ValuationRequest valuationRequest) {
        valuationRequest.setStatus("WAITING");
        valuationRequestRepository.save(valuationRequest);
    }

    public List<ValuationRequest> getRequestsByStatus(String status) {
        return valuationRequestRepository.findByStatus(status);
    }

    public List<ValuationRequest> getDetailedWaitingRequest() {
        return valuationRequestRepository.findWaitingRequestWithDetails();
    }

    public void assignConsultingStaff(Long requestId, Long consultingStaffId) {
        ValuationRequest valuationRequest = valuationRequestRepository.findById(requestId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Valuation Request not found for this id :: " + requestId));

        if ("Waiting".equalsIgnoreCase(valuationRequest.getStatus())) {
            valuationRequest.setConsultingStaffId(consultingStaffId);
            valuationRequest.setStatus("ACCEPTED");
            Integer duration = valuationRequest.getService().getDuration();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(valuationRequest.getAppointmentDate());
            calendar.add(Calendar.HOUR_OF_DAY, duration);
            Date receiveDate = calendar.getTime();

            calendar.setTime(valuationRequest.getAppointmentDate());
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Date workingDate = calendar.getTime();
            if (receiveDate.compareTo(workingDate) > 0){
                calendar.setTime(valuationRequest.getAppointmentDate());
                calendar.add(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                receiveDate = calendar.getTime();
            }

            valuationRequest.setReceivingDate(receiveDate);
            valuationRequestRepository.save(valuationRequest);

            for (int i = 0; i < valuationRequest.getQuantity(); i++) {
                ValuationReport valuationReport = valuationReportRepository.save(new ValuationReport());
                valuationRequest.addValuationRequestDetail(new ValuationRequestDetail(valuationReport, "WAITING",0.0, true));
            }

            Staff staff = staffRepository.findById(consultingStaffId).orElseThrow(() -> new ResourceNotFoundException("No Staff Found"));

            Context context = new Context();
            context.setVariable("requestId", requestId);
            context.setVariable("staffFirstName", staff.getFirstName());
            context.setVariable("staffLastName", staff.getLastName());
            context.setVariable("receiveDate", receiveDate);
            context.setVariable("cusFirstName", valuationRequest.getCustomer().getFirst_name());
            context.setVariable("cusLastName", valuationRequest.getCustomer().getLast_name());

            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(valuationRequest.getCustomer().getEmail());
            emailDetail.setSubject("Diascur Acceptance Request Announcement");

            emailService.sendMailTemplate(emailDetail, "AcceptRequest.html", context);
        } else {
            throw new IllegalStateException("Request is not in 'Waiting' state");
        }
    }
    public ValuationRequest findById (Long id){
        return valuationRequestRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Request found"));
    }



    // Tìm những requets có consulting staff id được truyền vào
    public List<ValuationRequest> getAcceptedRequestsByConsultingStaffId(Long consultingStaffId, String status) {
        return valuationRequestRepository.findByConsultingStaffIdAndStatus(consultingStaffId, status);
    }

    public List<ValuationRequest> getRequestsByTwoStatusAndConsultingStaffId(Long consultingStaffId, String status1, String status2) {
        return valuationRequestRepository.findByConsultingStaffIdAndTwoStatus(consultingStaffId, status1, status2);
    }

    public List<ValuationRequest> getRequestsByThreeStatusAndConsultingStaffId(Long consultingStaffId, String status1, String status2, String status3) {
        return valuationRequestRepository.findByConsultingStaffIdAndThreeStatus(consultingStaffId, status1, status2, status3);
    }

    // tao bien lai


    public String cancelRequest(Long id){
        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Valuation Request found"));

        valuationRequestRepository.delete(valuationRequest);
        return "Valuation Request has been cancelled";
    }

    public List<ValuationRequest> getValuationRequestByStaffIdNotStatus(Long id, String status){
        return valuationRequestRepository.findByConsultingStaffAndNotStatus(id, status);
    }

    public List<ValuationRequest> getRequestsByTwoStatus(String status1, String status2) {
        return valuationRequestRepository.findByTwoStatus(status1, status2);
    }
    public List<ValuationRequest> getRequestsByThreeStatus(String status1, String status2, String status3) {
        return valuationRequestRepository.findByThreeStatus(status1, status2, status3);
    }

    public ValuationRequest createAppointment(Long id, String date){

        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Valuation Request Found"));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            valuationRequest.setReceivingDate(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        formRepository.save(new Form(id, "HAND-OVER", "Receive Samples and Results", new Date()));

        return valuationRequestRepository.save(valuationRequest);
    }
}
