module com.example.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;


    opens com.example.progettoispw to javafx.fxml;
    opens com.example.progettoispw.RecipeModel to javafx.fxml;

    exports com.example.progettoispw;
    exports com.example.progettoispw.RecipeModel;
}