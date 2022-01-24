package com.example.progettoispw;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotDAO {
    private String password;
    private Conn con;
    private Connection conn;
    private static ForgotDAO instance=null;

    private ForgotDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static ForgotDAO getInstance(){
        if (ForgotDAO.instance == null)
            ForgotDAO.instance = new ForgotDAO();
        return instance;
    }

    public String passRec(String email) throws ExceptionPass {
        try {
            ResultSet rs = SimpleQueries.getPassFromEmail(email, conn);
            if (!rs.first()) { // rs empty
                throw new MyException("No Password Found matching with Email: " + email);
            }
            rs.first();
            password = rs.getString("Password");
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MyException e) {
            throw new ExceptionPass(email, e.getMessage());
        }
        return password;
    }
}
