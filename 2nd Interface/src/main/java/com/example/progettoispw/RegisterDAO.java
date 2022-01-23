package com.example.progettoispw;

import java.sql.*;

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

    public void registerdao(LogBean log) throws Exception{
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
                Exception e = new Exception("User already registered: " + log.getUser());
                throw e;
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
