package hotelgui;

import java.time.LocalDate;

// Room class
class Room {
    private int roomNumber;
    private String roomType;
    private int capacity;
    private double price;
    private boolean occupied;

    // Constructor
    public Room(int roomNumber, String roomType, int capacity, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.capacity = capacity;
        this.price = price;
        this.occupied = false;
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOccupied() {
        return occupied;
    }

    // Setters
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}

// Guest class
class Guest {
    private String name;
    private String contactNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int roomNumber;

    // Constructor
    public Guest(String name, String contactNumber, LocalDate checkInDate, LocalDate checkOutDate, int roomNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomNumber = roomNumber;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}