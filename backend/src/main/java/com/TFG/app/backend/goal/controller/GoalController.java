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

    /**
     * Endpoint to create a new goal
     * @param token
     * @param request
     * @return:
     *  - 201: Created GoalResponse if the goal is successfully created
     *  - 400: Bad Request if the request is invalid
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: Not Found if the user or category is not found
     */
    @PostMapping
    public ResponseEntity<GoalResponse> postGoal(@CookieValue(name = "accessToken", required = false) String token, @RequestBody AddGoalRequest request) {
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
        GoalResponse response = new GoalResponse();
        response.setId(createdGoal.getId());
        response.setName(createdGoal.getName());
        response.setTargetAmount(createdGoal.getTargetAmount());
        response.setIdCategory(createdGoal.getCategory().getId());
        response.setNameCategory(createdGoal.getCategory().getName());
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to get all goals for a specific month and year
     * @param token
     * @param month
     * @param year
     * @return:
     *  - 200: OK with a list of GoalResponse if goals are found
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: Not Found if the user is not found
     */
    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<GoalResponse>> getGoals(@CookieValue(name = "accessToken", required = false) String token, 
            @PathVariable("month") int month,
            @PathVariable("year") int year) {
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

    /**
     * Endpoint to delete an existing goal
     * @param token
     * @param id
     * @return:
     *  - 204: No Content if the goal is successfully deleted
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: Not Found if the user or goal is not found
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGoal(@CookieValue(name = "accessToken", required = false) String token, @PathVariable("id") Integer id) {
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

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint to update an existing goal
     * @param token
     * @param id
     * @param request
     * @return:
     *  - 200: OK GoalResponse if the goal is successfully updated
     *  - 400: Bad Request if the request is invalid
     *  - 401: Unauthorized if token is missing or invalid
     *  - 404: Not Found if the user or goal is not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<GoalResponse> putGoal(@CookieValue(name = "accessToken", required = false) String token, @PathVariable("id") Integer id, @RequestBody EditGoalRequest request) {
        Goal goal = goalService.findById(id);
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
