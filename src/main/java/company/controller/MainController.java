package company.controller;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import company.sdk.connection.ResponseCallback;
import company.sdk.model.*;
import company.sdk.services.BookService;
import company.sdk.services.CurriculumService;
import company.sdk.services.UserService;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class MainController {

    /*BookController bc = new BookController();
    UserController uc = new UserController();
    CurriculumController cc = new CurriculumController();*/
    Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();
    private BookService bookService = new BookService();
    private CurriculumService curriculumService = new CurriculumService();

    public MainController(){
        this.userService = new UserService();
    }


    public void run() throws IOException {

            System.out.println("Velkommen til Bookit" +
                    "\nHvad vil du?" +
                    "\n1: Find pensumliste" +
                    "\n2: Log ind" +
                    "\n3: Opret bruger");

            int i = sc.nextInt();

            switch (i){

                case 1: findCurriculum();
                    break;

                case 2: login();
                    break;

                case 3: createUser();
                    break;

                default: //findCurriculum();
                    break;
            }

    }

    public void login() {
        System.out.println("Brugernavn");

        String email = sc.next();

        System.out.println("Adgangskode");

        String password = sc.next();

        this.userService.login(email, password, new ResponseCallback<User>() {
            public void success(User currentUser) {


                    if (currentUser.getUserType()){
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

                            case 1: getAllUsers(currentUser.getToken());
                                break;

                            case 2: System.out.println("BrugerID");

                                int userID = sc.nextInt();
                                deleteUser(currentUser.getToken(), userID);
                                break;

                            case 3: createUser();
                                break;

                            case 4: createBook();
                                break;

                            case 5: changeUserProfile(currentUser);
                                break;

                            case 6: //logout(currentUser.getToken());
                                break;

                            default:
                                break;
                        }

                    }else{
                        System.out.println("Hej " + currentUser.getFirstName() +
                                "\nHvad vil du?" +
                                "\n1: Se din pensumliste" +
                                "\n2: Ændre profiloplysninger" +
                                "\n3: Slet profil" +
                                "\n4: Log ud");

                        int i = sc.nextInt();

                        switch (i){

                            case 1: //curriculumOfUser();
                                break;

                            case 2: //changeUserProfile();
                                break;

                            case 3: deleteUser(currentUser.getToken(), currentUser.getUserID());
                                break;

                            case 4: //logout(currentUser.getToken());

                            default:
                                break;
                        }
                    }

            }

            public void error(int status) {
                System.out.println("Darn it, something went wrong, error: " + status);
            }
        });






    }

   private void changeUserProfile(User currentUser) {

       boolean endEditUser = false;
       String firstName = null;
       String lastName = null;
       String email = null;
       String password = null;
       boolean userType = false;
       int userID = 0;

       User user;

       if (currentUser.getUserType()){
           System.out.println("Hvilken bruger ønsker du at ændre?");
           userService.getAll(currentUser.getToken(), new ResponseCallback<ArrayList<User>>() {
               public void success(ArrayList<User> data) {
                   for (User user : data){
                       System.out.println(user.getUserID() + ": " + user.getEmail());
                   }
               }

               public void error(int status) {

               }
           });

           userID = sc.nextInt();

           while (!endEditUser) {

               System.out.println("Hvad vil du gerne ændre?");
               System.out.println("1: Fornavn");
               System.out.println("2: Efternavn");
               System.out.println("3: E-mail");
               System.out.println("4: Adgangskode");
               System.out.println("5: Brugertype");
               System.out.println("6: Afslut");

               int choice = sc.nextInt();

               switch (choice) {
                   case 1: firstName = sc.next();
                       break;
                   case 2: lastName = sc.next();
                       break;
                   case 3: email = sc.next();
                       break;
                   case 4: password = sc.next();
                       break;
                   case 5: int j = sc.nextInt();
                       if (j == 1){
                           userType = true;
                       }
                       break;
                   case 6: endEditUser = true;
                       break;
                   default: endEditUser = true;
                       break;
               }

           }

       }else{

           userID = currentUser.getUserID();


           System.out.println("Hvad vil du gerne ændre?");
           System.out.println("1: Fornavn");
           System.out.println("2: Efternavn");
           System.out.println("3: E-mail");
           System.out.println("4: Adgangskode");

           int i = sc.nextInt();

           switch (i) {
               case 1: firstName = sc.nextLine();
                   break;
               case 2: lastName = sc.nextLine();
                   break;
               case 3: email = sc.nextLine();
                   break;
               case 4: password = sc.nextLine();
                   break;
           }
       }



       user = new User(firstName, lastName, email,password,userType);

       userService.editUser(currentUser.getToken(), user, userID, new ResponseCallback<String>() {
           public void success(String data) {

           }

           public void error(int status) {

           }
       });








    }

    /*
    private void curriculumOfUser(){

    }

    */

    private void createBook() {

        System.out.println("Title på bog");

        String title = sc.next();

        System.out.println("Version");

        int version = sc.nextInt();

        System.out.println("ISBN");

        double isbn = sc.nextDouble();

        System.out.println("\nForfattere du kan vælge i mellem");

        this.bookService.getAuthors(new ResponseCallback<ArrayList<Author>>() {
            public void success(ArrayList<Author> data) {
                for(Author author : data){
                    System.out.println(author.getId() + ": " + author.getName());
                }
            }
            public void error(int status) {
            }
        });

        System.out.println("\nHvor mange forfattere har bogen?");

        int numberOfAuthors = sc.nextInt();

        ArrayList<Author> listOfSelectedAuthors = new ArrayList<Author>();

        for (int i = 0; i < numberOfAuthors; i++){

            System.out.println("\nVælg ID på forfatter");

            int idOfSelectedAuthor = sc.nextInt();

            Author selectedAuthor = new Author(idOfSelectedAuthor, null);

            listOfSelectedAuthors.add(selectedAuthor);
        }

        System.out.println("\nForlag du kan vælge i mellem");

        this.bookService.getPublishers(new ResponseCallback<ArrayList<Publisher>>() {
            public void success(ArrayList<Publisher> data) {
                for(Publisher publisher : data){
                    System.out.println(publisher.getId() + " : " + publisher.getName());
                }
            }

            public void error(int status) {

            }
        });

        System.out.println("\nVælg ID på forlag");

        int publisherId = sc.nextInt();

        this.bookService.getBookstores(new ResponseCallback<ArrayList<BookStore>>() {
            public void success(ArrayList<BookStore> data) {
                for(BookStore bookStore : data){
                    System.out.println(bookStore.getId() + ": " + bookStore.getName());
                }
            }
            public void error(int status) {
            }
        });

        System.out.println("\nHvor mange forhandlere har bogen?");

        int numberOfBookstores = sc.nextInt();

        ArrayList<BookStore> listOfSelectedBookstores = new ArrayList<BookStore>();

        for (int i = 0; i < numberOfBookstores; i++){

            System.out.println("\nVælg ID på forhandler");

            int idOfSelectedBookstore = sc.nextInt();

            System.out.println("\nVælg prisen som forhandleren tager for bogen");

            double priceOfBook = sc.nextDouble();


            BookStore selectedBookstore = new BookStore(idOfSelectedBookstore, null, priceOfBook);

            listOfSelectedBookstores.add(selectedBookstore);


        }

        Book book = new Book(title, version, isbn, publisherId, listOfSelectedAuthors, listOfSelectedBookstores);

        bookService.createBook(book, new ResponseCallback<String>() {
            public void success(String data) {
                System.out.print("Bogen er oprettet");
            }

            public void error(int status) {

            }
        });

    }



    private void createUser() {

        System.out.println("Navn");

        String firstName = sc.next();

        System.out.println("Efternavn");

        String lastName = sc.next();

        System.out.println("E-mail");

        String email = sc.next();

        System.out.println("Adgangskode");

        String password = sc.next();

        System.out.println("Administrator? \n 1: Ja \n 2: Nej");

        int i = sc.nextInt();

        boolean admin = false;

        switch (i){
            case 1: admin = true;
                break;
            case 2:
                break;
            default:
                break;
        }

        User user = new User(firstName, lastName, email, password, admin);

        userService.createUser(user, new ResponseCallback<String>() {
            public void success(String data) {
                System.out.print("Brugeren er oprettet");
            }

            public void error(int status) {

            }
        });



    }



    private void deleteUser(String token, int userID) {
        this.userService.deleteUser(userID, token, new ResponseCallback<String>() {
            public void success(String message) {
                System.out.println(message);
            }

            public void error(int status) {

            }
        });
    }
