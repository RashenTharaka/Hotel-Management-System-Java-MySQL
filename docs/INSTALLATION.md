# Installation Guide

This guide explains how to run the Hotel Management System on a local computer.

## 1. Install Requirements

Install:

- Java JDK 8 or newer
- NetBeans IDE
- MySQL Server, Wampserver, or XAMPP
- phpMyAdmin

## 2. Open the Project

1. Open NetBeans.
2. Select **File > Open Project**.
3. Choose the `HotelManagementSystem` folder.
4. Make sure the `lib/` folder is included because it contains the required JAR files.

## 3. Import the Database

1. Start MySQL.
2. Open phpMyAdmin:

```text
http://localhost/phpmyadmin/
```

3. Import this file:

```text
database/hotelmanagementsystem.sql
```

The SQL file creates the database named:

```text
hotelmanagementsystem
```

## 4. Configure Database Connection

Open:

```text
config/database.properties
```

Default configuration:

```properties
db.host=localhost
db.port=3306
db.name=hotelmanagementsystem
db.user=root
db.password=
```

For default XAMPP, the MySQL root password is usually empty. If your MySQL has a password, add it after `db.password=`.

## 5. Build and Run

In NetBeans:

1. Right-click the project.
2. Select **Clean and Build**.
3. Select **Run**.

## 6. Demo Logins

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

## Common Issues

### Database connection failed

Check that:

- MySQL is running.
- The SQL database was imported.
- The database name is `hotelmanagementsystem`.
- The password in `config/database.properties` matches your local MySQL password.

### MySQL driver not found

Check that the `lib/mysql-connector-j-9.2.0.jar` file exists and the project libraries are loaded in NetBeans.

### Reports, Backups, or QR folders are missing

The application creates runtime folders automatically when needed.
