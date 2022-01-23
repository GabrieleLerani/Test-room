package com.example.progettoispw;

import java.io.*;
import java.util.ArrayList;

public class FileInterDAO {
    private static String username;
    private static File file;
    private static File filee;
    private static File f;
    private static File read;
    private static File foo;
    private static ArrayList<File> plan;
    private static FileInterDAO instance=null;

    private FileInterDAO(){
        plan=new ArrayList<>();
        for(int i=0; i<8; i++){
            plan.add(null);
        }
    }

    public static FileInterDAO getInstance(){
        if(FileInterDAO.instance==null)
            FileInterDAO.instance=new FileInterDAO();
        return instance;
    }

    private void writeCurrent(String username) throws IOException {
        f=new File("UtenteCorrente.dat");
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(username);
        objectOut.close();
        fileOut.close();
    }

    private String readCurrent() throws IOException, ClassNotFoundException {
        f = new File("UtenteCorrente.dat");
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        username = (String) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return username;
    }

    public void deleteCurrent(){
        f=new File("UtenteCorrente.dat");
        f.delete();
    }

    public void WriteLog(Login login) throws IOException {
        file=new File("Utente"+login.getUser()+".dat");
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(login);
        objectOut.close();
        fileOut.close();
        this.writeCurrent(login.getUser());
        System.out.println("Scrittura completata!");
    }

    public Login ReadLog() throws IOException, ClassNotFoundException {
        try {
            String user=this.readCurrent();
            file = new File("Utente"+user+".dat");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Login login = (Login) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Lettura completata!");
            return login;
        }catch(FileNotFoundException e) {
            return null;
        }
    }

    public ArrayList<Recipe> readRecipe() throws IOException, ClassNotFoundException {
        try {
            file = new File("Recipes.dat");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Recipe> recipe = (ArrayList<Recipe>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Lettura completata!");
            return recipe;
        }catch(FileNotFoundException e) {
            return null;
        }
    }

    public void writeRecipe(Recipe recipe) throws IOException, ClassNotFoundException {
        filee=new File("Recipes.dat");
        ArrayList<Recipe> recipes= this.readRecipe();
        if(recipes==null){
            recipes=new ArrayList<>();
        }
        recipes.add(recipe);
        FileOutputStream fileOut = new FileOutputStream(filee);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(recipes);
        objectOut.close();
        fileOut.close();
        System.out.println("Scrittura completata!");
    }

    public void deleteRecipes() throws IOException {
        filee=new File("Recipes.dat");
        filee.delete();
        System.out.println("Eliminazione completata!");
    }

    public ArrayList<Recipe> readSaved() throws IOException, ClassNotFoundException {
        try {
            file = new File("Saved.dat");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Recipe> recipe = (ArrayList<Recipe>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Lettura completata!");
            return recipe;
        }catch(FileNotFoundException e) {
            return null;
        }
    }

    public void writeSaved(Recipe recipe) throws IOException, ClassNotFoundException {
        filee=new File("Saved.dat");
        ArrayList<Recipe> recipes= this.readSaved();
        if(recipes==null){
            recipes=new ArrayList<>();
        }
        recipes.add(recipe);
        FileOutputStream fileOut = new FileOutputStream(filee);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(recipes);
        objectOut.close();
        fileOut.close();
        System.out.println("Scrittura completata!");
    }

    public void deleteSaved() throws IOException {
        filee=new File("Saved.dat");
        filee.delete();
        System.out.println("Eliminazione completata!");
    }

    public void writePlan(int day, ArrayList<Meal> meals) throws IOException, ClassNotFoundException {
        plan.add(day, new File("Day"+day+".dat"));
        FileOutputStream fileOut = new FileOutputStream(plan.get(day));
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(meals);
        objectOut.close();
        fileOut.close();
        System.out.println("Scrittura completata!");
    }

    public ArrayList<Meal> readPlan(int day) throws IOException, ClassNotFoundException {
        try {
            File temp=new File("Day"+day+".dat");
            if(!temp.exists()){
                MyException e=new MyException("File non ancora creato");
                throw e;
            }
            plan.set(day, temp);
            read = plan.get(day);
            FileInputStream fileIn = new FileInputStream(read);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Meal> meals = (ArrayList<Meal>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Lettura completata!");
            return meals;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        } catch (MyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeChef(Recipe recipe) throws IOException, ClassNotFoundException {
        foo=new File("MyList.dat");
        ArrayList<Recipe> recipes=this.readChef();
        if(recipes==null){
            recipes=new ArrayList<>();
        }
        recipes.add(recipe);
        FileOutputStream fileOut = new FileOutputStream(foo);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(recipes);
        objectOut.close();
        fileOut.close();
        System.out.println("Scrittura completata!");
    }

    public ArrayList<Recipe> readChef() throws IOException, ClassNotFoundException {
        try {
            foo=new File("MyList.dat");
            FileInputStream fileIn = new FileInputStream(foo);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Recipe> recipes = (ArrayList<Recipe>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Lettura completata!");
            return recipes;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
