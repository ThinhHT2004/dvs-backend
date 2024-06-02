package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.ValuationReport;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.repository.ValuationReportRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@AllArgsConstructor
public class ValuationReportServiceImpl implements ValuationReportService{

    @Autowired
    private ValuationReportRepository valuationReportRepository;
    @Autowired
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Override
    public ValuationReport updateValuationReport(Long id,ValuationReport updatedValuationReport) {
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceAccessException("No Valuation Request Detail with id " + id + " found"));
        ValuationReport valuationReport = valuationReportRepository
                .findById(valuationRequestDetail.getValuationReport().getId())
                .orElseThrow(() -> new ResourceAccessException("No Valuation Report with id " +
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

        valuationRequestDetail.setStatus("FILLED");
        valuationRequestDetailRepository.save(valuationRequestDetail);

        return valuationReportRepository.save(valuationReport);
    }
}
