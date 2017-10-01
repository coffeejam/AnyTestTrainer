package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by 4 on 30.05.2016.
 */
public class Controller {
    TestBuilder test;
    SettingsDialog settingsForm;
    TestDialog testingForm;
    Results resultsForm;
    final TestBuilder[] tb = new TestBuilder[1];
    final ActionListener answerQuestion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (tb[0].isLastQuestion()) {
                endTesting(tb[0]);
            } else {
                checkAnswer(tb[0], testingForm.getAnswer());
                nextQuestion(tb[0]);
            }
        }
    };
    final ActionListener startTest = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                startTest();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            nextQuestion(tb[0]);
        }
    };
    final ActionListener newTest = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            closeResults();
            newTesting();
        }
    };
    ActionListener endTest = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            endTesting(tb[0]);
        }
    };

    public Controller() {
        newTesting();
    }


    public void newTesting() {
        settingsForm = new SettingsDialog();
        settingsForm.setActionListener(startTest);
    }

    public void endTesting(TestBuilder test) {
        testingForm.dispose();
        resultsForm = new Results();
        resultsForm.setStat(test);
        resultsForm.setAnswers(test);
        resultsForm.scrollToTop();
        resultsForm.setActionListener(newTest);
    }

    public void nextQuestion(TestBuilder test) {
        Question q = test.getNextQuestion();
        testingForm.setText(q.getName());
        for (int i = 0; i < 4; i++) {
            testingForm.setOptionText(i, q.getOption(i));
        }
        testingForm.setSelected(0);
        testingForm.setTitle((test.getCurrentQuestionNumber() + 1) + " / " + test.size());
    }

    public void startTest() throws IOException {
        tb[0] = new TestBuilder(settingsForm.isQuestionShuffleChecked(), settingsForm.isOptionShuffleChecked(),
                settingsForm.getChosenTest());
        settingsForm.dispose();
        testingForm = new TestDialog();
        testingForm.setSubmitActionListener(answerQuestion);
        testingForm.setEndActionListener(endTest);
    }

    public void checkAnswer(TestBuilder test, int answer) {
        Question q = test.getCurrentQuestion();
        if (q.getAnswerNumber() != answer) {
            test.addWrongAnswer(q);
        }
    }

    public void closeResults() {
        resultsForm.dispose();
    }
}
