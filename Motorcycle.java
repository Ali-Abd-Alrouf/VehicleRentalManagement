

public class Motorcycle extends Vehicle implements Maintainable {

    private int engineCapacity;
    private boolean helmetIncluded;

    public Motorcycle(String vehicleId, String brand,
                      String model, int year,
                      double dailyPrice, int engineCapacity,
                      boolean helmetIncluded) {

        super(vehicleId, brand, model, year, dailyPrice);

        if (engineCapacity <= 0) {
            throw new IllegalArgumentException(
                "Engine capacity must be positive"
            );
        }

        this.engineCapacity = engineCapacity;
        this.helmetIncluded = helmetIncluded;
    }
    
@Override
public double calculateRentalCost(int days, boolean extraService) {

    if (days <= 0) {
        throw new IllegalArgumentException(
            "Rental days must be positive"
        );
    }

    double totalCost = getDailyPrice() * days;

    if (extraService) {
        totalCost += 5 * days;
    }

    return totalCost;
}

@Override
public void displayInfo() {

    super.displayInfo();

    System.out.println("Engine Capacity: " + engineCapacity + " CC");
    System.out.println("Helmet Included: " + helmetIncluded);
}

@Override
public boolean needsMaintenance() {
    return isMaintenanceRequired();
}

@Override
public void performMaintenance() {

    if (!isAvailable()) {
        throw new IllegalStateException(
            "Cannot maintain a rented motorcycle"
        );
    }

    setMaintenanceRequired(false);

    System.out.println(
        "Motorcycle maintenance completed successfully"
    );
}


}
