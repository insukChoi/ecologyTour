package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.domain.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {
    Optional<List<Tour>> findByExplainContaining(String keyword);

    Optional<List<Tour>> findByDetailExplainContaining(String keyword);

    Optional<List<Tour>> findByRegionsContaining(String regions);
}
