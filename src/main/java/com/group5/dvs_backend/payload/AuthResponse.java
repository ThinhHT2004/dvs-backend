package com.group5.dvs_backend.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    String mess;
    String role;
    Long id;
    String token;
}
