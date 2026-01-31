package manager;

import model.*;
import java.util.*;

public class ParkingLotManager {

    private static ParkingLotManager instance;

    private List<Floor> floors;
    private Map<String, ParkingSpot> activeTickets;

    private ParkingLotManager() {
        floors = new ArrayList<>();
        activeTickets = new HashMap<>();
    }

    public static ParkingLotManager getInstance() {
        if (instance == null) {
            instance = new ParkingLotManager();
        }
        return instance;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public List<ParkingSpot> findAvailableSpots(Vehicle v) {
        List<ParkingSpot> available = new ArrayList<>();
        for (Floor f : floors) {
            available.addAll(f.getAvailableSpots(v));
        }
        return available;
    }

    public String parkVehicle(Vehicle v, ParkingSpot spot) {
        spot.park(v);
        String ticketId = "T-" + v.getLicensePlate() + "-" + System.currentTimeMillis();
        activeTickets.put(v.getLicensePlate(), spot);
        return ticketId;
    }

    public ParkingSpot exitVehicle(String licensePlate) {
        ParkingSpot spot = activeTickets.remove(licensePlate);
        if (spot != null) {
            spot.free();
        }
        return spot;
    }

    public Vehicle findVehicle(String licensePlate) {
        ParkingSpot spot = activeTickets.get(licensePlate);
        if (spot != null) return spot.getCurrentVehicle();
        return null;
    }
}
