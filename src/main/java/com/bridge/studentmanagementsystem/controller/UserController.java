package com.bridge.studentmanagementsystem.controller;

import com.bridge.studentmanagementsystem.dto.AuthRequest;
import com.bridge.studentmanagementsystem.dto.JWTResponse;
import com.bridge.studentmanagementsystem.dto.RefreshTokenRequest;
import com.bridge.studentmanagementsystem.model.RefreshToken;
import com.bridge.studentmanagementsystem.model.UserInfo;
import com.bridge.studentmanagementsystem.service.JWTService;
import com.bridge.studentmanagementsystem.service.RefreshTokenService;
import com.bridge.studentmanagementsystem.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="userinfo")
public class UserController
{
    @Autowired
    private UserInfoUserDetailsService userInfoUserDetailsService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value="/addUser")
    public UserInfo addUser(@RequestBody UserInfo userInfo){
        return userInfoUserDetailsService.addUserInfo(userInfo);

    }


    @PostMapping("/authenticate")
    public JWTResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest){
           Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
           if(authentication.isAuthenticated()) {
              RefreshToken refreshToken= refreshTokenService.createRefreshToken(authRequest.getUsername());

              return JWTResponse.builder().accessToken(jwtService.generateToken(authRequest.getUsername()))
                       .token(refreshToken.getToken()).build();

           }
           else {
               throw new UsernameNotFoundException("Invalid username or password");
           }
    }


    @PostMapping("/RefreshToken")
    public JWTResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
          return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                  .map(refreshTokenService::verifyRefreshToken)
                  .map(RefreshToken::getUserInfo)
                  .map(userInfo -> {
                      String accessToken=jwtService.generateToken(userInfo.getName());
                      return  JWTResponse.builder()
                              .accessToken(accessToken)
                              .token(refreshTokenRequest.getToken())
                              .build();
                  })
                  .orElseThrow(() -> new RuntimeException("Refresh token not found in database "));


    }
}


