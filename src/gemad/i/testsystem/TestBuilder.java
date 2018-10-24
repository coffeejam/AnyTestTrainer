package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;
import gemad.i.testsystem.Utils.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class TestBuilder {

    private TestList test;
    private boolean doShuffleQ, doShuffleO, repeatTest;
    private ArrayList<Integer> questionOrder;
    private ArrayList<Question> wrongAnswers = new ArrayList<>();
    private int currentQuestion = 0;

    public TestBuilder(boolean doShuffleQ, boolean doShuffleO, ArrayList<String> testFile) throws IOException {
        test = new TestList("");
        for (int i = 0; i < testFile.size(); i++) {
            TestList tempTest = Reader.readTest(testFile.get(i));
            if (tempTest == null)
                return;
            fillSingleQuestions(tempTest); //adds options where there is only one
            test.addQuestions(tempTest.getQuestions()); // merges several tests into one
        }
        this.doShuffleQ = doShuffleQ;
        this.doShuffleO = doShuffleO;


        questionOrder = doShuffleQ ? Util.shuffleSmall(test.size()) : Util.formArray(test.size()); //todo maybe change to int[]
        if (doShuffleO) {
            for (Question q : test.getQuestions()) {
                q.shuffle();
            }
        }
    }

    private void fillSingleQuestions(TestList test) {
        ArrayList<Question> questions = test.getQuestions();
        ArrayList<Integer> singleQuestions = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        int optionsNumber = 4; // ideal options number
        for (int i = 0; i < questions.size(); i++){
            if (questions.get(i).size() == 1) {
                singleQuestions.add(i);
            }
        }
        if (singleQuestions.size() < 2)
            return;
        if (singleQuestions.size() < 4)
            optionsNumber = singleQuestions.size();
        for (Integer singleQ : singleQuestions) {
            Question q = questions.get(singleQ);
            ArrayList<Integer> validOptions = new ArrayList<>();
            validOptions.addAll(singleQuestions);
            validOptions.remove(singleQ);
            while (q.size() < optionsNumber) { // until there is ideal number of options
                Integer randomOption = random.nextInt(validOptions.size());
                    q.addOption(questions.get(validOptions.get(randomOption)).getOption(0));
                    //gets first and only option from question
                    validOptions.remove(randomOption.intValue());
            }
        }
        for (Integer singleQ : singleQuestions) {
            questions.get(singleQ).shuffle();
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
            return test.getQuestion(questionOrder.get(++currentQuestion));
        } else {
            return null;
        }
    }

    public Question getCurrentQuestion() {
        return test.getQuestion(questionOrder.get(currentQuestion));
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

    public static boolean isTestFile(String filename){
        if (filename != null && !filename.isEmpty() && filename.endsWith(".txt")) {
            File file = new File(filename);
            BufferedReader br;
            try {
                br = new BufferedReader( new InputStreamReader(new FileInputStream(filename), "windows-1251"));
                String line = br.readLine();
                if (line == null || !line.startsWith(":"))
                    return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else return false;
    }
}
