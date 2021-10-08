package com.newrelic.mysqldemo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;

@Service
public class MyService {

    private final ReportGenerator generator;

    public MyService(ReportGenerator reportGenerator) {
        this.generator = reportGenerator;
    }

    public long doThings() throws IOException {
        long start = System.currentTimeMillis();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_app_db?useSSL=false&user=root&password=root");

            statement = conn.createStatement();
            resultSet = statement.executeQuery("Select * from Persons");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        generator.log(end, duration);
        System.out.println("Got results in " + duration + "ms");
        return duration;
    }
}
