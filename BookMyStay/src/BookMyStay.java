import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing a confirmed reservation.
 */
class Reservation {
    private String bookingId;
    private String guestName;
    private String roomType;

    public Reservation(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return String.format("ID: %-6s | Guest: %-10s | Room: %-10s", bookingId, guestName, roomType);
    }
}

/**
 * Manages the historical record of confirmed bookings.
 */
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Adds a confirmed reservation to history in insertion order
    public void addRecord(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieves all stored reservations for review
    public List<Reservation> getAllRecords() {
        // Returns a copy to protect internal data integrity
        return new ArrayList<>(history);
    }
}

/**
 * Service to generate summaries from booking history.
 */
class BookingReportService {
    public void generateSummary(BookingHistory history) {
        List<Reservation> records = history.getAllRecords();
        System.out.println("--- Booking Summary Report ---");
        System.out.println("Total Bookings Confirmed: " + records.size());

        for (Reservation res : records) {
            System.out.println(res);
        }
        System.out.println("------------------------------");
    }
}

/**
 * Entry point for Use Case 8: Booking History & Reporting.
 * This class demonstrates historical tracking and reporting logic.
 */
public class BookMyStay {
    public static void main(String[] args) {
        // Welcome Message
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 1.0\n");

        // Initialize Services
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating the flow: Bookings are confirmed and added to history
        history.addRecord(new Reservation("BK001", "Alice", "Deluxe"));
        history.addRecord(new Reservation("BK002", "Bob", "Standard"));
        history.addRecord(new Reservation("BK003", "Charlie", "Suite"));

        // Admin requests the report
        reportService.generateSummary(history);
    }
}
