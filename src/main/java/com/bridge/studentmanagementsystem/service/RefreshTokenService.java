package com.bridge.studentmanagementsystem.service;


import com.bridge.studentmanagementsystem.model.RefreshToken;
import com.bridge.studentmanagementsystem.model.UserInfo;
import com.bridge.studentmanagementsystem.repository.RefreshTokenRepository;
import com.bridge.studentmanagementsystem.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public RefreshToken createRefreshToken(String userName){

        RefreshToken refreshToken=RefreshToken.builder()
                .userInfo(userInfoRepository.findByUsername(userName).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))

                .build();
         return refreshTokenRepository.save(refreshToken);

    }

    public Optional<RefreshToken> findByToken(String token){
          return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyRefreshToken(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0 ){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken()+"Refresh Token was expried");
        }
        return refreshToken;

    }
}
