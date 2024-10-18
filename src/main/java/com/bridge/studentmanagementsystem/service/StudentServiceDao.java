package com.bridge.studentmanagementsystem.service;

import com.bridge.studentmanagementsystem.dto.StudentDto;
import com.bridge.studentmanagementsystem.model.Student;

import java.util.List;

public interface StudentServiceDao {

    //Create Student: Add a new student to the database.
    //Get All Students: Retrieve all students from the database.
    //Get Student by ID: Retrieve a student by their unique identifier.
    //Update Student: Update a student’s information.
    //Delete Student: Remove a student from the database.
    //Search Students by Age: Retrieve all students whose age is within a given range.
    //Filter Students by Course: Retrieve students enrolled in a specific course.
    //MongoDB:

    public StudentDto addStudent(StudentDto studentDto);
    public List<StudentDto> getStudents();
    public StudentDto getStudent(int id);
    public StudentDto updateStudent(int id,StudentDto studentDto);
    public void deleteStudent(int id);
    public List<StudentDto> getStudentsByAge(int age1,int age2);
    public  List<StudentDto> getStudentsByCourse(String course);


}
