package model;

import java.time.LocalDateTime;

public abstract class Vehicle {
    protected String licensePlate;
    protected LocalDateTime entryTime;

    public Vehicle(String plate) {
        this.licensePlate = plate;
        this.entryTime = LocalDateTime.now();
    }

    public String getLicensePlate() { return licensePlate; }
    public LocalDateTime getEntryTime() { return entryTime; }

    public abstract boolean canParkIn(ParkingSpot spot);
}
