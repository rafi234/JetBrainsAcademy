package bullscows;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    Scanner sc = new Scanner(System.in);
    private String code;
    private int numberOfDigits;
    private int possibleSymbols;
    private boolean isGameFinished = false;
    private int bulls;
    private int cows;

    public void startGame() {
        this.prepareGame();
        this.gameLoop();
    }

    private void prepareGame() {
        do {
            try {
                System.out.println("Input the length of the secret code:");
                numberOfDigits = sc.nextInt();
                System.out.println("Input the number of possible symbols in the code:");
                possibleSymbols = sc.nextInt();
                this.code = SecretCodeGenerator.getSecretCode(numberOfDigits, possibleSymbols);
                System.out.println("Okay, let's start a game!");
                isGameFinished = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: it's not possible to generate a code with a length of "
                        + this.numberOfDigits + " with " + this.possibleSymbols + " unique symbols.");
            } catch (InputMismatchException e) {
                System.out.println("Error: given number isn't a number!");
                sc.next();
            }
        } while (!isGameFinished);
    }

    private void gameLoop() {
        boolean flag = false;
        while (!flag) {
            try {
                flag = checkIfGivenCodeIsCorrect(sc.next());
            } catch (InputMismatchException e) {
                System.out.println("Incorrect length of given number. Length must equal to " + numberOfDigits);
            }
        }
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private boolean checkIfGivenCodeIsCorrect(String code) {
        if (code.length() != numberOfDigits) {
            throw new InputMismatchException();
        }
        if (code.equals(this.code)) {
            System.out.println(this.stringCowsAndBullsGenerator(0, numberOfDigits));
            return true;
        }
        this.countBullsAndCows(code);
        System.out.println(this.stringCowsAndBullsGenerator(cows, bulls));
        return false;
    }

    private void countBullsAndCows(String code) {
        this.cows = 0;
        this.bulls = 0;
        String[] codeParts = this.code.split("");
        String[] givenCodeParts = code.split("");
        for (int i = 0; i < code.length(); ++i) {
            if (this.code.contains(givenCodeParts[i])) {
                if (codeParts[i].equals(givenCodeParts[i])) {
                    this.bulls++;
                } else {
                    this.cows++;
                }
            }
        }
    }

    private StringBuilder stringCowsAndBullsGenerator(int cows, int bulls) {
        StringBuilder message = new StringBuilder("Grade: ");
        if (cows != 0 && bulls != 0) {
            message.append(bulls)
                    .append(bulls == 1 ? " bull and " : " bulls and ")
                    .append(cows)
                    .append(cows == 1 ? " cow" : " cows");
        } else {
            if (cows == 0 && bulls == 0) {
                message.append("None");
            } else if (cows == 0) {
                message.append(bulls)
                        .append(bulls == 1 ? " bull" : " bulls");
            } else {
                message.append(cows)
                        .append(cows == 1 ? " cow" : " cows");
            }
        }
        return message;
    }
}
