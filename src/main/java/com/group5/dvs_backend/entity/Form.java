package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "form")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_valuation_request")
    private Long valuationRequestId;

    @Column(name = "form_type")
    private String formType;

    @Column(name = "note")
    private String note;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "status")
    private String status;

    public Form(Long valuationRequestId, String formType, String note, LocalDate createdDate) {
        this.valuationRequestId = valuationRequestId;
        this.formType = formType;
        this.note = note;
        this.createdDate = createdDate;
    }

    public Form(Long valuationRequestId, String formType, String note, LocalDate createdDate, String status) {
        this.valuationRequestId = valuationRequestId;
        this.formType = formType;
        this.note = note;
        this.createdDate = createdDate;
        this.status = status;
    }
}
