package org.university.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/university_db";

    private static final String USER = "root";

    // Replace with your actual MySQL password.
    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    private DatabaseUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}