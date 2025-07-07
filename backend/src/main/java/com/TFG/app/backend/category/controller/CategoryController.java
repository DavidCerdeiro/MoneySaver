package com.TFG.app.backend.category.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.category.dto.AddCategoryRequest;
import com.TFG.app.backend.category.entity.Category;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Endpoint to add a new category.
     * @param categoryRequest The request body containing the category details.
     * @output:
     *         - 201 Created if the category is created successfully.
     *         - 400 Bad Request if the category could not be created.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody AddCategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setIcon(categoryRequest.getIcon());
        if(categoryService.addCategory(category, categoryRequest.getIdUser()))
            return new ResponseEntity<>(HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
