package com.TFG.app.backend.goal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TFG.app.backend.goal.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

}
