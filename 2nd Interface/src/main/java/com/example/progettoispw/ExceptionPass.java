package com.example.progettoispw;

public class ExceptionPass extends Exception {
    public ExceptionPass(String pass, String message){
        super(pass+" non valida"+'\n'+"Questo è l'errore: "+message);
    }
}
