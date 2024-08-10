package utez.edu.mx.market.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String database = "market";
            String user = "root";
            String password = "root";

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database, user, password
            );
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        db.getConnection();
    }
}
