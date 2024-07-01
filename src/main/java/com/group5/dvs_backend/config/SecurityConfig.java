package com.group5.dvs_backend.config;

//import com.group5.dvs_backend.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                        .requestMatchers("/api/auth/**", "/oauth2/**").permitAll()
//                        .anyRequest().authenticated())
//                // Cấu hình đăng nhập OAuth2.
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .userInfoEndpoint(userInfoEndpoint -> // Cấu hình để lấy thông tin người dùng.
//                                        userInfoEndpoint
//                                                .oidcUserService(new OidcUserService())// Sử dụng OidcUserService để lấy thông tin người
//                                                // dùng từ OpenID Connect.
//                                                .userService(customOAuth2UserService)// Sử dụng customOAuth2UserService để xử lý thông
//                                // tin người dùng.
//                        ));
//        return http.build();
//    }

}
