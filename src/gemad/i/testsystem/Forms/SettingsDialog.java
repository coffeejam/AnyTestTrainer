package gemad.i.testsystem.Forms;

import gemad.i.testsystem.Configuration;
import gemad.i.testsystem.Data.TextConsts;
import gemad.i.testsystem.TestBuilder;
import gemad.i.testsystem.Utils.Translator;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.util.ArrayList;


public class SettingsDialog extends JFrame {
    private JCheckBox checkBoxShuffleQuest;
    private JCheckBox checkBoxShuffleAns;

    private JButton buttonBegin;
    private JPanel rootPanel;
    private JFileChooser openFile;
    private JButton addButton;
    private JList list1;
    private JButton deleteButton;
    private DefaultListModel listModel;

    public SettingsDialog() {
        super(Translator.getInstance().translate(TextConsts.PROGRAM_TITLE));
        checkBoxShuffleAns.setText(Translator.getInstance().translate(TextConsts.SHUFFLE_ANSWERS));
        checkBoxShuffleQuest.setText(Translator.getInstance().translate(TextConsts.SHUFFLE_QUESTIONS));
        buttonBegin.setText(Translator.getInstance().translate(TextConsts.BUTTON_START));
        addButton.setText(Translator.getInstance().translate(TextConsts.BUTTON_ADD));
        deleteButton.setText(Translator.getInstance().translate(TextConsts.BUTTON_DELETE));


        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getRootPane().setDefaultButton(buttonBegin);
        listModel = new DefaultListModel();
        list1.setModel(listModel);
        openFile = new JFileChooser();

        openFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFile.setFileFilter(new TextFilesFilter());
        addButton.addActionListener(e -> {
            openFile.setDialogTitle(Translator.getInstance().translate(TextConsts.CHOOSE_FILE));
            int res = openFile.showOpenDialog(SettingsDialog.this);
            if (res == JFileChooser.APPROVE_OPTION){
                String test = openFile.getSelectedFile().getAbsolutePath().trim();
                if (!listModel.contains(test)) {
                    listModel.addElement(test);
                    Configuration.getInstance().addTest(test);
                } else {
                    new Warning(Translator.getInstance().translate(TextConsts.TEST_EXIST));
                }
            }
        });
        list1.addListSelectionListener(e -> {
            if (list1.isSelectionEmpty()) {
                buttonBegin.setEnabled(false);
                deleteButton.setEnabled(false);
            }
            else {
                buttonBegin.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        });
        deleteButton.addActionListener(e -> removeSelected());

        this.pack();
        this.setBounds(500, 250, 500, 250);
//        this.setLocation(500, 250);
        this.setMinimumSize(this.getSize());
        this.setVisible(true);
    }

    public boolean isQuestionShuffleChecked(){
        return checkBoxShuffleQuest.isSelected();
    }

    public boolean isOptionShuffleChecked() {
        return checkBoxShuffleAns.isSelected();
    }

    public void setActionListener(ActionListener al) {
        buttonBegin.addActionListener(al);
    }

    public void setTestList(ArrayList<String> testList) {
        for (int i = 0; i < testList.size(); i++) {
            listModel.addElement(testList.get(i));
        }
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
//        for (int i = 0; i < listModel.getSize(); i++) {
//            selected.add((String) listModel.get(i));
//        }
        selected.addAll(list1.getSelectedValuesList());
        boolean[] remove = new boolean[selected.size()];
        for (int i = 0; i < selected.size(); i++) {
            remove[i] = !TestBuilder.isTestFile(selected.get(i));
        }
        for (int i = selected.size()-1; i >= 0; i--) {
            if (remove[i]) selected.remove(i);
        }
        return selected;
    }

    public ArrayList<String> getTestList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < listModel.getSize(); i++) {
            list.add((String) listModel.get(i));
        }
        return list;
    }

    private void removeSelected(){
        int[] selection = list1.getSelectedIndices();
        for (int i = selection.length-1; i >= 0; i--) {
            listModel.remove(selection[i]);
            Configuration.getInstance().removeTest((String) listModel.get(i));
        }
    }

}
