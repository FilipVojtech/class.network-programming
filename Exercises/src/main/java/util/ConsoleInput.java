package util;

import java.util.Scanner;

public class ConsoleInput {
    static Scanner sc = new Scanner(System.in);

    /**
     * Get a yes/no response from the user.
     *
     * @param prompt Message to be displayed
     * @return True if user answered yes. False when user answered no.
     */
    public static boolean getYesNo(String prompt) {

        while (true) {
            System.out.print(prompt + " [Y/n] ");
            String input = sc.nextLine();
            switch (input.toLowerCase()) {
                case ""-> {
                    System.out.println("y");
                    return true;
                }
                case "y", "yes" -> {
                    return true;
                }
                case "n", "no" -> {
                    return false;
                }
                default -> System.out.println("Please choose an answer.");
            }
        }
    }

    /**
     * Get a string from the user.
     *
     * @param prompt Message to be displayed.
     * @return String of what the user typed in.
     */
    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}
