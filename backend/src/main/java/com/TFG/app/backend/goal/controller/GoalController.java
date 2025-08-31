package com.TFG.app.backend.goal.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.TFG.app.backend.category.entity.Category;
import com.TFG.app.backend.category.service.CategoryService;
import com.TFG.app.backend.goal.dto.AddGoalRequest;
import com.TFG.app.backend.goal.dto.EditGoalRequest;
import com.TFG.app.backend.goal.dto.GoalResponse;
import com.TFG.app.backend.goal.entity.Goal;
import com.TFG.app.backend.goal.service.GoalService;
import com.TFG.app.backend.infraestructure.config.JwtService;
import com.TFG.app.backend.spending.service.SpendingService;
import com.TFG.app.backend.user.entity.User;
import com.TFG.app.backend.user.service.UserService;


@RestController
@RequestMapping("/api/goals")
public class GoalController {
    private final GoalService goalService;
    private final JwtService jwtService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final SpendingService spendingService;

    public GoalController(GoalService goalService, JwtService jwtService, UserService userService, CategoryService categoryService, SpendingService spendingService) {
        this.goalService = goalService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.spendingService = spendingService;
    }

    @PostMapping("/add")
    public ResponseEntity<Goal> addGoal(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddGoalRequest request) {
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

        Category category = categoryService.getCategoryFromId(request.getIdCategory());
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Goal createdGoal = goalService.addGoal(request.getName(), request.getTargetAmount(), category);
        return ResponseEntity.ok(createdGoal);
    }

    @GetMapping("/allFromUser")
    public ResponseEntity<List<GoalResponse>> getAllGoalsFromUser(@CookieValue(name = "accessToken", required = false) String token, 
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
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

        List<Goal> goals = goalService.getAllGoalsFromCategories(user.getId(), LocalDate.of(year, month, 1));

        List<GoalResponse> goalResponses = goals.stream().map(goal -> {
            GoalResponse response = new GoalResponse();
            response.setId(goal.getId());
            response.setName(goal.getName());
            response.setTargetAmount(goal.getTargetAmount());
            response.setIdCategory(goal.getCategory().getId());
            response.setNameCategory(goal.getCategory().getName());
            response.setAmountCategory(spendingService.getTotalAmountMonthlyByCategory(goal.getCategory().getId(), month, year));
            response.setPercent(goalService.calculatePercent(goal.getTargetAmount(), spendingService.getTotalAmountMonthlyByCategory(goal.getCategory().getId(), month, year)));
            return response;
        }).toList();
        return ResponseEntity.ok(goalResponses);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGoal(@CookieValue(name = "accessToken", required = false) String token, @RequestParam("id") Integer id) {
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

        boolean deleted = goalService.deleteGoal(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<GoalResponse> editGoal(@CookieValue(name = "accessToken", required = false) String token, @RequestBody EditGoalRequest request) {
        Goal goal = goalService.findById(request.getId());
        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        goal.setName(request.getName());
        goal.setTargetAmount(request.getTargetAmount());
        goal.setCategory(categoryService.getCategoryFromId(request.getIdCategory()));

        Goal updatedGoal = goalService.updateGoal(goal);
        GoalResponse response = new GoalResponse();
        response.setId(updatedGoal.getId());
        response.setName(updatedGoal.getName());
        response.setTargetAmount(updatedGoal.getTargetAmount());
        response.setIdCategory(updatedGoal.getCategory().getId());
        response.setNameCategory(updatedGoal.getCategory().getName());

        return ResponseEntity.ok(response);
    }
}
