package gemad.i.testsystem.Data;

import gemad.i.testsystem.Utils.Util;

import java.util.ArrayList;

public class Question {
    private String name, testName;
    private ArrayList<String> options = new ArrayList<>();
    private int answerNum;
    private ArrayList<Integer> order;
    private int questionNumber;
    private boolean shuffled = false;
    private final static String setBoldText = "\033[0;1m";
    private final static String setPlainText = "\033[0;0m";
    private ImageWrap imageWrap;


    public Question(String name, ArrayList<String> options, int questionNumber, int answer, String testName,
                    ImageWrap imageWrap) {
        this.name = name;
        this.options.addAll(options);
        this.answerNum = answer;
        this.questionNumber = questionNumber;
        this.testName = testName;
        this.imageWrap = imageWrap;

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
            return order.indexOf(answerNum);
        }
        return answerNum;
    }

    public ImageWrap getImage() {
        return imageWrap;
    }

    private boolean checkAnswer(int option){
        return answerNum == option;
    }

    public void setAnswer(int answer) {
        this.answerNum = answer;
    }

    public int size() {
        return options.size();
    }

    public ArrayList<Integer> getOrder() {
        return order;
    }


    // prints questions during test
    public void printQuestion(Question question, boolean shuffle, boolean markAnswer) {
        ArrayList<String> options = question.getOptions();
        char letter = 'а';
        System.out.println(question.getName());
        for (int i = 0; i < this.order.size() - 1; i++) {
            System.out.println(mark(markAnswer, setBoldText, i) + letter++ + ")"
                    + options.get(this.order.get(i).intValue()) + ";" + setPlainText);
        }
        System.out.println(mark(markAnswer, setBoldText, this.order.size() - 1) + letter + ")"
                + options.get(this.order.get(this.order.size() - 1)) + ".\n" + setPlainText);
    }

    //prints questions at the end of the test
//    public String returnQuestion(boolean shuffle, boolean markAnswer) {
//        StringBuilder questionText = new StringBuilder();
//        ArrayList<String> options = getOptions();
//        char letter = 'а';
//        questionText.append(getName() + "<br>");
//        for (int i = 0; i < this.order.size() - 1; i++) {
//            questionText.append(markByHtml(markAnswer, i, letter++ + ")"
//                    + options.get(this.order.get(i)) + ";<br>"));
//        }
//        questionText.append(markByHtml(markAnswer, this.order.size() - 1, letter + ")"
//                + options.get(this.order.get(this.order.size() - 1)) + ".<br>"));
//        return questionText.toString();
//    }

    private String mark(boolean set, String bold, int answer) {
        if (set && checkAnswer(answer)) {
            return bold + "=====> ";
        } else {
            return "";
        }
    }

//    private String markByHtml(boolean set, int answer, String text) {
//        if (set && checkAnswer(answer)) {
//            return "<b>" + text + "</b>";
//        } else {
//            return text;
//        }
//    }

    public void shuffle() {
        this.order = Util.shuffleSmall(this.size());
        this.shuffled = true;
    }

    public String getOption(int number) {
        return options.get(order.get(number).intValue());
    }

    public void addOption(String option){
        options.add(option);
    }

    public ArrayList<String> getShuffledOptions() {
        ArrayList<String> shuffledOptions = new ArrayList<>();
        for (int i = 0; i < this.order.size(); i++) {
            shuffledOptions.add(options.get(this.order.get(i).intValue()));
        }
        return shuffledOptions;
    }

    public boolean hasImage() {
        return !imageWrap.getPath().isEmpty();
    }
}
