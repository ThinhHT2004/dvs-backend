package com.group5.dvs_backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_valuation_report", nullable = false)
    private ValuationReport valuationReport;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_valuation_request_detail")
    private List<ValuationAssignment> assignmentList;

    @Column(name = "id_valuation_request")
    private Long valuationRequestId;


    @Column(name = "status")
    private String status;

    @Column(name = "size")
    private Double size;

    @Column(name = "is_diamond")
    private boolean isDiamond;

    public ValuationRequestDetail( ValuationReport valuationReport, String status, Double size, boolean isDiamond) {
        this.valuationReport = valuationReport;
        this.status = status;
        this.size = size;
        this.isDiamond = isDiamond;
    }
}
