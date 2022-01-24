package com.example.progettoispw;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Login enter(String user) {
        try {
            ResultSet rs = SimpleQueries.selectUserFromName(user, conn);
            if (!rs.first()) { // rs empty
                throw new MyException("No User Found matching with name: " + user);
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
        } catch (MyException e) {
            e.printStackTrace();
        }
        return a;
    }
}
