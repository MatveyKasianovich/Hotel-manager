package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<AmenityEntity, Long> {
    Optional<AmenityEntity> findByNameIgnoreCase(String name);
}
