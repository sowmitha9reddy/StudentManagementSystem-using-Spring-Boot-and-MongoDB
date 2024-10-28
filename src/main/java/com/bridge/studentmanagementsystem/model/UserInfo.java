package com.bridge.studentmanagementsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection="UserInfo")
public class UserInfo {

    private int id;
    private String name;
    private String username;
    private String password;
    private String roles;





}
