package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "valuation_request_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValuationRequestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_valuation_request", nullable = false)
    private ValuationRequest valuationRequest;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_valuation_report", nullable = false)
    private ValuationReport valuationReport;

    @Column(name = "status")
    private String status;

    @Column(name = "size")
    private Double size;

    @Column(name = "is_diamond")
    private boolean isDiamond;

    public ValuationRequestDetail(ValuationRequest valuationRequest, ValuationReport valuationReport, String status, Double size, boolean isDiamond) {
        this.valuationRequest = valuationRequest;
        this.valuationReport = valuationReport;
        this.status = status;
        this.size = size;
        this.isDiamond = isDiamond;
    }
}
