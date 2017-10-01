package gemad.i.testsystem;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by 4 on 21.04.2016.
 */
public class TestDialog extends JFrame{
    private JTextArea textArea1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton answerButton;
    private JButton endButton;
    private JPanel rootPanel;

    public TestDialog() {
        super("");
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(500, 250, 750, 500);
        this.setVisible(true);
        this.getRootPane().setDefaultButton(answerButton);
    }

    public void setText(String text) {
        textArea1.setText(text);
    }

    public void setOptionText(int number, String text) {
        switch (number) {
            case 0:
                radioButton1.setText("<html>" + text + "</html>");
                break;
            case 1:
                radioButton2.setText("<html>" + text + "</html>");
                break;
            case 2:
                radioButton3.setText("<html>" + text + "</html>");
                break;
            case 3:
                radioButton4.setText("<html>" + text + "</html>");
                break;
            default:
                break;
        }
    }

    public void setEndActionListener(ActionListener al) {
        endButton.addActionListener(al);
    }

    public void setSubmitActionListener(ActionListener al) {
        answerButton.addActionListener(al);
    }

    public int getAnswer() {
        if (radioButton1.isSelected()) {
            return 0;
        } else if (radioButton2.isSelected()) {
            return 1;
        } else if (radioButton3.isSelected()) {
            return 2;
        } else if (radioButton4.isSelected()) {
            return 3;
        } else {
            return -1;
        }
    }

    public void setSelected(int radioButton) {
        switch (radioButton) {
            case 0:
                radioButton1.setSelected(true);
                break;
            case 1:
                radioButton2.setSelected(true);
                break;
            case 2:
                radioButton3.setSelected(true);
                break;
            case 3:
                radioButton4.setSelected(true);
                break;
            default:
                break;
        }
    }
}
