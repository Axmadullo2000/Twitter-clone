package util;

import java.util.Scanner;

public class Util {

    static Scanner intScanner = new Scanner(System.in);
    static Scanner strScanner = new Scanner(System.in);

    public static int getInteger(String text) {
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

    public static String getText(String text) {
        System.out.printf("%s: ", text);
        return strScanner.nextLine();
    }

}
