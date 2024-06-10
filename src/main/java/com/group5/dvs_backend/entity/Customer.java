package com.group5.dvs_backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "dob",nullable = false)
    private Date dob;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<ValuationRequest> valuation_request;

}
