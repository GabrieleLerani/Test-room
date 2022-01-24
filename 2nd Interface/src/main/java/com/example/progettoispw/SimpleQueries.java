package com.example.progettoispw;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleQueries {
    private static String veg="Vegetarian";
    private static String v="Vegan";

    private SimpleQueries(){}

    public static ResultSet selectUserFromName(String username, Connection conn) throws SQLException {
        String sql = "SELECT * FROM Utenti where Username = ?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        return prep.executeQuery();
    }

    public static int insertUser(String username, String password, int cl, String email, String spec, Connection conn) throws SQLException {
        String sql = "INSERT INTO Utenti(Username, Password, Specializzazione, CookingLevel, Email) values (?,?,?,?,?)";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        prep.setString(2, password);
        prep.setString(3, spec);
        prep.setInt(4, cl);
        prep.setString(5, email);
        return prep.executeUpdate();
    }

    public static ResultSet getRecipeFromNameCLAPAll(String name, int cl, String ap, Connection conn) throws SQLException{
        String sql;
        PreparedStatement prep;
        if(ap.equalsIgnoreCase(v)) {
            sql = "SELECT * FROM ricetteinserite where Ricetta = ? AND Livello <= ? AND Intolleranza = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, name);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            return prep.executeQuery();
        }else if(ap.equalsIgnoreCase(veg)){
            sql = "SELECT * FROM ricetteinserite where Ricetta = ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, name);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, v);
        }else{
            sql = "SELECT * FROM ricetteinserite where Ricetta = ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, name);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, veg);
            prep.setString(5, v);
        }
        return prep.executeQuery();
    }

    public static int insertCookingLevel(int cook, String username, Connection conn) throws SQLException {
        String sql= "UPDATE utenti SET CookingLevel=? WHERE Username=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setInt(1, cook);
        prep.setString(2, username);
        return prep.executeUpdate();
    }

    public static ResultSet getPassFromEmail(String email, Connection conn) throws SQLException {
        String sql= "SELECT Password FROM utenti where Email = ?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, email);
        return prep.executeQuery();
    }

    public static int setUserFromName(String nameSet, String oldName, Connection conn) throws SQLException {
        String sql= "UPDATE utenti SET Username=? WHERE Username=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, nameSet);
        prep.setString(2, oldName);
        return prep.executeUpdate();
    }

    public static int setPassFromName(String password, String username, Connection conn) throws SQLException {
        String sql= "UPDATE utenti SET Password=? WHERE Username=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, password);
        prep.setString(2, username);
        return prep.executeUpdate();
    }

    public static int insertRecipeFromIngredient(String username, String title, String ingredient, int level, int time, String ap, String type, String description, String amount, String all, Connection conn) throws SQLException {
        String sql= "INSERT INTO ricetteinserite(Nome, Ricetta, Ingrediente, Livello, Tempo, Intolleranza, Type, Description, Ammontare, Allergies) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        prep.setString(2, title);
        prep.setString(3, ingredient);
        prep.setInt(4, level);
        prep.setInt(5, time);
        prep.setString(6, ap);
        prep.setString(7, type);
        prep.setString(8, description);
        prep.setString(9, amount);
        prep.setString(10, all);
        return prep.executeUpdate();
    }

    public static int insertImages(String name, String chef, File file, InputStream fin, Connection conn) throws SQLException {
        String sql= "INSERT INTO images(Nome, Chef, IMG) values (?, ?, ?)";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, name);
        prep.setString(2, chef);
        prep.setBinaryStream(3, fin, file.length());
        return prep.executeUpdate();
    }

    public static int insertAlimentarPreferences(String username, String pref, Connection conn) throws SQLException {
        String sql= "UPDATE utenti SET AlimentarPreferences=? WHERE Username=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, pref);
        prep.setString(2, username);
        return prep.executeUpdate();
    }

    public static int insertAllergies(String username, String all, Connection conn) throws SQLException {
        String sql= "INSERT INTO allergies(Name, Allergy) values (?, ?)";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        prep.setString(2, all);
        return prep.executeUpdate();
    }

    public static ResultSet getAllergies(String username, Connection conn) throws SQLException {
        String sql= "SELECT * FROM allergies where Name = ?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        return prep.executeQuery();
    }

    public static int deleteAllergies(String username, Connection conn) throws SQLException {
        String sql= "DELETE FROM allergies WHERE Name = ?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        return prep.executeUpdate();
    }

    public static ResultSet getImage(String name, Connection conn) throws SQLException {
        String sql= "SELECT * FROM images where Nome = ?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, name);
        return prep.executeQuery();
    }

    public static ResultSet getRecipeFromTimeCLAPAll(int time, int cl, String ap, Connection conn) throws SQLException{
        String sql;
        PreparedStatement prep;
        if(ap.equalsIgnoreCase(v)) {
            sql = "SELECT * FROM ricetteinserite where Tempo <= ? AND Livello <= ? AND Intolleranza = ?";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, time);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            return prep.executeQuery();
        }else if(ap.equalsIgnoreCase(veg)){
            sql = "SELECT * FROM ricetteinserite where Tempo <= ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, time);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, v);
        }else{
            sql = "SELECT * FROM ricetteinserite where Tempo <= ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setInt(1, time);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, veg);
            prep.setString(5, v);
        }
        return prep.executeQuery();
    }

    public static ResultSet getImageFromChef(String name, String chef, Connection conn) throws SQLException {
        String sql= "SELECT * FROM images where Nome = ? AND Chef=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, name);
        prep.setString(2, chef);
        return prep.executeQuery();
    }

    public static ResultSet getRecipeFromIngrCLAPAll(String ingr, int cl, String ap, Connection conn) throws SQLException{
        String sql;
        PreparedStatement prep;
        if(ap.equalsIgnoreCase(v)) {
            sql = "SELECT * FROM ricetteinserite where Ingrediente = ? AND Livello <= ? AND Intolleranza = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, ingr);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            return prep.executeQuery();
        }else if(ap.equalsIgnoreCase(veg)){
            sql = "SELECT * FROM ricetteinserite where Ingrediente = ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, ingr);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, v);
        }else{
            sql = "SELECT * FROM ricetteinserite where Ingrediente = ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, ingr);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, veg);
            prep.setString(5, v);
        }
        return prep.executeQuery();
    }

    public static ResultSet getRecipeFromTypeCLAPAll(String type, int cl, String ap, Connection conn) throws SQLException{
        String sql;
        PreparedStatement prep;
        if(ap.equalsIgnoreCase(v)) {
            sql = "SELECT * FROM ricetteinserite where Type = ? AND Livello <= ? AND Intolleranza = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1, type);
            prep.setInt(2, cl);
            prep.setString(3, ap);
        }else if(ap.equalsIgnoreCase(veg)){
            sql = "SELECT * FROM ricetteinserite where Type = ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, type);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, v);
        }else{
            sql = "SELECT * FROM ricetteinserite where Type = ? AND Livello <= ? AND (Intolleranza = ? OR Intolleranza = ? OR Intolleranza = ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, type);
            prep.setInt(2, cl);
            prep.setString(3, ap);
            prep.setString(4, veg);
            prep.setString(5, v);
        }
        return prep.executeQuery();
    }

    public static int upgradePremium(String username, Connection conn) throws SQLException {
        String sql= "UPDATE utenti SET Specializzazione=? WHERE Username=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, "Premium");
        prep.setString(2, username);
        return prep.executeUpdate();
    }

    public static ResultSet getSpecFromName(String username, Connection conn) throws SQLException {
        String sql= "SELECT * FROM utenti where Username=?";
        PreparedStatement prep=conn.prepareStatement(sql);
        prep.setString(1, username);
        return prep.executeQuery();
    }
}
