package com.example.certtesting.repo;

import com.example.certtesting.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepo extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}
