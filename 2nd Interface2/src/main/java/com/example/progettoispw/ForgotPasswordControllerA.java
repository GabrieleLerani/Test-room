package com.example.progettoispw;

import java.util.Random;

import static com.example.progettoispw.SendMail.send;

public class ForgotPasswordControllerA {
    private String random;
    private ForgotDAO dao;
    private String password="";
    private String email;

    public void sendMail(String email){
        this.email=email;
        Random rand = new Random();
        int num = rand.nextInt(100, 1000);
        random = Integer.toString(num);
        send(random, email);
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
