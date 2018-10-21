package gemad.i.testsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuestionForm extends JFrame {

    private JTextArea textArea1;
    private JButton answerButton;
    private JButton endButton;
    private JPanel rootPanel;
    private JPanel questionPanel;
    private JPanel optionsPanel;
    private JPanel buttonsPanel;
    private String question, imagefile;
    private ArrayList<String> options;
    private ButtonGroup buttonGroup;
    private ArrayList<JRadioButton> radioButtons;

    public QuestionForm(String question, String image, ArrayList<String> options) {
        super("");
        if (!question.isEmpty())
            this.question = question;
        if (!image.isEmpty())
            this.imagefile = image;
        this.options = options;
        rootPanel = new JPanel(new BorderLayout());
        answerButton = new JButton("Ответить");
        endButton = new JButton("Закончить");
        questionPanel = new JPanel(new BorderLayout());
        optionsPanel = new JPanel(new BorderLayout());
        buttonsPanel = new JPanel(new BorderLayout());
        rootPanelInit();
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(500, 250, 750, 500);
        this.setMinimumSize(new Dimension(500,400));
        this.setVisible(true);
        this.getRootPane().setDefaultButton(answerButton);

    }

    private void rootPanelInit() {

        Box rootBox = Box.createVerticalBox();
        rootPanel.add(rootBox);
        rootPanel.setBorder(new EmptyBorder(20,30,0,30));

        questionPanelInit();
        optionsPanelInit();
        buttonsPanelInit();

        rootBox.add(questionPanel);
        rootBox.add(optionsPanel);
        rootBox.add(Box.createVerticalGlue());
        rootBox.add(buttonsPanel);

    }

    private void questionPanelInit(){

        Box questionBox = Box.createHorizontalBox();
        questionPanel.add(questionBox);
        JScrollPane questionScroll;
        if (this.question != null){
        textArea1 = new JTextArea(question);
        textArea1.setEditable(false);
        questionScroll = new JScrollPane(textArea1);}
        else {
            JLabel image = new JLabel(new ImageIcon(imagefile));
            questionScroll = new JScrollPane(image);
        }
        questionBox.add(questionScroll);
    }

    private void optionsPanelInit() {

        optionsPanel.setBorder(new EmptyBorder(20,0,0,0));
        Box optionsBox = Box.createVerticalBox();
        optionsPanel.add(optionsBox);
        radioButtons = new ArrayList<>();
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < options.size(); i++) {
            radioButtons.add(new JRadioButton(options.get(i)));
            optionsBox.add(radioButtons.get(i));
            optionsBox.add(Box.createVerticalGlue());
            buttonGroup.add(radioButtons.get(i));
        }
        radioButtons.get(0).setSelected(true);
    }

    private void buttonsPanelInit() {

        Box buttonsBox = Box.createHorizontalBox();
        buttonsPanel.add(buttonsBox);
        buttonsBox.add(endButton);
        buttonsBox.add(Box.createHorizontalGlue());
        buttonsBox.add(answerButton);
    }

    public void setText(String text) {
        textArea1.setText(text);
    }

//    public void addRadioOption(String text, boolean selected) {
////        radioButtons.add(new JRadioButton("<html>" + text + "</html>", selected));
////        optionsPanel.add(radioButtons.get(radioButtons.size()-1));
//        optionsPanel.revalidate();
//        optionsPanel.repaint();
////        radioButtons.clear();
//    }

    public void setEndActionListener(ActionListener al) {
        endButton.addActionListener(al);
    }

    public void setSubmitActionListener(ActionListener al) {
        answerButton.addActionListener(al);
    }

    public int getAnswer() {
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).isSelected())
                return i;
        }
       return -1;
    }

//    public void setSelected(int radioButton) {
//
//    }

    //creates form with needed parameters for a question
    public void editForm(String question, String image, ArrayList<String> options) {
        if (!question.isEmpty())
            this.question = question;
        if (!image.isEmpty())
            this.imagefile = image;
        this.options = options;
        questionPanel.removeAll();
        questionPanel.invalidate();
        questionPanel.repaint();
        questionPanelInit();
        questionPanel.revalidate();

        optionsPanel.removeAll();
        optionsPanel.invalidate();
        optionsPanel.repaint();
        optionsPanelInit();
        optionsPanel.revalidate();
    }

    public static void main(String[] args) {
        ArrayList<String> options = new ArrayList<>();
        options.add("Вариант 1");
        options.add("Вариант 2");
        QuestionForm form = new QuestionForm("Тестирование вопросника", "", options);
        form.pack();
        form.setVisible(true);

    }

}
