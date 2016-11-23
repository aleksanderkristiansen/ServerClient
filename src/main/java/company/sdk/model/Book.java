package company.sdk.model;

import java.util.ArrayList;

public class Book {

    private int bookID;
    private double ISBN;
    private String publisher;
    private int publisherID;
    private String title;
    private String author;
    private double price;
    private int version;
    private ArrayList<Author> lstAuthors;
    private ArrayList<BookStore> lstBookStores;



    public Book(int bookID, String publisher, String title, String author, int version, double ISBN, double price) {
        this.bookID = bookID;
        this.publisher = publisher;
        this.title = title;
        this.author = author;
        this.version = version;
        this.ISBN = ISBN;
        this.price = price;
    }

    public Book(String publisher, String title, String author, int version, double ISBN, double price) {
        this.publisher = publisher;
        this.title = title;
        this.author = author;
        this.version = version;
        this.ISBN = ISBN;
        this.price = price;
    }

    public Book(String title, String author, double ISBN, double price, String publisher){
        this.title = title;
        this.author = title;
        this.ISBN = ISBN;
        this.price = price;
        this.publisher = publisher;
    }

    public Book(String title, int version, double ISBN, int publisherID, ArrayList<Author> lstAuthors, ArrayList<BookStore> lstBookStores){
        this.title = title;
        this.ISBN = ISBN;
        this.version = version;
        this.publisherID = publisherID;
        this.lstAuthors = lstAuthors;
        this.lstBookStores = lstBookStores;
    }


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public double getISBN() {
        return ISBN;
    }

    public void setISBN(double ISBN) {
        this.ISBN = ISBN;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public ArrayList<Author> getLstAuthors() {
        return lstAuthors;
    }

    public void setLstAuthors(ArrayList<Author> lstAuthors) {
        this.lstAuthors = lstAuthors;
    }

    public ArrayList<BookStore> getLstBookStores() {
        return lstBookStores;
    }

    public void setLstBookStores(ArrayList<BookStore> lstBookStores) {
        this.lstBookStores = lstBookStores;
    }


    //    @Override
//    public String toString() {
//        return "model.Book{" +
//                "name='" + name + '\'' +
//                ", publisher='" + publisher + '\'' +
//                ", ISBN='" + ISBN + '\'' +
//                '}';
//    }
}
