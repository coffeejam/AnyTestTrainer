package gemad.i.testsystem;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class Results extends JFrame{
    private JButton okButton;
    private JTextArea textArea2;
    private JPanel rootPanel;
    private JTextPane editorPane1;
    private JLabel label1;
    private JScrollPane scrollPane1;

    public Results() {
        super("Results");
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(500, 250, 750, 500);
        this.setVisible(true);
        this.setMinimumSize(this.getSize());
        this.getRootPane().setDefaultButton(okButton);
//        editorPane1.setContentType("text/html");
//        this.addComponentListener(new ResizeListener());
    }

    public void setActionListener(ActionListener newTest) {
        okButton.addActionListener(newTest);
    }

    public void setStat(TestBuilder test) {
        textArea2.setText(Result.returnStat(test.getWrongAnswers(), test.getCurrentQuestionNumber() + 1));
    }

//    public void setAnswers(TestBuilder test) {
//        editorPane1.setText("<html>" + Result.returnAnswers(test.getWrongAnswers(), test.getTestList()) + "</html>");
//    }

    public void scrollToTop() {
        editorPane1.setCaretPosition(0);
    }

    public void addImageToResult(ImageIcon image) throws BadLocationException {
        StyledDocument doc = (StyledDocument) editorPane1.getDocument();
        Style style = doc.addStyle("StyleName", null);
        StyleConstants.setIcon(style, image);
        doc.insertString(doc.getLength(), "ignored text", style);
    }

    public void addToResult(String text, boolean bold) throws BadLocationException {
        StyledDocument doc = editorPane1.getStyledDocument();
        Style style = doc.addStyle("StyleName", null);
        if (bold)
        StyleConstants.setBold(style, true);
        doc.insertString(doc.getLength(), text, style);
//        doc.setParagraphAttributes(12, 1, editorPane1.getStyle("Bold"), true);
    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            rootPanel.revalidate();
            rootPanel.repaint();
        }
    }



    public static void main(String[] args) throws MalformedURLException, BadLocationException {

        Results form = new Results();

        form.addToResult("Не жирная \n", false);
        form.addToResult("Жирная \n", true);
        form.addToResult("Снова не жирная \n", false);
        form.pack();
        form.setVisible(true);
    }
}
