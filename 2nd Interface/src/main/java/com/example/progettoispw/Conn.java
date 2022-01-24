package com.example.progettoispw;

import java.sql.*;

public class Conn {
    private static Conn instance=null;
    private static String user = "Progetto";
    private static String pass = "";
    private static String dbUrl = "jdbc:mysql://localhost:3306/progettoispw-db";
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private Connection connection = null;

    private Conn(){
    }

    public static Conn getInstance(){
        if (Conn.instance == null)
            Conn.instance = new Conn();
        return instance;
    }

    public Connection connect() {
        // STEP 2: loading dinamico del driver mysql
        try {
            Class.forName(driverClassName);

            // STEP 3: apertura connessione
            connection = DriverManager.getConnection(dbUrl, user, pass);

            // STEP 4: creazione ed esecuzione della query
            connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return connection;
    }
}
