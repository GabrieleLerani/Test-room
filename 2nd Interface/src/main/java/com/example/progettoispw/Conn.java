package com.example.progettoispw;

import java.sql.*;

public class Conn {
    private static Conn instance=null;
    private String USER = "Progetto";
    private String PASS = "";
    private String DB_URL = "jdbc:mysql://localhost:3306/progettoispw-db";
    private String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private Statement stmt = null;
    private Connection conn = null;

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
            Class.forName(DRIVER_CLASS_NAME);

            // STEP 3: apertura connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 4: creazione ed esecuzione della query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return conn;
    }
}
