package com.bridge.studentmanagementsystem.repository;

import com.bridge.studentmanagementsystem.model.RefreshToken;
import com.bridge.studentmanagementsystem.model.Student;
import jakarta.validation.ReportAsSingleViolation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
