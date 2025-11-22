package elearn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "system";   // or e_learn_user
    private static final String PASS = "system123"; // replace

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
