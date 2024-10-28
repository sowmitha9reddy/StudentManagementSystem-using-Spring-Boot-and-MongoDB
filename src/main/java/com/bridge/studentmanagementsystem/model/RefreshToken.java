package com.bridge.studentmanagementsystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "RefreshToken")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder

public class RefreshToken {

    @Id
    private String id;
     private String token;
     private Instant expiryDate;

    @DBRef
     private UserInfo userInfo;
}
