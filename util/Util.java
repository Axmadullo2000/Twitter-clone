package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {

    Scanner intScanner = new Scanner(System.in);
    Scanner strScanner = new Scanner(System.in);

    public int getInteger(String text) {
        while (true) {
            System.out.printf("%s: ", text);
            String input = intScanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    public String getText(String text) {
        System.out.printf("%s: ", text);
        return strScanner.nextLine();
    }

}
