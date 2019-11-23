package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByUserId(String userName);
}
