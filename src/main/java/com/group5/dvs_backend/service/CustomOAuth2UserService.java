//package com.group5.dvs_backend.service;
//
//import com.group5.dvs_backend.entity.Customer;
//import com.group5.dvs_backend.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User user = super.loadUser(userRequest);
//        Map<String, Object> attributes = user.getAttributes();
//
//        String email = (String) attributes.get("email");
//        String firstName = (String) attributes.get("given_name");
//        String lastName = (String) attributes.get("family_name");
//
//        Customer customer = customerRepository.findByEmail(email)
//                .orElse(new Customer()); // Tạo mới nếu không tìm thấy
//
//        customer.setEmail(email);
//        customer.setFirst_name(firstName);
//        customer.setLast_name(lastName);
//
//        customerRepository.save(customer); // Lưu hoặc cập nhật thông tin người dùng
//
//        System.out.println("User: " + user.toString());
//        return user;
//    }
//}