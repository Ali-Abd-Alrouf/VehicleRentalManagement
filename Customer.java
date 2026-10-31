
public class Customer {

    private String customerId;
    private String fullName;
    private String phone;
    private String email;
    private String drivingLicenseNumber;
    
public Customer(String customerId, String fullName,
                String phone, String email,
                String drivingLicenseNumber) {

    if (customerId == null || customerId.isBlank()) {
        throw new IllegalArgumentException(
            "Customer ID cannot be empty"
        );
    }

    if (fullName == null || fullName.isBlank()) {
        throw new IllegalArgumentException(
            "Customer name cannot be empty"
        );
    }

    if (drivingLicenseNumber == null ||
        drivingLicenseNumber.isBlank()) {
        throw new IllegalArgumentException(
            "Driving license cannot be empty"
        );
    }

    this.customerId = customerId;
    this.fullName = fullName;
    this.drivingLicenseNumber = drivingLicenseNumber;

   setPhone(phone);
setEmail(email);
}

public Customer(String customerId, String fullName,
                String phone, String drivingLicenseNumber) {

    this(customerId, fullName, phone, "Not Provided",
         drivingLicenseNumber);
}

public String getCustomerId() {
    return customerId;
}

public String getFullName() {
    return fullName;
}

public String getPhone() {
    return phone;
}

public String getEmail() {
    return email;
}

public String getDrivingLicenseNumber() {
    return drivingLicenseNumber;
}

public void setPhone(String phone) {

    if (phone == null || phone.isBlank()) {
        throw new IllegalArgumentException(
            "Phone cannot be empty"
        );
    }

    this.phone = phone;
}

public void setEmail(String email) {

    if (email == null || email.isBlank()) {
        throw new IllegalArgumentException(
            "Email cannot be empty"
        );
    }

    this.email = email;
}

public void displayInfo() {

    System.out.println("Customer ID: " + customerId);
    System.out.println("Full Name: " + fullName);
    System.out.println("Phone: " + phone);
    System.out.println("Email: " + email);
    System.out.println("Driving License: " + drivingLicenseNumber);

}


}
