package com.TFG.app.backend.category.controller;


import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.category.entity.Category;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public void create(@RequestBody Category category) {
        categoryService.createCategory(category);
    }
}
