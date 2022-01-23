package com.example.progettoispw;

import java.sql.*;

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
                Exception e = new Exception("No Password Found matching with Email: " + email);
                throw e;
            }
            rs.first();
            password = rs.getString("Password");
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            ExceptionPass ex=new ExceptionPass(email, e.getMessage());
            throw ex;
        }
        return password;
    }
}
