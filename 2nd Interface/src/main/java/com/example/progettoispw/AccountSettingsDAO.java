package com.example.progettoispw;

import java.sql.*;
import java.util.Locale;

public class AccountSettingsDAO {
    private MyException e;
    private Conn con;
    private Connection conn;
    private static AccountSettingsDAO instance=null;

    private AccountSettingsDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public synchronized static AccountSettingsDAO getInstance(){
        if (AccountSettingsDAO.instance == null)
            AccountSettingsDAO.instance = new AccountSettingsDAO();
        return instance;
    }

    public void changeUser(String nameSet, String oldName) {
        try {
            SimpleQueries.setUserFromName(nameSet, oldName, conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changePass(String username, String pass) throws Exception {
        try {
            if(pass.equals("")){
                System.out.println("Password non valida");
                e=new MyException(pass);
                throw e;
            }

            SimpleQueries.setPassFromName(pass, username, conn); //password cambiata
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MyException e){
            e.printStackTrace();
            ExceptionPass ex=new ExceptionPass(pass, e.getMessage());
            throw  ex; //password non valida
        }
    }
}
