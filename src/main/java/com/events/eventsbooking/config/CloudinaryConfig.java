package com.events.eventsbooking.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private String apiKey = "527958838357892";
    private String apiSecret = "DPtqa54soRyDwtbROI6yRub63yY";
    private String cloudName = "dtzxheei2";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> map = new HashMap<>();
        map.put("api_key", apiKey);
        map.put("api_secret", apiSecret);
        map.put("cloud_name", cloudName);
        return new Cloudinary(map);

    }
}
