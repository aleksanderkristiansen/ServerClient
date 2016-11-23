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
        System.out.println("Velkommen til Bookit" +
                "\nHvad vil du?" +
                "\n1: Find pensumliste" +
                "\n2: Log ind" +
                "\n3: Opret bruger");

        int i = sc.nextInt();

        switch (i){

            case 1: mainController.findCurriculum();
                guestView();
                break;

            case 2: mainController.login();
                break;

            case 3: mainController.createUser();
                guestView();
                break;

            default: mainController.findCurriculum();
                guestView();
                break;
        }
    }



}
