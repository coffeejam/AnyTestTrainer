package gemad.i.testsystem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;


public class SettingsDialog extends JFrame {
    private JCheckBox checkBoxShuffleQuest;
    private JCheckBox checkBoxShuffleAns;

    private JButton buttonBegin;
    private JPanel rootPanel;
    private JFileChooser openFile;
    private JTextField directoryField;
    private JButton openButton;

    public SettingsDialog() {
        super("Any Test Trainer");
        this.setContentPane(rootPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocation(500, 250);
        this.setVisible(true);
        this.getRootPane().setDefaultButton(buttonBegin);
        openFile = new JFileChooser();
        if (directoryField.getText().isEmpty())
            buttonBegin.setEnabled(false);

        openFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFile.setFileFilter(new TextFilesFilter());
        openButton.addActionListener(e -> {
            openFile.setDialogTitle("Choose text file");
            int res = openFile.showOpenDialog(SettingsDialog.this);
            if (res == JFileChooser.APPROVE_OPTION){
                directoryField.setText(openFile.getSelectedFile().getAbsolutePath());
                buttonBegin.setEnabled(true);
            }
        });

        directoryField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                    if ("".equals(directoryField.getText())) {
                        buttonBegin.setEnabled(false);
                    } else {
                        buttonBegin.setEnabled(true);
                    }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                    if ("".equals(directoryField.getText())) {
                        buttonBegin.setEnabled(false);
                    }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                    if ("".equals(directoryField.getText())) {
                        buttonBegin.setEnabled(false);
                    } else {
                        buttonBegin.setEnabled(true);
                    }
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
            return "Text files (*.txt)";
        }
    }

}
