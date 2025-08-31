package com.TFG.app.backend.category.repository;

import com.TFG.app.backend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TFG.app.backend.user.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    @Query(value = """
    SELECT * 
    FROM category c
    WHERE c."Id_User" = :userId
      AND (
            (c."DeletedAt" IS NULL AND c."CreatedAt" <= :month)
         OR (c."DeletedAt" IS NOT NULL AND c."CreatedAt" <= :month AND c."DeletedAt" > :month)
      )
    """, nativeQuery = true)
List<Category> findActivesByUserAndMonth(@Param("userId") Integer userId,
                                        @Param("month") LocalDate month);

    // Method to find all categories by user
    List<Category> findByUser(User user);

    void deleteById(Long id);

    Category findByIdAndDeletedAtIsNull(Integer idCategory);

    List<Category> findByUserAndDeletedAtIsNull(User user);

    boolean existsByNameAndUserAndDeletedAtIsNull(String name, User user);
}
