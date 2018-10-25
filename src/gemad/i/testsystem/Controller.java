package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TextConsts;
import gemad.i.testsystem.Forms.QuestionForm;
import gemad.i.testsystem.Forms.Results;
import gemad.i.testsystem.Forms.SettingsDialog;
import gemad.i.testsystem.Forms.Warning;
import gemad.i.testsystem.Utils.Translator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    TestBuilder test;
    private SettingsDialog settingsForm;
    private QuestionForm testingForm;
    private Results resultsForm;
    private Warning warningDialog;
    private ArrayList<String> testPaths;
    private final TestBuilder[] tb = new TestBuilder[1];
    private final ActionListener answerQuestion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkAnswer(tb[0], testingForm.getAnswer());
            if (tb[0].isLastQuestion()) {
                endTesting(tb[0]);
            } else {
                nextQuestion(tb[0]);
            }
        }
    };
    private final ActionListener startTest = e -> {
        try {
            startTest();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };
    private final ActionListener newTest = e -> {
        closeResults();
        newTesting();
    };
    private ActionListener endTest = e -> endTesting(tb[0]);

    public Controller(String[] args) {
        Translator.getInstance().setLanguage(Configuration.getInstance().getLanguage());
//        testPaths = new ArrayList<>();
//        if (args.length > 0)
//            if (args[0].endsWith(".txt"))
//                testPaths.add(args[0]);
        newTesting();
    }



    private void newTesting() {
        settingsForm = new SettingsDialog();
        ArrayList<String> tests = Configuration.getInstance().getTests();
        if (tests != null && !tests.isEmpty())
        settingsForm.setTestList(tests);
        settingsForm.setActionListener(startTest);
    }

    private void endTesting(TestBuilder test) {
        testingForm.dispose();
        resultsForm = new Results();
        resultsForm.setStat(test);
        Result.setAnswers(test.getWrongAnswers(), resultsForm);
        resultsForm.scrollToTop();
        resultsForm.setActionListener(newTest);
    }

    private void nextQuestion(TestBuilder test) {
        Question q = test.getNextQuestion();
        testingForm.editForm(q.getName(), q.getImage(), q.getShuffledOptions());
        setFormTitle(testingForm, test);
    }

    private void startTest() throws IOException {
        ArrayList<String> filenames = settingsForm.getSelectedTests();
        tb[0] = new TestBuilder(settingsForm.isQuestionShuffleChecked(), settingsForm.isOptionShuffleChecked(),
                filenames);
        if (tb[0].getTestList() == null) {
            warningDialog = new Warning(Translator.getInstance().translate(TextConsts.WRONG_TEST));
            warningDialog.pack();
            warningDialog.setVisible(true);
        } else {
//            testPaths = settingsForm.getTestList(); //saving testList for the next time
            settingsForm.dispose();
            Question q = tb[0].getCurrentQuestion();
            testingForm = new QuestionForm(q.getName(), q.getImage(), q.getShuffledOptions());
            setFormTitle(testingForm, tb[0]);
            testingForm.setSubmitActionListener(answerQuestion);
            testingForm.setEndActionListener(endTest);
        }
    }

    private void setFormTitle(JFrame form, TestBuilder test){
        form.setTitle((test.getCurrentQuestionNumber() + 1) + " / " + test.size() +
                " (" + test.getCurrentQuestion().getTestName() + ")");
    }

    private void checkAnswer(TestBuilder test, int answer) {
        Question q = test.getCurrentQuestion();
        if (q.getAnswerNumber() != answer) {
            test.addWrongAnswer(q);
        }
    }

    private void closeResults() {
        resultsForm.dispose();
    }
}
