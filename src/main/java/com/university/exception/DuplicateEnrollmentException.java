package com.university.exception;

public class DuplicateEnrollmentException extends RuntimeException {

    public DuplicateEnrollmentException(Long studentId, String courseCode) {
        super("Student " + studentId + " is already enrolled in course " + courseCode);
    }
}
