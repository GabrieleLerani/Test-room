package com.example.progettoispw;

public class ExceptionAdd extends Exception {
    public ExceptionAdd(String level, Throwable cause){
        super("This is the exception "+level+"\n"+"The reason is that "+cause+"\n"+"F" + "One of the fields is empty");
    }
}
