package com.group5.dvs_backend.entity;

import java.sql.Date;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_service", nullable = false)
    private Service service;

}
