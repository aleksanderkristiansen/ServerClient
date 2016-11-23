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
        System.out.println("Hej " + currentUser.getFirstName() +
                "\nHvad vil du?" +
                "\n1: Se din pensumliste" +
                "\n2: Ændre profiloplysninger" +
                "\n3: Slet profil" +
                "\n4: Log ud");

        int i = sc.nextInt();

        switch (i) {

            case 1: //mainController.curriculumOfUser();
                userView(currentUser);
                break;

            case 2: mainController.changeUserProfile(currentUser);
                userView(currentUser);
                break;

            case 3: mainController.deleteUser(currentUser.getToken(), currentUser.getUserID());
                userView(currentUser);
                break;

            case 4: mainController.logout(currentUser.getToken());
                break;

            default: mainController.logout(currentUser.getToken());
                break;
        }
    }
}
