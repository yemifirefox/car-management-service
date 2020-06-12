package com.clane.carmanagementservice.repository;

import com.clane.carmanagementservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findByIdIn(Set<Long> ids);
}
