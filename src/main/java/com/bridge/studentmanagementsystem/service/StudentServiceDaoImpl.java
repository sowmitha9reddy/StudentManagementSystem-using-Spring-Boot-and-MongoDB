package com.bridge.studentmanagementsystem.service;

import com.bridge.studentmanagementsystem.dto.StudentDto;
import com.bridge.studentmanagementsystem.globalexceptionhandler.StudentNotFoundException;
import com.bridge.studentmanagementsystem.model.Student;
import com.bridge.studentmanagementsystem.repository.StudentRepository;
import com.bridge.studentmanagementsystem.util.EmailService;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceDaoImpl implements StudentServiceDao {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    MongoClient client;

    @Autowired
    MongoConverter converter;

    @Autowired
    private EmailService emailService;

    @Override
    public StudentDto addStudent(StudentDto studentDto) {

        Student student = new Student(studentDto);
        emailService.sendEmail(studentDto.getEmail(), "Your Registration is successfull",student.toString());
        return mapToStudentDto(studentRepository.save(student));


    }

    private StudentDto mapToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setEmail(student.getEmail());
        studentDto.setAddress(student.getAddress());
        studentDto.setCourses(student.getCourses());
        return studentDto;
    }

    @Override
    public List<StudentDto> getStudents() {
         return studentRepository.findAll().
                stream()
                 .map(student -> mapToStudentDto(student))
                 .collect(Collectors.toList());

    }

    @Override
    public StudentDto getStudent(int id) {
        return mapToStudentDto(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student Id Not Found")));
    }

    @Override
    public StudentDto updateStudent(int id,StudentDto studentDto) {
     Student student=studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student Id Not Found"));
     student.setName(studentDto.getName());
     student.setEmail(studentDto.getEmail());
     student.setAddress(studentDto.getAddress());
     student.setCourses(studentDto.getCourses());
        emailService.sendEmail(studentDto.getEmail(), "Your Details updated  successfully",student.toString());
     return mapToStudentDto(studentRepository.save(student));


    }

    @Override
    public void deleteStudent(int id) {
         studentRepository.deleteById(id);

    }

    @Override
    public List<StudentDto> getStudentsByAge(int minAge, int maxAge) {
        final List<StudentDto> students = new ArrayList<>();

        MongoDatabase database = client.getDatabase("studentmanagement");
        MongoCollection<Document> collection = database.getCollection("student");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("age", new Document("$gte", minAge).append("$lte", maxAge))
                ),
                new Document("$sort",
                        new Document("age", 1L)
        )));


        result.forEach(doc -> students.add(converter.read(StudentDto.class,doc)));

        return students;
    }

    @Override
    public List<StudentDto> getStudentsByCourse(String course) {

        final List<StudentDto> students = new ArrayList<>();

        MongoDatabase database = client.getDatabase("studentmanagement");
        MongoCollection<Document> collection = database.getCollection("student");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("courses", course)
                )
        ));


        result.forEach(doc -> students.add(converter.read(StudentDto.class,doc)));

        return students;


    }
}
