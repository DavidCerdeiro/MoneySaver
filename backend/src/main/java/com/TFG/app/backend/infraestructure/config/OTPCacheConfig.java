package com.TFG.app.backend.infraestructure.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OTPCacheConfig {
    // Cache to store OTPs
    @Bean
    public Cache<String, String> otpCache() {
        // Key will be the user's email (String)
        // Value will be the OTP code (String)
        return CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES) // OTPs expire after 5 minutes
                .build();
    }
}