package com.group5.dvs_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "diamond")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @Column(name = "cut")
    private String cut;

    @Column(name = "symmetry")
    private String symmetry;

    @Column(name = "fluorescence")
    private String fluorescence;

    @Column(name = "shape")
    private String shape;

    @Column(name = "measurement")
    private String measurement;

    @Column(name = "image")
    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+7")
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Override
    public String toString() {
        return "Diamond{" +
                "id=" + id +
                ", price=" + price +
                ", source='" + source + '\'' +
                ", caratWeight=" + caratWeight +
                ", clarity='" + clarity + '\'' +
                ", origin='" + origin + '\'' +
                ", polish='" + polish + '\'' +
                ", color='" + color + '\'' +
                ", cut='" + cut + '\'' +
                ", symmetry='" + symmetry + '\'' +
                ", fluorescence='" + fluorescence + '\'' +
                ", shape='" + shape + '\'' +
                ", measurement='" + measurement + '\'' +
                ", image='" + image + '\'' +
                ", date=" + date +
                '}';
    }
}