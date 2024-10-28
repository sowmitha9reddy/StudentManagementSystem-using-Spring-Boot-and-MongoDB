package com.bridge.studentmanagementsystem.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JWTResponse {

    private String token;
    private String accessToken;
}
