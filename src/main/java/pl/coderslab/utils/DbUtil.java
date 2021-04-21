package pl.coderslab.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String URL = "jdbc:mysql://localhost:3306/workshop3?serverTimezone=UTC";
        String USER = "root";
        String PASS = "coderslab";

        Connection connection = DriverManager.getConnection(URL, USER, PASS);

        return connection;
    }


//    private static DataSource dataSource;
//    public static Connection getConnection() throws SQLException {
//        return getInstance().getConnection();   }
//    private static DataSource getInstance() {
//        if (dataSource == null) {
//            try {
//                Context initContext = new InitialContext();
//                Context envContext = (Context)initContext.lookup("java:/comp/env");
//                dataSource = (DataSource)envContext.lookup("jdbc/users");
//            } catch (NamingException e) { e.printStackTrace(); }
//        }
//        return dataSource;
//    }

}
