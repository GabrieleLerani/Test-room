package com.example.progettoispw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileInterDAO {
    private static String fileCurrent="UtenteCorrente.dat";
    private static String fileRecipe="Recipes.dat";
    private static String fileSaved="Saved.dat";
    private static String scrittura="Scrittura completata!";
    private static String lettura="Lettura completata!";
    private File file;
    private File filee;
    private File f;
    private File foo;
    private ArrayList<File> plan;
    private static Logger logger=Logger.getLogger(FileInterDAO.class.getName());
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
        f=new File(fileCurrent);
        FileOutputStream fileOut = new FileOutputStream(f);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(username);
        objectOut.close();
        fileOut.close();
    }

    private String readCurrent() throws IOException, ClassNotFoundException {
        f = new File(fileCurrent);
        FileInputStream fileIn = new FileInputStream(f);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        String username = (String) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return username;
    }

    public void deleteCurrent() throws IOException {
        Files.delete(Path.of(fileCurrent));
    }

    public void writeLog(Login login) throws IOException {
        file=new File("Utente"+login.getUser()+".dat");
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(login);
        objectOut.close();
        fileOut.close();
        this.writeCurrent(login.getUser());
        logger.log(Level.INFO, scrittura);
    }

    public Login readLog() throws IOException, ClassNotFoundException {
        try {
            String user=this.readCurrent();
            file = new File("Utente"+user+".dat");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Login login = (Login) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            logger.log(Level.INFO, lettura);
            return login;
        }catch(FileNotFoundException e) {
            return null;
        }
    }

    public List<Recipe> readRecipe() throws IOException, ClassNotFoundException {
        try {
            file = new File(fileRecipe);
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Recipe> recipe = (ArrayList<Recipe>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            logger.log(Level.INFO, lettura);
            return recipe;
        }catch(FileNotFoundException e) {
            return null;
        }
    }

    public void writeRecipe(Recipe recipe) throws IOException, ClassNotFoundException {
        filee=new File(fileRecipe);
        List<Recipe> recipes= this.readRecipe();
        if(recipes==null){
            recipes=new ArrayList<>();
        }
        recipes.add(recipe);
        FileOutputStream fileOut = new FileOutputStream(filee);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(recipes);
        objectOut.close();
        fileOut.close();
        logger.log(Level.INFO, scrittura);
    }

    public void deleteRecipes() throws IOException {
        Files.delete(Path.of(fileRecipe));
        logger.log(Level.INFO, "Eliminazione completata!");
    }

    public List<Recipe> readSaved() throws IOException, ClassNotFoundException {
        try {
            file = new File(fileSaved);
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Recipe> recipe = (ArrayList<Recipe>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            logger.log(Level.INFO, lettura);
            return recipe;
        }catch(FileNotFoundException e) {
            return null;
        }
    }

    public void writeSaved(Recipe recipe) throws IOException, ClassNotFoundException {
        filee=new File(fileSaved);
        List<Recipe> recipes= this.readSaved();
        if(recipes==null){
            recipes=new ArrayList<>();
        }
        recipes.add(recipe);
        FileOutputStream fileOut = new FileOutputStream(filee);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(recipes);
        objectOut.close();
        fileOut.close();
        logger.log(Level.INFO, scrittura);
    }

    public void deleteSaved() throws IOException {
        Files.delete(Path.of(fileSaved));
        logger.log(Level.INFO, "Eliminazione completata!");
    }

    public void writePlan(int day, List<Meal> meals) throws IOException {
        plan.add(day, new File("Day"+day+".dat"));
        FileOutputStream fileOut = new FileOutputStream(plan.get(day));
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(meals);
        objectOut.close();
        fileOut.close();
        logger.log(Level.INFO, scrittura);
    }

    public List<Meal> readPlan(int day) throws IOException, ClassNotFoundException {
        try {
            File temp=new File("Day"+day+".dat");
            if(!temp.exists()){
                throw new MyException("File non ancora creato");
            }
            plan.set(day, temp);
            File read = plan.get(day);
            FileInputStream fileIn = new FileInputStream(read);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<Meal> meals = (List<Meal>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            logger.log(Level.INFO, lettura);
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
        List<Recipe> recipes=this.readChef();
        if(recipes==null){
            recipes=new ArrayList<>();
        }
        recipes.add(recipe);
        FileOutputStream fileOut = new FileOutputStream(foo);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(recipes);
        objectOut.close();
        fileOut.close();
        logger.log(Level.INFO, scrittura);
    }

    public List<Recipe> readChef() throws IOException, ClassNotFoundException {
        try {
            foo=new File("MyList.dat");
            FileInputStream fileIn = new FileInputStream(foo);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Recipe> recipes = (ArrayList<Recipe>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            logger.log(Level.INFO, lettura);
            return recipes;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
