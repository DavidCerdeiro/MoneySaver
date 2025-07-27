package com.TFG.app.backend.category.repository;

import com.TFG.app.backend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.user.entity.User;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    // Method to find categories by user
    List<Category> findByUser(User user);

    // Method to find a category by user and id
    List<Category> findByUserAndId(User user, Integer idCategory);
}
