package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public abstract class AcademicMember extends Person {

    private String memberNumber;
    private Department department;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(String personId, String firstName, String lastName,
                          String email, String phoneNumber, LocalDate dateOfBirth,
                          String memberNumber, Department department,
                          LocalDate joinDate, String academicStatus,
                          String institutionalEmail, String advisorName) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);

        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    @Override
    public void validate() throws ValidationException {

        super.validate();

        if (memberNumber == null || memberNumber.trim().isEmpty()) {
            throw new ValidationException("Member number cannot be empty");
        }

        if (department == null) {
            throw new ValidationException("Department is required");
        }

        if (joinDate == null || joinDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Join date cannot be in the future");
        }

        if (academicStatus == null || academicStatus.trim().isEmpty()) {
            throw new ValidationException("Academic status cannot be empty");
        }

        if (institutionalEmail == null
                || institutionalEmail.trim().isEmpty()
                || !institutionalEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException("Invalid institutional email");
        }

        if (advisorName == null || advisorName.trim().isEmpty()) {
            throw new ValidationException("Advisor name cannot be empty");
        }
    }

    public abstract String getAcademicCategory();

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
                + ", Member Number: " + memberNumber
                + ", Department: " + department.getDepartmentName()
                + ", Academic Status: " + academicStatus;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nMember Number: " + memberNumber
                + "\nDepartment: " + (department == null ? "Not assigned" : department.getDepartmentName())
                + "\nJoin Date: " + joinDate
                + "\nAcademic Status: " + academicStatus
                + "\nInstitutional Email: " + institutionalEmail
                + "\nAdvisor Name: " + advisorName;
    }


}