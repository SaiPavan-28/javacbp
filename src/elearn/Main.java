package elearn;

import elearn.ui.LoginUI;

public class Main {
    public static void main(String[] args) {

        try {
        	Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception e) {
            System.out.println("MySQL Driver not found!");
        }

        new LoginUI();
    }
}
