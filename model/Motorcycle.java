package model;

public class Motorcycle extends Vehicle {
    public Motorcycle(String plate) {
        super(plate);
    }

    @Override
    public boolean canParkIn(ParkingSpot spot) {
        return spot.getType() == SpotType.COMPACT;
    }
}