package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.ValuationReport;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ValuationReportRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@AllArgsConstructor
public class ValuationReportServiceImpl implements ValuationReportService {
        @Autowired
        private ValuationRequestRepository valuationRequestRepository;
        @Autowired
        private ValuationReportRepository valuationReportRepository;
        @Autowired
        private ValuationRequestDetailRepository valuationRequestDetailRepository;

        @Override
        public ValuationReport updateValuationReport(Long vrId, Long id, ValuationReport updatedValuationReport) {
                ValuationRequest valuationRequest = valuationRequestRepository
                                .findById(vrId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "No Valuation Request with id" + vrId + " found"));
                ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "No Valuation Request Detail with id " + id + " found"));
                ValuationReport valuationReport = valuationReportRepository
                                .findById(valuationRequestDetail.getValuationReport().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("No Valuation Report with id " +
                                                valuationRequestDetail.getValuationReport().getId() + " found"));

                valuationReport.setColor(updatedValuationReport.getColor());
                valuationReport.setClarity(updatedValuationReport.getClarity());
                valuationReport.setFluorescence(updatedValuationReport.getFluorescence());
                valuationReport.setPolish(updatedValuationReport.getPolish());
                valuationReport.setOrigin(updatedValuationReport.getOrigin());
                valuationReport.setCaratWeight(updatedValuationReport.getCaratWeight());
                valuationReport.setShape(updatedValuationReport.getShape());
                valuationReport.setMeasurement(updatedValuationReport.getMeasurement());
                valuationReport.setProportion(updatedValuationReport.getProportion());
                valuationReport.setSymmetry(updatedValuationReport.getSymmetry());
                valuationReport.setGirdle(updatedValuationReport.getGirdle());
                valuationReport.setTable(updatedValuationReport.getTable());
                valuationReport.setDepth(updatedValuationReport.getDepth());
                valuationReport.setCulet(updatedValuationReport.getCulet());
                valuationReport.setCut(updatedValuationReport.getCut());
                valuationReport.setImage(updatedValuationReport.getImage());

                valuationRequestDetail.setStatus("FILLED");
                valuationRequest.setStatus("PROCESSING");
                valuationRequestDetailRepository.save(valuationRequestDetail);
                valuationRequestRepository.save(valuationRequest);
                return valuationReportRepository.save(valuationReport);
        }

        @Override
        public ValuationReport findById(Long id) {
                return valuationReportRepository
                                .findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("No Valuation Report found"));
        }

        @Override
        public ValuationReport save(ValuationReport valuationReport) {
                return valuationReportRepository.save(valuationReport);
        }
        @Override
        public ValuationReport findDiamondByValuationReportId(Long valuationReportId) {
                return valuationReportRepository.findById(valuationReportId).orElse(null);
        }

}
