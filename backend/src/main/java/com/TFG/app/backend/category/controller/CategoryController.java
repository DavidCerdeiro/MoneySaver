package com.TFG.app.backend.category.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.category.service.CategoryService;

import com.TFG.app.backend.category.dto.AddCategoryRequest;
import com.TFG.app.backend.category.dto.ModifyCategoryRequest;
import com.TFG.app.backend.category.dto.AllCategoriesFromUserResponse;
import com.TFG.app.backend.category.dto.CategoryResponse;
import com.TFG.app.backend.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;
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

    @PutMapping("/modify")
    public ResponseEntity<String> modifyCategory(@RequestBody ModifyCategoryRequest categoryRequest) {
        Category category = categoryService.getCategoryFromUserAndId(categoryRequest.getIdUser(), categoryRequest.getId());
        if (category != null) {
            category.setName(categoryRequest.getName());
            category.setIcon(categoryRequest.getIcon());
            if (categoryService.updateCategory(category)) {
                System.out.println("Category updated successfully");
                return new ResponseEntity<>(HttpStatus.OK);
                
            }
        }
        System.out.println("Category no updated successfully");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    @GetMapping("/all")
    public ResponseEntity<AllCategoriesFromUserResponse> AllCategoriesFromUser(@RequestParam("idUser") Long idUser) {
        List<Category> categories = categoryService.getAllCategoriesFromUser(idUser.intValue());
        List<CategoryResponse> response = categories.stream()
        .map(CategoryResponse::new)
        .collect(Collectors.toList());

        return ResponseEntity.ok(new AllCategoriesFromUserResponse(response));
    }
}
