package company.controller;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import company.sdk.connection.ResponseCallback;
import company.sdk.model.*;
import company.sdk.services.BookService;
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

                case 1: //findCurriculum();
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

                            case 5: //changeUserProfile();
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

/*    private void changeUserProfile() {
    }

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

        Book book = new Book(title, version, isbn, publisherId);

        bookService.createBook(listOfSelectedAuthors, listOfSelectedBookstores, book, new ResponseCallback<String>() {
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

    public void findCurriculum() throws IOException {

        ArrayList<String> schools = new ArrayList();

        for (int i = 0; i < cc.getAllCurriculums().size(); i++){
            boolean foundSchool = schools.contains(cc.getAllCurriculums().get(i).getSchool());

            if (!foundSchool){
                schools.add(cc.getAllCurriculums().get(i).getSchool());
            }
        }

        for (int i = 0; i < schools.size(); i++){
            System.out.println(i + ": " + schools.get(i));
        }

        int selectedSchool = sc.nextInt();

        ArrayList<String> educations = new ArrayList();

        for (int i = 0; i < cc.getAllCurriculums().size(); i++){

            boolean foundEducation = educations.contains(cc.getAllCurriculums().get(i).getEducation());

            if (!foundEducation){

                if (cc.getAllCurriculums().get(i).getSchool().equals(schools.get(selectedSchool))){

                    educations.add(cc.getAllCurriculums().get(i).getEducation());
                }

            }


        }

        for (int i = 0; i < educations.size(); i++){
            System.out.println(i + ": " + educations.get(i));
        }

        int selectedEducation = sc.nextInt();

        ArrayList<Integer> semester = new ArrayList();

        for (int i = 0; i < cc.getAllCurriculums().size(); i++){
            if (cc.getAllCurriculums().get(i).getEducation().equals(educations.get(selectedEducation))){
                semester.add(cc.getAllCurriculums().get(i).getSemester());
            }
        }

        for (int i = 0; i < semester.size(); i++){
            System.out.println(i + ": " + semester.get(i));
        }

        int selectedSemester = sc.nextInt();

        System.out.printf("%-7s %-55s %-70s %-20s %-55s %-20s\n", "Nr.",  "Book title:", "Book Author", "Book ISBN", "Book Publisher", "Cheapest price ");

        ArrayList<Book> bookOfCurriculum = new ArrayList<Book>();

        for (int i = 0; i < cc.getAllCurriculums().size(); i++){

            if (cc.getAllCurriculums().get(i).getSchool().equals(schools.get(selectedSchool)) && cc.getAllCurriculums().get(i).getEducation().equals(educations.get(selectedEducation)) && cc.getAllCurriculums().get(i).getSemester() == semester.get(selectedSemester)){

                for (int j = 0; j < cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).size(); j++){



                    String bookTitle = cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getTitle();

                    String bookAuthor = cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getAuthor();

                    double isbn = cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getISBN();

                    double price = cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getPrice();

                    String bookPublisher = cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getPublisher();

                    Book book = new Book(bookTitle, bookAuthor, isbn, price, bookPublisher);

                    if (bookOfCurriculum.isEmpty()){
                        bookOfCurriculum.add(book);
                    }else{
                        for (Book bookSearch: bookOfCurriculum ){

                            if (bookSearch.getPrice() > price){
                                bookSearch.setPrice(price);
                            }

                            if (bookSearch.getTitle().equals(bookTitle)){

                                if (!bookSearch.getAuthor().contains(bookAuthor)){
                                    bookAuthor = bookSearch.getAuthor() + " & " + bookAuthor;
                                    bookSearch.setAuthor(bookAuthor);
                                    break;
                                }
                                break;
                            }
                        }
                    }



                        //System.out.printf("%-7d %-55s %-70s %-20.0f\n", j,  cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getTitle(), cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getAuthor(), cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getISBN(), cc.getCurriculumsBooks(cc.getAllCurriculums().get(i).getCurriculumID()).get(j).getPublisher());


                }
            }
        }

        int k =1;

        for (Book bookPrint: bookOfCurriculum){
            System.out.printf("%-7d %-55s %-70s %-20.0f %-55s %-20.0f\n", k,  bookPrint.getTitle(), bookPrint.getAuthor(), bookPrint.getISBN(), bookPrint.getPublisher(), bookPrint.getPrice());
            k++;
        }








    }*/












}
