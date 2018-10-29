package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;
import gemad.i.testsystem.Data.TextConsts;
import gemad.i.testsystem.Forms.Results;
import gemad.i.testsystem.Utils.Translator;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Result {

    public static void printResult(ArrayList<Question> answers, TestList test) {
        final int testSize = test.size();
        Question q;
        if (answers.size() == testSize) {
            System.out.println(TextConsts.ALL_WRONG);
        } else {
            switch (answers.size()) {
                case 0:
                    if (testSize == 10) {
                        System.out.println(TextConsts.TEN_OUT_OF_TEN_CORRECT);
                    } else {
                        System.out.println(TextConsts.ALL_CORRECT);
                    }
                    return;
                case 10:
                    if (testSize == 10) {
                        System.out.println("Господи, 10 из 10! И все неправильно!");
                        break;
                    }
                default:
                    System.out.println("Вы ответили неверно на " + answers.size() + " вопросов из " + testSize);
                    break;
            }
        }
        System.out.println("Неверные ответы: ");
        for (int i = 0; i < answers.size() - 1; i++) {
            q = answers.get(i);
            System.out.print(q.getTestName() + "." + q.getQuestionNumber() + ", ");
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
        }
        q = answers.get(answers.size() - 1);
        System.out.println(q.getTestName() + "." + q.getQuestionNumber() + ". ");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Повторить верные ответы?");
        String answer = scanner.nextLine().toLowerCase();
        boolean doRepeat = (answer.equals("y") || answer.equals("д"));
        if (!doRepeat) {
            return;
        }
        for (int i = 0; i < answers.size() - 1; i++) {
            q = answers.get(i);
            System.out.print(q.getTestName() + "." + q.getQuestionNumber() + ". ");
            q.printQuestion(q, false, true);
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String returnStat(ArrayList<Question> answers, int questionsAnswered) {
        String result;
        if (answers.size() == questionsAnswered && questionsAnswered != 10) {
            result = Translator.getInstance().translate(TextConsts.ALL_WRONG);
        } else {
            switch (answers.size()) {
                case 0:
                    if (questionsAnswered == 10) {
                        result = Translator.getInstance().translate(TextConsts.TEN_OUT_OF_TEN_CORRECT);
                    } else {
                        result = Translator.getInstance().translate(TextConsts.ALL_CORRECT);
                    }
                    return result;
                case 10:
                    if (questionsAnswered == 10) {
                        result = Translator.getInstance().translate(TextConsts.TEN_OUT_OF_TEN_WRONG);
                        break;
                    }
                default:
                    result = String.format(Translator.getInstance().translate(TextConsts.YOU_ANSWERED_WRONG),
                            answers.size(), questionsAnswered);
                    break;
            }
        }
        return result;
    }

    public static void setAnswers(ArrayList<Question> answers, Results form) {
        Question q;
        for (int i = 0; i < answers.size(); i++) {
            q = answers.get(i);
            /*q.getTestName() + "." + Change this back when several tests feature added*/
            ArrayList<String> options = q.getShuffledOptions();
            int correctAnswer = q.getAnswerNumber();
            try {
                form.addToResult(q.getQuestionNumber() + 1 + ". " + q.getName() + "\n", false);
                if (q.hasImage()) {
                    form.addImageToResult(q.getImage());
                    form.addToResult("\n", false);
                }
                char c = 'a';
                for (int j = 0; j < options.size(); j++, c++){
                    form.addToResult("   " + c + ") " + options.get(j) + "\n", j==correctAnswer);
                }
                form.addToResult("\n", false);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }


}
