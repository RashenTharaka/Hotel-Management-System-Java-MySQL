package hotel.management.system;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conn implements AutoCloseable {

    Connection c;
    Statement s;

    Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DBConfig.getConnection();
            s = c.createStatement();
        } catch (ClassNotFoundException e) {
            UITheme.showMessageDialog(null,
                    "MySQL JDBC driver was not found. Please check the Jar files folder and NetBeans libraries.",
                    "Database Driver Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("MySQL JDBC driver was not found", e);
        } catch (SQLException e) {
            UITheme.showMessageDialog(null,
                    "Database connection failed.\n\nCheck MySQL is running, import the database SQL file, and update config/database.properties.\n\n"
                            + DBConfig.getSafeConnectionSummary() + "\n\n" + e.getMessage(),
                    "Database Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Database connection failed", e);
        }
    }

    @Override
    public void close() {
        try {
            if (s != null) {
                s.close();
            }
        } catch (SQLException ignored) {
        }
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException ignored) {
        }
    }
}
