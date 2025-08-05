package com.TFG.app.backend.category.service;

import org.springframework.stereotype.Service;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public boolean addCategory(Category category, Integer idUser) {
        User user = userService.getUserById(idUser);
        category.setUser(user);
        Category result = categoryRepository.save(category);
        return result != null;
    }

    public List<Category> getAllCategoriesFromUser(Integer idUser) {
        User user = userService.getUserById(idUser);
        if (user != null) {
            return categoryRepository.findByUser(user);
        }
        return null;
    }

    public Category getCategoryFromUserAndId(Integer idUser, Integer idCategory) {
        User user = userService.getUserById(idUser);
        if (user != null) {
            System.out.println("Fetching category with id: " + idCategory + " for user with id: " + idUser);
            return categoryRepository.findByUserAndId(user, idCategory);
        }
        System.out.println("User not found with id: " + idUser);
        return null;
    }

    public boolean updateCategory(Category category) {
        System.out.println("Updating category: " + category.getName());
        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory != null;
    }

    public void updateCategoryTotalSpendingMonthly() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            category.setTotalSpending(BigDecimal.ZERO);
            categoryRepository.save(category);
        }
    }

    public void addPeriodicSpendingToCategory(Integer categoryId, BigDecimal periodicSpendingAmount) {}
}