package gemad.i.testsystem;

import javax.swing.*;
import java.awt.event.ActionListener;


public class Results extends JFrame{
    private JButton okButton;
    private JTextArea textArea2;
    private JPanel rootPanel;
    private JEditorPane editorPane1;
    private JLabel label1;
    private JScrollPane scrollPane1;

    public Results() {
        super("Results");
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(500, 250, 750, 500);
        this.setVisible(true);
        this.getRootPane().setDefaultButton(okButton);
        editorPane1.setContentType("text/html");
    }

    public void setActionListener(ActionListener newTest) {
        okButton.addActionListener(newTest);
    }

    public void setStat(TestBuilder test) {
        textArea2.setText(Result.returnStat(test.getWrongAnswers(), test.getCurrentQuestionNumber() + 1));
    }

    public void setAnswers(TestBuilder test) {
        editorPane1.setText("<html>" + Result.returnAnswers(test.getWrongAnswers(), test.getTestList()) + "</html>");
    }

    public void scrollToTop() {
        editorPane1.setCaretPosition(0);
    }
}
