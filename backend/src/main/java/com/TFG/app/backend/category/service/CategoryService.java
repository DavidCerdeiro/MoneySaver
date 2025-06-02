package com.TFG.app.backend.category.service;

import org.springframework.stereotype.Service;
import com.TFG.app.backend.category.repository.CategoryRepository;
import com.TFG.app.backend.category.entity.Category;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
}
