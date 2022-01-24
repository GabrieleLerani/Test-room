package com.example.progettoispw;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterDAO {
    private int i;
    private Conn con;
    private Connection conn;
    private static RegisterDAO instance=null;

    private RegisterDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static RegisterDAO getInstance(){
        if (RegisterDAO.instance == null)
            RegisterDAO.instance = new RegisterDAO();
        return instance;
    }

    public void registerdao(LogBean log) throws MyException {
        try {

            if(log.getCL().equalsIgnoreCase("beginner")){
                i=1;
            }else if(log.getCL().equalsIgnoreCase("intermediate")){
                i=2;
            }else if(log.getCL().equalsIgnoreCase("advanced")){
                i=3;
            }

            ResultSet rs = SimpleQueries.selectUserFromName(log.getUser(), conn);
            if (!rs.first()) { // rs empty
                SimpleQueries.insertUser(log.getUser(), log.getPass(), i, log.getEmail(), log.getSpec(), conn);
            }else{
                throw new MyException("User already registered: " + log.getUser());
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
