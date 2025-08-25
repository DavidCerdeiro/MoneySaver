package com.TFG.app.backend.goal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TFG.app.backend.goal.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {


    @Query(value = """
        SELECT g.* 
        FROM goal g
        JOIN category c ON g."Id_Category" = c."Id"
        WHERE c."Id_User" = :userId
        """, nativeQuery = true
    )
    List<Goal> findAllByUserId(@Param("userId") int userId);
}
