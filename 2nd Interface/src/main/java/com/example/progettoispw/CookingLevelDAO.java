package com.example.progettoispw;

import java.sql.Connection;
import java.sql.SQLException;

public class CookingLevelDAO {
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

    public void insertCL(String cl, String username){
        int num=0;
        try {
            if (cl.toLowerCase().equalsIgnoreCase("beginner")) {
                num = 1;
            } else if (cl.toLowerCase().equalsIgnoreCase("intermediate")) {
                num = 2;
            }else if(cl.toLowerCase().equalsIgnoreCase("advanced")){
                num = 3;
            }

            SimpleQueries.insertCookingLevel(num, username, conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
