package com.example.progettoispw;

import java.io.IOException;

public class PaymentControllerA {
    private Login login;
    private FileInterDAO filedao;

    public PaymentControllerA() throws IOException, ClassNotFoundException {
        filedao=FileInterDAO.getInstance();
        login=filedao.readLog();
    }

    public void setPremiumUser() throws IOException {
        PaymentDAO paydao = PaymentDAO.getInstance();
        paydao.insertPremiumUser(login.getUser());
        login.setPremium();
        filedao.writeLog(login);
    }
}
