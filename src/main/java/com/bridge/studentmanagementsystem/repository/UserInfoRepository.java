package com.bridge.studentmanagementsystem.repository;

import com.bridge.studentmanagementsystem.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserInfoRepository extends MongoRepository<UserInfo,Integer> {


     Optional<UserInfo> findByUsername(String username);
}
