package gemad.i.testsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Warning extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel jLabel;

    public Warning(String text) {
        setName("Ошибка");
        setBounds(525, 275, 100, 100);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        jLabel.setText(text);

        buttonOK.addActionListener(e -> onOK());
    }

    private void onOK() {
        // add your code here
        dispose();
    }

}
