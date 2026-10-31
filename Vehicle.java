
public abstract class Vehicle {

    private String vehicleId;
    private String brand;
    private String model;
    private int year;
    private double dailyPrice;
    private boolean available;


public Vehicle(String vehicleId, String brand,
               String model, int year, double dailyPrice) {

    if (vehicleId == null || vehicleId.isBlank()) {
        throw new IllegalArgumentException(
            "Vehicle ID cannot be empty"
        );
    }

    if (brand == null || brand.isBlank()) {
        throw new IllegalArgumentException(
            "Brand cannot be empty"
        );
    }

    if (model == null || model.isBlank()) {
        throw new IllegalArgumentException(
            "Model cannot be empty"
        );
    }

    if (year <= 0) {
        throw new IllegalArgumentException(
            "Invalid manufacturing year"
        );
    }

    this.vehicleId = vehicleId;
    this.brand = brand;
    this.model = model;
    this.year = year;

    setDailyPrice(dailyPrice);

    this.available = true;
    
this.maintenanceRequired = false;

}



public String getVehicleId() {
    return vehicleId;
}

public String getBrand() {
    return brand;
}

public String getModel() {
    return model;
}

public int getYear() {
    return year;
}

public double getDailyPrice() {
    return dailyPrice;
}

public boolean isAvailable() {
    return available;
}

private boolean maintenanceRequired;


public void setDailyPrice(double dailyPrice) {
    if (dailyPrice > 0) {
        this.dailyPrice = dailyPrice;
    } else {
        throw new IllegalArgumentException(
            "Daily price must be positive"
        );
    }
}

public void setAvailable(boolean available) {
    this.available = available;
}

public void displayInfo() {
    System.out.println("Vehicle ID: " + vehicleId);
    System.out.println("Brand: " + brand);
    System.out.println("Model: " + model);
    System.out.println("Year: " + year);
    System.out.println("Daily Price: $" + dailyPrice);
    System.out.println("Available: " + available);
}

public abstract double calculateRentalCost(int days, boolean extraService);

public double calculateRentalCost(int days) {
    return calculateRentalCost(days, false);
}


public boolean isMaintenanceRequired() {
    return maintenanceRequired;
}

public void setMaintenanceRequired(boolean maintenanceRequired) {
    this.maintenanceRequired = maintenanceRequired;
}




}
