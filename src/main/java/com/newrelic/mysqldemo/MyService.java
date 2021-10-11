package com.newrelic.mysqldemo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;

@Service
public class MyService {

    private final ReportGenerator generator;
    private final Object lock = new Object();
    private final static int ITERATIONS = 100;

    public MyService(ReportGenerator reportGenerator) {
        this.generator = reportGenerator;
    }

    public long doThings() {
        for (int i = 0; i < ITERATIONS; i++) {
            try {
                doThing();
            } catch (Exception e) {
                e.printStackTrace();
                i--; // retry essentially
            }
        }
        return ITERATIONS;
    }

    public long doThing() throws IOException {
        long start = System.currentTimeMillis();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String databaseProductVersion = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_app_db?useSSL=false&user=root&password=root");
            DatabaseMetaData metaData = conn.getMetaData();
            databaseProductVersion = metaData.getDatabaseProductVersion();
            conn.getMetaData().getDatabaseProductName();
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
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        synchronized (lock) {
            generator.log(end, duration, databaseProductVersion);
        }
        //System.out.println("Got results in " + duration + "ms");
        return duration;
    }
}
