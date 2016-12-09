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

    public AdminView(MainController mainController){
        this.mainController = mainController;
    }

    public void adminView(User currentUser){
        boolean stop = false;
        while (!stop){
            System.out.println("\nAdministratormenu - hvad vil du?" +
                    "\n1: Se alle brugere" +
                    "\n2: Slet en bruger" +
                    "\n3: Opret en bruger" +
                    "\n4: Opret en bog" +
                    "\n5: Ændre profiloplysninger" +
                    "\n6: Log ud");

            try{
                sc = new Scanner(System.in);
                int i = sc.nextInt();

                switch (i){

                    case 1: mainController.getAllUsers(currentUser.getToken());
                        break;

                    case 2: System.out.println("BrugerID");
                        int userID = sc.nextInt();
                        mainController.deleteUser(currentUser.getToken(), userID);
                        break;

                    case 3: mainController.createUser();
                        break;

                    case 4: mainController.createBook();
                        break;

                    case 5: mainController.changeUserProfile(currentUser);
                        break;

                    case 6: mainController.logout(currentUser.getToken());
                        stop = true;
                        break;

                    default: System.out.println(i + " er ikke en mulighed, prøv igen");
                        break;
                }
            }catch (Exception exception){
                System.out.println("Ukendt værdi indtastet, prøv igen");
            }
        }
    }
}
