package com.auca.department;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.teacher.Teacher;

public class Department implements Identifiable, Registrable {

    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(String departmentId, String departmentName,
                      String departmentCode, String officeLocation,
                      String contactEmail, Teacher headTeacher) {

        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Teacher getHeadTeacher() {
        return headTeacher;
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    @Override
    public String getId() {
        return departmentId;
    }

    @Override
    public String getDisplayName() {
        return departmentName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (departmentId == null || departmentId.trim().isEmpty()) {
            throw new ValidationException("Department ID cannot be empty");
        }

        if (departmentName == null || departmentName.trim().length() < 2
                || departmentName.trim().length() > 60) {
            throw new ValidationException("Department name must be between 2 and 60 characters");
        }

        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            throw new ValidationException("Department code cannot be empty");
        }

        if (officeLocation == null || officeLocation.trim().isEmpty()) {
            throw new ValidationException("Office location cannot be empty");
        }

        if (contactEmail == null || contactEmail.trim().isEmpty()
                || contactEmail.length() > MAX_EMAIL_LENGTH
                || !contactEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidationException("Invalid department contact email");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Department ID: " + departmentId
                + ", Name: " + departmentName
                + ", Code: " + departmentCode;
    }

    @Override
    public String toString() {
        return "Department ID: " + departmentId
                + "\nDepartment Name: " + departmentName
                + "\nDepartment Code: " + departmentCode
                + "\nOffice Location: " + officeLocation
                + "\nContact Email: " + contactEmail
                + "\nHead Teacher: " + (headTeacher == null
                ? "Not assigned" : headTeacher.getDisplayName());
    }
}