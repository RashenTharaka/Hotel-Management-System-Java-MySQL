package hotel.management.system;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AppPaths {

    private static final String[] RUNTIME_DIRECTORIES = {
        "Backups",
        "QR",
        "Reports/assets",
        "Reports/Checkin",
        "Reports/Checkout",
        "Reports/Customers",
        "Reports/Employees",
        "Reports/Events",
        "Reports/Inventory",
        "Reports/Log Activities",
        "Reports/Orders",
        "Reports/Rooms"
    };

    private AppPaths() {
    }

    public static void ensureRuntimeFolders() {
        for (String directory : RUNTIME_DIRECTORIES) {
            ensureDirectory(directory);
        }
    }

    public static void ensureDirectory(String directory) {
        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException ex) {
            System.err.println("Could not create directory: " + directory + " - " + ex.getMessage());
        }
    }

    public static void ensureParentDirectory(String filePath) {
        Path parent = Paths.get(filePath).getParent();
        if (parent != null) {
            ensureDirectory(parent.toString());
        }
    }

    public static File file(String filePath) {
        ensureParentDirectory(filePath);
        return new File(filePath);
    }
}
