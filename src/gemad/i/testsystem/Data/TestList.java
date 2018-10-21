package gemad.i.testsystem.Data;

import java.util.ArrayList;

public class TestList {
    private ArrayList<Question> questions = new ArrayList<>();
    private String testName;


    public TestList(String name) {
        testName = name;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void addQuestions(ArrayList<Question> questions) {
        this.questions.addAll(questions);
    }

    public Question getQuestion(int number) {
        if (number < questions.size()) {
            return questions.get(number);
        } else {
            return null;
        }
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int size() {
        return questions.size();
    }

    public String getName() {
        return testName;
    }
}
