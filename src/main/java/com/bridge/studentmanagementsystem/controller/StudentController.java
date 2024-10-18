package com.bridge.studentmanagementsystem.controller;

import com.bridge.studentmanagementsystem.dto.StudentDto;

import com.bridge.studentmanagementsystem.service.StudentServiceDaoImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/student")
public class StudentController {

     @Autowired
     private StudentServiceDaoImpl studentServiceDaoImpl;

    @PostMapping(value="/addstd")
    public StudentDto addStudent(@Valid @RequestBody StudentDto studentDto) {
        return studentServiceDaoImpl.addStudent(studentDto);
    }

    @GetMapping(value="/getStud/{id}")
    public StudentDto getStudent(@PathVariable int id) {
        return  studentServiceDaoImpl.getStudent(id);
    }

    @GetMapping(value="/getStuds")
    public List<StudentDto> getStudents() {
        return  studentServiceDaoImpl.getStudents();
    }

    @GetMapping(value="/getStuds/{age1}/{age2}")
    public List<StudentDto> getStudentsByAge(@PathVariable int age1, @PathVariable int age2) {
        return  studentServiceDaoImpl.getStudentsByAge(age1, age2);

    }

    @GetMapping(value="/getStuds/{course}")
    public List<StudentDto> getStudentsByCourse(@PathVariable String course) {
        return  studentServiceDaoImpl.getStudentsByCourse(course);

    }

    @DeleteMapping(value="/delStud/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentServiceDaoImpl.deleteStudent(id);
    }

    @PostMapping(value = "/updateStud/{id}")
    public StudentDto updateStudent(@PathVariable int id,@Valid @RequestBody StudentDto studentDto) {
        return studentServiceDaoImpl.updateStudent(id,studentDto);

    }
}
