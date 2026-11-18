package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * One place for the database connection and the app settings.
 *
 * Settings are read from "db.properties" in the folder the app is started from
 * (the project folder). If that file is missing, the defaults below are used.
 */
public final class Database {

    private static final Properties CONFIG = loadConfig();

    private Database() {
    }

    private static Properties loadConfig() {
        Properties p = new Properties();
        p.setProperty("db.url", "jdbc:mysql://localhost:3306/blood_donor_databse?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata");
        p.setProperty("db.user", "root");
        p.setProperty("db.password", "");
        p.setProperty("app.username", "kirti");
        p.setProperty("app.password", "123");

        Path file = Paths.get("db.properties");
        if (Files.exists(file)) {
            try (InputStream in = new FileInputStream(file.toFile())) {
                p.load(in);
                System.out.println("Loaded settings from " + file.toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Could not read db.properties, using defaults: " + e.getMessage());
            }
        } else {
            System.out.println("db.properties not found in " + Paths.get("").toAbsolutePath() + ", using defaults");
        }
        return p;
    }

    public static String get(String key) {
        return CONFIG.getProperty(key);
    }

    /** Opens a connection to MySQL, or returns null (and prints the reason) if it fails. */
    public static Connection connect() {
        try {
            Connection con = DriverManager.getConnection(get("db.url"), get("db.user"), get("db.password"));
            System.out.println("Connected to MySQL successfully");
            return con;
        } catch (SQLException e) {
            System.out.println("Could not connect to MySQL. Check that MySQL is running and that db.properties is correct.");
            e.printStackTrace();
            return null;
        }
    }

    /** Maps a blood group like "A+" to its column in total_record, e.g. "Ap". Returns null if unknown. */
    public static String stockColumn(String bloodGroup) {
        if (bloodGroup == null) {
            return null;
        }
        switch (bloodGroup.trim()) {
            case "A+":  return "Ap";
            case "A-":  return "An";
            case "B+":  return "Bp";
            case "B-":  return "Bn";
            case "AB+": return "ABp";
            case "AB-": return "ABn";
            case "O+":  return "Op";
            case "O-":  return "On";
            default:    return null;
        }
    }

    /** All blood groups, in the order shown in drop-downs. */
    public static final String[] BLOOD_GROUPS = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    /** Quotes a value for a CSV file so commas or quotes inside it don't break the columns. */
    public static String csv(Object value) {
        String s = value == null ? "" : value.toString();
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            s = "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}
