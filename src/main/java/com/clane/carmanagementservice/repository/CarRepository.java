package com.clane.carmanagementservice.repository;

import com.clane.carmanagementservice.model.Car;
import com.clane.carmanagementservice.model.dto.CarDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByNameLike(String carName);

    @Query("select c.name from Car c WHERE c.name LIKE '%:name%'")
    String findName(@Param("name") String carName);

//    @Query("select c from Car c WHERE c.name LIKE '%:query%' or c.description LIKE '%:query%'")

    Optional<Car> findByEngineNumber(String engineNumber);

    Car findByNameLikeOrDescriptionLikeOrCategoriesIsLikeOrTagsIsLike(String name, String desc, String categories, String tags);



    @Query("SELECT new com.clane.carmanagementservice.model.dto.CarDto(c) FROM Car c " +
            "WHERE  Lower(c.name) LIKE %:query% " +
            "OR LOWER(c.description) LIKE %:query% "+
            "OR ((SELECT t FROM Tag t WHERE LOWER(t.name) LIKE %:query%) MEMBER OF c.tags) " +
            "OR ((SELECT cat FROM Category cat WHERE LOWER(cat.name) LIKE %:query%) MEMBER OF c.categories)"
    )
    List<CarDto> search(String query);

    //@Query("select c from Car c WHERE c.name LIKE '%:query%'")
    //List<Car> findByNameLike(@Param("query")String query);


}
