package com.TFG.app.backend.category.service;

import org.springframework.stereotype.Service;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.user.entity.User;

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
}
