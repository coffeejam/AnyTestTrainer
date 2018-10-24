package gemad.i.testsystem.Forms;

import gemad.i.testsystem.Data.TextConsts;
import gemad.i.testsystem.TestBuilder;
import gemad.i.testsystem.Utils.Translator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.util.ArrayList;


public class SettingsDialog extends JFrame {
    private JCheckBox checkBoxShuffleQuest;
    private JCheckBox checkBoxShuffleAns;

    private JButton buttonBegin;
    private JPanel rootPanel;
    private JFileChooser openFile;
    private JTextField directoryField;
    private JButton openButton;
    private JButton addButton;
    private JList list1;
    private DefaultListModel listModel;

    public SettingsDialog() {
        super(Translator.getInstance().translate(TextConsts.PROGRAM_TITLE));
        checkBoxShuffleAns.setText(Translator.getInstance().translate(TextConsts.SHUFFLE_ANSWERS));
        checkBoxShuffleQuest.setText(Translator.getInstance().translate(TextConsts.SHUFFLE_QUESTIONS));
        buttonBegin.setText(Translator.getInstance().translate(TextConsts.BUTTON_START));
        openButton.setText(Translator.getInstance().translate(TextConsts.BUTTON_OPEN));
        addButton.setText(Translator.getInstance().translate(TextConsts.BUTTON_ADD));

        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocation(500, 250);
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
        this.getRootPane().setDefaultButton(buttonBegin);
        listModel = new DefaultListModel();
        list1.setModel(listModel);
        openFile = new JFileChooser();
        if (directoryField.getText().isEmpty()) {
            buttonBegin.setEnabled(false);
            addButton.setEnabled(false);
        }

        openFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFile.setFileFilter(new TextFilesFilter());
        openButton.addActionListener(e -> {
            openFile.setDialogTitle(Translator.getInstance().translate(TextConsts.CHOOSE_FILE));
            int res = openFile.showOpenDialog(SettingsDialog.this);
            if (res == JFileChooser.APPROVE_OPTION){
                directoryField.setText(openFile.getSelectedFile().getAbsolutePath());
                buttonBegin.setEnabled(true);
            }
        });

        addButton.addActionListener(e -> {
            String path = directoryField.getText().trim();
            listModel.addElement(path);
        });

        directoryField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                    if ("".equals(directoryField.getText())) {
//                        buttonBegin.setEnabled(false);
                        addButton.setEnabled(false);
                    } else {
//                        buttonBegin.setEnabled(true);
                        addButton.setEnabled(true);
                    }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                    if ("".equals(directoryField.getText())) {
//                        buttonBegin.setEnabled(false);
                        addButton.setEnabled(false);
                    }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    if ("".equals(directoryField.getText())) {
//                        buttonBegin.setEnabled(false);
                        addButton.setEnabled(false);
                    } else {
                        addButton.setEnabled(true);
//                        buttonBegin.setEnabled(true);
                    }
            }
        });

        listModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {if (listModel.size() == 0) {
                buttonBegin.setEnabled(false);
//                    addButton.setEnabled(false);
            } else {
                buttonBegin.setEnabled(true);
//                    addButton.setEnabled(true);
            }}

            @Override
            public void intervalRemoved(ListDataEvent e) {if (listModel.size() == 0) {
                buttonBegin.setEnabled(false);
//                    addButton.setEnabled(false);
            } else {
                buttonBegin.setEnabled(true);
//                    addButton.setEnabled(true);
            }}

            @Override
            public void contentsChanged(ListDataEvent e) {

            }

        });
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

    public String getChosenTest() {
        return directoryField.getText();
    }

    public void setDirectoryField(String path) {
        directoryField.setText(path);
    }



    public void setActionListener(ActionListener al) {
        buttonBegin.addActionListener(al);
    }

    class TextFilesFilter extends FileFilter {
        // принимает файл или отказывает ему
        public boolean accept(java.io.File file) {
            // все каталоги принимаем
            if ( file.isDirectory() ) return true;
            // для файлов смотрим на расширение
            return ( file.getName().endsWith(".txt") );
        }
        // возвращает описание фильтра
        public String getDescription() {
            return Translator.getInstance().translate(TextConsts.TEXT_FILES) + " (*.txt)";
        }
    }

    //return selected tests, if their path is right
    public ArrayList<String> getSelectedTests(){
        ArrayList<String> selected = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            selected.add((String) listModel.get(i));
        }
        boolean[] remove = new boolean[selected.size()];
        for (int i = 0; i < selected.size(); i++) {
            remove[i] = !TestBuilder.isTestFile(selected.get(i));
        }
        for (int i = selected.size()-1; i >= 0; i--) {
            if (remove[i]) selected.remove(i);
        }
        return selected;
    }

}
