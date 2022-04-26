package bullscows;

import java.util.Random;

public class SecretCodeGenerator {
    static public Random random = new Random();

    static public String getSecretCode(int numberOfDigits, int possibleSymbols) {
        if (possibleSymbols <= 0
                || numberOfDigits <= 0
                || possibleSymbols > 36
                || possibleSymbols < numberOfDigits) {
            throw new NumberFormatException();
        }
        String strCode;
        do {
            strCode = generateSecretCode(numberOfDigits, possibleSymbols);
        } while (!checkIfNumbersAreUnique(strCode));
        printMess(numberOfDigits, possibleSymbols);
        return strCode;
    }

    private static String generateSecretCode(int numberOfDigits, int possibleSymbols) {
        StringBuilder secretCode = new StringBuilder();
        for (int i = 0; i < numberOfDigits; ++i) {
            int nextDigitOfCode = random.nextInt(possibleSymbols);
            secretCode.append(nextDigitOfCode < 10 ? (char) (nextDigitOfCode + '0') : (char) (nextDigitOfCode + 'a' - 10));
        }
        return secretCode.toString();
    }

    private static boolean checkIfNumbersAreUnique(String number) {
        for (int i = 0; i < number.length() - 1; ++i) {
            for (int j = i + 1; j < number.length(); ++j) {
                if (number.charAt(i) == number.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printMess(int numberOfDigits, int possibleSymbols) {
        StringBuilder str = new StringBuilder("The secret is prepared: ");
        str.append("*".repeat(Math.max(0, numberOfDigits)));
        str.append(" (0-");

        if (possibleSymbols < 11) {
            str.append(possibleSymbols - 1).append(").");
        } else {
            str.append("9, a-").append((char) (possibleSymbols + 'a' - 11)).append(").");
        }
        System.out.println(str.toString());
    }

}
