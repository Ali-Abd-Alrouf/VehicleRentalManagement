
import java.util.ArrayList;

public class RentalSystem {

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Customer> customers;
    private ArrayList<Rental> rentals;

    public RentalSystem() {
        vehicles = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    
public void addVehicle(Vehicle vehicle) {

    if (vehicle == null) {
        throw new IllegalArgumentException(
            "Vehicle cannot be null"
        );
    }

    for (Vehicle v : vehicles) {

        if (v.getVehicleId().equals(vehicle.getVehicleId())) {
            throw new IllegalArgumentException(
                "Vehicle ID already exists"
            );
        }
    }

    vehicles.add(vehicle);

    System.out.println("Vehicle added successfully");
}

public Vehicle findVehicleById(String vehicleId) {

    for (Vehicle v : vehicles) {

        if (v.getVehicleId().equals(vehicleId)) {
            return v;
        }
    }

    return null;
}

public void displayAllVehicles() {

    if (vehicles.isEmpty()) {
        System.out.println("No vehicles available in the system");
        return;
    }

    for (Vehicle v : vehicles) {
        v.displayInfo();
        System.out.println("-------------------------");
    }
}

public void displayAvailableVehicles() {

    boolean found = false;

    for (Vehicle v : vehicles) {

        if (v.isAvailable() && !v.isMaintenanceRequired()) {

            v.displayInfo();

            System.out.println("-------------------------");

            found = true;
        }
    }

    if (!found) {
        System.out.println("No available vehicles found");
    }
}

public void addCustomer(Customer customer) {

    if (customer == null) {
        throw new IllegalArgumentException(
            "Customer cannot be null"
        );
    }

    for (Customer c : customers) {

        if (c.getCustomerId().equals(customer.getCustomerId())) {
            throw new IllegalArgumentException(
                "Customer ID already exists"
            );
        }
    }

    customers.add(customer);

    System.out.println("Customer added successfully");
}

public Customer findCustomerById(String customerId) {

    for (Customer c : customers) {

        if (c.getCustomerId().equals(customerId)) {
            return c;
        }
    }

    return null;
}

public void displayAllCustomers() {

    if (customers.isEmpty()) {
        System.out.println("No customers registered in the system");
        return;
    }

    for (Customer c : customers) {
        c.displayInfo();
        System.out.println("-------------------------");
    }
}

public void rentVehicle(String rentalId, String customerId,
                        String vehicleId, int days,
                        boolean extraService) {

    for (Rental r : rentals) {
        if (r.getRentalId().equals(rentalId)) {
            throw new IllegalArgumentException(
                "Rental ID already exists"
            );
        }
    }

   
    Customer customer = findCustomerById(customerId);

    if (customer == null) {
        throw new IllegalArgumentException(
            "Customer not found"
        );
    }


    Vehicle vehicle = findVehicleById(vehicleId);

    if (vehicle == null) {
        throw new IllegalArgumentException(
            "Vehicle not found"
        );
    }

   
    if (!vehicle.isAvailable()) {
        throw new IllegalStateException(
            "Vehicle is already rented"
        );
    }

  
    if (vehicle.isMaintenanceRequired()) {
        throw new IllegalStateException(
            "Vehicle requires maintenance"
        );
    }

   
    if (days <= 0) {
        throw new IllegalArgumentException(
            "Rental days must be positive"
        );
    }

 
    Rental rental = new Rental(
        rentalId, customer, vehicle, days, extraService
    );

    
    rentals.add(rental);

    System.out.println("Vehicle rented successfully");
    System.out.println("Total Cost: $" + rental.getTotalCost());
}

public void returnVehicle(String rentalId) {

    for (Rental r : rentals) {

        if (r.getRentalId().equals(rentalId)) {

            if (!r.isActive()) {
                throw new IllegalStateException(
                    "Rental is already completed"
                );
            }

            r.completeRental();

            System.out.println("Vehicle returned successfully");
            return;
        }
    }

    throw new IllegalArgumentException(
        "Rental not found"
    );
}

public void displayAllRentals() {

    if (rentals.isEmpty()) {
        System.out.println("No rentals found in the system");
        return;
    }

    for (Rental r : rentals) {
        r.displayInfo();
        System.out.println("-------------------------");
    }
}

public double calculateTotalRevenue() {

    double totalRevenue = 0;

    for (Rental r : rentals) {

        if (!r.isActive()) {
            totalRevenue += r.getTotalCost();
        }
    }

    return totalRevenue;
}

public void markVehicleForMaintenance(String vehicleId) {

    Vehicle vehicle = findVehicleById(vehicleId);

    if (vehicle == null) {
        throw new IllegalArgumentException(
            "Vehicle not found"
        );
    }

    if (!vehicle.isAvailable()) {
        throw new IllegalStateException(
            "Cannot mark a rented vehicle for maintenance"
        );
    }

    if (vehicle.isMaintenanceRequired()) {
        throw new IllegalStateException(
            "Vehicle is already marked for maintenance"
        );
    }

    vehicle.setMaintenanceRequired(true);

    System.out.println(
        "Vehicle marked for maintenance successfully"
    );
}

public void performVehicleMaintenance(String vehicleId) {

    Vehicle vehicle = findVehicleById(vehicleId);

    if (vehicle == null) {
        throw new IllegalArgumentException(
            "Vehicle not found"
        );
    }

    if (!vehicle.isAvailable()) {
        throw new IllegalStateException(
            "Cannot maintain a rented vehicle"
        );
    }

    Maintainable maintainableVehicle = (Maintainable) vehicle;

    if (!maintainableVehicle.needsMaintenance()) {
        throw new IllegalStateException(
            "Vehicle does not need maintenance"
        );
    }

    maintainableVehicle.performMaintenance();

    System.out.println(
        "Vehicle is now ready for rental"
    );
}

public void displayCustomerRentalHistory(String customerId) {

    Customer customer = findCustomerById(customerId);

    if (customer == null) {
        throw new IllegalArgumentException("Customer not found");
    }

    boolean found = false;

    for (Rental rental : rentals) {

        if (rental.getCustomer().getCustomerId().equals(customerId)) {

            rental.displayInfo();
            System.out.println("-------------------------");
            found = true;
        }
    }

    if (!found) {
        System.out.println("No rental history for this customer");
    }
}

}
