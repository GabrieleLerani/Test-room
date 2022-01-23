package com.example.progettoispw;

import com.example.progettoispw.RecipeModel.Ingredient;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WeeklyPlanDAO {
    private ArrayList<Recipe> main;
    private ArrayList<Recipe> side;
    private ArrayList<Recipe> dess;
    private ArrayList<Recipe> def;

    private ArrayList<ArrayList<Ingredient>> ingredients;
    private String type;
    private String cT;
    private String desc;
    private String ric;
    private String nome;
    private byte[] image;
    private Blob blob;
    private String allergies;
    private int cookingLevel;
    private String ck;
    private ArrayList<String> names;
    private ArrayList<String> ar;

    private Conn con;
    private Connection conn;
    private static WeeklyPlanDAO instance=null;

    private WeeklyPlanDAO(){
        con=Conn.getInstance();
        conn=con.connect();
        main=new ArrayList<>();
        side=new ArrayList<>();
        dess=new ArrayList<>();
        ar=new ArrayList<>();
        ingredients= new ArrayList<>();
        names=new ArrayList<>();
    }

    public static WeeklyPlanDAO getInstance(){
        if(WeeklyPlanDAO.instance==null)
            WeeklyPlanDAO.instance=new WeeklyPlanDAO();
        return instance;
    }

    public boolean getFromDB(String username) {
        boolean b;
        try {
            ResultSet rs = SimpleQueries.getSpecFromName(username, conn);
            if (!rs.first()) {
                MyException e = new MyException("Non si dispone di nessuna specializzazione");
                throw e;
            }
            String spec = rs.getString("Specializzazione");
            if (spec.equals("Premium")) {
                b = true;
            } else {
                b = false;
            }
            return b;
        }catch (MyException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Recipe> getGen(String diff, String CL, String username, String AP){
        int num;
        ResultSet rs = null;
        ResultSet pq;

        try {
            num=instance.checkCL(CL,username);
            names.clear();
            ar.clear();
            ingredients.clear();

            if(diff.equalsIgnoreCase("main")) {
                main.clear();
                rs = SimpleQueries.getFromType("Main dish", conn);
            }else if(diff.equalsIgnoreCase("side")){
                side.clear();
                rs = SimpleQueries.getFromType("Side dish", conn);
            }else if(diff.equalsIgnoreCase("dess")){
                dess.clear();
                rs = SimpleQueries.getFromType("Dessert", conn);
            }

            if (!rs.first()) {
                MyException e = new MyException("Non si dispone di abbastanza ricette");
                throw e;
            }

            num=0;
            int z=0;

            do {
                // lettura delle colonne "by ricetta"
                ric=rs.getString("Ricetta");
                nome=rs.getString("Nome");
                allergies=rs.getString("Allergies");
                type=rs.getString("Type");
                desc=rs.getString("Description");
                cT=rs.getString("Tempo");
                cookingLevel=rs.getInt("Livello");
                ck=this.checkCL(cookingLevel);

                do{
                    ingredients.add(new ArrayList<>());
                    if(rs.getString("Ricetta").equals(ric)){
                        ingredients.get(z).add(new Ingredient(rs.getString("Ingrediente"), rs.getString("Ammontare")));
                    }else{
                        break;
                    }
                }while(rs.next());
                rs.previous();

                if(num==0 || !names.get(num-1).equals(nome) || !ar.get(num-1).equals(ric)) { //controllo che l'ultimo nome non sia lo stesso che si userà per ricavare l'immagine
                    pq = SimpleQueries.getImageFromChef(ric, nome, conn);
                    if (!pq.first()) {
                        MyException e = new MyException("Immagine non trovata");
                        throw e;
                    }
                    blob = pq.getBlob("IMG");

                    image = blob.getBytes(1, (int) blob.length());

                    if(diff.equalsIgnoreCase("main")) {
                        main.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                        StringTokenizer st = new StringTokenizer(allergies);
                        while (st.hasMoreTokens()) {
                            main.get(num).addAll(st.nextToken());
                        }
                    }else if(diff.equalsIgnoreCase("side")){
                        side.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                        StringTokenizer st = new StringTokenizer(allergies);
                        while (st.hasMoreTokens()) {
                            side.get(num).addAll(st.nextToken());
                        }
                    }else if(diff.equalsIgnoreCase("dess")){
                        dess.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                        StringTokenizer st = new StringTokenizer(allergies);
                        while (st.hasMoreTokens()) {
                            dess.get(num).addAll(st.nextToken());
                        }
                    }

                    num++;
                    z++;

                    pq.close();
                    names.add(nome);
                    ar.add(ric);
                }
            } while (rs.next());
            rs.close();

        }catch (MyException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        if(diff.equalsIgnoreCase("main")) {
            def=main;
        }else if(diff.equalsIgnoreCase("side")){
            def=side;
        }else if(diff.equalsIgnoreCase("dess")){
            def=dess;
        }else{
            def=null;
        }
        return def;
    }

    private int checkCL(String CL, String username){
        int i = 0;
        if(CL.toLowerCase().equals("beginner")){
            i=1;
        }else if(CL.toLowerCase().equals("intermediate")){
            i=2;
        }else if(CL.toLowerCase().equals("advanced")){
            i=3;
        }
        return i;
    }

    private String checkCL(int i){
        String level = "";
        if(i==1){
            level="Beginner";
        }else if(i==2){
            level="Intermediate";
        }else if(i==3){
            level="Advanced";
        }
        return level;
    }
}
