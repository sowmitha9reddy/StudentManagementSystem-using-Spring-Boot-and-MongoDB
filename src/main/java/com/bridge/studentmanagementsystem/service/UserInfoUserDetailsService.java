package com.bridge.studentmanagementsystem.service;

import com.bridge.studentmanagementsystem.model.UserInfo;
import com.bridge.studentmanagementsystem.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;
@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
     private UserInfoRepository userInfoRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo=userInfoRepository.findByUsername(username);
        System.out.println(userInfo+"----------------------------");

       return  userInfo.
               map(UserInfoUserDetails :: new )
                .orElseThrow(() -> new UsernameNotFoundException("user not found "));

    }

    public UserInfo addUserInfo(UserInfo userInfo) {
         userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);




    }
}
