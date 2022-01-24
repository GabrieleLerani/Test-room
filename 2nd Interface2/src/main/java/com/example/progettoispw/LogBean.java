package com.example.progettoispw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogBean implements Serializable {
    private String user;
    private String pass;
    private String spec;
    private String email;
    private String cl;
    private int check=0;
    private String ap;
    private ArrayList<String> all=new ArrayList<>();
    private boolean prem=false;

    public LogBean(){
    }

    public LogBean(String user, String pass){
        this.user=user;
        this.pass=pass;
    }

    public LogBean(String user, String pass, String spec, String email){
        this.user=user;
        this.pass=pass;
        this.spec=spec;
        this.email=email;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user= user;
    }

    public String getPass(){
        return pass;
    }

    public void setPass(String pass){
        this.pass= pass;
    }

    public String getCL() { return cl; }

    public void setCL(String cl){
        this.cl= cl;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email= email;
    }

    public String getSpec() { return spec; }

    public void setSpec(String spec) { this.spec= spec; }

    public int getCheck(){ return check; }

    public void setCheck(){ this.check =1; }

    public void desetCheck() { check =0; }

    public void setAP(String ap){ this.ap=ap; }

    public String getAP(){ return ap; }

    public void addAll(String all){ this.all.add(all); }

    public List<String> getAll(){ return all; }

    public void setPremium(){ prem=true; }

    public void desetPremium(){ prem=false; }

    public boolean getPremium(){ return prem; }
}
