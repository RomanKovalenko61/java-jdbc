package com.dmdev;

import com.dmdev.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;
        String sql = """
                 UPDATE info
                 SET data = 'TestTest'
                 WHERE id = 5
                 RETURNING *
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());
//            var executedResult = statement.executeUpdate(sql);
            var executedResult = statement.execute(sql);
            System.out.println(executedResult);
            System.out.println(statement.getUpdateCount());
        }
    }
}
