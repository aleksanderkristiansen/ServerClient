package company;

import company.controller.MainController;

import java.io.IOException;


/**
 * @author Crunchify
 *
 */

public class Main {

    public static void main(String[] args) throws IOException {

        MainController mc = new MainController(){};

        mc.run();
        }

}
