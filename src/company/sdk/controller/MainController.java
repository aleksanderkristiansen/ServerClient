package company.sdk.controller;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import company.sdk.model.Book;
import company.sdk.model.User;

/**
 * Created by aleksanderkristiansen on 24/10/2016.
 */
public class MainController {

    BookController bc = new BookController();
    UserController uc = new UserController();
    CurriculumController cc = new CurriculumController();
    Scanner sc = new Scanner(System.in);


    public void run() throws IOException {

            System.out.println("Velkommen til Bookit" +
                    "\nHvad vil du?" +
                    "\n1: Find pensumliste" +
                    "\n2: Log ind" +
                    "\n3: Log ud");

            int i = sc.nextInt();

            switch (i){

                case 1: findCurriculum();

                    break;

                case 2: login();
                    break;

                default: findCurriculum();
                    break;
            }

    }

    public void login() throws IOException{
        System.out.println("Brugernavn");

        String email = sc.next();

        System.out.println("Adgangskode");

        String password = sc.next();

        User currentUser = uc.getAuth(email, password);

        if (currentUser != null){

            if (currentUser.getUserType()){
                System.out.println("Velkommen til Bookit" +
                        "\nDu er logget ind som administrator" +
                        "\nHvad vil du?" +
                        "\n1: Se din pensumliste" +
                        "\n2: Ændre profiloplysninger" +
                        "\n3: Log ud");

                int i = sc.nextInt();

                switch (i){

                    case 1: getAllUsers(currentUser.getToken());

                        break;

                    case 2: getUser(currentUser.getToken(), 1);
                        break;

                    default: System.out.print("hej");
                        break;
                }

            }else{
                System.out.println("Velkommen til Bookit" +
                        "\nHvad vil du?" +
                        "\n1: Se din pensumliste" +
                        "\n2: Ændre profiloplysninger" +
                        "\n3: Log ud");

                int i = sc.nextInt();

                switch (i){

                    case 1: String test3;

                        break;

                    case 2: String test;
                        break;

                    case 3: logout(currentUser.getToken());
                        break;

                    default: String test2;
                        break;
                }
            }





        }


    }

    public void logout(String token) throws IOException{

        System.out.print(token);

        uc.logout(token);




    }

    public void getAllUsers(String token) throws IOException {

        for (int i = 0; i < uc.getAllUsers(token).size(); i++){
            System.out.println(uc.getAllUsers(token).get(i).getEmail());
        }
    }

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

        ArrayList<Book> bookOfCurriculum = new ArrayList<>();

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








    }












}
