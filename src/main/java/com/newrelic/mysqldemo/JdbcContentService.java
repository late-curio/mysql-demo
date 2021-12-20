package com.newrelic.mysqldemo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class JdbcContentService {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/spring_app_db?useSSL=false&user=root&password=root";
    private final AtomicInteger idGenerator = new AtomicInteger(100);
    private final AtomicInteger preparedIdGenerator = new AtomicInteger(1000);

    public int createContentViaPreparedStatement(String content) throws IOException {
        Connection conn = null;
        PreparedStatement statement = null;
        int id = idGenerator.incrementAndGet();
        try {
            conn = DriverManager.getConnection(JDBC_URL);
            statement = conn.prepareStatement("insert into content(id, content) values(?, ?)");
            statement.setInt(1, preparedIdGenerator.getAndIncrement());
            statement.setString(2, content);
            statement.execute();
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
                    statement = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public int createContentViaStatementAndManualSqlConcatenation(String content) throws IOException {
        Connection conn = null;
        Statement statement = null;
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
                    statement = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}
