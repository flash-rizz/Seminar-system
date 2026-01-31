package model;

public class ParkingSpot {
    private String spotId;
    private SpotType type;
    private boolean occupied;
    private Vehicle currentVehicle;

    public ParkingSpot(String spotId, SpotType type) {
        this.spotId = spotId;
        this.type = type;
        this.occupied = false;
        this.currentVehicle = null;
    }

    public String getSpotId() { return spotId; }
    public SpotType getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public Vehicle getCurrentVehicle() { return currentVehicle; }

    public boolean canFit(Vehicle v) {
        return !occupied && v.canParkIn(this);
    }

    public void park(Vehicle v) {
        this.currentVehicle = v;
        this.occupied = true;
    }

    public void free() {
        this.currentVehicle = null;
        this.occupied = false;
    }
}
