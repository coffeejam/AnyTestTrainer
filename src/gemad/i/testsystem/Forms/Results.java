package gemad.i.testsystem.Forms;

import gemad.i.testsystem.Data.TextConsts;
import gemad.i.testsystem.Result;
import gemad.i.testsystem.TestBuilder;
import gemad.i.testsystem.Utils.Translator;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.MalformedURLException;


public class Results extends JFrame{
    private JButton okButton;
    private JTextArea textArea2;
    private JPanel rootPanel;
    private JTextPane editorPane1;
    private JLabel label1;
    private JScrollPane scrollPane1;

    public Results() {
        super(Translator.getInstance().translate(TextConsts.RESULTS));
        label1.setText(Translator.getInstance().translate(TextConsts.CORRECT_ANSWERS));
        okButton.setText(Translator.getInstance().translate(TextConsts.BUTTON_OK));
        textArea2.setFont(new Font("Calibri", Font.PLAIN, 14));
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(500, 250, 750, 500);
        this.setMinimumSize(this.getSize());
        this.getRootPane().setDefaultButton(okButton);

//        editorPane1.setContentType("text/html");
//        this.addComponentListener(new ResizeListener());
        this.setVisible(true);
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
        StyleConstants.setLeftIndent(style, 16);
        doc.insertString(doc.getLength(), "ignored text", style);
    }

    public void addToResult(String text, boolean bold) throws BadLocationException {
        StyledDocument doc = editorPane1.getStyledDocument();
        StyleContext context = new StyleContext();
        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
//        Style style = doc.addStyle("StyleName", null);
        if (bold)
        StyleConstants.setBold(style, true);
        StyleConstants.setLeftIndent(style, 16);
        doc.insertString(doc.getLength(), text, style);
//        doc.setParagraphAttributes(12, 1, editorPane1.getStyle("Bold"), true);
    }

    class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            rootPanel.revalidate();
            rootPanel.repaint();
        }
    }



    public static void main(String[] args) {

        Results form = new Results();
    }
}
