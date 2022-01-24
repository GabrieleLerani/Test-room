package com.example.progettoispw;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentDAO {
    private static PaymentDAO instance=null;
    private Conn con;
    private Connection conn;

    private PaymentDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static PaymentDAO getInstance(){
        if (PaymentDAO.instance == null)
            PaymentDAO.instance = new PaymentDAO();
        return instance;
    }

    public void insertPremiumUser(String username){
        try {
            SimpleQueries.upgradePremium(username, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
