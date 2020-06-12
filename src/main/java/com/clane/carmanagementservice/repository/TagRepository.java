package com.clane.carmanagementservice.repository;

import com.clane.carmanagementservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByIdIn(Set<Long> ids);
}
