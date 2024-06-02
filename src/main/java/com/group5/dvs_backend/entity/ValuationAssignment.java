package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_valuation_staff", nullable = false)
    private Staff valuationStaff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_valuation_request_detail", nullable = false)
    private ValuationRequestDetail valuationRequestDetail;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private float price;

    @Column(name = "note")
    private String note;

}