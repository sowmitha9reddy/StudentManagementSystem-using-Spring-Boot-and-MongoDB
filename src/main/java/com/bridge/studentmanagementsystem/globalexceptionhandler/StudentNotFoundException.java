package com.bridge.studentmanagementsystem.globalexceptionhandler;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);

    }
}
