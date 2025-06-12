package com.TFG.app.backend.infraestructure.otp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.infraestructure.otp.entity.One_Time_Password;
import java.util.Optional;

public interface One_Time_PasswordRepository extends JpaRepository<One_Time_Password, Integer>{
    Optional<One_Time_Password> findByEmail(String email);

    
}
