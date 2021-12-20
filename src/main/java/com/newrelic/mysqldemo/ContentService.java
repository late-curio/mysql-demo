package com.newrelic.mysqldemo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ContentService {

    private AtomicInteger idGenerator = new AtomicInteger();

    public int manualContentSave(String content) throws IOException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int id = idGenerator.incrementAndGet();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_app_db?useSSL=false&user=root&password=root");
            statement = conn.createStatement();
            String replacement = "\\'";
            System.out.println("REPLACEMENT ***" + replacement + "***");
            content = content.replace("'", replacement);
            System.out.println("NEWCONTENT=[" + content + "]");
            statement.execute("insert into content(id, content) values (" + id + ", '" + content + "')");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return -1;
        }
        finally {
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
        return id;
    }
}
