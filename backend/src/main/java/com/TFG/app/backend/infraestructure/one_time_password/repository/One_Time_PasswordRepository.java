package com.TFG.app.backend.infraestructure.one_time_password.repository;

import com.TFG.app.backend.infraestructure.one_time_password.entity.One_Time_Password;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

public interface One_Time_PasswordRepository extends JpaRepository<One_Time_Password, Integer>{
    Optional<One_Time_Password> findByEmail(String email);
    List<One_Time_Password> findByExpirationBefore(Timestamp dateTime);
}
