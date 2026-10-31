
public class Rental {

    private String rentalId;
    private Customer customer;
    private Vehicle vehicle;
    private int rentalDays;
    private double totalCost;
    private boolean active;

public Rental(String rentalId, Customer customer,
              Vehicle vehicle, int rentalDays,
              boolean extraService) {

    if (rentalId == null || rentalId.isBlank()) {
        throw new IllegalArgumentException(
            "Rental ID cannot be empty"
        );
    }

    if (customer == null || vehicle == null) {
        throw new IllegalArgumentException(
            "Customer and vehicle are required"
        );
    }

    if (rentalDays <= 0) {
        throw new IllegalArgumentException(
            "Rental days must be positive"
        );
    }

    if (!vehicle.isAvailable()) {
        throw new IllegalArgumentException(
            "Vehicle is not available"
        );
    }
    
if (vehicle.isMaintenanceRequired()) {
    throw new IllegalStateException(
        "Vehicle requires maintenance before rental"
    );
}


    this.rentalId = rentalId;
    this.customer = customer;
    this.vehicle = vehicle;
    this.rentalDays = rentalDays;

    this.totalCost =
        vehicle.calculateRentalCost(rentalDays, extraService);

    this.active = true;

    vehicle.setAvailable(false);
}

public String getRentalId() {
    return rentalId;
}

public Customer getCustomer() {
    return customer;
}

public Vehicle getVehicle() {
    return vehicle;
}

public int getRentalDays() {
    return rentalDays;
}

public double getTotalCost() {
    return totalCost;
}

public boolean isActive() {
    return active;
}

public void completeRental() {

    if (!active) {
        throw new IllegalStateException(
            "Rental is already completed"
        );
    }

    this.active = false;

    vehicle.setAvailable(true);
}

public void displayInfo() {

    System.out.println("Rental ID: " + rentalId);

    System.out.println("Customer Name: "
                       + customer.getFullName());

    System.out.println("Vehicle ID: "
                       + vehicle.getVehicleId());

    System.out.println("Vehicle: "
                       + vehicle.getBrand() + " "
                       + vehicle.getModel());

    System.out.println("Rental Days: " + rentalDays);

    System.out.println("Total Cost: $" + totalCost);

    System.out.println("Status: "
                       + (active ? "Active" : "Completed"));
}

}
