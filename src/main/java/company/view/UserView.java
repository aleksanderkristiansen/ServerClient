package company.view;

import company.controller.MainController;
import company.sdk.model.User;

import java.util.Scanner;

/**
 * Created by aleksanderkristiansen on 23/11/2016.
 */
public class UserView {
    Scanner sc = new Scanner(System.in);
    MainController mainController;

    public UserView(MainController mainController){
        this.mainController = mainController;
    }



    public void userView(User currentUser){
        boolean stop = false;
        while (!stop){
            System.out.println("\n Hej " + currentUser.getFirstName() + " - hvad vil du?" +
                    "\n1: Se din pensumliste" +
                    "\n2: Ændre profiloplysninger" +
                    "\n3: Slet profil" +
                    "\n4: Log ud");
            try{
                sc = new Scanner(System.in);
                int i = sc.nextInt();

                switch (i) {

                    case 1: //mainController.curriculumOfUser();
                        break;

                    case 2: mainController.changeUserProfile(currentUser);
                        break;

                    case 3: mainController.deleteUser(currentUser.getToken(), currentUser.getUserID());
                        break;

                    case 4: mainController.logout(currentUser.getToken());
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
