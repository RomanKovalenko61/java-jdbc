package com.dmdev;

import com.dmdev.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

public class BlobRunner {

    public static void main(String[] args) throws SQLException, IOException {
        // blob = bytea
        // clob - TEXT

//        saveImage();
        getImage();
    }

    private static void getImage() throws SQLException, IOException {
        var sql = """
                SELECT image
                FROM aircraft
                WHERE id = ?;
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var image = resultSet.getBytes(1);
                Files.write(Path.of("resources", "boeing777_new.jpg"), image, StandardOpenOption.CREATE);
            }
        }
    }

    private static void saveImage() throws SQLException, IOException {
        var sql = """
                UPDATE aircraft
                SET image = ?
                WHERE id = 1;
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing777.jpg")));
            preparedStatement.executeUpdate();
        }
    }

    /*
 Метод org.postgresql.jdbc.PgConnection.createBlob() ещё не реализован
    private static void saveImage() throws SQLException, IOException {
       var sql = """
                UPDATE aircraft
                SET image = ?
                WHERE id = 1;
                """;
        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            var blob = connection.createBlob();
            blob.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing777.jpg")));

            preparedStatement.setBlob(1, blob);
            preparedStatement.executeUpdate();
            connection.commit();
        }
    }
*/
}
