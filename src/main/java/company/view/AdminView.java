package company.view;

import company.controller.MainController;
import company.sdk.model.User;

import java.util.Scanner;

/**
 * Created by aleksanderkristiansen on 23/11/2016.
 */
public class AdminView {
    Scanner sc = new Scanner(System.in);
    MainController mainController;
    GuestView guestView;

    public AdminView(MainController mainController){
        this.mainController = mainController;
        this.guestView = guestView;

    }

    public void adminView(User currentUser){
        System.out.println("Du er logget ind som administrator" +
                "\nHvad vil du?" +
                "\n1: Se alle brugere" +
                "\n2: Slet en bruger" +
                "\n3: Opret en bruger" +
                "\n4: Opret en bog" +
                "\n5: Ændre profiloplysninger" +
                "\n6: Log ud");

        int i = sc.nextInt();

        switch (i){

            case 1: mainController.getAllUsers(currentUser.getToken());
                adminView(currentUser);
                break;

            case 2: System.out.println("BrugerID");
                int userID = sc.nextInt();
                mainController.deleteUser(currentUser.getToken(), userID);
                adminView(currentUser);
                break;

            case 3: mainController.createUser();
                adminView(currentUser);
                break;

            case 4: mainController.createBook();
                adminView(currentUser);
                break;

            case 5: mainController.changeUserProfile(currentUser);
                adminView(currentUser);
                break;

            case 6: mainController.logout(currentUser.getToken());
                break;

            default: mainController.logout(currentUser.getToken());
                break;
        }
    }
}
