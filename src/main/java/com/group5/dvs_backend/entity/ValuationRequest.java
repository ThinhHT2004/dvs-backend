package com.group5.dvs_backend.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "valuation_request")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValuationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_consulting_staff")
    private Long consultingStaffId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;


    @Column(name = "appointment_date")
    private java.util.Date appointmentDate;

    @Column(name = "receiving_date")
    private java.util.Date receivingDate;

    @Column(name = "request_date")
    private java.util.Date requestDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_service", nullable = false)
    private Service service;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_valuation_request")
    private List<ValuationRequestDetail> valuationRequestDetailList;

    public void addValuationRequestDetail(ValuationRequestDetail valuationRequestDetail){
        if (valuationRequestDetailList == null){
            valuationRequestDetailList = new ArrayList<>();
        }
        valuationRequestDetailList.add(valuationRequestDetail);
    }
}
