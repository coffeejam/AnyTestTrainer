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
        settingsForm.setActionListener(startTest);
    }

    private void endTesting(TestBuilder test) {
        testingForm.dispose();
        resultsForm = new Results();
        resultsForm.setStat(test);
        resultsForm.setAnswers(test);
        resultsForm.scrollToTop();
        resultsForm.setActionListener(newTest);
    }

    private void nextQuestion(TestBuilder test) {
        Question q = test.getNextQuestion();
        testingForm.editForm(q.getName(), "", q.getOptions());
        setFormTitle(testingForm, test);
    }

    private void startTest() throws IOException {
        tb[0] = new TestBuilder(settingsForm.isQuestionShuffleChecked(), settingsForm.isOptionShuffleChecked(),
                settingsForm.getChosenTest().trim());
        if (tb[0].getTestList() == null) {
            warningDialog = new Warning("Неверный формат теста! Выберите корректный файл");
            warningDialog.pack();
            warningDialog.setVisible(true);
        } else {
            settingsForm.dispose();
            Question q = tb[0].getCurrentQuestion();
            testingForm = new QuestionForm(q.getName(), "", q.getOptions());
            setFormTitle(testingForm, tb[0]);
            testingForm.setSubmitActionListener(answerQuestion);
            testingForm.setEndActionListener(endTest);
        }
    }

    private void setFormTitle(JFrame form, TestBuilder test){
        form.setTitle((test.getCurrentQuestionNumber() + 1) + " / " + test.size());
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
