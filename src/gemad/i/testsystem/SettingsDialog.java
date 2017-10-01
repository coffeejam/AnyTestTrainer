package gemad.i.testsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 4 on 21.04.2016.
 */
public class SettingsDialog extends JFrame {
    private JCheckBox checkBoxShuffleQuest;
    private JCheckBox checkBoxShuffleAns;
    private JRadioButton radioButtonTest1;
    private JRadioButton radioButtonTest2;
    private JRadioButton radioButtonTest3;
    private JButton buttonBegin;
    private JRadioButton radioButtonAllTests;
    private JPanel rootPanel;

    public SettingsDialog() {
        super("Тест");
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocation(500, 250);
        this.setVisible(true);
        this.getRootPane().setDefaultButton(buttonBegin);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public boolean isQuestionShuffleChecked(){
        return checkBoxShuffleQuest.isSelected();
    }

    public boolean isOptionShuffleChecked() {
        return checkBoxShuffleAns.isSelected();
    }

    public int getChosenTest() {
        if (radioButtonTest1.isSelected()) {
            return 1;
        } else if (radioButtonTest2.isSelected()) {
            return 2;
        } else if (radioButtonTest3.isSelected()) {
            return 3;
        } else if (radioButtonAllTests.isSelected()) {
            return 0;
        } else {
            return -1;
        }
    }

    public void setActionListener(ActionListener al) {
        buttonBegin.addActionListener(al);
    }

}
