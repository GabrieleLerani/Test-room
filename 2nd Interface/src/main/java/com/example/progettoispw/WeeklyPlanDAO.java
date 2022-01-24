package com.example.progettoispw;

import com.example.progettoispw.RecipeModel.Ingredient;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WeeklyPlanDAO {
    private ArrayList<Recipe> main;
    private ArrayList<Recipe> side;
    private ArrayList<Recipe> dess;

    private ArrayList<ArrayList<Ingredient>> ingredients;
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
                throw new MyException("Non si dispone di nessuna specializzazione");
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

    public List<Recipe> getGen(String diff, String cl, String ap){
        ResultSet rs = null;

        try {
            names.clear();
            ar.clear();
            ingredients.clear();

            int cL=this.checkCL(cl);

            if(diff.equalsIgnoreCase("main")) {
                main.clear();
                rs = SimpleQueries.getRecipeFromTypeCLAPAll("Main dish", cL, ap, conn);
            }else if(diff.equalsIgnoreCase("side")){
                side.clear();
                rs = SimpleQueries.getRecipeFromTypeCLAPAll("Side dish", cL, ap, conn);
            }else if(diff.equalsIgnoreCase("dess")){
                dess.clear();
                rs = SimpleQueries.getRecipeFromTypeCLAPAll("Dessert", cL, ap, conn);
            }

            if (!rs.first()) {
                throw new MyException("Non si dispone di abbastanza ricette");
            }

            this.getThese(rs, diff);
            rs.close();

        }catch (MyException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Recipe> def;
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

    private int checkCL(String lv){
        int level = 0;
        if(lv.equalsIgnoreCase("Beginner")){
            level=1;
        }else if(lv.equalsIgnoreCase("Intermediate")){
            level=2;
        }else if(lv.equalsIgnoreCase("Advanced")){
            level=3;
        }
        return level;
    }

    private void getThese(ResultSet rs, String diff) throws SQLException, MyException {
        int num = 0;
        int z = 0;
        ResultSet pq;
        do {
            // lettura delle colonne "by ricetta"
            String ric = rs.getString("Ricetta");
            String nome = rs.getString("Nome");
            String allergies = rs.getString("Allergies");
            String type = rs.getString("Type");
            String desc = rs.getString("Description");
            String cT = rs.getString("Tempo");
            int cookingLevel = rs.getInt("Livello");
            String ck = this.checkCL(cookingLevel);

            do {
                ingredients.add(new ArrayList<>());
                if (rs.getString("Ricetta").equals(ric)) {
                    ingredients.get(z).add(new Ingredient(rs.getString("Ingrediente"), rs.getString("Ammontare")));
                } else {
                    break;
                }
            } while (rs.next());
            rs.previous();

            if (num == 0 || !names.get(num - 1).equals(nome) || !ar.get(num - 1).equals(ric)) { //controllo che l'ultimo nome non sia lo stesso che si userà per ricavare l'immagine
                pq = SimpleQueries.getImageFromChef(ric, nome, conn);
                if (!pq.first()) {
                    throw new MyException("Immagine non trovata");
                }
                Blob blob = pq.getBlob("IMG");

                byte[] image = blob.getBytes(1, (int) blob.length());

                if (diff.equalsIgnoreCase("main")) {
                    main.add(new Recipe(ric, image, type, ck, desc, cT, ingredients.get(z)));
                    main.get(num).setChef(nome);
                    this.all(main, num, allergies);
                } else if (diff.equalsIgnoreCase("side")) {
                    side.add(new Recipe(ric, image, type, ck, desc, cT, ingredients.get(z)));
                    side.get(num).setChef(nome);
                    this.all(side, num, allergies);
                } else if (diff.equalsIgnoreCase("dess")) {
                    dess.add(new Recipe(ric, image, type, ck, desc, cT, ingredients.get(z)));
                    dess.get(num).setChef(nome);
                    this.all(dess, num, allergies);
                }

                num++;
                z++;

                pq.close();
                names.add(nome);
                ar.add(ric);
            }
        } while (rs.next());
    }

    private void all(List<Recipe> m, int num, String allergies){
        StringTokenizer st = new StringTokenizer(allergies);
        while (st.hasMoreTokens()) {
            m.get(num).addAll(st.nextToken());
        }
    }
}
