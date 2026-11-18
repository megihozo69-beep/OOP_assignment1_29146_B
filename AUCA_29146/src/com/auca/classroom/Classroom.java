package com.auca.classroom;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class Classroom implements Identifiable, Registrable {

    private String classroomId;
    private String buildingName;
    private String roomNumber;
    private int capacity;
    private int floorNumber;
    private boolean available;

    public Classroom(String classroomId, String buildingName, String roomNumber,
                     int capacity, int floorNumber, boolean available) {

        this.classroomId = classroomId;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.floorNumber = floorNumber;
        this.available = available;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String getId() {
        return classroomId;
    }

    @Override
    public String getDisplayName() {
        return buildingName + " - " + roomNumber;
    }

    @Override
    public boolean isActive() {
        return available;
    }

    @Override
    public void validate() throws ValidationException {

        if (classroomId == null || classroomId.trim().isEmpty()) {
            throw new ValidationException("Classroom ID cannot be empty");
        }

        if (buildingName == null || buildingName.trim().isEmpty()) {
            throw new ValidationException("Building name cannot be empty");
        }

        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new ValidationException("Room number cannot be empty");
        }

        if (capacity <= 0) {
            throw new ValidationException("Classroom capacity must be greater than zero");
        }

        if (floorNumber < 0) {
            throw new ValidationException("Floor number cannot be negative");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Classroom ID: " + classroomId
                + ", Building: " + buildingName
                + ", Room: " + roomNumber;
    }

    @Override
    public String toString() {
        return "Classroom ID: " + classroomId
                + "\nBuilding Name: " + buildingName
                + "\nRoom Number: " + roomNumber
                + "\nCapacity: " + capacity
                + "\nFloor Number: " + floorNumber
                + "\nAvailable: " + available;
    }
}