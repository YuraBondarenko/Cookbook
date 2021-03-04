package com.example.cookbook.repository;

import com.example.cookbook.model.RecipeHistory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeHistoryRepository extends JpaRepository<RecipeHistory, Long> {
    List<RecipeHistory> findAllByName(String name, Pageable pageable);
}
