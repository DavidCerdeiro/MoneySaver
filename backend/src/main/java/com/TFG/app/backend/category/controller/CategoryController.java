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

import java.time.LocalDate;
import java.util.List;
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
     * @output:
     *         - 201 Created if the category is created successfully.
     *         - 400 Bad Request if the category could not be created.
     */
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddCategoryRequest categoryRequest) {

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

        if(categoryService.addCategory(category)){
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to modify a category.
     * @param categoryRequest
     * @output:
     *         - 200 OK if the category is modified successfully.
     *         - 404 Not Found if the category does not exist.
     */
    @PutMapping("/modify")
    public ResponseEntity<String> modifyCategory(@RequestBody ModifyCategoryRequest categoryRequest) {
        Category category = categoryService.getCategoryFromId(categoryRequest.getId());

        if (category != null) {
            category.setName(categoryRequest.getName());
            category.setIcon(categoryRequest.getIcon());
            
            if (categoryService.updateCategory(category)) {
                return new ResponseEntity<>(HttpStatus.OK);    
            }
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);       
    }

    /**
     * Endpoint to get all categories from a user.
     * @param token
     * @output:
     *         - 200 OK with the list of categories if successful.
     *         - 401 Unauthorized if the user is not authenticated.
     *         - 404 Not Found if the user does not exist.
     */
    @GetMapping("/all")
    public ResponseEntity<AllCategoriesFromUserResponse> AllCategoriesFromUser(@CookieValue(name = "accessToken", required = false) String token) {
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
        .map(category -> new CategoryResponse(category, spendingService.getTotalAmountMonthlyByCategory(category.getId(), currentMonth, currentYear)))
        .collect(Collectors.toList());

        return ResponseEntity.ok(new AllCategoriesFromUserResponse(response));
    }

    /**
     * Endpoint to delete a category.
     * @param id
     * @output:
     *         - 200 OK if the category is deleted successfully.
     *         - 404 Not Found if the category does not exist.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("id") Long id) {
        if (categoryService.deleteCategory(id.intValue())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to get monthly total amount spent by category.
     * @param token
     * @param month
     * @param year
     * @output:
     *         - 200 OK with the monthly spending by category if successful.
     *         - 401 Unauthorized if the user is not authenticated.
     *         - 404 Not Found if the user does not exist.
     */
    @GetMapping("/monthly")
    public ResponseEntity<CategoriesMonthlyResponse> getCategoriesMonthly(@CookieValue(name = "accessToken", required = false) String token, @RequestParam("month") int month, @RequestParam("year") int year) {
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

        List<CategoryResponse> response = categories.stream()
        .map(category -> new CategoryResponse(category, spendingService.getTotalAmountMonthlyByCategory(category.getId(), month, year)))
        .collect(Collectors.toList());

        return ResponseEntity.ok(new CategoriesMonthlyResponse(response));
    }

    @GetMapping("/compare")
    public ResponseEntity<CompareMonthsResponse> compareMonths(@CookieValue(name = "accessToken", required = false) String token, @RequestParam("month1") int month1, @RequestParam("month2") int month2, @RequestParam("year1") int year1, @RequestParam("year2") int year2) {
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

        List<CategoryResponse> totalMonth1 = categories.stream()
        .map(category -> new CategoryResponse(category, spendingService.getTotalAmountMonthlyByCategory(category.getId(), month1, year2)))
        .collect(Collectors.toList());

        List<CategoryResponse> totalMonth2 = categories.stream()
        .map(category -> new CategoryResponse(category, spendingService.getTotalAmountMonthlyByCategory(category.getId(), month2, year2)))
        .collect(Collectors.toList());

        return ResponseEntity.ok(new CompareMonthsResponse(totalMonth1, totalMonth2));
    }
}