package hotel.management.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConfig {

    private static final Path CONFIG_PATH = Paths.get("config", "database.properties");
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DATABASE = "hotelmanagementsystem";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private DBConfig() {
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = loadProperties();
        Connection connection = DriverManager.getConnection(getJdbcUrl(properties), getUser(properties), getPassword(properties));
        DatabaseSetup.ensureSchema(connection);
        return connection;
    }

    public static String getDatabaseName() {
        return getValue(loadProperties(), "db.name", DEFAULT_DATABASE);
    }

    public static String getUser() {
        return getUser(loadProperties());
    }

    public static String getPassword() {
        return getPassword(loadProperties());
    }

    public static String getSafeConnectionSummary() {
        Properties properties = loadProperties();
        return getJdbcUrl(properties) + " | user=" + getUser(properties) + " | config=" + CONFIG_PATH.toAbsolutePath();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        properties.setProperty("db.host", DEFAULT_HOST);
        properties.setProperty("db.port", DEFAULT_PORT);
        properties.setProperty("db.name", DEFAULT_DATABASE);
        properties.setProperty("db.user", DEFAULT_USER);
        properties.setProperty("db.password", DEFAULT_PASSWORD);
        properties.setProperty("db.useSSL", "false");
        properties.setProperty("db.allowPublicKeyRetrieval", "true");
        properties.setProperty("db.serverTimezone", "UTC");

        try {
            if (!Files.exists(CONFIG_PATH)) {
                writeDefaultConfig(properties);
            }
            try (InputStream input = Files.newInputStream(CONFIG_PATH)) {
                properties.load(input);
            }
        } catch (IOException ex) {
            System.err.println("Could not load database config. Using defaults. " + ex.getMessage());
        }

        applyEnvironmentOverrides(properties);
        return properties;
    }

    private static void writeDefaultConfig(Properties properties) throws IOException {
        Path parent = CONFIG_PATH.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        try (OutputStream output = Files.newOutputStream(CONFIG_PATH)) {
            properties.store(output, "Hotel Management System database settings");
        }
    }

    private static void applyEnvironmentOverrides(Properties properties) {
        setIfPresent(properties, "db.host", System.getenv("HMS_DB_HOST"));
        setIfPresent(properties, "db.port", System.getenv("HMS_DB_PORT"));
        setIfPresent(properties, "db.name", System.getenv("HMS_DB_NAME"));
        setIfPresent(properties, "db.user", System.getenv("HMS_DB_USER"));
        setIfPresent(properties, "db.password", System.getenv("HMS_DB_PASSWORD"));
    }

    private static void setIfPresent(Properties properties, String key, String value) {
        if (value != null && !value.trim().isEmpty()) {
            properties.setProperty(key, value.trim());
        }
    }

    private static String getJdbcUrl(Properties properties) {
        String url = properties.getProperty("db.url");
        if (url != null && !url.trim().isEmpty()) {
            return url.trim();
        }

        String host = getValue(properties, "db.host", DEFAULT_HOST);
        String port = getValue(properties, "db.port", DEFAULT_PORT);
        String database = getValue(properties, "db.name", DEFAULT_DATABASE);
        String useSSL = getValue(properties, "db.useSSL", "false");
        String allowPublicKeyRetrieval = getValue(properties, "db.allowPublicKeyRetrieval", "true");
        String serverTimezone = getValue(properties, "db.serverTimezone", "UTC");

        return "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=" + useSSL
                + "&allowPublicKeyRetrieval=" + allowPublicKeyRetrieval
                + "&serverTimezone=" + serverTimezone;
    }

    private static String getUser(Properties properties) {
        return getValue(properties, "db.user", DEFAULT_USER);
    }

    private static String getPassword(Properties properties) {
        return properties.getProperty("db.password", DEFAULT_PASSWORD);
    }

    private static String getValue(Properties properties, String key, String defaultValue) {
        String value = properties.getProperty(key);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}
