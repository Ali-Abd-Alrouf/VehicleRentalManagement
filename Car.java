
public class Car extends Vehicle implements Maintainable {

    private int seats;
    private String transmissionType;

 
public Car(String vehicleId, String brand, String model,
           int year, double dailyPrice,
           int seats, String transmissionType) {

    super(vehicleId, brand, model, year, dailyPrice);

    if (seats <= 0) {
        throw new IllegalArgumentException(
            "Seats must be positive"
        );
    }

    if (transmissionType == null ||
        (!transmissionType.equalsIgnoreCase("Automatic") &&
         !transmissionType.equalsIgnoreCase("Manual"))) {

        throw new IllegalArgumentException(
            "Transmission must be Automatic or Manual"
        );
    }

    this.seats = seats;
    this.transmissionType = transmissionType;
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
        totalCost += 15 * days;
    }

    return totalCost;
}

@Override
public void displayInfo() {

    super.displayInfo();

    System.out.println("Seats: " + seats);
    System.out.println("Transmission Type: " + transmissionType);
}

@Override
public boolean needsMaintenance() {
    return isMaintenanceRequired();
}

@Override
public void performMaintenance() {

    if (!isAvailable()) {
        throw new IllegalStateException(
            "Cannot maintain a rented car"
        );
    }

    setMaintenanceRequired(false);

    System.out.println("Car maintenance completed successfully");
}


}
