package com.TFG.app.backend.category.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;
import com.TFG.app.backend.category.dto.AddCategoryRequest;
import com.TFG.app.backend.category.dto.ModifyCategoryRequest;
import com.TFG.app.backend.category.dto.AllCategoriesFromUserResponse;
import com.TFG.app.backend.category.dto.CategoriesMonthlyResponse;
import com.TFG.app.backend.category.dto.CategoryResponse;
import com.TFG.app.backend.category.dto.CompareMonthsResponse;
import com.TFG.app.backend.category.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final SpendingService spendingService;
    private final JwtService jwtService;
    private final UserService userService;
    public CategoryController(CategoryService categoryService, SpendingService spendingService, JwtService jwtService, UserService userService) {
        this.categoryService = categoryService;
        this.spendingService = spendingService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Endpoint to add a new category.
     * @param categoryRequest The request body containing the category details.
     * @return:
     * - 201: Created CategoryResponse
     * - 400: Bad Request if the category could not be created
     * - 401: Unauthorized if token is missing or invalid
     * - 404: User not found in the database
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> postCategory(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddCategoryRequest categoryRequest) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setIcon(categoryRequest.getIcon());
        category.setUser(user);

        if(!categoryService.existsByNameAndUser(category.getName(), user)){
            categoryService.addCategory(category);
            return new ResponseEntity<>(new CategoryResponse(category), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Endpoint to modify a category.
     * @param categoryRequest
     * @return:
     * - 200: OK CategoryResponse if the category is modified successfully
     * - 400: Bad Request if the category could not be modified
     * - 401: Unauthorized if token is missing or invalid
     * - 404: User not found in the database or if the category does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> putCategory(@CookieValue(name = "accessToken", required = false) String token, @PathVariable("id") Integer id, @RequestBody ModifyCategoryRequest categoryRequest) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Category category = categoryService.getCategoryFromId(id);

        if (category != null) {
            category.setName(categoryRequest.getName());
            category.setIcon(categoryRequest.getIcon());
            if (categoryService.updateCategory(category)) {
                return new ResponseEntity<>(new CategoryResponse(category), HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    /**
     * Endpoint to get all categories from a user.
     * @param token
     * @return:
     * - 200: OK with the list of categories if successful
     * - 401: Unauthorized if token is missing or invalid
     * - 404: User not found in the database
     */
    @GetMapping
    public ResponseEntity<AllCategoriesFromUserResponse> getCategories(@CookieValue(name = "accessToken", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Category> categories = categoryService.getAllCategoriesFromUser(user.getId());

        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        List<CategoryResponse> response = categories.stream()
        .map(category -> new CategoryResponse(category, spendingService.getTotalAmountMonthlyByCategory(category.getId(), currentMonth, currentYear)))//Calculate the total spending for the category in the current month
        .collect(Collectors.toList());

        return ResponseEntity.ok(new AllCategoriesFromUserResponse(response));
    }

    /**
     * Endpoint to delete a category.
     * @param id
     * @return:
     * - 204: No Content if the category is deleted successfully
     * - 401: Unauthorized if token is missing or invalid
     * - 404: User not found or if the category does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@CookieValue(name = "accessToken", required = false) String token, @PathVariable("id") Long id) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (categoryService.deleteCategory(id.intValue())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to get monthly total amount spent by category.
     * @param token
     * @param month
     * @param year
     * @return:
     * - 200: OK with the monthly spending by category if successful
     * - 401: Unauthorized if token is missing or invalid
     * - 404: User not found in the database
     */
    @GetMapping("/{year}/{month}")
    public ResponseEntity<CategoriesMonthlyResponse> getCategoriesMonthly(@CookieValue(name = "accessToken", required = false) String token, @PathVariable("year") int year, @PathVariable("month") int month) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        List<Category> categories = categoryService.getAllCategoriesNotDeletedFromUser(user.getId(), LocalDate.of(year, month, 1));

        List<CategoryResponse> response = categories.stream()
        .map(category -> new CategoryResponse(category, spendingService.getTotalAmountMonthlyByCategory(category.getId(), month, year)))
        .collect(Collectors.toList());

        return ResponseEntity.ok(new CategoriesMonthlyResponse(response));
    }

    /**
     * Endpoint to compare monthly spending by category.
     * @param token
     * @param month1
     * @param month2
     * @param year1
     * @param year2
     * @return:
     * - 200: OK with the comparison monthly spending by category if successful
     * - 401: Unauthorized if token is missing or invalid
     * - 404: User not found in the database
     */
    @GetMapping("comparison")
    public ResponseEntity<CompareMonthsResponse> compareMonths(
            @CookieValue(name = "accessToken", required = false) String token,
            @RequestParam("month1") int month1,
            @RequestParam("month2") int month2,
            @RequestParam("year1") int year1,
            @RequestParam("year2") int year2) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = jwtService.getEmailFromToken(token);
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Category> categories1 = categoryService.getAllCategoriesNotDeletedFromUser(user.getId(), LocalDate.of(year1, month1, 1));
        List<Category> categories2 = categoryService.getAllCategoriesNotDeletedFromUser(user.getId(), LocalDate.of(year2, month2, 1));

        // Combine categories of both months
        List<Category> allCategories = new ArrayList<>();
        allCategories.addAll(categories1);
        allCategories.addAll(categories2);
        allCategories = allCategories.stream()
                .distinct()
                .collect(Collectors.toList());

        // Categories map by ID
        Map<Integer, Category> map1 = categories1.stream()
            .collect(Collectors.toMap(Category::getId, c -> c));
        Map<Integer, Category> map2 = categories2.stream()
            .collect(Collectors.toMap(Category::getId, c -> c));

        // Build CategoryResponse lists for each month
        List<CategoryResponse> totalMonth1 = allCategories.stream()
            .map(cat -> {
                Category c = map1.get(cat.getId());
                BigDecimal total = (c != null) ? spendingService.getTotalAmountMonthlyByCategory(c.getId(), month1, year1) : BigDecimal.ZERO;
                return new CategoryResponse(cat, total);
            })
            .collect(Collectors.toList());

        List<CategoryResponse> totalMonth2 = allCategories.stream()
            .map(cat -> {
                Category c = map2.get(cat.getId());
                BigDecimal total = (c != null) ? spendingService.getTotalAmountMonthlyByCategory(c.getId(), month2, year2) : BigDecimal.ZERO;
                return new CategoryResponse(cat, total);
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(new CompareMonthsResponse(totalMonth1, totalMonth2));
    }

}