# IRCTC Booking System

## Overview
This project is a **Java-based IRCTC (Indian Railway Catering and Tourism Corporation) Booking System** that allows users to **sign up, log in, and book tickets**. It stores user data and provides booking history retrieval.

## Features
- **User Registration**: Sign up with a username and password.
- **User Authentication**: Secure login using hashed passwords.
- **Ticket Booking**: Users can book train tickets.
- **Fetch Bookings**: Logged-in users can retrieve their past bookings.
- **Data Persistence**: User details and ticket bookings are stored in a JSON file.

## Installation & Setup
### **Prerequisites**
- Java 8 or later
- IntelliJ IDEA (or any Java IDE)
- Jackson Library for JSON Processing

### **Clone the Repository**
```sh
git clone [https://github.com/your-repo-url/IRCTC-Booking-System.git](https://github.com/Nirjus/Irctc-backend-CLI-program.git)
cd IRCTC-Booking-System
```

### **Run the Application**
1. Open the project in **IntelliJ IDEA**.
2. Compile and run `App.java`.
3. Follow on-screen instructions.

## Usage
### **1. User Sign-Up**
```
Enter your name: JohnDoe
Enter your password: *****
User registered successfully!
```

### **2. User Login**
```
Enter the username to Login: JohnDoe
Enter your password to Login: *****
Login successful, welcome JohnDoe!
```

### **3. Fetch Bookings**
```
Fetching your booking...
Ticket 1: Train XYZ | Date: 2025-03-25 | Seat No: A1
```

## Code Explanation
### **Login User** (`UserBookingServices.java`)
```java
private User loggedInUser; // Stores logged-in user globally

public void loginUser(String name, String password) {
    Optional<User> foundUser = userList.stream()
        .filter(user -> user.getName().equals(name) &&
                UserServicesUtil.checkPassword(password, user.getHashPassword()))
        .findFirst();

    if (foundUser.isPresent()) {
        loggedInUser = foundUser.get(); // Store the logged-in user
        System.out.println("Login successful, welcome " + loggedInUser.getName());
    } else {
        System.out.println("Login failed. Invalid username or password.");
    }
}
```

### **Fetch Bookings**
```java
public void fetchBooking() {
    if (loggedInUser == null) {
        System.out.println("No user is logged in. Please log in first.");
        return;
    }
    loggedInUser.printTickets();
}
```

## JSON File Formatting
To properly format JSON files in **IntelliJ IDEA**:
1. Open the JSON file.
2. Press **Ctrl + Alt + L** (Windows/Linux) or **Cmd + Option + L** (Mac).

## Contribution
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-branch`).
3. Commit changes (`git commit -m 'Added new feature'`).
4. Push the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## License
This project is licensed under the **MIT License**.



