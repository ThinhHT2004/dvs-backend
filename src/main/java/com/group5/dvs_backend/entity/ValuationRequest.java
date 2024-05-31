package com.group5.dvs_backend.entity;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "valuation_request")
public class ValuationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "id_customer", nullable = false)
    private long customerId;

    @Column(name = "id_consulting_staff")
    private long consultingStaffId;

    @Column(name = "id_service", nullable = false)
    private long serviceId;

    @Column(name = "appointment_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date appointmentDate;

    @Column(name = "receiving_date")
    @Temporal(TemporalType.DATE)
    private Date receivingDate;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "request_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date requestDate;

    public enum Status {
        WAITING, APPROVED, APPRAISING, COMPLETED
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getConsultingStaffId() {
        return consultingStaffId;
    }

    public void setConsultingStaffId(long consultingStaffId) {
        this.consultingStaffId = consultingStaffId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

}
