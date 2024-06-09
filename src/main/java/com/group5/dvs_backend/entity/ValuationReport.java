package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "valuation_report")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValuationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "final_price")
    private Double finalPrice;
    @Column(name = "created_date")
    private Date createdDate;


    @Column(name = "carat_weight")
    private Double caratWeight;
    @Column(name = "clarity")
    private String clarity;
    @Column(name = "origin")
    private String origin;
    @Column(name = "polish")
    private String polish;
    @Column(name = "color")
    private String color;
    @Column(name = "symmetry")
    private String symmetry;
    @Column(name = "fluorescence")
    private String fluorescence;
    @Column(name = "shape")
    private String shape;
    @Column(name = "measurement")
    private String measurement;
    @Column(name = "proportion")
    private String proportion;
}
