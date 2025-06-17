package com.TFG.app.backend.infraestructure.config;

import com.TFG.app.backend.infraestructure.one_time_password.service.One_Time_PasswordService;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import jakarta.annotation.PostConstruct;

/*
 * Service to handle the cleanup of expired One-Time Passwords (OTPs).
 * It performs an initial cleanup at application startup and schedules
 * a periodic cleanup every 10 minutes.
 */
@Service
public class OTPCleanupService {
    private final One_Time_PasswordService oneTimePasswordService;

    public OTPCleanupService(One_Time_PasswordService oneTimePasswordService) {
        this.oneTimePasswordService = oneTimePasswordService;
    }

    /**
     * Initial cleanup of expired OTPs when the application starts.
     */
    @PostConstruct
    public void initialCleanup() {
        oneTimePasswordService.deleteExpiredOTPs();
    }

    /**
     * Scheduled task to clean up expired OTPs every 10 minutes.
     */
    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void cleanupExpiredOTPs() {
        oneTimePasswordService.deleteExpiredOTPs();
    }
}
