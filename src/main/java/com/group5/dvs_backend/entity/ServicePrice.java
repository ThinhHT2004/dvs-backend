package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_price_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_service")
    private Long serviceId;

    @Column(name = "size_from")
    private Double sizeFrom;

    @Column(name = "size_to")
    private Double sizeTo;

    @Column(name = "init_price")
    private Double initPrice;

    @Column(name = "price_unit")
    private Double priceUnit;
}
