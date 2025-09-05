package com.TFG.app.backend.spending.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TFG.app.backend.spending.entity.Spending;

import java.util.List;
import com.TFG.app.backend.category.entity.Category;


public interface SpendingRepository extends JpaRepository<Spending, Integer> {

    @Query(value = """
        SELECT * FROM spending
        WHERE "Id_Category" = :categoryId
          AND EXTRACT(YEAR FROM "Date") = :year
          AND EXTRACT(MONTH FROM "Date") = :month
        """, nativeQuery = true)
    List<Spending> getAllByCategoryAndMonth(
            @Param("categoryId") int categoryId,
            @Param("month") int month,
            @Param("year") int year
    );

    @Query(value = """
        SELECT s.* 
        FROM spending s
        JOIN category c ON s."Id_Category" = c."Id"
        WHERE c."Id_User" = :userId
        AND EXTRACT(MONTH FROM s."Date") = :month
        AND EXTRACT(YEAR FROM s."Date") = :year
        """, nativeQuery = true
    )
    List<Spending> findAllByUserIdAndMonthAndYear(
        @Param("userId") int userId,
        @Param("month") int month,
        @Param("year") int year
    );
    @Query(value = """
        SELECT s.*
        FROM spending s
        JOIN category c ON s."Id_Category" = c."Id"
        WHERE s."Id_Establishment" = :idEstablishment
        AND c."DeletedAt" IS NULL
        """, nativeQuery = true)
    List<Spending> findByEstablishmentAndCategoryNotDeleted(@Param("idEstablishment") Integer idEstablishment);


    List<Spending> findByCategory(Category category);
}
