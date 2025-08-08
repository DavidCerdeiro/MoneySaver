package com.TFG.app.backend.spending.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.TFG.app.backend.spending.entity.Spending;
import java.util.List;

public interface SpendingRepository extends JpaRepository<Spending, Integer> {

    List<Spending> findAllByCategoryId(int categoryId);

    @Query(value = """
        SELECT * FROM spending
        WHERE "Id_User" = :userId
            AND EXTRACT(MONTH FROM "Date") = :month
            AND EXTRACT(YEAR FROM "Date") = :year
        """, nativeQuery = true
    )
    List<Spending> findAllByUserIdAndMonthAndYear(
    @Param("userId") int userId,
    @Param("month") int month,
    @Param("year") int year
    );

}
