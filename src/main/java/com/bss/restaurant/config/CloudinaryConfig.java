package com.bss.restaurant.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getCloudinary() {
        Map map = new HashMap();
        map.put("cloud_name", "dneeqhmmc");
        map.put("api_key", "636192734924766");
        map.put("api_secret", "8xDmy7AncNvYc8fN288iEDjy42g");
        map.put("secure", true);
        return new Cloudinary(map);
    }
}
