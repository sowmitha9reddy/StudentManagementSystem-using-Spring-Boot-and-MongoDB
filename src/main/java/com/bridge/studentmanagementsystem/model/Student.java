package com.bridge.studentmanagementsystem.model;


import com.bridge.studentmanagementsystem.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Document(collection = "student")

public class Student {
//    id (Unique identifier)
//    name (String)
//    age (Integer)
//    email (String)
//    courses (List of Strings) - Example: ["Math", "Science"]
//    address (Object containing street, city, state, and postal code)


    private int id;
    private String name;
    private int age;
    private String email;
    private String[] courses;
    private List<Address> address;


    public Student(StudentDto studentDto) {
        this.id = studentDto.getId();
        this.name = studentDto.getName();
        this.age = studentDto.getAge();
        this.email = studentDto.getEmail();
        this.courses = studentDto.getCourses();
        this.address = studentDto.getAddress();


    }
}
