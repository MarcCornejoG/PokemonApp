package practica10.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverHelper {


    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("rutaABaseDeDatos",
                "root", "contraseña");
        return conn;
    }

}
