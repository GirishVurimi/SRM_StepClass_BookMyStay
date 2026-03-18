import java.util.*;

/**
 * Domain model for optional Add-On Services.
 */
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

/**
 * Domain model representing a guest's reservation.
 */
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room ID: " + roomId + " (" + roomType + ")";
    }
}

/**
 * Manages the One-to-Many relationship between Reservations and Services.
 */
class AddOnServiceManager {
    // Maps Room ID to a List of selected Services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String roomId, Service service) {
        reservationServices.computeIfAbsent(roomId, k -> new ArrayList<>()).add(service);
    }

    public double calculateTotalServiceCost(String roomId) {
        List<Service> services = reservationServices.get(roomId);
        if (services == null) return 0.0;
        return services.stream().mapToDouble(Service::getCost).sum();
    }

    public void displayServices(String roomId) {
        List<Service> services = reservationServices.get(roomId);
        if (services != null && !services.isEmpty()) {
            System.out.println("   Add-ons: " + services);
            System.out.println("   Total Add-on Cost: $" + calculateTotalServiceCost(roomId));
        } else {
            System.out.println("   Add-ons: None");
        }
    }
}

/**
 * Main Application Class - Version 7.0
 */
public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 7.0\n");

        // 1. Setup Services and Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();
        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Treatment", 100.0);
        Service wifi = new Service("Premium WiFi", 15.0);

        // 2. Simulate a Confirmed Reservation (from Use Case 6)
        Reservation res1 = new Reservation("Alice", "Suite Room");
        res1.setRoomId("SU-101");

        Reservation res2 = new Reservation("Bob", "Single Room");
        res2.setRoomId("SR-102");

        // 3. Guests select Add-On Services
        System.out.println("--- Selecting Add-On Services ---");

        // Alice selects Breakfast and Spa
        serviceManager.addService(res1.getRoomId(), breakfast);
        serviceManager.addService(res1.getRoomId(), spa);

        // Bob selects only WiFi
        serviceManager.addService(res2.getRoomId(), wifi);

        // 4. Display Final Booking Summaries
        displayBookingSummary(res1, serviceManager);
        displayBookingSummary(res2, serviceManager);
    }

    private static void displayBookingSummary(Reservation res, AddOnServiceManager manager) {
        System.out.println("\nFinal Booking Details:");
        System.out.println(res);
        manager.displayServices(res.getRoomId());
        System.out.println("-------------------------------------");
    }
}
