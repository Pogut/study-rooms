package rooms;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Weekly room usage for library staff, exported as CSV. */
public class UsageReport {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/rooms";
    private static final String DB_USER = "library";
    private static final String DB_PASSWORD = System.getenv("ROOMS_DB_PASSWORD");

    public int hoursBooked(String roomId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT minutes FROM bookings WHERE room_id = ?")) {
            statement.setString(1, roomId);
            try (ResultSet rows = statement.executeQuery()) {
                int minutes = 0;
                while (rows.next()) {
                    minutes += rows.getInt("minutes");
                }
                return minutes / 60;
            }
        }
    }

    public void exportCsv(String roomId, String path) throws IOException, SQLException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("room,hours\n");
            writer.write(roomId + "," + hoursBooked(roomId) + "\n");
        }
    }

    public void archive(String path) throws IOException {
        new ProcessBuilder("gzip", path).start();
    }
}
