import java.util.Scanner;

/**
 * Simple scanner that takes an inputted financial statement and then
 * scans through to make sure that the statement is in a valid format
 */

public class FinancialScanner {

    private static int state = 0;
    private static int digitCount = 0;
    private static boolean valid = true;
    private static int decimalCount = 0;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while(true) {
            String num = in.nextLine();
            String[] characters = num.split("()");

            //Check if the statement starts correctly
            if (num.startsWith("$")) {
                //Move through char to validate the format
                scan:
                for (int i = 1; i < characters.length; i++) {
                    //Check the current char
                    switch (characters[i]) {
                        //Handles Symbols in the statement
                        case "*":
                            if (!((num.charAt(i - 1) == '$' || num.charAt(i - 1) == '*') && state == 0)) {
                                invalidate();
                                break scan;
                            }
                            break;
                        case ".":
                            //Checks for cases in which there are more than 1 decimal
                            if (decimalCount >= 1) {
                                invalidate();
                                break scan;
                            } else {
                                if (!(Character.isDigit(num.charAt(i - 1)) && Character.isDigit(num.charAt(i + 1)))) {
                                    invalidate();
                                    break scan;
                                }
                                else
                                    decimalCount++;
                            }
                            break;
                        case ",":
                            if (i + 3 <= num.length() - 1) {
                                if (!(Character.isDigit(num.charAt(i - 1)) && Character.isDigit(num.charAt(i + 1))
                                        && Character.isDigit(num.charAt(i + 2)) && Character.isDigit(num.charAt(i + 3)))) {
                                    System.out.println("Incorrect Statement Form");
                                    valid = false;
                                    break scan;
                                }
                            } else {
                                invalidate();
                                break scan;
                            }
                            break;

                        //Handles if the statement starts with a 0
                        case "0":
                            //If the first digit is 0 move to decimal point if one exists
                            if (digitCount == 0) {
                                if (i + 1 <= num.length() - 1)
                                    if (Character.isDigit(num.charAt(i + 1)) || num.charAt(i+1) == ',') {
                                        invalidate();
                                        break scan;
                                    } else if (num.contains("."))
                                        i = num.indexOf(".");
                            } else
                                digitCount++;
                            break;
                        //Handles digit cases
                        case "1":
                        case "2":
                        case "3":
                        case "4":
                        case "5":
                        case "6":
                        case "7":
                        case "8":
                        case "9":
                            digitCount++;
                            break;
                        default:
                            //Handles alien characters that shouldn't been in the statement
                            System.out.println("Invalid character " + num.charAt(i) + " in statement");
                            valid = false;
                            break scan;
                    }
                    //When the last star is found after the dollar sign, turn state positive
                    //to prevent any other stars found within the number that are misplaced
                    if (i == num.lastIndexOf("*")) state = 1;
                }
            } else
                invalidate();

            //If the statement comes through correctly
            if (valid)
                System.out.println("Format is correct!");

            //Reset values for next input
            digitCount = state = decimalCount = 0;
        }
    }

    //Declares that the statement inputted is incorrect
    public static void invalidate()
    {
        System.out.println("Incorrect Statement Form");
        valid = false;
    }
}

