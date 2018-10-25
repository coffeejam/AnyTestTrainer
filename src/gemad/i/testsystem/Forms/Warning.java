package gemad.i.testsystem.Forms;

import gemad.i.testsystem.Data.TextConsts;
import gemad.i.testsystem.Utils.Translator;

import javax.swing.*;

public class Warning extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel jLabel;

    public Warning(String text) {
        setTitle(Translator.getInstance().translate(TextConsts.ERROR));
        buttonOK.setText(Translator.getInstance().translate(TextConsts.BUTTON_OK));
        setBounds(525, 275, 100, 100);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        jLabel.setText(text);

        buttonOK.addActionListener(e -> onOK());
        this.pack();
        this.setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

}
