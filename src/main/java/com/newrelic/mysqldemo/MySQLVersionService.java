package com.newrelic.mysqldemo;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class MySQLVersionService {

    public String getVersion() {
        Connection conn = null;
        String databaseProductVersion = null;
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_app_db?allowPublicKeyRetrieval=true&useSSL=false&user=root&password=root");
            DatabaseMetaData metaData = conn.getMetaData();
            databaseProductVersion = metaData.getDatabaseProductVersion();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return databaseProductVersion;
    }
}
