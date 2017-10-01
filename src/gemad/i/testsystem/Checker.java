package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by 4 on 20.04.2016.
 */
public class Checker {
    public static boolean checkAnswer(Question question) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (answer == null) {
            return false;
        }
        char letter = answer.trim().toLowerCase().charAt(0);
        int answerNumber = (int) letter - 'а';
        if (answerNumber == question.getAnswerNumber()) {
            return true;
        } else {
            return false;
        }
    }
}
