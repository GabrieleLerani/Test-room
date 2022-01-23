package com.example.progettoispw;

import java.sql.*;

public class CookingLevelDAO {
    private int num;
    private static CookingLevelDAO instance=null;
    private Conn con;
    private Connection conn;

    private CookingLevelDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static CookingLevelDAO getInstance(){
        if (CookingLevelDAO.instance == null)
            CookingLevelDAO.instance = new CookingLevelDAO();
        return instance;
    }

    public void insertCL(String CL, String username){
        try {
            switch (CL.toLowerCase()) {
                case "beginner" -> num = 1;
                case "intermediate" -> num = 2;
                case "advanced" -> num = 3;
            }

            SimpleQueries.insertCookingLevel(num, username, conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
