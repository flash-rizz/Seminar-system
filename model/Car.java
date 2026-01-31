package model;

public class Car extends Vehicle {
    public Car(String plate) {
        super(plate);
    }

    @Override
    public boolean canParkIn(ParkingSpot spot) {
        return spot.getType() == SpotType.COMPACT
        || spot.getType() == SpotType.REGULAR;    }
}