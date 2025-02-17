package practica10.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverHelper {


    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/practica10?serverTimezone=UTC",
                "root", "castelao");
        return conn;
    }

}
