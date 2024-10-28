package com.bridge.studentmanagementsystem.dto;

import com.bridge.studentmanagementsystem.model.Address;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {


    @NotNull
    private int id;

    @NotEmpty
    @Pattern(regexp = "^[A-Z][a-z]{2,}( [A-Z][a-z]{2,})*$",message="Invalid Name")
    private String name;


    @NotNull
    @Min(value=10,message="Age Should be more than 10")
    @Max(value=60,message="age should be less than 60")
    @Positive
    private int age;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9#$%*]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$",message = "Invalid Email Address")
    private String email;

    @NotEmpty
    private String[] courses;

    private List<Address> address;
}
