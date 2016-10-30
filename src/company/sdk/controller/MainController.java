package company.sdk.controller;

import java.util.Scanner;
import java.io.IOException;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class MainController {


    public void run() throws IOException {
        BookController bc = new BookController();
        UserController uc = new UserController();

        Scanner sc = new Scanner(System.in);

        System.out.println("Brugernavn");

        String username = sc.next();

        System.out.println("Adgangskode");

        String password = sc.next();

        //String token = uc.getAuth(username, password);

        //if (!token.isEmpty()){

            System.out.println("Velkommen til Bookit" +
                    "\nHvad vil du?" +
                    "\n1: Find pensumliste" +
                    "\n2: Søg efter bog" +
                    "\n3: Log ud");

            int i = sc.nextInt();

            switch (i){

                case 1: System.out.print(bc.getAllBooks().get(1).getTitle());

                    break;

                case 2: System.out.println(bc.getBook(1).getTitle());
                    break;

                default: System.out.println("Adgangskode");;
                    break;
            }

        //}


    }












}
