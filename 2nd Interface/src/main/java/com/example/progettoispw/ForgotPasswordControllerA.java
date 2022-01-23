package com.example.progettoispw;

import java.util.Random;

public class ForgotPasswordControllerA {
    private String random;
    private int num;
    private ForgotDAO dao;
    private String password="";
    private String email;

    public void sendMail(String email){
        this.email=email;
        Random rand = new Random();
        num = rand.nextInt(100, 1000);
        random = Integer.toString(num);
        SendMail.send(random, email);
    }

    public String checkOTP(String otp) throws ExceptionPass {
        if(otp.equals(random)) {
            dao = ForgotDAO.getInstance();
            password = dao.passRec(this.email);
        }
        return password;
    }

    public void checkMail(String mail) throws ExceptionPass {
        dao = ForgotDAO.getInstance();
        dao.passRec(mail);
    }
}
