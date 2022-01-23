package com.example.progettoispw;

import java.sql.*;
import java.util.ArrayList;

public class AlimentarDAO {
    private String AP;
    private ArrayList<String> all;
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

    public void insertAP(String username, String pref, ArrayList<String> all) {
        ResultSet rs;

        try {
            if(!pref.equals("")) {
                SimpleQueries.insertAlimentarPreferences(username, pref, conn);
            }
            if (!all.isEmpty()) {
                if (!all.get(0).equals("No allergies")) {
                    for (int i = 0; i < all.size(); i++) {
                        SimpleQueries.insertAllergies(username, all.get(i), conn);
                    }
                } else {
                    rs = SimpleQueries.getAllergies(username, conn);
                    if (!rs.first()) {
                        MyException e = new MyException("No allergies detected");
                        throw e;
                    }
                    rs.first();
                    if (!rs.getString("Allergy").equals("")) {
                        SimpleQueries.deleteAllergies(username, conn);
                    }
                    rs.close();
                }
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            //per far sì che non si aggiunga due volte la stessa AP
            all.remove(0);
            System.out.println("Alimentar Preference already inserted");
            instance.insertAP(username, pref, all);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public String getPref(String username){
        try {
            ResultSet rs=SimpleQueries.selectUserFromName(username, conn);
            rs.first();
            AP=rs.getString("AlimentarPreferences");
            return AP;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getAll(String username) {
        all=new ArrayList<>();

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
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
}
