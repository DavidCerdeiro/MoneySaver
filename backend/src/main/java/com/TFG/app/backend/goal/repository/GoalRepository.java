package com.TFG.app.backend.goal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.goal.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    @Query(value = """
    SELECT g."Id", g."Id_Category", g."Name", g."TargetAmount",
           g."CreatedAt", g."DeletedAt"
    FROM goal g
    JOIN category c ON g."Id_Category" = c."Id"
    WHERE c."Id_User" = :userId
      AND (
            (g."DeletedAt" IS NULL AND g."CreatedAt" <= :month)
         OR (g."DeletedAt" IS NOT NULL AND g."CreatedAt" <= :month AND g."DeletedAt" > :month)
      )
    """, nativeQuery = true)
List<Goal> findActivesByUserAndMonth(@Param("userId") Integer userId,
                                     @Param("month") LocalDate month);

    List<Goal> findByCategoryAndDeletedAtIsNull(Category category);
}
