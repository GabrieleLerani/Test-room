package com.example.progettoispw;

import java.sql.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountSettingsDAO {
    private Conn con;
    private Connection conn;
    private static Logger logger=Logger.getLogger(AccountSettingsDAO.class.getName());
    private static AccountSettingsDAO instance=null;

    private AccountSettingsDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static synchronized AccountSettingsDAO getInstance(){
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

    public void changePass(String username, String pass) throws ExceptionPass {
        try {
            MyException e;
            if(pass.equals("")){
                logger.log(Level.INFO, "Password non valida");
                e=new MyException(pass);
                throw e;
            }

            SimpleQueries.setPassFromName(pass, username, conn); //password cambiata
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MyException e){
            e.printStackTrace();
            throw new ExceptionPass(pass, e.getMessage()); //password non valida
        }
    }
}
