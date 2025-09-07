package com.TFG.app.backend.category.service;

import org.springframework.stereotype.Service;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.goal.entity.Goal;
import com.TFG.app.backend.goal.service.GoalService;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final GoalService goalService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService, GoalService goalService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.goalService = goalService;
    }

    public void addCategory(Category category) {
        Category result = categoryRepository.save(category);
        if (result == null) {
            throw new RuntimeException("Error al guardar la categoría");
        }
    }

    public boolean existsByNameAndUser(String name, User user) {
        return categoryRepository.existsByNameAndUserAndDeletedAtIsNull(name, user);
    }
    public List<Category> getAllCategoriesFromUser(Integer idUser) {
        User user = userService.getUserById(idUser);
        if (user != null) {
            return categoryRepository.findByUserAndDeletedAtIsNull(user);
        }
        return null;
    }

    public List<Category> getAllCategoriesNotDeletedFromUser(Integer idUser, LocalDate date) {
        User user = userService.getUserById(idUser);
        if (user != null) {
            return categoryRepository.findActivesByUserAndMonth(user.getId(), date);
        }
        return null;
    }

    public Category getCategoryFromId(Integer idCategory) {
        return categoryRepository.findByIdAndDeletedAtIsNull(idCategory);
    }

    public boolean updateCategory(Category category) {
        Category updatedCategory = categoryRepository.save(category);

        return updatedCategory != null;
    }

    public boolean deleteCategory(Integer id) {
            Category category = categoryRepository.findByIdAndDeletedAtIsNull(id);
            List<Goal> goals = goalService.getAllGoalsFromCategory(category);
            for (Goal goal : goals) {
                goal.setDeletedAt(LocalDate.now().withDayOfMonth(1));        
            }

            category.setDeletedAt(LocalDate.now().withDayOfMonth(1));
            if( categoryRepository.save(category) != null) {
                return true;
            }

            return false;
    }
}