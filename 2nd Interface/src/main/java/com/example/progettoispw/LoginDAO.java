package com.example.progettoispw;

import java.sql.*;

public class LoginDAO {
    private static LoginDAO instance=null;
    private Conn con;
    private Connection conn;
    private Login a;

    private LoginDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static LoginDAO getInstance(){
        if (LoginDAO.instance == null)
            LoginDAO.instance = new LoginDAO();
        return instance;
    }

    public Login Login(String user) {
        try {
            ResultSet rs = SimpleQueries.selectUserFromName(user, conn);
            if (!rs.first()) { // rs empty
                Exception e = new Exception("No User Found matching with name: " + user);
                throw e;
            }
            rs.first();
            do {
                // lettura delle colonne "by name"
                String password = rs.getString("Password");
                String spec = rs.getString("Specializzazione");
                String email=rs.getString("Email");

                a = new Login(user, password, spec, email);
            } while (rs.next());
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
