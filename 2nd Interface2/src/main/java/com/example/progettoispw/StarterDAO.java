package com.example.progettoispw;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StarterDAO {
    private Conn con;
    private Connection conn;
    private static StarterDAO instance=null;

    private StarterDAO (){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static StarterDAO getInstance(){
        if(StarterDAO.instance==null)
            StarterDAO.instance=new StarterDAO();
        return instance;
    }

    public String getSpec(String username){
        try {
            MyException e=new MyException("Specializzazione non trovata");
            if(conn!=null) {
                ResultSet rs = SimpleQueries.getSpecFromName(username, conn);
                if(!rs.first()){
                    throw e;
                }
                return rs.getString("Specializzazione");
            }else{
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MyException e) {
            return null;
        }
    }
}
