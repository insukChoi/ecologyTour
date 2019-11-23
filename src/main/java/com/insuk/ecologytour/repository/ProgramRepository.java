package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.domain.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, String> {
    Optional<Program> findByProgramName(String programName);
}
