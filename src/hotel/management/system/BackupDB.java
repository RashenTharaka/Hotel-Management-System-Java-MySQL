package hotel.management.system;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BackupDB {

    public static boolean backupSQL() {
        try {
            AppPaths.ensureDirectory("Backups");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filePath = "Backups/Backup_" + timestamp + ".sql";

            List<String> command = new ArrayList<String>();
            command.add("mysqldump");
            command.add("-u" + DBConfig.getUser());
            if (!DBConfig.getPassword().isEmpty()) {
                command.add("-p" + DBConfig.getPassword());
            }
            command.add(DBConfig.getDatabaseName());
            command.add("-r");
            command.add(filePath);

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process runtimeProcess = processBuilder.start();
            String output = readProcessOutput(runtimeProcess);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete != 0 && output != null && !output.trim().isEmpty()) {
                System.err.println(output);
            }
            return processComplete == 0;
        } catch (Exception e) {
            System.err.println("SQL backup failed. Make sure mysqldump is installed and available in PATH.");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean backupCSV() {
        AppPaths.ensureDirectory("Backups");

        try (Connection conn = DBConfig.getConnection();
             Statement tableListStmt = conn.createStatement();
             ResultSet tables = tableListStmt.executeQuery("SHOW TABLES")) {

            while (tables.next()) {
                String table = tables.getString(1);

                try (Statement dataStmt = conn.createStatement();
                     ResultSet rs = dataStmt.executeQuery("SELECT * FROM `" + table + "`");
                     FileWriter writer = new FileWriter("Backups/" + table + ".csv")) {

                    ResultSetMetaData meta = rs.getMetaData();
                    int columnCount = meta.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        writer.write(escapeCsv(meta.getColumnName(i)));
                        if (i < columnCount) {
                            writer.write(",");
                        }
                    }
                    writer.write("\n");

                    while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            writer.write(escapeCsv(rs.getString(i)));
                            if (i < columnCount) {
                                writer.write(",");
                            }
                        }
                        writer.write("\n");
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String readProcessOutput(Process process) throws IOException {
        InputStream inputStream = process.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }

        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}
