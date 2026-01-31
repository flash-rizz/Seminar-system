package model;

public class Truck extends Vehicle {
    public Truck(String plate) {
        super(plate);
    }

    @Override
    public boolean canParkIn(ParkingSpot spot) {
        return spot.getType() == SpotType.REGULAR;
    }
}
