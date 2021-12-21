package com.newrelic.mysqldemo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class JdbcContentService {

    private static final String SQL = "insert into content(id, content) values(?, ?)";
    private static final String UPDATE_SQL = "update content set content=? where id=?";

    private final AtomicInteger preparedIdGenerator = new AtomicInteger(100);
    private final AtomicInteger idGenerator = new AtomicInteger(1000);

    private final JdbcTemplate jdbcTemplate;

    public JdbcContentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createContentViaPreparedStatement(String content) {
        int id = preparedIdGenerator.incrementAndGet();
        jdbcTemplate.update(SQL, id, content);
        return id;
    }

    public int updateContentViaPreparedStatement(int id, String content) {
        jdbcTemplate.update(UPDATE_SQL, content, id);
        return id;
    }

    public int createContentViaStatementAndManualSqlConcatenation(String content) {
        int id = idGenerator.incrementAndGet();
        DataSource dataSource = Objects.requireNonNull(jdbcTemplate.getDataSource());
        try(Statement statement = dataSource.getConnection().createStatement()) {
            String replacement = "\\'";
            String contentWithEscapedSingleQuotes = content.replace("'", replacement);
            statement.execute("insert into content(id, content) values (" + id + ", '" + contentWithEscapedSingleQuotes + "')");
        } catch (SQLException ex) {
            printSQLException(ex);
            return -1;
        }
        return id;
    }

    private static void printSQLException(SQLException ex) {
        System.err.println("SQLException: " + ex.getMessage());
        System.err.println("SQLState: " + ex.getSQLState());
        System.err.println("VendorError: " + ex.getErrorCode());
    }

}
