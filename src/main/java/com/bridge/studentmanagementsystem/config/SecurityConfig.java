package com.bridge.studentmanagementsystem.config;

import com.bridge.studentmanagementsystem.filter.JwtAuthFilter;
import com.bridge.studentmanagementsystem.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {




    private JwtAuthFilter authFilter;

    @Autowired
    public SecurityConfig(@Lazy  JwtAuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin= User.withUsername("Sowmitha").password(passwordEncoder.encode("dsr1")).roles("ADMIN").build();
//        UserDetails user= User.withUsername("Aadhya").password(passwordEncoder.encode("gar1")).roles("USER").build();
//       return  new InMemoryUserDetailsManager(admin,user);
        return  new UserInfoUserDetailsService();

    }

   //Spring Security
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return  httpSecurity.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/student/welcome", "userinfo/addUser", "userinfo/authenticate").permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/student/**")
//                .authenticated()
//                .and().formLogin()
//                .and().build();
//
//
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/student/welcome", "userinfo/addUser", "userinfo/authenticate","userinfo/RefreshToken").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/student/**")
                .authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }



       @Bean
       public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();

       }



}
