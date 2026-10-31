

public class Truck extends Vehicle implements Maintainable {

    private double maxLoadCapacity;

    private static final double SERVICE_FEE = 50.0;

    public Truck(String vehicleId, String brand,
                 String model, int year,
                 double dailyPrice, double maxLoadCapacity) {

        super(vehicleId, brand, model, year, dailyPrice);

        if (maxLoadCapacity <= 0) {
            throw new IllegalArgumentException(
                "Maximum load capacity must be positive"
            );
        }

        this.maxLoadCapacity = maxLoadCapacity;
    }
    
@Override
public double calculateRentalCost(int days, boolean extraService) {

    if (days <= 0) {
        throw new IllegalArgumentException(
            "Rental days must be positive"
        );
    }

    double totalCost = getDailyPrice() * days;

    totalCost += SERVICE_FEE;

    return totalCost;
}

@Override
public void displayInfo() {

    super.displayInfo();

    System.out.println("Max Load Capacity: " 
                       + maxLoadCapacity + " tons");
}

@Override
public boolean needsMaintenance() {
    return isMaintenanceRequired();
}

@Override
public void performMaintenance() {

    if (!isAvailable()) {
        throw new IllegalStateException(
            "Cannot maintain a rented truck"
        );
    }

    setMaintenanceRequired(false);

    System.out.println(
        "Truck maintenance completed successfully"
    );
}


}
