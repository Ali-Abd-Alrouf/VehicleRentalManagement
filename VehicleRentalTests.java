import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class VehicleRentalTests {
    private static int passed = 0;
    private static int failed = 0;

    interface TestAction { void run() throws Exception; }

    private static void test(int number, String description, TestAction action) {
        try {
            action.run();
            passed++;
            System.out.printf("Scenario %02d: PASS - %s%n", number, description);
        } catch (Throwable e) {
            failed++;
            System.out.printf("Scenario %02d: FAIL - %s%n", number, description);
            System.out.println("  Reason: " + e);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void equal(double actual, double expected, String message) {
        check(Math.abs(actual - expected) < 0.000001,
              message + " (expected " + expected + ", got " + actual + ")");
    }

    private static void rejects(TestAction action, String message) throws Exception {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException expected) {
            return;
        }
        throw new AssertionError(message + ": expected an exception");
    }

    private static String capture(TestAction action) throws Exception {
        PrintStream old = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bytes, true, "UTF-8"));
            action.run();
        } finally {
            System.setOut(old);
        }
        return bytes.toString("UTF-8");
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Rental> rentals(RentalSystem system) throws Exception {
        Field f = RentalSystem.class.getDeclaredField("rentals");
        f.setAccessible(true);
        return (ArrayList<Rental>) f.get(system);
    }

    private static Car car(String id) {
        return new Car(id, "Toyota", "Corolla", 2022, 50, 5, "Automatic");
    }
    private static Motorcycle motorcycle(String id) {
        return new Motorcycle(id, "Honda", "CBR", 2023, 40, 600, true);
    }
    private static Truck truck(String id) {
        return new Truck(id, "Volvo", "FH16", 2024, 120, 8.5);
    }
    private static Customer customer(String id) {
        return new Customer(id, "Test Customer", "0599999999", "test@example.com", "DL-" + id);
    }

    public static void main(String[] args) {
        test(1, "Add car, motorcycle and truck", () -> {
            RentalSystem s = new RentalSystem();
            s.addVehicle(car("V101"));
            s.addVehicle(motorcycle("M101"));
            s.addVehicle(truck("T101"));
            check(s.findVehicleById("V101") instanceof Car, "Car missing");
            check(s.findVehicleById("M101") instanceof Motorcycle, "Motorcycle missing");
            check(s.findVehicleById("T101") instanceof Truck, "Truck missing");
        });

        test(2, "Reject duplicate vehicle ID", () -> {
            RentalSystem s = new RentalSystem();
            s.addVehicle(car("V101"));
            rejects(() -> s.addVehicle(truck("V101")), "Duplicate ID accepted");
            check(s.findVehicleById("V101") instanceof Car, "Original vehicle replaced");
        });

        test(3, "Register customer", () -> {
            RentalSystem s = new RentalSystem();
            s.addCustomer(customer("C101"));
            check(s.findCustomerById("C101") != null, "Customer not found");
        });

        test(4, "Rent available car", () -> {
            RentalSystem s = new RentalSystem();
            Car c = car("V101");
            s.addVehicle(c);
            s.addCustomer(customer("C101"));
            s.rentVehicle("R101", "C101", "V101", 3, false);
            check(rentals(s).size() == 1, "Rental not stored");
            check(rentals(s).get(0).isActive(), "Rental not active");
            check(!c.isAvailable(), "Rented car still available");
            equal(rentals(s).get(0).getTotalCost(), 150, "Wrong car cost");
        });

        test(5, "Reject second rental of same car", () -> {
            RentalSystem s = new RentalSystem();
            s.addVehicle(car("V101"));
            s.addCustomer(customer("C101"));
            s.addCustomer(customer("C102"));
            s.rentVehicle("R101", "C101", "V101", 2, false);
            rejects(() -> s.rentVehicle("R102", "C102", "V101", 1, false), "Double booking accepted");
            check(rentals(s).size() == 1, "Rejected rental was stored");
        });

        test(6, "Motorcycle helmet fee $5 per day", () -> {
            RentalSystem s = new RentalSystem();
            s.addVehicle(motorcycle("M101"));
            s.addCustomer(customer("C101"));
            s.rentVehicle("R201", "C101", "M101", 3, true);
            equal(rentals(s).get(0).getTotalCost(), 135, "Helmet fee wrong");
        });

        test(7, "Truck mandatory $50 fee once", () -> {
            RentalSystem s = new RentalSystem();
            s.addVehicle(truck("T101"));
            s.addCustomer(customer("C101"));
            s.rentVehicle("R301", "C101", "T101", 3, false);
            equal(rentals(s).get(0).getTotalCost(), 410, "Truck fee wrong");
        });

        test(8, "Reject zero and negative rental days", () -> {
            RentalSystem s = new RentalSystem();
            Car c = car("V101");
            s.addVehicle(c);
            s.addCustomer(customer("C101"));
            rejects(() -> s.rentVehicle("R0", "C101", "V101", 0, false), "Zero days accepted");
            rejects(() -> s.rentVehicle("RN", "C101", "V101", -2, false), "Negative days accepted");
            check(c.isAvailable() && rentals(s).isEmpty(), "Invalid rental changed state");
        });

        test(9, "Return active rental", () -> {
            RentalSystem s = new RentalSystem();
            Car c = car("V101");
            s.addVehicle(c);
            s.addCustomer(customer("C101"));
            s.rentVehicle("R101", "C101", "V101", 2, false);
            s.returnVehicle("R101");
            check(!rentals(s).get(0).isActive(), "Rental still active");
            check(c.isAvailable(), "Returned car not available");
        });

        test(10, "Reject returning same rental twice", () -> {
            RentalSystem s = new RentalSystem();
            s.addVehicle(car("V101"));
            s.addCustomer(customer("C101"));
            s.rentVehicle("R101", "C101", "V101", 1, false);
            s.returnVehicle("R101");
            rejects(() -> s.returnVehicle("R101"), "Second return accepted");
        });

        test(11, "Customer rental history filters correctly", () -> {
            RentalSystem s = new RentalSystem();
            s.addCustomer(customer("C101"));
            s.addCustomer(customer("C102"));
            s.addVehicle(car("V101"));
            s.addVehicle(motorcycle("M101"));
            s.addVehicle(truck("T101"));
            s.rentVehicle("R101", "C101", "V101", 1, false);
            s.returnVehicle("R101");
            s.rentVehicle("R102", "C101", "M101", 1, true);
            s.rentVehicle("R103", "C102", "T101", 1, false);
            String history = capture(() -> s.displayCustomerRentalHistory("C101"));
            check(history.contains("R101") && history.contains("R102"), "Customer rentals missing");
            check(history.contains("Completed") && history.contains("Active"), "Rental statuses missing");
            check(!history.contains("R103"), "Other customer's rental leaked");
        });

        test(12, "Revenue includes completed rentals only", () -> {
            RentalSystem s = new RentalSystem();
            s.addCustomer(customer("C101"));
            s.addVehicle(car("V101"));
            s.addVehicle(motorcycle("M101"));
            s.rentVehicle("R101", "C101", "V101", 2, false); // 100
            s.rentVehicle("R102", "C101", "M101", 3, true); // 135
            equal(s.calculateTotalRevenue(), 0, "Active rentals counted");
            s.returnVehicle("R101");
            equal(s.calculateTotalRevenue(), 100, "Completed rental not counted correctly");
            s.returnVehicle("R102");
            equal(s.calculateTotalRevenue(), 235, "Revenue total incorrect");
        });

        test(13, "Flag available car for maintenance", () -> {
            RentalSystem s = new RentalSystem();
            Car c = car("V101");
            s.addVehicle(c);
            s.markVehicleForMaintenance("V101");
            check(c.isMaintenanceRequired(), "Maintenance flag not set");
            check(c.isAvailable(), "Maintenance unexpectedly changed availability");
        });

        test(14, "Reject rental of flagged vehicle", () -> {
            RentalSystem s = new RentalSystem();
            Car c = car("V101");
            s.addVehicle(c);
            s.addCustomer(customer("C101"));
            s.markVehicleForMaintenance("V101");
            rejects(() -> s.rentVehicle("R101", "C101", "V101", 2, false), "Flagged car rented");
            check(rentals(s).isEmpty(), "Rejected rental stored");
        });

        test(15, "Maintain idle truck and rent afterward", () -> {
            RentalSystem s = new RentalSystem();
            Truck t = truck("T101");
            s.addVehicle(t);
            s.addCustomer(customer("C101"));
            s.markVehicleForMaintenance("T101");
            s.performVehicleMaintenance("T101");
            check(!t.isMaintenanceRequired(), "Truck flag not cleared");
            s.rentVehicle("R301", "C101", "T101", 2, false);
            check(!t.isAvailable() && rentals(s).get(0).isActive(), "Truck not rentable after maintenance");
        });

        test(16, "Reject maintenance on rented motorcycle", () -> {
            RentalSystem s = new RentalSystem();
            Motorcycle m = motorcycle("M101");
            s.addVehicle(m);
            s.addCustomer(customer("C101"));
            s.rentVehicle("R201", "C101", "M101", 1, false);
            rejects(() -> s.performVehicleMaintenance("M101"), "Maintenance on rented motorcycle accepted");
            check(!m.isAvailable(), "Rented motorcycle became available");
        });

        test(17, "Maintenance through Maintainable reference", () -> {
            Car c = car("V101");
            Motorcycle m = motorcycle("M101");
            Truck t = truck("T101");
            Vehicle[] vehicles = {c, m, t};
            for (Vehicle v : vehicles) {
                v.setMaintenanceRequired(true);
                Maintainable ref = (Maintainable) v;
                check(ref.needsMaintenance(), "Interface did not report flag: " + v.getVehicleId());
                ref.performMaintenance();
                check(!ref.needsMaintenance(), "Interface did not clear flag: " + v.getVehicleId());
            }
        });

        System.out.println();
        System.out.println("===== FINAL TEST REPORT =====");
        System.out.println("Total: 17");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        if (failed > 0) System.exit(1);
    }
}
