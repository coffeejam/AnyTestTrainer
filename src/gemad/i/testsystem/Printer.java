package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;

import java.util.ArrayList;


public class Printer {

    public static void printQuestion(Question question, boolean shuffle) {
        ArrayList<String> options = question.getOptions();
        ArrayList<Integer> order;
        char letter = 'а';
        if (shuffle) {
            order = Util.shuffleSmall(question.size());
        } else {
            order = Util.formArray(question.size());
        }
        System.out.println(question.getName());
        for (int i = 0; i < order.size() - 1; i++) {
            System.out.println(letter++ + ")" + options.get(order.get(i)) + ";");
        }
        System.out.println(letter + ")" + options.get(order.get(order.size() - 1)) + ".\n");

    }


}
