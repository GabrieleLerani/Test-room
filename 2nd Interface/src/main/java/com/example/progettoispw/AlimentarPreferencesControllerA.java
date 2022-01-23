package com.example.progettoispw;

import java.io.IOException;
import java.util.ArrayList;

public class AlimentarPreferencesControllerA {
    private AlimentarDAO dao;
    private Login login;
    private ArrayList<String> pref;
    private FileInterDAO filedao;

    public AlimentarPreferencesControllerA() throws IOException, ClassNotFoundException {
        dao=AlimentarDAO.getInstance();
        filedao=FileInterDAO.getInstance();
        login=filedao.ReadLog();
    }

    public void setPref(String pref, ArrayList<String> all) throws IOException {
        if(pref.equalsIgnoreCase("I have no particular preferences")){
            pref="None";
        }
        dao.insertAP(login.getUser(), pref, all);
        if(!pref.equalsIgnoreCase("")) {
            login.setAP(pref);
        }
        if(!all.isEmpty()) {
            login.deleteAll();
            for (int i = 0; i < all.size(); i++) {
                login.addAll(all.get(i));
            }
        }
        filedao.WriteLog(login);
    }

    public String getPref(){
        String pref=dao.getPref(login.getUser());
        return pref;
    }

    public ArrayList<String> getAll(){
        ArrayList<String> all=dao.getAll(login.getUser());
        return all;
    }

    public void clear(){
        dao.clear(login.getUser());
    }
}
