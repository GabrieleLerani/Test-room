package com.example.progettoispw;

import java.io.IOException;

public class PaymentControllerA {
    private PaymentDAO paydao;
    private Login login;
    private FileInterDAO filedao;

    public PaymentControllerA() throws IOException, ClassNotFoundException {
        filedao=FileInterDAO.getInstance();
        login=filedao.ReadLog();
    }

    public void setPremiumUser() throws IOException {
        paydao = PaymentDAO.getInstance();
        paydao.insertPremiumUser(login.getUser());
        login.setPremium();
        filedao.WriteLog(login);
    }
}
