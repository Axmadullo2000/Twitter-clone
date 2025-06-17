package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {

    Scanner intScanner = new Scanner(System.in);
    Scanner strScanner = new Scanner(System.in);

    public int getInteger(String text) {
        try {
            System.out.printf("%s: ", text);
            return intScanner.nextInt();
        }catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public String getText(String text) {
        System.out.printf("%s: ", text);
        return strScanner.nextLine();
    }
}
