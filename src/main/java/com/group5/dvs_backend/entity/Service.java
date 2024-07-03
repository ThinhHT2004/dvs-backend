package com.group5.dvs_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "active")
    private boolean active;

    @Column(name = "updated_date")
    private Date updatedDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn( name = "id_service")
    List<ServicePrice> servicePriceList;


    @PostPersist
    public void setServicePricesAfterSave() {
        for (ServicePrice price : servicePriceList) {
            price.setServiceId(id);
        }
    }

}
