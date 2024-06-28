package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;
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
    private Date createdDate;

    @Column(name = "status")
    private String status;

    public Form(Long valuationRequestId, String formType, String note, Date createdDate) {
        this.valuationRequestId = valuationRequestId;
        this.formType = formType;
        this.note = note;
        this.createdDate = createdDate;
    }

    public Form(Long valuationRequestId, String formType, String note, Date createdDate, String status) {
        this.valuationRequestId = valuationRequestId;
        this.formType = formType;
        this.note = note;
        this.createdDate = createdDate;
        this.status = status;
    }
}
