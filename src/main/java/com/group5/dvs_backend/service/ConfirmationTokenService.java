package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.ConfirmationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
    void setConfirmedAt(String token);
}
