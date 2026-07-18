# Hotel Management System

A Java Swing desktop application for managing hotel and resort operations. The system includes modules for employees, customers, rooms, events, inventory, check-in/check-out, activity logs, reports, and database backup.

<p align="center">
  <img src="src/icons/HotelManagementSystem-Poster.png"
       alt="Kingfisher Resort Management System showing the admin dashboard, employee management, and event management modules"
       width="100%">
</p>

## Features

- Admin and receptionist login
- Employee management
- Customer management
- Room management
- Event management
- Inventory and order management
- Check-in and check-out management
- Activity log tracking
- PDF report generation
- MySQL database backup support
- Modern Java Swing interface with centered windows, rounded buttons, readable tables, and improved forms

## Technologies Used

- Java Swing
- MySQL
- JDBC
- NetBeans Ant project
- iText PDF
- JFreeChart
- JCalendar
- ZXing
- rs2xml

## Requirements

- Java JDK 8 or newer
- NetBeans IDE recommended
- MySQL Server, Wampserver, or XAMPP
- phpMyAdmin for database import

## Project Structure

```text
HotelManagementSystem/
├── src/
├── lib/
├── database/
├── config/
├── docs/
├── nbproject/
├── build.xml
├── manifest.mf
├── README.md
└── .gitignore
```

## Database Setup

Import this SQL file using phpMyAdmin:

```text
database/hotelmanagementsystem.sql
```

The SQL file creates the database:

```text
hotelmanagementsystem
```

## Database Configuration

The database connection file is:

```text
config/database.properties
```

Default settings:

```properties
db.host=localhost
db.port=3306
db.name=hotelmanagementsystem
db.user=root
db.password=
```

If your MySQL root user has a password, add it after `db.password=`.

## How to Run in NetBeans

1. Open NetBeans.
2. Click **File > Open Project**.
3. Select the `HotelManagementSystem` folder.
4. Right-click the project and choose **Clean and Build**.
5. Right-click the project and choose **Run**.

## Demo Login Details

Admin:

```text
NIC: 111111111111
Password: 000000
```

Receptionist:

```text
NIC: 134289036V
Password: 123456
```

## Documentation

More setup and usage details are available in the `docs/` folder.

## Note

This is an academic desktop application. Demo passwords are stored in plain text for simplicity. For production use, password hashing, stronger validation, and role-based security improvements should be added.
