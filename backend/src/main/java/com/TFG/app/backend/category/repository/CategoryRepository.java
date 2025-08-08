package com.TFG.app.backend.category.repository;

import com.TFG.app.backend.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TFG.app.backend.user.entity.User;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    // Method to find categories by user and not deleted
    List<Category> findByUserAndIsDeletedFalse(User user);

    // Method to find all categories by user
    List<Category> findByUser(User user);


    void deleteById(Long id);
    // Method to find a category by user and id
    Category findByIdAndIsDeletedFalse(Integer idCategory);
}
