package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 4 on 30.05.2016.
 */
public class TestBuilder {
    final static private String test1filename = "test1.txt";
    final static private String test2filename = "test2.txt";
    final static private String test3filename = "test3.txt";

    final static private String test1name = "Общая характеристика организации информационного обмена в информационно-технологических сетях";
    final static private String test2name = "Общая характеристика организации информационного обмена в локальных вычислительных сетях";
    final static private String test3name = "Организация информационного взаимодействия в ИТС глобального сообщества Интернет";

    final static private int[] test1answers = {0, 2, 1, 3, 2, 1, 2, 1, 1, 2, 2, 0, 3, 2, 3, 0, 1,
            0, 2, 1, 3, 1, 0, 2, 1, 3, 2, 2, 2, 3, 1, 2, 0, 3, 3};
    final static private int[] test2answers = {1, 3, 0, 3, 0, 1, 1, 3, 2, 3, 1, 3, 3, 1, 1, 3, 2,
            2, 0, 3, 1, 3, 3, 0, 3, 1, 3, 1, 3, 1, 0, 3, 1, 0, 1};
    final static private int[] test3answers = {1, 1, 1, 3, 0, 1, 0, 3, 1, 2, 0, 2, 1, 3, 1, 0, 2,
            3, 3, 0, 2, 3, 1, 2, 0, 2, 0, 1, 2, 1, 3, 1, 3, 1, 3, 1, 0, 2, 2, 1};

    final static private String testNum1 = "1";
    final static private String testNum2 = "1а";
    final static private String testNum3 = "2";

    private TestList test;
    private boolean doShuffleQ, doShuffleO, repeatTest;
    private ArrayList<Integer> questionOrder;
    private ArrayList<Question> wrongAnswers = new ArrayList<>();
    private int currentQuestion = -1;

    public TestBuilder(boolean doShuffleQ, boolean doShuffleO, int testNum) throws IOException {
        final TestList test1 = Reader.readTest(test1filename, test1name, test1answers, testNum1);
        final TestList test2 = Reader.readTest(test2filename, test2name, test2answers, testNum2);
        final TestList test3 = Reader.readTest(test3filename, test3name, test3answers, testNum3);
        this.doShuffleQ = doShuffleQ;
        this.doShuffleO = doShuffleO;
        switch (testNum) {
            case 0:
                test = new TestList("Полный тест");
                test.addQuestions(test1.getQuestions());
                test.addQuestions(test2.getQuestions());
                test.addQuestions(test3.getQuestions());
                break;
            case 1:
                test = test1;
                break;
            case 2:
                test = test2;
                break;
            case 3:
                test = test3;
                break;
            default:
                test = test1;
                break;
        }
        questionOrder = doShuffleQ ? Util.shuffleSmall(test.size()) : Util.formArray(test.size());
        if (doShuffleO) {
            for (Question q : test.getQuestions()) {
                q.shuffle();
            }
        }
    }


    public void setTest(TestList test) {
        this.test = test;
    }

    public void setDoShuffleQ(boolean doShuffleQ) {
        this.doShuffleQ = doShuffleQ;
    }

    public void setDoShuffleO(boolean doShuffleO) {
        this.doShuffleO = doShuffleO;
    }

    public void setQuestionOrder(ArrayList<Integer> questionOrder) {
        this.questionOrder = questionOrder;
    }

    public int size() {
        if (questionOrder != null) {
            return questionOrder.size();
        } else {
            return 0;
        }
    }

    public Question getNextQuestion() {
        if (questionOrder != null && currentQuestion < questionOrder.size()) {
            return test.getQuestion(questionOrder.get(++currentQuestion).intValue());
        } else {
            return null;
        }
    }

    public Question getCurrentQuestion() {
        return test.getQuestion(questionOrder.get(currentQuestion).intValue());
    }

    public boolean isLastQuestion() {
        return questionOrder.size() - 1 == currentQuestion;
    }

    public void addWrongAnswer(Question q) {
        wrongAnswers.add(q);
    }

    public ArrayList<Question> getWrongAnswers() {
        return wrongAnswers;
    }

    public TestList getTestList() {
        return test;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestion;
    }
}
