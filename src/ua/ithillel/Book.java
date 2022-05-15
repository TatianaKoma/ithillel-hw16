package ua.ithillel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Book {
    private final String name;
    private final String author;
    public static String FILENAME = "file.txt";
    public static String TEMP_FILENAME = "tempFile.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public Book(String name, String surnameOfAuthor) {
        this.name = name;
        this.author = surnameOfAuthor;
    }

    @Override
    public String toString() {
        return "book: \"" + name + '\"' +
                ", author: '" + author + '\'';
    }

    public static void addBook() {
        System.out.println("\nEnter a name of book: ");
        String nameOfBook = scanner.nextLine();
        if (isBookExists(nameOfBook)) {
            System.out.println("The book: \"" + nameOfBook + "\" already exists in the catalog.");
        } else {
            System.out.println("Enter a surname of author: ");
            String surnameOfAuthor = scanner.nextLine();
            Book book = new Book(nameOfBook, surnameOfAuthor);
            saveToFile(book);
            System.out.println("The book: \"" + nameOfBook + "\" by author: " + surnameOfAuthor + " was added to the catalog.");
        }
    }

    public static void loadFromFile() {
        if (isCatalogExists()) {
            System.out.println("\nThe list of books in the catalog: ");
            try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
                String book;
                while ((book = reader.readLine()) != null) {
                    System.out.println(book);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeBook() {
        if (isCatalogExists()) {
            System.out.println("\nEnter a name of book:");
            String nameOfBook = scanner.nextLine();
            if (!isBookExists(nameOfBook)) {
                System.out.println("The book: \"" + nameOfBook + "\" doesn't exist in the catalog.");
            } else {
                File tempFile = new File(TEMP_FILENAME);
                File file = new File(FILENAME);
                try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));) {
                    String book;
                    String[] dataOfBook;
                    while ((book = reader.readLine()) != null) {
                        dataOfBook = book.split("\"");
                        if (!nameOfBook.equals(dataOfBook[1])) {
                            writer.write(book + System.getProperty("line.separator"));
                        }
                    }
                    System.out.println("The book: \"" + nameOfBook + "\" was removed from the catalog");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Path path1 = Paths.get(FILENAME);
                Path path2 = Paths.get(TEMP_FILENAME);

                try {
                    Files.delete(path1);
                    Files.move(path2, path1);
                } catch (IOException e) {
                    System.err.println("Something went wrong - " + e);
                }
            }
        }
    }

    private static void saveToFile(Book book) {
        try (BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter(FILENAME, true)))) {
            bufferedWriter.write(book.toString() + System.getProperty("line.separator"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isCatalogExists() {
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("\nThe catalog is empty. Try to add a book.");
            return false;
        } else {
            return true;
        }
    }

    private static boolean isBookExists(String nameOfBook) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String book;
            String[] dataOfBook;
            while ((book = reader.readLine()) != null) {
                dataOfBook = book.split("\"");
                if (nameOfBook.equals(dataOfBook[1])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
