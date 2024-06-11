package com.group5.dvs_backend.config;

import java.util.HashMap;
import java.util.Map;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "dktusfxw3");
        config.put("api_key", "978696418784732");
        config.put("api_secret", "CkvmwGWViFeYjqtINgQn9MuotH8");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
