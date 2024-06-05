package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "valuation_assignment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ValuationAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "id_valuation_staff", nullable = false)
    private Staff valuationStaff;

    @ManyToOne
    @JoinColumn(name = "id_valuation_request_detail")
    private ValuationRequestDetail valuationRequestDetail;


    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private float price;

    @Column(name = "note")
    private String note;

    public ValuationAssignment(Staff valuationStaff, String status, float price, String note) {
        this.valuationStaff = valuationStaff;
        this.status = status;
        this.price = price;
        this.note = note;
    }

    public ValuationAssignment(Staff valuationStaff, String status) {
        this.valuationStaff = valuationStaff;
        this.status = status;
    }
}