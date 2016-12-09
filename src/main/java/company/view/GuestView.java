package company.view;

import company.controller.MainController;

import java.util.Scanner;

/**
 * Created by aleksanderkristiansen on 23/11/2016.
 */
public class GuestView {
    Scanner sc = new Scanner(System.in);
    MainController mainController;

    public GuestView(MainController mainController){
        this.mainController = mainController;
    }

    public void guestView(){

        System.out.println("Velkommen til Bookit");

        boolean stop = false;

        while (!stop){
            System.out.println("\nStartmenuen - hvad vil du?" +
                    "\n1: Find pensumliste" +
                    "\n2: Log ind" +
                    "\n3: Opret bruger");
                try {

                    sc = new Scanner(System.in);

                    int i = sc.nextInt();

                    switch (i){

                        case 1: mainController.findCurriculum();
                            break;

                        case 2: mainController.login();
                            break;

                        case 3: mainController.createUser();
                            break;

                        default: System.out.println(i + " er ikke en mulighed, prøv igen");
                            break;
                    }
                } catch (Exception exception){
                    System.out.println("Ukendt værdi indtastet, prøv igen");
                }
        }
    }
}
