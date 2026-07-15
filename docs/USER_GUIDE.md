# User Guide

## Login

The application supports two main roles:

- Admin
- Receptionist

Use the demo login details in the README after importing the database.

## Admin Dashboard

The admin dashboard provides access to:

- Employee management
- Room management
- Customer management
- Event management
- Inventory management
- Reports
- Activity logs
- Database backup

## Receptionist Dashboard

The receptionist dashboard provides access to hotel operation features such as:

- Customer management
- Check-in
- Check-out
- Room search
- Event search

## Employee Management

Admins can add, update, view, and delete employees. Passwords are required for Admin and Receptionist roles.

## Room Management

Users can add rooms, update room availability, update cleaning status, and search room records.

## Customer Management

Users can add customers, assign available rooms, update customer records, and remove customer records.

## Event Management

Users can add, update, search, and delete event records.

## Inventory Management

Users can manage inventory items and create inventory orders.

## Reports

The system can generate PDF reports for several modules. Generated reports are saved inside runtime `Reports/` folders.

## Backup

The system includes database backup support. SQL backup requires `mysqldump` to be available in the system PATH. CSV backup can be created from the application database tables.
