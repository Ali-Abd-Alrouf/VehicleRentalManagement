

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        RentalSystem system = new RentalSystem();
        
Customer testCustomer = new Customer(
    "C999", "Test User", "0599999999", "DL999"
);

System.out.println("Constructor Overloading Test:");
testCustomer.displayInfo();

Vehicle testCar = new Car(
    "V999", "Toyota", "Corolla",
    2022, 50, 5, "Automatic"
);

System.out.println("Method Overloading Test:");
System.out.println("Without Extra Service: $" +
    testCar.calculateRentalCost(3));

System.out.println("With Extra Service: $" +
    testCar.calculateRentalCost(3, true));


        int choice = -1;

        do {
            System.out.println("\n===== Vehicle Rental System =====");
            System.out.println("1. Add Car");
            System.out.println("2. Add Motorcycle");
            System.out.println("3. Add Truck");
            System.out.println("4. Display All Vehicles");
            System.out.println("5. Display Available Vehicles");
            System.out.println("6. Add Customer");
            System.out.println("7. Display All Customers");
            System.out.println("8. Rent Vehicle");
            System.out.println("9. Return Vehicle");
            System.out.println("10. Display All Rentals");
            System.out.println("11. Calculate Total Revenue");
            System.out.println("12. Mark Vehicle for Maintenance");
            System.out.println("13. Perform Vehicle Maintenance");
        System.out.println("14. Search Vehicle");
        System.out.println("15. View Customer Rental History");
        System.out.println("0. Exit");

            try {
                System.out.print("Enter your choice: ");
                choice = input.nextInt();

                switch (choice) {

                    case 1: {
                        System.out.print("Enter Vehicle ID: ");
                        String carId = input.next();

                        System.out.print("Enter Brand: ");
                        String carBrand = input.next();

                        System.out.print("Enter Model: ");
                        String carModel = input.next();

                        System.out.print("Enter Year: ");
                        int carYear = input.nextInt();

                        System.out.print("Enter Daily Price: ");
                        double carPrice = input.nextDouble();

                        System.out.print("Enter Number of Seats: ");
                        int seats = input.nextInt();

                        System.out.print("Enter Transmission (Automatic/Manual): ");
                        String transmission = input.next();

                        Car car = new Car(
                            carId,
                            carBrand,
                            carModel,
                            carYear,
                            carPrice,
                            seats,
                            transmission
                        );

                        system.addVehicle(car);
                        break;
                    }

                    case 2: {
                        System.out.print("Enter Vehicle ID: ");
                        String motorcycleId = input.next();

                        System.out.print("Enter Brand: ");
                        String motorcycleBrand = input.next();

                        System.out.print("Enter Model: ");
                        String motorcycleModel = input.next();

                        System.out.print("Enter Year: ");
                        int motorcycleYear = input.nextInt();

                        System.out.print("Enter Daily Price: ");
                        double motorcyclePrice = input.nextDouble();

                        System.out.print("Enter Engine Capacity (CC): ");
                        int engineCapacity = input.nextInt();

                        System.out.print("Helmet Included (true/false): ");
                        boolean helmetIncluded = input.nextBoolean();

                        Motorcycle motorcycle = new Motorcycle(
                            motorcycleId,
                            motorcycleBrand,
                            motorcycleModel,
                            motorcycleYear,
                            motorcyclePrice,
                            engineCapacity,
                            helmetIncluded
                        );

                        system.addVehicle(motorcycle);
                        break;
                    }

                    case 3: {
                        System.out.print("Enter Vehicle ID: ");
                        String truckId = input.next();

                        System.out.print("Enter Brand: ");
                        String truckBrand = input.next();

                        System.out.print("Enter Model: ");
                        String truckModel = input.next();

                        System.out.print("Enter Year: ");
                        int truckYear = input.nextInt();

                        System.out.print("Enter Daily Price: ");
                        double truckPrice = input.nextDouble();

                        System.out.print("Enter Max Load Capacity (tons): ");
                        double maxLoadCapacity = input.nextDouble();

                        Truck truck = new Truck(
                            truckId,
                            truckBrand,
                            truckModel,
                            truckYear,
                            truckPrice,
                            maxLoadCapacity
                        );

                        system.addVehicle(truck);
                        break;
                    }

                    case 4: {
                        system.displayAllVehicles();
                        break;
                    }

                    case 5: {
                        system.displayAvailableVehicles();
                        break;
                    }

                    case 6: {
                        System.out.print("Enter Customer ID: ");
                        String customerId = input.next();

                        input.nextLine();

                        System.out.print("Enter Full Name: ");
                        String fullName = input.nextLine();

                        System.out.print("Enter Phone: ");
                        String phone = input.next();

                        System.out.print("Enter Email: ");
                        String email = input.next();

                        System.out.print("Enter Driving License Number: ");
                        String licenseNumber = input.next();

                        Customer customer = new Customer(
                            customerId,
                            fullName,
                            phone,
                            email,
                            licenseNumber
                        );

                        system.addCustomer(customer);
                        break;
                    }

                    case 7: {
                        system.displayAllCustomers();
                        break;
                    }

                    case 8: {
                        System.out.print("Enter Rental ID: ");
                        String rentalId = input.next();

                        System.out.print("Enter Customer ID: ");
                        String rentCustomerId = input.next();

                        System.out.print("Enter Vehicle ID: ");
                        String rentVehicleId = input.next();

                        System.out.print("Enter Number of Rental Days: ");
                        int rentalDays = input.nextInt();

                        System.out.print("Extra Service (true/false): ");
                        boolean extraService = input.nextBoolean();

                        system.rentVehicle(
                            rentalId,
                            rentCustomerId,
                            rentVehicleId,
                            rentalDays,
                            extraService
                        );

                        break;
                    }

                    case 9: {
                        System.out.print("Enter Rental ID to return: ");
                        String returnRentalId = input.next();

                        system.returnVehicle(returnRentalId);
                        break;
                    }

                    case 10: {
                        system.displayAllRentals();
                        break;
                    }

                    case 11: {
                        double totalRevenue =
                            system.calculateTotalRevenue();

                        System.out.println(
                            "Total Revenue: $" + totalRevenue
                        );

                        break;
                    }

                    case 12: {
                        System.out.print("Enter Vehicle ID for Maintenance: ");
                        String maintenanceVehicleId = input.next();

                        system.markVehicleForMaintenance(
                            maintenanceVehicleId
                        );

                        break;
                    }

                    case 13: {
                        System.out.print("Enter Vehicle ID to Perform Maintenance: ");
                        String performMaintenanceId = input.next();

                        system.performVehicleMaintenance(
                            performMaintenanceId
                        );

                        break;
                    }

                    case 14: {
                        System.out.print("Enter Vehicle ID to Search: ");
                        String searchVehicleId = input.next();

                        Vehicle foundVehicle =
                            system.findVehicleById(searchVehicleId);

                        if (foundVehicle != null) {
                            System.out.println("Vehicle Found:");
                            foundVehicle.displayInfo();
                        } else {
                            System.out.println("Vehicle not found");
                        }

                        break;
                    }
                    
case 15: {
    System.out.print("Enter Customer ID: ");
    String historyCustomerId = input.next();

    system.displayCustomerRentalHistory(historyCustomerId);

    break;
}


                    case 0: {
                        System.out.println("Exiting system...");
                        break;
                    }

                    default: {
                        System.out.println("Invalid choice");
                        break;
                    }
                }

            } catch (InputMismatchException e) {

                System.out.println(
                    "Invalid input! Please enter the correct data type."
                );

                input.nextLine();

            } catch (IllegalArgumentException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (IllegalStateException e) {

                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 0);

        input.close();
    }
}
