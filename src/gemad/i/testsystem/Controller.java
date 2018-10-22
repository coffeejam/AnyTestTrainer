package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Controller {
    TestBuilder test;
    private SettingsDialog settingsForm;
    private QuestionForm testingForm;
    private Results resultsForm;
    private Warning warningDialog;
    private String lastTestPath;
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

    public Controller() {
        newTesting();
    }


    private void newTesting() {
        settingsForm = new SettingsDialog();
        if (lastTestPath != null)
        settingsForm.setDirectoryField(lastTestPath);
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
        String filename = settingsForm.getChosenTest().trim();
        tb[0] = new TestBuilder(settingsForm.isQuestionShuffleChecked(), settingsForm.isOptionShuffleChecked(),
                filename);
        if (tb[0].getTestList() == null) {
            warningDialog = new Warning("Неверный формат теста! Выберите корректный файл");
            warningDialog.pack();
            warningDialog.setVisible(true);
        } else {
            lastTestPath = filename;
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
