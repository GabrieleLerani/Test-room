package com.example.progettoispw;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AlimentarDAO {
    private Conn con;
    private Connection conn;
    private static AlimentarDAO instance=null;

    private AlimentarDAO(){
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static AlimentarDAO getInstance(){
        if (AlimentarDAO.instance == null)
            AlimentarDAO.instance = new AlimentarDAO();
        return instance;
    }

    public void insertAP(String username, String pref, List<String> all) {
        ResultSet rs;

        try {
            if (!pref.equals("")) {
                SimpleQueries.insertAlimentarPreferences(username, pref, conn);
            }
            if (!all.isEmpty()) {
                if (!all.get(0).equals("No allergies")) {
                    this.insertAll(username, all, conn, pref);
                } else {
                    rs = SimpleQueries.getAllergies(username, conn);
                    if (!rs.first()) {
                        throw new MyException("No allergies detected");
                    }
                    rs.first();
                    if (!rs.getString("Allergy").equals("")) {
                        SimpleQueries.deleteAllergies(username, conn);
                    }
                    rs.close();
                }
            }
        }catch (MyException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPref(String username){
        String ap;
        try {
            ResultSet rs=SimpleQueries.selectUserFromName(username, conn);
            rs.first();
            ap=rs.getString("AlimentarPreferences");
            return ap;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAll(String username) {
        List<String> all=new ArrayList<>();

        try {
            ResultSet rs=SimpleQueries.getAllergies(username, conn);
            if(!rs.first()){
                all.add("No Allergies");
            }else {
                rs.first();
                do {
                    // lettura delle colonne "by ap"
                    all.add(rs.getString("Allergy"));
                } while (rs.next());
                rs.close();
            }
            return all;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void clear(String username){
        try {
            SimpleQueries.deleteAllergies(username, conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertAll(String username, List<String> all, Connection conn, String pref){
        int j=0;
        for (int i = 0; i < all.size(); i++) {
            Logger logger=Logger.getLogger(AlimentarDAO.class.getName());
            try {
                SimpleQueries.insertAllergies(username, all.get(i), conn);
            } catch (SQLIntegrityConstraintViolationException e) {
                //per far sì che non si aggiunga due volte la stessa AP
                all.remove(j);
                logger.log(Level.INFO, "Alimentar Preference already inserted");
                instance.insertAP(username, pref, all);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