/*
    public void logout(String token) throws IOException{

        System.out.print(token);

        uc.logout(token);




    } */

    public void getAllUsers(String token){

    this.userService.getAll(token,new ResponseCallback<ArrayList<User>>() {
      public void success(ArrayList<User> users) {

        for(User user: users){
          System.out.println(user.getFirstName() + " " + user.getLastName());
        }

      }

      public void error(int status) {

      }
    });

    }

    /*

    public void getUser(String token, int id) throws IOException {

        System.out.println("Fornavn: " + uc.getUser(token, id).getFirstName()
                + "\n" + "Efternavn:" + uc.getUser(token, id).getLastName()
                + "\n" + "Email : " + uc.getUser(token, id).getEmail());
    }
*/
    public void findCurriculum() throws IOException {

        this.curriculumService.getCurriculums(new ResponseCallback<ArrayList<Curriculum>>() {
            public void success(ArrayList<Curriculum> data) {
                ArrayList<String> schools = new ArrayList();
                ArrayList<String> educations = new ArrayList();
                ArrayList<Integer> semesters = new ArrayList();


                for (Curriculum school : data){
                    if (!schools.contains(school)){
                        schools.add(school.getSchool());
                    }
                }

                for (String school : schools){
                    System.out.println(schools.indexOf(school) + ": " + school);
                }

                System.out.println("Vælg skole");
                int selectedSchool = sc.nextInt();

                for (Curriculum education : data){
                    if (education.getSchool().equals(schools.get(selectedSchool)) && !educations.contains(education.getEducation())){
                        educations.add(education.getEducation());
                    }
                }

                for (String education : educations){
                    System.out.println(educations.indexOf(education) + ": " + education);
                }

                System.out.println("Vælg uddannelse");
                int selectedEducation = sc.nextInt();

                for (Curriculum semester : data){
                    if (semester.getSchool().equals(schools.get(selectedSchool)) && semester.getEducation().equals(educations.get(selectedEducation))){
                        semesters.add(semester.getSemester());
                    }
                }

                for (int semester : semesters){
                    System.out.println(semesters.indexOf(semester) + ": " + semester);
                }

                System.out.println("Vælg semester");
                int selectedSemester = sc.nextInt();

                for (Curriculum books : data){
                    if (books.getSchool().equals(schools.get(selectedSchool)) && books.getEducation().equals(educations.get(selectedEducation)) && books.getSemester() == semesters.get(selectedSemester)){
                        curriculumService.getCurriculumBooks(books.getCurriculumID(), new ResponseCallback<ArrayList<Book>>() {
                            public void success(ArrayList<Book> data) {
                                ArrayList<Book> curriculumBooks = new ArrayList<Book>();
                                String bookAuthors = null;

                                for (Book book : data){
                                    if (curriculumBooks.isEmpty()){
                                        curriculumBooks.add(book);
                                    }else{
                                        for (Book bookSearch: curriculumBooks ){

                                            if (bookSearch.getPrice() > book.getPrice()){
                                                bookSearch.setPrice(book.getPrice());
                                            }

                                            if (bookSearch.getTitle().equals(book.getTitle())){

                                                if (!bookSearch.getAuthor().contains(book.getAuthor())){
                                                    bookAuthors = bookSearch.getAuthor() + " & " + book.getAuthor();
                                                    bookSearch.setAuthor(bookAuthors);
                                                    break;
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                                int k = 0;
                                for (Book bookPrint: curriculumBooks){
                                    System.out.printf("%-7d %-55s %-70s %-20.0f %-55s %-20.0f\n", k,  bookPrint.getTitle(), bookPrint.getAuthor(), bookPrint.getISBN(), bookPrint.getPublisher(), bookPrint.getPrice());
                                    k++;
                                }


                            }

                            public void error(int status) {

                            }
                        });

                    }
                }
            }

            public void error(int status) {

            }
        });
    }
}
