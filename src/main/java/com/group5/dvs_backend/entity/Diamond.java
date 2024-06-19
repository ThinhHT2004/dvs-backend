package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diamond")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @Column(name = "source")
    private String source;

    @Column(name = "carat_weight")
    private double caratWeight;

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

    @Column(name = "culet")
    private String culet;

    @Column(name = "girdle")
    private String girdle;

    @Column(name = "table_attribute")
    private String tableAttribute;

    @Column(name = "depth")
    private String depth;

    @Column(name = "proportion")
    private String proportion;

}