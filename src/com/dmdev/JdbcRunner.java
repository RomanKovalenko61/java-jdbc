package com.dmdev;

import com.dmdev.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;
        String sql = """
                 SELECT *
                 FROM ticket
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            System.out.println(connection.getSchema());
            System.out.println(connection.getTransactionIsolation());

            var executedResult = statement.executeQuery(sql);
            while (executedResult.next()) {
                System.out.println(executedResult.getLong("id"));
                System.out.println(executedResult.getString("passenger_no"));
                System.out.println(executedResult.getBigDecimal("cost"));
//                executedResult.updateLong("id", 1000); лучше такого не делать, нужно включать этот режим (ResultSet.CONCUR_UPDATABLE)
//                ResultSet.TYPE_SCROLL_INSENSITIVE можем прокручивать вперед/назад, будем видеть измененные строки во время итерированная или нет
                System.out.println("-------");
            }
        }
    }
}
