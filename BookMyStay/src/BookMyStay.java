import java.io.*;
import java.util.*;

/**
 * Model class for a Reservation that supports Serialization.
 */
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookingId;
    private String guestName;

    public Reservation(String bookingId, String guestName) {
        this.bookingId = bookingId;
        this.guestName = guestName;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId + " | Guest: " + guestName;
    }
}

/**
 * Manages Hotel State (Inventory and Bookings) with Persistence logic.
 */
class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;
    private int availableRooms;
    private List<Reservation> bookingHistory;

    public HotelState(int initialRooms) {
        this.availableRooms = initialRooms;
        this.bookingHistory = new ArrayList<>();
    }

    public void addBooking(String id, String name) {
        if (availableRooms > 0) {
            bookingHistory.add(new Reservation(id, name));
            availableRooms--;
            System.out.println("Success: Booking added for " + name);
        } else {
            System.out.println("Error: No rooms available for " + name);
        }
    }

    public void displayStatus() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Rooms Available: " + availableRooms);
        System.out.println("History: " + bookingHistory);
        System.out.println("----------------------------\n");
    }
}

/**
 * Entry point: BookMyStay
 * Demonstrates saving and loading system state from a file.
 */
public class BookMyStay {
    private static final String FILE_NAME = "hotel_data.ser";

    public static void main(String[] args) {
        System.out.println("Book My Stay App - Use Case 12: Data Persistence & Recovery");

        HotelState state;

        // 1. Attempt System Recovery (Deserialization)
        File file = new File(FILE_NAME);
        if (file.exists()) {
            System.out.println("Restoring system state from persistent storage...");
            state = loadState();
        } else {
            System.out.println("No previous state found. Initializing new system...");
            state = new HotelState(10); // Start with 10 rooms
            state.addBooking("BK001", "Alice");
            state.addBooking("BK002", "Bob");
        }

        // Display state after loading/initialization
        state.displayStatus();

        // 2. Simulate new activity
        System.out.println("Processing new booking...");
        state.addBooking("BK003", "Charlie");

        // 3. Data Persistence (Serialization) before shutdown
        System.out.println("Saving state to " + FILE_NAME + " before shutdown...");
        saveState(state);

        System.out.println("System shutdown complete. Run the program again to see recovery!");
    }

    // Helper: Save object to file
    private static void saveState(HotelState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    // Helper: Load object from file
    private static HotelState loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (HotelState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed: " + e.getMessage());
            return new HotelState(10);
        }
    }
}
