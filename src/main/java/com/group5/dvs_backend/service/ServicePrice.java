package com.group5.dvs_backend.service;

public interface ServicePrice {
    Double findPriceByIdAndSize(Long serviceID, Double size);
}
