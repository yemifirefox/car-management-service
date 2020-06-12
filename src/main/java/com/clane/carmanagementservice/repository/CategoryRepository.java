package com.clane.carmanagementservice.repository;

import com.clane.carmanagementservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /*@Query("select c from Category c WHERE c.car.engineNumber = :engineNumber")
    Category findByCarEngineNumber(@Param("engineNumber") String engineNumber);

    @Query("select c from Category c WHERE c.name LIKE '%:name%'")
    Category search(@Param("name") String carName);*/

    Set<Category> findByIdIn(Set<Long> ids);
}
