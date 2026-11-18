package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public class Student extends AcademicMember {

    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus,
                   String institutionalEmail, String advisorName,
                   String studentNumber, String programName,
                   LocalDate admissionDate, int studyYear,
                   String guardianPhone, boolean scholarshipStatus) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth,
                memberNumber, department, joinDate, academicStatus,
                institutionalEmail, advisorName);

        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public boolean isScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setScholarshipStatus(boolean scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    @Override
    public String getRoleDescription() {
        return "Student";
    }

    @Override
    public String getAcademicCategory() {
        return "Undergraduate Student";
    }

    @Override
    public boolean isActive() {
        return getAcademicStatus().equalsIgnoreCase("ACTIVE");
    }

    @Override
    public void validate() throws ValidationException {

        super.validate();

        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            throw new ValidationException("Student number cannot be empty");
        }

        if (programName == null || programName.trim().length() < 2
                || programName.trim().length() > 60) {
            throw new ValidationException("Program name must be between 2 and 60 characters");
        }

        if (admissionDate == null || admissionDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Admission date cannot be in the future");
        }

        if (studyYear < 1 || studyYear > 6) {
            throw new ValidationException("Study year must be between 1 and 6");
        }

        if (guardianPhone == null
                || !guardianPhone.matches("\\+?[0-9]{10,15}")) {
            throw new ValidationException("Guardian phone must contain 10 to 15 digits");
        }
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
                + ", Student Number: " + studentNumber
                + ", Program: " + programName
                + ", Study Year: " + studyYear;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nStudent Number: " + studentNumber
                + "\nProgram Name: " + programName
                + "\nAdmission Date: " + admissionDate
                + "\nStudy Year: " + studyYear
                + "\nGuardian Phone: " + guardianPhone
                + "\nScholarship Status: " + scholarshipStatus;
    }
}