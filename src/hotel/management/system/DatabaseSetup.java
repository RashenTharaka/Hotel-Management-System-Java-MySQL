package hotel.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseSetup {

    private DatabaseSetup() {
    }

    public static void ensureSchema(Connection connection) throws SQLException {
        if (connection == null) {
            return;
        }
        createMissingTables(connection);
        addMissingColumns(connection);
        seedDefaultData(connection);
    }

    private static void createMissingTables(Connection c) throws SQLException {
        execute(c, "CREATE TABLE IF NOT EXISTS activitylog ("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "nic VARCHAR(12) NOT NULL, "
                + "activity VARCHAR(255) NOT NULL, "
                + "result VARCHAR(255) DEFAULT NULL, "
                + "activity_time TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, "
                + "name VARCHAR(100) DEFAULT NULL, "
                + "jobrole VARCHAR(50) DEFAULT NULL, "
                + "PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS employee ("
                + "nic VARCHAR(12) NOT NULL, "
                + "name VARCHAR(100) DEFAULT NULL, "
                + "age VARCHAR(10) DEFAULT NULL, "
                + "gender VARCHAR(10) DEFAULT NULL, "
                + "job VARCHAR(50) DEFAULT NULL, "
                + "address VARCHAR(500) DEFAULT NULL, "
                + "phone VARCHAR(15) DEFAULT NULL, "
                + "email VARCHAR(50) DEFAULT NULL, "
                + "password VARCHAR(255) NOT NULL, "
                + "datejoined VARCHAR(20) DEFAULT NULL, "
                + "salary INT DEFAULT NULL, "
                + "PRIMARY KEY (nic)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS customers ("
                + "customer_id INT NOT NULL AUTO_INCREMENT, "
                + "name VARCHAR(255) NOT NULL, "
                + "phone VARCHAR(20) NOT NULL, "
                + "NIC VARCHAR(20) NOT NULL, "
                + "check_in_date DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "room_number VARCHAR(10) NOT NULL, "
                + "payment_status ENUM('Pending','Paid') NOT NULL DEFAULT 'Pending', "
                + "room_type VARCHAR(15) DEFAULT NULL, "
                + "room_price VARCHAR(15) NOT NULL, "
                + "PRIMARY KEY (customer_id), "
                + "UNIQUE KEY NIC (NIC)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS room ("
                + "roomnumber VARCHAR(10) DEFAULT NULL, "
                + "availability VARCHAR(20) DEFAULT NULL, "
                + "cleaning_status VARCHAR(20) DEFAULT NULL, "
                + "price VARCHAR(20) DEFAULT NULL, "
                + "bed_type VARCHAR(20) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS event ("
                + "eventnumber INT NOT NULL, "
                + "description VARCHAR(50) DEFAULT NULL, "
                + "date VARCHAR(20) DEFAULT NULL, "
                + "time VARCHAR(20) DEFAULT NULL, "
                + "duration VARCHAR(20) DEFAULT NULL, "
                + "PRIMARY KEY (eventnumber)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS inventory ("
                + "item_id INT NOT NULL AUTO_INCREMENT, "
                + "item_name VARCHAR(255) NOT NULL, "
                + "category VARCHAR(100) DEFAULT NULL, "
                + "quantity INT NOT NULL DEFAULT 0, "
                + "price DECIMAL(10,2) NOT NULL DEFAULT 0, "
                + "supplier_name VARCHAR(50) NOT NULL DEFAULT '', "
                + "supplier_phone VARCHAR(15) NOT NULL DEFAULT '', "
                + "date_added DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "orders INT DEFAULT 0, "
                + "PRIMARY KEY (item_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS inventory_orders ("
                + "order_id INT NOT NULL AUTO_INCREMENT, "
                + "item_name VARCHAR(255) NOT NULL, "
                + "order_quantity VARCHAR(50) NOT NULL, "
                + "order_date DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "cost DOUBLE DEFAULT NULL, "
                + "PRIMARY KEY (order_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS checkin_details ("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "customer_id VARCHAR(20) DEFAULT NULL, "
                + "room_no VARCHAR(20) DEFAULT NULL, "
                + "name VARCHAR(100) DEFAULT NULL, "
                + "checkin_time DATETIME DEFAULT NULL, "
                + "price VARCHAR(20) DEFAULT NULL, "
                + "nights VARCHAR(10) DEFAULT NULL, "
                + "total VARCHAR(20) DEFAULT NULL, "
                + "paid_amount VARCHAR(20) DEFAULT NULL, "
                + "pending_amount VARCHAR(20) DEFAULT NULL, "
                + "PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

        execute(c, "CREATE TABLE IF NOT EXISTS checkout ("
                + "id INT NOT NULL AUTO_INCREMENT, "
                + "cus_id VARCHAR(20) DEFAULT NULL, "
                + "room_no VARCHAR(20) DEFAULT NULL, "
                + "price VARCHAR(20) DEFAULT NULL, "
                + "pending VARCHAR(100) DEFAULT NULL, "
                + "paid VARCHAR(10) DEFAULT NULL, "
                + "checkin_time DATETIME DEFAULT NULL, "
                + "checkout_time DATETIME DEFAULT NULL, "
                + "PRIMARY KEY (id)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
    }

    private static void addMissingColumns(Connection c) throws SQLException {
        ensureColumn(c, "activitylog", "name", "VARCHAR(100) DEFAULT NULL");
        ensureColumn(c, "activitylog", "jobrole", "VARCHAR(50) DEFAULT NULL");
        ensureColumn(c, "activitylog", "activity_time", "TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP");

        ensureColumn(c, "employee", "datejoined", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "employee", "salary", "INT DEFAULT NULL");

        ensureColumn(c, "customers", "check_in_date", "DATETIME DEFAULT CURRENT_TIMESTAMP");
        ensureColumn(c, "customers", "room_type", "VARCHAR(15) DEFAULT NULL");
        ensureColumn(c, "customers", "room_number", "VARCHAR(10) DEFAULT NULL");
        ensureColumn(c, "customers", "room_price", "VARCHAR(15) DEFAULT NULL");
        ensureColumn(c, "customers", "payment_status", "ENUM('Pending','Paid') NOT NULL DEFAULT 'Pending'");

        ensureColumn(c, "room", "cleaning_status", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "room", "price", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "room", "bed_type", "VARCHAR(20) DEFAULT NULL");

        ensureColumn(c, "inventory", "category", "VARCHAR(100) DEFAULT NULL");
        ensureColumn(c, "inventory", "supplier_name", "VARCHAR(50) NOT NULL DEFAULT ''");
        ensureColumn(c, "inventory", "supplier_phone", "VARCHAR(15) NOT NULL DEFAULT ''");
        ensureColumn(c, "inventory", "date_added", "DATETIME DEFAULT CURRENT_TIMESTAMP");
        ensureColumn(c, "inventory", "orders", "INT DEFAULT 0");

        ensureColumn(c, "inventory_orders", "cost", "DOUBLE DEFAULT NULL");

        ensureColumn(c, "checkin_details", "customer_id", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "room_no", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "name", "VARCHAR(100) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "checkin_time", "DATETIME DEFAULT NULL");
        ensureColumn(c, "checkin_details", "price", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "nights", "VARCHAR(10) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "total", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "paid_amount", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkin_details", "pending_amount", "VARCHAR(20) DEFAULT NULL");

        ensureColumn(c, "checkout", "cus_id", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkout", "room_no", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkout", "price", "VARCHAR(20) DEFAULT NULL");
        ensureColumn(c, "checkout", "pending", "VARCHAR(100) DEFAULT NULL");
        ensureColumn(c, "checkout", "paid", "VARCHAR(10) DEFAULT NULL");
        ensureColumn(c, "checkout", "checkin_time", "DATETIME DEFAULT NULL");
        ensureColumn(c, "checkout", "checkout_time", "DATETIME DEFAULT NULL");
    }

    private static void seedDefaultData(Connection c) throws SQLException {
        if (countRows(c, "room") == 0) {
            execute(c, "INSERT INTO room (roomnumber, availability, cleaning_status, price, bed_type) VALUES "
                    + "('001','Available','Cleaned','35000','Double Bed'),"
                    + "('002','Available','Cleaned','35000','Double Bed'),"
                    + "('003','Available','Cleaned','35000','Double Bed'),"
                    + "('004','Available','Cleaned','25000','Single Bed'),"
                    + "('005','Available','Cleaned','25000','Single Bed'),"
                    + "('006','Available','Dirty','25000','Single Bed')");
        }
        if (countRows(c, "employee") == 0) {
            execute(c, "INSERT INTO employee (nic, name, age, gender, job, address, phone, email, password, datejoined, salary) VALUES "
                    + "('111111111111', 'Admin User', '35', 'Male', 'Admin', 'Colombo', '0710000000', 'admin@example.com', '000000', CURRENT_DATE(), 200000),"
                    + "('134289036V', 'Reception User', '32', 'Female', 'Receptionist', 'Colombo', '0710000001', 'reception@example.com', '123456', CURRENT_DATE(), 60000)");
        }
    }

    private static void ensureColumn(Connection c, String table, String column, String definition) throws SQLException {
        if (!columnExists(c, table, column)) {
            execute(c, "ALTER TABLE `" + table + "` ADD COLUMN `" + column + "` " + definition);
        }
    }

    private static boolean columnExists(Connection c, String table, String column) throws SQLException {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, table);
            ps.setString(2, column);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private static int countRows(Connection c, String table) throws SQLException {
        try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM `" + table + "`")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private static void execute(Connection c, String sql) throws SQLException {
        try (Statement st = c.createStatement()) {
            st.executeUpdate(sql);
        }
    }
}
