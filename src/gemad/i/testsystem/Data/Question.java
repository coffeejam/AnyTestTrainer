package gemad.i.testsystem.Data;

import gemad.i.testsystem.Util;

import java.util.ArrayList;

/**
 * Created by 4 on 20.04.2016.
 */
public class Question {
    private String name, testName;
    private ArrayList<String> options = new ArrayList<>();
    private ArrayList<Integer> order;
    private int answerNumber, questionNumber, optionsSize;
    private boolean shuffled = false;
    private final static String setBoldText = "\033[0;1m";
    private final static String setPlainText = "\033[0;0m";

    public Question(String name, ArrayList<String> options, int questionNumber, int answerNumber, String testNum) {
        this.name = name;
        this.options.addAll(options);
        this.answerNumber = answerNumber;
        this.questionNumber = questionNumber;
        this.testName = testNum;
        optionsSize = options.size();
        this.order = Util.formArray(this.size());
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getTestName() {
        return testName;
    }

    public int getAnswerNumber() {
        if (shuffled) {
            return order.indexOf(answerNumber);
        }
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    public int size() {
        return optionsSize;
    }

    public ArrayList<Integer> getOrder() {
        return order;
    }

    public void printQuestion(Question question, boolean shuffle, boolean markAnswer) {
        ArrayList<String> options = question.getOptions();
        char letter = 'а';
        System.out.println(question.getName());
        for (int i = 0; i < this.order.size() - 1; i++) {
            System.out.println(mark(markAnswer, setBoldText, i) + letter++ + ")"
                    + options.get(this.order.get(i)) + ";" + setPlainText);
        }
        System.out.println(mark(markAnswer, setBoldText, this.order.size() - 1) + letter + ")"
                + options.get(this.order.get(this.order.size() - 1)) + ".\n" + setPlainText);
    }

    public String returnQuestion(Question question, boolean shuffle, boolean markAnswer) {
        StringBuilder questionText = new StringBuilder();
        ArrayList<String> options = question.getOptions();
        char letter = 'а';
        questionText.append(question.getName() + "<br>");
        for (int i = 0; i < this.order.size() - 1; i++) {
            questionText.append(markByHtml(markAnswer, i, letter++ + ")"
                    + options.get(this.order.get(i)) + ";<br>"));
        }
        questionText.append(markByHtml(markAnswer, this.order.size() - 1, letter + ")"
                + options.get(this.order.get(this.order.size() - 1)) + ".<br>"));
        return questionText.toString();
    }

    private String mark(boolean set, String bold, int answerNumber) {
        if (set && answerNumber == this.getAnswerNumber()) {
            return bold + "=====> ";
        } else {
            return "";
        }
    }

    private String markByHtml(boolean set, int answerNumber, String text) {
        if (set && answerNumber == this.getAnswerNumber()) {
            return "<b>" + text + "</b>";
        } else {
            return text;
        }
    }

    public void shuffle() {
        this.order = Util.shuffleSmall(this.size());
        this.shuffled = true;
    }

    public String getOption(int number) {
        return options.get(order.get(number).intValue());
    }
}
