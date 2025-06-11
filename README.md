# Flight Booking System (Java Swing GUI)

This is a Java GUI application for booking flights. Users can enter flight class, origin, and destination to book a ticket. The application validates user input and logs each interaction.

## Features

- Java Swing-based interface
- Class-based flight booking (First, Business, Coach)
- Real-time button feedback via flashing animation
- Logging of all user actions
- Basic sound feedback on booking
- Error handling for invalid class inputs

##� Technologies Used

- Java 8+
- Swing (GUI)
- Multithreading (`FlashingThread`)
- Logging system (`AssignmentLogger`)
- Custom exceptions (`FlightClassException`)

## How to Run

### Requirements:
- Java 8 or later
- IDE like IntelliJ IDEA or Visual Studio Code with Java extensions

### Steps:

1. **Clone or Download** this repository: git clone https://github.com/lawadeolokun/Flight-Booking-System.git
2. Open the project in your Java IDE
3. Compile all classes under `src/` :
   ```bash
   javac -d out gui/FlightBooking.java exceptions/*.java flights/*.java logging/*.java
5. Run the GUI:
   ```bash
   cd out
   java gui.FlightBooking
   
OR simply right-click FlightBooking.java and choose Run in your IDE
