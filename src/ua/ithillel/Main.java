package ua.ithillel;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        System.out.println("Welcome to the library!");
        while (!quit) {

            menu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> Book.addBook();
                case 2 -> Book.loadFromFile();
                case 3 -> Book.removeBook();
                case 0 -> {
                    quit = true;
                    System.out.println("\nThanks.Bye!");
                }
                default -> System.out.println("\nSomething went wrong");
            }
        }
    }

    private static void menu() {
        System.out.println("""
                \nChoose your action:
                1) Add book
                2) Show list of books
                3) Remove book
                0) Exit""");
    }
}
