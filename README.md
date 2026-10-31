
# Vehicle Rental Management System

## Project Description

This is a Java Object-Oriented Programming (OOP) project
that manages vehicle rentals.

The system supports Cars, Motorcycles, and Trucks.

## Features

- Add cars, motorcycles, and trucks
- Display all vehicles
- Display available vehicles
- Register and display customers
- Rent vehicles
- Return rented vehicles
- View rental records
- View customer rental history
- Calculate total revenue
- Mark vehicles for maintenance
- Perform vehicle maintenance
- Search for vehicles by ID

## OOP Concepts Used

- Classes and Objects
- Encapsulation
- Constructors and Constructor Overloading
- Inheritance
- Abstract Classes
- Polymorphism
- Method Overriding
- Method Overloading
- Interfaces
- Exception Handling

## Project Structure

- Vehicle.java
- Car.java
- Motorcycle.java
- Truck.java
- Customer.java
- Rental.java
- RentalSystem.java
- Maintainable.java
- Main.java
- VehicleRentalTests.java

All Java source files are located in the src folder.

## Requirements

- Java JDK installed
- Java 17 or newer recommended
- VS Code or any Java-compatible IDE

## How to Compile and Run

Open a terminal in the project folder.

Navigate to the source directory:

```powershell
cd src
```

Compile the Java files:

```powershell
javac *.java
```

Run the application:

```powershell
java Main
```

## Run Automated Tests

Compile the Java files:

```powershell
javac *.java
```

Run the tests:

```powershell
java VehicleRentalTests
```

The test suite checks 17 required scenarios.

## Notes

- Vehicle IDs, Customer IDs, and Rental IDs must be unique.
- A rented vehicle cannot be rented again until returned.
- Vehicles requiring maintenance cannot be rented.
- Rental costs depend on vehicle type and optional services.
- Total revenue includes completed rentals only.
- Data is stored in memory while the program is running.
