package gemad.i.testsystem;

import javax.swing.*;
import java.io.IOException;


public class Runner {


    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Controller(args);
        Runtime.getRuntime().addShutdownHook(new saveThread());
    }

    static class saveThread extends Thread{
        public void run(){
            Configuration.getInstance().saveConfiguration(Configuration.CONFIG_FILENAME);
        }
    }
}
