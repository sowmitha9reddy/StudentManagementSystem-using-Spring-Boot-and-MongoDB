package com.bridge.studentmanagementsystem.filter;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.bridge.studentmanagementsystem.service.JWTService;
import com.bridge.studentmanagementsystem.service.UserInfoUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;




    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthFilter(@Lazy  UserInfoUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String  authHeader=request.getHeader("Authorization");
        String token=null;
        String username = null;
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyMSIsImlhdCI6MTcyOTY2MjM0OSwiZXhwIjoxNzI5NjY0MTQ5fQ.kDSrH_RjO3tgVM2aIVsi6IEypqNbMtNnl1aTBl_O8O8
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
           token=authHeader.substring(7);
            username = jwtService.extractUsername(token);
            System.out.println(username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println(userDetails.getUsername());
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
