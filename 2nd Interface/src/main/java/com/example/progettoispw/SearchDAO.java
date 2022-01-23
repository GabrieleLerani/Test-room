package com.example.progettoispw;

import com.example.progettoispw.RecipeModel.Ingredient;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SearchDAO extends SubjectSearchDAO{
    private ArrayList<Recipe> recipes;
    private ArrayList<Recipe> states;
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
    private int state=0;
    private int internalstate=0;
    private Conn con;
    private Connection conn;
    private static SearchDAO instance=null;

    private SearchDAO(){
        recipes=new ArrayList<>();
        names=new ArrayList<>();
        states=new ArrayList<>();
        ar=new ArrayList<>();
        ingredients= new ArrayList<>();
        con=Conn.getInstance();
        conn=con.connect();
    }

    public static SearchDAO getInstance(){
        if (SearchDAO.instance == null)
            SearchDAO.instance = new SearchDAO();
        return instance;
    }

    public ArrayList<Recipe> searchRec(String name, String CL, String AP, String username) throws Exception{
        int num;

        try {
            num=instance.checkCL(CL,username);
            if(AP==null){
                AP=instance.getAP(username);
            }
            recipes.clear();
            names.clear();
            ingredients.clear();

            ResultSet rs=SimpleQueries.getRecipeFromNameCLAPAll(name, num, AP, conn);
            ResultSet pq=SimpleQueries.getImage(name, conn);
            if(!rs.first() || !pq.first()){
                MyException e = new MyException("Ricetta o immagine non trovata");
                throw e;
            }
            rs.first();
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

                do {
                    ingredients.add(new ArrayList<>());
                    if(rs.getString("Ricetta").equals(ric)){
                        ingredients.get(z).add(new Ingredient(rs.getString("Ingrediente"), rs.getString("Ammontare")));
                    }else{
                        break;
                    }
                }while(rs.next());
                rs.previous();
                blob=pq.getBlob("IMG");

                image=blob.getBytes(1, (int) blob.length());
                recipes.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                StringTokenizer st = new StringTokenizer(allergies);
                while (st.hasMoreTokens()) {
                    recipes.get(num).addAll(st.nextToken());
                }
                num++;
                z++;
            } while (rs.next() && pq.next());
            rs.close();
            pq.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        state++;
        return recipes;
    }

    public ArrayList<Recipe> searchRecipe(String time, String CL, String AP, String username) throws Exception{
        int num;
        ResultSet pq;

        try {
            num=instance.checkCL(CL,username);
            if(AP==null){
                AP=instance.getAP(username);
            }
            recipes.clear();
            names.clear();
            ar.clear();
            ingredients.clear();

            ResultSet rs=SimpleQueries.getRecipeFromTimeCLAPAll(Integer.parseInt(time), num, AP, conn);
            if(!rs.first()){
                MyException e = new MyException("Ricetta non trovata");
                throw e;
            }
            rs.first();
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
                    recipes.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                    StringTokenizer st = new StringTokenizer(allergies);
                    while (st.hasMoreTokens()) {
                        recipes.get(num).addAll(st.nextToken());
                    }
                    num++;
                    z++;

                    pq.close();
                    names.add(nome);
                    ar.add(ric);
                }
            } while (rs.next());
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        state++;
        return recipes;
    }

    public ArrayList<Recipe> searchRecipeIngr(String ingr, String CL, String AP, String username) throws Exception{
        int num;
        ResultSet pq;

        try {
            num=instance.checkCL(CL,username);
            if(AP==null){
                AP=instance.getAP(username);
            }
            recipes.clear();
            names.clear();
            ar.clear();
            ingredients.clear();

            ResultSet rs=SimpleQueries.getRecipeFromIngrCLAPAll(ingr, num, AP, conn);
            if(!rs.first()){
                MyException e = new MyException("Ricetta non trovata");
                throw e;
            }
            rs.first();
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

                do {
                    ingredients.add(new ArrayList<>());
                    if(rs.getString("Ricetta").equals(ric)){
                        ingredients.get(z).add(new Ingredient(rs.getString("Ingrediente"), rs.getString("Ammontare")));
                    }else{
                        break;
                    }
                }while(rs.next());
                rs.previous();

                if(num==0 || !names.get(num-1).equals(nome) || !ar.get(num-1).equals(ric)) {
                    pq = SimpleQueries.getImageFromChef(ric, nome, conn);
                    if (!pq.first()) {
                        MyException e = new MyException("Immagine non trovata");
                        throw e;
                    }
                    blob = pq.getBlob("IMG");

                    image = blob.getBytes(1, (int) blob.length());
                    recipes.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                    StringTokenizer st = new StringTokenizer(allergies);
                    while (st.hasMoreTokens()) {
                        recipes.get(num).addAll(st.nextToken());
                    }
                    num++;
                    z++;

                    pq.close();
                    names.add(nome);
                    ar.add(ric);
                }
            } while (rs.next());
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        state++;
        return recipes;
    }

    public ArrayList<Recipe> searchRecipeType(String type, String CL, String AP, String username) throws Exception{
        int num;
        ResultSet pq;

        try {
            num=instance.checkCL(CL,username);
            if(AP==null){
                AP=instance.getAP(username);
            }
            recipes.clear();
            names.clear();
            ar.clear();
            ingredients.clear();

            ResultSet rs=SimpleQueries.getRecipeFromTypeCLAPAll(type, num, AP, conn);
            if(!rs.first()){
                MyException e = new MyException("Ricetta non trovata");
                throw e;
            }
            rs.first();
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

                if(num==0 || !names.get(num-1).equals(nome) || !ar.get(num-1).equals(ric)) {
                    pq = SimpleQueries.getImageFromChef(ric, nome, conn);
                    if (!pq.first()) {
                        MyException e = new MyException("Immagine non trovata");
                        throw e;
                    }
                    blob = pq.getBlob("IMG");

                    image = blob.getBytes(1, (int) blob.length());
                    recipes.add(new Recipe(ric, nome, image, type, ck, desc, cT, ingredients.get(z)));
                    StringTokenizer st = new StringTokenizer(allergies);
                    while (st.hasMoreTokens()) {
                        recipes.get(num).addAll(st.nextToken());
                    }
                    num++;
                    z++;

                    pq.close();
                    names.add(nome);
                    ar.add(ric);
                }
            } while (rs.next());
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        state++;
        return recipes;
    }

    private int checkCL(String CL, String username){
        int i = 0;
        if(CL==null){
            i=instance.getCL(username);
        }else if(CL.toLowerCase().equals("beginner")){
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

    private int getCL(String username) {
        int i=0;

        try {
            ResultSet rs = SimpleQueries.selectUserFromName(username, conn);
            if (!rs.first()) {
                i = 0;
            } else {
                i = rs.getInt("CookingLevel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private String getAP(String username){
        String ap = null;

        try {
            ResultSet rs=SimpleQueries.selectUserFromName(username, conn);
            rs.first();
            ap=rs.getString("AlimentarPreferences");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ap;
    }

    @Override
    protected boolean isThereAnythingToNotify(){
        boolean b= internalstate<state;
        internalstate=state;
        if(b) {
            this.doSomething();
        }
        return b;
    }

    protected void doSomething(){
        states=recipes;
    }

    public ArrayList<Recipe> getState(){
        return states;
    }
}
