package com.auca.enrollment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.student.Student;
import com.auca.course.Course;

import java.time.LocalDate;

public class Enrollment implements Identifiable, Registrable {

    private String enrollmentId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String semester;
    private String enrollmentStatus;

    public Enrollment(String enrollmentId, Student student, Course course,
                      LocalDate enrollmentDate, String semester,
                      String enrollmentStatus) {

        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public String getId() {
        return enrollmentId;
    }

    @Override
    public String getDisplayName() {
        return student.getDisplayName() + " - " + course.getCourseTitle();
    }

    @Override
    public boolean isActive() {
        return enrollmentStatus != null
                && enrollmentStatus.equalsIgnoreCase("ACTIVE");
    }

    @Override
    public void validate() throws ValidationException {

        if (enrollmentId == null || enrollmentId.trim().isEmpty()) {
            throw new ValidationException("Enrollment ID cannot be empty");
        }

        if (student == null) {
            throw new ValidationException("Student is required");
        }

        if (course == null) {
            throw new ValidationException("Course is required");
        }

        if (enrollmentDate == null || enrollmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Enrollment date cannot be in the future");
        }

        if (semester == null || semester.trim().isEmpty()) {
            throw new ValidationException("Semester cannot be empty");
        }

        if (enrollmentStatus == null
                || !(enrollmentStatus.equalsIgnoreCase("ACTIVE")
                || enrollmentStatus.equalsIgnoreCase("COMPLETED")
                || enrollmentStatus.equalsIgnoreCase("CANCELLED"))) {
            throw new ValidationException("Invalid enrollment status");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Enrollment ID: " + enrollmentId
                + ", Student: " + student.getDisplayName()
                + ", Course: " + course.getCourseTitle()
                + ", Status: " + enrollmentStatus;
    }

    @Override
    public String toString() {
        return "Enrollment ID: " + enrollmentId
                + "\nStudent: " + (student == null ? "Not assigned" : student.getDisplayName())
                + "\nCourse: " + (course == null ? "Not assigned" : course.getCourseTitle())
                + "\nEnrollment Date: " + enrollmentDate
                + "\nSemester: " + semester
                + "\nEnrollment Status: " + enrollmentStatus;
    }
}