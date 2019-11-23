package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.domain.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, String> {
    Optional<Region> findByRegionName(String regionName);

    Optional<Region> findByRegionCode(String regionCode);
}
