package com.TFG.app.backend.category.service;

import org.springframework.stereotype.Service;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;
import org.springframework.transaction.annotation.Transactional;
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
            return categoryRepository.findByUserAndIsDeletedFalse(user);
        }
        return null;
    }

    public List<Category> getAllCategoriesNotDeletedFromUser(Integer idUser) {
        User user = userService.getUserById(idUser);
        if (user != null) {
            return categoryRepository.findByUser(user);
        }
        return null;
    }
    public Category getCategoryFromId(Integer idCategory) {
        return categoryRepository.findByIdAndIsDeletedFalse(idCategory);
    }

    public boolean updateCategory(Category category) {
        System.out.println("Updating category: " + category.getName());
        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory != null;
    }

    @Transactional
    public boolean deleteCategory(Integer id) {
        try {
            Category category = categoryRepository.findByIdAndIsDeletedFalse(id);
            
                category.setDeleted(true);
                categoryRepository.save(category);

                return true;
        } catch (Exception e) {
            System.out.println("Error deleting category: " + e.getMessage());
            return false;
        }
    }
}