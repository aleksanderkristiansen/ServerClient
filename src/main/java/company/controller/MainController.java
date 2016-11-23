package company.controller;


import java.util.ArrayList;
import java.util.Scanner;

import company.sdk.connection.ResponseCallback;
import company.sdk.model.*;
import company.sdk.services.BookService;
import company.sdk.services.CurriculumService;
import company.sdk.services.UserService;
import company.view.AdminView;
import company.view.GuestView;
import company.view.UserView;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class MainController {
    Scanner sc = new Scanner(System.in);
    private UserService userService = new UserService();
    private BookService bookService = new BookService();
    private CurriculumService curriculumService = new CurriculumService();
    private GuestView guestView;
    private AdminView adminView;
    private UserView userView;

    public MainController(){
        this.guestView = new GuestView(this);
        this.adminView = new AdminView(this);
        this.userView = new UserView(this);
    }


    public void run(){

        guestView.guestView();

    }

    public void login() {
        System.out.println("Brugernavn");

        String email = sc.next();

        System.out.println("Adgangskode");

        String password = sc.next();

        this.userService.login(email, password, new ResponseCallback<User>() {
            public void success(User currentUser) {


                    if (currentUser.getUserType()){

                        adminView.adminView(currentUser);


                    }else{
                        userView.userView(currentUser);
                    }

            }

            public void error(int status) {
                System.out.println("Darn it, something went wrong, error: " + status);
            }
        });






    }

   public void changeUserProfile(User currentUser) {

       boolean endEditUser = false;
       String firstName = null;
       String lastName = null;
       String email = null;
       String password = null;
       boolean userType = false;
       int userID;

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
               case 1: firstName = sc.next();
                   break;
               case 2: lastName = sc.next();
                   break;
               case 3: email = sc.next();
                   break;
               case 4: password = sc.next();
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

    public void createBook() {

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



    public void createUser() {

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



    public void deleteUser(String token, int userID) {
        this.userService.deleteUser(userID, token, new ResponseCallback<String>() {
            public void success(String message) {
                System.out.println(message);
            }

            public void error(int status) {

            }
        });
    }

    public void logout(String token){
        userService.logOut(token, new ResponseCallback<String>() {
            public void success(String data) {
                guestView.guestView();
            }

            public void error(int status) {

            }
        });




    }

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
    public void findCurriculum(){

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
                                String bookAuthors;

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
                                System.out.printf("%-7s %-55s %-20s\n", "Nr.", "Title", "Billigste pris");
                                for (Book bookPrint: curriculumBooks){
                                    System.out.printf("%-7d %-55s %-20.0f\n", bookPrint.getBookID() ,  bookPrint.getTitle(), bookPrint.getPrice());
                                }

                                int selectedBook = 1;

                                while (selectedBook != 0){
                                    System.out.println("Se mere om bog (indtast nr. på bog eller 0 for at afslutte");
                                    selectedBook = sc.nextInt();
                                    if (selectedBook !=0){
                                        bookService.getBook(selectedBook, new ResponseCallback<Book>() {
                                            public void success(Book data) {
                                                System.out.println("Title: " + data.getTitle());
                                                System.out.printf("ISBN: %-20.0f\n", data.getISBN());
                                                System.out.println("Version: " + data.getVersion());
                                                System.out.println("Forlag: " + data.getPublisher());
                                                System.out.println("Forfattere");
                                                for (Author author : data.getLstAuthors()){
                                                    System.out.println(author.getName());
                                                }
                                                System.out.println("Forhandler og pris");
                                                for (BookStore bookstore : data.getLstBookStores()){
                                                    System.out.println(bookstore.getName() + " " + bookstore.getPriceOfBook());
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
            }

            public void error(int status) {

            }
        });
    }
}
